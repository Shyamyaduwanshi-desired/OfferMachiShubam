package com.desired.offermachi.retalier.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.customer.model.NotificationModel;
import com.desired.offermachi.customer.presenter.CustomerNotificationPresenter;
import com.desired.offermachi.retalier.constant.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RetailerNotificationPresenter {
    private Context context;
    private RetailerNotificationList retailerNotificationList;

    public RetailerNotificationPresenter(Context context, RetailerNotificationList retailerNotificationList) {
        this.context = context;
        this.retailerNotificationList = retailerNotificationList;

    }

    public interface RetailerNotificationList{
        void success(ArrayList<NotificationModel> response);
        void error(String response);
        void fail(String response);
    }

    public void sentRequest(final String userid) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        final ArrayList<NotificationModel> list = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "select_all_push_notifications_data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                        String result=reader.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        JSONObject object;
                        for (int count = 0; count < jsonArray.length(); count++) {
                            object = jsonArray.getJSONObject(count);
                            NotificationModel notificationModel = new NotificationModel(
                                    object.getString("title"),
                                    object.getString("description")
                            );
                            list.add(notificationModel);
                        }
                        retailerNotificationList.success(list);

                    }else if(status == 404){
                        retailerNotificationList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    retailerNotificationList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                retailerNotificationList.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("type", "retailer");
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }
}
