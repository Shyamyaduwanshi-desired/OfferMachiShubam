package com.desired.offermachi.retalier.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.ui.RetalierDashboard;
import com.desired.offermachi.retalier.ui.RetalierOtpActivity;
import com.desired.offermachi.retalier.ui.RetalierRegistration;

public class RegistrationProfileDetailsFragment extends Fragment {
    View view;
    String name,mobile,email;
    EditText nameedt,mobileedt,emailedt;

    public RegistrationProfileDetailsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.retalier_personal_detalis_activity, container, false);


         nameedt=(EditText)view.findViewById(R.id.name_edt_id);
         mobileedt=(EditText)view.findViewById(R.id.mobile_edt_id);
         emailedt=(EditText)view.findViewById(R.id.email_edt_id);
         Button nextbutton=(Button)view.findViewById(R.id.next_button_id);
         nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registraionvalid();
            }
        });
        return  view;
    }
    private void Registraionvalid() {

        name = nameedt.getText().toString();
        if (TextUtils.isEmpty(name)){
            Toast.makeText(getContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }
        mobile = mobileedt.getText().toString().trim();
        if (TextUtils.isEmpty(mobile) || !TextUtils.isDigitsOnly(mobile) || mobile.length() < 10 || mobile.length() > 10) {
            Toast.makeText(getContext(), "Please enter your mobile", Toast.LENGTH_SHORT).show();
            return;
        }
        email = emailedt.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            RetalierRegistration.tabLayout.setScrollPosition(1, 0f, true);
            RetalierRegistration.viewPager1.setCurrentItem(1);
            Toast.makeText(getContext(), "Sucess", Toast.LENGTH_SHORT).show();
            RegistrationStoreDetailsFrgment fragment = new RegistrationStoreDetailsFrgment();

            SharedPreferences sharedPreferences = getContext().getSharedPreferences("prefrence", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username",name);
            editor.putString("email",email);
            editor.putString("mobile",mobile);
            editor.apply();
        }
    }
}



