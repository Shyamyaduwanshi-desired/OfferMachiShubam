package com.desired.offermachi.customer.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.NotificationModel;
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.customer.view.activity.OtpActivtivity;
import com.desired.offermachi.customer.view.activity.ViewOfferActivity;
import com.desired.offermachi.retalier.constant.AppData;
import com.desired.offermachi.retalier.model.FAQ;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CustomerNotificationPresenter {
    private Context context;
    private NotificationList notificationList;
    private ProgressDialog progress;
    String idholder;

    public CustomerNotificationPresenter(Context context, NotificationList notificationList) {
        this.context = context;
        this.notificationList = notificationList;

    }

    public interface NotificationList{
        void success(ArrayList<NotificationModel> response,int totalRecords,int totalPages);
        void error(String response);
        void fail(String response);
    }

    public void sentRequest(final String userid) {

        if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }
        final ArrayList<NotificationModel> list = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "select_all_push_notifications_data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!((Activity) context).isFinishing()) {
                    hidepDialog();
                }
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                        String result=reader.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        JSONObject object;
                        for (int count = 0; count < jsonArray.length(); count++) {
                            object = jsonArray.getJSONObject(count);
                            String custom_offertype = object.getString("custom_offertype");

                            NotificationModel notificationModel = new NotificationModel(
                                    object.getString("id"),
                                    object.getString("title"),
                                    object.getString("description"),
                                    object.getString("is_open"),
                                    object.getString("custom_offertype")
                            );

                            list.add(notificationModel);
                        }
                        Collections.reverse(list);
                        notificationList.success(list,0,0);
                    }else if(status == 404){
                        notificationList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    notificationList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(!((Activity) context).isFinishing()) {
                    hidepDialog();
                }
                notificationList.fail("Server Error.\n Please try after some time.");
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

    public void AllNotiWithPagination(final String userid,int currentPagNo) {

        if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }
        final ArrayList<NotificationModel> list = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "select_all_push_notifications_data_page", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!((Activity) context).isFinishing()) {
                    hidepDialog();
                }
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
//                        String result=reader.getString("result");
//                        JSONArray jsonArray = new JSONArray(result);
//                        JSONObject object;
                        String result = reader.getString("result");
                        JSONObject jsObj = new JSONObject(result);
                        int totalRecord=0,countPage=0;
                        if(jsObj!=null) {
                            if (TextUtils.isEmpty(jsObj.getString("count"))) {
                                totalRecord = 0;
                            } else {
                                totalRecord = jsObj.getInt("count");
                            }

                            if (TextUtils.isEmpty(jsObj.getString("count_page"))) {
                                countPage = 0;
                            } else {
                                countPage = jsObj.getInt("count_page");
                            }
//                            totalRecord = jsObj.getInt("count");
//                            countPage = jsObj.getInt("count_page");
                            JSONArray jsonArray = jsObj.getJSONArray("push_notifications");
                            Log.e("", "jsonArray= " + jsonArray.toString());
                            JSONObject object;
                            for (int count = 0; count < jsonArray.length(); count++) {
                                object = jsonArray.getJSONObject(count);
                                String custom_offertype = object.getString("custom_offertype");

                                NotificationModel notificationModel = new NotificationModel(
                                        object.getString("id"),
                                        object.getString("title"),
                                        object.getString("description"),
                                        object.getString("is_open"),
                                        object.getString("custom_offertype")
                                );

                                list.add(notificationModel);
                            }
                            Collections.reverse(list);
                            notificationList.success(list,totalRecord,countPage);
                        }
                        else
                        {
                            notificationList.error("Data not found");
                        }
                    }else if(status == 404){
                        notificationList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    notificationList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(!((Activity) context).isFinishing()) {
                    hidepDialog();
                }
                notificationList.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("type", "customer");
                params.put("pageno", String.valueOf(currentPagNo));
                params.put("perpage", "10");
//                params.put("latitude", UserSharedPrefManager.GetLat(context));
//                params.put("longitude", UserSharedPrefManager.GetLong(context));
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
