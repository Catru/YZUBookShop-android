package com.example.xmfy.yzubookshop.utils;

import android.content.SharedPreferences;

import com.example.xmfy.yzubookshop.model.User;

/**
 * Created by xmfy on 2018/1/14.
 */
public class LoginUtils {

    public static boolean clearUser(SharedPreferences preferences){
        return preferences.edit().clear().commit();
    }

    public static boolean isLogined(SharedPreferences preferences) {
        return !preferences.getString("account", "").equals("");
    }

    public static User loadUser(SharedPreferences preferences){
        if (!isLogined(preferences))
            return null;
        String account = preferences.getString("account", "");
        String pwd = preferences.getString("pwd", "");
        String username = preferences.getString("username", "");
        String gender = preferences.getString("gender", "");
        String phone = preferences.getString("phone", "");
        String headshot = preferences.getString("headshot", "");
        return new User(account, pwd, username, gender, phone, headshot);
    }

    public static void saveUser(SharedPreferences preferences, User user){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("account", user.getAccount());
        editor.putString("pwd", user.getPwd());
        editor.putString("username", user.getUsername());
        editor.putString("gender",user.getGender());
        editor.putString("phone", user.getPhone());
        editor.putString("headshot", user.getHeadshot());
        editor.apply();
    }

    public static String getUsername(SharedPreferences preferences){
        return preferences.getString("username", "");
    }

    public static String getHeadshot(SharedPreferences preferences){
        return preferences.getString("headshot", "");
    }
}
