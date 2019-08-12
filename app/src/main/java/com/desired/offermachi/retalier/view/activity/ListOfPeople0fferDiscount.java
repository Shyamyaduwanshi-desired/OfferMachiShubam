package com.desired.offermachi.retalier.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.desired.offermachi.R;

public class ListOfPeople0fferDiscount extends AppCompatActivity {

    Button viewcouponbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_list_people_offer_discount);
        viewcouponbutton=(Button)findViewById(R.id.viewoffer_discount_id);
        viewcouponbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListOfPeople0fferDiscount.this, RetalierViewOfferDiscount.class);
                startActivity(intent);
            }
        });
    }

}
