package com.desired.offermachi.customer.presenter;

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

public class GetCouponPresenter {
    private Context context;
    private GetCoupon getCoupon;

    public GetCouponPresenter(Context context, GetCoupon getCoupon) {
        this.context = context;
        this.getCoupon = getCoupon;

    }

    public interface GetCoupon{
        void couponsuccess(String response);
        void couponerror(String response);
        void couponfail(String response);
    }
    public void GetCoupons(final String id, final String offer_id,final String getcoupon) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_get_coupon_code_data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                        getCoupon.couponsuccess(reader.getString("message"));

                    }else if(status == 404){
                        getCoupon.couponerror(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    getCoupon.couponfail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getCoupon.couponfail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", id);
                params.put("offer_id",offer_id);
                params.put("active", getcoupon);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

}
