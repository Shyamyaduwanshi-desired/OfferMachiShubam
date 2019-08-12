package com.desired.offermachi.retalier.presenter;

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
import com.desired.offermachi.retalier.model.ViewOfferModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewOfferDetailPresenter {
    private Context context;
    private OfferDiscount offerDiscount;

    public ViewOfferDetailPresenter(Context context, OfferDiscount offerDiscount) {
        this.context = context;
        this.offerDiscount=offerDiscount;
    }

    public interface OfferDiscount{
        void success(String response);
        void error(String response);
        void fail(String response);
    }
    public void getOfferDiscount(final String offerid) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Get Offer & Discount Please Wait..");
        progress.setCancelable(false);
        progress.show();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "single_offer_details_data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                   /* Log.e("offer", "result="+response );*/
                    int status = reader.getInt("status");
                    String message=reader.getString("message");
                    if (status == 200) {
                        offerDiscount.success(reader.getString("result"));
                    } else if (status == 404) {
                        offerDiscount.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    offerDiscount.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                offerDiscount.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("offer_id",offerid);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }


}