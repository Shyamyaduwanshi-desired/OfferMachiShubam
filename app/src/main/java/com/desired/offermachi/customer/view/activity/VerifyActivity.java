package com.desired.offermachi.customer.view.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.view.activity.RetalierDashboard;
import com.desired.offermachi.retalier.view.activity.RetalierVerifyActivity;

public class VerifyActivity extends AppCompatActivity implements View.OnClickListener {
    Button continuebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_activity);
        initview();
        startTimer();
    }
    private void initview(){
        TextView topdealsoftheday=(TextView)findViewById(R.id.verify_text_id);
        Typeface verify= ResourcesCompat.getFont(getApplicationContext(), R.font.ralewaybold);
        topdealsoftheday.setTypeface(verify);
      continuebutton=findViewById(R.id.login_button_id);
      continuebutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==continuebutton){
            Nevigate();
        }
    }
    public void Nevigate()
    {
        cancelTimer();
        if (UserSharedPrefManager.getInstance(VerifyActivity.this).isLoggedIn()) {//for customer
//                Intent intent = new Intent(VerifyActivity.this, CategoryActivity.class);
            Intent intent = new Intent(VerifyActivity.this, ActInterestCategoryNew.class);
            startActivity(intent);
            finish();
        }
        else  if (SharedPrefManagerLogin.getInstance(VerifyActivity.this).isLoggedIn()) {//for retailer

            Intent intent = new Intent(VerifyActivity.this, RetalierDashboard.class);
            startActivity(intent);
            finish();
        }

    }
    CountDownTimer cTimer = null;
    //start timer function
    void startTimer() {
        cTimer = new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                Nevigate();
            }
        };
        cTimer.start();
    }


    //cancel timer
    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }

}



