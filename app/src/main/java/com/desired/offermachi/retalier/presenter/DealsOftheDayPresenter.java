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
        void success(ArrayList<DealsModel> response);
        void Adddealsuccess(String response);
        void error(String response);
        void fail(String response);
    }
    public void getRetailerDealsOftheDay(final String userid) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Get Deals of the Days Please Wait..");
        progress.setCancelable(false);
        progress.show();
        final ArrayList<DealsModel> list = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "retailer_deals_of_the_day", new Response.Listener<String>() {
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
                            DealsModel dealsModel=new DealsModel(
                                    object.getString("id"),
                                    object.getString("offer_id"),
                                    object.getString("offer_title"),
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
                                    object.getString("deals_of_the_day_status"),
                                    object.getString("offer_image"),
                                    object.getString("qr_code_image")

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
        final ArrayList<DealsModel> list = new ArrayList<>();
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
                            DealsModel dealsModel=new DealsModel(
                                    object.getString("id"),
                                    object.getString("offer_id"),
                                    object.getString("offer_title"),
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
                                    object.getString("deals_of_the_day_status"),
                                    object.getString("offer_image"),
                                    object.getString("qr_code_image")

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