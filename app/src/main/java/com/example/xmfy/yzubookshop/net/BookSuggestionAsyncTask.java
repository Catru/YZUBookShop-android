package com.example.xmfy.yzubookshop.net;

import android.os.AsyncTask;

import com.example.xmfy.yzubookshop.global.AppConstants;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.module.buy.BookSuggestion;
import com.example.xmfy.yzubookshop.utils.OKHttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by xmfy on 2018/1/27.
 */
public class BookSuggestionAsyncTask extends AsyncTask<String, Void, String> {

    private AsyncResponse asyncResponse;

    public void setAsyncResponse(AsyncResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected String doInBackground(String... strings) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("value", strings[0]);
        map.put("size", strings[1]);
        map.put("category1", strings[2]);
        map.put("category2", strings[3]);
        return OKHttpUtils.doGetWithParams(AppConstants.BOOK_BUY_SUGGESTION, map);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Gson gson = new Gson();
        FormedData<List<BookSuggestion>> data = gson.fromJson(s, new TypeToken<FormedData<List<BookSuggestion>>>(){}.getType());
        onResponse(data);
    }

    private void onResponse(FormedData<List<BookSuggestion>> data) {
        try {
            asyncResponse.onDataReceivedSuccess(data);
        } catch (Exception e) {
            asyncResponse.onDataReceivedFailed();
        }
    }
}
