package com.desired.offermachi.retalier.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desired.offermachi.R;

public class RetalierForgotPassword extends AppCompatActivity {

    EditText forgotmobile;
    Button donebutton;
    LinearLayout resetlinear ,mobilecategory;
    int count=0;
    int countBACK=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_forgot_password);

        mobilecategory=(LinearLayout) findViewById(R.id.mobilecategory_linear_id);
        donebutton=(Button)findViewById(R.id.done_button_id);
        resetlinear=(LinearLayout)findViewById(R.id.resetlinear_id);


        TextView topdealsoftheday=(TextView)findViewById(R.id.forgot_password_text_id);
        Typeface forgotpassword= ResourcesCompat.getFont(getApplicationContext(), R.font.ralewaybold);
        topdealsoftheday.setTypeface(forgotpassword);

        TextView topdealsoftheday1=(TextView)findViewById(R.id.reset_text_id);
        Typeface resetpassword= ResourcesCompat.getFont(getApplicationContext(), R.font.ralewaybold);
        topdealsoftheday1.setTypeface(resetpassword);

        mobilecategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countBACK=1;
                if (count==0){
                    resetlinear.setVisibility(View.GONE);
                    count=1;

                }else{
                    resetlinear.setVisibility(View.VISIBLE);
                    count=0;
                }
            }
        });
        donebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RetalierForgotPassword.this, RetalierRegistration.class);
                startActivity(intent);
            }
        });

    }
}



