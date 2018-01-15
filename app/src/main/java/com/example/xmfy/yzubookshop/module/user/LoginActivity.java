package com.example.xmfy.yzubookshop.module.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.model.User;
import com.example.xmfy.yzubookshop.net.AsyncResponse;
import com.example.xmfy.yzubookshop.net.LoginAsyncTask;
import com.example.xmfy.yzubookshop.utils.LoginUtils;

public class LoginActivity extends AppCompatActivity {

    private Button btn_back;
    private Button btn_login;
    private EditText et_account;
    private EditText et_pwd;
    private TextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        btn_back = (Button) findViewById(R.id.toolbar_left_btn);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_account = (EditText) findViewById(R.id.et_account);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        tv_register = (TextView) findViewById(R.id.tv_register);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.finish();
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.finish();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = et_account.getText().toString().trim();
                String pwd = et_pwd.getText().toString().trim();
                if (account.length()==0 || pwd.length()==0){
                    Toast.makeText(LoginActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                LoginAsyncTask task = new LoginAsyncTask();
                task.setOnAsyncResponse(new AsyncResponse<User>() {
                    @Override
                    public void onDataReceivedSuccess(FormedData<User> formedData) {
                        if (!formedData.isSuccess())
                            Toast.makeText(LoginActivity.this, formedData.getError(), Toast.LENGTH_SHORT).show();
                        else{
                            User usr = formedData.getData();
                            Toast.makeText(LoginActivity.this, "欢迎用户: "+usr.getUsername(), Toast.LENGTH_SHORT).show();
                            LoginUtils.saveUser(getSharedPreferences("User", MODE_PRIVATE), usr);
                            LoginActivity.this.finish();
                        }
                    }

                    @Override
                    public void onDataReceivedFailed() {
                        Toast.makeText(LoginActivity.this, "网络延迟", Toast.LENGTH_SHORT).show();
                    }
                });
                task.execute(account, pwd);
            }
        });
    }


}
