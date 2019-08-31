package com.desired.offermachi.customer.view.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.desired.offermachi.R;

public class VerifyActivity extends AppCompatActivity implements View.OnClickListener {
    Button continuebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_activity);
        initview();
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
            Intent intent = new Intent(VerifyActivity.this, CategoryActivity.class);
            startActivity(intent);
            finish();
        }
    }
}



