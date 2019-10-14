package com.desired.offermachi.retalier.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.desired.offermachi.R;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.DealsModel;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.view.adapter.DealsOfDayAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DasBoardMenuFragment extends Fragment {
    View view;
    String idholder;
    private ArrayList<DealsModel> alDealModel;
    private ProgressDialog pDialog;
    TextView activeoffer,followerscount,dealtoday,expiressoon;
    private static final String ROOT_URL = "http://offermachi.in/api/get_retailer_dashboard";
    RecyclerView deals_day_recyclerView,expiressoon_recyclerview;
    private DealsOfDayAdapter dealsOfDayAdapter;


    public DasBoardMenuFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.retalier_dashboard_fragment, container, false);
        alDealModel=new ArrayList<DealsModel>();
        init();
        return view;
    }

    private void init() {
        UserModel user = SharedPrefManagerLogin.getInstance(getContext()).getUser();
        idholder= user.getId();

        activeoffer = (TextView)view.findViewById(R.id.active_offer_count_id);
        followerscount = (TextView)view.findViewById(R.id.followers_count_id);followerscount = (TextView)view.findViewById(R.id.followers_count_id);
        dealtoday = (TextView)view.findViewById(R.id.deal_count_id);
        expiressoon = (TextView)view.findViewById(R.id.expires_id);

        deals_day_recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview_deal_today_id);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        deals_day_recyclerView.setLayoutManager(gridLayoutManager);
        deals_day_recyclerView.setItemAnimator(new DefaultItemAnimator());
        deals_day_recyclerView.setNestedScrollingEnabled(true);

        expiressoon_recyclerview=(RecyclerView)view.findViewById(R.id.recyclerview_expiresson_id);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        expiressoon_recyclerview.setLayoutManager(gridLayoutManager1);
        expiressoon_recyclerview.setItemAnimator(new DefaultItemAnimator());
        expiressoon_recyclerview.setNestedScrollingEnabled(true);

        if(isNetworkConnected()){
            Fetchlocationdata();
        }else {
            Toast.makeText(getActivity(), "Please connect to internet", Toast.LENGTH_SHORT).show();
        }

    }

    private void Fetchlocationdata() {
        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 1024 * 1024);

        // Use HttpURLConnection as the HTTP client
        Network network = new BasicNetwork(new HurlStack());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (null == response || response.length() == 0) {
                            Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                                JSONObject mainJson = new JSONObject(response);
                                int status = mainJson.getInt("status");
                                String message=mainJson.getString("message");
                                if(status == 200){
                                    String result = mainJson.getString("result");

                                    JSONObject jsonObject = new JSONObject(result);
                                    String active_offers=jsonObject.getString("active_offers");
                                    String followers=jsonObject.getString("followers");
                                    String expires_soon=jsonObject.getString("expires_soon");
                                    String deal_today=jsonObject.getString("deal_today");
                                    String expires_soon_count=jsonObject.getString("expires_soon_count");
                                    String deal_today_count=jsonObject.getString("deal_today_count");

                                    activeoffer.setText(active_offers);
                                    followerscount.setText(followers);
                                    dealtoday.setText(deal_today_count);
                                    expiressoon.setText(expires_soon_count);
                                    //alDealModel = new Gson().fromJson(deal_today,  new TypeToken<ArrayList<DealsModel>>(){}.getType());
                                    JSONArray jsonArray2=new JSONArray(deal_today);
                                    JSONObject object2;
                                    for (int count = 0; count < jsonArray2.length(); count++) {
                                        object2 = jsonArray2.getJSONObject(count);

                                        DealsModel dealsModel=new DealsModel(
                                                object2.optString("id"),
                                                object2.optString("offer_id"),
                                                object2.optString("offer_title"),
                                                object2.optString("offer_category"),
                                                object2.optString("sub_category"),
                                                object2.optString("offer_type"),
                                                object2.optString("offer_type_name"),
                                                object2.optString("offer_value"),
                                                object2.optString("offer_details"),
                                                object2.optString("start_date"),
                                                object2.optString("end_date"),
                                                object2.optString("alltime"),
                                                object2.optString("description"),
                                                object2.optString("coupon_code"),
                                                object2.optString("posted_by"),
                                                object2.optString("status"),
                                                object2.optString("offer_brand_name"),
                                                object2.optString("deals_of_the_day_status"),
                                                object2.optString("offer_image"),
                                                object2.optString("qr_code_image")

                                        );
                                        alDealModel.add(dealsModel);
                                    }
                                    JSONArray jsonArray3=new JSONArray(deal_today);
                                    JSONObject object3;
                                    for (int count = 0; count < jsonArray3.length(); count++) {
                                        object2 = jsonArray3.getJSONObject(count);

                                        DealsModel dealsModel=new DealsModel(
                                                object2.optString("id"),
                                                object2.optString("offer_id"),
                                                object2.optString("offer_title"),
                                                object2.optString("offer_category"),
                                                object2.optString("sub_category"),
                                                object2.optString("offer_type"),
                                                object2.optString("offer_type_name"),
                                                object2.optString("offer_value"),
                                                object2.optString("offer_details"),
                                                object2.optString("start_date"),
                                                object2.optString("end_date"),
                                                object2.optString("alltime"),
                                                object2.optString("description"),
                                                object2.optString("coupon_code"),
                                                object2.optString("posted_by"),
                                                object2.optString("status"),
                                                object2.optString("offer_brand_name"),
                                                object2.optString("deals_of_the_day_status"),
                                                object2.optString("offer_image"),
                                                object2.optString("qr_code_image")

                                        );
                                        alDealModel.add(dealsModel);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            displayData1();
                            displayData2();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",idholder);
                return params;
            }
        };
        RequestQueue queue = new RequestQueue(cache, network);
        queue.start();
        queue.add(stringRequest);

    }

    private void displayData1() {

        dealsOfDayAdapter = new DealsOfDayAdapter(getContext(), alDealModel);
        deals_day_recyclerView.setAdapter(dealsOfDayAdapter);
        if (dealsOfDayAdapter.getItemCount() == 0) {
            Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
        } else {

        }

    }
    private void displayData2 () {

        dealsOfDayAdapter = new DealsOfDayAdapter(getContext(), alDealModel);
        expiressoon_recyclerview.setAdapter(dealsOfDayAdapter);
        if (dealsOfDayAdapter.getItemCount() == 0) {
            Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
        } else {

        }

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
