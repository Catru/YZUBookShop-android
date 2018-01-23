package com.example.xmfy.yzubookshop.module.selling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.global.AppConstants;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.model.Selling;
import com.example.xmfy.yzubookshop.module.selling.bean.Category1;
import com.example.xmfy.yzubookshop.module.selling.bean.Category2;
import com.example.xmfy.yzubookshop.net.AsyncResponse;
import com.example.xmfy.yzubookshop.net.SellingAsyncTask;
import com.example.xmfy.yzubookshop.utils.CategoryLoader;
import com.example.xmfy.yzubookshop.utils.KeyBoardUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

public class SellingEditActivity extends AppCompatActivity {
    private Selling bindSelling;
    private List<Category1> cList1 = CategoryLoader.cList1;
    private List<List<Category2>> cList2 = CategoryLoader.cList2;
    private Button btn_back;
    private TextView tv_save;
    private RecyclerView rc_selling_photos;
    private PhotoAdapter photoAdapter;
    private OptionsPickerView pvOptions;

    private EditText et_selling_title, et_selling_author, et_selling_price, et_selling_keywords, et_selling_description;
    private TextView tv_selling_category;

    private ArrayList<String> selectedPhotos;
    private ArrayList<String> originalPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling_edit);
        bindView();
        loadContent();
        initClickEvents();
    }

    private void bindView() {
        //绑定视图
        btn_back = (Button) findViewById(R.id.toolbar_left_btn);
        tv_save = (TextView) findViewById(R.id.toolbar_right_tv);
        rc_selling_photos = (RecyclerView) findViewById(R.id.rc_selling_photos);
        tv_selling_category = (TextView) findViewById(R.id.tv_selling_category);
        et_selling_title = (EditText) findViewById(R.id.et_selling_title);
        et_selling_author = (EditText) findViewById(R.id.et_selling_author);
        et_selling_price = (EditText) findViewById(R.id.et_selling_price);
        et_selling_keywords = (EditText) findViewById(R.id.et_selling_keywords);
        et_selling_description = (EditText) findViewById(R.id.et_selling_description);

        //初始化自定义类别选择器
        pvOptions = new OptionsPickerView.Builder(SellingEditActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                Category1 c1 = cList1.get(options1);
                Category2 c2 = cList2.get(options1).get(option2);
                bindSelling.setCategory1(c1.getId());
                bindSelling.setCategory2(c2.getId());
                tv_selling_category.setText(c1.getPickerViewText() + " " + c2.getPickerViewText());
            }
        }).build();
        pvOptions.setPicker(cList1, cList2);
    }

    private void loadContent() {
        //绑定书本信息,设为全局变量
        Gson gson = new Gson();
        bindSelling = gson.fromJson(getIntent().getStringExtra("selling"), Selling.class);

        //初始化文本信息
        et_selling_title.setText(bindSelling.getTitle());
        et_selling_author.setText(bindSelling.getAuthor());
        et_selling_price.setText(bindSelling.getPrice() + "");
        et_selling_keywords.setText(bindSelling.getKeywords());
        et_selling_description.setText(bindSelling.getDescription());

        //初始化类别
        HashMap map = CategoryLoader.getCategoryNameById(bindSelling.getCategory1(), bindSelling.getCategory2());
        tv_selling_category.setText(map.get(1) + " " + map.get(2));

        //初始化图片
        selectedPhotos = new ArrayList<>(Arrays.asList(bindSelling.getPhotoUrl().split(" ")));
        photoAdapter = new PhotoAdapter(this, selectedPhotos);
        rc_selling_photos.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        rc_selling_photos.setAdapter(photoAdapter);

        //复制一份图片地址,检查用
        originalPhotos = new ArrayList<>();
        for (String url : selectedPhotos){
            originalPhotos.add(url);
        }
    }

    private void initClickEvents() {
        //返回按钮
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SellingEditActivity.this.finish();
            }
        });

        //保存按钮
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAndUpdateSelling()) {
                    if (needUploadPhoto())
                        updateSellingPhoto();
                    else {
                        StringBuilder builder = new StringBuilder();
                        for(String url : selectedPhotos.toArray(new String[selectedPhotos.size()])){
                            builder.append(url + " ");
                        }
                        bindSelling.setPhotoUrl(builder.toString());
                        updateSelling();
                    }
                }
            }
        });

        //图片点击事件
        rc_selling_photos.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                            PhotoPicker.builder()
                                    .setPhotoCount(PhotoAdapter.MAX)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(false)
                                    .setSelected(selectedPhotos)
                                    .start(SellingEditActivity.this);
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(SellingEditActivity.this);
                        }
                    }
                }));

        //类别选择点击事件
        tv_selling_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyBoardUtils.closeKeybord(SellingEditActivity.this, getCurrentFocus());
                pvOptions.show();
            }
        });
    }

    //返回数据并加载图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null) {
                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();
        }
    }

    //验证输入规则性
    private Boolean checkAndUpdateSelling() {
        String str = et_selling_title.getText().toString().trim();
        if (str.length() > 30) {
            Toast.makeText(this, "书名长度不能超过30位", Toast.LENGTH_SHORT).show();
            return false;
        }
        bindSelling.setTitle(str);
        str = et_selling_author.getText().toString().trim();
        if (str.length() > 30) {
            Toast.makeText(this, "作者长度不能超过30位", Toast.LENGTH_SHORT).show();
            return false;
        }
        bindSelling.setAuthor(str);
        float price = Float.parseFloat(et_selling_price.getText().toString().trim());
        if (price < 0) {
            Toast.makeText(this, "价格错误", Toast.LENGTH_SHORT).show();
            return false;
        }
        bindSelling.setPrice(price);
        str = et_selling_keywords.getText().toString().trim();
        String[] keywords = str.split(" ");
        if (keywords.length > 4) {
            Toast.makeText(this, "关键字不多于4组", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (str.length() > 50) {
            Toast.makeText(this, "关键字总长度不能超过30位", Toast.LENGTH_SHORT).show();
            return false;
        }
        bindSelling.setKeywords(str);
        str = et_selling_description.getText().toString().trim();
        if (str.length() > 30) {
            Toast.makeText(this, "描述长度不能超过200字", Toast.LENGTH_SHORT).show();
            return false;
        }
        bindSelling.setDescription(str);
        if (selectedPhotos.size() < 3) {
            Toast.makeText(this, "图片不能少于3张!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //上传图片成功后更新photoUrl, 最后更新整条记录
    private void updateSelling() {
        SellingAsyncTask<List<Selling>> task = new SellingAsyncTask<>();
        task.setType(SellingAsyncTask.METHOD_UPDATE);
        task.setAsyncResponse(new AsyncResponse<List<Selling>>() {
            @Override
            public void onDataReceivedSuccess(FormedData<List<Selling>> formedData) {
                if (formedData.isSuccess()) {
                    Toast.makeText(SellingEditActivity.this, "更新成功!", Toast.LENGTH_SHORT).show();
                    SellingEditActivity.this.finish();
                } else {
                    Toast.makeText(SellingEditActivity.this, formedData.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDataReceivedFailed() {
                Toast.makeText(SellingEditActivity.this, "更新失败!请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
        task.execute(bindSelling.getId() + "", bindSelling.getAccount(), bindSelling.getTitle(), bindSelling.getAuthor(), bindSelling.getPrice() + "", bindSelling.getKeywords(), bindSelling.getCategory1() + "", bindSelling.getCategory2() + "", bindSelling.getDescription(), bindSelling.getPhotoUrl());
    }

    //上传图片
    private void updateSellingPhoto() {
        List<String> sList = new ArrayList<>();
        sList.add(bindSelling.getAccount());
        for (String photoUrl : selectedPhotos) {
            if (!photoUrl.startsWith("http")) {
                sList.add(photoUrl);
            }
        }
        String[] photoArray = sList.toArray(new String[sList.size()]);
        SellingAsyncTask<Integer> task = new SellingAsyncTask<>();
        task.setType(SellingAsyncTask.METHOD_UPLOAD_PHOTOS);
        task.setAsyncResponse(new AsyncResponse<Integer>() {
            @Override
            public void onDataReceivedSuccess(FormedData<Integer> formedData) {
                if (formedData.isSuccess()) {
                    Toast.makeText(SellingEditActivity.this, "上传图片成功!", Toast.LENGTH_SHORT).show();
                    //修改photoUrl
                    updatePhotoUrl();
                    updateSelling();
                } else {
                    Toast.makeText(SellingEditActivity.this, formedData.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDataReceivedFailed() {
                Toast.makeText(SellingEditActivity.this, "上传图片失败!请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
        task.execute(photoArray);
    }

    //更新图片地址
    private void updatePhotoUrl(){
        StringBuilder builder = new StringBuilder();
        for (String url : selectedPhotos){
            if (!url.startsWith("http")){
                String newUrl = AppConstants.SELLING_PHOTO_LOCATION + bindSelling.getAccount() + "/" + url.substring(url.lastIndexOf("/")) + " ";
                builder.append(newUrl);
            } else
                builder.append(url + " ");
        }
        Log.e("photo", builder.toString());
        bindSelling.setPhotoUrl(builder.toString());
    }

    private boolean needUploadPhoto(){
        for (String url : selectedPhotos){
            if (!url.startsWith("http"))
                return true;
        }
        return false;
    }
}
