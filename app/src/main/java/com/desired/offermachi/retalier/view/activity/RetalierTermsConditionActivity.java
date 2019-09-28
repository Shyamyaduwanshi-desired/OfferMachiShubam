package com.desired.offermachi.retalier.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.NotificationCountPresenter;
import com.desired.offermachi.customer.view.activity.InfoActivity;

public class RetalierTermsConditionActivity extends AppCompatActivity implements View.OnClickListener,NotificationCountPresenter.NotiUnReadCount {
    ImageView imageViewback,info;
    ImageView imgNotiBell;
    private String idholder;
    TextView tvNotiCount;
    private NotificationCountPresenter notiCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_tems_condition);

        User user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
        idholder= user.getId();
        notiCount = new NotificationCountPresenter(this,this);
        tvNotiCount = findViewById(R.id.txtMessageCount);
        notiCount.NotificationUnreadCount(idholder);

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

    @Override
    public void successnoti(String response) {
        if(TextUtils.isEmpty(response))
        {
            tvNotiCount.setText("0");
        }
        else {

            tvNotiCount.setText(response);
        }
    }

    @Override
    public void errornoti(String response) {

    }

    @Override
    public void failnoti(String response) {

    }
}

