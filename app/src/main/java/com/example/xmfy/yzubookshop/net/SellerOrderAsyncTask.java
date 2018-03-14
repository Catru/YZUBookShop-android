package com.example.xmfy.yzubookshop.net;

import android.os.AsyncTask;

import com.example.xmfy.yzubookshop.global.AppConstants;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.model.OrderCollection;
import com.example.xmfy.yzubookshop.utils.OKHttpUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.FormBody;

/**
 * Created by xmfy on 2018/2/4.
 */

public class OrderAsyncTask<T> extends AsyncTask<String, Void, String> {


    public static final int QUERY_BALANCE = 1;
    public static final int BUYER_SUBMIT = 2;
    public static final int BUYER_QUERY = 3;
    public static final int BUYER_CONFIRM_DELIVERY = 4;
    public static final int SELLER_CONFIRM_ORDER = 5;
    public static final int SELLER_SEND_DELIVERY = 6;

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
            case QUERY_BALANCE:
                return OKHttpUtils.doGetWithSingleParam(AppConstants.ACCOUNT_QUERY_BALANCE, "account", strings[0]);
            case BUYER_SUBMIT:
                FormBody body = new FormBody.Builder()
                        .add("account", strings[0])
                        .add("receiver", strings[1])
                        .add("phone", strings[2])
                        .add("province", strings[3])
                        .add("city", strings[4])
                        .add("district", strings[5])
                        .add("location", strings[6])
                        .add("cartCollections", strings[7])
                        .build();
                return OKHttpUtils.doPostWithParams(AppConstants.ORDER_BUYER_SUBMIT, body);
            case BUYER_QUERY:
                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                map.put("account", strings[0]);
                map.put("flag", strings[1]);
                return OKHttpUtils.doGetWithParams(AppConstants.ORDER_BUYER_QUERY, map);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Gson gson = new Gson();
        FormedData data;
        switch (type){

            case QUERY_BALANCE:
                data = gson.fromJson(s, new TypeToken<FormedData<Float>>() {}.getType());
                onResponse(data);
                break;
            case BUYER_SUBMIT:
                data = gson.fromJson(s, new TypeToken<FormedData<Integer>>() {}.getType());
                onResponse(data);
                break;
            case BUYER_QUERY:
                gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                data = gson.fromJson(s, new TypeToken<FormedData<List<OrderCollection>>>(){}.getType());
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
