package com.example.xmfy.yzubookshop.module.selling;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

    final static int TYPE_ADD = 1;
    final static int TYPE_PHOTO = 2;

    final static int MAX = 9;

    public SellingAdapter(List<Selling> sList, Context context) {
        this.sList = sList;
        this.context = context;
    }

    public void update(List<Selling> list){
        sList = list;
        notifyDataSetChanged();
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
        holder.price.setText(String.format("￥%.2f", selling.getPrice()));
        holder.views.setText(selling.getViews()+"");
        holder.collects.setText(selling.getCollects()+"");
        ((Activity)context).runOnUiThread(new ImgRunnable(context, selling.getPhotoUrl().split(" ")[0], holder.cover));
        return v;
    }

    class ViewHolder{
        private TextView title, author, price;
        private RichText views, collects;
        private ImageView cover;

        public ViewHolder(View v) {
            title = v.findViewById(R.id.tv_selling_title);
            author = v.findViewById(R.id.tv_selling_author);
            price = v.findViewById(R.id.tv_selling_price);
            views = v.findViewById(R.id.tv_selling_views);
            collects = v.findViewById(R.id.tv_selling_collects);
            cover = v.findViewById(R.id.iv_selling_pic);
        }
    }

    class ImgRunnable implements Runnable {
        private Context context;
        private String url;
        private ImageView imageView;

        public ImgRunnable(Context context, String url, ImageView imageView) {
            this.context = context;
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        public void run() {
            Glide.with(context).load(url).into(imageView);
        }
    }


}
