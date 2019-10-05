package com.desired.offermachi.retalier.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

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

public class ProfileImagePresenter {
    private Context context;
   private ProfileImage ProfileImage;


    public ProfileImagePresenter(Context context, ProfileImage profileImage) {
        this.context = context;
        this.ProfileImage = profileImage;
        //appData = new AppData(context);
    }

    public interface ProfileImage{
        void success(String response);
        void error(String response);
        void fail(String response);
    }
    public void updateProfileImage(final String userid,final String image) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Update Profile Please Wait..");
        progress.setCancelable(false);
        progress.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "retailer_update_profile_image", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                        // SignUpPreference.getInstance(context).clear();
                        ProfileImage.success(reader.getString("message"));
                        String result=reader.getString("result");
                        JSONObject jsonObject=new JSONObject(result);
                        UserModel userModel=new UserModel(
                                jsonObject.getString("id"),
                                jsonObject.getString("category_id"),
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
                        Log.e("profilepicture", "ImageHolder="+  jsonObject.getString("profile_image") );
                        SharedPrefManagerLogin.getInstance(context).userLogin(userModel);
                    }else if(status == 404){
                        ProfileImage.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ProfileImage.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                ProfileImage.fail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",userid);
                params.put("profile_image",image );
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }
}
