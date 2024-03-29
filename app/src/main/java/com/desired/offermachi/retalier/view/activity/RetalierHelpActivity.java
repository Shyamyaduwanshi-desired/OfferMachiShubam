package com.desired.offermachi.retalier.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.desired.offermachi.customer.view.activity.InfoActivity;
import com.desired.offermachi.retalier.model.FAQ;
import com.desired.offermachi.retalier.view.adapter.FaqAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RetalierHelpActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageViewback,info;
    RecyclerView recyclerView;
    private ProgressDialog pDialog;
    private static final String ROOT_URL = "http://offermachi.in/api/help_faq";
    private ArrayList<FAQ> faqList;
    TextView txtphone,txtemail;
    ImageView imgNotiBell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_help_activity);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        faqList=new ArrayList<>();
        init();
    }
    private void init(){
//        txtemail=findViewById(R.id.email);
//        txtphone=findViewById(R.id.phonenumber);
        imageViewback = findViewById(R.id.imageback);
        imageViewback.setOnClickListener(this);
        info=findViewById(R.id.info_id);
        info.setOnClickListener(this);
        recyclerView=(RecyclerView)findViewById(R.id.liveshiplist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(RetalierHelpActivity.this));
        if(isNetworkConnected()){
           Fetchfaq();
        }else {
            Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show();
        }
        imgNotiBell=findViewById(R.id.imgNotiBell);
        imgNotiBell.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==imageViewback){
            onBackPressed();
        }else if (v==imgNotiBell){
            startActivity(new Intent(getApplicationContext(), RetalierNotificationActivity.class));
        }else if(v==info){
            Intent intent = new Intent(RetalierHelpActivity.this, InfoActivity.class);
            startActivity(intent);
        }
    }
    private void Fetchfaq() {
        showpDialog();
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);

        // Use HttpURLConnection as the HTTP client
        Network network = new BasicNetwork(new HurlStack());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hidepDialog();
                        if (null == response || response.length() == 0) {
                            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                                JSONObject mainJson = new JSONObject(response);
                                String status=mainJson.getString("status");
                                String message=mainJson.getString("message");
                                if (status.equals("200")) {
                                    String result = mainJson.getString("result");

                                    JSONArray jsonArray = new JSONArray(result);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jobj = jsonArray.getJSONObject(i);
                                        String id = jobj.getString("id");
                                        String post_title = jobj.getString("post_title");
                                        String post_body = jobj.getString("post_body");
                                        String post_type = jobj.getString("post_type");
                                        String statuss = jobj.getString("status");
                                        FAQ faq = new FAQ(
                                                id,post_title, post_body
                                        );

                                        faqList.add(faq);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            displayData1();
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
                params.put("type", "retailer");
                return params;
            }
        };
        RequestQueue queue = new RequestQueue(cache, network);
        queue.start();
        queue.add(stringRequest);

        /*RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);*/
    }
    private void displayData1() {
        FaqAdapter faqAdapter = new FaqAdapter(getApplicationContext(),faqList);
        recyclerView.setAdapter(faqAdapter);
        if (faqAdapter.getItemCount() == 0) {
            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
        } else {

        }

    }
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}