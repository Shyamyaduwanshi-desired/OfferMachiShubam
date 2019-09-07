package com.desired.offermachi.customer.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.customer.model.StoreModel;
import com.desired.offermachi.retalier.constant.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StoreListPresenter {
    private Context context;
    private StoreList storelist;
    private ProgressDialog progress;

    public StoreListPresenter(Context context, StoreList storelist) {
        this.context = context;
        this.storelist = storelist;

    }

    public interface StoreList {
        void success(ArrayList<StoreModel> response);

        void followsuccess(String response);

        void error(String response);

        void fail(String response);
    }

    public void ViewAllStore(final String userid) {
        if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }
        final ArrayList<StoreModel> list = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "select_all_retailer_data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               hidepDialog();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        String result = reader.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        JSONObject object;
                        for (int count = 0; count < jsonArray.length(); count++) {
                            object = jsonArray.getJSONObject(count);
                            StoreModel storeModel=new StoreModel(
                                    object.getString("id"),
                                    object.getString("shop_name"),
                                    object.getString("shop_logo"),
                                    object.getString("shop_category"),
                                    object.getString("favourite_status")

                            );
                            list.add(storeModel);
                        }
                        storelist.success(list);


                    } else if (status == 404) {
                        storelist.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    storelist.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               hidepDialog();
                storelist.fail("Server Error.\n Please try after some time.");
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

    public void FollowStore(final String userid,final String retailerid,final String active) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_add_follow_retailer", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        storelist.followsuccess(reader.getString("message"));
                    } else if (status == 404) {
                        storelist.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    storelist.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                storelist.fail("Server Error.\n Please try after some time.");
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
    public void StoreFilter(final String userid,final String catid) {
      /*  if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }*/
        final ArrayList<StoreModel> list = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "select_retailer_all_data_by_filter", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  hidepDialog();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        String result = reader.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        JSONObject object;
                        for (int count = 0; count < jsonArray.length(); count++) {
                            object = jsonArray.getJSONObject(count);
                            StoreModel storeModel=new StoreModel(
                                    object.getString("id"),
                                    object.getString("shop_name"),
                                    object.getString("shop_logo"),
                                    object.getString("shop_category"),
                                    object.getString("favourite_status")

                            );
                            list.add(storeModel);
                        }
                        storelist.success(list);


                    } else if (status == 404) {
                        storelist.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    storelist.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  hidepDialog();
                storelist.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("category_id", catid);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }
    public void ShortBy(final String userid,final String status) {
       /* if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }*/
        final ArrayList<StoreModel> list = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "select_sort_by_filter_retailers", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //hidepDialog();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        String result = reader.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        JSONObject object;
                        for (int count = 0; count < jsonArray.length(); count++) {
                            object = jsonArray.getJSONObject(count);
                            StoreModel storeModel=new StoreModel(
                                    object.getString("id"),
                                    object.getString("shop_name"),
                                    object.getString("shop_logo"),
                                    object.getString("shop_category"),
                                    object.getString("favourite_status")

                            );
                            list.add(storeModel);
                        }
                        storelist.success(list);


                    } else if (status == 404) {
                        storelist.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    storelist.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  hidepDialog();
                storelist.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("sort_by", status);
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