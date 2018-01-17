package com.example.xmfy.yzubookshop.module.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.Delivery;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.net.AsyncResponse;
import com.example.xmfy.yzubookshop.net.DeliveryAsyncTask;
import com.example.xmfy.yzubookshop.utils.LoginUtils;
import com.google.gson.Gson;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;

import java.util.List;

public class DeliveryDetailActivity extends AppCompatActivity{

    private EditText et_delivery_receiver;
    private EditText et_delivery_phone;
    private TextView tv_delivery_region;
    private EditText et_delivery_location;
    private SwitchCompat sc_delivery_default;
    private Button btn_back;
    private TextView tv_save;
    private boolean isEdit = false;
    private Delivery delivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_detail);
        initView();
        initClickEvents();
    }

    private void initView() {
        String deliveryString = getIntent().getStringExtra("delivery");
        if (deliveryString != null){
            isEdit = true;
            delivery = new Gson().fromJson(deliveryString, Delivery.class);
        }
        CityPickerView.getInstance().init(DeliveryDetailActivity.this);
        et_delivery_receiver = (EditText) findViewById(R.id.et_delivery_receiver);
        et_delivery_phone = (EditText) findViewById(R.id.et_delivery_phone);
        tv_delivery_region = (TextView) findViewById(R.id.et_delivery_region);
        et_delivery_location = (EditText) findViewById(R.id.et_delivery_location);
        sc_delivery_default = (SwitchCompat) findViewById(R.id.sc_delivery_default);
        btn_back = (Button) findViewById(R.id.toolbar_left_btn);
        tv_save = (TextView) findViewById(R.id.toolbar_right_tv);
        if (isEdit){
            et_delivery_receiver.setText(delivery.getReceiver());
            et_delivery_phone.setText(delivery.getPhone());
            tv_delivery_region.setText(delivery.getProvince()+" "+delivery.getCity()+" "+delivery.getDistrict());
            et_delivery_location.setText(delivery.getLocation());
            sc_delivery_default.setChecked(delivery.getDefaults()==1);
        }
    }

    private void initClickEvents() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeliveryDetailActivity.this.finish();
            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String receiver = et_delivery_receiver.getText().toString().trim();
                String phone = et_delivery_phone.getText().toString().trim();
                String[] region = tv_delivery_region.getText().toString().split(" ");
                String location = et_delivery_location.getText().toString().trim();
                Boolean b = sc_delivery_default.isChecked();
                if (receiver.length() == 0){
                    Toast.makeText(DeliveryDetailActivity.this, "请输入收货人姓名!", Toast.LENGTH_SHORT).show();
                }else if(phone.length()!=11){
                    Toast.makeText(DeliveryDetailActivity.this, "手机格式错误!", Toast.LENGTH_SHORT).show();
                }else if(region.length != 3){
                    Toast.makeText(DeliveryDetailActivity.this, "请选择地区!", Toast.LENGTH_SHORT).show();
                }else if(location.length()<5){
                    Toast.makeText(DeliveryDetailActivity.this, "详细地址过短!", Toast.LENGTH_SHORT).show();
                }else{
                    DeliveryAsyncTask task = new DeliveryAsyncTask();
                    task.setAsyncResponse(isEdit?DeliveryAsyncTask.METHOD_UPDATE:DeliveryAsyncTask.METHOD_ADD, new AsyncResponse<List<Delivery>>() {
                        @Override
                        public void onDataReceivedSuccess(FormedData<List<Delivery>> formedData) {
                            if (formedData.isSuccess()){
                                DeliveryDetailActivity.this.finish();
                            }else {
                                Toast.makeText(DeliveryDetailActivity.this, "数据库繁忙,请稍后重试!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onDataReceivedFailed() {
                            Toast.makeText(DeliveryDetailActivity.this, "数据库繁忙,请稍后重试!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    if (isEdit)
                        task.execute(delivery.getId()+"",delivery.getAccount(), receiver, phone, region[0], region[1], region[2], location, (b?1:0) +"");
                    else{
                        task.execute(LoginUtils.getAccount(getSharedPreferences("User", MODE_PRIVATE)),
                                receiver, phone, region[0], region[1], region[2], location, (b?1:0) +"");
                    }
                }

            }
        });
        tv_delivery_region.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityConfig cityConfig = new CityConfig.Builder(DeliveryDetailActivity.this)
                        .setCustomItemLayout(R.layout.item_city)
                        .setCustomItemTextViewId(R.id.item_city_name_tv)
                        .provinceCyclic(false)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .build();
                CityPickerView.getInstance().setConfig(cityConfig);
                CityPickerView.getInstance().setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        if (district != null){
                            tv_delivery_region.setText(province.toString()+" "+city.toString()+" "+district.toString());
                        }else if(city != null){
                            tv_delivery_region.setText(province.toString()+" "+city.toString());
                        }else {
                            tv_delivery_region.setText(province.toString());
                        }
                    }
                });
                CityPickerView.getInstance().showCityPicker(DeliveryDetailActivity.this);
            }
        });
    }

}
