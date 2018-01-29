package com.example.xmfy.yzubookshop.module.buy;

import android.content.Context;
import android.widget.Toast;

import com.example.xmfy.yzubookshop.model.Book;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.module.buy.searchTab.SearchConditions;
import com.example.xmfy.yzubookshop.net.AsyncResponse;
import com.example.xmfy.yzubookshop.net.BookSearchAsyncTask;
import com.example.xmfy.yzubookshop.net.BookSuggestionAsyncTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xmfy on 2018/1/26.
 */
public class BookSearchHelper {

    private static final String COLORS_FILE_NAME = "books.json";

    private static List<Book> bookList = new ArrayList<>();

    private static List<BookSuggestion> bookSuggestions = new ArrayList<>(Arrays.asList(
            new BookSuggestion("title","解忧杂货铺1"),
            new BookSuggestion("keywords","杂货铺"),
            new BookSuggestion("author","东野圭吾")
    ));

    public interface OnFindColorsListener {
        void onResults(List<Book> results);
    }

    public interface OnFindSuggestionsListener {
        void onResults(List<BookSuggestion> results);
    }

    public static List<BookSuggestion> getHistory(Context context, int count) {
        List<BookSuggestion> suggestionList = new ArrayList<>();
        for (int i = 0; i < bookSuggestions.size(); i++) {
            BookSuggestion bookSuggestion = bookSuggestions.get(i);
            bookSuggestion.setHistory(true);
            suggestionList.add(bookSuggestion);
            if (suggestionList.size() == count)
                break;
        }
        return suggestionList;
    }

    public static void resetSuggestionsHistory() {
        for (BookSuggestion bookSuggestion : bookSuggestions)
            bookSuggestion.setHistory(false);
    }

    public static void findSuggestions(final Context context, final String query, final int limit, final int c1, final int c2, final long simulatedDelay,
                                       final OnFindSuggestionsListener listener) {
        BookSearchHelper.resetSuggestionsHistory();
        BookSuggestionAsyncTask task = new BookSuggestionAsyncTask();
        task.setAsyncResponse(new AsyncResponse<List<BookSuggestion>>() {
            @Override
            public void onDataReceivedSuccess(FormedData<List<BookSuggestion>> formedData) {
                if (formedData.isSuccess()){
                    listener.onResults(formedData.getData());
                } else
                    Toast.makeText(context, formedData.getError(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDataReceivedFailed() {
                Toast.makeText(context, "网络连接错误, 请检查相关设置!", Toast.LENGTH_SHORT).show();
            }
        });
        task.execute(query, limit+"", c1+"", c2+"");
//        try {
//            Thread.sleep(simulatedDelay)
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public static void findBooks(final Context context, String type, String value, final OnFindColorsListener listener) {
        initBookList(context);
        BookSearchAsyncTask<List<Book>> task = new BookSearchAsyncTask<>();
        task.setResponseType(new TypeToken<FormedData<List<Book>>>(){});
        task.setAsyncResponse(new AsyncResponse<List<Book>>() {
            @Override
            public void onDataReceivedSuccess(FormedData<List<Book>> formedData) {
                if (formedData.isSuccess()){
                    listener.onResults(formedData.getData());
                }else {
                    Toast.makeText(context, formedData.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDataReceivedFailed() {
                Toast.makeText(context, "网络连接异常,请检查相关设置!", Toast.LENGTH_SHORT).show();
            }
        });
        task.execute(type, value);
    }

    public static void findBooks(final Context context, SearchConditions conditions, final OnFindColorsListener listener) {
        initBookList(context);
        BookSearchAsyncTask<List<Book>> task = new BookSearchAsyncTask<>();
        task.setResponseType(new TypeToken<FormedData<List<Book>>>(){});
        task.setAsyncResponse(new AsyncResponse<List<Book>>() {
            @Override
            public void onDataReceivedSuccess(FormedData<List<Book>> formedData) {
                if (formedData.isSuccess()){
                    listener.onResults(formedData.getData());
                }else {
                    Toast.makeText(context, formedData.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDataReceivedFailed() {
                Toast.makeText(context, "网络连接异常,请检查相关设置!", Toast.LENGTH_SHORT).show();
            }
        });
        task.execute(conditions.getType(), conditions.getValue(), conditions.getC1()+"", conditions.getC2()+"", conditions.getMin()+"", conditions.getMax()+"", conditions.getSort()+"", conditions.getAccount());
    }

    private static void initBookList(Context context) {
        if (bookList.isEmpty()) {
            String jsonString = loadJson(context);
            bookList = deserializeColors(jsonString);
        }
    }

    private static String loadJson(Context context) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(COLORS_FILE_NAME);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return jsonString;
    }

    private static List<Book> deserializeColors(String jsonString) {

        Gson gson = new Gson();

        Type collectionType = new TypeToken<List<Book>>() {
        }.getType();
        return gson.fromJson(jsonString, collectionType);
    }
}
