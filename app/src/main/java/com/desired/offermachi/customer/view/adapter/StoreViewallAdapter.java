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
import com.desired.offermachi.customer.model.StoreModel;
import com.desired.offermachi.customer.view.activity.ProductActivity;
import com.desired.offermachi.customer.view.activity.ViewAllOfferFollowActivity;
import com.desired.offermachi.customer.view.activity.ViewStoreOfferActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StoreViewallAdapter extends RecyclerView.Adapter<StoreViewallAdapter.MyViewHolder> {

    private ArrayList<StoreModel> storeModelArrayList;
    private Context mContext;


    public StoreViewallAdapter(Context context,ArrayList<StoreModel> storeModelArrayList) {
        this.mContext = context;
        this.storeModelArrayList = storeModelArrayList;
    }
    @Override
    public StoreViewallAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_list_item, parent, false);
        StoreViewallAdapter.MyViewHolder myViewHolder = new StoreViewallAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final StoreViewallAdapter.MyViewHolder holder, final int listPosition) {
        final StoreModel storeModel=storeModelArrayList.get(listPosition);
        holder.storename.setText(storeModel.getStorename());
        if(storeModel.getStoreimage().equals("")){
        }else{
            Picasso.get().load(storeModel.getStoreimage()).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(holder.storeimage);
        }
        holder.storeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, ProductActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);

            }
        });

        holder.btnviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, ViewStoreOfferActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);
            }
        });
        holder.btnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    @Override
    public int getItemCount() {
        return storeModelArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView storeimage;
        TextView storename;
        TextView btnfollow,btnviewall;

        public MyViewHolder(View itemView) {
            super(itemView);
            storeimage = (ImageView) itemView.findViewById(R.id.storeimage);
            storename = (TextView) itemView.findViewById(R.id.storename);
            btnfollow = (TextView) itemView.findViewById(R.id.btnfollow);
            btnviewall = (TextView) itemView.findViewById(R.id.btnviewall);
        }
    }
}

