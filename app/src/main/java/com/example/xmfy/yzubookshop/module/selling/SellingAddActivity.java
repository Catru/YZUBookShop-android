package com.example.xmfy.yzubookshop.module.selling;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.global.AppConstants;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.model.SellingDto;
import com.example.xmfy.yzubookshop.module.selling.bean.Category1;
import com.example.xmfy.yzubookshop.module.selling.bean.Category2;
import com.example.xmfy.yzubookshop.net.AsyncResponse;
import com.example.xmfy.yzubookshop.net.SellingAsyncTask;
import com.example.xmfy.yzubookshop.utils.CategoryLoader;
import com.example.xmfy.yzubookshop.utils.KeyBoardUtils;
import com.example.xmfy.yzubookshop.utils.LoginUtils;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

public class SellingAddActivity extends AppCompatActivity {

    private List<Category1> cList1 = CategoryLoader.cList1;
    private List<List<Category2>> cList2 = CategoryLoader.cList2;
    private Button btn_back;
    private TextView tv_save;
    private OptionsPickerView pvOptions;
    private PhotoAdapter photoAdapter;
    private RecyclerView rc_selling_photos;

    private EditText et_selling_title, et_selling_author, et_selling_price, et_selling_keywords, et_selling_description;
    private TextView tv_selling_category;

    private ArrayList<String> selectedPhotos;

    private SellingDto sellingDto;

    private int cat1 = -1, cat2 = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling_edit);
        bindView();
        initClickEvents();
    }

    private void bindView() {
        //绑定视图
        btn_back = (Button) findViewById(R.id.toolbar_left_btn);
        tv_save = (TextView) findViewById(R.id.toolbar_right_tv);
        tv_selling_category = (TextView) findViewById(R.id.tv_selling_category);
        rc_selling_photos = (RecyclerView) findViewById(R.id.rc_selling_photos);
        et_selling_title = (EditText) findViewById(R.id.et_selling_title);
        et_selling_author = (EditText) findViewById(R.id.et_selling_author);
        et_selling_price = (EditText) findViewById(R.id.et_selling_price);
        et_selling_keywords = (EditText) findViewById(R.id.et_selling_keywords);
        et_selling_description = (EditText) findViewById(R.id.et_selling_description);

        sellingDto = new SellingDto();
        sellingDto.setAccount(LoginUtils.getAccount(getSharedPreferences("User", MODE_PRIVATE)));

        //初始化自定义类别选择器
        pvOptions = new OptionsPickerView.Builder(SellingAddActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                Category1 c1 = cList1.get(options1);
                Category2 c2 = cList2.get(options1).get(option2);
                cat1 = c1.getId();
                cat2 = c2.getId();
                tv_selling_category.setText(c1.getPickerViewText() + " " + c2.getPickerViewText());
            }
        }).build();
        pvOptions.setPicker(cList1, cList2);

        //初始化图片选择器
        selectedPhotos = new ArrayList<>();
        photoAdapter = new PhotoAdapter(this, selectedPhotos);
        rc_selling_photos.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        rc_selling_photos.setAdapter(photoAdapter);
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

    private void initClickEvents(){
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SellingAddActivity.this);
                builder.setTitle("撤销");
                builder.setMessage("您确定要放弃发布商品吗?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SellingAddActivity.this.finish();
                    }
                });
                builder.show();
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
                                    .start(SellingAddActivity.this);
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(SellingAddActivity.this);
                        }
                    }
                }));

        //类别选择点击事件
        tv_selling_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyBoardUtils.closeKeybord(SellingAddActivity.this, getCurrentFocus());
                pvOptions.show();
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isDataValid()){
                    SellingAsyncTask<Integer> task = new SellingAsyncTask<>();
                    task.setType(SellingAsyncTask.METHOD_ADD);
                    task.setAsyncResponse(new AsyncResponse<Integer>() {
                        @Override
                        public void onDataReceivedSuccess(FormedData<Integer> formedData) {
                            if (formedData.isSuccess()){
                                Toast.makeText(SellingAddActivity.this, "发布新书成功!", Toast.LENGTH_SHORT).show();
                                SellingAddActivity.this.finish();
                            }else
                                Toast.makeText(SellingAddActivity.this, formedData.getError(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onDataReceivedFailed() {
                            Toast.makeText(SellingAddActivity.this, "网络故障, 请检查设置!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    task.execute(getDataSArray());
                }
            }
        });

    }

    //验证数据规则性
    private boolean isDataValid(){
        String str = et_selling_title.getText().toString().trim();
        if (str.length() > 30){
            Toast.makeText(this, "书名长度不能超过30位", Toast.LENGTH_SHORT).show();
            return false;
        }
        sellingDto.setTitle(str);
        str = et_selling_author.getText().toString().trim();
        if (str.length() > 30) {
            Toast.makeText(this, "作者长度不能超过30位", Toast.LENGTH_SHORT).show();
            return false;
        }
        sellingDto.setAuthor(str);
        float price = Float.parseFloat(et_selling_price.getText().toString().trim());
        if (price < 0) {
            Toast.makeText(this, "价格错误", Toast.LENGTH_SHORT).show();
            return false;
        }
        sellingDto.setPrice(price);
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
        sellingDto.setKeywords(str);
        str = et_selling_description.getText().toString().trim();
        if (str.length() > 30) {
            Toast.makeText(this, "描述长度不能超过200字", Toast.LENGTH_SHORT).show();
            return false;
        }
        sellingDto.setDescription(str);
        if (cat1 == -1 || cat2 == -1){
            Toast.makeText(this, "请选择图书种类!", Toast.LENGTH_SHORT).show();
            return false;
        }
        sellingDto.setCategory1(cat1);
        sellingDto.setCategory2(cat2);
        if (selectedPhotos.size() < 3) {
            Toast.makeText(this, "图片不能少于3张!", Toast.LENGTH_SHORT).show();
            return false;
        }
        StringBuilder builder = new StringBuilder();
        for (String url : selectedPhotos){
            if (!url.startsWith("http")){
                String newUrl = AppConstants.SELLING_PHOTO_LOCATION + sellingDto.getAccount() + "/" + url.substring(url.lastIndexOf("/") + 1) + " ";
                builder.append(newUrl);
            } else
                builder.append(url + " ");
        }
        sellingDto.setPhotoUrl(builder.toString());
        return true;
    }


    private String[] getDataSArray(){
        String[] data = new String[selectedPhotos.size() + 9];
        data[0] = sellingDto.getAccount();
        data[1] = sellingDto.getTitle();
        data[2] = sellingDto.getAuthor();
        data[3] = sellingDto.getPrice() + "";
        data[4] = sellingDto.getKeywords();
        data[5] = sellingDto.getCategory1()+"";
        data[6] = sellingDto.getCategory2()+"";
        data[7] = sellingDto.getDescription();
        data[8] = sellingDto.getPhotoUrl();
        for (int i = 0; i < selectedPhotos.size(); i++)
            data[9 + i] = selectedPhotos.get(i);
        return data;
    }
}
