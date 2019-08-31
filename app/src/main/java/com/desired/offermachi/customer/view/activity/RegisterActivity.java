package com.desired.offermachi.customer.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.CustomerSignupPresenter;
import com.desired.offermachi.retalier.presenter.LoginPresenter;
import com.desired.offermachi.retalier.view.activity.RetalierLogin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, CustomerSignupPresenter.CustomerSignUp {

    TextView loginrtext;
    Button registerbutton;
    EditText etmobile;
    EditText etpassword;
    String check_agree="0";
    CheckBox termscheck;
    private CustomerSignupPresenter presenter;
    String android_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registraion_activity);
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
       /* android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);*/
        presenter = new CustomerSignupPresenter(RegisterActivity.this, RegisterActivity.this);
        TextView topdealsoftheday=(TextView)findViewById(R.id.register_text_id);
        Typeface register= ResourcesCompat.getFont(getApplicationContext(), R.font.ralewaybold);
        topdealsoftheday.setTypeface(register);
        loginrtext=findViewById(R.id.login_text_id);
        loginrtext.setOnClickListener(this);
        registerbutton=findViewById(R.id.register_button_id);
        registerbutton.setOnClickListener(this);
        etmobile=findViewById(R.id.etmobile);
        etpassword=findViewById(R.id.etpassword);
        termscheck = findViewById(R.id.terms_check);
        termscheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    check_agree = "1";
                }
                else{
                    check_agree = "0";
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v==loginrtext){
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }else if (v==registerbutton){
            validation();
        }
    }
    private void validation() {
        SharedPreferences sharedPreferences=getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        android_id=sharedPreferences.getString(getString(R.string.FCM_TOKEN),"");
        Log.e("register", "android_id==="+android_id );
        String mobile = etmobile.getText().toString().trim();
        String password = etpassword.getText().toString().trim();
        if (mobile.isEmpty()) {
            etmobile.requestFocus();
            etmobile.setError("Please enter mobile number");
        }else if (!isValidMobile(mobile)){
            etmobile.requestFocus();
            etmobile.setError("Please enter valid mobile number");
        }
        else if (password.isEmpty()){
            etpassword.requestFocus();
            etpassword.setError("Please enter password");
        }else if (password.length() < 8) {
            etpassword.requestFocus();
            etpassword.setError("Please enter minimum 8 digit password");
        }
       /* else if (!isValidPassword(password)) {
            etpassword.requestFocus();
            etpassword.setError("Please enter password contain character, number and special character");
        }*/
        else if (check_agree.equals("0")){
            Toast.makeText(getApplicationContext(), "please agree terms and conditions!", Toast.LENGTH_SHORT).show();
        }else {
            if(isNetworkConnected()){
                presenter.sentRequest(mobile,password,check_agree,android_id);
            }else {
                showAlert("Please connect to internet", R.style.DialogAnimation);
            }
        }
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
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
   /* private boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[*@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }*/
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
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
}
