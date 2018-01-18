package com.example.xmfy.yzubookshop.global;

import android.app.Application;
import android.util.Log;

import com.example.xmfy.yzubookshop.module.selling.bean.Category1;
import com.example.xmfy.yzubookshop.utils.CategoryLoader;

import java.util.List;

/**
 * Created by xmfy on 2018/1/17.
 */
public class MyApp extends Application {

    public static List<Category1> categoryList;

    @Override
    public void onCreate() {
        super.onCreate();
        loadCategories();
    }

    private void loadCategories() {
        categoryList = CategoryLoader.loadCategories(this);
        Log.e("Books", categoryList.toString());
    }
}
