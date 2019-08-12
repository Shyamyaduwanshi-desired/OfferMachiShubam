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
import com.desired.offermachi.retalier.model.ViewOfferModel;
import com.desired.offermachi.retalier.view.activity.RetalierProductActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewOfferDiscountAdapter extends RecyclerView.Adapter<ViewOfferDiscountAdapter.MyViewHolder> {
    private ArrayList<ViewOfferModel> viewOfferModelArrayList;
    private final Context mContext;

    public ViewOfferDiscountAdapter(Context context, ArrayList<ViewOfferModel> viewOfferModelArrayList) {
        this.viewOfferModelArrayList = viewOfferModelArrayList;
        this.mContext = context;
    }
    @Override
    public ViewOfferDiscountAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.retalier_view_offer_activity, parent, false);

        ViewOfferDiscountAdapter.MyViewHolder myViewHolder = new ViewOfferDiscountAdapter.MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewOfferDiscountAdapter.MyViewHolder holder, final int i) {
        final ViewOfferModel viewOfferModel = viewOfferModelArrayList.get(i);
        holder.productname.setText(viewOfferModel.getOffername());
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
    }
    @Override
    public int getItemCount() {
        return viewOfferModelArrayList.size();
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


