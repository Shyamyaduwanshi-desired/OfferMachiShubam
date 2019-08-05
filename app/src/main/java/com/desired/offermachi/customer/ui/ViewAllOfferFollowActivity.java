package com.desired.offermachi.customer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desired.offermachi.R;

public class ViewAllOfferFollowActivity extends AppCompatActivity {
    ImageView proimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_offer_follow_activity);

        TextView viewalloffer =(TextView)findViewById(R.id.viewalloffer_id);
        viewalloffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ViewAllOfferFollowActivity.this, ViewStoreOfferActivity.class);
                startActivity(intent);
            }
        });
        proimg=findViewById(R.id.proimg);
        proimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAllOfferFollowActivity.this, ViewAllOfferFollowActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout productlinearunfollow =(LinearLayout)findViewById(R.id.product_linear_unfollow_id);
        productlinearunfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //    Intent intent = new Intent(StoreCouponCodeActivity.this, ViewAllOfferFollowActivity.class);
                Intent intent = new Intent(ViewAllOfferFollowActivity.this, ViewStoreOfferActivity.class);
                startActivity(intent);

            }
        });

    }
}
