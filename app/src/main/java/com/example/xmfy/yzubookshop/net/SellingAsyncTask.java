package com.example.xmfy.yzubookshop.net;

import android.os.AsyncTask;

import com.example.xmfy.yzubookshop.global.AppConstants;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.model.Selling;
import com.example.xmfy.yzubookshop.utils.OKHttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;

/**
 * Created by xmfy on 2018/1/22.
 */
public class SellingAsyncTask<T> extends AsyncTask<String, Void, String> {

    public static final int METHOD_QUERY = 1;
    public static final int METHOD_UPDATE = 2;
    public static final int METHOD_DELETE = 3;
    public static final int METHOD_ADD = 4;
    public static final int METHOD_UPLOAD_PHOTOS = 5;

    private int type;
    private AsyncResponse<T> asyncResponse;

    public void setType(int type) {
        this.type = type;
    }

    public void setAsyncResponse(AsyncResponse<T> asyncResponse) {
        this.asyncResponse = asyncResponse;
    }

    @Override
    protected String doInBackground(String... strings) {
        switch (type) {
            case METHOD_QUERY:
                return OKHttpUtils.doGetWithSingleParam(AppConstants.SELLING_QUERY, "account", strings[0]);
            case METHOD_UPDATE:
                FormBody body = new FormBody.Builder()
                        .add("id", strings[0])
                        .add("account", strings[1])
                        .add("title", strings[2])
                        .add("author", strings[3])
                        .add("price", strings[4])
                        .add("keywords", strings[5])
                        .add("category1", strings[6])
                        .add("category2", strings[7])
                        .add("description", strings[8])
                        .add("photoUrl", strings[9])
                        .build();
                return OKHttpUtils.doPostWithParams(AppConstants.SELLING_UPDATE, body);
            case METHOD_UPLOAD_PHOTOS:
                List<File> files = new ArrayList<>();
                for (int i =1;i<strings.length;i++)
                    files.add(new File(strings[i]));
                return OKHttpUtils.upLoadFiles(strings[0], files);
            case METHOD_ADD:
                List<File> newSellingPhotos = new ArrayList<>();
                for (int i = 9; i < strings.length ;i++)
                    newSellingPhotos.add(new File(strings[i]));
                return OKHttpUtils.postNewSelling(newSellingPhotos, strings[0], strings[1], strings[2], strings[3], strings[4], strings[5], strings[6], strings[7], strings[8]);
            case METHOD_DELETE:
                return OKHttpUtils.doPostWithParams(AppConstants.SELLING_DELETE, new FormBody.Builder().add("id", strings[0]).build());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Gson gson = new Gson();
        FormedData data;
        switch (type) {
            case METHOD_QUERY:
                data = gson.fromJson(s, new TypeToken<FormedData<List<Selling>>>() {}.getType());
                onResponse(data);
                break;
            case METHOD_UPDATE:
                data = gson.fromJson(s, new TypeToken<FormedData<Integer>>() {}.getType());
                onResponse(data);
                break;
            case METHOD_UPLOAD_PHOTOS:
                data = gson.fromJson(s, new TypeToken<FormedData<Integer>>() {}.getType());
                onResponse(data);
                break;
            case METHOD_ADD:
                data = gson.fromJson(s, new TypeToken<FormedData<Integer>>() {}.getType());
                onResponse(data);
                break;
            case METHOD_DELETE:
                data = gson.fromJson(s, new TypeToken<FormedData<Integer>>() {}.getType());
                onResponse(data);
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
