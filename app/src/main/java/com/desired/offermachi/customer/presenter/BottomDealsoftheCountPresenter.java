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

public class BottomDealsoftheCountPresenter {
    private Context context;
    private ProgressDialog progress;
    public BottomDealsoftheCountPresenter.BottomNotiRead bottomNotiRead;

    public BottomDealsoftheCountPresenter(Context context, BottomNotiRead bottomNotiRead) {
        this.context = context;
        this.bottomNotiRead = bottomNotiRead;
    }
    public interface BottomNotiRead{
        void successbottomnoti(String response,String deal,String coupon,String fav);
        void errorbottomnoti(String response);
        void failbottomnoti(String response);
    }

    public void BottomNotificationUnreadCount(final String userid) {

        if(!((Activity) context).isFinishing())
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
        }
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_records_summery" +
                "\n" +
                "\n", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!((Activity) context).isFinishing()) {
                    hidepDialog(); }

                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    String  message = reader.getString("message");
                    if(status == 200){
                        String result=reader.getString("result");
                        JSONObject readerresult= new JSONObject(result);
                        bottomNotiRead.successbottomnoti("",readerresult.getString("deal_of_day_count"),readerresult.getString("my_coupons_count"),readerresult.getString("favourites_data_count"));
//                        bottomNotiRead.successbottomnoti(readerresult.getString("my_coupons_count"),"2");
//                        bottomNotiRead.successbottomnoti(readerresult.getString("favourites_data_count"),"3");
                    }else {
                        bottomNotiRead.errorbottomnoti(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    bottomNotiRead.failbottomnoti("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(!((Activity) context).isFinishing()) {
                    hidepDialog();
                }
                bottomNotiRead.failbottomnoti("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
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
