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
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.view.activity.OtpActivtivity;
import com.desired.offermachi.retalier.constant.AppData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomerUpdateProfilePresenter {
    private Context context;
    private UpdateProfile updateProfile;

    public CustomerUpdateProfilePresenter(Context context, UpdateProfile updateProfile) {
        this.context = context;
        this.updateProfile = updateProfile;

    }

    public interface UpdateProfile{
        void success(String response);
        void error(String response);
        void fail(String response);
    }

    public void UpdateCustomerProfile(final String userid,final String name,final String email,final String mobile,final String gender, final String address,final String image) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Profile Update Please Wait..");
        progress.setCancelable(false);
        progress.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_details_update", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                        updateProfile.success(reader.getString("message"));
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


                    }else if(status == 404){
                        updateProfile.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    updateProfile.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                updateProfile.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",userid);
                params.put("name",name);
                params.put("email",email);
                params.put("gender",gender);
                params.put("mobile",mobile);
                params.put("address",address);
                params.put("profile_image",image);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

}
