package com.example.xmfy.yzubookshop.module.buy.searchTab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.CartCollection;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.module.buy.CartCollectionAdapter;
import com.example.xmfy.yzubookshop.net.AsyncResponse;
import com.example.xmfy.yzubookshop.net.CartAsyncTask;
import com.example.xmfy.yzubookshop.utils.LoginUtils;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ListView lv_cartList;

    private List<CartCollection> cList;

    CartCollectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        bindView();
        loadData();

    }

    private void bindView(){
        lv_cartList = (ListView) findViewById(R.id.lv_cartList);
    }

    private void loadData(){
        cList = new ArrayList<>();
        adapter = new CartCollectionAdapter(CartActivity.this, cList);
        lv_cartList.setAdapter(adapter);
        CartAsyncTask<List<CartCollection>> task = new CartAsyncTask<>(CartAsyncTask.TYPE_QUERY, new AsyncResponse<List<CartCollection>>() {
            @Override
            public void onDataReceivedSuccess(FormedData<List<CartCollection>> formedData) {
                if (formedData.isSuccess()){
                    cList = formedData.getData();
                    adapter.update(cList);
                }else {
                    Toast.makeText(CartActivity.this, formedData.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDataReceivedFailed() {

            }
        });
        task.execute(LoginUtils.getAccount(getSharedPreferences("User", MODE_PRIVATE)));
    }

}
