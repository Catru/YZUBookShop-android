package com.example.xmfy.yzubookshop.module.login;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.global.AppConstants;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.model.User;
import com.example.xmfy.yzubookshop.net.AsyncResponse;
import com.example.xmfy.yzubookshop.utils.LoginUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_back;
    private EditText et_account;
    private EditText et_pwd;
    private EditText et_pwd2;
    private EditText et_username;
    private RadioButton rb_male;
    private EditText et_phone;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        btn_back = (Button) findViewById(R.id.toolbar_left_btn);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });
        et_account = (EditText) findViewById(R.id.et_reg_account);
        et_pwd = (EditText) findViewById(R.id.et_reg_pwd);
        et_pwd2 = (EditText) findViewById(R.id.et_reg_pwd2);
        et_username = (EditText) findViewById(R.id.et_reg_username);
        rb_male = (RadioButton) findViewById(R.id.rb_reg_male);
        et_phone = (EditText) findViewById(R.id.et_reg_phone);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String pwd = et_pwd.getText().toString().trim();
        String pwd2 = et_pwd2.getText().toString().trim();
        if (!pwd.equals(pwd2)){
            Toast.makeText(RegisterActivity.this, "注册失败, 密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        String account = et_account.getText().toString().trim();
        String username = et_username.getText().toString().trim();
        String gender = rb_male.isChecked()? "男" : "女";
        String phone = et_phone.getText().toString().trim();
        final User user = new User(account, pwd, username, gender, phone, "");
        RegisterAsyncTask task = new RegisterAsyncTask();
        task.execute(user);
        task.setRegisterResponse(new RegisterResponse() {
            @Override
            public void onDataReceivedSuccess(Integer result, User usr) {
                if (result == 0)
                    Toast.makeText(RegisterActivity.this, "注册失败,请稍后重试", Toast.LENGTH_SHORT).show();
                else{
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    LoginUtils.saveUser(getSharedPreferences("User", MODE_PRIVATE), usr);
                    RegisterActivity.this.finish();
                }
            }
        });
    }


    private class RegisterAsyncTask extends AsyncTask<User, Void, Integer>{

        public RegisterResponse registerResponse;
        public User user;

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            registerResponse.onDataReceivedSuccess(integer, user);
        }

        public void setRegisterResponse(RegisterResponse registerResponse) {
            this.registerResponse = registerResponse;
        }

        @Override
        protected Integer doInBackground(User... users) {
            user = users[0];
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("account", user.getAccount())
                    .add("pwd", user.getPwd())
                    .add("username", user.getUsername())
                    .add("gender", user.getGender())
                    .add("phone", user.getPhone())
                    .add("headshot", user.getHeadshot())
                    .build();
            Request request = new Request.Builder()
                    .url(AppConstants.REGISTER_ADDRESS)
                    .post(body)
                    .build();
            Response response;
            try {
                response = client.newCall(request).execute();
                return Integer.parseInt(response.body().string());
            } catch (IOException e) {
                Toast.makeText(RegisterActivity.this, "注册失败,请稍后重试", Toast.LENGTH_SHORT).show();
                return 0;
            }

        }
    }

    private interface RegisterResponse{
        void onDataReceivedSuccess(Integer integer, User user);
    }
    
}
