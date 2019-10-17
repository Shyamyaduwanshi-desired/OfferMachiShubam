package com.desired.offermachi.customer.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
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
        void success(ArrayList<StoreModel> response,int totalRecords,int totalPages);

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
        final ArrayList<StoreModel> list = new ArrayList<>();//select_all_retailer_data2,select_all_retailer_data
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "select_all_retailer_data2", new Response.Listener<String>() {
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
                        storelist.success(list,0,0);


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
                params.put("latitude", UserSharedPrefManager.GetLat(context));
                params.put("longitude", UserSharedPrefManager.GetLong(context));
//                params.put("dist", UserSharedPrefManager.GetDistance(context));
//                params.put("dist", "1");
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

    public void StoreFilter(final String userid,final String catid) {//for multiple categories
      /*  if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }*/
        final ArrayList<StoreModel> list = new ArrayList<>();//select_retailer_all_data_by_filter_category2
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "select_retailer_all_data_by_filter_category2", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  hidepDialog();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        String result = reader.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        Log.e("",""+jsonArray.toString()+" result= "+result);
                        if(jsonArray!=null&&jsonArray.length()>0) {
                            JSONObject object;
                            for (int count = 0; count < jsonArray.length(); count++) {
                                object = jsonArray.getJSONObject(count);
                                StoreModel storeModel = new StoreModel(
                                        object.getString("id"),
                                        object.getString("shop_name"),
                                        object.getString("shop_logo"),
                                        object.getString("shop_category"),
                                        object.getString("favourite_status")

                                );
                                list.add(storeModel);
                            }
                            storelist.success(list,0,0);
                        }
                        else
                        {
                            storelist.error("Data not found");
                        }


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
                params.put("latitude", UserSharedPrefManager.GetLat(context));
                params.put("longitude", UserSharedPrefManager.GetLong(context));
//                params.put("dist", UserSharedPrefManager.GetDistance(context));
//                params.put("dist", "1");
                Log.e("","Input param= "+params.toString());
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
                        storelist.success(list,0,0);


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


    public void ViewAllStorePagination(final String userid,int currentPagNo) {
        if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }
        final ArrayList<StoreModel> list = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "select_all_retailer_data2", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               hidepDialog();
                try {
                    list.clear();
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
//                        String result = reader.getString("result");
//                        JSONArray jsonArray = new JSONArray(result);
//                        JSONObject object;
                        String result = reader.getString("result");
                        JSONObject jsObj = new JSONObject(result);
                        int totalRecord=0,countPage=0;
                        if(jsObj!=null) {
                            if(TextUtils.isEmpty(jsObj.getString("count")))
                            {
                                totalRecord=0;
                            }
                            else {
                                totalRecord = jsObj.getInt("count");
                            }

                            if(TextUtils.isEmpty(jsObj.getString("count_page")))
                            {
                                countPage=0;
                            }
                            else {
                                countPage = jsObj.getInt("count_page");
                            }
//                            totalRecord = jsObj.getInt("count");
//                            countPage = jsObj.getInt("count_page");
                            JSONArray jsonArray = jsObj.getJSONArray("retailers");
                            Log.e("", "jsonArray= " + jsonArray.toString());
                            JSONObject object;
                            for (int count = 0; count < jsonArray.length(); count++) {
                                object = jsonArray.getJSONObject(count);
                                StoreModel storeModel = new StoreModel(
                                        object.getString("id"),
                                        object.getString("shop_name"),
                                        object.getString("shop_logo"),
                                        object.getString("shop_category"),
                                        object.getString("favourite_status")

                                );
                                list.add(storeModel);
                            }
                        }
                        storelist.success(list,totalRecord,countPage);


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
                params.put("pageno", String.valueOf(currentPagNo));
                params.put("perpage", "1");
                params.put("latitude", UserSharedPrefManager.GetLat(context));
                params.put("longitude", UserSharedPrefManager.GetLong(context));
//                params.put("dist", UserSharedPrefManager.GetDistance(context));
//                params.put("dist", "1");
                Log.e("","Input param= "+params.toString());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    public void StoreFilterPagination(final String userid,final String catid,int currentPagNo) {
        final ArrayList<StoreModel> list = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "select_retailer_all_data_by_filter_category2", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  hidepDialog();
                try {
                    list.clear();
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {

//                        String result = reader.getString("result");
//                        JSONArray jsonArray = new JSONArray(result);
//                        Log.e("",""+jsonArray.toString()+" result= "+result);
//                        if(jsonArray!=null&&jsonArray.length()>0) {
//                            JSONObject object;

                        String result = reader.getString("result");
                        JSONObject jsObj = new JSONObject(result);
                        int totalRecord=0,countPage=0;
                        if(jsObj!=null) {
                            if(TextUtils.isEmpty(jsObj.getString("count")))
                            {
                                totalRecord=0;
                            }
                            else {
                                totalRecord = jsObj.getInt("count");
                            }

                            if(TextUtils.isEmpty(jsObj.getString("count_page")))
                            {
                                countPage=0;
                            }
                            else {
                                countPage = jsObj.getInt("count_page");
                            }
                            JSONArray jsonArray = jsObj.getJSONArray("retailers");
                            Log.e("", "jsonArray= " + jsonArray.toString());
                            if(jsonArray!=null&&jsonArray.length()>0) {
                                JSONObject object;
                                for (int count = 0; count < jsonArray.length(); count++) {
                                    object = jsonArray.getJSONObject(count);
                                    StoreModel storeModel = new StoreModel(
                                            object.getString("id"),
                                            object.getString("shop_name"),
                                            object.getString("shop_logo"),
                                            object.getString("shop_category"),
                                            object.getString("favourite_status")

                                    );
                                    list.add(storeModel);
                                }
                                storelist.success(list,totalRecord,countPage);
                            }
                            else {
                                storelist.error("Data not found");
                            }
//                            storelist.success(list,totalRecord,countPage);
                        }
                        else
                        {
                            storelist.error("Data not found");
                        }


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
                params.put("categories_id", catid);
                params.put("pageno", String.valueOf(currentPagNo));
                params.put("perpage", "12");
                params.put("latitude", UserSharedPrefManager.GetLat(context));
                params.put("longitude", UserSharedPrefManager.GetLong(context));
//                params.put("dist", UserSharedPrefManager.GetDistance(context));
//                params.put("dist", "1");
                Log.e("","Input param= "+params.toString());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    public void ShortByPagination(final String userid,final String status,int currentPagNo) {

        final ArrayList<StoreModel> list = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "select_sort_by_filter_retailers2_page", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //hidepDialog();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        list.clear();
//                        String result = reader.getString("result");
//                        JSONArray jsonArray = new JSONArray(result);
//                        JSONObject object;

                        String result = reader.getString("result");
                        JSONObject jsObj = new JSONObject(result);
                        int totalRecord=0,countPage=0;
                        if(jsObj!=null) {
                            if(TextUtils.isEmpty(jsObj.getString("count")))
                            {
                                totalRecord=0;
                            }
                            else {
                                totalRecord = jsObj.getInt("count");
                            }

                            if(TextUtils.isEmpty(jsObj.getString("count_page")))
                            {
                                countPage=0;
                            }
                            else {
                                countPage = jsObj.getInt("count_page");
                            }
//                            totalRecord = jsObj.getInt("count");
//                            countPage = jsObj.getInt("count_page");
                            JSONArray jsonArray = jsObj.getJSONArray("retailers");
                            Log.e("", "jsonArray= " + jsonArray.toString());
                            JSONObject object;
                            for (int count = 0; count < jsonArray.length(); count++) {
                                object = jsonArray.getJSONObject(count);
                                StoreModel storeModel = new StoreModel(
                                        object.getString("id"),
                                        object.getString("shop_name"),
                                        object.getString("shop_logo"),
                                        object.getString("shop_category"),
                                        object.getString("favourite_status")

                                );
                                list.add(storeModel);
                            }
                            storelist.success(list, totalRecord, countPage);
                        }
                        else
                        {
                            storelist.error("Data not found");
                        }

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
                params.put("pageno", String.valueOf(currentPagNo));
                params.put("perpage", "12");
                params.put("latitude", UserSharedPrefManager.GetLat(context));
                params.put("longitude", UserSharedPrefManager.GetLong(context));
//                params.put("dist", UserSharedPrefManager.GetDistance(context));
//                params.put("dist", "1");
                Log.e("","Input param= "+params.toString());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    public void StoreFilterSingle(final String userid,final String catid) {
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
                        storelist.success(list,0,0);


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



    private void showpDialog() {
        if (!progress.isShowing())
            progress.show();
    }

    private void hidepDialog() {
        if (progress.isShowing())
            progress.dismiss();
    }

}