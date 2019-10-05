package com.desired.offermachi.customer.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
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
//    AppData appData;

    public CustomerOtpPresenter(Context context, CustomerOtp customerotp) {
        this.context = context;
        this.customerotp = customerotp;
//        appData=new AppData();
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

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "userdata", new Response.Listener<String>()
        {
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
                        String usertype="1";

                        User user = new User(
                                jsonObject.getString("id"),
                                jsonObject.getString("username"),
                                jsonObject.getString("email"),
                                jsonObject.getString("mobile"),
                                jsonObject.getString("dob"),
                                jsonObject.getString("address"),
                                jsonObject.getString("gender"),
                                jsonObject.getString("profile_image"),
                                "0",
                                "0",
                                usertype

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

//    http://offermachi.in/api/common_login_moblie_otp_verfiy
    public void verifyOtpCommon(final String userid,final String otp) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Sending please Wait..");
        progress.setCancelable(false);
        progress.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "common_login_moblie_otp_verfiy", new Response.Listener<String>() //for customer
//        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "retailer_data", new Response.Listener<String>() //for retailer
        {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {

                        String result = reader.getString("result");
                        JSONObject jsonObject = new JSONObject(result);//
//                        String usertype="1";
                        Log.e("","json data= "+jsonObject.toString());
                        String group=jsonObject.getString("group");
                        if(group=="customer"||group.equals("customer")) {
                            User user = new User(
                                    jsonObject.getString("id"),
                                    jsonObject.getString("username"),
                                    jsonObject.getString("email"),
                                    jsonObject.getString("mobile"),
                                    jsonObject.getString("address"),
                                    jsonObject.getString("gender"),
                                    jsonObject.getString("dob"),
                                    jsonObject.getString("profile_image"),
                                    "0",
                                    "0",
                                    "1"
                            );

                            UserSharedPrefManager.getInstance(context).userLogin(user);

                        }else {

//                        JSONObject jsonObject = new JSONObject(result);
                            UserModel userModel = new UserModel(
                                    jsonObject.getString("id"),
                                    jsonObject.getString("category_id"),
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
                                    jsonObject.getString("shop_logo"),
                                    "2"/*retailer_data*/

                            );
                            SharedPrefManagerLogin.getInstance(context).userLogin(userModel);
                        }
                        customerotp.success(reader.getString("message"));
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
                params.put("otp", otp);
                Log.e("","input param= "+params.toString());
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
//retailer_resend_otp
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "common_resend_otp", new Response.Listener<String>() {
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
                        Log.e("","shyam resend otp= "+reader.toString());
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
