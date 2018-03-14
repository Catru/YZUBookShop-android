package com.example.xmfy.yzubookshop.module.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.BookInfo;
import com.example.xmfy.yzubookshop.utils.CategoryLoader;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xmfy on 2018/3/8.
 */

public class BookListAdapter extends BaseAdapter {

    private Context context;
    private List<BookInfo> bookInfos;

    public BookListAdapter(Context context, List<BookInfo> bookInfos) {
        this.context = context;
        this.bookInfos = bookInfos;
    }

    public void update(List<BookInfo> bookInfos) {
        this.bookInfos = bookInfos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return bookInfos == null?0:bookInfos.size();
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
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.item_order_book, viewGroup, false);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        BookInfo bookInfo = bookInfos.get(i);
        String cover_url = bookInfo.getUrls().split(" ")[0];
        Glide.with(context).load(cover_url).into(holder.iv_order_cover);
        holder.tv_order_title.setText(bookInfo.getTitle());
        holder.tv_order_author.setText(bookInfo.getAuthor());
        HashMap<Integer, String> map = CategoryLoader.getCategoryNameById(bookInfo.getCategory1(), bookInfo.getCategory2());
        holder.tv_order_category.setText(map.get(1) + " -> "+ map.get(2));
        holder.tv_order_price.setText(String.format("￥%.2f", bookInfo.getPrice()));
        return v;
    }

    class ViewHolder{
        ImageView iv_order_cover;
        TextView tv_order_title;
        TextView tv_order_author;
        TextView tv_order_category;
        TextView tv_order_price;

        public ViewHolder(View v) {
            iv_order_cover = v.findViewById(R.id.iv_order_cover);
            tv_order_title = v.findViewById(R.id.tv_order_title);
            tv_order_author = v.findViewById(R.id.tv_order_author);
            tv_order_category = v.findViewById(R.id.tv_order_category);
            tv_order_price = v.findViewById(R.id.tv_order_price);
        }
    }
}
