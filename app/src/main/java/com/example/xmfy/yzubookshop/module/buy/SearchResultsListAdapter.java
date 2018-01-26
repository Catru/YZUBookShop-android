package com.example.xmfy.yzubookshop.module.buy;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.arlib.floatingsearchview.util.Util;
import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.Book;
import com.example.xmfy.yzubookshop.widget.RichText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xmfy on 2018/1/26.
 */
public class SearchResultsListAdapter extends RecyclerView.Adapter<SearchResultsListAdapter.ViewHolder>{

    private List<Book> bookList = new ArrayList<>();

    private int mLastAnimatedItemPosition = -1;

    public interface OnItemClickListener{
        void onClick(Book book);
    }

    private OnItemClickListener mItemsOnClickListener;

    public void swapData(List<Book> newBookList){
        bookList = newBookList;
        notifyDataSetChanged();
    }

    public void setItemsOnClickListener(OnItemClickListener onClickListener){
        this.mItemsOnClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buy_book, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Book book = bookList.get(position);
        holder.tv_buy_title.setText(book.getTitle());
        holder.tv_buy_author.setText(book.getAuthor());
        holder.tv_buy_price.setText(book.getPrice()+"");
        holder.rt_buy_views.setText(book.getViews()+"");
        holder.rt_buy_collects.setText(book.getViews()+"");

        if(mLastAnimatedItemPosition < position){
            animateItem(holder.itemView);
            mLastAnimatedItemPosition = position;
        }

        if(mItemsOnClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemsOnClickListener.onClick(bookList.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final ImageView iv_buy_pic;
        public final TextView tv_buy_title;
        public final TextView tv_buy_author;
        public final TextView tv_buy_price;
        public final RichText rt_buy_views;
        public final RichText rt_buy_collects;

        public ViewHolder(View v) {
            super(v);
            iv_buy_pic = v.findViewById(R.id.iv_buy_pic);
            tv_buy_title = v.findViewById(R.id.tv_buy_title);
            tv_buy_author = v.findViewById(R.id.tv_buy_author);
            tv_buy_price = v.findViewById(R.id.tv_buy_price);
            rt_buy_views = v.findViewById(R.id.rt_buy_views);
            rt_buy_collects = v.findViewById(R.id.rt_buy_collects);
        }
    }

    private void animateItem(View view) {
        view.setTranslationY(Util.getScreenHeight((Activity) view.getContext()));
        view.animate()
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator(3.f))
                .setDuration(700)
                .start();
    }
}
