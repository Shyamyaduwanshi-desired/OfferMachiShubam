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


public class RetalierLogin extends AppCompatActivity {

    TextView forgotpasswordtext,registertext;
    Button loginnbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_login);
        forgotpasswordtext=(TextView)findViewById(R.id.forgot_password_id);
        registertext=(TextView)findViewById(R.id.register_text_id);
        loginnbutton=(Button)findViewById(R.id.login_button_id);
        TextView topdealsoftheday=(TextView)findViewById(R.id.login_text_id);
        Typeface otp= ResourcesCompat.getFont(getApplicationContext(), R.font.ralewaybold);
        topdealsoftheday.setTypeface(otp);

        forgotpasswordtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RetalierLogin.this, RetalierForgotPassword.class);
                startActivity(intent);
            }
        });

        registertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RetalierLogin.this, RetalierRegistration.class);
                startActivity(intent);
            }
        });
        loginnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RetalierLogin.this, RetalierDashboard.class);
                startActivity(intent);
            }
        });

    }
}


