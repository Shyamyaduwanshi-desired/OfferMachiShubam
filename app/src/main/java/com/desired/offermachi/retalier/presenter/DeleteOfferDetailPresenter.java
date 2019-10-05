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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DeleteOfferDetailPresenter {
    private Context context;
    private DeleteOffer deleteOffer;

    public DeleteOfferDetailPresenter(Context context, DeleteOffer deleteOffer) {
        this.context = context;
        this.deleteOffer=deleteOffer;
    }

    public interface DeleteOffer{
        void success(String response);
        void error(String response);
        void fail(String response);
    }

    public void deleteOffer(final String userId,final String offerid) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setCancelable(false);
        progress.show();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "offer_delete_by_offer_id_retailer_id", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    /* Log.e("offer", "result="+response );*/
                    int status = reader.getInt("status");
                    String message=reader.getString("message");
                    if (status == 200) {
                        deleteOffer.success(reader.getString("result"));
                    } else if (status == 404) {
                        deleteOffer.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    deleteOffer.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                deleteOffer.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("offer_id",offerid);
                params.put("user_id",userId);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

}