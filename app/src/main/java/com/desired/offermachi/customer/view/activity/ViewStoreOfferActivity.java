package com.desired.offermachi.customer.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.model.category_model;
import com.desired.offermachi.customer.presenter.CustomerSelectCategoryPresenter;
import com.desired.offermachi.customer.presenter.NotificationCountPresenter;
import com.desired.offermachi.customer.presenter.ViewStoreOfferPresenter;
import com.desired.offermachi.customer.view.adapter.CustomerTrendingAdapter;
import com.desired.offermachi.customer.view.adapter.CustomerTrendingAdapterNew;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class ViewStoreOfferActivity extends AppCompatActivity implements View.OnClickListener, ViewStoreOfferPresenter.ViewOffer, NotificationCountPresenter.NotiUnReadCount{
    ImageView imageViewback;
    RecyclerView categoryrecycle;
//    private CustomerTrendingAdapter customerTrendingAdapter;
    private CustomerTrendingAdapterNew customerTrendingAdapter;
    private ViewStoreOfferPresenter presenter;
    String idholder,storeid;
    TextView tvNotiCount;
    private NotificationCountPresenter notiCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_store_offer);
        initview();
    }
    private void initview(){
        User user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
        idholder= user.getId();
        Intent intent=getIntent();
        storeid=intent.getStringExtra("storeid");
        presenter = new ViewStoreOfferPresenter(ViewStoreOfferActivity.this, ViewStoreOfferActivity.this);
        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(this);
        categoryrecycle=findViewById(R.id.categoryrecycleview);
//        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
//        categoryrecycle.setLayoutManager(gridLayoutManager1);
        categoryrecycle.setHasFixedSize(true);
        categoryrecycle.setLayoutManager(new LinearLayoutManager(this));
        categoryrecycle.setItemAnimator(new DefaultItemAnimator());
        categoryrecycle.setNestedScrollingEnabled(false);

        notiCount = new NotificationCountPresenter(this,this);
        tvNotiCount = findViewById(R.id.txtMessageCount);
        notiCount.NotificationUnreadCount(idholder);


        if (isNetworkConnected()) {
            presenter.ViewAllStoreOffer(idholder,storeid);
        }  else {
            showAlert("Please connect to internet.", R.style.DialogAnimation);
        }
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(locationReceiver,
                new IntentFilter("Favourite"));
    }
    public BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String fav = intent.getStringExtra("fav");
            String offerid = intent.getStringExtra("offerid");
            presenter.AddFavourite(idholder,offerid,fav);
        }
    };
    @Override
    public void onClick(View v) {
        if (v==imageViewback){
            onBackPressed();
        }
    }

    @Override
    public void success(ArrayList<SelectCategoryModel> response) {
//        customerTrendingAdapter=new CustomerTrendingAdapter(ViewStoreOfferActivity.this,response);
        customerTrendingAdapter=new CustomerTrendingAdapterNew(ViewStoreOfferActivity.this,response);
        categoryrecycle.setAdapter(customerTrendingAdapter);
    }

    @Override
    public void favsuccess(String response) {
        if (isNetworkConnected()) {
            presenter.ViewAllStoreOffer(idholder,storeid);
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
