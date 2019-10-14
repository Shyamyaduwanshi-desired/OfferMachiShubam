package com.desired.offermachi.retalier.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.retalier.constant.AppData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditOfferPresenter {
    private Context context;
    private PostOfferDiscount postOfferDiscount;

    public EditOfferPresenter(Context context, PostOfferDiscount postOfferDiscount) {
        this.context = context;
        this.postOfferDiscount = postOfferDiscount;
    }

    public interface PostOfferDiscount{
        void successoffer(String response);
        void erroroffer(String response);
        void failoffer(String response);
    }

    public void sentEditRequest(final String user_id, final String offer_title, final String offer_brand, final String offer_type, final String offer_value, final String offer_image, final String offer_category
           ,final String description , final String start_date, final String end_date, final String coupon_code, final String alltime,int type,String offerLocalityId)
    {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "update_offer_by_offer_id", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                        postOfferDiscount.successoffer(reader.getString("message"));

                    }else if(status == 404){
                        postOfferDiscount.erroroffer(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                     postOfferDiscount.failoffer("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                postOfferDiscount.failoffer("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",user_id);
                params.put("offer_title", offer_title);
                params.put("offer_brand",offer_brand);
                params.put("offer_type", offer_type);
                params.put("offer_value", offer_value);
                params.put("offer_image", offer_image);
                params.put("offer_category", offer_category);
                params.put("description",description);
                params.put("start_date", start_date);
                params.put("end_date", end_date);
                params.put("coupon_code", coupon_code);
                params.put("alltime", alltime);
                params.put("offer_locality", offerLocalityId);
                Log.e("","push offer input data= "+params.toString());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }



    public void editOffer(final String user_id, final String offer_title, final String offer_brand, final String offer_type, final String offer_value, final String offer_image, final String offer_category
            ,final String description , final String start_date, final String end_date, final String coupon_code, final String alltime,final String offerLocalityId,String offerId ) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "update_offer_by_offer_id", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                        postOfferDiscount.successoffer(reader.getString("message"));

                    }else if(status == 404){
                        postOfferDiscount.erroroffer(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    postOfferDiscount.failoffer("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                postOfferDiscount.failoffer("Server Error.\n Please try after some time.");

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",user_id);
                params.put("offer_title", offer_title);
                params.put("offer_brand",offer_brand);
                params.put("offer_type", offer_type);
                params.put("offer_value", offer_value);
                if(offer_image.length()!=0)
                    params.put("offer_image", offer_image);
                params.put("offer_category", offer_category);
                params.put("description",description);
                params.put("start_date", start_date);
                params.put("end_date", end_date);
                params.put("offer_id", offerId);
                params.put("coupon_code", coupon_code);
                params.put("alltime", alltime);
                params.put("offer_locality", offerLocalityId);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

}