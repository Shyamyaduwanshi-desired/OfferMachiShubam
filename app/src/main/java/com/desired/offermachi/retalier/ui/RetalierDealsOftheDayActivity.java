package com.desired.offermachi.retalier.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.desired.offermachi.R;

public class RetalierDealsOftheDayActivity extends AppCompatActivity {

    Button addnewdeals;
    ImageView imageViewback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_deals_of_the_activity);

        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addnewdeals=(Button)findViewById(R.id.addnewdeals_id);
        addnewdeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RetalierDealsOftheDayActivity.this, RetalierAddDeals.class);
                startActivity(intent);
            }
        });

    }
}
