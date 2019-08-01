package com.desired.offermachi.customer.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.desired.offermachi.R;

public class OtpActivtivity extends AppCompatActivity {

    Button otpsubmitbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_activity);

        TextView topdealsoftheday=(TextView)findViewById(R.id.otp_text_id);
        Typeface otp= ResourcesCompat.getFont(getApplicationContext(), R.font.ralewaybold);
        topdealsoftheday.setTypeface(otp);

        otpsubmitbutton=(Button)findViewById(R.id.submit_button_id);
        otpsubmitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtpActivtivity.this, VerifyActivity.class);
                startActivity(intent);

            }

        });

    }
}




