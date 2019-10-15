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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.FAQ;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.model.mylocation_model;
import com.desired.offermachi.retalier.presenter.VerifyCouponcodePresenter;
import com.desired.offermachi.retalier.view.adapter.FaqAdapter;
import com.desired.offermachi.retalier.view.adapter.MyLOcationADapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyLocationActivity extends AppCompatActivity implements View.OnClickListener{

    String idholder;
    private ArrayList<mylocation_model> mylocation_modelslist;
    private ProgressDialog pDialog;
    private static final String ROOT_URL = "http://offermachi.in/api/get_retailer_locationby_reatailer_id";
    RecyclerView recyclerView;
    ImageView imageViewback,info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylocation_activity);
        init();
        mylocation_modelslist=new ArrayList<>();
    }

    private void init() {
        UserModel user = SharedPrefManagerLogin.getInstance(this).getUser();
        idholder= user.getId();
        imageViewback = findViewById(R.id.imageback);
        info=findViewById(R.id.info_id);
        imageViewback.setOnClickListener(this);
        info.setOnClickListener(this);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview_mylocation);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyLocationActivity.this));

        if(isNetworkConnected()){
            Fetchlocationdata();
        }else {
            Toast.makeText(MyLocationActivity.this, "Please connect to internet", Toast.LENGTH_SHORT).show();
        }


    }

    private void Fetchlocationdata() {
        Cache cache = new DiskBasedCache(MyLocationActivity.this.getCacheDir(), 1024 * 1024);

        // Use HttpURLConnection as the HTTP client
        Network network = new BasicNetwork(new HurlStack());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (null == response || response.length() == 0) {
                            Toast.makeText(MyLocationActivity.this, "No Data", Toast.LENGTH_SHORT).show();
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
                                        String user_id = jobj.getString("user_id");
                                        String locality_name= jobj.getString("locality_name");
                                        String location_address = jobj.getString("location_address");
                                        String location_contact_phone = jobj.getString("location_contact_phone");

                                        mylocation_model mylocation_model = new mylocation_model(
                                                id,locality_name,location_address,location_contact_phone
                                        );

                                        mylocation_modelslist.add(mylocation_model);

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
                        Toast.makeText(MyLocationActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
        MyLOcationADapter myLOcationADapter = new MyLOcationADapter(getApplicationContext(),mylocation_modelslist);
        recyclerView.setAdapter(myLOcationADapter);
        if (myLOcationADapter.getItemCount() == 0) {
            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
        } else {

        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) MyLocationActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onClick(View v) {
        if (v==imageViewback){
            onBackPressed();
        }else if(v==info){
            Intent intent = new Intent(MyLocationActivity.this, InfoActivity.class);
            startActivity(intent);

        }
    }
}