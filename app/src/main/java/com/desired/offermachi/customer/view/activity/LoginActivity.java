package com.desired.offermachi.customer.view.activity;

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
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.CustomerLoginPresenter;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.presenter.LoginPresenter;
import com.desired.offermachi.retalier.view.activity.RetalierDashboard;
import com.desired.offermachi.retalier.view.activity.RetalierLogin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, CustomerLoginPresenter.CustomerLogin {

    TextView registertext;
    Button loginnbutton;
    EditText etmobile;
    EditText etpassword;
    private CustomerLoginPresenter presenter;
    String android_id;
    TextView forgotpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        if (UserSharedPrefManager.getInstance(this).isLoggedIn()) {
            User user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
           if (user.getSmartShopping().equals("0")){
               finish();
               startActivity(new Intent(this, CategoryActivity.class));
           }else{
               finish();
               startActivity(new Intent(this, DashBoardActivity.class));
           }

            return;
        }
        initview();
    }
    private void initview(){
        presenter = new CustomerLoginPresenter(LoginActivity.this, LoginActivity.this);
        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        TextView topdealsoftheday=(TextView)findViewById(R.id.login_text_id);
        Typeface otp= ResourcesCompat.getFont(getApplicationContext(), R.font.ralewaybold);
        topdealsoftheday.setTypeface(otp);
        registertext=findViewById(R.id.register_text_id);
        registertext.setOnClickListener(this);
        loginnbutton=findViewById(R.id.login_button_id);
        loginnbutton.setOnClickListener(this);
        forgotpassword=findViewById(R.id.forgotpassword);
        forgotpassword.setOnClickListener(this);
        etmobile=findViewById(R.id.etmobile);
        etpassword=findViewById(R.id.etpassword);
    }
    @Override
    public void onClick(View v) {
        if (v==registertext){
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }else if (v==loginnbutton){
            loginvalid();
        }else if (v==forgotpassword){
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }

    }
    private void loginvalid() {
        String mobile = etmobile.getText().toString().trim();
        String password = etpassword.getText().toString().trim();
        if (mobile.isEmpty()) {
            etmobile.requestFocus();
            etmobile.setError("Please enter mobile number");
        }else if (!isValidMobile(mobile)){
            etmobile.requestFocus();
            etmobile.setError("Please enter valid mobile number");
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
        startActivity(new Intent(LoginActivity.this, RegistraionAsActivity.class));
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

