package com.example.xmfy.yzubookshop.net;

import android.os.AsyncTask;

import com.example.xmfy.yzubookshop.global.AppConstants;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.model.Selling;
import com.example.xmfy.yzubookshop.utils.OKHttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.FormBody;

/**
 * Created by xmfy on 2018/1/30.
 */
public class CollectionAsyncTask<T> extends AsyncTask<String, Void, String>{

    public static final int TYPE_CHANGE = 1;
    public static final int TYPE_QUERY = 2;

    private int type = 1;
    private AsyncResponse<T> asyncResponse;

    public void setType(int type) {
        this.type = type;
    }

    public void setAsyncResponse(AsyncResponse<T> asyncResponse) {
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected String doInBackground(String... strings) {
        switch (type){
            case TYPE_CHANGE:
                FormBody formBody = new FormBody.Builder()
                        .add("account", strings[0])
                        .add("bookId", strings[1])
                        .add("method", strings[2])
                        .build();
                return OKHttpUtils.doPostWithParams(AppConstants.COLLECTS_CHANGE, formBody);
            case TYPE_QUERY:
                return OKHttpUtils.doGetWithSingleParam(AppConstants.COLLECTS_QUERY, "account", strings[0]);
            default:
                return "";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        switch (type){
            case TYPE_QUERY:
                Gson gson = new Gson();
                FormedData data = gson.fromJson(s, new TypeToken<FormedData<List<Selling>>>(){}.getType());
                onResponse(data);
                break;
            case TYPE_CHANGE:
                if (asyncResponse!=null){
                    FormedData data2 = new FormedData(true, Integer.parseInt(s));
                    onResponse(data2);
                }
                break;
        }
    }

    private void onResponse(FormedData<T> data) {
        try {
            asyncResponse.onDataReceivedSuccess(data);
        } catch (Exception e) {
            asyncResponse.onDataReceivedFailed();
        }
    }
}
