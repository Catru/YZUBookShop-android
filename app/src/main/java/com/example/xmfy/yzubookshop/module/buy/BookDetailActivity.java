package com.example.xmfy.yzubookshop.module.buy;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.BookSearchBean;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.net.AsyncResponse;
import com.example.xmfy.yzubookshop.net.CartAsyncTask;
import com.example.xmfy.yzubookshop.net.CollectionAsyncTask;
import com.example.xmfy.yzubookshop.utils.CommonUtils;
import com.example.xmfy.yzubookshop.utils.LoginUtils;
import com.example.xmfy.yzubookshop.widget.RichText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

public class BookDetailActivity extends AppCompatActivity {

    private BookSearchBean bindBook;
    private Boolean isCollected;
    private SharedPreferences preference;

    private Button btn_back;

    private ViewPager vp_book_detail_pics;
    private TextView tv_book_detail_title;
    private TextView tv_book_detail_author;
    private TextView tv_book_detail_price;
    private TextView tv_book_detail_views;
    private TextView tv_book_detail_collects;
    private TagGroup tag_group;
    private TextView tv_book_detail_description;

    private RichText rt_book_detail_collect;
    private Button btn_cart;
    private Button btn_buy;

    private List<ImageView> imgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        bindView();
        loadData();
        initClickEvents();
    }

    private void bindView() {
        imgs = new ArrayList<>();
        btn_back = (Button) findViewById(R.id.toolbar_left_btn);
        vp_book_detail_pics = (ViewPager) findViewById(R.id.vp_book_detail_pics);
        tv_book_detail_title = (TextView) findViewById(R.id.tv_book_detail_title);
        tv_book_detail_author = (TextView) findViewById(R.id.tv_book_detail_author);
        tv_book_detail_price = (TextView) findViewById(R.id.tv_book_detail_price);
        tv_book_detail_views = (TextView) findViewById(R.id.tv_book_detail_views);
        tv_book_detail_collects = (TextView) findViewById(R.id.tv_book_detail_collects);
        tag_group = (TagGroup) findViewById(R.id.tag_group);
        tv_book_detail_description = (TextView) findViewById(R.id.tv_book_detail_description);

        rt_book_detail_collect = (RichText) findViewById(R.id.rt_book_detail_collect);
        btn_cart = (Button) findViewById(R.id.btn_cart);
        btn_buy = (Button) findViewById(R.id.btn_buy);

        preference = getSharedPreferences("User", MODE_PRIVATE);
    }


    private void loadData() {
        bindBook = new Gson().fromJson(getIntent().getStringExtra("book"), BookSearchBean.class);
        CollectionAsyncTask<Integer> task = new CollectionAsyncTask<>();
        task.setType(CollectionAsyncTask.TYPE_VIEWS_ADD);
        task.execute(bindBook.getId()+"");
        if (bindBook != null){
            final String[] urls = bindBook.getPhotoUrl().split(" ");
            for (String url : urls){
                ImageView iv = new ImageView(this);
                iv.setAdjustViewBounds(true);
                iv.setScaleType(ImageView.ScaleType.CENTER);
                imgs.add(iv);
                Glide.with(this).load(url).into(iv);
            }
            vp_book_detail_pics.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return imgs.size();
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    container.addView(imgs.get(position));
                    return imgs.get(position);
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView(imgs.get(position));
                }
            });
            tv_book_detail_title.setText(bindBook.getTitle());
            tv_book_detail_author.setText("作者:" + bindBook.getAuthor());
            tv_book_detail_price.setText(String.format("%.2f", bindBook.getPrice()));
            tv_book_detail_views.setText(bindBook.getViews()+"");
            tv_book_detail_collects.setText(bindBook.getCollects()+"");
            tv_book_detail_description.setText(bindBook.getDescription());
            isCollected = bindBook.getIsCollected() != 0;
            if (isCollected){
                int w = CommonUtils.dip2px(this, 35);
                Drawable drawable = getResources().getDrawable(R.mipmap.ic_stared, getTheme());
                drawable.setBounds(0,0,w,w);
                rt_book_detail_collect.setCompoundDrawables(null, drawable, null, null);
            }
            tag_group.setTags(bindBook.getKeywords().split(" "));
        }
    }

    private void initClickEvents(){
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookDetailActivity.this.finish();
            }
        });

        rt_book_detail_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginUtils.isLogined(preference)){
                    CollectionAsyncTask<Integer> task = new CollectionAsyncTask<>();
                    task.setType(CollectionAsyncTask.TYPE_CHANGE);
                    task.execute(LoginUtils.getAccount(preference), bindBook.getId() + "", isCollected ? "delete" : "add");
                    Drawable drawable = getResources().getDrawable(isCollected ? R.mipmap.ic_unstar : R.mipmap.ic_stared, getTheme());
                    int w = CommonUtils.dip2px(BookDetailActivity.this, 35);
                    drawable.setBounds(0,0,w,w);
                    rt_book_detail_collect.setCompoundDrawables(null, drawable, null, null);
                    isCollected = !isCollected;
                }else
                    Toast.makeText(BookDetailActivity.this, "请先登录!", Toast.LENGTH_SHORT).show();
            }
        });

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartAsyncTask<Integer> task = new CartAsyncTask<>(CartAsyncTask.TYPE_INSERT, new AsyncResponse<Integer>() {
                    @Override
                    public void onDataReceivedSuccess(FormedData<Integer> formedData) {
                        if (formedData.isSuccess()){
                            Toast.makeText(BookDetailActivity.this, "添加成功,点击购物车查看", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(BookDetailActivity.this, formedData.getError(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onDataReceivedFailed() {
                        Toast.makeText(BookDetailActivity.this, "网络异常,请检查相关设置!", Toast.LENGTH_SHORT).show();
                    }
                });
                task.execute(bindBook.getAccount(), bindBook.getId()+"", bindBook.getPrice()+"", LoginUtils.getAccount(preference));
            }
        });

    }
}
