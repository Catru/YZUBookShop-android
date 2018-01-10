package com.example.xmfy.yzubookshop.utils;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xmfy on 2018/1/8.
 */
public class DownloadUtils {

    public static File downloadFile(String url, final String target) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("image", "下载图片失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException{
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len;
                FileOutputStream fos = null;
                File file = new File(target);
                try {
                    file.createNewFile();
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf))!=-1)
                        fos.write(buf, 0 ,len);
                    fos.flush();
                }catch (IOException e){
                    Log.e("image", "下载图片失败");
                    e.printStackTrace();
                }finally {
                    if (is!=null)
                        is.close();
                    if (fos!=null)
                        fos.close();
                }
            }
        });
        return new File(target);
    }

}
