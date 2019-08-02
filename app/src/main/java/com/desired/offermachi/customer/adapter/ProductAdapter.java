package com.desired.offermachi.customer.adapter;

import android.app.Activity;
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
import com.desired.offermachi.customer.model.slider_model;
import com.desired.offermachi.customer.ui.ViewAllOfferFollowActivity;
import com.desired.offermachi.customer.ui.ViewStoreOfferActivity;


import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private ArrayList<slider_model> productdatasetcategory;
    private final Context mContext;
    private Object ProductDetailsActivity;

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView productimg;
        TextView productname;
        LinearLayout productlinearunfollow;


        public MyViewHolder(View itemView) {

            super(itemView);

            productimg = (ImageView) itemView.findViewById(R.id.productimage_id);
            productname = (TextView) itemView.findViewById(R.id.product_name_id);
            productlinearunfollow =(LinearLayout)itemView.findViewById(R.id.product_linear_unfollow_id);

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

        holder.productimg.setImageResource(productdatasetcategory.get(listPosition).getImg());
        holder.productname.setText(productdatasetcategory.get(listPosition).getProductname());


        holder.productimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ViewAllOfferFollowActivity.class);
                mContext.startActivity(intent);
            }
        });

        holder.productlinearunfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ViewStoreOfferActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return productdatasetcategory.size();
    }

}



