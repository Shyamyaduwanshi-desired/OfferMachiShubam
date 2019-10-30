package com.desired.offermachi.customer.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.Util;
import com.desired.offermachi.customer.model.muliplelocationshowmodel;
import com.desired.offermachi.customer.view.adapter.MultipleshoelocationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OfferPageMultipleLocation extends AppCompatActivity implements View.OnClickListener {
    RecyclerView multiple_location;
    String idholder, locality_name, location_address, mobile, location_latitude, location_longitude;
    ArrayList<muliplelocationshowmodel> multiplearrayList;
    MultipleshoelocationAdapter multipleshoelocationAdapter;
    ImageView imageViewback;
    private muliplelocationshowmodel muliplelocationshowmodel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offerdetalis_multiple_location);
        multiplearrayList = new ArrayList<>();
        imageViewback = findViewById(R.id.imageback);
        imageViewback.setOnClickListener(this);

        Intent intent = getIntent();
        idholder = intent.getStringExtra("id");
        locality_name = intent.getStringExtra("locality_name");
        location_address = intent.getStringExtra("location_address");
        mobile = intent.getStringExtra("mobile");
        location_latitude = intent.getStringExtra("location_latitude");
        location_longitude = intent.getStringExtra("location_longitude");
        String loaction = intent.getStringExtra("location");
        /*  Toast.makeText(OfferPageMultipleLocation.this, "asdzff"+loaction.toString(), Toast.LENGTH_SHORT).show();*/
     /*   ArrayList<muliplelocationshowmodel> muliplelocations = (ArrayList<muliplelocationshowmodel>) intent.getSerializableExtra("muliplelocations");
        Toast.makeText(OfferPageMultipleLocation.this, "asdzff"+muliplelocations.size(), Toast.LENGTH_SHORT).show();*/

        multiple_location = findViewById(R.id.multiple_location_show);
        multiple_location.setHasFixedSize(true);
        multiple_location.setLayoutManager(new LinearLayoutManager(this));
        multiple_location.setItemAnimator(new DefaultItemAnimator());
        multiple_location.setNestedScrollingEnabled(false);
        if (!Util.isEmptyString(loaction)) {
            try {
                JSONArray jsonArray = new JSONArray(loaction);
                JSONObject object3;
                for (int count = 0; count < jsonArray.length(); count++) {
                    object3 = jsonArray.getJSONObject(count);
                    idholder = object3.getString("id");
                    locality_name = object3.getString("locality_name");
                    location_address = object3.getString("address");
                    mobile = object3.getString("mobile");
                    location_latitude = object3.getString("location_latitude");
                    location_longitude = object3.getString("location_longitude");

                    muliplelocationshowmodel = new muliplelocationshowmodel(
                            idholder, locality_name, location_address, mobile, location_latitude, location_longitude
                    );
                    multiplearrayList.add(muliplelocationshowmodel);
                }
                multipleshoelocationAdapter = new MultipleshoelocationAdapter(getApplicationContext(), multiplearrayList);
                multiple_location.setAdapter(multipleshoelocationAdapter);
            } catch (JSONException j) {

            }
        }

        
        /*muliplelocationshowmodel muliplelocationshowmodel = new muliplelocationshowmodel(
                idholder, locality_name, location_address,mobile,location_latitude,location_longitude
        );*/

        // multiplearrayList.add(muliplelocationshowmodel);


    }

    @Override
    public void onClick(View v) {
        if (v == imageViewback) {
            onBackPressed();
        }

    }
}
