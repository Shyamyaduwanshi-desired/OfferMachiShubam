package com.desired.offermachi.customer.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import com.desired.offermachi.R;
import com.desired.offermachi.customer.model.days_model;

import java.util.ArrayList;

public class DaysDistrubAdapter extends RecyclerView.Adapter<DaysDistrubAdapter.MyViewHolder> {

    private ArrayList<days_model> daysdata;
    private final Context mContext;
    private static CheckBox lastChecked = null;
    private static int lastCheckedPos = 0;

    private DaysAdapterClick itemClick;
    public DaysDistrubAdapter(Context context, ArrayList<days_model> data, DaysAdapterClick cateClick) {
        this.daysdata = data;
        this.mContext = context;
        this.itemClick = cateClick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adptr_disturb_second_layout, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textView = holder.textView;
        textView.setText(daysdata.get(listPosition).getTittle());
        if(daysdata.get(listPosition).isSelected())
        {
            holder.ivCheck.setImageResource(R.drawable.ic_check_box_40dp);
        }
        else
        {
            holder.ivCheck.setImageResource(R.drawable.ic_un_check_box_40dp);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onDaysClick(listPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return daysdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

//        CheckBox checkBox;
        TextView textView;
        ImageView ivCheck;

        public MyViewHolder(View itemView) {

            super(itemView);
//            this.checkBox=(CheckBox)itemView.findViewById(R.id.days_check_box_id);
            this.ivCheck= itemView.findViewById(R.id.iv_check);
            this.textView =(TextView)itemView.findViewById(R.id.days_textview_id);
        }
    }

    public interface DaysAdapterClick{
        void onDaysClick(int position);
    }
}


