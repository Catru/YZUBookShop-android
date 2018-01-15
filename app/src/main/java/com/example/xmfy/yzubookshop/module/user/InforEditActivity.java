package com.example.xmfy.yzubookshop.module.user;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.net.AsyncResponse;
import com.example.xmfy.yzubookshop.net.InfoEditAsyncTask;

public class InforEditActivity extends AppCompatActivity implements View.OnClickListener{

    private Bundle bundle;
    private Button btn_back;
    private TextView tv_save;
    private TextView tv_edit_key;
    private EditText et_edit_value;
    private String account;
    private String key_CN;
    private String key;
    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_edit);
        initView();
        loadContent();
        initClickEvents();
    }

    private void initClickEvents() {
        btn_back.setOnClickListener(this);
        tv_save.setOnClickListener(this);
    }

    private void loadContent() {
        bundle = getIntent().getExtras();
        account = bundle.getString("account");
        key_CN = bundle.getString("key_CN");
        key = bundle.getString("key");
        value = bundle.getString("value");
        tv_edit_key.setText(key_CN);
        et_edit_value.setText(value);
        et_edit_value.setSelection(value.length());
    }

    private void initView() {
        btn_back = (Button) findViewById(R.id.toolbar_left_btn);
        tv_save = (TextView) findViewById(R.id.toolbar_right_tv);
        tv_edit_key = (TextView) findViewById(R.id.infor_edit);
        et_edit_value = (EditText) findViewById(R.id.infor_edit_content);
    }

    private boolean checkContent(){
        String v = et_edit_value.getText().toString().trim();
        if (v.length() == 0){
            Toast.makeText(this, key_CN + "不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (key.equals("username") && v.length() > 30){
            Toast.makeText(this, "用户名过长!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (key.equals("phone") && v.length() != 11){
            Toast.makeText(this, "手机格式错误!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.toolbar_left_btn)
            InforEditActivity.this.finish();
        else if(view.getId() == R.id.toolbar_right_tv){
            if (checkContent()){
                InfoEditAsyncTask task = new InfoEditAsyncTask();
                task.setAsyncResponse(new AsyncResponse() {
                    @Override
                    public void onDataReceivedSuccess(FormedData formedData) {
                        if (formedData.isSuccess()){
                            SharedPreferences preference = getSharedPreferences("User", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preference.edit();
                            editor.putString(key, et_edit_value.getText().toString().trim());
                            editor.apply();
                            Toast.makeText(InforEditActivity.this, key_CN+" 修改成功!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(InforEditActivity.this, key_CN+" 修改失败!" + formedData.getError(), Toast.LENGTH_SHORT).show();
                        }
                        InforEditActivity.this.finish();
                    }

                    @Override
                    public void onDataReceivedFailed() {
                        Toast.makeText(InforEditActivity.this, "网络延迟!", Toast.LENGTH_SHORT).show();
                    }
                });
                task.execute(key, et_edit_value.getText().toString().trim(), account);
            }
        }
    }
}
