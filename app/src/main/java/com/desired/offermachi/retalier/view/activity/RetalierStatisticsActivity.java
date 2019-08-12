package com.desired.offermachi.retalier.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.desired.offermachi.R;

public class RetalierStatisticsActivity  extends AppCompatActivity {

    ImageView imageViewback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_statistics_activity);

        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView gotthecoupontext =(TextView)findViewById(R.id.gotthecoupon_text_id);
        gotthecoupontext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RetalierStatisticsActivity.this, RetalierListPeopleActivity.class);
                startActivity(intent);
            }
        });

        TextView redeemthecoupontext =(TextView)findViewById(R.id.redeemthecoupon_text_id);
        redeemthecoupontext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RetalierStatisticsActivity.this, RetalierListPeopleActivity.class);
                startActivity(intent);
            }
        });

        TextView Offerdiscounttext =(TextView)findViewById(R.id.Offerdiscount_text_id);
        Offerdiscounttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RetalierStatisticsActivity.this, ListOfPeople0fferDiscount.class);
                startActivity(intent);
            }
        });

    }
}
