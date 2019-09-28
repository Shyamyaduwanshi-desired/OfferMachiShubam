package com.desired.offermachi.retalier.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.customer.model.NotificationModel;
import com.desired.offermachi.retalier.constant.AppData;
import com.desired.offermachi.retalier.model.CityBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CityPresenter {
    private Context context;
    private CityInfo cityInfo;

    public CityPresenter(Context context, CityInfo cityInfo) {
        this.context = context;
        this.cityInfo = cityInfo;

    }

    public interface CityInfo{
        void success(ArrayList<CityBean> response,String status);
        void error(String response);
        void fail(String response);
    }

    public void GetAllCity() {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        final ArrayList<CityBean> list = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "get_city", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");

                    if(status == 200){
                        String result=reader.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        JSONObject object;
                        CityBean bean;

                        bean=new CityBean();
                        bean.setId("0");
                        bean.setCity_name("Select City");
                        bean.setStatus("1");
                        list.add(bean);

                        for (int count = 0; count < jsonArray.length(); count++) {
                            object = jsonArray.getJSONObject(count);
                            bean=new CityBean();
                            bean.setId(object.getString("id"));
                            bean.setCity_name(object.getString("city_name"));
                            bean.setStatus(object.getString("status"));
                            list.add(bean);
                        }
                        cityInfo.success(list,"1");

                    }else if(status == 404){
                        cityInfo.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    cityInfo.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                cityInfo.fail("Server Error.\n Please try after some time.");
            }
        }
        ) /*{
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("user_id", userid);
//                params.put("type", "retailer");
                return params;
            }
        }*/;

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    public void GetLocationViaCity(final String cityId) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        final ArrayList<CityBean> list = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "get_location_by_cityid", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");

                    if(status == 200){
                        String result=reader.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        JSONObject object;
                        CityBean bean;

                        bean=new CityBean();
                        bean.setId("0");
                        bean.setCity_name("Select Location");
                        bean.setStatus("1");
                        list.add(bean);

                        for (int count = 0; count < jsonArray.length(); count++) {
                            object = jsonArray.getJSONObject(count);
                            bean=new CityBean();
                            bean.setId(object.getString("id"));
                            bean.setCity_name(object.getString("locality_name"));
                            bean.setStatus(object.getString("status"));
                            bean.setSelected(false);
                            list.add(bean);
                        }
                        cityInfo.success(list,"2");

                    }else if(status == 404){
                        cityInfo.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    cityInfo.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                cityInfo.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("city_location_id", cityId);
//                params.put("type", "retailer");
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }
}
