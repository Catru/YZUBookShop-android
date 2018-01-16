package com.example.xmfy.yzubookshop.module.user;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.Delivery;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.net.AsyncResponse;
import com.example.xmfy.yzubookshop.net.DeliveryAsyncTask;
import com.example.xmfy.yzubookshop.utils.LoginUtils;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity{
    private Button btn_back;
    private Button btn_delivery_add;
    private RecyclerView rv_delivery;
    private RecyclerView.LayoutManager rv_layout_manager;
    private DeliveryAdapter adapter;
    private List<Delivery> deliveryList;
    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        initView();
    }

    private void initView() {
        account = LoginUtils.getAccount(getSharedPreferences("User", MODE_PRIVATE));
        btn_back = (Button) findViewById(R.id.toolbar_left_btn);
        rv_delivery = (RecyclerView) findViewById(R.id.rv_delivery);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeliveryActivity.this.finish();
            }
        });
        rv_layout_manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.custom_rc_divider2));
        rv_delivery.addItemDecoration(divider);
        rv_delivery.setLayoutManager(rv_layout_manager);
        adapter = new DeliveryAdapter(DeliveryActivity.this, new ArrayList<Delivery>());
        adapter.setLoadFinishedListener(new DeliveryAdapter.LoadFinishedListener() {
            @Override
            public void onLoadFinished() {
                initClickEvents();
            }
        });
        rv_delivery.setAdapter(adapter);
        loadContent();
    }

    private void loadContent(){
        deliveryList = new ArrayList<>();
        DeliveryAsyncTask task = new DeliveryAsyncTask();
        task.setAsyncResponse(DeliveryAsyncTask.METHOD_QUERY, new AsyncResponse<List<Delivery>>() {
            @Override
            public void onDataReceivedSuccess(FormedData<List<Delivery>> formedData) {
                if (formedData.isSuccess()){
                    deliveryList = formedData.getData();
                    adapter.updateData(deliveryList);
                }else{
                    Toast.makeText(DeliveryActivity.this, formedData.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDataReceivedFailed() {
                Toast.makeText(DeliveryActivity.this, "数据库繁忙,请稍后重试!", Toast.LENGTH_SHORT).show();
            }
        });
        task.execute(account);
    }

    private void initClickEvents() {
        for(RadioButton rb :adapter.radioButtonList){
            rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton btn, boolean b) {
                    if (b){
                        setDefaultLocation((int) btn.getTag(R.id.delivery_id), (String) btn.getTag(R.id.delivery_account));
                    }
                }
            });
        }
    }

    private void setDefaultLocation(int id, String account) {
        DeliveryAsyncTask task = new DeliveryAsyncTask();
        task.setAsyncResponse(DeliveryAsyncTask.METHOD_SET_DEFAULT_LOCATION, new AsyncResponse<List<Delivery>>() {
            @Override
            public void onDataReceivedSuccess(FormedData<List<Delivery>> formedData) {
                if (formedData.isSuccess()){
                    deliveryList = formedData.getData();
                    adapter.updateData(deliveryList);
                }else{
                    Toast.makeText(DeliveryActivity.this, formedData.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDataReceivedFailed() {
                Toast.makeText(DeliveryActivity.this, "数据库繁忙,请稍后重试!", Toast.LENGTH_SHORT).show();
            }
        });
        task.execute(id+"" , account);
    }
}
