package com.desired.offermachi.retalier.presenter;

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

public class CustomerSupportPresenter {
    private Context context;
    private CustomerSupport customerSupport;

    public CustomerSupportPresenter(Context context, CustomerSupport customerSupport) {
        this.context = context;
        this.customerSupport = customerSupport;

    }

    public interface CustomerSupport {
        void success(String response);

        void error(String response);

        void fail(String response);
    }

    public void SendCustomerSupport(final String name, final String email, final String mobile, final String message) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage(" Please Wait..");
        progress.setCancelable(false);
        progress.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_support_data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        customerSupport.success(reader.getString("message"));

                    } else if (status == 404) {
                        customerSupport.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    customerSupport.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                customerSupport.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("mobile", mobile);
                params.put("message", message);
                params.put("type", "Retailer");

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }
}