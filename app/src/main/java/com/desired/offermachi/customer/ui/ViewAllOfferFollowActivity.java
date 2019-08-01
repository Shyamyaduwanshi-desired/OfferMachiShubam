package com.desired.offermachi.customer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.desired.offermachi.R;

public class ViewAllOfferFollowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_offer_follow_activity);

        TextView viewalloffer =(TextView)findViewById(R.id.viewalloffer_id);
        viewalloffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ViewAllOfferFollowActivity.this, ViewOfferActivity.class);
                startActivity(intent);
            }
        });
    }
}
