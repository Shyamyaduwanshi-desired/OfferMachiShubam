package com.desired.offermachi.retalier.view.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
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

import com.desired.offermachi.R;
import com.desired.offermachi.customer.presenter.NotificationCountPresenter;
import com.desired.offermachi.customer.view.activity.InfoActivity;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.PeopleModel;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.model.ViewOfferModel;
import com.desired.offermachi.retalier.presenter.RetailerPeopleListPresenter;
import com.desired.offermachi.retalier.view.adapter.PeopleListAdapter;
import com.desired.offermachi.retalier.view.adapter.PushOfferAdapter;

import java.util.ArrayList;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class RetalierListPeopleActivity  extends AppCompatActivity implements View.OnClickListener, RetailerPeopleListPresenter.PeopleList, NotificationCountPresenter.NotiUnReadCount {

    RecyclerView recyclerView;
    String idholder;
    ImageView imageViewback,info;
    private PeopleListAdapter peopleListAdapter;
    private RetailerPeopleListPresenter presenter;
    String status;
    TextView tvNotiCount;
    private NotificationCountPresenter notiCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_list_of_people_activity);
        init();

    }

    private void init(){
        Intent intent=getIntent();
        status=intent.getStringExtra("status");
        presenter=new RetailerPeopleListPresenter(RetalierListPeopleActivity.this,RetalierListPeopleActivity.this);
        UserModel user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        idholder= user.getId();
        imageViewback = findViewById(R.id.imageback);
        imageViewback.setOnClickListener(this);
        info= findViewById(R.id.info_id);
        info.setOnClickListener(this);
        recyclerView=findViewById(R.id.recyclelist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        notiCount = new NotificationCountPresenter(this,this);
        tvNotiCount = findViewById(R.id.txtMessageCount);
        notiCount.NotificationUnreadCount(idholder);

        if (isNetworkConnected()){
            presenter.GetPeopleList(idholder,status);

        }else{
            showAlert("Please connect Internet.", R.style.DialogAnimation);
        }
    }
    @Override
    public void onClick(View v) {
        if (v==imageViewback){
            onBackPressed();
        }else if(v==info){
            Intent intent = new Intent(RetalierListPeopleActivity.this, InfoActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void success(ArrayList<PeopleModel> response) {
        peopleListAdapter = new PeopleListAdapter(getApplicationContext(),response);
        recyclerView.setAdapter(peopleListAdapter);
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
        startActivity(new Intent(getApplicationContext(),RetalierStatisticsActivity.class));
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
