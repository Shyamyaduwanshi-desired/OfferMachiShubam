package com.desired.offermachi.customer.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.desired.offermachi.R;
import com.desired.offermachi.customer.view.activity.DashBoardActivity;
import com.desired.offermachi.retalier.presenter.CustomerSupportPresenter;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class CustomerSupportFragment extends Fragment implements View.OnClickListener,CustomerSupportPresenter.CustomerSupport {
    View view;
    Button submitbutton;
    EditText etname,etemail,etmessage,etmobile;
    private CustomerSupportPresenter presenter;

    public CustomerSupportFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.customer_support_activity, container, false);
       // init();
         return  view;
    }
    private void init(){
        presenter = new CustomerSupportPresenter(getContext(), CustomerSupportFragment.this);
        etname=view.findViewById(R.id.etname);
        etemail=view.findViewById(R.id.etemail);
        etmobile=view.findViewById(R.id.etmobile);
        etmessage=view.findViewById(R.id.etmessage);
        submitbutton=view.findViewById(R.id.submit_customer_button_id);
        submitbutton.setOnClickListener(this);
    }
    private void validation() {
        String name = etname.getText().toString();
        String email = etemail.getText().toString().trim();
        String mobile = etmobile.getText().toString().trim();
        String message = etmessage.getText().toString();
        if (name.isEmpty()) {
            etname.requestFocus();
            etname.setError("Please enter name");
        }else if (email.isEmpty()){
            etemail.requestFocus();
            etemail.setError("Please enter email");
        }
        else if (message.isEmpty()){
            etemail.requestFocus();
            etemail.setError("Please enter message");
        }
        else if (mobile.isEmpty()) {
            etmobile.requestFocus();
            etmobile.setError("Please enter mobile number");
        }
        else if (!isValidMobile(mobile)){
            etmobile.requestFocus();
            etmobile.setError("Please enter valid mobile number");
        }
        else {
            if(isNetworkConnected()){
                presenter.SendCustomerSupport(name,email,mobile,message);
            }else {
                showAlert("Please connect to internet", R.style.DialogAnimation);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v==submitbutton){
            validation();
        }

    }

    @Override
    public void success(String response) {
        showAlert(response, R.style.DialogAnimation);
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
                startActivity(new Intent(getContext(), DashBoardActivity.class));
            }
        }).show();
    }
    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
