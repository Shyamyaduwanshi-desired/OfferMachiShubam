package com.desired.offermachi.customer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.desired.offermachi.R;

public class StoreCouponCodeActivity extends AppCompatActivity {

    ImageView imageViewback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_coupon_code_activity);

        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        LinearLayout productlinearunfollow =(LinearLayout)findViewById(R.id.product_linear_unfollow_id);
        productlinearunfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StoreCouponCodeActivity.this, ViewAllOfferFollowActivity.class);
                startActivity(intent);

            }
        });

    }
}

