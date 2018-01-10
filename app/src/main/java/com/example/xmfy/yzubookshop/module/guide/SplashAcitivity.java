package com.example.xmfy.yzubookshop.module.guide;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.xmfy.yzubookshop.MainActivity;
import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.global.AppConstants;
import com.example.xmfy.yzubookshop.utils.GuideUtils;

public class SplashAcitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isFirstOpen = GuideUtils.getBoolean(this, AppConstants.FIRST_OPEN);
        if(!isFirstOpen){
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                enterMainActivity();
            }
        }, 2000);
    }

    private void enterMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
