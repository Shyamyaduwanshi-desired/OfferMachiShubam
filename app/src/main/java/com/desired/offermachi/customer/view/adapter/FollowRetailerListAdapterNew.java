package com.desired.offermachi.customer.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.model.FollowStoreModel;
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.customer.view.activity.ViewStoreOfferActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class FollowRetailerListAdapterNew extends RecyclerView.Adapter<FollowRetailerListAdapterNew.MyViewHolder> {

    private ArrayList<FollowStoreModel> storeModelArrayList,arSearchItem;
    private Context mContext;
    private String followstatus;
    String status;

    public FollowRetailerListAdapterNew(Context context, ArrayList<FollowStoreModel> storeModelArrayList) {
        this.mContext = context;
        this.storeModelArrayList = storeModelArrayList;
        this.arSearchItem = new ArrayList<>();
        this.arSearchItem.addAll(storeModelArrayList);

    }
    @Override
    public FollowRetailerListAdapterNew.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_retailor_store_list, parent, false);
        FollowRetailerListAdapterNew.MyViewHolder myViewHolder = new FollowRetailerListAdapterNew.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final FollowRetailerListAdapterNew.MyViewHolder holder, final int listPosition) {
        final FollowStoreModel storeModel=storeModelArrayList.get(listPosition);
        holder.storename.setText(storeModel.getStorename());
//        holder.storename2.setText(storeModel.getStorename());
        if(storeModel.getStoreimage().equals("")){
        }else{
            Picasso.get().load(storeModel.getStoreimage()).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(holder.storeimage);
        }
        /*if(storeModel.getStoreimage().equals("")){
        }else{
            Picasso.get().load(storeModel.getStoreimage()).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(holder.storeimagethumb);
        }*/
        status=storeModel.getStoreFav();
        if (status.equals("1")){
            holder.btnfollow.setText("Unfollow");
        }else if (status.equals("0")){
            holder.btnfollow.setText("Follow");
        }
    /*   holder.storeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, ProductActivity.class);
                myIntent.putExtra("offerid",storeModel.getId());
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);

            }
        });*/

        holder.btnviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, ViewStoreOfferActivity.class);
                myIntent.putExtra("storeid",storeModel.getId());
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);
            }
        });
        holder.btnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status=storeModel.getStoreFav();
                if (status.equals("0")){
                    followstatus="1";
                    Intent intent=new Intent("StoreFollow");
                    intent.putExtra("storeid",storeModel.getId());
                    intent.putExtra("followstatus",followstatus);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                }else if (status.equals("1")){
                    followstatus="0";
                    Intent intent=new Intent("StoreFollow");
                    intent.putExtra("storeid",storeModel.getId());
                    intent.putExtra("followstatus",followstatus);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return storeModelArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView storeimage/*,storeimagethumb*/;
        TextView storename/*,storename2*/;
        TextView btnfollow,btnviewall;
        //  LinearLayout producttext;

        public MyViewHolder(View itemView) {
            super(itemView);
            storeimage = (ImageView) itemView.findViewById(R.id.storeimage);
//            storeimagethumb = (ImageView) itemView.findViewById(R.id.storelogothumb);
            storename = (TextView) itemView.findViewById(R.id.storename);
//            storename2 = (TextView) itemView.findViewById(R.id.storename2);
            btnfollow = (TextView) itemView.findViewById(R.id.btnfollow);
            btnviewall = (TextView) itemView.findViewById(R.id.btnviewall);
            //producttext=(LinearLayout)itemView.findViewById(R.id.product_linear_unfollow_text_id);
        }
    }
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        storeModelArrayList.clear();
        if (charText.length() == 0) {
            storeModelArrayList.addAll(arSearchItem);
        } else {
            for (FollowStoreModel wp : arSearchItem) {
                if (wp.getStorename().toLowerCase(Locale.getDefault()).contains(charText))
//                if (wp.getCatName().startsWith(charText))
                {
                    storeModelArrayList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}

