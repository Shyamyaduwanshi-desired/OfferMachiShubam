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
import com.desired.offermachi.customer.model.CategoryListModel;
import com.desired.offermachi.customer.model.FollowStoreModel;
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.customer.model.StoreModel;
import com.desired.offermachi.retalier.constant.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerFollowCategoryRetailerPresenter {
    private Context context;
    private FollowList followList;
    private ProgressDialog progress;

    public CustomerFollowCategoryRetailerPresenter(Context context, FollowList followList) {
        this.context = context;
        this.followList = followList;

    }

    public interface FollowList {
        void catsuccess(ArrayList<CategoryListModel> response);

        void followsuccess(String response);

        void retsuccess(ArrayList<FollowStoreModel> response);

        void error(String response);

        void fail(String response);
    }

    public void ViewAllCategoryRetailer(final String userid) {
        if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }
        final ArrayList<CategoryListModel> list = new ArrayList<>();
        final ArrayList<FollowStoreModel> list2 = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_follow_category_and_store_data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hidepDialog();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        list.clear();
                        String result=reader.getString("result");
                        JSONObject jsonObject = new JSONObject(result);
                        String catfollow = jsonObject.getString("category_follow");
                        String storefollow = jsonObject.getString("store_follow");
                        JSONArray jsonArray = new JSONArray(catfollow);
                        JSONObject object;
                        CategoryListModel categoryListModel;
                        for (int count = 0; count < jsonArray.length(); count++) {
                            object = jsonArray.getJSONObject(count);
                          /*  CategoryListModel categoryListModel=new CategoryListModel(
                                    object.getString("id"),
                                    object.getString("category_name"),
                                    object.getString("category_image"),
                                    object.getString("follow_status"),
                                    object.getString("category_offer_image")

                            );*/

                            categoryListModel=new CategoryListModel();
                            categoryListModel.setCatid(object.getString("id"));
                            categoryListModel.setCatname(object.getString("category_name"));
                            categoryListModel.setCatimage(object.getString("category_image"));
                            categoryListModel.setFollowstatus(object.getString("follow_status"));
                            categoryListModel.setBannerimage(object.getString("category_offer_image"));

                            list.add(categoryListModel);
                        }
                        followList.catsuccess(list);
                        JSONArray jsonArray2 = new JSONArray(storefollow);
                        JSONObject object3;
                        for (int count = 0; count < jsonArray2.length(); count++) {
                            object3 = jsonArray2.getJSONObject(count);
                            FollowStoreModel followStoreModel=new FollowStoreModel(
                                    object3.getString("id"),
                                    object3.getString("shop_name"),
                                    object3.getString("shop_logo"),
                                    object3.getString("shop_category"),
                                    object3.getString("favourite_status"),
                                    object3.getString("about_store")

                            );
                            list2.add(followStoreModel);
                        }
                        followList.retsuccess(list2);


                    } else if (status == 404) {
                        followList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    followList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidepDialog();
                followList.fail("Server Error.\n Please try after some time.");
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
    public void CategoryFollow(final String userid,final String catid,final String followstatus) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_follow_category", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        followList.followsuccess(reader.getString("message"));
                    } else if (status == 404) {
                        followList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    followList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                followList.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("category_id", catid);
                params.put("status", followstatus);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
      /*  RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);*/
    }

    public void FollowStore(final String userid,final String retailerid,final String active) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_add_follow_retailer", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        followList.followsuccess(reader.getString("message"));
                    } else if (status == 404) {
                        followList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    followList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                followList.fail("Server Error.\n Please try after some time.");
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

    private void showpDialog() {
        if (!progress.isShowing())
            progress.show();
    }

    private void hidepDialog() {
        if (progress.isShowing())
            progress.dismiss();
    }
}
