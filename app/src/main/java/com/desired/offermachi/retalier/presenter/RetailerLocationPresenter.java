package com.desired.offermachi.retalier.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.retalier.constant.AppData;
import com.desired.offermachi.retalier.model.CityBean;
import com.desired.offermachi.retalier.model.RetailerLocation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RetailerLocationPresenter {
    private Context context;
    private RetailerLocationInfo retailerLocationInfo;

    public RetailerLocationPresenter(Context context, RetailerLocationInfo retailerLocationInfo) {
        this.context = context;
        this.retailerLocationInfo = retailerLocationInfo;

    }

    public interface RetailerLocationInfo{
        void success(ArrayList<RetailerLocation> response, String status);
        void error(String response);
        void fail(String response);
    }
    ArrayList<RetailerLocation> alRetailerLocation = new ArrayList<>();
    public void GetAllRetailerLocation(String userId) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "get_retailer_locationby_reatailer_id", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");

                    if(status == 200){
                        String result=reader.getString("result");
                        alRetailerLocation = new Gson().fromJson(result,  new TypeToken<ArrayList<RetailerLocation>>(){}.getType());

                        retailerLocationInfo.success(alRetailerLocation,"1");

                    }else if(status == 404){
                        retailerLocationInfo.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    retailerLocationInfo.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                retailerLocationInfo.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userId);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

}
