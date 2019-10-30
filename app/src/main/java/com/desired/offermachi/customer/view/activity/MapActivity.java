package com.desired.offermachi.customer.view.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.NotificationCountPresenter;
import com.desired.offermachi.customer.presenter.SmartShoppingOfferPresenter;
import com.desired.offermachi.retalier.view.activity.ActAddPushOffer;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.johnnylambada.location.LocationObserver;
import com.johnnylambada.location.LocationProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class MapActivity extends AppCompatActivity implements LocationObserver, Runnable, LocationListener,OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, SmartShoppingOfferPresenter.NotificationOfferList, View.OnClickListener , NotificationCountPresenter.NotiUnReadCount {
    SupportMapFragment mapFragment;
    protected GoogleApiClient mGoogleApiClient;
    private GoogleMap mMapSession;
    Marker mCurrLocationMarker;
    double lati = 0;//22.71246
    double longi = 0;//75.86491
    Location mLastLocation;
    String catid,idholder,distance="10";
    private SmartShoppingOfferPresenter presenter;
    TextView txtselectkilometer;
    EditText edtlocation;
    ImageView imgNotiBell;
    ImageView imageViewback ,info;
    TextView tvNotiCount;
    private NotificationCountPresenter notiCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initview();
    }
    private void initview(){
        findViewById(R.id.btnMapProcess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, SmartShoppingRemoveActivity.class);
                intent.putExtra("lat",""+lati);
                intent.putExtra("longi",""+longi);
                intent.putExtra("distance",""+dist);
                intent.putExtra("catIds",catid);
                startActivity(intent);
                //finish();
            }
        });
        presenter=new SmartShoppingOfferPresenter(MapActivity.this,MapActivity.this);
        Intent intent=getIntent();
        catid=intent.getStringExtra("catid");
        User user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
        idholder=user.getId();
        txtselectkilometer=findViewById(R.id.selectkilometer);
        txtselectkilometer.setOnClickListener(this);
        imgNotiBell=findViewById(R.id.imgNotiBell);
        imgNotiBell.setOnClickListener(this);
        imageViewback = findViewById(R.id.imageback);
        imageViewback.setOnClickListener(this);
        info=findViewById(R.id.info_id);
        info.setOnClickListener(this);
        edtlocation=findViewById(R.id.etlocation);
        tvNotiCount = findViewById(R.id.txtMessageCount);
        notiCount = new NotificationCountPresenter(this,this);
        notiCount.NotificationUnreadCount(idholder);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        buildGoogleApiClient();

        setCurLoc();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            lati = mLastLocation.getLatitude();
            longi = mLastLocation.getLongitude();
        }
        myLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMapSession = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMapSession.setMyLocationEnabled(true);

    }
    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
    public void myLocation()//only for show current position
    {
        LatLng latLng = new LatLng(lati, longi);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMapSession.addMarker(markerOptions);
        //move map camera
        mMapSession.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));

//        mMapSession.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16f));
//        mMapSession.getUiSettings().setZoomControlsEnabled(true);
        txtselectkilometer.setText("1 Km");
        dist = 1;
        drawCircle();

    }
    public void setCurLoc()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkLocationPermission()) {
                LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                if (!manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, 101);
                } else {
                    initLocation();
                }
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            }
        } else {
            LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 101);
            } else {
                initLocation();
            }
        }
    }
    private LocationProvider locationProvider;
    private void initLocation() {
        locationProvider = new LocationProvider.Builder(this)
                .locationObserver(this)
                .intervalMs(10000)//300000
                .onPermissionDeniedFirstTime(this)
                .onPermissionDeniedAgain(this)
                .onPermissionDeniedForever(this)
                .build();
        locationProvider.startTrackingLocation();
    }
    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onLocation(Location location) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getSubLocality();
            String city = addresses.get(0).getLocality();
            Log.e("","run onLocation lati= "+location.getLatitude()+" longi= "+location.getLongitude());
            edtlocation.setText(address+" "+city);
           /* LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            mCurrLocationMarker =mMapSession.addMarker(markerOptions);*/
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        Log.e("","run");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationProvider.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
    }

    private void showAlert(String message, int animationSource){
        final PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog.setCanceledOnTouchOutside(false);
        TextView title = (TextView) prettyDialog.findViewById(libs.mjn.prettydialog.R.id.tv_title);
        TextView tvmessage = (TextView) prettyDialog.findViewById(libs.mjn.prettydialog.R.id.tv_message);
        title.setTextSize(15);
        tvmessage.setTextSize(15);
        prettyDialog.setIconTint(R.color.colorPrimary);
        prettyDialog.setIcon(R.drawable.pdlg_icon_info);
        prettyDialog.setTitle("");
        prettyDialog.setMessage(message);
        prettyDialog.setAnimationEnabled(false);
        prettyDialog.getWindow().getAttributes().windowAnimations = animationSource;
        prettyDialog.addButton("Ok", R.color.black, R.color.white, new PrettyDialogCallback() {
            @Override
            public void onClick() {
                prettyDialog.dismiss();
            }
        }).show();
    }
    @Override
    public void success(ArrayList<SelectCategoryModel> alData) {
        for (SelectCategoryModel data:alData) {
            addMarker(data);
        }
    }

    @Override
    public void error(String response) {
        showAlert(response, R.style.DialogAnimation);
    }

    @Override
    public void fail(String response) {
        showAlert(response, R.style.DialogAnimation);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    private void showdialog(){
        final Dialog dialog = new Dialog(MapActivity.this);
        dialog.setContentView(R.layout.kilometerdialog);
        RadioButton rb1 = (RadioButton) dialog.findViewById(R.id.rd1);
        RadioButton rb2 = (RadioButton) dialog.findViewById(R.id.rd2);
        RadioButton rb3 = (RadioButton) dialog.findViewById(R.id.rd3);
        RadioButton rb4 = (RadioButton) dialog.findViewById(R.id.rd4);
        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                txtselectkilometer.setText("1 Km");
                dist = 1;
                drawCircle();

            }
        });
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                txtselectkilometer.setText("3 Km");
                dist = 3;
                drawCircle();
            }
        });
        rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                txtselectkilometer.setText("5 Km");
                dist = 5;
                drawCircle();
            }
        });
        rb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                txtselectkilometer.setText("10 Km");
                dist = 10;
                drawCircle();
            }
        });

        dialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v==txtselectkilometer){
            showdialog();
        }else if (v==imgNotiBell){
            startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
        }else if (v == imageViewback) {
            onBackPressed();
        }else if(v==info){
            Intent intent = new Intent(MapActivity.this, InfoActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void successnoti(String response) {
        if(TextUtils.isEmpty(response))
        {
            tvNotiCount.setText("0");
        }
        else {
//            tvNotiCount.setText(push_notifications_count);
            tvNotiCount.setText(response);
//            Log.e("","count= "+tvNotiCount);
        }


    }

    @Override
    public void errornoti(String response) {

    }

    @Override
    public void failnoti(String response) {

    }
    public float getZoomLevel(Circle circle) {
        float zoomLevel=0;
        if (circle != null){
            double radius = circle.getRadius();
            double scale = radius / 500;
            zoomLevel =(int) (16 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel +.5f;
    }

    Circle circle;
    int dist = 10;
    public void drawCircle(){
        if(circle!=null){
            circle.remove();
        }
        if(mMapSession!=null){
            mMapSession.clear();
        }
        circle = mMapSession.addCircle(new CircleOptions()
                        .center(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()))
                        .radius(dist*1000)
                        .strokeColor(Color.RED)
                //.fillColor(Color.BLUE)
        );
        circle.isVisible();



        float currentZoomLevel = getZoomLevel(circle);
        float animateZomm = currentZoomLevel + 5;

        Log.e("Zoom Level:", currentZoomLevel + "");
        Log.e("Zoom Level Animate:", animateZomm + "");

        mMapSession.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()), animateZomm));
        mMapSession.animateCamera(CameraUpdateFactory.zoomTo(currentZoomLevel-1), 2000, null);
        callApi();
    }

    void callApi(){
        if (isNetworkConnected()){
            presenter.sentRequest(idholder,String.valueOf(lati),String.valueOf(longi),catid,dist+"");
        }else{
            showAlert("Please connect to internet.",R.style.DialogAnimation);
        }

    }

    void addMarker(SelectCategoryModel data){


        //Show Marker on a Location
        mMapSession.addMarker(new MarkerOptions().position(new LatLng(data.getLat(),data.getLng())).title(data.getStoreName()));
/*
        //Change Default Color of Marker

        googleMap.addMarker(new MarkerOptions()
                .position(BROOKLYN_BRIDGE)
                .title("First Pit Stop")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        //Replace Default Marker Icon with Custom Image

        googleMap.addMarker(new MarkerOptions()
                .position(WALL_STREET)
                .title("Wrong Turn!")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.my_flag)));
*/

    }
}
