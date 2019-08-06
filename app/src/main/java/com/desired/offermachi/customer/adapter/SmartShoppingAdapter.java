package com.desired.offermachi.customer.adapter;

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
import com.desired.offermachi.customer.model.Category_list_model;
import com.desired.offermachi.customer.model.smart_shopping_model;
import com.desired.offermachi.customer.ui.DashBoardActivity;
import com.desired.offermachi.customer.ui.SmartShoppingRemoveActivity;

import java.util.ArrayList;

public class SmartShoppingAdapter extends RecyclerView.Adapter<SmartShoppingAdapter.MyViewHolder> {

    private ArrayList<smart_shopping_model> categorylistdataset;
    private final Context mContext;
    private Object ProductDetailsActivity;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout categorylinear;
        ImageView productimg;
        TextView productname,unfolltext;

        public MyViewHolder(View itemView) {

            super(itemView);

            this.productimg = (ImageView) itemView.findViewById(R.id.category_image_id);
            this.productname = (TextView) itemView.findViewById(R.id.category_name_id);
            this.unfolltext=(TextView)itemView.findViewById(R.id.unfollowtext);
            this.categorylinear=(LinearLayout)itemView.findViewById(R.id.linear_category_id);
        }
    }
    public SmartShoppingAdapter(Context context, ArrayList<smart_shopping_model> data) {
        this.categorylistdataset = data;
        this.mContext = context;
    }

    @Override
    public SmartShoppingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_activity, parent, false);

        SmartShoppingAdapter.MyViewHolder myViewHolder = new SmartShoppingAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final SmartShoppingAdapter.MyViewHolder holder, final int listPosition) {

        ImageView productimg = holder.productimg;
        TextView productname = holder.productname;
        TextView unfolltext =holder.unfolltext;
        LinearLayout categorylinear=holder.categorylinear;

        productimg.setImageResource(categorylistdataset.get(listPosition).getImg());
        productname.setText(categorylistdataset.get(listPosition).getProductname());

        categorylinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, SmartShoppingRemoveActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return categorylistdataset.size();
    }
}


