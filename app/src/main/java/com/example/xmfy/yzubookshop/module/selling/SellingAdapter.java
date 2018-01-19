package com.example.xmfy.yzubookshop.module.selling;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.Selling;
import com.example.xmfy.yzubookshop.widget.RichText;

import java.util.List;

/**
 * Created by xmfy on 2018/1/19.
 */
public class SellingAdapter extends BaseAdapter{
    private List<Selling> sList;
    private Context context;

    public SellingAdapter(List<Selling> sList, Context context) {
        this.sList = sList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return sList == null ? 0 : sList.size();
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
        ViewHolder holder = null;
        if (v == null){
            v = LayoutInflater.from(context).inflate(R.layout.item_selling, viewGroup, false);
            holder = new ViewHolder(v);
            v.setTag(holder);
        }else {
            holder = (ViewHolder) v.getTag();
        }
        Selling selling = sList.get(i);
        holder.title.setText(selling.getTitle());
        holder.author.setText(selling.getAuthor());
        holder.price.setText("￥"+selling.getPrice());
        holder.views.setText(selling.getViews()+"");
        holder.collects.setText(selling.getCollects()+"");
        return v;
    }

    class ViewHolder{
        private TextView title, author, price;
        private RichText views, collects;

        public ViewHolder(View v) {
            title = v.findViewById(R.id.tv_selling_title);
            author = v.findViewById(R.id.tv_selling_author);
            price = v.findViewById(R.id.tv_selling_price);
            views = v.findViewById(R.id.tv_selling_views);
            collects = v.findViewById(R.id.tv_selling_collects);
        }
    }
}
