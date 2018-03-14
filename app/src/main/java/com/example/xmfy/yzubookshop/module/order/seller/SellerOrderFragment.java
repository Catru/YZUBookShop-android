package com.example.xmfy.yzubookshop.module.order;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.model.OrderCollection;
import com.example.xmfy.yzubookshop.net.AsyncResponse;
import com.example.xmfy.yzubookshop.net.OrderAsyncTask;
import com.example.xmfy.yzubookshop.utils.LoginUtils;

import java.util.List;

/**
 * Created by xmfy on 2018/2/4.
 */

public class OrderFragment extends Fragment {

    // 1:待确认 2:待付款 3:待发货 4:待收货 5:已完成
    private static final int CONFIRM = 1;
    private static final int PAY = 2;
    private static final int SEND= 3;
    private static final int RECEIVE = 4;
    private static final int FINISH = 5;

    private int type = 1;
    private String account;

    private View view;
    private ListView lv_order_collection;
    private OrderBuyerAdapter adapter;

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
        account = LoginUtils.getAccount(getContext().getSharedPreferences("User", Context.MODE_PRIVATE));
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
        OrderAsyncTask<List<OrderCollection>> task = new OrderAsyncTask<>(OrderAsyncTask.BUYER_QUERY, new AsyncResponse<List<OrderCollection>>() {
            @Override
            public void onDataReceivedSuccess(FormedData<List<OrderCollection>> formedData) {
                Log.e("OrderFragment", formedData.toString());
                adapter = new OrderBuyerAdapter(getContext(), formedData.getData(), type);
                lv_order_collection.setAdapter(adapter);
            }

            @Override
            public void onDataReceivedFailed() {

            }
        });
        task.execute(account, type+"");
    }

    private void bindView() {
        lv_order_collection = view.findViewById(R.id.lv_order_collection);
    }
}
