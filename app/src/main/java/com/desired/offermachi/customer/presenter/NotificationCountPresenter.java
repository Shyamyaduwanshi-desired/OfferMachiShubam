package com.desired.offermachi.customer.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.customer.model.NotificationModel;
import com.desired.offermachi.retalier.constant.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NotificationCountPresenter {
    private Context context;
    private ProgressDialog progress;
    public NotiUnReadCount notiUnreadCount;
    public NotificationCountPresenter(Context context, NotiUnReadCount notiUnreadCount) {
        this.context = context;
        this.notiUnreadCount = notiUnreadCount;
    }

    public interface NotiUnReadCount{
        void successnoti(String response);
        void errornoti(String response);
        void failnoti(String response);
    }

    public void NotificationUnreadCount(final String userid) {

        if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "get_unread_push_notifications_count_data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!((Activity) context).isFinishing()) {
                    hidepDialog();
                }

                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    String  message = reader.getString("message");
                    if(status == 200){
                        String result=reader.getString("result");
                        JSONObject readerresult= new JSONObject(result);
//                        String push_notifications_count= readerresult.getString("push_notifications_count");
                        notiUnreadCount.successnoti(readerresult.getString("push_notifications_unread_count"));

                    }else {
                        notiUnreadCount.errornoti(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    notiUnreadCount.failnoti("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(!((Activity) context).isFinishing()) {
                    hidepDialog();
                }
                notiUnreadCount.failnoti("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("type", "customer");
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
