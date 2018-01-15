package com.example.xmfy.yzubookshop.net;

import android.os.AsyncTask;

import com.example.xmfy.yzubookshop.global.AppConstants;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xmfy on 2018/1/15.
 */
public class InfoEditAsyncTask extends AsyncTask<String, Void, String> {

    private AsyncResponse asyncResponse;

    public void setAsyncResponse(AsyncResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected String doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("key", strings[0])
                .add("value", strings[1])
                .add("account", strings[2])
                .build();
        Request request = new Request.Builder()
                .url(AppConstants.USER_UPDATE_ADDRESS)
                .post(body)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String str) {
        super.onPostExecute(str);
        try {
            Gson gson = new Gson();
            FormedData<Integer> data = gson.fromJson(str, new TypeToken<FormedData<Integer>>(){}.getType());
            asyncResponse.onDataReceivedSuccess(data);
        }catch (Exception e){
            asyncResponse.onDataReceivedFailed();
        }
    }
}
