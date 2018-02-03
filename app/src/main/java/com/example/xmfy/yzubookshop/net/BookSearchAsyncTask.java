package com.example.xmfy.yzubookshop.net;

import android.os.AsyncTask;

import com.example.xmfy.yzubookshop.global.AppConstants;
import com.example.xmfy.yzubookshop.model.BookSearchBean;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.utils.OKHttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by xmfy on 2018/1/27.
 */
public class BookSearchAsyncTask extends AsyncTask<String, Void, String> {

    public static final int QUERY_NORMAL = 1;
    public static final int QUERY_BY_TYPE = 2;
    public static final int QUERY_HOT = 3;

    private int type = 1;

    public BookSearchAsyncTask(int type) {
        this.type = type;
    }

    private AsyncResponse asyncResponse;

    public void setAsyncResponse(AsyncResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected String doInBackground(String... strings) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        switch (type){
            case QUERY_NORMAL:
                map.put("type", strings[0]);
                map.put("value", strings[1]);
                map.put("c1", strings[2]);
                map.put("c2", strings[3]);
                map.put("min", strings[4]);
                map.put("max", strings[5]);
                map.put("sort", strings[6]);
                map.put("account", strings[7]);
                return OKHttpUtils.doGetWithParams(AppConstants.QUERY_BOOK, map);
            case QUERY_BY_TYPE:
                map.put("type", strings[0]);
                map.put("value", strings[1]);
                map.put("account", strings[2]);
                return OKHttpUtils.doGetWithParams(AppConstants.QUERY_BY_TYPE, map);
            case QUERY_HOT:
                map.put("size", strings[0]);
                map.put("account", strings[1]);
                return OKHttpUtils.doGetWithParams(AppConstants.QUERY_HOT_BOOKS, map);
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Gson gson = new Gson();
        FormedData data = gson.fromJson(s, new TypeToken<FormedData<List<BookSearchBean>>>(){}.getType());
        onResponse(data);
    }

    private void onResponse(FormedData<List<BookSearchBean>> data) {
        try {
            asyncResponse.onDataReceivedSuccess(data);
        } catch (Exception e) {
            asyncResponse.onDataReceivedFailed();
        }
    }
}
