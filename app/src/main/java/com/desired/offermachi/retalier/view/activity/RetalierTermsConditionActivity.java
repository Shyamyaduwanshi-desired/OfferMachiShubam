package com.desired.offermachi.retalier.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.desired.offermachi.R;

public class RetalierTermsConditionActivity extends AppCompatActivity {
    ImageView imageViewback ;

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
        WebView mywebview = (WebView)findViewById(R.id.webView1);
        mywebview.loadUrl("http://offermachi.in/pages/terms_condition");

    }
}

