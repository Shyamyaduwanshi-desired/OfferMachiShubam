package com.desired.offermachi.customer.view.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.BuildConfig;
import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.model.category_model;
import com.desired.offermachi.customer.presenter.GetCouponPresenter;
import com.desired.offermachi.customer.view.activity.DashBoardActivity;
import com.desired.offermachi.customer.view.activity.ProductActivity;
import com.desired.offermachi.customer.view.fragment.HomeFragment;
import com.desired.offermachi.retalier.constant.AppData;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerTrendingAdapter extends RecyclerView.Adapter<CustomerTrendingAdapter.MyViewHolder>{
    private ArrayList<SelectCategoryModel> selectCategoryModelArrayList;
    private Context mContext;
    private String Favstatus;
    private String idholder;


    public CustomerTrendingAdapter(Context context, ArrayList<SelectCategoryModel> selectCategoryModelArrayList) {
        this.selectCategoryModelArrayList = selectCategoryModelArrayList;
        this.mContext = context;
    }

    @Override
    public CustomerTrendingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_activity, parent, false);
        CustomerTrendingAdapter.MyViewHolder myViewHolder = new CustomerTrendingAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomerTrendingAdapter.MyViewHolder holder, final int i) {
        final SelectCategoryModel selectCategoryModel=selectCategoryModelArrayList.get(i);
        holder.productname.setText(selectCategoryModel.getOffername());
        holder.productdate.setText(selectCategoryModel.getOfferenddate());
        holder.offertype.setText(selectCategoryModel.getOffertypename()+" Off "+selectCategoryModel.getOffervalue());
        if(selectCategoryModel.getOfferImage().equals("")){
        }else{
            Picasso.get().load(selectCategoryModel.getOfferImage()).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(holder.productimg);
        }
       /* holder.productshare.setText();*/
        if (selectCategoryModel.getOfferfav().equals("1")){
            holder.likeimg.setImageResource(R.drawable.ic_like);
        }else{
            holder.likeimg.setImageResource(R.drawable.heart);
        }
        if (selectCategoryModel.getOfferCouponCodeStatus().equals("1")){
            holder.productbutton.setText("View Coupon Code");
        }else if (selectCategoryModel.getOfferCouponCodeStatus().equals("2")){
            holder.productbutton.setText("Redeemed");
        }else{
            holder.productbutton.setText("Get Coupon Code");
        }

       holder.productname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, ProductActivity.class);
                myIntent.putExtra("offerid",selectCategoryModel.getId());
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);

            }
        });
        holder.productbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = UserSharedPrefManager.getInstance(mContext).getCustomer();
                idholder= user.getId();
                String couponstatus=selectCategoryModel.getOfferCouponCodeStatus();
                if (couponstatus.equals("0")){
                    couponstatus="1";
                    getcoupon(idholder,selectCategoryModel.getId(),couponstatus);

                }else if (couponstatus.equals("1")){
                    final Dialog dialog = new Dialog((Activity) v.getContext());
                    dialog.setContentView(R.layout.coupon_code_activity);
                    dialog.setTitle("Custom Dialog");
                    ImageView image=(ImageView)dialog.findViewById(R.id.qr_code_img_id);
                    if(selectCategoryModel.getOfferqrcodeimage().equals("")){
                    }else{
                        Picasso.get().load(selectCategoryModel.getOfferqrcodeimage()).networkPolicy(NetworkPolicy.NO_CACHE)
                                .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(image);
                    }
                    Button button=(Button)dialog.findViewById(R.id.coupon_button_apply_id);
                    button.setText(selectCategoryModel.getCoupon_code());
                    TextView textView=(TextView)dialog.findViewById(R.id.coupon_ok_id);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                        }

                    });

                    dialog.show();

                }else if (couponstatus.equals("2")){

                }
               /* Intent intent=new Intent("Coupon");
                intent.putExtra("",)*/


            }
        });

        holder.likeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fav=selectCategoryModel.getOfferfav();
                if (fav.equals("0")) {
                    Favstatus="1";
                    holder.likeimg.setImageResource(R.drawable.ic_like);
                    Intent intent = new Intent("Favourite");
                    intent.putExtra("fav", Favstatus);
                    intent.putExtra("offerid", selectCategoryModel.getId());
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                }else if(fav.equals("1")) {
                    Favstatus="0";
                    holder.likeimg.setImageResource(R.drawable.heart);
                    Intent intent = new Intent("Favourite");
                    intent.putExtra("fav", Favstatus);
                    intent.putExtra("offerid", selectCategoryModel.getId());
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                }
            }
        });

        holder.productshare.setOnClickListener(new View.OnClickListener() {
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
               /* Dialog dialog = new Dialog((Activity) v.getContext());
                dialog.setContentView(R.layout.share_item_activity);
                dialog.setTitle("Custom Dialog");
                LinearLayout message = (LinearLayout) dialog.findViewById(R.id.message_linear_id);
                LinearLayout whatsapp = (LinearLayout) dialog.findViewById(R.id.whatsapp_linear_id);
                LinearLayout facebook=(LinearLayout) dialog.findViewById(R.id.facebook_linear_id);
                dialog.show();*/

            }
        });
    }
    @Override
    public int getItemCount() {
        return selectCategoryModelArrayList.size();
    }
    public void setfilter(List<SelectCategoryModel> newlist) {
        selectCategoryModelArrayList = new ArrayList<>();
        selectCategoryModelArrayList.addAll(newlist);
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

     //   public CardView categorylinear;
        ImageView productimg,likeimg;
        TextView productname,productdate,productshare,offertype;
        Button productbutton;

        public MyViewHolder(View itemView) {
            super(itemView);
           // categorylinear=itemView.findViewById(R.id.card_linear_id);
            productimg = itemView.findViewById(R.id.product_image_id);
            productname =  itemView.findViewById(R.id.name_product_id);
            productdate = itemView.findViewById(R.id.product_date_id);
            productshare = itemView.findViewById(R.id.product_share_id);
            productbutton=itemView.findViewById(R.id.product_button_id);
            offertype=itemView.findViewById(R.id.offertype);
            likeimg=itemView.findViewById(R.id.heart_image_id);
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
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(postRequest);
    }
}
