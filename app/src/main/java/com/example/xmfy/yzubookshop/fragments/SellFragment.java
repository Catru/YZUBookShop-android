package com.example.xmfy.yzubookshop.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.model.Selling;
import com.example.xmfy.yzubookshop.module.selling.SellingAdapter;
import com.example.xmfy.yzubookshop.module.selling.SellingAddActivity;
import com.example.xmfy.yzubookshop.module.selling.SellingEditActivity;
import com.example.xmfy.yzubookshop.net.AsyncResponse;
import com.example.xmfy.yzubookshop.net.SellingAsyncTask;
import com.example.xmfy.yzubookshop.utils.CommonUtils;
import com.example.xmfy.yzubookshop.utils.LoginUtils;
import com.google.gson.Gson;

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
    private SharedPreferences preferences;
    private TextView tv_add;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sell, container, false);
        bindView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (LoginUtils.isLogined(preferences)){
            loadData();
            loadContent();
            initClickEvents();
        }
    }

    private void bindView() {
        lv_selling = view.findViewById(R.id.lv_selling);
        tv_add = view.findViewById(R.id.toolbar_right_tv);
        sList = new ArrayList<>();
        preferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
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
                if (index == 0){
                    Intent intent = new Intent(getActivity(), SellingEditActivity.class);
                    intent.putExtra("selling", new Gson().toJson(sList.get(position)));
                    startActivity(intent);
                }else if (index ==1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("删除书本");
                    builder.setMessage("您确定要删除这本书籍吗? 删除后记录不可恢复!");
                    builder.setPositiveButton("确定", new deleteClickListener(position));
                    builder.show();
                }
                return false;
            }
        });

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SellingAddActivity.class));
            }
        });
    }


    private void loadData(){
        SellingAsyncTask<List<Selling>> task = new SellingAsyncTask<>();
        task.setType(SellingAsyncTask.METHOD_QUERY);
        task.setAsyncResponse(new AsyncResponse<List<Selling>>() {
            @Override
            public void onDataReceivedSuccess(FormedData<List<Selling>> formedData) {
                if (formedData.isSuccess()){
                    sList = formedData.getData();
                    adapter.update(sList);
                }else {
                    Toast.makeText(getContext(), formedData.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDataReceivedFailed() {
                Toast.makeText(getContext(), "获取出售书籍列表失败,请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
        task.execute(LoginUtils.getAccount(preferences));
    }

    private class deleteClickListener implements DialogInterface.OnClickListener {
        private int position;

        deleteClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            SellingAsyncTask<Integer> task = new SellingAsyncTask<>();
            task.setType(SellingAsyncTask.METHOD_DELETE);
            task.setAsyncResponse(new AsyncResponse<Integer>() {
                @Override
                public void onDataReceivedSuccess(FormedData<Integer> formedData) {
                    if (formedData.isSuccess()){
                        Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                        onResume();
                    }else
                        Toast.makeText(getContext(), formedData.getError(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onDataReceivedFailed() {
                    Toast.makeText(getContext(), "网络繁忙, 请检查相关设置", Toast.LENGTH_SHORT).show();
                }
            });
            task.execute(sList.get(position).getId()+"");
        }
    }
}
