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
import com.desired.offermachi.customer.model.CategoryListModel;
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.customer.view.activity.OtpActivtivity;
import com.desired.offermachi.retalier.constant.AppData;
import com.desired.offermachi.retalier.model.DealsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerCategoryListPresenter {
    private Context context;
    private CustomerCategoryList customerCategoryList;
    private ProgressDialog progress;

    public CustomerCategoryListPresenter(Context context, CustomerCategoryList customerCategoryList) {
        this.context = context;
        this.customerCategoryList = customerCategoryList;

    }

    public interface CustomerCategoryList {
        void followsuccess(String response);

        void success(ArrayList<CategoryListModel> response);

        void error(String response);

        void fail(String response);
    }

    public void GetCategoryList(final String userid) {
        if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Get Category Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }
        final ArrayList<CategoryListModel> list = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_select_main_category_data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!((Activity) context).isFinishing()) {
                    hidepDialog();
                }
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        list.clear();
                        String result=reader.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        JSONObject object;
                        for (int count = 0; count < jsonArray.length(); count++) {
                            object = jsonArray.getJSONObject(count);
                            CategoryListModel categoryListModel=new CategoryListModel(
                                    object.getString("id"),
                                    object.getString("category_name"),
                                    object.getString("category_image"),
                                    object.getString("follow_status"),
                                    object.getString("category_offer_image")

                            );
                            list.add(categoryListModel);
                        }
                        customerCategoryList.success(list);

                    } else if (status == 404) {
                        customerCategoryList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    customerCategoryList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(!((Activity) context).isFinishing()) {
                    hidepDialog();
                }
                customerCategoryList.fail("Server Error.\n Please try after some time.");
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
                        customerCategoryList.followsuccess(reader.getString("message"));
                    } else if (status == 404) {
                        customerCategoryList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    customerCategoryList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                customerCategoryList.fail("Server Error.\n Please try after some time.");
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
