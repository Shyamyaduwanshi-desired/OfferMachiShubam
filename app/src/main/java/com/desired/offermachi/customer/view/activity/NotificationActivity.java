package com.desired.offermachi.customer.view.activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.NotificationModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.CustomerNotificationPresenter;
import com.desired.offermachi.customer.view.adapter.CustomerNotificationAdapter;
import com.desired.offermachi.customer.view.adapter.CustomerTrendingAdapter;

import java.util.ArrayList;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, CustomerNotificationPresenter.NotificationList {
    ImageView imageViewback;
    Switch simpleSwitch;
    private CustomerNotificationAdapter customerNotificationAdapter;
    private CustomerNotificationPresenter presenter;
    RecyclerView categoryrecycle;
    String idholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        init();
    }
    private void init(){
        presenter=new CustomerNotificationPresenter(NotificationActivity.this,NotificationActivity.this);
        User user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
        idholder=user.getId();
        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(this);
        simpleSwitch =findViewById(R.id.simpleSwitch);
        simpleSwitch.setOnCheckedChangeListener(this);
        categoryrecycle = findViewById(R.id.categoryrecycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        categoryrecycle.setLayoutManager(linearLayoutManager);
        categoryrecycle.setItemAnimator(new DefaultItemAnimator());
        if (isNetworkConnected()){
            presenter.sentRequest(idholder);
        }
    }

    @Override
    public void onClick(View v) {
        if (v==imageViewback){
            onBackPressed();
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            Intent intent = new Intent(NotificationActivity.this, DoNotDistrubActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void success(ArrayList<NotificationModel> response) {
        customerNotificationAdapter = new CustomerNotificationAdapter(response,NotificationActivity.this );
        categoryrecycle.setAdapter(customerNotificationAdapter);
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
}


//    private void showdilog() {
//        final Dialog dialog = new Dialog(NotificationActivity.this);
//        dialog.setContentView(R.layout.do_not_distrub_activity);
//        dialog.setTitle("Custom Dialog");
//        TextView text1 = (TextView) dialog.findViewById(R.id.donotdistrub_text_id);
//        LinearLayout checkbox=(LinearLayout) dialog.findViewById(R.id.checkbox_id);
//        Button submitbutton=(Button)dialog.findViewById(R.id.submit_button_id);
//        dialog.show();
//        submitbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showdilogsecond();
//            }
//        });
//    }
//    private void showdilogsecond() {
//
//        final Dialog dialog = new Dialog(NotificationActivity.this);
//        dialog.setContentView(R.layout.disturb_dialog_activity);
//        dialog.setTitle("Custom Dialog");
//        TextView text1 = (TextView) dialog.findViewById(R.id.distrub_text_id);
//        LinearLayout checkbox=(LinearLayout) dialog.findViewById(R.id.checkbox_id);
//        Button submitbutton=(Button)dialog.findViewById(R.id.submit_button_id);
//        dialog.show();
//
//    }




