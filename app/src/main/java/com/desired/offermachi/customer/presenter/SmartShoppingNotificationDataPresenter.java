package com.desired.offermachi.customer.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.customer.view.activity.OtpActivtivity;
import com.desired.offermachi.retalier.constant.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SmartShoppingNotificationDataPresenter {
    private Context context;
    private NotificationOfferDataList notificationOfferDataList;
    private ProgressDialog progress;

    public SmartShoppingNotificationDataPresenter(Context context, NotificationOfferDataList notificationOfferDataList) {
        this.context = context;
        this.notificationOfferDataList = notificationOfferDataList;

    }

    public interface NotificationOfferDataList {
        void success(ArrayList<SelectCategoryModel> response);

        void favsuccess(String response);

        void error(String response);

        void fail(String response);
    }

    public void ViewAllSmartData(final String userid) {
        if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }
        final ArrayList<SelectCategoryModel> list = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "smart_shoping_user_notification_data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!((Activity) context).isFinishing()) {
                    hidepDialog();
                }
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        String result = reader.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        JSONObject object;
                        for (int count = 0; count < jsonArray.length(); count++) {
                            object = jsonArray.getJSONObject(count);
                            SelectCategoryModel selectCategoryModel=new SelectCategoryModel(
                                    object.getString("id"),
                                    object.getString("offer_id"),
                                    object.getString("offer_title"),
                                    object.getString("offer_title_slug"),
                                    object.getString("offer_category"),
                                    object.getString("sub_category"),
                                    object.getString("offer_type"),
                                    object.getString("offer_type_name"),
                                    object.getString("offer_value"),
                                    object.getString("offer_details"),
                                    object.getString("start_date"),
                                    object.getString("end_date"),
                                    object.getString("alltime"),
                                    object.getString("description"),
                                    object.getString("coupon_code"),
                                    object.getString("posted_by"),
                                    object.getString("status"),
                                    object.getString("offer_brand_name"),
                                    object.getString("favourite_status"),
                                    object.getString("offer_image"),
                                    object.getString("qr_code_image"),
                                    object.getString("coupon_code_status"),
                                    ""

                            );
                            list.add(selectCategoryModel);
                        }
                        notificationOfferDataList.success(list);


                    } else if (status == 404) {
                        notificationOfferDataList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    notificationOfferDataList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(!((Activity) context).isFinishing()) {
                    hidepDialog();
                }
                notificationOfferDataList.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
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
                        notificationOfferDataList.favsuccess(reader.getString("message"));

                    }else if(status == 404){
                        notificationOfferDataList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    notificationOfferDataList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                notificationOfferDataList.fail("Server Error.\n Please try after some time.");
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
    public void Remove(final String userid,final String offerid) {
      /*  final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();*/

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "smart_shoping_remove_single_offer_data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                        notificationOfferDataList.favsuccess(reader.getString("message"));

                    }else if(status == 404){
                        notificationOfferDataList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    notificationOfferDataList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progress.dismiss();
                notificationOfferDataList.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("offer_id", offerid);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }
    public void RemoveAll(final String userid) {
       /* final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();*/

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "smart_shoping_remove_all_offer_data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                        notificationOfferDataList.favsuccess(reader.getString("message"));

                    }else if(status == 404){
                        notificationOfferDataList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    notificationOfferDataList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  progress.dismiss();
                notificationOfferDataList.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
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