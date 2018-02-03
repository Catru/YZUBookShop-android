package com.example.xmfy.yzubookshop.module.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.News;

import java.util.List;

/**
 * Created by xmfy on 2018/1/9.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> newsList;
    private Context context;

    private OnItemClickListener mItemsOnClickListener;

    public void setmItemsOnClickListener(OnItemClickListener mItemsOnClickListener) {
        this.mItemsOnClickListener = mItemsOnClickListener;
    }

    public NewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    public void update(List<News> list) {
        this.newsList = list;
        notifyDataSetChanged();
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, final int position) {
        holder.news_title.setText(newsList.get(position).getTitle());
        holder.news_content.setText(newsList.get(position).getDescription());
        Glide.with(context).load(newsList.get(position).getIconAddress()).into(holder.news_icon);
        if (mItemsOnClickListener != null) {
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemsOnClickListener.onClick(newsList.get(position).getWebUrl());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        ImageView news_icon;
        TextView news_title;
        TextView news_content;

        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            news_icon = itemView.findViewById(R.id.news_icon);
            news_title = itemView.findViewById(R.id.news_title);
            news_content = itemView.findViewById(R.id.news_content);
        }
    }


    public interface OnItemClickListener {
        void onClick(String url);
    }
}
