package com.example.xmfy.yzubookshop.module.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.Delivery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xmfy on 2018/1/16.
 */
public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.ViewHolder> {

    private Context context;
    private List<Delivery> deliveryList;
    public List<RadioButton> radioButtonList;
    private LoadFinishedListener loadFinishedListener;

    public DeliveryAdapter(Context context, List<Delivery> deliveryList) {
        this.context = context;
        this.deliveryList = deliveryList;
        radioButtonList = new ArrayList<>();
    }

    public void setLoadFinishedListener(LoadFinishedListener loadFinishedListener) {
        this.loadFinishedListener = loadFinishedListener;
    }

    public void updateData(List<Delivery> data) {
        this.deliveryList = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_delivery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        Delivery d = deliveryList.get(i);
        holder.tv_delivery_receiver.setText(d.getReceiver());
        holder.tv_delivery_phone.setText(d.getPhone());
        holder.tv_delivery_location.setText(d.getProvince() + " " + d.getCity() + " " + d.getDistrict() + " " + d.getLocation());
        holder.rb_delivery_default.setChecked(d.getDefaults() == 1);
        holder.rb_delivery_default.setTag(R.id.delivery_id, d.getId());
        holder.rb_delivery_default.setTag(R.id.delivery_account, d.getAccount());
        if (radioButtonList.size() < deliveryList.size())
            radioButtonList.add(holder.rb_delivery_default);
        if (i == deliveryList.size()-1)
            loadFinishedListener.onLoadFinished();
    }


    @Override
    public int getItemCount() {
        return deliveryList == null ? 0 : deliveryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_delivery_receiver;
        TextView tv_delivery_phone;
        TextView tv_delivery_location;
        RadioButton rb_delivery_default;
        TextView tv_delivery_edit;
        TextView tv_delivery_delete;

        public ViewHolder(View v) {
            super(v);
            tv_delivery_receiver = v.findViewById(R.id.tv_delivery_receiver);
            tv_delivery_phone = v.findViewById(R.id.tv_delivery_phone);
            tv_delivery_location = v.findViewById(R.id.tv_delivery_location);
            rb_delivery_default = v.findViewById(R.id.rb_delivery_default);
            tv_delivery_edit = v.findViewById(R.id.tv_delivery_edit);
            tv_delivery_delete = v.findViewById(R.id.tv_delivery_delete);
        }
    }

    interface LoadFinishedListener{
        void onLoadFinished();
    }

}
