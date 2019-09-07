package com.desired.offermachi.customer.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.customer.view.activity.OtpActivtivity;
import com.desired.offermachi.retalier.constant.AppData;
import com.desired.offermachi.retalier.view.activity.RetalierOtpActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class CustomerSignupPresenter {
    private Context context;
    private CustomerSignUp customersignUp;

    public CustomerSignupPresenter(Context context, CustomerSignUp customersignUp) {
        this.context = context;
        this.customersignUp = customersignUp;
    }

    public interface CustomerSignUp{
        void success(String response);
        void error(String response);
        void fail(String response);
    }

    public void sentRequest(final String mobile, final String password,final String terms,final String devicekey) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "user_signup", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                        customersignUp.success(reader.getString("message"));
                        String result=reader.getString("result");
                        JSONObject jsonObject=new JSONObject(result);
                                String userid=jsonObject.getString("user_id");
                                String otp=jsonObject.getString("otp");
                                Intent intent = new Intent(context, OtpActivtivity.class);
                                intent.putExtra("userid",userid);
                                intent.putExtra("otp",otp);
                                context.startActivity(intent);
                               ((Activity)context).finish();

                    }else if(status == 404){
                        customersignUp.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    customersignUp.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                customersignUp.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", mobile);
                params.put("password", password);
                params.put("terms_condition", terms);
                params.put("device_key",devicekey);
                params.put("device_type", "Android");
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }
}
