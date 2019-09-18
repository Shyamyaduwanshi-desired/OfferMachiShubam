package com.desired.offermachi.retalier.view.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.R;
import com.desired.offermachi.customer.view.activity.InfoActivity;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.FollowerModel;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.model.ViewOfferModel;
import com.desired.offermachi.retalier.presenter.FollowerPresenter;
import com.desired.offermachi.retalier.presenter.ViewOfferPresenter;
import com.desired.offermachi.retalier.view.adapter.MultiAdapter;
import com.desired.offermachi.retalier.view.adapter.PushOfferAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class RetalierPushActivity extends AppCompatActivity implements View.OnClickListener ,ViewOfferPresenter.OfferDiscount, FollowerPresenter.Follower {

    ImageView imageViewback,info;
    RecyclerView product_recyclerview;
    private PushOfferAdapter pushOfferAdapter;
    private MultiAdapter multiAdapter;
    private ViewOfferPresenter presenter;
    private FollowerPresenter followerpresenter;
    private String idholder,PushOfferid;
    AlertDialog alertDialog;
    ImageView imgNotiBell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reatalier_push_offer_activity);
        init();
    }

  private void init(){
      presenter = new ViewOfferPresenter(this, RetalierPushActivity.this);
      followerpresenter = new FollowerPresenter(this, RetalierPushActivity.this);
      UserModel user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
      idholder= user.getId();
      imageViewback = findViewById(R.id.imageback);
      imageViewback.setOnClickListener(this);
      info= findViewById(R.id.info_id);
      info.setOnClickListener(this);
      product_recyclerview = findViewById(R.id.pushoffer_recycler_id);
      GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
      product_recyclerview.setLayoutManager(gridLayoutManager);
      product_recyclerview.setItemAnimator(new DefaultItemAnimator());
      if (isNetworkConnected()) {
          presenter.getOfferDiscount(idholder);
      }  else {
          showAlert("Please connect to internet.", R.style.DialogAnimation);
      }
      LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(pushReceiver,
              new IntentFilter("Push"));
      imgNotiBell=findViewById(R.id.imgNotiBell);
      imgNotiBell.setOnClickListener(this);


  }
    public BroadcastReceiver pushReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            PushOfferid = intent.getStringExtra("pushofferid");
            if (isNetworkConnected()) {
                followerpresenter.sentRequest(idholder);
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
            Intent intent = new Intent(RetalierPushActivity.this, InfoActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public void success(ArrayList<ViewOfferModel> response) {
        pushOfferAdapter = new PushOfferAdapter(getApplicationContext(),response);
        product_recyclerview.setAdapter(pushOfferAdapter);
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
    @Override
    public void followersuccess(ArrayList<FollowerModel> followerModels) {
        LayoutInflater li = LayoutInflater.from(RetalierPushActivity.this);
        View confirmDialog = li.inflate(R.layout.dialog_followers, null);
        RecyclerView recyclerView = (RecyclerView) confirmDialog.findViewById(R.id.recyclerViewrate);
        Button btnsend = (Button) confirmDialog.findViewById(R.id.sendoffer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        multiAdapter = new MultiAdapter(getApplicationContext(),followerModels);
        recyclerView.setAdapter(multiAdapter);
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(RetalierPushActivity.this);
        alert.setView(confirmDialog);
        alertDialog = alert.create();
        WindowManager.LayoutParams wmlp =  alertDialog.getWindow().getAttributes();
        wmlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        alertDialog.getWindow().setAttributes(wmlp);
        alertDialog.show();
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (multiAdapter.getSelected().size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < multiAdapter.getSelected().size(); i++) {
                        stringBuilder.append(multiAdapter.getSelected().get(i).getId());
                        stringBuilder.append(",");
                    }
                    String followerid=stringBuilder.toString();
                    if (isNetworkConnected()) {
                        followerpresenter.SendOffer(idholder,PushOfferid,followerid);
                      //  Toast.makeText(RetalierPushActivity.this, ""+followerid, Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }  else {
                        alertDialog.dismiss();
                        showAlert("Please connect to internet.", R.style.DialogAnimation);
                    }

                } else {
                    Toast.makeText(RetalierPushActivity.this, "No Selection", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void followersend(String response) {
        Toast.makeText(this, ""+response, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void followererror(String response) {
        showAlert(response, R.style.DialogAnimation);
    }

    @Override
    public void followerfail(String response) {
        showAlert(response, R.style.DialogAnimation);
    }

}



