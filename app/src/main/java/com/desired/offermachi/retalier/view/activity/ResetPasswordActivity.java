package com.desired.offermachi.retalier.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.presenter.CustomerForgotPasswordPresenter;
import com.desired.offermachi.customer.view.activity.CustomerResetPasswordActivity;
import com.desired.offermachi.customer.view.activity.LoginActivity;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener, CustomerForgotPasswordPresenter.CustomerForgotPassword {
    Button btndone;
    EditText etnewpasssword,etconfirmpassword;
    String idholder;
    private CustomerForgotPasswordPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        TextView topdealsoftheday1=(TextView)findViewById(R.id.reset_text_id);
        Typeface resetpassword= ResourcesCompat.getFont(getApplicationContext(), R.font.ralewaybold);
        topdealsoftheday1.setTypeface(resetpassword);
        init();
    }
    private void init(){
        presenter=new CustomerForgotPasswordPresenter(ResetPasswordActivity.this,ResetPasswordActivity.this);
        Intent intent=getIntent();
        idholder=intent.getStringExtra("userid");
        etnewpasssword=findViewById(R.id.newpassword);
        etconfirmpassword=findViewById(R.id.confirmpassword);
        btndone=findViewById(R.id.btndone);
        btndone.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v==btndone){
            validation();
        }
    }
    private void validation() {
        String password = etnewpasssword.getText().toString().trim();
        String confirmpassword = etconfirmpassword.getText().toString().trim();
        if (password.isEmpty()){
            etnewpasssword.requestFocus();
            etnewpasssword.setError("Please enter password");
        }else if (password.length() < 8) {
            etnewpasssword.requestFocus();
            etnewpasssword.setError("Please enter minimum 8 digit password");
        }
        else if (confirmpassword.isEmpty()){
            etconfirmpassword.requestFocus();
            etconfirmpassword.setError("Please enter Confirm password");
        }else if (confirmpassword.length() < 8) {
            etconfirmpassword.requestFocus();
            etconfirmpassword.setError("Please enter minimum 8 digit password");
        }else if (!(password.equals(confirmpassword))){
            Toast.makeText(this, "Do Not Match Password And Confirm Password", Toast.LENGTH_SHORT).show();
        } else {
            if(isNetworkConnected()){
                presenter.ResetPassword(idholder,password);
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
        Toast.makeText(this, ""+response, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), RetalierLogin.class));
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

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
        startActivity(new Intent(ResetPasswordActivity.this, RetalierLogin.class));
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
