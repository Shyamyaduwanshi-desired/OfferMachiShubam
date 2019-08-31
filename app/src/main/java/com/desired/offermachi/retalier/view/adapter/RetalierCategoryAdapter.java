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
import com.desired.offermachi.retalier.model.retalier_category_model;
import com.desired.offermachi.retalier.view.activity.RetalierProductActivity;
import com.desired.offermachi.retalier.view.activity.RetalierViewOfferActivity;

import java.util.ArrayList;

public class RetalierCategoryAdapter extends RecyclerView.Adapter<RetalierCategoryAdapter.MyViewHolder> {

    private ArrayList<retalier_category_model> categorylistdataset;
    private final Context mContext;
    private Object ProductDetailsActivity;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

      //  public CardView categorylinear;
        ImageView productimg,likeimg;
        TextView productname,productdate;
        Button productbutton;


        public MyViewHolder(View itemView) {
            super(itemView);
            //this.categorylinear=(CardView)itemView.findViewById(R.id.card_linear_id);
            this.productimg = (ImageView) itemView.findViewById(R.id.product_image_id);
            this.productname = (TextView) itemView.findViewById(R.id.pushname_product_id);
            this.productdate = (TextView) itemView.findViewById(R.id.product_date_id);
            this.productbutton=(Button)itemView.findViewById(R.id.btnaddtodeal);
        }

    }

    public RetalierCategoryAdapter(Context context,ArrayList<retalier_category_model> data) {
        this.categorylistdataset = data;
        this.mContext = context;
    }

    @Override
    public RetalierCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.retalier_category_item, parent, false);

        RetalierCategoryAdapter.MyViewHolder myViewHolder = new RetalierCategoryAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final RetalierCategoryAdapter.MyViewHolder holder, final int listPosition) {

       // CardView categorylinear= (CardView) holder.categorylinear;
        ImageView productimg = holder.productimg;
        TextView productname = holder.productname;
        TextView productdate = holder.productdate;
        Button productbutton =(Button)holder.productbutton;
        final ImageView likeimg=(ImageView)holder.likeimg;

        productimg.setImageResource(categorylistdataset.get(listPosition).getImg());
        productname.setText(categorylistdataset.get(listPosition).getProductname());
        productdate.setText(categorylistdataset.get(listPosition).getProductdate());
        productname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, RetalierProductActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);
            }
        });
        productbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, RetalierViewOfferActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return categorylistdataset.size();
    }
}
