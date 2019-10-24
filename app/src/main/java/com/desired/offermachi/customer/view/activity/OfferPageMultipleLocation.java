package com.desired.offermachi.customer.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.model.muliplelocationshowmodel;
import com.desired.offermachi.customer.view.adapter.MultipleshoelocationAdapter;
import com.desired.offermachi.retalier.model.mylocation_model;
import com.desired.offermachi.retalier.view.activity.MyLocationEditActivity;
import com.desired.offermachi.retalier.view.activity.PickLocation;

import java.util.ArrayList;

public class OfferPageMultipleLocation extends AppCompatActivity implements View.OnClickListener {
    RecyclerView multiple_location;
    String idholder,locality_name,location_address,mobile,location_latitude,location_longitude;
    ArrayList<muliplelocationshowmodel> multiplearrayList ;
    MultipleshoelocationAdapter multipleshoelocationAdapter;
    ImageView imageViewback;
    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offerdetalis_multiple_location);
        multiplearrayList = new ArrayList<>();
        imageViewback = findViewById(R.id.imageback);
        imageViewback.setOnClickListener(this);

        Intent intent=getIntent();
        idholder= intent.getStringExtra("id");
        locality_name= intent.getStringExtra("locality_name");
        location_address= intent.getStringExtra("location_address");
        mobile= intent.getStringExtra("mobile");
        location_latitude= intent.getStringExtra("location_latitude");
        location_longitude= intent.getStringExtra("location_longitude");


        multiple_location = (RecyclerView)findViewById(R.id.multiple_location_show);
        multiple_location.setHasFixedSize(true);
        multiple_location.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        multiple_location.setNestedScrollingEnabled(false);



        muliplelocationshowmodel muliplelocationshowmodel = new muliplelocationshowmodel(
                idholder, locality_name, location_address,mobile,location_latitude,location_longitude
        );

        multiplearrayList.add(muliplelocationshowmodel);
        multipleshoelocationAdapter=new MultipleshoelocationAdapter(getApplicationContext(),multiplearrayList);
        multiple_location.setAdapter(multipleshoelocationAdapter);
    }

    @Override
    public void onClick(View v) {
      if (v==imageViewback){
            onBackPressed();
        }

    }
}
