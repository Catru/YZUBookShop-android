package com.example.xmfy.yzubookshop.net;

import android.os.AsyncTask;

import com.example.xmfy.yzubookshop.global.AppConstants;
import com.example.xmfy.yzubookshop.model.Delivery;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.utils.OKHttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.FormBody;

/**
 * Created by xmfy on 2018/1/16.
 */
public class DeliveryAsyncTask extends AsyncTask<String, Void, String>{

    public static final int METHOD_QUERY = 1;
    public static final int METHOD_SET_DEFAULT_LOCATION = 2;
    public static final int METHOD_UPDATE = 3;
    public static final int METHOD_DELETE = 4;
    public static final int METHOD_ADD = 5;

    private AsyncResponse asyncResponse;
    private int type;

    public void setAsyncResponse(int type, AsyncResponse asyncResponse) {
        this.type = type;
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected String doInBackground(String... strings) {
        switch (type){
            case METHOD_QUERY:
                return OKHttpUtils.doGetWithSingleParam(AppConstants.DELIVERY_ADDRESS, "account", strings[0]);
            case METHOD_SET_DEFAULT_LOCATION:
                FormBody body = new FormBody.Builder()
                        .add("id", strings[0])
                        .add("account", strings[1])
                        .build();
                return OKHttpUtils.doPostWithParams(AppConstants.DELIVERY_SET_DEFAULT, body);
            case METHOD_ADD:
                FormBody body_add = new FormBody.Builder()
                        .add("account", strings[0])
                        .add("receiver", strings[1])
                        .add("phone", strings[2])
                        .add("province", strings[3])
                        .add("city", strings[4])
                        .add("district", strings[5])
                        .add("location", strings[6])
                        .add("defaults", strings[7])
                        .build();
                return OKHttpUtils.doPostWithParams(AppConstants.DELIVERY_ADD, body_add);
            case METHOD_DELETE:
                FormBody body_delete = new FormBody.Builder()
                        .add("id", strings[0])
                        .build();
                return OKHttpUtils.doPostWithParams(AppConstants.DELIVERY_DELETE, body_delete);
            case METHOD_UPDATE:
                FormBody body_update = new FormBody.Builder()
                        .add("id",strings[0])
                        .add("account", strings[1])
                        .add("receiver", strings[2])
                        .add("phone", strings[3])
                        .add("province", strings[4])
                        .add("city", strings[5])
                        .add("district", strings[6])
                        .add("location", strings[7])
                        .add("defaults", strings[8])
                        .build();
                return OKHttpUtils.doPostWithParams(AppConstants.DELIVERY_UPDATE, body_update);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Gson gson = new Gson();
        if (type == METHOD_SET_DEFAULT_LOCATION){
            FormedData data = gson.fromJson(s, new TypeToken<FormedData<Integer>>(){}.getType());
            onResponse(data);
        }else {
            FormedData data = gson.fromJson(s, new TypeToken<FormedData<List<Delivery>>>(){}.getType());
            onResponse(data);
        }
    }

    private void onResponse(FormedData data){
        try {
            asyncResponse.onDataReceivedSuccess(data);
        }catch (Exception e){
            asyncResponse.onDataReceivedFailed();
        }
    }

}
