package com.example.xmfy.yzubookshop.global;

import android.app.Application;

import com.example.xmfy.yzubookshop.utils.CategoryLoader;

/**
 * Created by xmfy on 2018/1/17.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        loadCategories();
    }

    private void loadCategories() {
        CategoryLoader.loadCategories(this);
    }

}
