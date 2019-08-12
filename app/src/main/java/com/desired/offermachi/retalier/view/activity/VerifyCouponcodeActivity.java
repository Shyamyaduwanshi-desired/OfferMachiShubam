package com.desired.offermachi.retalier.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.ui.RegistraionAsActivity;
import com.desired.offermachi.retalier.presenter.LoginPresenter;
import com.desired.offermachi.retalier.presenter.VerifyCouponcodePresenter;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class VerifyCouponcodeActivity extends AppCompatActivity implements View.OnClickListener, VerifyCouponcodePresenter.VerifyCoupon {
EditText etcouponcode,etmobilenumber;
Button btnverify;
    private VerifyCouponcodePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_couponcode);
        initview();
    }
    private void initview(){
        presenter = new VerifyCouponcodePresenter(VerifyCouponcodeActivity.this, VerifyCouponcodeActivity.this);
        etcouponcode=findViewById(R.id.couponcode);
        etmobilenumber=findViewById(R.id.mobilenumber);
        btnverify=findViewById(R.id.btnverify);
        btnverify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==btnverify){
            validation();
        }
    }
    private void validation() {
        String mobile=etmobilenumber.getText().toString().trim();
        String couponcode=etcouponcode.getText().toString();
        if (mobile.isEmpty()) {
            etmobilenumber.requestFocus();
            etmobilenumber.setError("Please enter mobile number");
        }else if (!isValidMobile(mobile)){
            etmobilenumber.requestFocus();
            etmobilenumber.setError("Please enter valid mobile number");
        }
        else if (couponcode.isEmpty()){
            etcouponcode.requestFocus();
            etcouponcode.setError("Enter valid Coupon Code");
        }
        else {
            if(isNetworkConnected()){
                presenter.sentRequest(mobile,couponcode);
            }else {
                showAlert("Please connect to internet", R.style.DialogAnimation);
            }
        }
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(VerifyCouponcodeActivity.this, RetalierDashboard.class));
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void success(String response) {
        Toast.makeText(this, ""+response, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void error(String response) {
        showAlert(response, R.style.DialogAnimation);
    }

    @Override
    public void fail(String response) {
        showAlert(response, R.style.DialogAnimation);
    }
}
