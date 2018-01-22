package com.example.xmfy.yzubookshop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.xmfy.yzubookshop.fragments.BuyFragment;
import com.example.xmfy.yzubookshop.fragments.MineFragment;
import com.example.xmfy.yzubookshop.fragments.NewsFragment;
import com.example.xmfy.yzubookshop.fragments.SectionsPagerAdapter;
import com.example.xmfy.yzubookshop.fragments.SellFragment;
import com.example.xmfy.yzubookshop.fragments.TopsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    private BottomNavigationBar bottomNavigationBar;
    private CustomViewPager vp;
    private List<Fragment> fragments;
    int lastSelectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBottomNavigationBar();
        initViewPager();
    }

    private void initViewPager() {
        vp = (CustomViewPager) findViewById(R.id.main_vp);
        fragments = new ArrayList<>();
        fragments.add(new NewsFragment());
        fragments.add(new TopsFragment());
        fragments.add(new BuyFragment());
        fragments.add(new SellFragment());
        fragments.add(new MineFragment());
        vp.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(), fragments));
        vp.addOnPageChangeListener(this);
        vp.setCurrentItem(2);
    }

    private void initBottomNavigationBar() {
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.clearAll();
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setBarBackgroundColor(R.color.BottomBar);
        bottomNavigationBar.setActiveColor(R.color.White);
        bottomNavigationBar.setInActiveColor(R.color.LightGreen);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_news_white, "资讯"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_tops_white, "推荐"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_book_white, "购书"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_sell_white, "卖书"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_mine_white, "我的"))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();
    }

    @Override
    public void onTabSelected(int position) {
        lastSelectedPosition = position;
        vp.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        vp.setPosition(position);
        lastSelectedPosition = position;
        bottomNavigationBar.selectTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
