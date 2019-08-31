package com.desired.offermachi.customer.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.model.filter_show_model;
import com.desired.offermachi.customer.view.activity.DashBoardActivity;

import java.util.ArrayList;

public class FilterShowAdapter extends RecyclerView.Adapter<FilterShowAdapter.MyViewHolder> {

    private ArrayList<filter_show_model> filterlistdataset;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout categorylinear;
        ImageView productimg;
        TextView productname,unfolltext;

        public MyViewHolder(View itemView) {

            super(itemView);

            this.productimg = (ImageView) itemView.findViewById(R.id.filter_image_id);
            this.productname = (TextView) itemView.findViewById(R.id.filter_name_id);
            this.categorylinear=(LinearLayout)itemView.findViewById(R.id.linear_electronics_id);
        }
    }
    public FilterShowAdapter(Context context, ArrayList<filter_show_model> data) {
        this.filterlistdataset = data;
        this.mContext = context;
    }

    @Override
    public FilterShowAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_filter_activity, parent, false);

        FilterShowAdapter.MyViewHolder myViewHolder = new FilterShowAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final FilterShowAdapter.MyViewHolder holder, final int listPosition) {

        ImageView productimg = holder.productimg;
        TextView productname = holder.productname;
        LinearLayout categorylinear=holder.categorylinear;

        productimg.setImageResource(filterlistdataset.get(listPosition).getImg());
        productname.setText(filterlistdataset.get(listPosition).getProductname());

        categorylinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, DashBoardActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return filterlistdataset.size();
    }
}



