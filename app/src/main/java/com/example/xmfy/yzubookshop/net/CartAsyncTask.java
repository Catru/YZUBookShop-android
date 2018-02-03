package com.example.xmfy.yzubookshop.net;

import android.os.AsyncTask;

import com.example.xmfy.yzubookshop.global.AppConstants;
import com.example.xmfy.yzubookshop.model.CartCollection;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.utils.OKHttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.FormBody;

/**
 * Created by xmfy on 2018/2/2.
 */
public class CartAsyncTask<T> extends AsyncTask<String, Void, String> {

    public static final int TYPE_QUERY = 1;
    public static final int TYPE_INSERT = 2;
    public static final int TYPE_DELETE = 3;
    public static final int TYPE_QUERY_COUNT = 4;

    private int type = 1;

    private AsyncResponse<T> asyncResponse;

    public CartAsyncTask(int type, AsyncResponse<T> asyncResponse) {
        this.type = type;
        this.asyncResponse = asyncResponse;
    }

    public CartAsyncTask(int type) {
        this.type = type;
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
            case TYPE_INSERT:
                FormBody body = new FormBody.Builder()
                        .add("seller", strings[0])
                        .add("bookId", strings[1])
                        .add("price", strings[2])
                        .add("buyer", strings[3])
                        .build();
                return OKHttpUtils.doPostWithParams(AppConstants.CART_INSERT, body);
            case TYPE_QUERY_COUNT:
                return OKHttpUtils.doGetWithSingleParam(AppConstants.CART_QUERY_COUNT, "buyer", strings[0]);
            case TYPE_QUERY:
                return OKHttpUtils.doGetWithSingleParam(AppConstants.CART_QUERY, "buyer", strings[0]);
            case TYPE_DELETE:
                FormBody body1 = new FormBody.Builder()
                        .add("id", strings[0]).build();
                return OKHttpUtils.doPostWithParams(AppConstants.CART_DELETE, body1);
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        FormedData data = null;
        switch (type){
            case TYPE_INSERT:
                data = new Gson().fromJson(s, new TypeToken<FormedData<Integer>>(){}.getType());
                break;
            case TYPE_QUERY_COUNT:
                data = new Gson().fromJson(s, new TypeToken<FormedData<Integer>>(){}.getType());
                break;
            case TYPE_QUERY:
                data = new Gson().fromJson(s, new TypeToken<FormedData<List<CartCollection>>>(){}.getType());
        }
        if (asyncResponse != null && data != null)
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
