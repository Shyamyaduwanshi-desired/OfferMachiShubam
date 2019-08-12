package com.desired.offermachi.retalier.presenter;

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
import com.desired.offermachi.retalier.view.activity.RetalierVerifyActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OtpPresenter {
    private Context context;
    private Otp otp;

    public OtpPresenter(Context context, Otp otp) {
        this.context = context;
        this.otp = otp;
    }

    public interface Otp {

        void success(String response);

        void error(String response);

        void fail(String response);


        void successresend(String response);

        void errorresend(String response);

        void failresend(String response);


    }

    public void verifyOtp(final String userid) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Sending please Wait..");
        progress.setCancelable(false);
        progress.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "retailer_data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        otp.success(reader.getString("message"));
                        String result = reader.getString("result");
                        JSONObject jsonObject = new JSONObject(result);
                        UserModel userModel = new UserModel(
                                jsonObject.getString("id"),
                                jsonObject.getString("username"),
                                jsonObject.getString("email"),
                                jsonObject.getString("mobile"),
                                jsonObject.getString("shop_name"),
                                jsonObject.getString("shop_contact_number"),
                                jsonObject.getString("address"),
                                jsonObject.getString("city"),
                                jsonObject.getString("shop_days"),
                                jsonObject.getString("opening_time"),
                                jsonObject.getString("closing_time"),
                                jsonObject.getString("about_store"),
                                jsonObject.getString("gender"),
                                jsonObject.getString("profile_image"),
                                jsonObject.getString("shop_logo")
                        );
                        SharedPrefManagerLogin.getInstance(context).userLogin(userModel);

                    } else if (status == 404) {
                        otp.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    otp.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                otp.fail("Server Error.\n Please try after some time.");
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

    public void resentOtp(final String userid) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Resend OTP please Wait..");
        progress.setCancelable(false);
        progress.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "retailer_resend_otp", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        String result = reader.getString("result");
                        JSONObject jsonObject = new JSONObject(result);
                        otp.successresend(jsonObject.getString("otp"));
                    } else if (status == 404) {
                        otp.errorresend(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    otp.failresend("Something went wrong Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                otp.failresend("Server Error.\n Please try after some time.");
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
}
