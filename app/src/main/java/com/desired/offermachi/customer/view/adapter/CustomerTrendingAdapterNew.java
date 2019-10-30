package com.desired.offermachi.customer.view.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.desired.offermachi.RoundRectCornerImageView;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.constant.Util;
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.view.activity.ProductActivity;
import com.desired.offermachi.retalier.constant.AppData;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CustomerTrendingAdapterNew extends RecyclerView.Adapter<CustomerTrendingAdapterNew.MyViewHolder>{
    private ArrayList<SelectCategoryModel> selectCategoryModelArrayList,arSearchItem;
    private Context mContext;
    private String Favstatus;
    private String idholder;
    AppData appdata;
    String fromHome;
    private SelectCategoryModel selectCategoryModel;

    public CustomerTrendingAdapterNew(Context context, ArrayList<SelectCategoryModel> selectCategoryModelArrayList, String fromHome) {
        this.selectCategoryModelArrayList = selectCategoryModelArrayList;
        this.mContext = context;
        this.fromHome=fromHome;
        appdata=new AppData();
        this.arSearchItem = new ArrayList<SelectCategoryModel>();
        this.arSearchItem.addAll(selectCategoryModelArrayList);
    }

    @Override
    public CustomerTrendingAdapterNew.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                      int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adpter_home_offer_new, parent, false);
        CustomerTrendingAdapterNew.MyViewHolder myViewHolder = new CustomerTrendingAdapterNew.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomerTrendingAdapterNew.MyViewHolder holder, final int i) {
        if(selectCategoryModelArrayList.size()>0) {

            selectCategoryModel = selectCategoryModelArrayList.get(i);
//        holder.productname.setText(selectCategoryModel.getOffername());
//        holder.offertype.setText(selectCategoryModel.getOffertype());
            holder.productdate.setText("Exp on: " + appdata.ConvertDate4(selectCategoryModel.getOfferenddate()));

//        holder.offertype.setText(selectCategoryModel.getOffertypename()+" Off "+selectCategoryModel.getOffervalue());

//        String text = "<font color=#cc0029>First Color</font> <font color=#ffcc00>Second Color</font>";
//        yourtextview.setText(Html.fromHtml(text));


            if (selectCategoryModel.getOffername() == null) {
                holder.productname.setText("");
            } else {
                holder.productname.setText(selectCategoryModel.getOffername());
            }
            Log.e("Adapter", "" + selectCategoryModel.getOffertypename());
            if (Util.isEmptyString(selectCategoryModel.getOffertypename())) {
                holder.offertype.setText("");
                holder.text_linear.setVisibility(View.GONE);
            } else {
                holder.offertype.setText(selectCategoryModel.getOffertypename() + " Off " + selectCategoryModel.getOffervalue());
            }


            if (selectCategoryModel.getOfferdescription().length() > 30) {
                holder.tvDsc.setText(Html.fromHtml(selectCategoryModel.getOfferdescription().substring(0, 30) + "..."));
            } else {
                holder.tvDsc.setText(Html.fromHtml(selectCategoryModel.getOfferdescription()));
            }

            if (selectCategoryModel.getOfferImage().equals("")) {
            } else {

                Picasso.get().load(selectCategoryModel.getOfferImage()).placeholder(R.drawable.ic_broken).into(holder.productimg);
            }

            if (selectCategoryModel.getOfferfav().equals("1")) {
                holder.likeimg.setImageResource(R.drawable.ic_like);
            } else {
                holder.likeimg.setImageResource(R.drawable.heart);
            }

            if (selectCategoryModel.getOfferCouponCodeStatus().equals("1")) {
                holder.productbutton.setText("View Coupon");//View Coupon Code
                holder.productbutton.setTextColor(mContext.getResources().getColor(R.color.black));
                holder.productbuttonlayout.setBackgroundResource(R.drawable.view_coupon_code_bg);
            } else if (selectCategoryModel.getOfferCouponCodeStatus().equals("2")) {
                holder.productbutton.setText("Redeemed");
            } else {
                holder.productbutton.setText("Get Coupon");//Get Coupon Code
                holder.productbutton.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.productbuttonlayout.setBackgroundResource(R.drawable.home_coupon_code_bg);
            }

            Log.e("", "store logo= " + selectCategoryModel.getStoreLogo());
            if (TextUtils.isEmpty(selectCategoryModel.getStoreLogo()) || selectCategoryModel.getStoreLogo().equals("")) {
            } else {
                Picasso.get().load(selectCategoryModel.getStoreLogo()).networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.shortlogo).into(holder.ivStoreLogo);
            }
        }
       holder.productname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, ProductActivity.class);
                myIntent.putExtra("offerid",selectCategoryModelArrayList.get(i).getId());
                Log.e("Adpater","id..."+"position..."+i+""+selectCategoryModelArrayList.get(i).getId());
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);

            }
        });

        holder.productimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, ProductActivity.class);
                myIntent.putExtra("offerid",selectCategoryModelArrayList.get(i).getId());
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);

            }
        });

        holder.productbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = UserSharedPrefManager.getInstance(mContext).getCustomer();
                idholder= user.getId();
                String couponstatus=selectCategoryModelArrayList.get(i).getOfferCouponCodeStatus();
                if (couponstatus.equals("0")){
                    couponstatus="1";
                    getcoupon(idholder,selectCategoryModelArrayList.get(i).getId(),couponstatus);

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
            }
        });


        holder.likeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fav=selectCategoryModelArrayList.get(i).getOfferfav();
                if (fav.equals("0")) {
                    Favstatus="1";
                    holder.likeimg.setImageResource(R.drawable.ic_like);
                    Intent intent = new Intent("Favourite");
                    intent.putExtra("fav", Favstatus);
                    intent.putExtra("offerid", selectCategoryModelArrayList.get(i).getId());
                    Log.e("Adpater","id..."+"position..."+i+""+selectCategoryModelArrayList.get(i).getId());
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                }else if(fav.equals("1")) {
                    Favstatus="0";
                    holder.likeimg.setImageResource(R.drawable.heart);
                    Intent intent = new Intent("Favourite");
                    intent.putExtra("fav", Favstatus);
                    intent.putExtra("offerid", selectCategoryModelArrayList.get(i).getId());
                    Log.e("Adpater","id..."+selectCategoryModelArrayList.get(i).getId());
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                }
            }
        });

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

        if(fromHome.equals("1")){
            if(selectCategoryModelArrayList.size()>10){
                return 10;
            }
            else {
                return selectCategoryModelArrayList.size();

            }
        }
        else {
            return selectCategoryModelArrayList.size();

        }
    }
    public void setfilter(List<SelectCategoryModel> newlist) {
        selectCategoryModelArrayList = new ArrayList<>();
        selectCategoryModelArrayList.addAll(newlist);
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

     //   public CardView categorylinear;
        ImageView likeimg,ivStoreLogo,iv_share;
        TextView productname,productdate,offertype,tvDsc;
        Button productbutton;
        RelativeLayout productbuttonlayout;
        RelativeLayout rlShare,rlLike;
        CardView cv_image;
        LinearLayout text_linear;
        RoundRectCornerImageView productimg;
//        AndroidLikeButton ivLikeBtn;

        public MyViewHolder(View itemView) {
            super(itemView);

            productimg = itemView.findViewById(R.id.iv_image);
            productname =  itemView.findViewById(R.id.tv_product_name);
            tvDsc =  itemView.findViewById(R.id.tv_prod_dsc);
            productdate = itemView.findViewById(R.id.tv_prod_date);
            iv_share = itemView.findViewById(R.id.iv_share);
           // rlLike = itemView.findViewById(R.id.rl_like);
            productbutton=itemView.findViewById(R.id.bt_get_acoupon_code);
            productbuttonlayout=itemView.findViewById(R.id.rl_get_coupon_layout);
           // cv_image=itemView.findViewById(R.id.cv_image);
            offertype=itemView.findViewById(R.id.tv_flat_discount);
            likeimg=itemView.findViewById(R.id.iv_like);
            ivStoreLogo=itemView.findViewById(R.id.iv_icon);
            text_linear =itemView.findViewById(R.id.text_linear);
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


    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        selectCategoryModelArrayList.clear();
        if (charText.length() == 0) {
            selectCategoryModelArrayList.addAll(arSearchItem);
        } else {
            for (SelectCategoryModel wp : arSearchItem) {
                if (wp.getOffername().toLowerCase(Locale.getDefault()).contains(charText))
//                if (wp.getCatName().startsWith(charText))
                {
                    selectCategoryModelArrayList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        return selectCategoryModelArrayList.size();
//        return contacts.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

}
