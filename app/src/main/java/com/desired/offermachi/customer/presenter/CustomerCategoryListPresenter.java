package com.desired.offermachi.customer.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.CategoryListModel;
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.customer.view.activity.OtpActivtivity;
import com.desired.offermachi.retalier.constant.AppData;
import com.desired.offermachi.retalier.model.DealsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
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

//        void feedSuccess(ArrayList<CategoryListModel> response,String status);

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
                        CategoryListModel categoryListModel;
                        for (int count = 0; count < jsonArray.length(); count++) {
                            object = jsonArray.getJSONObject(count);
//                            CategoryListModel categoryListModel=new CategoryListModel(
//                                    object.getString("id"),
//                                    object.getString("category_name"),
//                                    object.getString("category_image"),
//                                    object.getString("follow_status"),
//                                    object.getString("category_offer_image")
//
//                            );
                            categoryListModel=new CategoryListModel();
                            categoryListModel.setCatid(object.getString("id"));
                            categoryListModel.setCatname(object.getString("category_name"));
                            categoryListModel.setCatimage(object.getString("category_image"));
                            categoryListModel.setFollowstatus(object.getString("follow_status"));
                            categoryListModel.setBannerimage(object.getString("category_offer_image"));

                            categoryListModel.setCheckStatus(false);

                            if(TextUtils.isEmpty(UserSharedPrefManager.GetStoredFilter(context)))
                            {
                                categoryListModel.setCheckStatus(false);
                            }else
                            {
                                String allSelCat=UserSharedPrefManager.GetStoredFilter(context);
                                String[] values = allSelCat.split(",");
//                                ArrayList list = new ArrayList(Arrays.asList(values));
//                               ArrayList<String>arSelCat=new ArrayList<>();
//                                System.out.println(Arrays.toString(values));
//                                arSelCat=Arrays.toString(values);
                                boolean checkflag=false;
                                for(int ii=0;ii<values.length;ii++)
                                {
                                    if(values[ii].equals(object.getString("id")))
                                    {
                                        checkflag=true;
                                    }
                                }

//                                if(allSelCat.contains(object.getString("id")))
                                if(checkflag)
                                {
                                    categoryListModel.setCheckStatus(true);
                                }
                                else
                                {
                                    categoryListModel.setCheckStatus(false);
                                }

                            }
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
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    public void GetFeedCategoryList(final String userid) {
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
                        CategoryListModel categoryListModel;

                        for (int count = 0; count < jsonArray.length(); count++)
                        {
                            object = jsonArray.getJSONObject(count);

                            categoryListModel=new CategoryListModel();
                            categoryListModel.setCatid(object.getString("id"));
                            categoryListModel.setCatname(object.getString("category_name"));
                            categoryListModel.setCatimage(object.getString("category_image"));
                            categoryListModel.setFollowstatus(object.getString("follow_status"));
                            categoryListModel.setBannerimage(object.getString("category_offer_image"));

                            categoryListModel.setCheckStatus(false);

                            if(TextUtils.isEmpty(UserSharedPrefManager.GetStoredFilter(context)))
                            {
                                categoryListModel.setCheckStatus(false);
                            }else
                            {
                                String allSelCat=UserSharedPrefManager.GetStoredFilter(context);
                                String[] values = allSelCat.split(",");
                                boolean checkflag=false;
                                for(int ii=0;ii<values.length;ii++)
                                {
                                    if(values[ii].equals(object.getString("id")))
                                    {
                                        checkflag=true;
                                    }
                                }
                                if(checkflag)
                                {
                                    categoryListModel.setCheckStatus(true);
                                }
                                else
                                {
                                    categoryListModel.setCheckStatus(false);
                                }

                            }

                            if(object.getString("follow_status").equals("1")) {
                                list.add(categoryListModel);
                            }
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
        )
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
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
