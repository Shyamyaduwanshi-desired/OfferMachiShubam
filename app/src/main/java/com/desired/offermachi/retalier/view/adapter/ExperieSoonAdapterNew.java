package com.desired.offermachi.retalier.view.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
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
import com.desired.offermachi.retalier.constant.AppData;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.DealsModelNew;
import com.desired.offermachi.retalier.model.ExpiressoonModel;
import com.desired.offermachi.retalier.model.UserModel;
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

public class ExperieSoonAdapterNew extends RecyclerView.Adapter<ExperieSoonAdapterNew.MyViewHolder>{
    private ArrayList<ExpiressoonModel> selectCategoryModelArrayList;
    private Context mContext;
    private String Favstatus;
    private String idholder;

    AppData appdata;

    public ExperieSoonAdapterNew(Context context, ArrayList<ExpiressoonModel> selectCategoryModelArrayList) {
        this.selectCategoryModelArrayList = selectCategoryModelArrayList;
        this.mContext = context;
        appdata=new AppData();

    }
    @Override
    public ExperieSoonAdapterNew.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.add_deals_layout_new, parent, false);
        ExperieSoonAdapterNew.MyViewHolder myViewHolder = new ExperieSoonAdapterNew.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ExperieSoonAdapterNew.MyViewHolder holder, final int i) {
        final ExpiressoonModel selectCategoryModel=selectCategoryModelArrayList.get(i);
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
        if (selectCategoryModel.getOfferstatus().equals("1")){
            holder.likeimg.setImageResource(R.drawable.ic_like);
        }else{
            holder.likeimg.setImageResource(R.drawable.heart);
        }


        if (selectCategoryModel.getOfferstatus().equals("1")){
            holder.likeimg.setImageResource(R.drawable.ic_like);
        }else{
            holder.likeimg.setImageResource(R.drawable.heart);
        }

//        if (selectCategoryModel.getOfferCouponCodeStatus().equals("1")){
//            holder.productbutton.setText("View Coupon Code");//View Coupon Code
//            holder.productbutton.setTextColor(mContext.getResources().getColor(R.color.black));
//            holder.productbuttonlayout.setBackgroundResource(R.drawable.view_coupon_code_bg);
//        }else if (selectCategoryModel.getOfferCouponCodeStatus().equals("2")){
//            holder.productbutton.setText("Redeemed");
//        }else{
//            holder.productbutton.setText("Get Coupon Code");//Get Coupon Code
//            holder.productbutton.setTextColor(mContext.getResources().getColor(R.color.white));
//            holder.productbuttonlayout.setBackgroundResource(R.drawable.home_coupon_code_bg);
//        }


        Log.e("","store logo= "+selectCategoryModel.getOfferImage());
        if(TextUtils.isEmpty(selectCategoryModel.getOfferImage())||selectCategoryModel.getOfferImage().equals("")){
        }else{
            Picasso.get().load(selectCategoryModel.getOfferImage()).networkPolicy(NetworkPolicy.NO_CACHE)
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
                Intent myIntent = new Intent(mContext, RetalierProductActivity.class);
                myIntent.putExtra("offerid",selectCategoryModel.getId());
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);

            }
        });

        holder.cv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, RetalierProductActivity.class);
                myIntent.putExtra("offerid",selectCategoryModel.getId());
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);

            }
        });



//        holder.productbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UserModel user = SharedPrefManagerLogin.getInstance(mContext).getUser();
//                idholder= user.getId();
//                String couponstatus=selectCategoryModel.getOfferCouponCodeStatus();
//                if (couponstatus.equals("0")){
//                    couponstatus="1";
//                    getcoupon(idholder,selectCategoryModel.getId(),couponstatus);
//
//                }else if (couponstatus.equals("1")){
//                    final Dialog dialog = new Dialog((Activity) v.getContext());
//                    dialog.setContentView(R.layout.coupon_code_activity);
//                    dialog.setTitle("Custom Dialog");
//                    ImageView image=(ImageView)dialog.findViewById(R.id.qr_code_img_id);
//                    if(selectCategoryModel.getOfferqrcodeimage().equals("")){
//                    }else{
//                        Picasso.get().load(selectCategoryModel.getOfferqrcodeimage()).networkPolicy(NetworkPolicy.NO_CACHE)
//                                .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(image);
//                    }
//                    Button button=(Button)dialog.findViewById(R.id.coupon_button_apply_id);
//                    button.setText(selectCategoryModel.getCoupon_code());
//                    TextView textView=(TextView)dialog.findViewById(R.id.coupon_ok_id);
//                    textView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//
//                    });
//
//                    dialog.show();
//
//                }else if (couponstatus.equals("2")){
//
//                }
//            }
//        });

//        holder.rlLike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String fav=selectCategoryModel.getOfferfav();
//                if (fav.equals("0")) {
//                    Favstatus="1";
//                    holder.likeimg.setImageResource(R.drawable.ic_like);
//                    Intent intent = new Intent("Favourite");
//                    intent.putExtra("fav", Favstatus);
//                    intent.putExtra("offerid", selectCategoryModel.getId());
//                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
//                }else if(fav.equals("1")) {
//                    Favstatus="0";
//                    holder.likeimg.setImageResource(R.drawable.heart);
//                    Intent intent = new Intent("Favourite");
//                    intent.putExtra("fav", Favstatus);
//                    intent.putExtra("offerid", selectCategoryModel.getId());
//                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
//                }
//            }
//        });

        holder.iv_share.setOnClickListener(new View.OnClickListener() {
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
        return selectCategoryModelArrayList.size();
    }
    public void setfilter(List<ExpiressoonModel> newlist) {
        selectCategoryModelArrayList = new ArrayList<>();
        selectCategoryModelArrayList.addAll(newlist);
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //   public CardView categorylinear;
        ImageView productimg,likeimg,ivStoreLogo,iv_share;
        TextView productname,productdate/*,offertype*/,tvDsc;
        RelativeLayout productbuttonlayout;
        RelativeLayout rlShare,rlLike;
        Button productbutton ;
        CardView cv_image;
//        AndroidLikeButton ivLikeBtn;

        public MyViewHolder(View itemView) {
            super(itemView);

            productimg = itemView.findViewById(R.id.iv_image);
            productname =  itemView.findViewById(R.id.tv_product_name);
            tvDsc =  itemView.findViewById(R.id.tv_prod_dsc);
            productdate = itemView.findViewById(R.id.tv_prod_date);
            //rlShare = itemView.findViewById(R.id.rl_share);
            //rlLike = itemView.findViewById(R.id.rl_like);
            productbutton=itemView.findViewById(R.id.bt_get_a_code_id);
            productbuttonlayout=itemView.findViewById(R.id.bt_get_acoupon_code);
            cv_image=itemView.findViewById(R.id.cv_image);
            iv_share=itemView.findViewById(R.id.iv_share);

//            offertype=itemView.findViewById(R.id.tv_flat_diss);
            likeimg=itemView.findViewById(R.id.iv_like);
            ivStoreLogo=itemView.findViewById(R.id.iv_icon);
//            ivLikeBtn=itemView.findViewById(R.id.bt_like);
        }
    }

    private void getcoupon(final String id, final String offerid, final String getcoupon){
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_get_coupon_code_data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                        Log.e("home", "response========== "+reader.getString("message") );
                        Intent intent = new Intent("Refresh");
                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

                    }else if(status == 404){
                        Toast.makeText(mContext, ""+reader.getString("message"), Toast.LENGTH_SHORT).show();

                        Log.e("home", "response========== "+reader.getString("message") );
                        //getCoupon.couponerror(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Something went wrong. Please try after some time.", Toast.LENGTH_SHORT).show();
                    // getCoupon.couponfail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Server Error.\\n Please try after some time.", Toast.LENGTH_SHORT).show();
                // getCoupon.couponfail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", id);
                params.put("offer_id",offerid);
                params.put("active", getcoupon);
                Log.e("","Input param= "+params.toString());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(postRequest);
    }

    @Override
    public int getItemViewType(int position) {
        return selectCategoryModelArrayList.size();
    }

}
