package com.desired.offermachi.retalier.view.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DashboardMenuActivity extends AppCompatActivity {
    String idholder;
    private ArrayList<DealsModel> list;
    private ProgressDialog pDialog;
    TextView activeoffer,followerscount;
    private static final String ROOT_URL = "http://offermachi.in/api/get_retailer_dashboard";
    RecyclerView deals_day_recyclerView,expiressoon_recyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_dashboard_fragment);
        list=new ArrayList<>();
        init();
    }

    private void init() {
        UserModel user = SharedPrefManagerLogin.getInstance(this).getUser();
        idholder= user.getId();

        activeoffer = (TextView)findViewById(R.id.active_offer_count_id);
         followerscount = (TextView)findViewById(R.id.followers_count_id);

        deals_day_recyclerView=(RecyclerView)findViewById(R.id.recyclerview_deal_today_id);
        deals_day_recyclerView.setHasFixedSize(true);
        deals_day_recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        if(isNetworkConnected()){
            Fetchlocationdata();
        }else {
            Toast.makeText(getApplicationContext(), "Please connect to internet", Toast.LENGTH_SHORT).show();
        }

    }
    private void Fetchlocationdata() {
        Cache cache = new DiskBasedCache(getApplicationContext().getCacheDir(), 1024 * 1024);

        // Use HttpURLConnection as the HTTP client
        Network network = new BasicNetwork(new HurlStack());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (null == response || response.length() == 0) {
                            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                                JSONObject mainJson = new JSONObject(response);
                                int status = mainJson.getInt("status");
                                if (status == 200) {
                                    String result = mainJson.getString("result");

                                    JSONArray jsonArray = new JSONArray(result);

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jobj = jsonArray.getJSONObject(i);
                                        String active_offers = jobj.getString("active_offers");
                                        String followers = jobj.getString("followers");
                                        String deal_today = jobj.getString("deal_today");
                                        String expires_soon = jobj.getString("expires_soon");


                                        activeoffer.setText(active_offers);
                                        followerscount.setText(followers);





















//                                        JSONArray jsonArray2=new JSONArray(deal_today);
//                                        JSONObject object;
//                                        for (int count = 0; count < jsonArray2.length(); count++) {
//                                            object = jsonArray2.getJSONObject(count);
//                                            DealsModel dealsModel=new DealsModel(
//                                                    object.getString("id"),
//                                                    object.getString("offer_id"),
//                                                    object.getString("offer_title"),
//                                                    object.getString("offer_category"),
//                                                    object.getString("sub_category"),
//                                                    object.getString("offer_type"),
//                                                    object.getString("offer_type_name"),
//                                                    object.getString("offer_value"),
//                                                    object.getString("offer_details"),
//                                                    object.getString("start_date"),
//                                                    object.getString("end_date"),
//                                                    object.getString("alltime"),
//                                                    object.getString("description"),
//                                                    object.getString("coupon_code"),
//                                                    object.getString("posted_by"),
//                                                    object.getString("status"),
//                                                    object.getString("offer_brand_name"),
//                                                    object.getString("deals_of_the_day_status"),
//                                                    object.getString("offer_image"),
//                                                    object.getString("qr_code_image")
//
//                                            );
//                                            list.add(dealsModel);
//                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
//                            displayData1();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
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



        DealsOfDayAdapter dealsOfDayAdapter = new DealsOfDayAdapter(getApplicationContext(),list);
        deals_day_recyclerView.setAdapter(dealsOfDayAdapter);
        if (dealsOfDayAdapter.getItemCount() == 0) {
            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
        } else {

        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}