package com.example.xmfy.yzubookshop.global;

import android.app.Application;
import android.util.Log;

import com.example.xmfy.yzubookshop.module.selling.bean.Category1;
import com.example.xmfy.yzubookshop.module.selling.bean.Category2;
import com.example.xmfy.yzubookshop.utils.CategoryLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xmfy on 2018/1/17.
 */
public class MyApp extends Application {

    public static List<Category1> cList1;
    public static List<List<Category2>>  cList2;

    @Override
    public void onCreate() {
        super.onCreate();
        loadCategories();
    }

    private void loadCategories() {
        cList1 = new ArrayList<>();
        cList2 = new ArrayList<>();
        CategoryLoader.loadCategories(this, cList1, cList2);
        Log.e("options", cList1.toString());
        Log.e("options", cList2.toString());
    }
}
