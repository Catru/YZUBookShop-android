package com.example.xmfy.yzubookshop.utils;

import com.example.xmfy.yzubookshop.global.AppConstants;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xmfy on 2018/1/16.
 */
public class OKHttpUtils {

    public static String doPostWithParams(String url, FormBody body) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
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

    public static String doPost(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
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

    public static String doGetWithParams(String url, LinkedHashMap<String, String> params) {
        Iterator<String> keys = params.keySet().iterator();
        Iterator<String> values = params.values().iterator();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("?");
        for (int i = 0; i < params.size(); i++) {
            stringBuffer.append(keys.next() + "=" + values.next());
            if (i != params.size() - 1)
                stringBuffer.append("&");
        }
        url += stringBuffer.toString();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            return null;
        }
    }

    public static String doGetWithSingleParam(String url, String key, String value) {
        url += "?" + key + "=" + value;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            return null;
        }
    }

    public static String doGet(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            return null;
        }
    }

    public static String upLoadFiles(String account, List<File> fileList){
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");
        builder.addFormDataPart("account", account);
        for (File file : fileList)
            builder.addFormDataPart("img", file.getName(), RequestBody.create(MEDIA_TYPE_JPG, file));
        MultipartBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(AppConstants.SELLING_UPLOADPHOTOS)
                .post(requestBody)
                .build();
        OkHttpClient client = new OkHttpClient();
        Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        }catch (IOException e){
            return null;
        }
    }


}
