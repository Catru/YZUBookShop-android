package com.example.xmfy.yzubookshop.module.buy;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.BookInfo;
import com.example.xmfy.yzubookshop.model.CartCollection;
import com.example.xmfy.yzubookshop.utils.CommonUtils;
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

    private BookInfoAdapter.OnPriceChangeListener onPriceChangeListener;

    private OnDeleteItemListerner onDeleteItemListerner;

    private SwipeMenuCreator creator;

    public interface OnDeleteItemListerner{
        void onDelete(BookInfo bookInfo);
    }

    void setOnDeleteItemListern(OnDeleteItemListerner onDeleteItemListerner) {
        this.onDeleteItemListerner = onDeleteItemListerner;
    }

    void setOnPriceChangeListener(BookInfoAdapter.OnPriceChangeListener onPriceChangeListener) {
        this.onPriceChangeListener = onPriceChangeListener;
    }

    CartCollectionAdapter(Context context, List<CartCollection> cList) {
        this.context = context;
        this.cList = cList;
        adapters = new ArrayList<>();
        init();
    }

    private void init() {
        creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(context);
                deleteItem.setBackground(R.color.icon_selling_delete);
                deleteItem.setWidth(CommonUtils.dip2px(context, 90));
                deleteItem.setIcon(R.mipmap.icon_selling_delete);
                menu.addMenuItem(deleteItem);
            }
        };
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
        CartCollection c = cList.get(i);
        holder.rt_seller.setText(c.getSeller());
        BookInfoAdapter adapter;
        if (adapters.size() < i + 1){
            adapter = new BookInfoAdapter(context, c.getBooks());
            adapter.setOnPriceChangeListener(new BookInfoAdapter.OnPriceChangeListener() {
                @Override
                public void onChange(float price) {
                    if (onPriceChangeListener != null){
                        onPriceChangeListener.onChange(price);
                    }
                }
            });
            adapters.add(adapter);
        } else
            adapter = adapters.get(i);
        holder.lv_bookInfo.setAdapter(adapter);
        holder.lv_bookInfo.setMenuCreator(creator);
        setListViewHeightBasedOnChildren(holder.lv_bookInfo);
        holder.lv_bookInfo.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, final int index) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("删除");
                builder.setMessage("您确定要从购物车移除这本书吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int postion) {
                        onDeleteItemListerner.onDelete(cList.get(i).getBooks().get(index));
                    }
                });
                builder.show();
                return false;
            }
        });
        return v;
    }

    class ViewHolder{
        RichText rt_seller;
        SwipeMenuListView lv_bookInfo;

        ViewHolder(View v){
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

    public List<CartCollection> getCheckedBooks(){
        List<CartCollection> list = new ArrayList<>();
        for (int i = 0;i<cList.size();i++){
            List<BookInfo> books = adapters.get(i).getCheckedBookInfos();
            if (books.size() > 0)
                list.add(new CartCollection(cList.get(i).getSeller(), books));
        }
        return list;
    }
}
