package com.example.xmfy.yzubookshop.module.buy.searchTab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.module.selling.bean.Category;

import java.util.List;

/**
 * Created by xmfy on 2018/1/29.
 */
public class CategoryAdapter<T extends Category> extends BaseAdapter{

    private Context context;
    private List<T> cList;
    private int checked = -1;

    public CategoryAdapter(Context context, List<T> cList) {
        this.cList = cList;
        this.context = context;
    }

    public void update(List<T> newList) {
        cList = newList;
        notifyDataSetChanged();
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getCurrentId(){
        if (checked == -1)
            return -1;
        return cList.get(checked).getId();
    }

    @Override
    public int getCount() {
        return cList.size();
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

        Category c = cList.get(i);
        viewHolder.id = c.getId();
        viewHolder.tv_buy_category.setText(c.getName());
        viewHolder.tv_buy_category.setTextColor(context.getResources().getColor(i==checked?R.color.menubar_active:R.color.menubar_default, context.getTheme()));
        viewHolder.iv_buy_category_checked.setVisibility(i==checked ? View.VISIBLE : View.GONE);
        return v;
    }

    class ViewHolder {
        public int id;
        public TextView tv_buy_category;
        public ImageView iv_buy_category_checked;

        public ViewHolder(View view) {
            tv_buy_category = view.findViewById(R.id.tv_buy_sort);
            iv_buy_category_checked = view.findViewById(R.id.iv_buy_sort_checked);
        }
    }
}
