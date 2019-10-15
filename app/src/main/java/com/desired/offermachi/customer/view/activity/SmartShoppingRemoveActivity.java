package com.desired.offermachi.customer.view.activity;

import android.app.Dialog;
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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.SmartShoppingNotificationDataPresenter;
import com.desired.offermachi.customer.presenter.TrendingListPresenter;
import com.desired.offermachi.customer.view.adapter.CustomerTrendingAdapter;
import com.desired.offermachi.customer.view.adapter.SmartShoppingNotificationAdapter;
import com.desired.offermachi.customer.view.adapter.SmartShoppingRemoveAdapter;
import com.desired.offermachi.customer.model.category_model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class SmartShoppingRemoveActivity  extends AppCompatActivity implements View.OnClickListener, SmartShoppingNotificationDataPresenter.NotificationOfferDataList {

    ImageView imageViewback,info;
    RecyclerView categoryrecycle;
    String result;
    private SmartShoppingNotificationAdapter smartShoppingNotificationAdapter;
    private SmartShoppingNotificationDataPresenter presenter;
    String idholder;
    TextView btnclearall;
    ImageView imgNotiBell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_shopping_text_activity);
        init();
    }
    private void init(){
        presenter=new SmartShoppingNotificationDataPresenter(SmartShoppingRemoveActivity.this,SmartShoppingRemoveActivity.this);
        User user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
        idholder=user.getId();
        imageViewback = findViewById(R.id.imageback);
        imageViewback.setOnClickListener(this);
        info= findViewById(R.id.info_id);
        info.setOnClickListener(this);
        categoryrecycle = findViewById(R.id.categoryrecycleview);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        categoryrecycle.setLayoutManager(gridLayoutManager1);
        categoryrecycle.setItemAnimator(new DefaultItemAnimator());
        categoryrecycle.setNestedScrollingEnabled(false);
        btnclearall=findViewById(R.id.clearall);
        btnclearall.setOnClickListener(this);
        imgNotiBell=findViewById(R.id.imgNotiBell);
        imgNotiBell.setOnClickListener(this);
        if (isNetworkConnected()) {
            presenter.ViewAllSmartData(idholder);
        } else {
            showAlert("Please connect to internet.", R.style.DialogAnimation);
        }

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(locationReceiver,
                new IntentFilter("Favourite"));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(CouponReceiver,
                new IntentFilter("Refresh"));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(RemoveReceiver,
                new IntentFilter("Remove"));
    }
    public BroadcastReceiver RemoveReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String offerid = intent.getStringExtra("offerid");
            presenter.Remove(idholder,offerid);

        }
    };
    public BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String fav = intent.getStringExtra("fav");
            String offerid = intent.getStringExtra("offerid");
            presenter.AddFavourite(idholder,offerid,fav);
        }
    };
    public BroadcastReceiver CouponReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent2) {
            if (isNetworkConnected()) {
                presenter.ViewAllSmartData(idholder);
            } else {
                showAlert("Please connect to internet.", R.style.DialogAnimation);
            }

        }
    };
    @Override
    public void onClick(View v) {
        if (v == imageViewback) {
            onBackPressed();
        }else if (v==btnclearall){
            presenter.RemoveAll(idholder);

        }else if (v==imgNotiBell){
            startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
        }else if(v==info){
            Intent intent = new Intent(SmartShoppingRemoveActivity.this, InfoActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void success(ArrayList<SelectCategoryModel> response) {
        smartShoppingNotificationAdapter = new SmartShoppingNotificationAdapter(SmartShoppingRemoveActivity.this,response);
        categoryrecycle.setAdapter(smartShoppingNotificationAdapter);
    }

    @Override
    public void favsuccess(String response) {
        finish();
        startActivity(getIntent());
    }

    @Override
    public void error(String response) {
        showAlert(response, R.style.DialogAnimation);
    }

    @Override
    public void fail(String response) {
        showAlert(response, R.style.DialogAnimation);
    }

    private void showAlert(String message, int animationSource) {
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
        super.onBackPressed();
        finish();
        startActivity(new Intent(getApplicationContext(),DashBoardActivity.class));
    }
}

