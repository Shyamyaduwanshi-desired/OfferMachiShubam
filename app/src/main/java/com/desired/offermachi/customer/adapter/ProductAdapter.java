package com.desired.offermachi.customer.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.desired.offermachi.R;
import com.desired.offermachi.customer.model.category_model;
import com.desired.offermachi.customer.model.slider_model;
import com.desired.offermachi.customer.ui.ProductActivity;
import com.desired.offermachi.customer.ui.ViewAllOfferFollowActivity;
import com.desired.offermachi.customer.ui.ViewStoreOfferActivity;


import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private ArrayList<slider_model> productdatasetcategory;
    private final Context mContext;
    private Object ViewStoreOfferActivity;


    public static class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView productimg;
        TextView productname;
        LinearLayout producttext;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.productimg = (ImageView) itemView.findViewById(R.id.productimage_id);
            this.productname = (TextView) itemView.findViewById(R.id.product_name_id);
            this.producttext=(LinearLayout)itemView.findViewById(R.id.product_linear_unfollow_text_id);
        }
    }
    public ProductAdapter(Context context,ArrayList<slider_model> data) {
        this.productdatasetcategory = data;
        this.mContext = context;
    }
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trending_deals_content_activity, parent, false);

        ProductAdapter.MyViewHolder myViewHolder = new ProductAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ProductAdapter.MyViewHolder holder, final int listPosition) {

        ImageView productimg = holder.productimg;
        TextView productname = holder.productname;
        LinearLayout producttext =holder.producttext;


        productimg.setImageResource(productdatasetcategory.get(listPosition).getImg());
        productname.setText(productdatasetcategory.get(listPosition).getProductname());

        productimg.setImageResource(productdatasetcategory.get(listPosition).getImg());
        productname.setText(productdatasetcategory.get(listPosition).getProductname());

        productimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(mContext, ProductActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);

            }
        });

        producttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, ViewStoreOfferActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return productdatasetcategory.size();
    }
}
