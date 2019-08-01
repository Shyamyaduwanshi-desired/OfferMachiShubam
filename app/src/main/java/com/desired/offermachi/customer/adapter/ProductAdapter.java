package com.desired.offermachi.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.model.slider_model;


import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private ArrayList<slider_model> productdatasetcategory;
    private final Context mContext;
    private Object ProductDetailsActivity;

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView productimg;
        TextView productname;


        public MyViewHolder(View itemView) {

            super(itemView);

            this.productimg = (ImageView) itemView.findViewById(R.id.productimage_id);
            this.productname = (TextView) itemView.findViewById(R.id.product_name_id);

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

        productimg.setImageResource(productdatasetcategory.get(listPosition).getImg());
        productname.setText(productdatasetcategory.get(listPosition).getProductname());

    }
    @Override
    public int getItemCount() {
        return productdatasetcategory.size();
    }
}



