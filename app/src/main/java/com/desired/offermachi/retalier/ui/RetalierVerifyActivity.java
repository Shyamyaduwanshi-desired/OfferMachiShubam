package com.desired.offermachi.retalier.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.desired.offermachi.R;

public class RetalierVerifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_verify_activity);

        TextView topdealsoftheday=(TextView)findViewById(R.id.verify_text_id);
        Typeface verify= ResourcesCompat.getFont(getApplicationContext(), R.font.ralewaybold);
        topdealsoftheday.setTypeface(verify);

        Button continuebutton =(Button)findViewById(R.id.login_button_id);
        continuebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RetalierVerifyActivity.this, RetalierDashboard.class);
                startActivity(intent);

            }
        });


    }

}
