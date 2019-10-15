package com.desired.offermachi.retalier.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.desired.offermachi.R;
import com.desired.offermachi.retalier.model.ExpiressoonModel;
import com.desired.offermachi.retalier.view.activity.RetalierProductActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExpiressoonAdapter extends RecyclerView.Adapter<ExpiressoonAdapter.MyViewHolder> {

    private ArrayList<ExpiressoonModel> expiresModelArrayList;
    private final Context mContext;

    public ExpiressoonAdapter(Context context,ArrayList<ExpiressoonModel> expiresModelArrayList) {
        this.expiresModelArrayList = expiresModelArrayList;
        this.mContext = context;
    }

    @Override
    public ExpiressoonAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deals_of_the_day_item, parent, false);

        ExpiressoonAdapter.MyViewHolder myViewHolder = new ExpiressoonAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ExpiressoonAdapter.MyViewHolder holder, final int i) {
        try {
            final ExpiressoonModel expiressoonModel = expiresModelArrayList.get(i);

            if(expiressoonModel.getOffername().length()>15)
            {
                holder.productname.setText(expiressoonModel.getOffername().substring(0,15)+"...");
            }
            else
            {
                holder.productname.setText(expiressoonModel.getOffername());
            }

//        holder.productname.setText(dealsModel.getOffername());
            holder.productdate.setText(expiressoonModel.getOfferenddate());
            holder.offertypename.setText(expiressoonModel.getOffertypename()+" Off "+expiressoonModel.getOffervalue());
            if(expiressoonModel.getOfferImage().equals("")){
            }else{
                Picasso.get().load(expiressoonModel.getOfferImage()).networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(holder.productimg);
            }
            holder.productname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(mContext, RetalierProductActivity.class);
                    myIntent.putExtra("offerid",expiressoonModel.getId());
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(myIntent);
                }
            });
        }catch (Exception e){e.printStackTrace();}


    }
    @Override
    public int getItemCount() {
        return expiresModelArrayList.size();
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


