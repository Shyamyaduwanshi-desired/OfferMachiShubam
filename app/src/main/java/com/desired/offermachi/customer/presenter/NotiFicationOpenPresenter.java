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
import com.desired.offermachi.retalier.constant.AppData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotiFicationOpenPresenter {
    private Context context;
    private  OpenNotificationInfo readNoti;
    private ProgressDialog progress;
//    String push_notifications_count;

    public NotiFicationOpenPresenter(Context context, OpenNotificationInfo readNoti) {
        this.context = context;
        this.readNoti = readNoti;
    }

    public interface OpenNotificationInfo{
        void successReadNoti(String response);
        void errornoti(String response);
        void failnoti(String response);
    }

    public void ReadNotification(final String userid,final String offerId) {

        if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }
//        http://offermachi.in/api/push_notification_open_status


        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "push_notification_open_status", new Response.Listener<String>() {
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
//                        String result=reader.getString("result");
                        readNoti.successReadNoti(reader.getString("result"));
                    }else {
                        readNoti.errornoti(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    readNoti.failnoti("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(!((Activity) context).isFinishing()) {
                    hidepDialog();
                }
                readNoti.failnoti("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("push_notification_id", offerId);
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
