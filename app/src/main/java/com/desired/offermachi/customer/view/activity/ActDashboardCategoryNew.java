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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.CategoryListModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.CustomerCategoryListPresenter;
import com.desired.offermachi.customer.view.adapter.CategortListAdapter;
import com.desired.offermachi.customer.view.adapter.DashboardCategortListAdapter;

import java.util.ArrayList;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class ActDashboardCategoryNew extends AppCompatActivity implements View.OnClickListener, CustomerCategoryListPresenter.CustomerCategoryList {
    ImageView imageViewback,ivInfo;
    RecyclerView product_recyclerview;
    private DashboardCategortListAdapter categortListAdapter=null;
    private CustomerCategoryListPresenter presenter;
    private String idholder,followsatus,Catid;
    int adptrPos=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);
        initview();
    }

      private void initview(){
          presenter = new CustomerCategoryListPresenter(ActDashboardCategoryNew.this, ActDashboardCategoryNew.this);
          User user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
          idholder= user.getId();
          imageViewback = findViewById(R.id.imageback);
          ivInfo = findViewById(R.id.info_id);

          product_recyclerview = (RecyclerView) findViewById(R.id.category_recycler_id);
          GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4, LinearLayoutManager.VERTICAL, false);
          product_recyclerview.setLayoutManager(gridLayoutManager);
          product_recyclerview.setItemAnimator(new DefaultItemAnimator());
          if (isNetworkConnected()) {
              presenter.GetCategoryList(idholder);//for show all category
          }  else {
              showAlert("Please connect to internet.", R.style.DialogAnimation);
          }

          LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(followReceiver,
                  new IntentFilter("Follow"));


          imageViewback.setOnClickListener(this);
          ivInfo.setVisibility(View.GONE);
      }

    public BroadcastReceiver followReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            followsatus = intent.getStringExtra("followstatus");
            Catid = intent.getStringExtra("catid");
            adptrPos = intent.getIntExtra("pos",0);

            if (isNetworkConnected()) {
                presenter.CategoryFollow(idholder,Catid,followsatus);
            }  else {
                showAlert("Please connect to internet.", R.style.DialogAnimation);
            }
        }
    };

    @Override
    public void onClick(View v) {
        if (v==imageViewback){
            onBackPressed();
        }
    }

    @Override
    public void followsuccess(String response) {
        arCatList.get(adptrPos).setFollowstatus(followsatus);
        if (categortListAdapter != null)
        {
            categortListAdapter.notifyDataSetChanged();
        }

    }
    ArrayList<CategoryListModel> arCatList=new ArrayList<>();
    @Override
    public void success(ArrayList<CategoryListModel> response) {
        arCatList.clear();
        arCatList=response;

        categortListAdapter = new DashboardCategortListAdapter(this,getApplicationContext(),arCatList);
        product_recyclerview.setAdapter(categortListAdapter);
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
        finish();
    }

}
