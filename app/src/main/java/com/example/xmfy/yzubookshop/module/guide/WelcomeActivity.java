package com.example.xmfy.yzubookshop.module.guide;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.global.AppConstants;
import com.example.xmfy.yzubookshop.utils.GuideUtils;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private GuideViewPagerAdapter adapter;
    private List<View> views;
    private Button startBtn;

    //引导页图片资源
    private static final int[] pics = { R.layout.guide_view1, R.layout.guide_view2, R.layout.guide_view3, R.layout.guide_view4};

    private ImageView[] dots;

    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        views = new ArrayList<>();

        for(int i = 0;i <pics.length;i++){
            View view = LayoutInflater.from(this).inflate(pics[i], null);
            if(i == pics.length - 1){
                startBtn = view.findViewById(R.id.btn_login);
                startBtn.setTag("enter");
                startBtn.setOnClickListener(this);
            }
            views.add(view);
        }

        viewPager = (ViewPager) findViewById(R.id.vp_welcome);
        adapter = new GuideViewPagerAdapter(views);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new PageChangeListener());
        initDots();
    }

    @Override
    protected void onPause(){
        super.onPause();
        GuideUtils.putBoolean(WelcomeActivity.this, AppConstants.FIRST_OPEN, true);
        finish();
    }

    private void initDots(){
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        dots = new ImageView[pics.length];
        for(int i = 0; i<pics.length;i++){
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(false);
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);
        }
        currentIndex = 0;
        dots[currentIndex].setEnabled((true));
    }

    /**
     * 设置当前view
     *
     * @param position
     */
    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }
        viewPager.setCurrentItem(position);
    }

    /**
     * 设置当前指示点
     *
     * @param position
     */
    private void setCurDot(int position) {
        if (position < 0 || position > pics.length || currentIndex == position) {
            return;
        }
        dots[position].setEnabled(true);
        dots[currentIndex].setEnabled(false);
        currentIndex = position;
    }



    @Override
    public void onClick(View view) {
        if (view.getTag().equals("enter")) {
            enterMainActivity();
            return;
        }
        int position = (Integer) view.getTag();
        setCurView(position);
        setCurDot(position);
    }

    private void enterMainActivity(){
        startActivity(new Intent(WelcomeActivity.this, SplashAcitivity.class));
        GuideUtils.putBoolean(WelcomeActivity.this, AppConstants.FIRST_OPEN, true);
        finish();
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setCurDot(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
}
