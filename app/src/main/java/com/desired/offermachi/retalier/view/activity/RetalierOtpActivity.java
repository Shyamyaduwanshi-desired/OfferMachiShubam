package com.desired.offermachi.retalier.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.presenter.OtpPresenter;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class RetalierOtpActivity extends AppCompatActivity implements View.OnClickListener , OtpPresenter.Otp  {

    Button otpsubmitbutton;
    String Idholder,Otp;
    EditText etotp;
    TextView txtresendotp;
    private OtpPresenter presenter;
    String OtpHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_otp_activity);
        presenter = new OtpPresenter(RetalierOtpActivity.this, RetalierOtpActivity.this);
        init();
    }
    private void init(){
        Intent intent=getIntent();
        Idholder= intent.getStringExtra("userid");
        OtpHolder=intent.getStringExtra("otp");
        etotp=findViewById(R.id.etotp);
       // etotp.setText(Otp);
        txtresendotp=findViewById(R.id.resendotp);
        txtresendotp.setOnClickListener(this);
        TextView topdealsoftheday=(TextView)findViewById(R.id.otp_text_id);
        Typeface otp= ResourcesCompat.getFont(getApplicationContext(), R.font.ralewaybold);
        topdealsoftheday.setTypeface(otp);
        otpsubmitbutton=(Button)findViewById(R.id.submit_button_id);
        otpsubmitbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==otpsubmitbutton){
            Otp = etotp.getText().toString();
            if(TextUtils.isEmpty(Otp)){
                etotp.requestFocus();
                etotp.setError("Please enter otp");
            }else if (!(OtpHolder.equals(Otp))){

                Toast.makeText(this, "Enter Wrong Otp Please Try Again. ", Toast.LENGTH_SHORT).show();
            }else {
                if(isNetworkConnected()){
                    presenter.verifyOtp(Idholder);
                }else {
                    showAlert("Please connect to internet", R.style.DialogAnimation);
                }
            }

        }else if (v==txtresendotp){
            if(isNetworkConnected()){
                presenter.resentOtp(Idholder);
            }else {

                showAlert("Please connect to internet", R.style.DialogAnimation);
            }
        }
    }

    @Override
    public void success(String response) {
        Toast.makeText(this, ""+response, Toast.LENGTH_SHORT).show();
      startActivity(new Intent(getApplicationContext(), RetalierVerifyActivity.class));
      finish();

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
    public void successresend(String response) {
        OtpHolder=response;
    }

    @Override
    public void errorresend(String response) {
        showAlert(response, R.style.DialogAnimation);
    }

    @Override
    public void failresend(String response) {
        showAlert(response, R.style.DialogAnimation);
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
        startActivity(new Intent(RetalierOtpActivity.this, RetalierRegistration.class));
        finish();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    //for auto fatch OTP
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter("android.provider.Telephony" +
                ".SMS_RECEIVED"));
    }

    BroadcastReceiver receiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            {
                final Bundle bundle = intent.getExtras();
                try {
                    if (bundle != null) {
                        final Object[] pdusObj = (Object[]) bundle.get("pdus");
                        for (int i = 0; i < pdusObj.length; i++) {
                            SmsMessage currentMessage = SmsMessage.createFromPdu((byte[])
                                    pdusObj[i]);
                            String msg=currentMessage.getDisplayMessageBody();
                            String arr[] = msg.split(" OTP ");
                            String getotp = arr[1];
//                            String getotp=msg.substring((msg.length()-4),msg.length());

                            Log.e("","getotp= "+getotp);
                            etotp.setText(getotp.trim());
                            if (etotp.getText().toString().equals(OtpHolder)) {
                                if(isNetworkConnected()){
                                    presenter.verifyOtp(Idholder);
                                }else {
                                    showAlert("Please connect to internet", R.style.DialogAnimation);
                                }

                            }

                        }
                    }

                } catch (Exception e) {

                }
            }
        }
    };
}