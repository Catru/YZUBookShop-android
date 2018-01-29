package com.example.xmfy.yzubookshop.module.buy.searchTab;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.module.selling.bean.Category1;
import com.example.xmfy.yzubookshop.module.selling.bean.Category2;
import com.example.xmfy.yzubookshop.utils.CategoryLoader;
import com.example.xmfy.yzubookshop.utils.ScreenUtils;
import com.example.xmfy.yzubookshop.widget.RichText;
import com.jaygoo.widget.RangeSeekBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xmfy on 2018/1/29.
 */
public class ClassifyTab {

    private List<SortBean> sortList;

    private Context context;
    private RichText rt_sort, rt_category, rt_price;

    private PopupWindow pw_sort, pw_category, pw_price;

    private ListView lv_sort, lv_category_left, lv_category_right;

    private TextView tv_seekbar_price;
    private RangeSeekBar seekBar_price;

    private SortAdapter sortAdapter;
    private CategoryAdapter<Category1> categoryAdapter1;
    private CategoryAdapter<Category2> categoryAdapter2;

    private int color_active, color_default;
    private Drawable arrow_down, arrow_up;
    private List<Category1> cList1;
    private List<List<Category2>> cList2;

    private SearchConditions searchConditions;

    public ClassifyTab(Context context, RichText rt_sort, RichText rt_category, RichText rt_price) {
        this.context = context;
        this.rt_sort = rt_sort;
        this.rt_category = rt_category;
        this.rt_price = rt_price;
    }

    public void load(){
        initData();
        initAdapter();
        loadViews();
        initClickEvents();
    }

    public void setSearchConditions(SearchConditions searchConditions) {
        this.searchConditions = searchConditions;
    }

    public SearchConditions getSearchConditions() {
        return searchConditions;
    }

    private void initData(){
        ScreenUtils.initScreen((Activity) context);
        sortList = new ArrayList<>(Arrays.asList(new SortBean("默认排序", true),
                new SortBean("价格优先", false), new SortBean("浏览量优先", false), new SortBean("收藏量优先", false), new SortBean("综合排序", false)));

        color_active = context.getResources().getColor(R.color.menubar_active, context.getTheme());
        color_default = context.getResources().getColor(R.color.menubar_default, context.getTheme());

        arrow_down =context.getResources().getDrawable(R.mipmap.ic_arrow_black, context.getTheme());
        arrow_down.setBounds(0, 0, arrow_down.getMinimumWidth(), arrow_down.getMinimumHeight());

        arrow_up = context.getResources().getDrawable(R.mipmap.ic_arrow_orange, context.getTheme());
        arrow_up.setBounds(0, 0, arrow_up.getMinimumWidth(), arrow_up.getMinimumHeight());

        cList1 = CategoryLoader.cList1;
        cList2 = CategoryLoader.cList2;
    }

    private void initAdapter(){
        sortAdapter = new SortAdapter(context, sortList);
        categoryAdapter1 = new CategoryAdapter<>(context, cList1);
        categoryAdapter2 = new CategoryAdapter<>(context, new ArrayList<Category2>());
    }

    private void loadViews(){
        //初始化排序栏
        View popupView_sort = LayoutInflater.from(context).inflate(R.layout.layout_pw_sort, null);
        lv_sort = popupView_sort.findViewById(R.id.lv_sort);
        lv_sort.setAdapter(sortAdapter);
        pw_sort = new PopupWindow(popupView_sort, ScreenUtils.getScreenW((Activity) context)/3, LinearLayout.LayoutParams.WRAP_CONTENT);
        pw_sort.setAnimationStyle(R.style.popup_window_anim);
        pw_sort.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        pw_sort.setFocusable(true);
        pw_sort.setOutsideTouchable(true);
        pw_sort.update();

        //初始化分类栏
        View popupView_category = LayoutInflater.from(context).inflate(R.layout.layout_pw_category, null);
        lv_category_left = popupView_category.findViewById(R.id.lv_category_left);
        lv_category_left.setAdapter(categoryAdapter1);
        pw_category = new PopupWindow(popupView_category, LinearLayout.LayoutParams.WRAP_CONTENT, ScreenUtils.getScreenH((Activity) context)/2);
        pw_category.setAnimationStyle(R.style.popup_window_anim);
        pw_category.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        pw_category.setFocusable(true);
        pw_category.setOutsideTouchable(true);
        pw_category.update();

        lv_category_right = popupView_category.findViewById(R.id.lv_category_right);
        lv_category_right.setAdapter(categoryAdapter2);

        View popupView_price = LayoutInflater.from(context).inflate(R.layout.layout_pw_price, null);
        tv_seekbar_price = popupView_price.findViewById(R.id.tv_seekbar_price);
        seekBar_price = popupView_price.findViewById(R.id.seekbar_price);
        seekBar_price.setRange(0, 250);
        seekBar_price.setValue(0, 200);
        pw_price = new PopupWindow(popupView_price, ScreenUtils.getScreenW((Activity) context)*2/3, LinearLayout.LayoutParams.WRAP_CONTENT);
        pw_price.setAnimationStyle(R.style.popup_window_anim);
        pw_price.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        pw_price.setFocusable(true);
        pw_price.setOutsideTouchable(true);
        pw_price.update();
    }

    private void initClickEvents(){
        //初始化排序栏
        lv_sort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!sortList.get(i).getChecked()) {
                    for (int k = 0; k < sortList.size(); k++)
                        sortList.get(k).setChecked(i==k);
                    sortAdapter.update(sortList);
                    rt_sort.setText(sortList.get(i).getText());
                    searchConditions.setSort(i);
                }
            }
        });
        rt_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pw_sort.isShowing()){
                    pw_sort.dismiss();
                } else{
                    pw_sort.showAsDropDown(rt_sort, 0, 50);
                    rt_sort.setTextColor(color_active);
                    rt_sort.setCompoundDrawables(null, null, arrow_up, null);
                }
            }
        });
        pw_sort.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                rt_sort.setTextColor(color_default);
                rt_sort.setCompoundDrawables(null, null, arrow_down, null);
            }
        });

        lv_category_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                categoryAdapter2.setChecked(-1);
                if (categoryAdapter1.getChecked() == i){
                    categoryAdapter1.setChecked(-1);
                    lv_category_right.setVisibility(View.GONE);
                }else {
                    categoryAdapter1.setChecked(i);
                    categoryAdapter2.update(cList2.get(categoryAdapter1.getChecked()));
                    lv_category_right.setVisibility(View.VISIBLE);
                    searchConditions.setC2(categoryAdapter1.getCurrentId());
                }
                categoryAdapter1.notifyDataSetChanged();
                searchConditions.setC1(cList1.get(categoryAdapter1.getChecked()).getId());
            }
        });

        lv_category_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                categoryAdapter2.setChecked(i);
                categoryAdapter2.notifyDataSetChanged();
                searchConditions.setC2(categoryAdapter2.getCurrentId());
            }
        });

        rt_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pw_category.isShowing()){
                    pw_category.dismiss();
                }else {
                    pw_category.showAsDropDown(rt_category, -250, 50);
                    rt_category.setTextColor(color_active);
                rt_category.setCompoundDrawables(null, null, arrow_up, null);
            }
            }
        });
        pw_category.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                rt_category.setTextColor(color_default);
                rt_category.setCompoundDrawables(null, null, arrow_down, null);
            }
        });

        seekBar_price.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
                tv_seekbar_price.setText((int)min+"-"+(int)max);
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
                float[] mm = view.getCurrentRange();
                searchConditions.setMin(Math.round(mm[0]));
                searchConditions.setMax(Math.round(mm[1]));
            }
        });

        rt_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pw_price.isShowing()){
                    pw_price.dismiss();
                }else {
                    pw_price.showAsDropDown(rt_category, 0, 50);
                    rt_price.setTextColor(color_active);
                    rt_price.setCompoundDrawables(null, null, arrow_up, null);
                }
            }
        });
        pw_price.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                rt_price.setTextColor(color_default);
                rt_price.setCompoundDrawables(null, null, arrow_down, null);
            }
        });

    }
}