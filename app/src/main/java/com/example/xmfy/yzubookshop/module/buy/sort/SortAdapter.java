package com.example.xmfy.yzubookshop.module.buy.sort;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xmfy.yzubookshop.R;

import java.util.List;

/**
 * Created by xmfy on 2018/1/28.
 */
public class SortAdapter extends BaseAdapter {

    private Context context;
    private List<SortBean> beanList;

    public SortAdapter(Context context, List<SortBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    public void update(List<SortBean> newList) {
        beanList = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return beanList.size();
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
        ViewHolder viewHolder;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.item_buy_sort, viewGroup, false);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        SortBean bean = beanList.get(i);
        viewHolder.tv_buy_sort.setText(bean.getText());
        viewHolder.tv_buy_sort.setTextColor(context.getResources().getColor(bean.getChecked()?R.color.menubar_active:R.color.menubar_default, context.getTheme()));
        viewHolder.iv_buy_sort_checked.setVisibility(bean.getChecked() ? View.VISIBLE : View.GONE);
        return v;
    }

    class ViewHolder {
        public TextView tv_buy_sort;
        public ImageView iv_buy_sort_checked;

        public ViewHolder(View view) {
            tv_buy_sort = view.findViewById(R.id.tv_buy_sort);
            iv_buy_sort_checked = view.findViewById(R.id.iv_buy_sort_checked);
        }
    }

}
