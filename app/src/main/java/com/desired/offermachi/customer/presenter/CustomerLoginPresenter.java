package com.desired.offermachi.customer.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
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


public class CustomerLoginPresenter {
    private Context context;
    private CustomerLogin customerLogin;

    public CustomerLoginPresenter(Context context, CustomerLogin customerLogin) {
        this.context = context;
        this.customerLogin = customerLogin;

    }

    public interface CustomerLogin{
        void success(String response);
        void error(String response);
        void fail(String response);
    }

    public void sentRequestSingle(final String mobile, final String password,final String devicekey) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Login Please Wait..");
        progress.setCancelable(false);
        progress.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "user_login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                        Log.e("","Login result= "+reader.toString());
                        customerLogin.success(reader.getString("message"));
                        String result=reader.getString("result");
                        JSONObject jsonObject=new JSONObject(result);
                        String userid=jsonObject.getString("id");
                        String otp=jsonObject.getString("otp");
                        Log.e("","Shyam otp= "+otp);
                        Intent intent = new Intent(context, OtpActivtivity.class);
                        intent.putExtra("userid",userid);
                        intent.putExtra("otp",otp);
                        context.startActivity(intent);
                        ((Activity)context).finish();

                    }else if(status == 404){
                        customerLogin.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    customerLogin.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                customerLogin.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", mobile);
                params.put("password", password);
                params.put("device_key",devicekey);
                params.put("device_type", "Android");
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }


    public void sentRequest(final String mobile, final String password,final String devicekey) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Login Please Wait..");
        progress.setCancelable(false);
        progress.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "common_login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                        Log.e("","Login result= "+reader.toString());
                        customerLogin.success(reader.getString("message"));
                        String result=reader.getString("result");
                        JSONObject jsonObject=new JSONObject(result);

                        String userid=jsonObject.getString("id");
                        String otp=jsonObject.getString("otp");
                        Log.e("Login","Shyam otp= "+otp);
                        Intent intent = new Intent(context, OtpActivtivity.class);
                        intent.putExtra("userid",userid);
                        intent.putExtra("otp",otp);
                        context.startActivity(intent);
                        ((Activity)context).finish();

                    }else if(status == 404){
                        customerLogin.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    customerLogin.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                customerLogin.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", mobile);
                params.put("password", password);
                params.put("device_key",devicekey);
                params.put("device_type", "Android");
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);

    }


}
