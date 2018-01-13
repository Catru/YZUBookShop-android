package com.example.xmfy.yzubookshop.module.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.example.xmfy.yzubookshop.R;

/**
 * Created by xmfy on 2018/1/10.
 */
public class NewsActivity extends AppCompatActivity{

    private WebView webView;
    private Button btn_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initView();
        initWebView();
    }

    private void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl(getIntent().getStringExtra("url"));
    }

    private void initView(){
        webView = (WebView) findViewById(R.id.wv_news_detail);
        btn_back = (Button) findViewById(R.id.toolbar_left_btn);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsActivity.this.finish();
            }
        });
    }


}
