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
import com.desired.offermachi.retalier.model.UserModel;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class ProfilePresenter {
    private Context context;
    private Profile profile;
   // private AppData appData;

    public ProfilePresenter(Context context, Profile profile) {
        this.context = context;
        this.profile = profile;
        //appData = new AppData(context);
    }

    public interface Profile{
        void success(String response);
        void error(String response);
        void fail(String response);
    }

    public void updateProfile(final String userid, final String username, final String email, final String mobile, final String gender) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Update Profile Please Wait..");
        progress.setCancelable(false);
        progress.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "retailer_personal_details_update", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                       // SignUpPreference.getInstance(context).clear();
                        profile.success(reader.getString("message"));
                        String result=reader.getString("result");
                        JSONObject jsonObject=new JSONObject(result);
                        UserModel userModel=new UserModel(
                                jsonObject.getString("id"),
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
                                jsonObject.getString("shop_logo")
                                ,
                                "2"
                        );
                        SharedPrefManagerLogin.getInstance(context).userLogin(userModel);
                    }else if(status == 404){
                       profile.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    profile.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                profile.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",userid);
                params.put("name",username );
                params.put("email",email );
                params.put("mobile",mobile );
                params.put("gender",gender );
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }

    /*public void viewReview(final String userID) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "showUserReviews", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 1){
                        profile.success(response);
                    }else if(status == 0){
                       profile.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    profile.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                profile.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userID);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }*/
}
