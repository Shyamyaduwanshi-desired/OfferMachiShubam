package com.desired.offermachi.customer.view.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity implements LocationListener {
    private static final int REQUEST_CODE_PERMISSION = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    boolean checkFlag=false;
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
        }
        else if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS}, REQUEST_CODE_PERMISSION);

        }else if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_SMS}, REQUEST_CODE_PERMISSION);
        }
        else{
            checkFlag=true;

            getLocation();

//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {// usertype=1(customer),usertype=2(retailer)
//
//                    if (UserSharedPrefManager.getInstance(SplashActivity.this).isLoggedIn()) {
//                        startActivity(new Intent(SplashActivity.this, DashBoardActivity.class));
//                        finish();
//                    }
//                    else  if (SharedPrefManagerLogin.getInstance(SplashActivity.this).isLoggedIn()) {
//                        startActivity(new Intent(SplashActivity.this, RetalierDashboard.class));
//                        finish();
//
//                    }
//                    else
//                    {
//                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                        startActivity(intent);
//                        finish();
//                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
//
//                    }
//
//                }
//            }, 1000);
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
//for get current location

    String sCurrentLocation="";
    LocationManager locationManager;
    public String lati ;
    public String longi,idholder ;
    void getLocation() {
        try {
            locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String address = addresses.get(0).getSubLocality();
        String city = addresses.get(0).getLocality();
        sCurrentLocation=address + ", " + city;
//        tvLocation.setText(address + ", " + city);
        lati= String.valueOf(location.getLatitude());
        longi= String.valueOf(location.getLongitude());
//        Toast.makeText(this, "lati= "+lati+" longi= "+longi, Toast.LENGTH_SHORT).show();
       if(checkFlag) {
           //1=current,2=other location from location click icon
           String diff= UserSharedPrefManager.GetCurrentOrOtherLoc(this);
           switch (diff)
           {
               case "1":
                   UserSharedPrefManager.SaveCurrentLatLongAndLocNm(this,lati,longi,sCurrentLocation);
                   break;
               case "2":
                   break;
           }

           Navigate();
       }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void Navigate()
    {
        checkFlag=false;
        if (UserSharedPrefManager.getInstance(SplashActivity.this).isLoggedIn()) {
            startActivity(new Intent(SplashActivity.this, DashBoardActivity.class));
            finish();
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

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {// usertype=1(customer),usertype=2(retailer)
//            checkFlag=false;
//                if (UserSharedPrefManager.getInstance(SplashActivity.this).isLoggedIn()) {
//                    startActivity(new Intent(SplashActivity.this, DashBoardActivity.class));
//                    finish();
//                }
//                else  if (SharedPrefManagerLogin.getInstance(SplashActivity.this).isLoggedIn()) {
//                    startActivity(new Intent(SplashActivity.this, RetalierDashboard.class));
//                    finish();
//
//                }
//                else
//                {
//                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
//
//                }
//
//            }
//        }, 1000);
    }
}

