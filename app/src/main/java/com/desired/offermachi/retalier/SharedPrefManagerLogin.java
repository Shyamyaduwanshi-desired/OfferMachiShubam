package com.desired.offermachi.retalier;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.desired.offermachi.customer.ui.SplashActivity;

public class SharedPrefManagerLogin {
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "mobile";
    private static final  String KEY_STORENAME="storename";
    private static final  String KEY_STORECONTACT="storecontact";
    private static final  String KEY_STOREADDRESS="storeaddress";
    private static final  String KEY_STORECITY="storecity";
    private static final  String KEY_STOREDAYHOURS="storehours";
    private static final  String KEY_STOREABOUTSTORE="storedaystore";
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
        editor.putInt(KEY_ID, (user.getId()));
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PHONE, user.getPhone());
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

                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PHONE, null),
                sharedPreferences.getString(KEY_STORENAME, null),
                sharedPreferences.getString(KEY_STORECONTACT, null),
                sharedPreferences.getString(KEY_STOREADDRESS, null),
                sharedPreferences.getString(KEY_STORECITY, null),
                sharedPreferences.getString(KEY_STOREDAYHOURS, null),
                sharedPreferences.getString(KEY_STOREABOUTSTORE, null)
        );

    }
    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent=new Intent(ctx, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }
}
