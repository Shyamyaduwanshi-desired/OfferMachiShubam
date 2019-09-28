package com.desired.offermachi.retalier.view.activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.desired.offermachi.R;
import com.desired.offermachi.customer.presenter.NotificationCountPresenter;
import com.desired.offermachi.customer.view.activity.InfoActivity;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.presenter.StaticsPresenter;
import org.json.JSONException;
import org.json.JSONObject;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class RetalierStatisticsActivity  extends AppCompatActivity implements View.OnClickListener, StaticsPresenter.Statics, NotificationCountPresenter.NotiUnReadCount {

    ImageView imageViewback,info;
    TextView gotthecoupontext,redeemthecoupontext,Offerdiscounttext;
    private StaticsPresenter presenter;
    String idholder;
    ImageView imgNotiBell;
    TextView tvNotiCount;
    private NotificationCountPresenter notiCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_statistics_activity);
        init();
    }
    private void init(){
        UserModel user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        idholder=user.getId();
        presenter=new StaticsPresenter(RetalierStatisticsActivity.this,RetalierStatisticsActivity.this);
        gotthecoupontext =(TextView)findViewById(R.id.gotthecoupon_text_id);
        gotthecoupontext.setOnClickListener(this);
        redeemthecoupontext =(TextView)findViewById(R.id.redeemthecoupon_text_id);
        redeemthecoupontext.setOnClickListener(this);
        Offerdiscounttext =(TextView)findViewById(R.id.Offerdiscount_text_id);
        Offerdiscounttext.setOnClickListener(this);
        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(this);
        info=findViewById(R.id.info_id);
        info.setOnClickListener(this);

        notiCount = new NotificationCountPresenter(this,this);
        tvNotiCount = findViewById(R.id.txtMessageCount);
        notiCount.NotificationUnreadCount(idholder);

        if (isNetworkConnected()){
            presenter.sentRequest(idholder);
        }else{
            showAlert("Please Connect Internet.", R.style.DialogAnimation);
        }
        imgNotiBell=findViewById(R.id.imgNotiBell);
        imgNotiBell.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==imageViewback){
            onBackPressed();
        }
        else if(v==info){
            Intent intent = new Intent(RetalierStatisticsActivity.this, InfoActivity.class);
            startActivity(intent);
        }
        else if (v==gotthecoupontext){
            Intent intent = new Intent(RetalierStatisticsActivity.this, RetalierListPeopleActivity.class);
            intent.putExtra("status","1");
            startActivity(intent);
            finish();
        }
        else if (v==redeemthecoupontext){
            Intent intent = new Intent(RetalierStatisticsActivity.this, RetalierListPeopleActivity.class);
            intent.putExtra("status","2");
            startActivity(intent);
            finish();
        }
        else if (v==Offerdiscounttext){
            Intent intent = new Intent(RetalierStatisticsActivity.this, ListOfPeople0fferDiscount.class);
            startActivity(intent);
            finish();
        }else if (v==imgNotiBell){
            startActivity(new Intent(getApplicationContext(), RetalierNotificationActivity.class));
        }

    }

    @Override
    public void success(String response) {
        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(response);
            String gotcoupon=jsonObject.getString("got_the_coupon");
            gotthecoupontext.setText(gotcoupon);
            String redemcoupon=jsonObject.getString("redeem_the_coupon");
            redeemthecoupontext.setText(redemcoupon);
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

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void successnoti(String response) {
        if(TextUtils.isEmpty(response))
        {
            tvNotiCount.setText("0");
        }
        else {

            tvNotiCount.setText(response);
        }
    }

    @Override
    public void errornoti(String response) {

    }

    @Override
    public void failnoti(String response) {

    }
}
