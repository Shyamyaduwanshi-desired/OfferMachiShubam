package com.desired.offermachi.retalier.presenter;

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
import com.desired.offermachi.retalier.constant.AppData;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.view.activity.RetalierOtpActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginPresenter {
    private Context context;
    private Login login;

    public LoginPresenter(Context context, Login login) {
        this.context = context;
        this.login = login;

    }

    public interface Login{
        void success(String response);
        void error(String response);
        void fail(String response);
    }

    public void sentRequest(final String mobile,final String password, final String devicekey) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Login Please Wait..");
        progress.setCancelable(false);
        progress.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "retailer_login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                        login.success(reader.getString("message"));
                        String result=reader.getString("result");
                        JSONObject jsonObject=new JSONObject(result);
                        String userid=jsonObject.getString("user_id");
                      //  String otp=jsonObject.getString("otp");
                        Intent intent = new Intent(context, RetalierOtpActivity.class);
                        intent.putExtra("userid",userid);
                      //  intent.putExtra("otp",otp);
                        context.startActivity(intent);
                        ((Activity)context).finish();

                    }else if(status == 404){
                       login.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    login.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                login.fail("Server Error.\n Please try after some time.");
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

  /*  public void signInThroughSocialMedia(final String username, final String email, final String token) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Login Please Wait..");
        progress.setCancelable(false);
        progress.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "signInThroughSocialMediaUser", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 1){
                        appData.setUserID(reader.getString("user_id"));
                        login.success(reader.getString("message"));
                    }else if(status == 0){
                       login.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    login.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                login.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("email_id", email);
                params.put("id", token);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }*/
}
