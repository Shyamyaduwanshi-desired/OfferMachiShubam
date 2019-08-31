package com.desired.offermachi.customer.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.customer.view.activity.OtpActivtivity;
import com.desired.offermachi.customer.view.activity.SmartShoppingRemoveActivity;
import com.desired.offermachi.retalier.constant.AppData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SmartShoppingOfferPresenter {
    private Context context;
    private NotificationOfferList notificationOfferList;

    public SmartShoppingOfferPresenter(Context context, NotificationOfferList notificationOfferList) {
        this.context = context;
        this.notificationOfferList = notificationOfferList;

    }

    public interface NotificationOfferList {
        void success(String response);

        void error(String response);

        void fail(String response);
    }

    public void sentRequest(final String userid, final String latitude, final String longitude,final String catid,final String distance) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "smart_shoping_get_store_details_by_range", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        notificationOfferList.success(reader.getString("message"));
                      //  Toast.makeText(context, ""+reader.getString("message"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, SmartShoppingRemoveActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();

                    } else if (status == 404) {
                        notificationOfferList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    notificationOfferList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                notificationOfferList.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",userid);
                params.put("latitude",latitude );
                params.put("longitude",longitude );
                params.put("category_id",catid);
                params.put("distance",distance);
                Log.e("map", "params===="+params );
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }
}
