package com.example.xmfy.yzubookshop.module.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.model.Delivery;

import java.util.List;

/**
 * Created by xmfy on 2018/1/16.
 */
public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.ViewHolder> {

    private Context context;
    private List<Delivery> deliveryList;
    private OnClickOperationListener onClickOperationListener;
    private OnClickCheckBoxListener onClickCheckBoxListener;

    DeliveryAdapter(Context context, List<Delivery> deliveryList) {
        this.context = context;
        this.deliveryList = deliveryList;
    }

    void setOnClickOperationListerner(OnClickOperationListener onClickOperationListener) {
        this.onClickOperationListener = onClickOperationListener;
    }

    void setOnClickCheckBoxListener(OnClickCheckBoxListener onClickCheckBoxListener) {
        this.onClickCheckBoxListener = onClickCheckBoxListener;
    }

    void updateData(List<Delivery> data) {
        this.deliveryList = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_delivery, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int i) {
        Delivery d = deliveryList.get(i);
        holder.tv_delivery_receiver.setText(d.getReceiver());
        holder.tv_delivery_phone.setText(d.getPhone());
        holder.tv_delivery_location.setText(d.getProvince() + " " + d.getCity() + " " + d.getDistrict() + " " + d.getLocation());
        holder.rb_delivery_default.setChecked(d.getDefaults() == 1);
        holder.rb_delivery_default.setTag(R.id.delivery_id, d.getId());
        holder.rb_delivery_default.setTag(R.id.delivery_account, d.getAccount());
        if (onClickOperationListener != null){
            holder.tv_delivery_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickOperationListener.click(1, i);
                }
            });

            holder.tv_delivery_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickOperationListener.click(2, i);
                }
            });
        }

        if (onClickCheckBoxListener != null){
            holder.rb_delivery_default.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b)
                        onClickCheckBoxListener.click(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return deliveryList == null ? 0 : deliveryList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_delivery_receiver;
        TextView tv_delivery_phone;
        TextView tv_delivery_location;
        RadioButton rb_delivery_default;
        TextView tv_delivery_edit;
        TextView tv_delivery_delete;

        ViewHolder(View v) {
            super(v);
            tv_delivery_receiver = v.findViewById(R.id.tv_delivery_receiver);
            tv_delivery_phone = v.findViewById(R.id.tv_delivery_phone);
            tv_delivery_location = v.findViewById(R.id.tv_delivery_location);
            rb_delivery_default = v.findViewById(R.id.rb_delivery_default);
            tv_delivery_edit = v.findViewById(R.id.tv_delivery_edit);
            tv_delivery_delete = v.findViewById(R.id.tv_delivery_delete);
        }
    }

    public interface OnClickOperationListener{
        void click(int type, int position);
    }

    public interface OnClickCheckBoxListener{
        void click(int position);
    }

}
