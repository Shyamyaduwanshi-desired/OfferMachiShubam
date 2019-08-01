package com.desired.offermachi.customer.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.model.Trending_model;


import java.util.ArrayList;

public class TrendingDealsAdapter extends RecyclerView.Adapter<TrendingDealsAdapter.MyViewHolder> {

    private ArrayList<Trending_model> trendingdataset;
    private final Context mContext;
    private Object ProductDetailsActivity;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView categorylinear;
        ImageView productimg,likeimg;
        TextView productname,productdetails,productdate,productshare;
        Button addtocart;

        public MyViewHolder(View itemView) {

            super(itemView);

        }

    }

    public TrendingDealsAdapter(Context context,ArrayList<Trending_model> data) {
        this.trendingdataset = data;
        this.mContext = context;
    }

    @Override
    public TrendingDealsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_activity, parent, false);

        TrendingDealsAdapter.MyViewHolder myViewHolder = new TrendingDealsAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final TrendingDealsAdapter.MyViewHolder holder, final int listPosition) {

        CardView categorylinear= (CardView) holder.categorylinear;
        ImageView productimg = holder.productimg;
        TextView productname = holder.productname;
        TextView productdate = holder.productdate;
        TextView productshare = holder.productshare;
        TextView productdetails = holder.productdetails;
        final ImageView likeimg=(ImageView)holder.likeimg;
        Button addtocart =(Button)holder.addtocart;


        productimg.setImageResource(trendingdataset.get(listPosition).getImg());
        productname.setText(trendingdataset.get(listPosition).getProductname());
        productdetails.setText(trendingdataset.get(listPosition).getProductdetails());
        productdate.setText(trendingdataset.get(listPosition).getProductdate());
        productshare.setText(trendingdataset.get(listPosition).getProductshare());

    }
    @Override
    public int getItemCount() {
        return trendingdataset.size();
    }
}


