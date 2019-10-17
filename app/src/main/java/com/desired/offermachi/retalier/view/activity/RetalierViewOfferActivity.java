package com.desired.offermachi.retalier.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.NotificationCountPresenter;
import com.desired.offermachi.customer.view.activity.InfoActivity;
import com.desired.offermachi.retalier.model.ViewOfferModel;
import com.desired.offermachi.retalier.view.adapter.ViewOfferDiscountAdapter;

import java.util.ArrayList;
import java.util.List;

public class RetalierViewOfferActivity extends AppCompatActivity implements  NotificationCountPresenter.NotiUnReadCount  {

    ImageView imageViewback,info;
    RecyclerView product_recyclerview;
    private ViewOfferDiscountAdapter viewOfferAdapter;
    private List<ViewOfferModel> viewcategorylistdataset = new ArrayList<>();
    private Context mContext;
    TextView tvNotiCount;
    private NotificationCountPresenter notiCount;
    private String idholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_view_offers_activity);
        mContext=getApplicationContext();

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
                    Intent intent = new Intent(RetalierViewOfferActivity.this, InfoActivity.class);
                    startActivity(intent);
            }
        });



        product_recyclerview = (RecyclerView)findViewById(R.id.viewalloffer_recycler_id);
        viewOfferAdapter = new ViewOfferDiscountAdapter(getApplicationContext(), (ArrayList<ViewOfferModel>) viewcategorylistdataset);
        product_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        product_recyclerview.setItemAnimator(new DefaultItemAnimator());
        product_recyclerview.setHasFixedSize(true);
        product_recyclerview.setItemAnimator(new DefaultItemAnimator());
        product_recyclerview.setAdapter(viewOfferAdapter);
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

