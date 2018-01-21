package com.example.xmfy.yzubookshop.module.selling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.global.MyApp;
import com.example.xmfy.yzubookshop.module.selling.bean.Category1;
import com.example.xmfy.yzubookshop.module.selling.bean.Category2;
import com.example.xmfy.yzubookshop.utils.KeyBoardUtils;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

public class SellingEditActivity extends AppCompatActivity {
    private List<Category1> cList1 = MyApp.cList1;
    private List<List<Category2>> cList2 = MyApp.cList2;
    private Button btn_back;
    private RecyclerView rc_selling_photos;
    private PhotoAdapter photoAdapter;
    private OptionsPickerView pvOptions;

    private TextView tv_selling_category;

    private ArrayList<String> selectedPhotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling_edit);
        bindView();
        loadContent();
        initClickEvents();
    }

    private void bindView() {
        btn_back = (Button) findViewById(R.id.toolbar_left_btn);
        rc_selling_photos = (RecyclerView) findViewById(R.id.rc_selling_photos);
        tv_selling_category = (TextView) findViewById(R.id.tv_selling_category);
    }

    private void loadContent() {
        selectedPhotos.add("http://192.168.1.100:8080/resources/selling/18751103565/cover1.jpg");
        selectedPhotos.add("http://192.168.1.100:8080/resources/selling/18751103565/cover2.jpg");
        selectedPhotos.add("http://192.168.1.100:8080/resources/selling/18751103565/cover3.jpg");
        photoAdapter = new PhotoAdapter(this, selectedPhotos);
        rc_selling_photos.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        rc_selling_photos.setAdapter(photoAdapter);

        pvOptions = new  OptionsPickerView.Builder(SellingEditActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String tx = cList1.get(options1).getPickerViewText()
                        + " " + cList2.get(options1).get(option2).getPickerViewText();
                tv_selling_category.setText(tx);
            }
        }).build();
        pvOptions.setPicker(cList1, cList2);
    }

    private void initClickEvents() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SellingEditActivity.this.finish();
            }
        });

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

        tv_selling_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyBoardUtils.closeKeybord(SellingEditActivity.this, getCurrentFocus());
                pvOptions.show();
            }
        });
    }

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



}
