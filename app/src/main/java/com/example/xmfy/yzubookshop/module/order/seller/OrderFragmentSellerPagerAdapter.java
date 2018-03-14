package com.example.xmfy.yzubookshop.module.order.seller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by xmfy on 2018/2/4.
 */

public class OrderFragmentSellerPagerAdapter extends FragmentPagerAdapter {

    private String[] titles;

    public OrderFragmentSellerPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return SellerOrderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
