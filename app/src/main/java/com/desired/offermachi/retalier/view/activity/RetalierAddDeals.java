package com.desired.offermachi.retalier.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.presenter.NotificationCountPresenter;
import com.desired.offermachi.customer.view.activity.InfoActivity;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.DealsModel;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.model.ViewOfferModel;
import com.desired.offermachi.retalier.model.retalier_category_model;
import com.desired.offermachi.retalier.presenter.DealsOftheDayPresenter;
import com.desired.offermachi.retalier.view.adapter.AddDealsofDayAdapter;
import com.desired.offermachi.retalier.view.adapter.DealsOfDayAdapter;
import com.desired.offermachi.retalier.view.adapter.RetalierCategoryAdapter;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class RetalierAddDeals extends AppCompatActivity implements View.OnClickListener , DealsOftheDayPresenter.DealsOftheDay , NotificationCountPresenter.NotiUnReadCount {
    ImageView imageViewback,info;
    RecyclerView product_recyclerview;
    private AddDealsofDayAdapter addDealsofDayAdapter;
    private DealsOftheDayPresenter presenter;
    private String idholder,Offerid;
    ImageView imgNotiBell;
    TextView tvNotiCount;
    private NotificationCountPresenter notiCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_add_deals);
        init();
    }
     private void init(){
            presenter = new DealsOftheDayPresenter(this, RetalierAddDeals.this);
            UserModel user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
            idholder= user.getId();
            imageViewback=findViewById(R.id.imageback);
            imageViewback.setOnClickListener(this);
            info=findViewById(R.id.info_id);
            info.setOnClickListener(this);
            product_recyclerview = findViewById(R.id.addtodeals_recycler_id);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
            product_recyclerview.setLayoutManager(gridLayoutManager);
            product_recyclerview.setItemAnimator(new DefaultItemAnimator());
            notiCount = new NotificationCountPresenter(this,this);
            tvNotiCount = findViewById(R.id.txtMessageCount);
            notiCount.NotificationUnreadCount(idholder);

         if (isNetworkConnected()) {
             presenter.getDealsOftheDay(idholder);
         }  else {
             showAlert("Please connect to internet.", R.style.DialogAnimation);
         }
         LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(locationReceiver,
                 new IntentFilter("Offer"));
         imgNotiBell=findViewById(R.id.imgNotiBell);
         imgNotiBell.setOnClickListener(this);
    }
    public BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Offerid = intent.getStringExtra("offerid");
            if (isNetworkConnected()) {
                presenter.AddtoDeals(Offerid);
            }  else {
                showAlert("Please connect to internet.", R.style.DialogAnimation);
            }

        }
    };


    @Override
    public void onClick(View v) {
        if (v==imageViewback){
            onBackPressed();
        }else if (v==imgNotiBell){
            startActivity(new Intent(getApplicationContext(), RetalierNotificationActivity.class));
        }else if(v==info){
            Intent intent = new Intent(RetalierAddDeals.this, InfoActivity.class);
            startActivity(intent);
        }

    }
    @Override
    public void Adddealsuccess(String response) {
        Toast.makeText(this, ""+response, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void success(ArrayList<DealsModel> response) {
        addDealsofDayAdapter = new AddDealsofDayAdapter(getApplicationContext(), response);
        product_recyclerview.setAdapter(addDealsofDayAdapter);
    }

    @Override
    public void error(String response) {
        showAlert(response, R.style.DialogAnimation);
    }

    @Override
    public void fail(String response) {
        showAlert(response, R.style.DialogAnimation);
    }
    private void showAlert(String message, int animationSource){
        final PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog.setCanceledOnTouchOutside(false);
        TextView title = (TextView) prettyDialog.findViewById(libs.mjn.prettydialog.R.id.tv_title);
        TextView tvmessage = (TextView) prettyDialog.findViewById(libs.mjn.prettydialog.R.id.tv_message);
        title.setTextSize(15);
        tvmessage.setTextSize(15);
        prettyDialog.setIconTint(R.color.colorPrimary);
        prettyDialog.setIcon(R.drawable.pdlg_icon_info);
        prettyDialog.setTitle("");
        prettyDialog.setMessage(message);
        prettyDialog.setAnimationEnabled(false);
        prettyDialog.getWindow().getAttributes().windowAnimations = animationSource;
        prettyDialog.addButton("Ok", R.color.black, R.color.white, new PrettyDialogCallback() {
            @Override
            public void onClick() {
                prettyDialog.dismiss();
            }
        }).show();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),RetalierDealsOftheDayActivity.class));
        finish();
        super.onBackPressed();
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