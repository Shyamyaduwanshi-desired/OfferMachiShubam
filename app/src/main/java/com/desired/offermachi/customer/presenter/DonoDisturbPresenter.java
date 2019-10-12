package com.desired.offermachi.customer.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
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
        void successDND(JSONObject Response,String status);
        void error(String response);
        void dndServiceOff();
        void fail(String response);
    }

    public void GetDoNoDisDataList(final String preSetDndId) {
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

                            boolean presetFlag=false;
                            if(id.equals(preSetDndId)&&!TextUtils.isEmpty(preSetDndId))
                            {
                                presetFlag=true;
                            }

                            if (type.equals("day")) {

                                bean.setId(id);
                                bean.setTittle(tittle);
                                bean.setSelected(presetFlag);
                                daysdata.add(bean);
                            } else {

                                hours_model data1 = new hours_model(
                                        id, tittle, presetFlag
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


    public void GetDoNoDisStatus(final String userid) {
        if(progress!=null) {
            progress.dismiss();
            progress=null;
        }
        progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        showpDialog();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "common_get_dnd_services_status", new Response.Listener<String>() {
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
                        JSONObject objJson = mainJson.getJSONObject("result");

                       /* String id = objJson.getString("id");
                        String dnd_id = objJson.getString("dnd_id");
                        String dnd_start_time = objJson.getString("dnd_start_time");
                        String dnd_end_time = objJson.getString("dnd_end_time");
                        String status1 = objJson.getString("status");*/

                        donoDisList.successDND(objJson,"3");


                    } else  if(status.equals("201"))
                    {
                        donoDisList.successDND(null,"4");
                    }

                    else {
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

                Log.e("Enter param", "params= " + params.toString());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    public void offDndService(final String userid) {
        progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        showpDialog();

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "remove_dnd_for_userby_id", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hidepDialog();
                try {
                    JSONObject mainJson = new JSONObject(response);
                    String status = mainJson.getString("status");
                    String message = mainJson.getString("message");
                    if (status.equals("200"))
                    {

                        donoDisList.dndServiceOff();
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
                params.put("user_id", userid);
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