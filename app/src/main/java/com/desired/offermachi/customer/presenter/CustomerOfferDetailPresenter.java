package com.desired.offermachi.customer.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.customer.model.StoreModel;
import com.desired.offermachi.retalier.constant.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerOfferDetailPresenter {
    private Context context;
    private OfferDetail offerDetail;
    private ProgressDialog progress;

    public CustomerOfferDetailPresenter(Context context,OfferDetail offerDetail) {
        this.context = context;
        this.offerDetail = offerDetail;

    }

    public interface OfferDetail {
        void success(String response);

        void storesuccess(String response);

        void favsuccess(String response);

        void couponsucess(String response);

        void error(String response);

        void fail(String response);
    }

    public void OfferDetail(final String userid,final String offerid) {
        if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_offer_and_store_details", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               hidepDialog();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        String result=reader.getString("result");
                        JSONObject reader2 = new JSONObject(result);
                        offerDetail.storesuccess(reader2.getString("single_store_data"));
                        offerDetail.success(reader2.getString("single_offer_data"));


                    } else if (status == 404) {
                        offerDetail.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    offerDetail.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              hidepDialog();
                offerDetail.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("offer_id", offerid);
                Log.e("postdata", "postparams= "+params );
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    public void AddFavourite(final String userid, final String offerid,final String active) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_add_favourite_offer", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                        offerDetail.favsuccess(reader.getString("message"));

                    }else if(status == 404){
                        offerDetail.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    offerDetail.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                offerDetail.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("offer_id",offerid);
                params.put("active", active);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }


    public void AddStoreFollow(final String userid, final String retailerid,final String active) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_add_follow_retailer", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                        offerDetail.favsuccess(reader.getString("message"));

                    }else if(status == 404){
                        offerDetail.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    offerDetail.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                offerDetail.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("retailer_id", retailerid);
                params.put("active", active);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    public void GetCoupons(final String id, final String offer_id,final String getcoupon) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_get_coupon_code_data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                        offerDetail.couponsucess(reader.getString("message"));

                    }else if(status == 404){
                        offerDetail.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    offerDetail.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                offerDetail.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", id);
                params.put("offer_id",offer_id);
                params.put("active", getcoupon);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }
    private void showpDialog() {
        if (!progress.isShowing())
            progress.show();
    }

    private void hidepDialog() {
        if (progress.isShowing())
            progress.dismiss();
    }
}