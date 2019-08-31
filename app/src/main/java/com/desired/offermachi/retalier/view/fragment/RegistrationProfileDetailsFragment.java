package com.desired.offermachi.retalier.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.view.activity.RetalierRegistration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationProfileDetailsFragment extends Fragment implements View.OnClickListener {
    View view;
    String name,mobile,email,password;
    EditText nameedt,mobileedt,emailedt,etpassword;
    Button nextbutton;
    public RegistrationProfileDetailsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.retalier_personal_detalis_activity, container, false);
        initView();
        return  view;
    }
    private void initView() {
        nameedt=(EditText)view.findViewById(R.id.name_edt_id);
        mobileedt=(EditText)view.findViewById(R.id.mobile_edt_id);
        emailedt=(EditText)view.findViewById(R.id.email_edt_id);
        etpassword=(EditText)view.findViewById(R.id.etpassword);
        nextbutton=(Button)view.findViewById(R.id.next_button_id);
        nextbutton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == nextbutton) {
            Registraionvalid();
        }
    }
    private void Registraionvalid() {
        name = nameedt.getText().toString();
        mobile = mobileedt.getText().toString().trim();
        email = emailedt.getText().toString();
        password = etpassword.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            nameedt.requestFocus();
            nameedt.setError("Please enter your name");
            return;
        }else if (TextUtils.isEmpty(mobile)){
            mobileedt.requestFocus();
            mobileedt.setError("Please enter your mobile");
        }
        else if (!isValidMobile(mobile)){
            mobileedt.requestFocus();
            mobileedt.setError("Please enter valid mobile number");
        }
        else if (mobile.length() < 10){
            mobileedt.requestFocus();
            mobileedt.setError("Please enter 10 digit mobile number");
        }
        else if (TextUtils.isEmpty(email)) {
            emailedt.requestFocus();
            emailedt.setError("Please enter email id");
        }
        else if (!isValidMail(email)) {
            emailedt.requestFocus();
            emailedt.setError("Please enter valid email id");
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
        else {
            RetalierRegistration.tabLayout.setScrollPosition(1, 0f, true);
            RetalierRegistration.viewPager1.setCurrentItem(1);
            Intent intent = new Intent("Data");
            intent.putExtra("name",name);
            intent.putExtra("email",email);
            intent.putExtra("mobile",mobile);
            intent.putExtra("password",password);
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
        }
    }
    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
   /* private boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[*@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }*/
}



