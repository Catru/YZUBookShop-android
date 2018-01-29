package com.example.xmfy.yzubookshop.net;

import android.os.AsyncTask;

import com.example.xmfy.yzubookshop.global.AppConstants;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.utils.OKHttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.LinkedHashMap;

/**
 * Created by xmfy on 2018/1/27.
 */
public class BookSearchAsyncTask<T> extends AsyncTask<String, Void, String> {

    private AsyncResponse<T> asyncResponse;
    private TypeToken<FormedData<T>> responseType;

    public void setResponseType(TypeToken<FormedData<T>> responseType) {
        this.responseType = responseType;
    }

    public void setAsyncResponse(AsyncResponse<T> asyncResponse) {
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected String doInBackground(String... strings) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("type", strings[0]);
        map.put("value", strings[1]);
        map.put("c1", strings[2]);
        map.put("c2", strings[3]);
        map.put("min", strings[4]);
        map.put("max", strings[5]);
        map.put("sort", strings[6]);
        map.put("account", strings[7]);
        return OKHttpUtils.doGetWithParams(AppConstants.BOOK_SEARCH_BY_TYPE, map);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Gson gson = new Gson();
        FormedData data = gson.fromJson(s, responseType.getType());
        onResponse(data);
    }

    private void onResponse(FormedData<T> data) {
        try {
            asyncResponse.onDataReceivedSuccess(data);
        } catch (Exception e) {
            asyncResponse.onDataReceivedFailed();
        }
    }
}
