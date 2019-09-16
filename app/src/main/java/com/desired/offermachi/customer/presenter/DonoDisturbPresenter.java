package com.desired.offermachi.customer.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.customer.model.StoreModel;
import com.desired.offermachi.customer.model.days_model;
import com.desired.offermachi.customer.model.hours_model;
import com.desired.offermachi.retalier.constant.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonoDisturbPresenter {
    private Context context;
    private DoNotDisInfo donoDisList;
    private ProgressDialog progress;

    public DonoDisturbPresenter(Context context, DoNotDisInfo donoDisList) {
        this.context = context;
        this.donoDisList = donoDisList;

    }

    public interface DoNotDisInfo {
        void success(ArrayList<days_model> dayResponse,ArrayList<hours_model> hoursResponse,String status);

        void error(String response);

        void fail(String response);
    }

    public void GetDoNoDisDataList() {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();
//         ArrayList<SelectCategoryModel> list = new ArrayList<>();
        ArrayList<hours_model> hoursdata = new ArrayList<>();
         ArrayList<days_model> daysdata = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.GET, AppData.url + "common_dnd_services", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!((Activity) context).isFinishing())
                {
                    hidepDialog();
                }

                try {
                    JSONObject mainJson = new JSONObject(response);
                    String status = mainJson.getString("status");
                    String message = mainJson.getString("message");
                    if (status.equals("200"))
                    {
                        JSONArray jsonArray = mainJson.getJSONArray("result");
                        JSONObject objJson;
                        days_model bean;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            bean=new days_model();
                            objJson = jsonArray.getJSONObject(i);
                            String id = objJson.getString("id");
                            String tittle = objJson.getString("title");
                            String type = objJson.getString("type");
                            String value = objJson.getString("value");
                            String is_active = objJson.getString("is_active");
                            String cr_date = objJson.getString("cr_date");

                            if (type.equals("day")) {

//                                days_model data2 = new days_model(
//                                        id,tittle, false);

                                bean.setId(id);
                                bean.setTittle(tittle);
                                bean.setSelected(false);
                                daysdata.add(bean);
                            } else {

                                hours_model data1 = new hours_model(
                                        id, tittle, false
                                );
                                hoursdata.add(data1);
                            }
                        }
                        donoDisList.success(daysdata,hoursdata,"1");
                        

                    } else {
                        donoDisList.error(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    donoDisList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(!((Activity) context).isFinishing())
                {
                    hidepDialog();
                }

                donoDisList.fail("Server Error.\n Please try after some time.");
            }
        }
        ) ;

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    public void SubmitDoNoDisData(final String userid,final String dndid) {
            progress = new ProgressDialog(context);
            progress.setMessage("Please Wait..");
            progress.setCancelable(false);
            showpDialog();

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "common_dnd_services_start", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hidepDialog();
                try {
                    JSONObject mainJson = new JSONObject(response);
                    String status = mainJson.getString("status");
                    String message = mainJson.getString("message");
                    if (status.equals("200"))
                    {

                        donoDisList.success(null,null,"2");
                    } else {
                        donoDisList.error(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    donoDisList.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(!((Activity) context).isFinishing())
                {
                    hidepDialog();
                }
                donoDisList.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userid", userid);
                params.put("dndid", dndid);
                Log.e("surbhi", "params" + params);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    private void showpDialog() {
        if (!progress.isShowing())
            progress.show();
    }

    private void hidepDialog() {
        if (progress.isShowing())
            progress.dismiss();
    }

}