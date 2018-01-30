package com.example.xmfy.yzubookshop.module.user;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.model.Selling;
import com.example.xmfy.yzubookshop.module.selling.SellingAdapter;
import com.example.xmfy.yzubookshop.net.AsyncResponse;
import com.example.xmfy.yzubookshop.net.CollectionAsyncTask;
import com.example.xmfy.yzubookshop.utils.CommonUtils;
import com.example.xmfy.yzubookshop.utils.LoginUtils;

import java.util.ArrayList;
import java.util.List;


public class CollectionActivity extends AppCompatActivity {
    private Button btn_back;
    private List<Selling> sList;
    private SharedPreferences preferences;
    private SwipeMenuListView lv_mycollection;
    private SellingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_collection);
        bindView();
        loadData();
        loadContent();
        initClickEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void bindView() {
        lv_mycollection = (SwipeMenuListView) findViewById(R.id.lv_mycollection);
        btn_back = (Button) findViewById(R.id.toolbar_left_btn);
        sList = new ArrayList<>();
        preferences = getSharedPreferences("User",MODE_PRIVATE);
    }

    private void loadContent() {
        adapter = new SellingAdapter(sList, CollectionActivity.this);
        lv_mycollection.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(CollectionActivity.this);
                deleteItem.setBackground(R.color.icon_selling_delete);
                deleteItem.setWidth(CommonUtils.dip2px(CollectionActivity.this, 90));
                deleteItem.setIcon(R.mipmap.icon_selling_delete);
                menu.addMenuItem(deleteItem);
            }
        };
        lv_mycollection.setMenuCreator(creator);
    }

    private void initClickEvents(){
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CollectionActivity.this.finish();
            }
        });

        lv_mycollection.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CollectionActivity.this);
                builder.setTitle("取消收藏");
                builder.setMessage("您确定要取消收藏这本书吗?\n" + sList.get(position).getTitle());
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CollectionAsyncTask<Integer> task = new CollectionAsyncTask();
                        task.setType(CollectionAsyncTask.TYPE_CHANGE);
                        task.setAsyncResponse(new AsyncResponse() {
                            @Override
                            public void onDataReceivedSuccess(FormedData formedData) {
                                Toast.makeText(CollectionActivity.this, "操作成功!",Toast.LENGTH_SHORT).show();
                                onResume();
                            }

                            @Override
                            public void onDataReceivedFailed() {
                                Toast.makeText(CollectionActivity.this, "操作失败,请稍后再试!",Toast.LENGTH_SHORT).show();
                            }
                        });
                        task.execute(LoginUtils.getAccount(getSharedPreferences("User", MODE_PRIVATE)), sList.get(position).getId()+"", "delete");
                    }
                });
                builder.show();
                return false;
            }
        });
    }

    private void loadData() {
        CollectionAsyncTask<List<Selling>> task = new CollectionAsyncTask<>();
        task.setType(CollectionAsyncTask.TYPE_QUERY);
        task.setAsyncResponse(new AsyncResponse<List<Selling>>() {
            @Override
            public void onDataReceivedSuccess(FormedData<List<Selling>> formedData) {
                if (formedData.isSuccess()){
                    sList = formedData.getData();
                    adapter.update(sList);
                }else {
                    Toast.makeText(CollectionActivity.this, formedData.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDataReceivedFailed() {
                Toast.makeText(CollectionActivity.this, "网络连接错误,请检查相关设置!", Toast.LENGTH_SHORT).show();
            }
        });
        task.execute(LoginUtils.getAccount(preferences));
    }
}
