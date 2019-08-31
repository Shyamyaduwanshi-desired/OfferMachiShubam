package com.desired.offermachi.customer.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.retalier.constant.AppData;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class StoreDetailPresenter {
    private Context context;
    private StoreDetail storeDetail;
    private ProgressDialog progress;

    public StoreDetailPresenter(Context context, StoreDetail storeDetail) {
        this.context = context;
        this.storeDetail = storeDetail;

    }

    public interface StoreDetail {
        void success(String response);

        void relatedsuccess(String response);

        void error(String response);

        void fail(String response);
    }

    public void StoreDetail(final String userid, final String retailerid, final String categoryid) {
        if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "select_single_store_data_and_related_store", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("store", "response=="+response );
               hidepDialog();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        String result=reader.getString("result");
                        JSONObject reader2 = new JSONObject(result);
                        storeDetail.success(reader2.getString("single_store_data"));
                        storeDetail.relatedsuccess(reader2.getString("related_stores"));

                    } else if (status == 404) {
                        storeDetail.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    storeDetail.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               hidepDialog();
                storeDetail.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("retailer_id", retailerid);
                params.put("category_id", categoryid);
                Log.e("postdata", "postparams= "+params );
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