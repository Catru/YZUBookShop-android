package com.example.xmfy.yzubookshop.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xmfy on 2018/1/3.
 */
public class GuideUtils {

    private static final String guideFileName = "guide";

    public static String getString(Context context, String strKey){
        SharedPreferences setPreferences = context.getSharedPreferences(guideFileName, Context.MODE_PRIVATE);
        return setPreferences.getString(strKey, "");
    }

    public static String getString(Context context, String strKey,
                                   String strDefault) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                guideFileName, Context.MODE_PRIVATE);
        return setPreferences.getString(strKey, strDefault);
    }

    public static void putString(Context context, String strKey, String strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                guideFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putString(strKey, strData);
        editor.apply();
    }

    public static Boolean getBoolean(Context context, String strKey) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                guideFileName, Context.MODE_PRIVATE);
        return setPreferences.getBoolean(strKey, false);
    }

    public static Boolean getBoolean(Context context, String strKey,
                                     Boolean strDefault) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                guideFileName, Context.MODE_PRIVATE);
        return setPreferences.getBoolean(strKey, strDefault);
    }

    public static void putBoolean(Context context, String strKey,
                                  Boolean strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                guideFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putBoolean(strKey, strData);
        editor.apply();
    }

    public static int getInt(Context context, String strKey) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                guideFileName, Context.MODE_PRIVATE);
        return setPreferences.getInt(strKey, -1);
    }

    public static int getInt(Context context, String strKey, int strDefault) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                guideFileName, Context.MODE_PRIVATE);
        return setPreferences.getInt(strKey, strDefault);
    }

    public static void putInt(Context context, String strKey, int strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                guideFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putInt(strKey, strData);
        editor.apply();
    }

    public static long getLong(Context context, String strKey) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                guideFileName, Context.MODE_PRIVATE);
        return setPreferences.getLong(strKey, -1);
    }

    public static long getLong(Context context, String strKey, long strDefault) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                guideFileName, Context.MODE_PRIVATE);
        return setPreferences.getLong(strKey, strDefault);
    }

    public static void putLong(Context context, String strKey, long strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                guideFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putLong(strKey, strData);
        editor.apply();
    }
}
