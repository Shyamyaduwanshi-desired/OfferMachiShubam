package com.desired.offermachi.retalier.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.view.activity.RegistraionAsActivity;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.presenter.LoginPresenter;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


public class RetalierLogin extends AppCompatActivity implements View.OnClickListener, LoginPresenter.Login{

    TextView registertext;
    Button loginnbutton;
    EditText etmobilenumber;
    EditText etpassword;
    private LoginPresenter presenter;
    String android_id;
    TextView txtforgotpasssword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_login);
        if (SharedPrefManagerLogin.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, RetalierDashboard.class));
            return;
        }
        presenter = new LoginPresenter(RetalierLogin.this, RetalierLogin.this);
        initView();
    }
    private void initView() {
        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        etmobilenumber=findViewById(R.id.mobilenumber);
        etpassword=findViewById(R.id.etpassword);
        registertext=(TextView)findViewById(R.id.register_text_id);
        loginnbutton=(Button)findViewById(R.id.login_button_id);
        loginnbutton.setOnClickListener(this);
        registertext.setOnClickListener(this);
        txtforgotpasssword=findViewById(R.id.forgot_password_id);
        txtforgotpasssword.setOnClickListener(this);
        TextView topdealsoftheday=(TextView)findViewById(R.id.login_text_id);
        Typeface otp= ResourcesCompat.getFont(getApplicationContext(), R.font.ralewaybold);
        topdealsoftheday.setTypeface(otp);
    }
    @Override
    public void onClick(View v) {
        if (v == loginnbutton) {
            loginvalid();
        }else if (v== registertext){
            Intent intent = new Intent(RetalierLogin.this, RetalierRegistration.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }else if (v==txtforgotpasssword){
            Intent intent = new Intent(RetalierLogin.this, RetalierForgotPassword.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
    }
    private void loginvalid() {
        String mobile = etmobilenumber.getText().toString().trim();
        String password = etpassword.getText().toString().trim();
        if (mobile.isEmpty()) {
            etmobilenumber.requestFocus();
            etmobilenumber.setError("Please enter mobile number");
        }else if (!isValidMobile(mobile)){
            etmobilenumber.requestFocus();
            etmobilenumber.setError("Please enter valid mobile number");
        }else if (password.isEmpty()){
            etpassword.requestFocus();
            etpassword.setError("Please enter password");
        }else if (password.length() < 8) {
            etpassword.requestFocus();
            etpassword.setError("Please enter minimum 8 digit password");
        }else {
            if(isNetworkConnected()){
                presenter.sentRequest(mobile,password,android_id);
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
    public void success(String response) {

    }

    @Override
    public void error(String response) {
        showAlert(response, R.style.DialogAnimation);
    }

    @Override
    public void fail(String response) {
        showAlert(response, R.style.DialogAnimation);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RetalierLogin.this, RegistraionAsActivity.class));
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
}


