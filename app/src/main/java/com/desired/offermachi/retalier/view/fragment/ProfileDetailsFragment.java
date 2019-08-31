package com.desired.offermachi.retalier.view.fragment;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.desired.offermachi.R;
import com.desired.offermachi.customer.view.activity.ChangePasswordActivity;
import com.desired.offermachi.customer.view.activity.EditProfileActivity;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.presenter.ProfilePresenter;
import com.desired.offermachi.retalier.presenter.SignupPresenter;
import com.desired.offermachi.retalier.view.activity.RetailerChangePasswordActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

import static android.app.Activity.RESULT_OK;

public class ProfileDetailsFragment extends Fragment implements View.OnClickListener,ProfilePresenter.Profile {
    View view;
String idholder,nameholder,emailholder,phoneholder,genderholder;
LinearLayout btnedit;
EditText etname,etemail,etmobile,etgender;
    int editState = 0;
    TextView txtedit;
    private ProfilePresenter presenter;
    TextView changepasswordtext;
    public ProfileDetailsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.retalier_personal_profile_activity, container, false);
        presenter = new ProfilePresenter(getContext(), ProfileDetailsFragment.this);
        init();
        return  view;
    }

    private void init(){
        UserModel user = SharedPrefManagerLogin.getInstance(getContext()).getUser();
        idholder= user.getId();
        nameholder=user.getUsername();
        emailholder=user.getEmail();
        phoneholder=user.getMobile();
        btnedit=view.findViewById(R.id.btnedit);
        etname=view.findViewById(R.id.editname);
        etemail=view.findViewById(R.id.editemail);
        etmobile=view.findViewById(R.id.editmobile);
        etgender=view.findViewById(R.id.editgender);
        txtedit=view.findViewById(R.id.edit);
        etname.setText(nameholder);
        etemail.setText(emailholder);
        etmobile.setText(phoneholder);
        btnedit.setOnClickListener(this);
        changepasswordtext=view.findViewById(R.id.changepassword_id);
        changepasswordtext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==btnedit){
            if (editState == 0){
                txtedit.setText("Save");
                editProfile();
                editState = 1;
            }else{
                txtedit.setText("Edit");
                updateprofile();
                editState = 0;
            }
        }else if (v==changepasswordtext){
            Intent intent = new Intent(getContext(), RetailerChangePasswordActivity.class);
            startActivity(intent);
        }

    }
    private void editProfile(){
        etname.setEnabled(true);
        etmobile.setEnabled(true);
        etemail.setEnabled(true);
        etgender.setEnabled(true);
    }
    private void updateprofile(){
        etname.setEnabled(false);
        etmobile.setEnabled(false);
        etemail.setEnabled(false);
        etgender.setEnabled(false);
        nameholder = etname.getText().toString();
        phoneholder = etmobile.getText().toString().trim();
        emailholder = etemail.getText().toString();
        genderholder=etgender.getText().toString();
        if (TextUtils.isEmpty(nameholder)){
            Toast.makeText(getContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(phoneholder)){
            Toast.makeText(getContext(), "Please enter your mobile", Toast.LENGTH_SHORT).show();
        }
        else if (!isValidMobile(phoneholder)){
            Toast.makeText(getContext(), "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
        }
        else if (phoneholder.length() < 10){
            Toast.makeText(getContext(), "Please enter 10 digit mobile number", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(emailholder)) {
            Toast.makeText(getContext(), "Please enter email id", Toast.LENGTH_SHORT).show();
        }
        else if (!isValidMail(emailholder)) {
            Toast.makeText(getContext(), "Please enter valid email id", Toast.LENGTH_SHORT).show();
        }
        else {
            if (isNetworkConnected()) {
                presenter.updateProfile(idholder,nameholder,emailholder,phoneholder,genderholder);
            }
        }
    }
    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void success(String response) {
        Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
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
        final PrettyDialog prettyDialog = new PrettyDialog(getContext());
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

