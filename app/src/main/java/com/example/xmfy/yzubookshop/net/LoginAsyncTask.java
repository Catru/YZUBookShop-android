package com.example.xmfy.yzubookshop.net;

import android.os.AsyncTask;
import android.util.Log;

import com.example.xmfy.yzubookshop.global.AppConstants;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.model.User;
import com.example.xmfy.yzubookshop.utils.OKHttpUtils;
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
        FormBody body = new FormBody.Builder()
                .add("account", strings[0])
                .add("pwd", strings[1])
                .build();
        return OKHttpUtils.doPostWithParams(AppConstants.LOGIN_ADDRESS, body);
    }

    @Override
    protected void onPostExecute(String formedString) {
        super.onPostExecute(formedString);
        Gson gson = new Gson();
        FormedData<User> formedData = gson.fromJson(formedString, new TypeToken<FormedData<User>>(){}.getType());
        asyncResponse.onDataReceivedSuccess(formedData);
    }
}
