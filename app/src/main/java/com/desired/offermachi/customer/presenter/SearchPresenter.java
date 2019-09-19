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
import com.desired.offermachi.customer.model.SearchBean;
import com.desired.offermachi.retalier.constant.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchPresenter {
    private Context context;
    private SearchListInfo searchList;
    private ProgressDialog progress;

    public SearchPresenter(Context context, SearchListInfo searchList) {
        this.context = context;
        this.searchList = searchList;

    }

    public interface SearchListInfo {
        void successSearch(ArrayList<SearchBean> responseByStore,ArrayList<SearchBean> responseByOffer,ArrayList<SearchBean> responseByLocation,ArrayList<String> response,String status);

//        void success(ArrayList<SearchBean> response);

        void error(String response);

        void fail(String response);
    }

    public void GetSearchList(final String catid,final String text) {
        if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }
        final ArrayList<SearchBean> listStoreName = new ArrayList<>();
        final ArrayList<SearchBean> listByOffer = new ArrayList<>();
        final ArrayList<SearchBean> listByLocation = new ArrayList<>();
        final ArrayList<String> alllistNm = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "common_index_search_list", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!((Activity) context).isFinishing()) {
                    hidepDialog();
                }
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    String message = reader.getString("message");
                    if (status == 200) {
                        listStoreName.clear();
//                        listByOffer.clear();
//                        listByLocation.clear();
                        String result=reader.getString("result");
                        JSONObject mainJsObj = new JSONObject(result);
                        String Search_By_Store_Name=mainJsObj.getString("Search By Store Name");
                        String Search_By_Offer=mainJsObj.getString("Search By offer");
                        String Search_By_location=mainJsObj.getString("Search By Location");

                        JSONArray jsonArraySearchByStore = new JSONArray(Search_By_Store_Name);
                        JSONArray jsonArraySearchByOffer = new JSONArray(Search_By_Offer);
                        JSONArray jsonArraySearchBylocation = new JSONArray(Search_By_location);
                        JSONObject object;
                        SearchBean searchBean;
                        for (int count = 0; count < jsonArraySearchByStore.length(); count++) {
                            object = jsonArraySearchByStore.getJSONObject(count);

                            searchBean=new SearchBean();
                            searchBean.setId(object.getString("id"));
                            searchBean.setName(object.getString("shop_name"));
                            searchBean.setType(object.getString("type"));
                            listStoreName.add(searchBean);
                            alllistNm.add(object.getString("shop_name"));
                        }

                        JSONObject objectByOffer;
                        for (int count = 0; count < jsonArraySearchByOffer.length(); count++) {
                            objectByOffer = jsonArraySearchByOffer.getJSONObject(count);

                            searchBean=new SearchBean();
                            searchBean.setId(objectByOffer.getString("id"));
                            searchBean.setName(objectByOffer.getString("offer_title_slug"));
                            searchBean.setType(objectByOffer.getString("type"));
//                            listByOffer.add(searchBean);
                            listByOffer.add(searchBean);

                            alllistNm.add(objectByOffer.getString("offer_title_slug"));
                        }
                        JSONObject objectBylocation;
                        for (int count = 0; count < jsonArraySearchBylocation.length(); count++) {
                            objectBylocation = jsonArraySearchBylocation.getJSONObject(count);

                            searchBean=new SearchBean();
                            searchBean.setId(objectBylocation.getString("id"));
                            searchBean.setName(objectBylocation.getString("locality_name"));
                            searchBean.setType(objectBylocation.getString("type"));
//                            listByLocation.add(searchBean);
                            listByLocation.add(searchBean);
                            alllistNm.add(objectBylocation.getString("locality_name"));
                        }

                        searchList.successSearch(listStoreName,listByOffer,listByLocation,alllistNm,"");
//                        searchList.successSearch(list,listByOffer,listByLocation,alllistNm,"");
                    } else if (status == 404) {
                        searchList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    searchList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(!((Activity) context).isFinishing()) {
                    hidepDialog();
                }
                searchList.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("category", catid);
                params.put("search_text", text);
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
