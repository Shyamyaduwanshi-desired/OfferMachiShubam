package com.desired.offermachi.customer.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.retalier.constant.AppData;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.UserModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomerOtpPresenter {
    private Context context;
    private CustomerOtp customerotp;

    public CustomerOtpPresenter(Context context, CustomerOtp customerotp) {
        this.context = context;
        this.customerotp = customerotp;
    }

    public interface CustomerOtp {

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

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "userdata", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        customerotp.success(reader.getString("message"));
                        String result = reader.getString("result");
                        JSONObject jsonObject = new JSONObject(result);
                        User user = new User(
                                jsonObject.getString("id"),
                                jsonObject.getString("username"),
                                jsonObject.getString("email"),
                                jsonObject.getString("mobile"),
                                jsonObject.getString("address"),
                                jsonObject.getString("gender"),
                                jsonObject.getString("profile_image"),
                                "0",
                                "0"

                        );
                        UserSharedPrefManager.getInstance(context).userLogin(user);

                    } else if (status == 404) {
                        customerotp.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    customerotp.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                customerotp.fail("Server Error.\n Please try after some time.");
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
                        Toast.makeText(context, ""+reader.getString("message"), Toast.LENGTH_SHORT).show();
                        String result = reader.getString("result");
                        /* JSONObject jsonObject = new JSONObject(result);*/
                        customerotp.successresend(result);
                    } else if (status == 404) {
                        customerotp.errorresend(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    customerotp.failresend("Something went wrong Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                customerotp.failresend("Server Error.\n Please try after some time.");
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
