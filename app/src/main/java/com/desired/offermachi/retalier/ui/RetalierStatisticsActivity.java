package com.desired.offermachi.retalier.ui;

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

        TextView firsttext =(TextView)findViewById(R.id.first_text_id);
        firsttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RetalierStatisticsActivity.this, RetalierListPeopleActivity.class);
                startActivity(intent);
            }
        });

        TextView secondtext =(TextView)findViewById(R.id.second_text_id);
        secondtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RetalierStatisticsActivity.this, RetalierListPeopleActivity.class);
                startActivity(intent);
            }
        });

    }
}
