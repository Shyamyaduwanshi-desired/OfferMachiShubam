package com.desired.offermachi.retalier.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.desired.offermachi.R;

public class RetalierAddDeals extends AppCompatActivity {
    ImageView imageViewback;
    Button buttonviewoffer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_add_deals);

        buttonviewoffer =(Button)findViewById(R.id.button_add_view_offer_id);
        buttonviewoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RetalierAddDeals.this, RetalierViewOfferActivity.class);
                startActivity(intent);
            }
        });
        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}