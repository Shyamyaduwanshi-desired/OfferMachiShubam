package com.desired.offermachi.customer.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.model.NotificationModel;
import com.desired.offermachi.customer.presenter.CustomerDealsOftheDaysPresenter;
import com.desired.offermachi.customer.view.activity.DashBoardActivity;
import com.desired.offermachi.customer.view.fragment.DealsoftheDayFragment;
import com.desired.offermachi.customer.view.fragment.ExclusiveFragment;

import java.util.List;

public class CustomerNotificationAdapter extends RecyclerView.Adapter<CustomerNotificationAdapter.MyViewHolder> {

private List<NotificationModel> modelList;
private Context context;
String Custom_offertype;
private NotiAdapterClick itemClick;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_title, tv_msg;
    public CardView noti_item_card;


    public MyViewHolder(View itemView) {
        super(itemView);
        tv_title = (TextView) itemView.findViewById(R.id.title);
        tv_msg = (TextView) itemView.findViewById(R.id.msg);
        noti_item_card= (CardView)itemView.findViewById(R.id.noti_item_card_id);


    }
}

    public CustomerNotificationAdapter(List<NotificationModel> modelList, Context context, NotiAdapterClick cateClick) {
        this.modelList = modelList;
        this.context = context;
        this.itemClick = cateClick;

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
        CardView noti_item_card = holder.noti_item_card;

        noti_item_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemClick.onNotiClick(position);

//                Custom_offertype=hmBhavModel.getCustom_offertype();
//
//                if (Custom_offertype.equals("0")){
//
//                    Intent intent = new Intent(context, DashBoardActivity.class);
//                    context.startActivity(intent);
//
//                }else if (Custom_offertype.equals("1")){
//
//
//                }else if(Custom_offertype.equals("2")){
//
//                    Intent intent = new Intent(context, ExclusiveFragment.class);
//                    context.startActivity(intent);
//                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public interface NotiAdapterClick{
        void onNotiClick(int position);
    }
}