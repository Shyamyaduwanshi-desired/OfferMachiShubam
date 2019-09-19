package com.desired.offermachi.customer.constant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.view.activity.LoginActivity;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.view.activity.RetalierLogin;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserSharedPrefManager {
    private static final  String SHARED_PREF_NAME = "Usersharedpref";
    private static final  String KEY_ID = "cusid";
    private static final  String KEY_NAME = "cusname";
    private static final  String KEY_EMAIL = "cusemail";
    private static final  String KEY_PHONE = "cusmobile";
    private static final  String KEY_STOREADDRESS="cusaddress";
    private static final  String KEY_GENDER="cusgender";
    private static final  String KEY_DOB = "cusdob";
    private static final  String KEY_PROFILE="cusprofileimage";
    private static final  String KEY_SmartShopping="smartshopping";
    private static final  String KEY_NotificationSound="notificationsound";
    private static final  String KEY_USER_TYPE="user_type";
    private static final  String KEY_CAT_FILTER="user_cat_filter";
    private static final  String KEY_CAT_INTEREST="user_cat_interest";
    private static UserSharedPrefManager mInstance;
    private static Context ctx;
    private UserSharedPrefManager(Context context) {
        ctx = context;
    }
    public static synchronized UserSharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new UserSharedPrefManager(context);
        }
        return mInstance;
    }
    public void userLogin(User user) {

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID, (user.getId()));
        editor.putString(KEY_NAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PHONE, user.getMobile());
        editor.putString(KEY_STOREADDRESS, user.getAddress());
        editor.putString(KEY_GENDER,user.getGender());
        editor.putString(KEY_DOB,user.getDob());
        editor.putString(KEY_PROFILE,user.getProfile());
        editor.putString(KEY_SmartShopping,user.getSmartShopping());
        editor.putString(KEY_NotificationSound,user.getNotificationsound());
        editor.putString(KEY_USER_TYPE,user.getUsertype());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PHONE, null) != null;
    }

    //this method will checker whether user is already logged in or not
    public String UserType() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String value=sharedPreferences.getString(KEY_USER_TYPE, "0");
        return value;
    }
    //this method will checker whether user is already logged in or not
//    public void SetUserType() {
//        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        String value=sharedPreferences.getString(KEY_USER_TYPE, "0");
//    }
    //this method will give the logged in user
    public User getCustomer() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(

                sharedPreferences.getString(KEY_ID, "NA"),
                sharedPreferences.getString(KEY_NAME, "NA"),
                sharedPreferences.getString(KEY_EMAIL, "NA"),
                sharedPreferences.getString(KEY_PHONE, "NA"),
                sharedPreferences.getString(KEY_STOREADDRESS, "NA"),
                sharedPreferences.getString(KEY_GENDER, "NA"),
                sharedPreferences.getString(KEY_DOB,""),
                sharedPreferences.getString(KEY_PROFILE,  "NA"),
                sharedPreferences.getString(KEY_SmartShopping, "NA"),
                sharedPreferences.getString(KEY_NotificationSound, "NA"),
                sharedPreferences.getString(KEY_USER_TYPE, "0")

        );
    }
    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent=new Intent(ctx, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
       // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);

    }

    public static String GetStoredFilter(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String value=sharedPreferences.getString(KEY_CAT_FILTER, "");
        return value;
    }
    public static void SaveStoreFilter(Context context,String allSelectedCategory) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CAT_FILTER, allSelectedCategory);
        editor.apply();
    }

    public static String GetInterestCat(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String value=sharedPreferences.getString(KEY_CAT_INTEREST, "");
        return value;
    }
    public static void SaveInterestCat(Context context,String allSelectedCategory) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CAT_INTEREST, allSelectedCategory);
        editor.apply();
    }

}
