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
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.FollowerModel;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.model.ViewOfferModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FollowerPresenter {
    private Context context;
    private Follower follower;

    public FollowerPresenter(Context context, Follower follower) {
        this.context = context;
        this.follower = follower;

    }

    public interface Follower {
        void followersuccess(ArrayList<FollowerModel> followerModels);

        void followersend(String response);

        void followererror(String response);

        void followerfail(String response);
    }

    public void sentRequest(final String userid) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Get Followers Please Wait..");
        progress.setCancelable(false);
        progress.show();
       final ArrayList<FollowerModel> followerModels = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "retailer_select_all_followers", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {

                        String result = reader.getString("result");
                        JSONArray jsonArray = new JSONArray(result);
                        JSONObject object;
                        for (int count = 0; count < jsonArray.length(); count++) {
                            object = jsonArray.getJSONObject(count);
                            FollowerModel followerModel=new FollowerModel(
                                    object.getString("id"),
                                    object.getString("username")

                            );
                            followerModels.add(followerModel);

                        }
                        follower.followersuccess(followerModels);


                    } else if (status == 404) {
                        follower.followererror(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    follower.followerfail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                follower.followerfail("Server Error.\n Please try after some time.");
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



    public void SendOffer(final String userid,final String offerid,final String followerid) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Send Offer Please Wait..");
        progress.setCancelable(false);
        progress.show();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "retailer_send_push_offers", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        follower.followersend(reader.getString("message"));
                    } else if (status == 404) {
                        follower.followererror(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    follower.followerfail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                follower.followerfail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("retailer_id", userid);
                params.put("offer_id", offerid);
                params.put("user_ids", followerid);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

}