package com.desired.offermachi.retalier.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.retalier.constant.AppData;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.view.activity.RetalierDashboard;
import com.desired.offermachi.retalier.view.activity.RetalierOtpActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignupPresenter {
    private Context context;
    private SignUp signUp;

    public SignupPresenter(Context context, SignUp signUp) {
        this.context = context;
        this.signUp = signUp;
    }

    public interface SignUp{
        void success(String response);
        void error(String response);
        void fail(String response);
    }

    public void sentRequest(final String name, final String mobile, final String email, final String shop_name, final String shop_contact_number, final String address, final String city
    , final String shop_logo, final String shop_day_hours, final String opening_time, final String closing_time, final String about_store) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "retailer_signup", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                        signUp.success(reader.getString("message"));
                        String result=reader.getString("result");
                        JSONObject jsonObject=new JSONObject(result);
                                String userid=jsonObject.getString("user_id");
                                String otp=jsonObject.getString("otp");
                                Intent intent = new Intent(context, RetalierOtpActivity.class);
                                intent.putExtra("userid",userid);
                                intent.putExtra("otp",otp);
                                context.startActivity(intent);
                               ((Activity)context).finish();

                    }else if(status == 404){
                       signUp.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    signUp.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                signUp.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("mobile", mobile);
                params.put("email", email);
                params.put("shop_name", shop_name);
                params.put("shop_contact_number", shop_contact_number);
                params.put("address", address);
                params.put("city", city);
                params.put("shop_logo", shop_logo);
                params.put("shop_days", shop_day_hours);
                params.put("opening_time",opening_time);
                params.put("closing_time",closing_time);
                params.put("about_store",about_store);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }
}
