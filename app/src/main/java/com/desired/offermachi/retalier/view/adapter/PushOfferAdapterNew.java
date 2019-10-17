package com.desired.offermachi.retalier.view.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.view.activity.ProductActivity;
import com.desired.offermachi.retalier.constant.AppData;
import com.desired.offermachi.retalier.model.DealsModelNew;
import com.desired.offermachi.retalier.model.ViewOfferModel;
import com.desired.offermachi.retalier.view.activity.RetalierProductActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PushOfferAdapterNew extends RecyclerView.Adapter<PushOfferAdapterNew.MyViewHolder>{
    private ArrayList<DealsModelNew> pushofferdatalist;
    private final Context mContext;
    int countBACK=0;
    int count=0;
    private String Favstatus;
    private String idholder;
    AppData appdata;

    public PushOfferAdapterNew(Context context, ArrayList<DealsModelNew> pushofferdatalist) {
        this.pushofferdatalist = pushofferdatalist;
        this.mContext = context;
        appdata=new AppData();

    }
    @Override
    public PushOfferAdapterNew.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.push_offer_activity_new, parent, false);
        PushOfferAdapterNew.MyViewHolder myViewHolder = new PushOfferAdapterNew.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final PushOfferAdapterNew.MyViewHolder holder, final int i) {
        final DealsModelNew selectCategoryModel=pushofferdatalist.get(i);
        holder.productname.setText(selectCategoryModel.getOffername());
        holder.productdate.setText("Exp on: "+appdata.ConvertDate4(selectCategoryModel.getOfferenddate()));

        if(selectCategoryModel.getOfferdescription().length()>30)
        {
            holder.tvDsc.setText(selectCategoryModel.getOfferdescription().substring(0,30)+"...");
        }
        else
        {
            holder.tvDsc.setText(selectCategoryModel.getOfferdescription());
        }

        if(selectCategoryModel.getOfferImage().equals("")){
        }else{

            Picasso.get().load(selectCategoryModel.getOfferImage()).placeholder(R.drawable.ic_broken).into(holder.productimg);
        }


        Log.e("","store logo= "+selectCategoryModel.getStoreLogo());
        if(TextUtils.isEmpty(selectCategoryModel.getStoreLogo())||selectCategoryModel.getStoreLogo().equals("")){
        }else{
            Picasso.get().load(selectCategoryModel.getStoreLogo()).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.shortlogo).into(holder.ivStoreLogo);
        }
        holder.productname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, RetalierProductActivity.class);
                myIntent.putExtra("offerid",selectCategoryModel.getId());
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);
            }
        });
        holder.productbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("Push");
                intent.putExtra("pushofferid",selectCategoryModel.getId());
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

            }
        });



        holder.rlShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "OfferMachi");
                String shareMessage= "\nGet regular updates on the best deals, cashback offers, and discount coupons across retail stores in your location. Get it for free at.\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + "com.desired.offermachi"  +"\n\n";
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                sendIntent.setType("text/plain");
                mContext.startActivity(Intent.createChooser(sendIntent, "choose one"));


            }
        });
    }
    @Override
    public int getItemCount() {
        return pushofferdatalist.size();
    }
    public void setfilter(List<DealsModelNew> newlist) {
        pushofferdatalist = new ArrayList<>();
        pushofferdatalist.addAll(newlist);
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //   public CardView categorylinear;
        ImageView productimg,likeimg,ivStoreLogo;
        TextView productname,productdate/*,offertype*/,tvDsc;
        Button productbutton;
        RelativeLayout productbuttonlayout;
        RelativeLayout rlShare,rlLike;




        public MyViewHolder(View itemView) {
            super(itemView);

            productimg = itemView.findViewById(R.id.iv_image);
            productname =  itemView.findViewById(R.id.tv_product_name);
            tvDsc =  itemView.findViewById(R.id.tv_prod_dsc);
            productdate = itemView.findViewById(R.id.tv_prod_date);
            rlShare = itemView.findViewById(R.id.rl_share);

            productbutton=itemView.findViewById(R.id.bt_get_a_code);
            productbuttonlayout=itemView.findViewById(R.id.rl_get_coupon_layout);
            likeimg=itemView.findViewById(R.id.iv_like);
            ivStoreLogo=itemView.findViewById(R.id.iv_icon);
        }
    }



    @Override
    public int getItemViewType(int position) {
        return pushofferdatalist.size();
    }

}
