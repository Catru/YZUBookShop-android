package com.example.xmfy.yzubookshop.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xmfy on 2018/1/9.
 */
public class ImageLoader {

    private RecyclerView recyclerView;
    private int type;
    public static final int TYPE_NEWS=1;
    public static final int TYPE_CONTACTS=2;

    public ImageLoader(RecyclerView recyclerView, int type) {
        this.recyclerView = recyclerView;
        this.type = type;
    }

    public Bitmap getBitmapFromURL(String urlString) {
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
            return bitmap;
        } catch (IOException e) {
            return null;
        } finally {
            try {
                if(is!=null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadImage(ImageView imageView, String url){
        NewsAsyncTask task = new NewsAsyncTask(imageView);
        task.execute(url);
    }

    private class NewsAsyncTask extends AsyncTask<String, Void, Bitmap>{
        ImageView imageView;

        public NewsAsyncTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            return getBitmapFromURL(strings[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageView != null && bitmap != null)
                imageView.setImageBitmap(bitmap);
        }

    }

}
