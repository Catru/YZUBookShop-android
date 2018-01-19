package com.example.xmfy.yzubookshop;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by xmfy on 2018/1/19.
 */
public class CustomViewPager extends ViewPager{

    private int position;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return position != 3 && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return position != 3 && super.onInterceptTouchEvent(ev);
    }
}
