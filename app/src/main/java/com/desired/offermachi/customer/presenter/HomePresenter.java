package com.desired.offermachi.customer.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.desired.offermachi.customer.view.activity.OtpActivtivity;
import com.desired.offermachi.retalier.constant.AppData;
import com.desired.offermachi.retalier.model.DealsModel;
import com.desired.offermachi.retalier.model.ViewOfferModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomePresenter {
    private Context context;
    private HomeList homeList;
    private ProgressDialog progress;

    public HomePresenter(Context context, HomeList homeList) {
        this.context = context;
        this.homeList = homeList;

    }

    public interface HomeList {
        void categorysuccess(ArrayList<SelectCategoryModel> response);

        void Offersuccess(ArrayList<SelectCategoryModel> response);

        void Storesuccess(ArrayList<StoreModel> response);

        void success(String response);

        void error(String response);

        void fail(String response);
    }

    public void GetAllListSingle(final String catid,final String userid) {
        if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }
        final ArrayList<SelectCategoryModel> list = new ArrayList<>();
        final ArrayList<SelectCategoryModel> list2 = new ArrayList<>();
        final ArrayList<StoreModel> list3 = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_home_page_offers_data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!((Activity) context).isFinishing())
                {
                    hidepDialog();
                }
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        String result = reader.getString("result");
                        JSONObject jsonObject = new JSONObject(result);
                        String catofferdata = jsonObject.getString("cat_offer_data");
                        String all_offer_data = jsonObject.getString("all_offer_data");
                        String all_store_data = jsonObject.getString("all_store_data");
                        JSONArray jsonArray = new JSONArray(catofferdata);
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
                        homeList.categorysuccess(list);

                        JSONArray jsonArray2 = new JSONArray(all_offer_data);
                        JSONObject object2;
                        for (int count = 0; count < jsonArray2.length(); count++) {
                            object2 = jsonArray2.getJSONObject(count);
                            SelectCategoryModel selectCategoryModel=new SelectCategoryModel(
                                    object2.getString("id"),
                                    object2.getString("offer_id"),
                                    object2.getString("offer_title"),
                                    object2.getString("offer_title_slug"),
                                    object2.getString("offer_category"),
                                    object2.getString("sub_category"),
                                    object2.getString("offer_type"),
                                    object2.getString("offer_type_name"),
                                    object2.getString("offer_value"),
                                    object2.getString("offer_details"),
                                    object2.getString("start_date"),
                                    object2.getString("end_date"),
                                    object2.getString("alltime"),
                                    object2.getString("description"),
                                    object2.getString("coupon_code"),
                                    object2.getString("posted_by"),
                                    object2.getString("status"),
                                    object2.getString("offer_brand_name"),
                                    object2.getString("favourite_status"),
                                    object2.getString("offer_image"),
                                    object2.getString("qr_code_image"),
                                    object2.getString("coupon_code_status"),
                                    object2.getString("shop_logo")
                            );
                            list2.add(selectCategoryModel);
                        }
                        homeList.Offersuccess(list2);



                        JSONArray jsonArray3 = new JSONArray(all_store_data);
                        JSONObject object3;
                        for (int count = 0; count < jsonArray3.length(); count++) {
                            object3 = jsonArray3.getJSONObject(count);
                            StoreModel storeModel=new StoreModel(
                                    object3.getString("id"),
                                    object3.getString("shop_name"),
                                    object3.getString("shop_logo"),
                                    object3.getString("shop_category"),
                                    object3.getString("favourite_status")

                            );
                            list3.add(storeModel);
                        }
                        homeList.Storesuccess(list3);

                    } else if (status == 404) {
                        homeList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    homeList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(!((Activity) context).isFinishing())
                {
                    hidepDialog();
                }

                homeList.fail("Data is not available for this category. \nPlease select another location");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("category_id", catid);
                params.put("user_id", userid);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    public void GetAllMultipleCateList(final String catid,final String userid) {
//        if(!((Activity) context).isFinishing())
        if(progress!=null)
        {
            progress.dismiss();
            progress=null;
        }

            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();

        final ArrayList<SelectCategoryModel> list = new ArrayList<>();
        final ArrayList<SelectCategoryModel> list2 = new ArrayList<>();
        final ArrayList<StoreModel> list3 = new ArrayList<>();//customer_home_page_offers_data_bycategoryid,customer_home_page_offers_data_bycategoryid2
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_home_page_offers_data_bycategoryid2", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!((Activity) context).isFinishing())
                {
                    hidepDialog();
                }

                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        String result = reader.getString("result");
                        JSONObject jsonObject = new JSONObject(result);
                        String catofferdata = jsonObject.getString("cat_offer_data");
//                        String all_offer_data = jsonObject.getString("all_offer_data");
                        String all_store_data = jsonObject.getString("all_store_data");

//                        JSONArray jsonArray = new JSONArray(catofferdata);
//                        JSONObject object;
//                        for (int count = 0; count < jsonArray.length(); count++) {
//                            object = jsonArray.getJSONObject(count);
//                            SelectCategoryModel selectCategoryModel=new SelectCategoryModel(
//                                    object.getString("id"),
//                                    object.getString("offer_id"),
//                                    object.getString("offer_title"),
//                                    object.getString("offer_category"),
//                                    object.getString("sub_category"),
//                                    object.getString("offer_type"),
//                                    object.getString("offer_type_name"),
//                                    object.getString("offer_value"),
//                                    object.getString("offer_details"),
//                                    object.getString("start_date"),
//                                    object.getString("end_date"),
//                                    object.getString("alltime"),
//                                    object.getString("description"),
//                                    object.getString("coupon_code"),
//                                    object.getString("posted_by"),
//                                    object.getString("status"),
//                                    object.getString("offer_brand_name"),
//                                    object.getString("favourite_status"),
//                                    object.getString("offer_image"),
//                                    object.getString("qr_code_image"),
//                                    object.getString("coupon_code_status")
//
//                            );
//                            list.add(selectCategoryModel);
//                        }

                        homeList.categorysuccess(list);

                        JSONArray jsonArray2 = new JSONArray(catofferdata);
                        JSONObject object2;
                        for (int count = 0; count < jsonArray2.length(); count++) {
                            object2 = jsonArray2.getJSONObject(count);
                            SelectCategoryModel selectCategoryModel=new SelectCategoryModel(
                                    object2.getString("id"),
                                    object2.getString("offer_id"),
                                    object2.getString("offer_title"),
                                    object2.getString("offer_title_slug"),
                                    object2.getString("offer_category"),
                                    object2.getString("sub_category"),
                                    object2.getString("offer_type"),
                                    object2.getString("offer_type_name"),
                                    object2.getString("offer_value"),
                                    object2.getString("offer_details"),
                                    object2.getString("start_date"),
                                    object2.getString("end_date"),
                                    object2.getString("alltime"),
                                    object2.getString("description"),
                                    object2.getString("coupon_code"),
                                    object2.getString("posted_by"),
                                    object2.getString("status"),
                                    object2.getString("offer_brand_name"),
                                    object2.getString("favourite_status"),
                                    object2.getString("offer_image"),
                                    object2.getString("qr_code_image"),
                                    object2.getString("coupon_code_status"),
                                    object2.getString("shop_logo")
                            );
                            list2.add(selectCategoryModel);
                        }

                        homeList.Offersuccess(list2);

                        JSONArray jsonArray3 = new JSONArray(all_store_data);
                        JSONObject object3;
                        for (int count = 0; count < jsonArray3.length(); count++) {
                            object3 = jsonArray3.getJSONObject(count);
                            StoreModel storeModel=new StoreModel(
                                    object3.getString("id"),
                                    object3.getString("shop_name"),
                                    object3.getString("shop_logo"),
                                    object3.getString("shop_category"),
                                    object3.getString("favourite_status")

                            );
                            list3.add(storeModel);
                        }
                        homeList.Storesuccess(list3);

                    } else if (status == 404) {
                        homeList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    homeList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(!((Activity) context).isFinishing())
                {
                    hidepDialog();
                }

                homeList.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("category_ids", catid);
                params.put("user_id", userid);

                params.put("latitude", UserSharedPrefManager.GetLat(context));
                params.put("longitude", UserSharedPrefManager.GetLong(context));
//                params.put("dist", "500");
                Log.e("","Input param= "+params.toString());
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
                        homeList.success(reader.getString("message"));

                    }else if(status == 404){
                        homeList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    homeList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                homeList.fail("Server Error.\n Please try after some time.");
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

    public void AddStoreFollow(final String userid, final String retailerid,final String active) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_add_follow_retailer", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                        homeList.success(reader.getString("message"));

                    }else if(status == 404){
                        homeList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    homeList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                homeList.fail("Server Error.\n Please try after some time.");
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