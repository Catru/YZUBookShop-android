package com.example.xmfy.yzubookshop.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by xmfy on 2018/1/31.
 */
public class CustomScrollView extends ScrollView{

    private ScrollListener mScrollListener;

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollListener(ScrollListener mScrollListener) {
        this.mScrollListener = mScrollListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE){
            if (mScrollListener != null){
                int contentHeight=getChildAt(0).getHeight();
                int scrollHeight=getHeight();
                int scrollY=getScrollY();
                mScrollListener.onScroll(scrollY);

                if(scrollY+scrollHeight>=contentHeight||contentHeight<=scrollHeight)
                    mScrollListener.onScrollToBottom();
                else
                    mScrollListener.notBottom();

                if (scrollY == 0)
                    mScrollListener.onScrollToTop();
            }
        }
        return super.onTouchEvent(ev);
    }

    public interface ScrollListener{
        void onScrollToBottom();
        void onScrollToTop();
        void onScroll(int scrollY);
        void notBottom();
    }
}
