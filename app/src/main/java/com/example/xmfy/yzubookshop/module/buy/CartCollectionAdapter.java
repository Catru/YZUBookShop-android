package com.example.xmfy.yzubookshop.module.buy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.CartCollection;
import com.example.xmfy.yzubookshop.widget.RichText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xmfy on 2018/2/2.
 */

public class CartCollectionAdapter extends BaseAdapter {

    private Context context;

    private List<CartCollection> cList;

    private List<BookInfoAdapter> adapters;

    private List<CheckBox> checkBoxes;

    private OnCheckBoxClickListener mCheckBoxClickListener;

    private interface OnCheckBoxClickListener{
        void onClick(int position, Boolean checked);
    }

    public void setmCheckBoxClickListener(OnCheckBoxClickListener checkBoxClickListener) {
        this.mCheckBoxClickListener = checkBoxClickListener;
    }

    public CartCollectionAdapter(Context context, List<CartCollection> cList) {
        this.context = context;
        this.cList = cList;
        adapters = new ArrayList<>();
        checkBoxes = new ArrayList<>();
    }

    public void update(List<CartCollection> newList) {
        cList = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return cList == null ? 0 : cList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View v, ViewGroup viewGroup) {
        ViewHolder holder;
        if (v == null){
            v = LayoutInflater.from(context).inflate(R.layout.item_cart_collection, viewGroup, false);
            holder = new ViewHolder(v);
            v.setTag(holder);
        }else {
            holder = (ViewHolder) v.getTag();
        }
        holder.cb_collection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                updateCheckBoxStatus(i, b);
            }
        });

        CartCollection c = cList.get(i);
        holder.rt_seller.setText(c.getSeller());
        BookInfoAdapter adapter;
        if (adapters.size() < i + 1){
            adapter = new BookInfoAdapter(context, c.getBooks());
            adapters.add(adapter);
            checkBoxes.add(holder.cb_collection);
        } else
            adapter = adapters.get(i);
        holder.lv_bookInfo.setAdapter(adapter);
        setListViewHeightBasedOnChildren(holder.lv_bookInfo);
        return v;
    }

    private void updateCheckBoxStatus(int i, boolean b){
        Log.e("cart", "position:"+i + "   "+ b);
        adapters.get(i).updateCheckBox(b);
    }

    class ViewHolder{
        CheckBox cb_collection;
        RichText rt_seller;
        SwipeMenuListView lv_bookInfo;

        ViewHolder(View v){
            cb_collection = v.findViewById(R.id.cb_collection);
            rt_seller = v.findViewById(R.id.rt_seller);
            lv_bookInfo = v.findViewById(R.id.lv_bookInfo);
        }
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);  //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}
