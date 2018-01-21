package com.example.xmfy.yzubookshop.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.Selling;
import com.example.xmfy.yzubookshop.module.selling.SellingAdapter;
import com.example.xmfy.yzubookshop.module.selling.SellingEditActivity;
import com.example.xmfy.yzubookshop.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xmfy on 2018/1/3.
 */
public class SellFragment extends Fragment {
    private View view;
    private SwipeMenuListView lv_selling;
    private List<Selling> sList;
    private SellingAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sell, container, false);
        bindView();
        loadData();
        loadContent();
        initClickEvents();
        return view;
    }

    private void bindView() {
        lv_selling = view.findViewById(R.id.lv_selling);
        sList = new ArrayList<>();
    }

    private void loadContent(){
        adapter = new SellingAdapter(sList, getContext());
        lv_selling.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem editItem = new SwipeMenuItem(getContext());
                editItem.setBackground(R.color.icon_selling_edit);
                editItem.setWidth(CommonUtils.dip2px(getContext(), 90));
                editItem.setIcon(R.mipmap.icon_selling_edit);
                menu.addMenuItem(editItem);
                SwipeMenuItem deleteItem = new SwipeMenuItem(getContext());
                deleteItem.setBackground(R.color.icon_selling_delete);
                deleteItem.setWidth(CommonUtils.dip2px(getContext(), 90));
                deleteItem.setIcon(R.mipmap.icon_selling_delete);
                menu.addMenuItem(deleteItem);
            }
        };
        lv_selling.setMenuCreator(creator);
    }


    private void initClickEvents() {
        lv_selling.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                startActivity(new Intent(getActivity(), SellingEditActivity.class));
                return false;
            }
        });
    }


    private void loadData(){
        sList.add(new Selling(1, "18751103565", "解忧杂货店(此商品不参与榜单活动)","(日)东野圭吾", 27.30f, "这是一本书", "keywords", "http://192.168.1.100:8080/resources/selling/18751103565/cover1.jpg http://192.168.1.100:8080/resources/selling/18751103565/cover2.jpg",1,11,null,1,"promotion",132,132));
        sList.add(new Selling(2, "18751103565", "解忧杂货店(此商品不参与榜单活动)","(日)东野圭吾", 27.30f, "这是一本书", "keywords", "http://192.168.1.100:8080/resources/selling/18751103565/cover2.jpg",1,11,null,1,"promotion",132,132));
        sList.add(new Selling(3, "18751103565", "解忧杂货店(此商品不参与榜单活动)","(日)东野圭吾", 27.30f, "这是一本书","keywords", "http://192.168.1.100:8080/resources/selling/18751103565/cover3.jpg",1,11,null,1,"promotion",132,132));
        sList.add(new Selling(4, "18751103565", "解忧杂货店(此商品不参与榜单活动)","(日)东野圭吾", 27.30f, "这是一本书","keywords", "http://192.168.1.100:8080/resources/selling/18751103565/cover4.jpg",1,11,null,1,"promotion",132,132));
        sList.add(new Selling(5, "18751103565", "解忧杂货店(此商品不参与榜单活动)","(日)东野圭吾", 27.30f, "这是一本书","keywords", "http://192.168.1.100:8080/resources/selling/18751103565/cover1.jpg",1,11,null,1,"promotion",132,132));
        sList.add(new Selling(6, "18751103565", "解忧杂货店(此商品不参与榜单活动)","(日)东野圭吾", 27.30f, "这是一本书","keywords", "http://192.168.1.100:8080/resources/selling/18751103565/cover1.jpg",1,11,null,1,"promotion",132,132));
        sList.add(new Selling(7, "18751103565", "解忧杂货店(此商品不参与榜单活动)","(日)东野圭吾", 27.30f, "这是一本书","keywords", "http://192.168.1.100:8080/resources/selling/18751103565/cover1.jpg",1,11,null,1,"promotion",132,132));
    }
}
