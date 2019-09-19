package com.desired.offermachi.retalier.view.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.FollowerModel;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.model.ViewOfferModel;
import com.desired.offermachi.retalier.presenter.FollowerPresenter;
import com.desired.offermachi.retalier.presenter.ViewOfferPresenter;
import com.desired.offermachi.retalier.view.adapter.MultiAdapter;
import com.desired.offermachi.retalier.view.adapter.PushOfferAdapter;

import java.util.ArrayList;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class ActRetalierPushOffer extends AppCompatActivity implements View.OnClickListener ,ViewOfferPresenter.OfferDiscount, FollowerPresenter.Follower {

    ImageView imageViewback;
    RecyclerView product_recyclerview;
    private PushOfferAdapter pushOfferAdapter;
    private MultiAdapter multiAdapter;
    private ViewOfferPresenter presenter;
    private FollowerPresenter followerpresenter;
    private String idholder,PushOfferid;
    AlertDialog alertDialog;
    ImageView imgNotiBell;
    Button btAddPushOffer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_reatalier_push_offer);
        init();
    }

  private void init(){
      presenter = new ViewOfferPresenter(this, ActRetalierPushOffer.this);
      followerpresenter = new FollowerPresenter(this, ActRetalierPushOffer.this);
      UserModel user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
      idholder= user.getId();
      btAddPushOffer = findViewById(R.id.bt_add_push_offfer);
      imageViewback = findViewById(R.id.imageback);
      product_recyclerview = findViewById(R.id.pushoffer_recycler_id);

      GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
      product_recyclerview.setLayoutManager(gridLayoutManager);
      product_recyclerview.setItemAnimator(new DefaultItemAnimator());

   /*   if (isNetworkConnected()) {
          presenter.getOfferDiscount(idholder);
      }  else {
          showAlert("Please connect to internet.", R.style.DialogAnimation);
      }*/

      LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(pushReceiver,
              new IntentFilter("Push"));
      imgNotiBell=findViewById(R.id.imgNotiBell);

      imgNotiBell.setOnClickListener(this);
      imageViewback.setOnClickListener(this);
      btAddPushOffer.setOnClickListener(this);


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
                    presenter.getPushedOffer(idholder);
                    break;
                case 2:
//                            presenter.getPushedOffer(idholder);
                    break;
            }
        }  else {
            showAlert("Please connect to internet.", R.style.DialogAnimation);
        }
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
        }else if (v==btAddPushOffer){
            startActivity(new Intent(getApplicationContext(), ActAddPushOffer.class));
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
//        startActivity(new Intent(getApplicationContext(),RetalierDashboard.class));
        finish();
//        super.onBackPressed();
    }
    @Override
    public void followersuccess(ArrayList<FollowerModel> followerModels) {
        try {
            LayoutInflater li = LayoutInflater.from(ActRetalierPushOffer.this);
            View confirmDialog = li.inflate(R.layout.dialog_followers, null);
            RecyclerView recyclerView = (RecyclerView) confirmDialog.findViewById(R.id.recyclerViewrate);
            Button btnsend = (Button) confirmDialog.findViewById(R.id.sendoffer);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            multiAdapter = new MultiAdapter(getApplicationContext(),followerModels);
            recyclerView.setAdapter(multiAdapter);

            AlertDialog.Builder alert = new AlertDialog.Builder(ActRetalierPushOffer.this);
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
                        Toast.makeText(ActRetalierPushOffer.this, "No Selection", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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



