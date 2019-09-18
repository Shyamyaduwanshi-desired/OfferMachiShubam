package com.desired.offermachi.retalier.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.view.activity.InfoActivity;

public class RetalierTermsConditionActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageViewback,info;
    ImageView imgNotiBell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_tems_condition);

        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        info=findViewById(R.id.info_id);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(RetalierTermsConditionActivity.this, InfoActivity.class);
                    startActivity(intent);

            }
        });
        WebView mywebview = (WebView)findViewById(R.id.webView1);
        mywebview.loadUrl("http://offermachi.in/pages/terms_condition");
        imgNotiBell=findViewById(R.id.imgNotiBell);
        imgNotiBell.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v==imgNotiBell){
            startActivity(new Intent(getApplicationContext(), RetalierNotificationActivity.class));
        }
    }
}

