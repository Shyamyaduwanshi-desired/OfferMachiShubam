package com.desired.offermachi.retalier.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.NotificationCountPresenter;
import com.desired.offermachi.customer.view.activity.InfoActivity;

public class ListOfPeople0fferDiscount extends AppCompatActivity implements NotificationCountPresenter.NotiUnReadCount {

    Button viewcouponbutton;
    ImageView info;
    TextView tvNotiCount;
    private NotificationCountPresenter notiCount;
    private String idholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_list_people_offer_discount);
        User user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
        idholder= user.getId();
        notiCount = new NotificationCountPresenter(this,this);
        tvNotiCount = findViewById(R.id.txtMessageCount);
        notiCount.NotificationUnreadCount(idholder);

        viewcouponbutton=(Button)findViewById(R.id.viewoffer_discount_id);
        viewcouponbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListOfPeople0fferDiscount.this, RetalierViewOfferDiscount.class);
                startActivity(intent);
            }
        });

        info=findViewById(R.id.info_id);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(ListOfPeople0fferDiscount.this, InfoActivity.class);
                    startActivity(intent);

            }
        });
    }

    @Override
    public void successnoti(String response) {

    }

    @Override
    public void errornoti(String response) {

    }

    @Override
    public void failnoti(String response) {

    }
}
