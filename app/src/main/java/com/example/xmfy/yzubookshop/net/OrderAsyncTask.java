package com.example.xmfy.yzubookshop.net;

import android.os.AsyncTask;
import android.util.Log;

import com.example.xmfy.yzubookshop.global.AppConstants;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.utils.OKHttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.FormBody;

/**
 * Created by xmfy on 2018/2/4.
 */

public class OrderAsyncTask<T> extends AsyncTask<String, Void, String> {

    public static final int VALID = 1;
    public static final int QUERY = 2;
    public static final int INSERT = 2;

    private int type;
    private AsyncResponse<T> asyncResponse;

    public OrderAsyncTask(int type, AsyncResponse<T> asyncResponse) {
        this.type = type;
        this.asyncResponse = asyncResponse;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setAsyncResponse(AsyncResponse<T> asyncResponse) {
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected String doInBackground(String... strings) {
        switch (type){
            case VALID:
                FormBody body = new FormBody.Builder()
                        .add("account", strings[0])
                        .add("bookIds", strings[1])
                        .build();
                return OKHttpUtils.doPostWithParams(AppConstants.ORDER_VALID, body);
            case INSERT:
                FormBody insertBody = new FormBody.Builder()
                        .add("account", strings[0])
                        .add("cartCollections", strings[1])
                        .build();
                return OKHttpUtils.doPostWithParams(AppConstants.ORDER_INSERT, insertBody);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Gson gson = new Gson();
        FormedData data;
        switch (type){
            case VALID:
                data = gson.fromJson(s, new TypeToken<FormedData<Integer>>() {}.getType());
                onResponse(data);
                break;
            case INSERT:
                Log.e("order", s);
                data = gson.fromJson(s, new TypeToken<FormedData<Integer>>() {}.getType());
                onResponse(data);
                break;
        }
    }

    private void onResponse(FormedData<T> data) {
        if (asyncResponse != null){
            try {
                asyncResponse.onDataReceivedSuccess(data);
            } catch (Exception e) {
                asyncResponse.onDataReceivedFailed();
            }
        }
    }
}
