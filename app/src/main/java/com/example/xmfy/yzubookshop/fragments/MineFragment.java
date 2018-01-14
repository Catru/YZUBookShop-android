package com.example.xmfy.yzubookshop.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.module.login.LoginActivity;
import com.example.xmfy.yzubookshop.utils.LoginUtils;

/**
 * Created by xmfy on 2018/1/3.
 */
public class MineFragment extends Fragment implements View.OnClickListener{

    private View view;
    private TextView tv_login;
    private TextView tv_login_infor;
    private SharedPreferences preference;
    private Boolean isLogined = false;
    private LinearLayout ll_personal_infor;
    private LinearLayout ll_mycollection;
    private LinearLayout ll_receive_address;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView();
        initLoginInfor();
        return view;
    }

    @Override
    public void onResume() {
        isLogined = LoginUtils.isLogined(preference);
        initLoginInfor();
        super.onResume();
    }

    private void initLoginInfor() {
        if(isLogined){
            String username = LoginUtils.getUsername(preference);
            tv_login_infor.setText("尊敬的"+username+", 您好!");
            tv_login.setText("注销");
            tv_login.setOnClickListener(new LogoutListener());
        }else{
            tv_login_infor.setText("您尚未登录");
            tv_login.setText("登录");
            tv_login.setOnClickListener(new LoginClickListener());
        }
    }

    private void initView() {
        tv_login = view.findViewById(R.id.toolbar_right_tv);
        tv_login_infor = view.findViewById(R.id.tv_login_infor);
        preference = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        isLogined = LoginUtils.isLogined(preference);
        ll_personal_infor = view.findViewById(R.id.ll_personal_infor);
        ll_mycollection = view.findViewById(R.id.ll_mycollection);
        ll_receive_address = view.findViewById(R.id.ll_receive_address);
        ll_personal_infor.setOnClickListener(this);
        ll_mycollection.setOnClickListener(this);
        ll_receive_address.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (!isLogined){
            Toast.makeText(getContext(), "请先登录!", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()){
            case R.id.ll_personal_infor:
                Toast.makeText(getContext(), "个人资料", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_mycollection:
                break;
            case R.id.ll_receive_address:
                break;
        }
    }

    private class LoginClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    private class LogoutListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("注销页");
            builder.setMessage("您确定要注销账号吗?");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    LoginUtils.clearUser(getContext().getSharedPreferences("User", Context.MODE_PRIVATE));
                    isLogined = false;
                    initLoginInfor();
                }
            });
            builder.show();
        }
    }
}
