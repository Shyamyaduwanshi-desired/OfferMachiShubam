package com.desired.offermachi.customer.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.retalier.constant.AppData;
import com.desired.offermachi.retalier.presenter.DealsOftheDayPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class CustomerDealsOftheDaysPresenter {
    private Context context;
    private DealsList dealsList;
    private ProgressDialog progress;

    public CustomerDealsOftheDaysPresenter(Context context, DealsList dealsList) {
        this.context = context;
        this.dealsList = dealsList;

    }

    public interface DealsList {
        void success(ArrayList<SelectCategoryModel> response);

        void favsuc(String  response);

        void getsuc(String  response);

        void error(String response);

        void fail(String response);
    }

    public void ViewAllDeals(final String userid) {
        if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }
        final ArrayList<SelectCategoryModel> list = new ArrayList<>();//http://offermachi.in/api/customer_deals_of_the_day_data
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_deals_of_the_day_data2", new Response.Listener<String>() {
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
                            SelectCategoryModel selectCategoryModel=new SelectCategoryModel(
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
                                    object.getString("favourite_status"),
                                    object.getString("offer_image"),
                                    object.getString("qr_code_image"),
                                    object.getString("coupon_code_status"),
                                    object.getString("shop_logo")

                            );
                            list.add(selectCategoryModel);
                        }

                        Collections.sort(list, new Comparator<SelectCategoryModel>() {//yyyy-MM-dd
                            DateFormat f = new SimpleDateFormat("yyyy-MM-dd");//2019-09-26
                            @Override
                            public int compare(SelectCategoryModel lhs, SelectCategoryModel rhs) {
                                try {
                                    return f.parse(lhs.getOfferstartdate()).compareTo(f.parse(rhs.getOfferstartdate()));
                                } catch (ParseException e) {
                                    throw new IllegalArgumentException(e);
                                }
                            }
                        });

                        Collections.reverse(list);


                        dealsList.success(list);


                    } else if (status == 404) {
                        dealsList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dealsList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidepDialog();
                dealsList.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("latitude", UserSharedPrefManager.GetLat(context));
                params.put("longitude", UserSharedPrefManager.GetLong(context));
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    public void Favourite(final String userid, final String offerid,final String active) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_add_favourite_offer", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                        dealsList.favsuc(reader.getString("message"));

                    }else if(status == 404){
                        dealsList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dealsList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dealsList.fail("Server Error.\n Please try after some time.");
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
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }
    public void DealFilterSingle(final String userid,final String catid) {
        final ArrayList<SelectCategoryModel> list = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_deals_of_the_day_by_filter", new Response.Listener<String>() {
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
                            SelectCategoryModel selectCategoryModel=new SelectCategoryModel(
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
                                    object.getString("favourite_status"),
                                    object.getString("offer_image"),
                                    object.getString("qr_code_image"),
                                    object.getString("coupon_code_status"),
                                    object.getString("shop_logo")

                            );
                            list.add(selectCategoryModel);
                        }
                        dealsList.success(list);


                    } else if (status == 404) {
                        dealsList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dealsList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // hidepDialog();
                dealsList.fail("Server Error.\n Please try after some time.");
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
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    public void DealFilter(final String userid,final String catid) {
        final ArrayList<SelectCategoryModel> list = new ArrayList<>();//customer_deals_of_the_day_by_filter_categories,customer_deals_of_the_day_by_filter_categories2
        // customer_deals_of_the_day_by_filter
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_deals_of_the_day_by_filter_categories2", new Response.Listener<String>() {
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
                            SelectCategoryModel selectCategoryModel=new SelectCategoryModel(
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
                                    object.getString("favourite_status"),
                                    object.getString("offer_image"),
                                    object.getString("qr_code_image"),
                                    object.getString("coupon_code_status"),
                                    object.getString("shop_logo")

                            );
                            list.add(selectCategoryModel);
                        }
                        dealsList.success(list);


                    } else if (status == 404) {
                        dealsList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dealsList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // hidepDialog();
                dealsList.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("category_ids", catid);
                params.put("latitude", UserSharedPrefManager.GetLat(context));
                params.put("longitude", UserSharedPrefManager.GetLong(context));
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
        final ArrayList<SelectCategoryModel> list = new ArrayList<>();//customer_deals_of_the_data_by_sort_by,customer_deals_of_the_data_by_sort_by2
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_deals_of_the_data_by_sort_by2", new Response.Listener<String>() {
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
                            SelectCategoryModel selectCategoryModel=new SelectCategoryModel(
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
                                    object.getString("favourite_status"),
                                    object.getString("offer_image"),
                                    object.getString("qr_code_image"),
                                    object.getString("coupon_code_status"),
                                    object.getString("shop_logo")

                            );
                            list.add(selectCategoryModel);
                        }
                        dealsList.success(list);


                    } else if (status == 404) {
                        dealsList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dealsList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  hidepDialog();
                dealsList.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("sort_by", status);
                params.put("latitude", UserSharedPrefManager.GetLat(context));
                params.put("longitude", UserSharedPrefManager.GetLong(context));
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
