package com.example.xmfy.yzubookshop.module.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.OrderCollection;
import com.example.xmfy.yzubookshop.utils.ScreenUtils;
import com.example.xmfy.yzubookshop.widget.RichText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xmfy on 2018/3/10.
 */

public class OrderBuyerAdapter extends BaseAdapter {

    private Context context;
    private List<OrderCollection> cList;
    private int flag = 1;
    private List<OrderBookListAdapter> adapters = new ArrayList<>();

    public OrderBuyerAdapter(Context context, List<OrderCollection> cList, int flag) {
        this.context = context;
        this.cList = cList;
        this.flag = flag;
    }

    public void update(List<OrderCollection> cList){
        this.cList = cList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return cList == null?0:cList.size();
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
    public View getView(int i, View v, ViewGroup viewGroup) {
        ViewHolder holder;
        if (v == null){
            v = LayoutInflater.from(context).inflate(R.layout.item_order_collection_buyer, viewGroup, false);
            holder = new ViewHolder(v);
            v.setTag(holder);
        }else {
            holder = (ViewHolder) v.getTag();
        }
        OrderCollection collection = cList.get(i);
        holder.sellerName.setText(collection.getSellerName());
        holder.sellerPhone.setText(collection.getSellerPhone());
        OrderBookListAdapter adapter;
        if (adapters.size() < i + 1){
            adapter = new OrderBookListAdapter(context, collection.getBooks());
            adapters.add(adapter);
        }else
            adapter = adapters.get(i);
        holder.lv_books.setAdapter(adapter);
        ScreenUtils.setListViewHeightBasedOnChildren(holder.lv_books);
        switch (flag){
            case 1:
                holder.btn1.setVisibility(View.GONE);
                holder.btn2.setVisibility(View.GONE);
                holder.btn3.setText("等待卖家确认");
                holder.btn3.setVisibility(View.VISIBLE);
                break;
        }
        return v;
    }

    class ViewHolder{
        RichText sellerName;
        TextView sellerPhone;
        ListView lv_books;
        Button btn3, btn2, btn1;

        ViewHolder(View v){
            sellerName = v.findViewById(R.id.rt_order_buyer_seller);
            sellerPhone = v.findViewById(R.id.tv_order_buyer_seller_phone);
            lv_books = v.findViewById(R.id.lv_order_books);
            btn3 = v.findViewById(R.id.btn_order_buyer_3);
            btn2 = v.findViewById(R.id.btn_order_buyer_2);
            btn1 = v.findViewById(R.id.btn_order_buyer_1);
        }
    }
}
