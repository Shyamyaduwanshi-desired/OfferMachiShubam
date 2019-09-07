package com.desired.offermachi.retalier.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.presenter.CustomerForgotPasswordPresenter;
import com.desired.offermachi.customer.view.activity.CustomerResetPasswordActivity;
import com.desired.offermachi.customer.view.activity.ForgotPasswordActivity;
import com.desired.offermachi.customer.view.activity.RegistraionAsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class RetalierForgotPassword extends AppCompatActivity implements View.OnClickListener, CustomerForgotPasswordPresenter.CustomerForgotPassword {

    EditText forgotmobile;
    Button btnsubmit;
    private CustomerForgotPasswordPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_forgot_password);
        init();
    }
    private void init(){
        presenter = new CustomerForgotPasswordPresenter(RetalierForgotPassword.this, RetalierForgotPassword.this);
        TextView topdealsoftheday=(TextView)findViewById(R.id.forgot_password_text_id);
        Typeface forgotpassword= ResourcesCompat.getFont(getApplicationContext(), R.font.ralewaybold);
        topdealsoftheday.setTypeface(forgotpassword);
        forgotmobile=findViewById(R.id.mobile_edit_id);
        btnsubmit=findViewById(R.id.btnsubmit);
        btnsubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v==btnsubmit){
            validation();

        }
    }
    private void validation() {
        String mobile = forgotmobile.getText().toString().trim();
        if (mobile.isEmpty()) {
            forgotmobile.requestFocus();
            forgotmobile.setError("Please enter mobile number");
        }else if (!isValidMobile(mobile)){
            forgotmobile.requestFocus();
            forgotmobile.setError("Please enter valid mobile number");
        }else {
            if(isNetworkConnected()){
                presenter.sentRequest(mobile);
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
        startActivity(new Intent(RetalierForgotPassword.this, RetalierLogin.class));
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
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            String userid = jsonObject.getString("id");
            Intent intent = new Intent(getApplicationContext(),ResetPasswordActivity.class);
            intent.putExtra("userid", userid);
            startActivity(intent);
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
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
}



