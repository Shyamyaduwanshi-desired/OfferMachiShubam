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
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.retalier.constant.AppData;

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

public class CustomerFeedsPresenter {
    private Context context;
    private FeedsList feedsList;
    private ProgressDialog progress;

    public CustomerFeedsPresenter(Context context, FeedsList feedsList) {
        this.context = context;
        this.feedsList = feedsList;

    }

    public interface FeedsList {
        //diff=1 all feeds data,diff=2 all filter feeds data,diff=3 all short by feeds data,
        void success(ArrayList<SelectCategoryModel> response,int totalRecords,int totalPages,int diff);

        void favsuc(String  response);

        void getsuc(String  response);

        void error(String response);

        void fail(String response);
    }

    public void ViewAllFeeds(final String userid,int loadItem) {
        if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }
        final ArrayList<SelectCategoryModel> list = new ArrayList<>();//select_customer_feeds_data
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "select_customer_feeds_data", new Response.Listener<String>() {
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
                            String favStatus= object.getString("favourite_status");
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
                                    object.getString("shop_logo")

                            );
                            if(loadItem==0)
                            {
                                list.add(selectCategoryModel);
                            }
                            else
                            {
                                if(count<loadItem)
                                {
                                    list.add(selectCategoryModel);
                                }

                            }
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

                        feedsList.success(list,0,0,1);




//                        Collections.sort(datestring, new Comparator<String>() {
//                            DateFormat f = new SimpleDateFormat("MM/dd/yyyy '@'hh:mm a");
//                            @Override
//                            public int compare(String o1, String o2) {
//                                try {
//                                    return f.parse(o1).compareTo(f.parse(o2));
//                                } catch (ParseException e) {
//                                    throw new IllegalArgumentException(e);
//                                }
//                            }
//                        });


                    } else if (status == 404) {
                        feedsList.error(reader.getString("message"));
                        hidepDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    hidepDialog();
                    feedsList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               hidepDialog();
                feedsList.fail("Server Error.\n Please try after some time.");
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

    public void ViewAllFeedsWithPagination(final String userid,int currentPagNo) {
        if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }
        final ArrayList<SelectCategoryModel> list = new ArrayList<>();//select_customer_feeds_data
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "select_customer_feeds_data_page", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               hidepDialog();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        String result = reader.getString("result");
                        JSONObject jsObj = new JSONObject(result);
                        int totalRecord=0,countPage=0;
                        if(jsObj!=null)
                        {
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
//                            totalRecord= jsObj.getInt("count");
//                            countPage= jsObj.getInt("count_page");
                        JSONArray jsonArray =jsObj.getJSONArray("offers");
                        Log.e("","jsonArray= "+jsonArray.toString());
                        JSONObject object;
                        for (int count = 0; count < jsonArray.length(); count++) {
                            object = jsonArray.getJSONObject(count);
//                            String favStatus= object.getString("favourite_status");
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
                                    object.getString("shop_logo")

                            );
                              list.add(selectCategoryModel);
                        }

//                        Collections.sort(list, new Comparator<SelectCategoryModel>() {//yyyy-MM-dd
//                            DateFormat f = new SimpleDateFormat("yyyy-MM-dd");//2019-09-26
//                            @Override
//                            public int compare(SelectCategoryModel lhs, SelectCategoryModel rhs) {
//                                try {
//                                    return f.parse(lhs.getOfferstartdate()).compareTo(f.parse(rhs.getOfferstartdate()));
//                                } catch (ParseException e) {
//                                    throw new IllegalArgumentException(e);
//                                }
//                            }
//                        });
//


                        Collections.reverse(list);
                        }
                        feedsList.success(list,totalRecord,countPage,1);

//                        Collections.sort(datestring, new Comparator<String>() {
//                            DateFormat f = new SimpleDateFormat("MM/dd/yyyy '@'hh:mm a");
//                            @Override
//                            public int compare(String o1, String o2) {
//                                try {
//                                    return f.parse(o1).compareTo(f.parse(o2));
//                                } catch (ParseException e) {
//                                    throw new IllegalArgumentException(e);
//                                }
//                            }
//                        });

                    } else if (status == 404) {
                        feedsList.error(reader.getString("message"));
                        hidepDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    hidepDialog();
                    feedsList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               hidepDialog();
                feedsList.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("pageno", String.valueOf(currentPagNo));
                params.put("perpage", "10");
                Log.e("","Input param= "+params.toString());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    public void FilterWithPagination(final String userid,final String catid,int currentPagNo) {
       /* if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }*///select_customer_feeds_data_by_filter_categories_page
        final ArrayList<SelectCategoryModel> list = new ArrayList<>();//select_customer_feeds_data_by_filter,select_customer_feeds_data_by_filter_categories
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "select_customer_feeds_data_by_filter_categories_page", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  hidepDialog();
                try {
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
                           /* totalRecord = jsObj.getInt("count");
                            countPage = jsObj.getInt("count_page");*/
                            JSONArray jsonArray = jsObj.getJSONArray("offers");
                            Log.e("", "jsonArray= " + jsonArray.toString());
                            JSONObject object;
                            for (int count = 0; count < jsonArray.length(); count++) {
                                object = jsonArray.getJSONObject(count);
                                SelectCategoryModel selectCategoryModel = new SelectCategoryModel(
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
                                        object.getString("shop_logo")


                                );
                                list.add(selectCategoryModel);
                            }
                        }
                        feedsList.success(list,totalRecord,countPage,2);//2 for filter


                    } else if (status == 404) {
                        feedsList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //  hidepDialog();
                    feedsList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // hidepDialog();
                feedsList.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("categories_id", catid);
                params.put("pageno", String.valueOf(currentPagNo));
                params.put("perpage", "10");
                Log.e("","Input param= "+params.toString());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    public void ShortByWithPagination(final String userid,final String status,int currentPagNo) {
        final ArrayList<SelectCategoryModel> list = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_feeds_data_by_sort_by_page", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // hidepDialog();
                try {
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
                            JSONArray jsonArray = jsObj.getJSONArray("offers");
                            Log.e("", "jsonArray= " + jsonArray.toString());
                            JSONObject object;
                            for (int count = 0; count < jsonArray.length(); count++) {
                                object = jsonArray.getJSONObject(count);
                                SelectCategoryModel selectCategoryModel = new SelectCategoryModel(
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
                                        object.getString("shop_logo")
                                );
                                list.add(selectCategoryModel);
                            }
                        }
                        feedsList.success(list,totalRecord,countPage,3);


                    } else if (status == 404) {
                        feedsList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    feedsList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  hidepDialog();
                feedsList.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("sort_by", status);
                params.put("pageno", String.valueOf(currentPagNo));
                params.put("perpage", "10");
                Log.e("","Input param= "+params.toString());
                return params;
            }
        };

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
                        feedsList.favsuc(reader.getString("message"));

                    }else if(status == 404){
                        feedsList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    feedsList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                feedsList.fail("Server Error.\n Please try after some time.");
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


    public void Filter(final String userid,final String catid) {
       /* if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }*///select_customer_feeds_data_by_filter_categories_page
        final ArrayList<SelectCategoryModel> list = new ArrayList<>();//select_customer_feeds_data_by_filter,select_customer_feeds_data_by_filter_categories
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "select_customer_feeds_data_by_filter_categories_page", new Response.Listener<String>() {
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
                                    object.getString("shop_logo")
                            );

                            list.add(selectCategoryModel);
                        }
                        feedsList.success(list,0,0,2);


                    } else if (status == 404) {
                        feedsList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                  //  hidepDialog();
                    feedsList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // hidepDialog();
                feedsList.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("categories_id", catid);
                Log.e("","Input param= "+params.toString());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    public void ShortBy(final String userid,final String status) {
      /*  if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }*/
        final ArrayList<SelectCategoryModel> list = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_feeds_data_by_sort_by", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // hidepDialog();
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
                                    object.getString("shop_logo")
                            );
                            list.add(selectCategoryModel);
                        }
                        feedsList.success(list,0,0,3);


                    } else if (status == 404) {
                        feedsList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    feedsList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  hidepDialog();
                feedsList.fail("Server Error.\n Please try after some time.");
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
