package com.desired.offermachi.retalier.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.model.DealsModel;
import com.desired.offermachi.retalier.model.ViewOfferModel;
import com.desired.offermachi.retalier.model.retalier_category_model;
import com.desired.offermachi.retalier.view.activity.RetalierProductActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DealsOfDayAdapter extends RecyclerView.Adapter<DealsOfDayAdapter.MyViewHolder> {

    private ArrayList<DealsModel> dealsModelArrayList;
    private final Context mContext;

    public DealsOfDayAdapter(Context context,ArrayList<DealsModel> dealsModelArrayList) {
        this.dealsModelArrayList = dealsModelArrayList;
        this.mContext = context;
    }

    @Override
    public DealsOfDayAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deals_of_the_day_item, parent, false);

        DealsOfDayAdapter.MyViewHolder myViewHolder = new DealsOfDayAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final DealsOfDayAdapter.MyViewHolder holder, final int i) {

        final DealsModel dealsModel = dealsModelArrayList.get(i);

        if(dealsModel.getOffername().length()>15)
        {
            holder.productname.setText(dealsModel.getOffername().substring(0,15)+"...");
        }
        else
        {
            holder.productname.setText(dealsModel.getOffername());
        }

//        holder.productname.setText(dealsModel.getOffername());
        holder.productdate.setText(dealsModel.getOfferenddate());
        holder.offertypename.setText(dealsModel.getOffertypename()+" Off "+dealsModel.getOffervalue());
        if(dealsModel.getOfferImage().equals("")){
        }else{
            Picasso.get().load(dealsModel.getOfferImage()).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(holder.productimg);
        }
        holder.productname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, RetalierProductActivity.class);
                myIntent.putExtra("offerid",dealsModel.getId());
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return dealsModelArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView productimg;
        TextView productname,productdate,offertypename;

        public MyViewHolder(View itemView) {
            super(itemView);
            productimg = (ImageView) itemView.findViewById(R.id.product_image_id);
            productname = (TextView) itemView.findViewById(R.id.pushname_product_id);
            offertypename=itemView.findViewById(R.id.offertypename);
            productdate = (TextView) itemView.findViewById(R.id.product_date_id);
        }
    }
}

