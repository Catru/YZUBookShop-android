package com.example.xmfy.yzubookshop.module.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.xmfy.yzubookshop.R;

/**
 * Created by xmfy on 2018/2/4.
 */

public class OrderFragment extends Fragment {

    // 1:待确认 2:待付款 3:待收货 4:待评价 5:已关闭
    private static final int CONFIRMED = 1;
    private static final int UNPAID = 2;
    private static final int UNRECEIVED = 3;
    private static final int UNCOMMENTED = 4;
    private static final int SHUT = 5;

    private int type = 1;

    private View view;
    private ListView lv_order_collection;

    public static OrderFragment newInstance(int type){
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView();
        loadContent();
    }

    private void loadContent() {
    }

    private void bindView() {
        lv_order_collection = view.findViewById(R.id.lv_order_collection);
    }
}
