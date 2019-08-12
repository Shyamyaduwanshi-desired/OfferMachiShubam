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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.model.ViewOfferModel;
import com.desired.offermachi.retalier.presenter.ViewOfferPresenter;
import com.desired.offermachi.retalier.view.adapter.ViewOfferDiscountAdapter;

import java.util.ArrayList;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class RetalierViewOfferDiscount extends AppCompatActivity implements View.OnClickListener, ViewOfferPresenter.OfferDiscount {

    ImageView imageViewback;
    RecyclerView product_recyclerview;
    private ViewOfferDiscountAdapter viewOfferAdapter;
    private ViewOfferPresenter presenter;
    private String idholder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_view_offer_discount);
        init();
    }
    private void init(){
        presenter = new ViewOfferPresenter(this, RetalierViewOfferDiscount.this);
        UserModel user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        idholder= user.getId();
        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(this);
        product_recyclerview = (RecyclerView)findViewById(R.id.view_opffer_discount_recycler_id);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
        product_recyclerview.setLayoutManager(gridLayoutManager);
        product_recyclerview.setItemAnimator(new DefaultItemAnimator());
        if (isNetworkConnected()) {
            presenter.getOfferDiscount(idholder);
        }  else {
            showAlert("Please connect to internet.", R.style.DialogAnimation);
    }

    }

    @Override
    public void onClick(View v) {
        if (v==imageViewback){
            onBackPressed();
        }
    }

    @Override
    public void success(ArrayList<ViewOfferModel> response) {
        viewOfferAdapter = new ViewOfferDiscountAdapter(getApplicationContext(), response);
        product_recyclerview.setAdapter(viewOfferAdapter);
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

