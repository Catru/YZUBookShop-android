package com.example.xmfy.yzubookshop.module.order;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.xmfy.yzubookshop.R;

public class OrderActivity extends AppCompatActivity {

    private TabLayout tab_order;
    private ViewPager vp_order;
    private Button btn_back;
    private String[] titles = {"待确认", "代付款", "待收货", "待回复", "已关闭"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        bindView();
        loadFragments();
    }

    private void loadFragments() {
        OrderFragmentPagerAdapter adapter = new OrderFragmentPagerAdapter(getSupportFragmentManager(), titles);
        vp_order.setAdapter(adapter);
        tab_order.setupWithViewPager(vp_order);
    }

    private void bindView() {
        tab_order = (TabLayout) findViewById(R.id.tab_order);
        vp_order = (ViewPager) findViewById(R.id.vp_order);
        btn_back = (Button) findViewById(R.id.toolbar_left_btn);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderActivity.this.finish();
            }
        });
    }
}
