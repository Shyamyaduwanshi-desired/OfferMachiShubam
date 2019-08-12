package com.desired.offermachi.retalier.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
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
import com.desired.offermachi.retalier.view.activity.RetalierProductActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AddDealsofDayAdapter  extends RecyclerView.Adapter<AddDealsofDayAdapter.MyViewHolder> {

    private ArrayList<DealsModel> dealsModelArrayList;
    private final Context mContext;

    public AddDealsofDayAdapter(Context context,ArrayList<DealsModel> dealsModelArrayList) {
        this.dealsModelArrayList = dealsModelArrayList;
        this.mContext = context;
    }

    @Override
    public AddDealsofDayAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.retalier_category_item, parent, false);

        AddDealsofDayAdapter.MyViewHolder myViewHolder = new AddDealsofDayAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final AddDealsofDayAdapter.MyViewHolder holder, final int i) {

        final DealsModel dealsModel = dealsModelArrayList.get(i);
        holder.productname.setText(dealsModel.getOffername());
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
        holder.productbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("Offer");
                intent.putExtra("offerid",dealsModel.getId());
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return dealsModelArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView productimg;
        Button productbutton;
        TextView productname,productdate,offertypename;

        public MyViewHolder(View itemView) {
            super(itemView);
            productimg = (ImageView) itemView.findViewById(R.id.product_image_id);
            productname = (TextView) itemView.findViewById(R.id.pushname_product_id);
            offertypename=itemView.findViewById(R.id.offertypename);
            productdate = (TextView) itemView.findViewById(R.id.product_date_id);
            productbutton=itemView.findViewById(R.id.btnaddtodeal);
        }
    }
}

