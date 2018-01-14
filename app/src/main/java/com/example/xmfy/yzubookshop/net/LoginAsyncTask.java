package com.example.xmfy.yzubookshop.net;

import android.os.AsyncTask;
import android.util.Log;

import com.example.xmfy.yzubookshop.global.AppConstants;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xmfy on 2018/1/14.
 */
public class LoginAsyncTask extends AsyncTask<String, Void, String> {

    public AsyncResponse<User> asyncResponse;

    public void setOnAsyncResponse(AsyncResponse<User> asyncResponse)
    {
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected String doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("account", strings[0])
                .add("pwd", strings[1])
                .build();
        Request request = new Request.Builder()
                .url(AppConstants.LOGIN_ADDRESS)
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
    protected void onPostExecute(String formedString) {
        super.onPostExecute(formedString);
        Gson gson = new Gson();
        FormedData<User> formedData = gson.fromJson(formedString, new TypeToken<FormedData<User>>(){}.getType());
        asyncResponse.onDataReceivedSuccess(formedData);
    }
}
