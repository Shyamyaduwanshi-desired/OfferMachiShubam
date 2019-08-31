package com.desired.offermachi.customer.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.model.NotificationModel;

import java.util.List;

public class CustomerNotificationAdapter extends RecyclerView.Adapter<CustomerNotificationAdapter.MyViewHolder> {

private List<NotificationModel> modelList;
private Context context;


public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_title, tv_msg;


    public MyViewHolder(View itemView) {
        super(itemView);
        tv_title = (TextView) itemView.findViewById(R.id.title);
        tv_msg = (TextView) itemView.findViewById(R.id.msg);


    }
}

    public CustomerNotificationAdapter(List<NotificationModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @Override
    public CustomerNotificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_list, parent, false);
        return new CustomerNotificationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomerNotificationAdapter.MyViewHolder holder, int position) {
        NotificationModel hmBhavModel = modelList.get(position);
        holder.tv_title.setText(hmBhavModel.getTitle());
        holder.tv_msg.setText(hmBhavModel.getMsg());

        //Picasso.get().load(hmBhavModel.getProductImage()).into(holder.iv_productImage);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}