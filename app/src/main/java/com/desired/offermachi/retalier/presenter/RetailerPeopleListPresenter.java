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
import com.desired.offermachi.customer.model.NotificationModel;
import com.desired.offermachi.retalier.constant.AppData;
import com.desired.offermachi.retalier.model.OfferTypeModel;
import com.desired.offermachi.retalier.model.PeopleModel;
import com.desired.offermachi.retalier.model.ViewOfferModel;
import com.desired.offermachi.retalier.view.activity.RetalierOtpActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RetailerPeopleListPresenter {
    private Context context;
    private PeopleList peopleList;

    public RetailerPeopleListPresenter(Context context, PeopleList peopleList) {
        this.context = context;
        this.peopleList = peopleList;

    }

    public interface PeopleList{
        void success(ArrayList<PeopleModel> response);
        void error(String response);
        void fail(String response);
    }

    public void GetPeopleList(final String userid,final String status) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        final ArrayList<PeopleModel> peopleModelArrayList= new ArrayList<PeopleModel>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "retailer_list_of_people_data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){

                        String result=reader.getString("result");
                        JSONArray jsonArray=new JSONArray(result);
                        JSONObject object;
                        for (int count = 0; count < jsonArray.length(); count++) {
                            object = jsonArray.getJSONObject(count);
                            PeopleModel peopleModel = new PeopleModel(
                                    object.getString("username"),
                                    object.getString("profile_image"),
                                    object.getString("coupon_code"),
                                    object.getString("qr_code_image")
                            );
                            peopleModelArrayList.add(peopleModel);

                        }
                        peopleList.success(peopleModelArrayList);


                    }else if(status == 404){
                        peopleList.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    peopleList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                peopleList.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("status", status);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

}
