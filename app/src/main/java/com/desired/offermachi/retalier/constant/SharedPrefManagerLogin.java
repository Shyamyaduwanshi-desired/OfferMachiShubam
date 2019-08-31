package com.desired.offermachi.retalier.constant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.view.activity.RetalierLogin;

public class SharedPrefManagerLogin {
    private static final  String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final  String KEY_ID = "id";
    private static final  String KEY_NAME = "username";
    private static final  String KEY_EMAIL = "email";
    private static final  String KEY_PHONE = "mobile";
    private static final  String KEY_STORENAME="storename";
    private static final  String KEY_STORECONTACT="storecontact";
    private static final  String KEY_STOREADDRESS="storeaddress";
    private static final  String KEY_STORECITY="storecity";
    private static final  String KEY_STOREDAYS="storedays";
    private static final  String KEY_STOREOPENTIME="opening_time";
    private static final  String KEY_STORECLOSETIME="closing_time";
    private static final  String KEY_ABOUTSTORE="about_store";
    private static final  String KEY_GENDER="gender";
    private static final  String KEY_PROFILE="profileimage";
    private static final String KEY_IMAGE="storeimage";

    private static SharedPrefManagerLogin mInstance;
    private static Context ctx;
    private SharedPrefManagerLogin(Context context) {
        ctx = context;
    }
    public static synchronized SharedPrefManagerLogin getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManagerLogin(context);
        }
        return mInstance;
    }
    public void userLogin(UserModel user) {

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID, (user.getId()));
        editor.putString(KEY_NAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PHONE, user.getMobile());
        editor.putString(KEY_STORENAME, user.getStore_name());
        editor.putString(KEY_STORECONTACT, user.getStore_contact_number());
        editor.putString(KEY_STOREADDRESS, user.getStore_address());
        editor.putString(KEY_STORECITY, user.getStore_city());
        editor.putString(KEY_STOREDAYS,user.getStore_day() );
        editor.putString(KEY_STOREOPENTIME,user.getStore_opentime() );
        editor.putString(KEY_STORECLOSETIME,user.getStore_closetime());
        editor.putString(KEY_ABOUTSTORE,user.getAbout_store());
        editor.putString(KEY_GENDER,user.getGender());
        editor.putString(KEY_PROFILE,user.getProfile());
        editor.putString(KEY_IMAGE, user.getStoreimage());

        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null) != null;
    }

    //this method will give the logged in user
    public UserModel getUser() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new UserModel(

                sharedPreferences.getString(KEY_ID, "NA"),
                sharedPreferences.getString(KEY_NAME, "NA"),
                sharedPreferences.getString(KEY_EMAIL, "NA"),
                sharedPreferences.getString(KEY_PHONE, "NA"),
                sharedPreferences.getString(KEY_STORENAME, "NA"),
                sharedPreferences.getString(KEY_STORECONTACT, "NA"),
                sharedPreferences.getString(KEY_STOREADDRESS, "NA"),
                sharedPreferences.getString(KEY_STORECITY, "NA"),
                sharedPreferences.getString(KEY_STOREDAYS, "NA"),
                sharedPreferences.getString(KEY_STOREOPENTIME, "NA"),
                sharedPreferences.getString(KEY_STORECLOSETIME, "NA"),
                sharedPreferences.getString(KEY_ABOUTSTORE, "NA"),
                sharedPreferences.getString(KEY_GENDER, "NA"),
                sharedPreferences.getString(KEY_PROFILE, "NA"),
                sharedPreferences.getString(KEY_IMAGE, "NA")
        );
    }
    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent=new Intent(ctx, RetalierLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }
}
