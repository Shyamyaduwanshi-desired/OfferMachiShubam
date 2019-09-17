package com.desired.offermachi.customer.view.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.crashlytics.android.Crashlytics;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.view.activity.RetalierDashboard;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.splash_activity);
        checkPermissions();
    }
    private void checkPermissions(){
        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_PERMISSION);

        }else if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);

        } else if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);

        }else if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_PERMISSION);
        }else{

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {// usertype=1(customer),usertype=2(retailer)
//                    if ((UserSharedPrefManager.getInstance(SplashActivity.this).UserType().equals("0"))) {
////
//                    }
//                    else
//                    {
//
//                    }

                    if (UserSharedPrefManager.getInstance(SplashActivity.this).isLoggedIn()) {
//                        User user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
                        startActivity(new Intent(SplashActivity.this, DashBoardActivity.class));
                        finish();

//                        if (user.getSmartShopping().equals("0")){
////                            startActivity(new Intent(SplashActivity.this, ActInterestCategory.class));
////                            startActivity(new Intent(SplashActivity.this, ActInterestCategoryNew.class));
//                            startActivity(new Intent(SplashActivity.this, DashBoardActivity.class));
//                            finish();
//                        }else{
//                            startActivity(new Intent(SplashActivity.this, DashBoardActivity.class));
//                            finish();
//                        }
                    }
                    else  if (SharedPrefManagerLogin.getInstance(SplashActivity.this).isLoggedIn()) {
                        startActivity(new Intent(SplashActivity.this, RetalierDashboard.class));
                        finish();

                    }
                    else
                    {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                    }

                  /*  Intent intent = new Intent(SplashActivity.this, RegistraionAsActivity.class);
                    startActivity(intent);
                    finish();
                      overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    */
                }
            }, 1000);
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_CODE_PERMISSION){
            for (int i = 0, len = permissions.length; i < len; i++) {
                String permission = permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    boolean showRationale = shouldShowRequestPermissionRationale( permission );
                    if (! showRationale) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                    }else if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permission)) {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Permission Necessary");
                        alertBuilder.setMessage("Allow Permission To access the Functionality Of our App.");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_PERMISSION);

                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();


                    }else if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)){
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Permission Necessary");
                        alertBuilder.setMessage("Allow Permission To access the Functionality Of our App.");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);

                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                    }else if (Manifest.permission.READ_EXTERNAL_STORAGE.equals(permission)){
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Permission Necessary");
                        alertBuilder.setMessage("Allow Permission To access the Functionality Of our App.");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);

                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                    }else if (Manifest.permission.ACCESS_COARSE_LOCATION.equals(permission)){
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Permission Necessary");
                        alertBuilder.setMessage("Allow Permission To access the Functionality Of our App.");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_PERMISSION);

                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                    }
                }else {
                    checkPermissions();
                }
            }
        }

    }
}

