package com.example.xmfy.yzubookshop.module.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.xmfy.yzubookshop.R;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences preference;
    private Button btn_back;
    private TextView tv_account;
    private TextView tv_username;
    private TextView tv_gender;
    private TextView tv_phone;
    private TableRow row_username;
    private TableRow row_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        loadInfo();
        initClickEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadInfo();
    }

    private void initClickEvents() {
        btn_back.setOnClickListener(this);
        row_username.setOnClickListener(this);
        row_phone.setOnClickListener(this);
    }

    private void initView() {
        preference = getSharedPreferences("User", MODE_PRIVATE);
        btn_back = (Button) findViewById(R.id.toolbar_left_btn);
        tv_account = (TextView) findViewById(R.id.infor_account);
        tv_username = (TextView) findViewById(R.id.infor_username);
        tv_gender = (TextView) findViewById(R.id.infor_gender);
        tv_phone = (TextView) findViewById(R.id.infor_phone);
        row_username = (TableRow) findViewById(R.id.row_username);
        row_phone = (TableRow) findViewById(R.id.row_phone);
    }

    private void loadInfo() {
        tv_account.setText(preference.getString("account", "账户"));
        tv_username.setText(preference.getString("username", "用户名"));
        tv_gender.setText(preference.getString("gender", "男"));
        tv_phone.setText(preference.getString("phone", "手机"));
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.toolbar_left_btn){
            UserInfoActivity.this.finish();
        }else{
            Intent intent = new Intent(UserInfoActivity.this, InforEditActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("account", tv_account.getText().toString());
            if (view.getId() == R.id.row_username){
                bundle.putString("key_CN", "用户名");
                bundle.putString("key", "username");
                bundle.putString("value", tv_username.getText().toString());
            }else if(view.getId() == R.id.row_phone){
                bundle.putString("key_CN", "手机");
                bundle.putString("key", "phone");
                bundle.putString("value", tv_phone.getText().toString());
            }
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }
}
