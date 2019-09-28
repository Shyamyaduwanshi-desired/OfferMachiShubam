package com.desired.offermachi.customer.view.activity;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.StoreModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.HomePresenter;
import com.desired.offermachi.customer.presenter.NotificationCountPresenter;
import com.desired.offermachi.customer.presenter.StoreListPresenter;
import com.desired.offermachi.customer.view.adapter.CustomerStoreAdapter;
import com.desired.offermachi.customer.view.adapter.StoreViewallAdapter;
import com.desired.offermachi.customer.view.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class StoreCouponCodeActivity extends AppCompatActivity implements View.OnClickListener, StoreListPresenter.StoreList , NotificationCountPresenter.NotiUnReadCount {

    ImageView imageViewback,info;
    RecyclerView storerecycle;
    private CustomerStoreAdapter customerStoreAdapter;
    private StoreListPresenter presenter;
    String idholder;TextView tvNotiCount;
    private NotificationCountPresenter notiCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_coupon_code_activity);
        initview();
    }
    private void initview(){
        User user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
        idholder= user.getId();
        presenter = new StoreListPresenter(StoreCouponCodeActivity.this, StoreCouponCodeActivity.this);
        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(this);
        info=findViewById(R.id.info_id);
        info.setOnClickListener(this);
        storerecycle=findViewById(R.id.storerecycleview);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getApplicationContext(), 3, LinearLayoutManager.VERTICAL, false);
        storerecycle.setLayoutManager(gridLayoutManager2);
        storerecycle.setItemAnimator(new DefaultItemAnimator());
        notiCount = new NotificationCountPresenter(this,this);
        tvNotiCount = findViewById(R.id.txtMessageCount);
        notiCount.NotificationUnreadCount(idholder);
        if (isNetworkConnected()) {
            presenter.ViewAllStore(idholder);
        }  else {
            showAlert("Please connect to internet.", R.style.DialogAnimation);
        }
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(StoreReceiver,
                new IntentFilter("StoreFollow"));
    }
    public BroadcastReceiver StoreReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = intent.getStringExtra("followstatus");
            String storeid = intent.getStringExtra("storeid");
            presenter.FollowStore(idholder,storeid,status);
        }
    };
    @Override
    public void onClick(View v) {
        if (v==imageViewback){
            onBackPressed();
        }else if(v==info){
            Intent intent = new Intent(StoreCouponCodeActivity.this, InfoActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void success(ArrayList<StoreModel> response) {
        customerStoreAdapter=new CustomerStoreAdapter(getApplicationContext(),response);
        storerecycle.setAdapter(customerStoreAdapter);
    }

    @Override
    public void followsuccess(String response) {
        if (isNetworkConnected()) {
            presenter.ViewAllStore(idholder);
        }  else {
            showAlert("Please connect to internet.", R.style.DialogAnimation);
        }
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
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
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

