package com.desired.offermachi.retalier.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.view.activity.InfoActivity;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.DealsModel;
import com.desired.offermachi.retalier.model.DealsModelNew;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.presenter.DealsOftheDayPresenter;
import com.desired.offermachi.retalier.view.adapter.DealsOfAdapterNew;
import com.desired.offermachi.retalier.view.adapter.DealsOfDayAdapter;

import java.util.ArrayList;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class RetalierDealsOftheDayActivity extends AppCompatActivity implements View.OnClickListener, DealsOftheDayPresenter.DealsOftheDay {

//    Button addnewdeals;
    ImageView imageViewback,info;
    RecyclerView product_recyclerview;
//    private DealsOfDayAdapter dealsOfDayAdapter;
    private DealsOfAdapterNew dealsOfAdapterNew;
    private DealsOftheDayPresenter presenter;
    private String idholder;
    ImageView imgNotiBell;
    FloatingActionButton addDealOftheDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_deals_of_the_activity);
        init();
    }
    private void init(){
        presenter = new DealsOftheDayPresenter(this, RetalierDealsOftheDayActivity.this);
        UserModel user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        idholder= user.getId();
        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(this);
        info= findViewById(R.id.info_id);
        info.setOnClickListener(this);
//        addnewdeals=findViewById(R.id.addnewdeals_id);
//        addnewdeals.setOnClickListener(this);
//
        addDealOftheDay=findViewById(R.id.floting_add_botton);
        addDealOftheDay.setOnClickListener(this);


        product_recyclerview =findViewById(R.id.dealsoftheday_recycler_id);
        product_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        product_recyclerview.setItemAnimator(new DefaultItemAnimator());
        product_recyclerview.setHasFixedSize(true);
        product_recyclerview.setItemAnimator(new DefaultItemAnimator());

        imgNotiBell=findViewById(R.id.imgNotiBell);
        imgNotiBell.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CallAPI(1);
    }

    private void CallAPI(int i) {
        if (isNetworkConnected()) {
            switch (i)
            {
                case 1:
                    presenter.getRetailerDealsOftheDay(idholder);
                    break;
                case 2:
//                            presenter.getPushedOffer(idholder);
                    break;
            }
        }  else {
            showAlert("Please connect to internet.", R.style.DialogAnimation);
        }
    }

    @Override
    public void onClick(View v) {
        if (v==addDealOftheDay)//addnewdeals
        {
            Intent intent = new Intent(RetalierDealsOftheDayActivity.this, ActAddDealsOftheDay.class);
//            Intent intent = new Intent(RetalierDealsOftheDayActivity.this, RetalierAddDeals.class);
            startActivity(intent);
        }else if (v==imageViewback){
            onBackPressed();
        }else if (v==imgNotiBell){
            startActivity(new Intent(getApplicationContext(), RetalierNotificationActivity.class));
        }else if(v==info){
            Intent intent = new Intent(RetalierDealsOftheDayActivity.this, InfoActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void success(ArrayList<DealsModelNew> response) {
        dealsOfAdapterNew = new DealsOfAdapterNew(getApplicationContext(), response);
        product_recyclerview.setAdapter(dealsOfAdapterNew);
    }

    @Override
    public void Adddealsuccess(String response) {

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
        startActivity(new Intent(getApplicationContext(),RetalierDashboard.class));
        finish();
        super.onBackPressed();
    }
}
