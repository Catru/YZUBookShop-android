package com.example.xmfy.yzubookshop.module.buy;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.arlib.floatingsearchview.util.Util;
import com.bumptech.glide.Glide;
import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.BookSearchBean;
import com.example.xmfy.yzubookshop.utils.CommonUtils;
import com.example.xmfy.yzubookshop.widget.RichText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xmfy on 2018/1/26.
 */
public class SearchResultsListAdapter extends RecyclerView.Adapter<SearchResultsListAdapter.ViewHolder> {

    private Context context;

    private List<BookSearchBean> bookList = new ArrayList<>();

    private int mLastAnimatedItemPosition = -1;

    private Drawable collected;
    private Drawable uncollected;

    public SearchResultsListAdapter(Context context) {
        this.context = context;
        initDrawable();
    }

    private void initDrawable() {
        int w = CommonUtils.dip2px(context, 20);
        collected = context.getResources().getDrawable(R.mipmap.icon_collected, context.getTheme());
        collected.setBounds(0, 0, w, w);
        uncollected = context.getResources().getDrawable(R.mipmap.icon_uncollected, context.getTheme());
        uncollected.setBounds(0, 0, w, w);
    }

    public interface OnItemClickListener {
        void onClick(BookSearchBean book);
    }

    public interface OnCollectsClickListener {
        void onClick(RichText rt, BookSearchBean book);
    }

    private OnItemClickListener mItemsOnClickListener;

    private OnCollectsClickListener mCollectsClickListener;

    public void swapData(List<BookSearchBean> newBookList) {
        bookList = newBookList;
        notifyDataSetChanged();
    }

    public void setItemsOnClickListener(OnItemClickListener onClickListener) {
        this.mItemsOnClickListener = onClickListener;
    }

    public void setCollectsClickListener(OnCollectsClickListener onCollectsClickListener) {
        this.mCollectsClickListener = onCollectsClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buy_book, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        BookSearchBean book = bookList.get(position);
        holder.tv_buy_title.setText(book.getTitle());
        holder.tv_buy_author.setText(book.getAuthor());
        holder.tv_buy_price.setText(book.getPrice() + "");
        holder.rt_buy_views.setText(book.getViews() + "");
        holder.rt_buy_collects.setText(book.getCollects() + "");
        Glide.with(context).load(book.getPhotoUrl().split(" ")[0]).into(holder.iv_buy_pic);
        holder.rt_buy_collects.setCompoundDrawables(book.getIsCollected() != 0 ? collected : uncollected, null, null, null);
        if (mLastAnimatedItemPosition < position) {
            animateItem(holder.itemView);
            mLastAnimatedItemPosition = position;
        }

        if (mItemsOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemsOnClickListener.onClick(bookList.get(position));
                }
            });
        }

        if (mCollectsClickListener != null) {
            holder.rt_buy_collects.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCollectsClickListener.onClick(holder.rt_buy_collects, bookList.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView iv_buy_pic;
        final TextView tv_buy_title;
        final TextView tv_buy_author;
        final TextView tv_buy_price;
        final RichText rt_buy_views;
        final RichText rt_buy_collects;

        ViewHolder(View v) {
            super(v);
            iv_buy_pic = v.findViewById(R.id.iv_buy_pics);
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
