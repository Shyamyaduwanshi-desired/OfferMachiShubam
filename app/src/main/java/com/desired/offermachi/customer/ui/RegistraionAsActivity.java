package com.desired.offermachi.customer.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.ui.RetalierLogin;

public class RegistraionAsActivity extends AppCompatActivity {

    RadioButton customerradiobutton ,reatalierradiobutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registraion_as_activity);


        TextView topdealsoftheday=(TextView)findViewById(R.id.regitringas_text_id);
        Typeface regitringastext= ResourcesCompat.getFont(getApplicationContext(), R.font.ralewaybold);
        topdealsoftheday.setTypeface(regitringastext);


        customerradiobutton =(RadioButton)findViewById(R.id.customer_radio_button_id);
        customerradiobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegistraionAsActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        reatalierradiobutton=(RadioButton)findViewById(R.id.reatalier_radio_id);
        reatalierradiobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegistraionAsActivity.this, RetalierLogin.class);
                startActivity(intent);

            }
        });










    }
}

