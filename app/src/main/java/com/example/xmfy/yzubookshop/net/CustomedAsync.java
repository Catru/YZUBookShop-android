package com.example.xmfy.yzubookshop.net;

import android.os.AsyncTask;
import android.util.Log;

import com.example.xmfy.yzubookshop.model.Carousel;
import com.example.xmfy.yzubookshop.model.FormedData;
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
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(strings[0]).build();
        Response response;
        try{
            response = client.newCall(request).execute();
            return response.body().string();
        }catch (IOException e){
            Log.e("JsonUtils", "异步下载失败");
            return null;
        }
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
