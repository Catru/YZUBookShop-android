package com.example.xmfy.yzubookshop.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.module.Login.LoginActivity;

/**
 * Created by xmfy on 2018/1/3.
 */
public class MineFragment extends Fragment {

    private View view;
    private TextView tv_login;
    private LinearLayout ll_personal_infor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView();
        return view;
    }

    private void initView() {
        ll_personal_infor = view.findViewById(R.id.ll_personal_infor);
        tv_login = view.findViewById(R.id.toolbar_right_tv);
        tv_login.setOnClickListener(new LoginClickListener());
    }

    private class LoginClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }
}
