package com.example.xmfy.yzubookshop.module.buy;

import android.content.Context;
import android.util.Log;
import android.widget.Filter;

import com.example.xmfy.yzubookshop.model.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by xmfy on 2018/1/26.
 */
public class BookSearchHelper {

    private static final String COLORS_FILE_NAME = "books.json";

    private static List<Book> bookList = new ArrayList<>();

    private static List<BookSuggestion> bookSuggestions = new ArrayList<>(Arrays.asList(
            new BookSuggestion("解忧杂货铺1"),
            new BookSuggestion("解忧杂货铺2"),
            new BookSuggestion("解忧杂货铺3"),
            new BookSuggestion("解忧杂货铺4"),
            new BookSuggestion("解忧杂货铺5"),
            new BookSuggestion("解忧杂货铺6"),
            new BookSuggestion("解忧杂货铺7"),
            new BookSuggestion("解忧杂货铺8")
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

    public static void findSuggestions(Context context, String query, final int limit, final long simulatedDelay,
                                       final OnFindSuggestionsListener listener) {
        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                try {
                    Thread.sleep(simulatedDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BookSearchHelper.resetSuggestionsHistory();
                List<BookSuggestion> bookSuggestionList = new ArrayList<BookSuggestion>();
                if (!(constraint == null || constraint.length() == 0)) {

                    for (BookSuggestion suggestion : bookSuggestions) {
                        if (suggestion.getBody().toUpperCase()
                                .contains(constraint.toString().toUpperCase())) {

                            bookSuggestionList.add(suggestion);
                            if (limit != -1 && bookSuggestionList.size() == limit) {
                                break;
                            }
                        }
                    }
                }

                FilterResults results = new FilterResults();
                Collections.sort(bookSuggestionList, new Comparator<BookSuggestion>() {
                    @Override
                    public int compare(BookSuggestion book1, BookSuggestion book2) {
                        return book1.isHistory() ? -1 : 0;
                    }
                });
                results.values = bookSuggestionList;
                results.count = bookSuggestionList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                if (listener != null) {
                    listener.onResults((List<BookSuggestion>) results.values);
                }
            }
        }.filter(query);
    }

    public static void findBooks(Context context, String query, final OnFindColorsListener listener) {
        initBookList(context);
        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                List<Book> books = new ArrayList<>();
                if (!(constraint == null || constraint.length() == 0)) {
                    for (Book book : bookList) {
                        if (book.getTitle().toUpperCase()
                                .contains(constraint.toString().toUpperCase())) {
                            books.add(book);
                        }
                    }

                }
                FilterResults results = new FilterResults();
                results.values = books;
                results.count = books.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                Log.e("publishResults", results.values.toString());
                Log.e("publishResults", results.count+"");
                if (listener != null) {
                    listener.onResults((List<Book>) results.values);
                }
            }
        }.filter(query);
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
