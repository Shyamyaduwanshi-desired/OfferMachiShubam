package com.desired.offermachi.retalier.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.retalier.constant.AppData;
import com.desired.offermachi.retalier.model.DealsModel;
import com.desired.offermachi.retalier.model.DealsModelNew;
import com.desired.offermachi.retalier.model.ViewOfferModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DealsOftheDayPresenter {
    private Context context;
    private DealsOftheDay dealsOftheDay;

    public DealsOftheDayPresenter(Context context, DealsOftheDay dealsOftheDay ) {
        this.context = context;
        this.dealsOftheDay = dealsOftheDay;
    }

    public interface DealsOftheDay{
        void success(ArrayList<DealsModelNew> response);
        void Adddealsuccess(String response);
        void error(String response);
        void fail(String response);
    }
    public void getRetailerDealsOftheDay(final String userid) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Get Deals of the Days Please Wait..");
        progress.setCancelable(false);
        progress.show();
        final ArrayList<DealsModelNew> list = new ArrayList<>();//old retailer_deals_of_the_day
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "get_deals_of_the_day_data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    Log.e("offer", "result="+response );
                    int status = reader.getInt("status");
                    String message=reader.getString("message");
                    if (status == 200) {
                        list.clear();
                        String result=reader.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        JSONObject object;
                        for (int count = 0; count < jsonArray.length(); count++) {
                            object = jsonArray.getJSONObject(count);
                            DealsModelNew dealsModel=new DealsModelNew(
                                    object.optString("id"),
                                    object.optString("offer_id"),
                                    object.optString("offer_title"),
                                    object.optString("offer_title_slug"),
                                    object.optString("offer_category"),
                                    object.optString("sub_category"),
                                    object.optString("offer_type"),
                                    object.optString("offer_type_name"),
                                    object.optString("offer_value"),
                                    object.optString("offer_details"),
                                    object.optString("start_date"),
                                    object.optString("end_date"),
                                    object.optString("alltime"),
                                    object.optString("description"),
                                    object.optString("coupon_code"),
                                    object.optString("posted_by"),
                                    object.optString("status"),
                                    object.optString("offer_brand_name"),
                                    object.optString("favourite_status"),
                                    object.optString("offer_image"),
                                    object.optString("qr_code_image"),
                                    object.optString("coupon_code_status"),
                                    object.optString("shop_logo")
                            );
                            list.add(dealsModel);

                        }
                        dealsOftheDay.success(list);
                    } else if (status == 404) {
                        dealsOftheDay.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dealsOftheDay.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                dealsOftheDay.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",userid);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    public void getDealsOftheDay(final String userid) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Get Deals of the Days Please Wait..");
        progress.setCancelable(false);
        progress.show();
        final ArrayList<DealsModelNew> list = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "retailer_add_to_deals_data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                     Log.e("offer", "result="+response );
                    int status = reader.getInt("status");
                    String message=reader.getString("message");
                    if (status == 200) {
                        list.clear();
                        String result=reader.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        JSONObject object;
                        for (int count = 0; count < jsonArray.length(); count++) {
                            object = jsonArray.getJSONObject(count);
                            DealsModelNew dealsModel=new DealsModelNew(
                                    object.optString("id"),
                                    object.optString("offer_id"),
                                    object.optString("offer_title"),
                                    object.optString("offer_title_slug"),
                                    object.optString("offer_category"),
                                    object.optString("sub_category"),
                                    object.optString("offer_type"),
                                    object.optString("offer_type_name"),
                                    object.optString("offer_value"),
                                    object.optString("offer_details"),
                                    object.optString("start_date"),
                                    object.optString("end_date"),
                                    object.optString("alltime"),
                                    object.optString("description"),
                                    object.optString("coupon_code"),
                                    object.optString("posted_by"),
                                    object.optString("status"),
                                    object.optString("offer_brand_name"),
                                    object.optString("favourite_status"),
                                    object.optString("offer_image"),
                                    object.optString("qr_code_image"),
                                    object.optString("coupon_code_status"),
                                    object.optString("shop_logo")
                            );
                            list.add(dealsModel);

                        }
                        dealsOftheDay.success(list);
                    } else if (status == 404) {
                        dealsOftheDay.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dealsOftheDay.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                dealsOftheDay.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",userid);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    public void AddtoDeals(final String offer_id ) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "retailer_add_to_deals_action", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                        dealsOftheDay.Adddealsuccess(reader.getString("message"));

                    }else if(status == 404){
                        dealsOftheDay.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dealsOftheDay.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                dealsOftheDay.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("offer_id",offer_id);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

}