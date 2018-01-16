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

    private AsyncResponse<List<Delivery>> asyncResponse;
    private int type;

    public void setAsyncResponse(int type, AsyncResponse<List<Delivery>> asyncResponse) {
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
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Gson gson = new Gson();
        FormedData data;
        switch (type){
            case METHOD_QUERY:
            case METHOD_SET_DEFAULT_LOCATION:
                data = gson.fromJson(s, new TypeToken<FormedData<List<Delivery>>>(){}.getType());
                onResponse(data);
                break;
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
