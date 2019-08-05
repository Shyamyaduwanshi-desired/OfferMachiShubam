package com.desired.offermachi.customer.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.model.category_model;
import com.desired.offermachi.customer.ui.ProductActivity;


import java.util.ArrayList;

public class CategoryAdapter  extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private ArrayList<category_model> productdataset;
    private final Context mContext;
    private Object ProductDetailsActivity;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView categorylinear;
        ImageView productimg,likeimg;
        TextView productname,productdate,productshare;
        Button productbutton;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.categorylinear=(CardView)itemView.findViewById(R.id.card_linear_id);
            this.productimg = (ImageView) itemView.findViewById(R.id.product_image_id);
            this.productname = (TextView) itemView.findViewById(R.id.name_product_id);
            this.productdate = (TextView) itemView.findViewById(R.id.product_date_id);
            this.productshare = (TextView) itemView.findViewById(R.id.product_share_id);
            this.productbutton=(Button)itemView.findViewById(R.id.product_button_id);
            this.likeimg=(ImageView)itemView.findViewById(R.id.heart_image_id);
        }
    }

    public CategoryAdapter(Context context,ArrayList<category_model> data) {
        this.productdataset = data;
        this.mContext = context;
    }

    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item_activity, parent, false);

        CategoryAdapter.MyViewHolder myViewHolder = new CategoryAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final CategoryAdapter.MyViewHolder holder, final int listPosition) {

        CardView categorylinear= (CardView) holder.categorylinear;
        ImageView productimg = holder.productimg;
        TextView productname = holder.productname;
        TextView productdate = holder.productdate;
        TextView productshare = holder.productshare;
        Button productbutton =(Button)holder.productbutton;
        final ImageView likeimg=(ImageView)holder.likeimg;

        productimg.setImageResource(productdataset.get(listPosition).getImg());
        productname.setText(productdataset.get(listPosition).getProductname());
        productdate.setText(productdataset.get(listPosition).getProductdate());
        productshare.setText(productdataset.get(listPosition).getProductshare());

        productname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(mContext, ProductActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);

            }
        });
        productbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog((Activity) v.getContext());
                dialog.setContentView(R.layout.coupon_code_activity);
                dialog.setTitle("Custom Dialog");

                TextView text1 = (TextView) dialog.findViewById(R.id.code_text_id);
                ImageView image=(ImageView)dialog.findViewById(R.id.qr_code_img_id);
                LinearLayout linaer=(LinearLayout)dialog.findViewById(R.id.or_linear_id);
                TextView couponcodetext=(TextView)dialog.findViewById(R.id.couponcode_text_id);
                Button button=(Button)dialog.findViewById(R.id.coupon_button_apply_id);

                TextView textView=(TextView)dialog.findViewById(R.id.coupon_ok_id);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Dialog dialog = new Dialog((Activity) v.getContext());
                        dialog.setContentView(R.layout.share_item_activity);
                        dialog.setTitle("Custom Dialog");
                        LinearLayout text1 = (LinearLayout) dialog.findViewById(R.id.message_linear_id);
                        LinearLayout text2 = (LinearLayout) dialog.findViewById(R.id.whatsapp_linear_id);
                        LinearLayout text3 = (LinearLayout) dialog.findViewById(R.id.facebook_linear_id);
                        dialog.show();

                    }

                });

                dialog.show();

            }
        });

        likeimg.setOnClickListener(new View.OnClickListener() {
            String wish = "0";
            @Override
            public void onClick(View v) {

                if (wish.equals("0")) {
                    wish = "1";
                    likeimg.setImageResource(R.drawable.heart);
                }else if (wish.equals("1")) {

                    wish = "0";
                    likeimg.setImageResource(R.drawable.ic_like);

                }
            }
        });

        productshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog((Activity) v.getContext());
                dialog.setContentView(R.layout.share_item_activity);
                dialog.setTitle("Custom Dialog");
                LinearLayout message = (LinearLayout) dialog.findViewById(R.id.message_linear_id);
                LinearLayout whatsapp = (LinearLayout) dialog.findViewById(R.id.whatsapp_linear_id);
                LinearLayout facebook=(LinearLayout) dialog.findViewById(R.id.facebook_linear_id);
                dialog.show();
            }
        });

    }
    @Override
    public int getItemCount() {
        return productdataset.size();
    }
}
