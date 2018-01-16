package com.example.xmfy.yzubookshop.net;

import android.os.AsyncTask;
import android.util.Log;

import com.example.xmfy.yzubookshop.model.Carousel;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.utils.OKHttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xmfy on 2018/1/8.
 */
public class CustomedAsync extends AsyncTask<String, Void, String> {

    public AsyncResponse asyncResponse;

    public void setOnAsyncResponse(AsyncResponse asyncResponse)
    {
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected String doInBackground(String... strings) {
        return OKHttpUtils.doGet(strings[0]);
    }

    @Override
    protected void onPostExecute(String msg) {
        super.onPostExecute(msg);
        if (msg != null){
            Gson gson = new Gson();
            FormedData formedData = gson.fromJson(msg, new TypeToken<FormedData<List<Carousel>>>(){}.getType());
            asyncResponse.onDataReceivedSuccess(formedData);
        }else
            asyncResponse.onDataReceivedFailed();
    }
}
