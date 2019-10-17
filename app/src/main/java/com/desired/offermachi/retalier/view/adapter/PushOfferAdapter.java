package com.desired.offermachi.retalier.view.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.model.DealsModelNew;
import com.desired.offermachi.retalier.model.ViewOfferModel;
import com.desired.offermachi.retalier.view.activity.RetalierProductActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PushOfferAdapter extends RecyclerView.Adapter<PushOfferAdapter.MyViewHolder> {
    private ArrayList<DealsModelNew> viewOfferModelArrayList;
    private final Context mContext;
    int countBACK=0;
    int count=0;
    public PushOfferAdapter(Context context,ArrayList<DealsModelNew> viewOfferModelArrayList) {
        this.viewOfferModelArrayList = viewOfferModelArrayList;
        this.mContext = context;
    }

    @Override
    public PushOfferAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.push_offers_item, parent, false);

        PushOfferAdapter.MyViewHolder myViewHolder = new PushOfferAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final PushOfferAdapter.MyViewHolder holder, final int i) {
        final DealsModelNew viewOfferModel = viewOfferModelArrayList.get(i);

        if(viewOfferModel.getOffername().length()>15)
        {
            holder.productname.setText(viewOfferModel.getOffername().substring(0,15)+"...");
        }
        else
        {
            holder.productname.setText(viewOfferModel.getOffername());
        }

        holder.productdate.setText(viewOfferModel.getOfferenddate());
        holder.offertypename.setText(viewOfferModel.getOffertypename()+" Off "+viewOfferModel.getOffervalue());
        if(viewOfferModel.getOfferImage().equals("")){
        }else{
            Picasso.get().load(viewOfferModel.getOfferImage()).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(holder.productimg);
        }
        holder.productname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, RetalierProductActivity.class);
                myIntent.putExtra("offerid",viewOfferModel.getId());
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);
            }
        });
       holder.productbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("Push");
                intent.putExtra("pushofferid",viewOfferModel.getId());
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

            }
        });

    }
    @Override
    public int getItemCount() {
        return viewOfferModelArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView productimg,likeimg;
        TextView productname,productdate,offertypename;
        Button productbutton;

        public MyViewHolder(View itemView) {
            super(itemView);
            productimg =  itemView.findViewById(R.id.product_image_id);
            productname = itemView.findViewById(R.id.pushname_product_id);
            productdate = itemView.findViewById(R.id.product_date_id);
            offertypename=itemView.findViewById(R.id.offertypename);
            productbutton=itemView.findViewById(R.id.btnaddtodeal);

        }
    }
}



