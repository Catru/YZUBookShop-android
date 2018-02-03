package com.example.xmfy.yzubookshop.net;

import android.os.AsyncTask;

import com.example.xmfy.yzubookshop.global.AppConstants;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.model.News;
import com.example.xmfy.yzubookshop.utils.OKHttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by xmfy on 2018/2/3.
 */
public class NewsAsyncTask extends AsyncTask<String, Void, String> {
    private AsyncResponse asyncResponse;

    public void setAsyncResponse(AsyncResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected String doInBackground(String... strings) {
        return OKHttpUtils.doGetWithSingleParam(AppConstants.NEWS_ADDRESS, "size",
                strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Gson gson = new Gson();
        FormedData<List<News>> data = gson.fromJson(s, new TypeToken<FormedData<List<News>>>(){}.getType());
        if (asyncResponse != null){
            onResponse(data);
        }
    }

    private void onResponse(FormedData data) {
        try {
            asyncResponse.onDataReceivedSuccess(data);
        } catch (Exception e) {
            asyncResponse.onDataReceivedFailed();
        }
    }
}
