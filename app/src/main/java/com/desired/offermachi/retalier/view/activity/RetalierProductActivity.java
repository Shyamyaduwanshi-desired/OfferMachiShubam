package com.desired.offermachi.retalier.view.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.NotificationCountPresenter;
import com.desired.offermachi.customer.view.activity.InfoActivity;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.OfferLocation;
import com.desired.offermachi.retalier.model.RetailerLocation;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.model.ViewOfferModel;
import com.desired.offermachi.retalier.presenter.DeleteOfferDetailPresenter;
import com.desired.offermachi.retalier.presenter.SignupPresenter;
import com.desired.offermachi.retalier.presenter.ViewOfferDetailPresenter;
import com.desired.offermachi.retalier.presenter.ViewOfferPresenter;
import com.desired.offermachi.retalier.view.fragment.RegistrationStoreDetailsFrgment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class RetalierProductActivity extends AppCompatActivity implements View.OnClickListener, ViewOfferDetailPresenter.OfferDiscount, NotificationCountPresenter.NotiUnReadCount {

    ImageView imageViewback,info;
    Button couponbutton;
    String offerid;
    private ViewOfferDetailPresenter presenter;
    ImageView viewPager_product;
    TextView txtoffername,txtoffertypename,txtofferdescription,txtenddate,txttime,txtbrandname;
    ImageView qr_code_img_id;
    Button coupon_button_apply_id;
    String couponcode,qrcodeimage;
    TextView btnok;
    TextView tvNotiCount;
    private NotificationCountPresenter notiCount;
    private String idholder;
    private Button btnDelete;
    private ArrayList<OfferLocation> alOfferLocation;
    private String offerLocations;
    private Button btnEdit;
    private String data="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_product_activity);
        presenter=new ViewOfferDetailPresenter(this, RetalierProductActivity.this);
        init();
    }
    private  void init(){
        Intent intent=getIntent();
        offerid=intent.getStringExtra("offerid");
        imageViewback=findViewById(R.id.imageviewback);
        info=findViewById(R.id.info_id);
        viewPager_product=findViewById(R.id.viewPager_product);
        txtoffername=findViewById(R.id.offername);
        txtoffertypename=findViewById(R.id.offertypename);
        txtofferdescription=findViewById(R.id.offerdescription);
        txtenddate=findViewById(R.id.enddate);
        txttime=findViewById(R.id.time);
        txtbrandname=findViewById(R.id.brandname);
        couponbutton =(Button)findViewById(R.id.coupon_button_id);
        imageViewback.setOnClickListener(this);
        info.setOnClickListener(this);
        couponbutton.setOnClickListener(this);
        UserModel user = SharedPrefManagerLogin.getInstance(this).getUser();
        idholder= user.getId();
        notiCount = new NotificationCountPresenter(this,this);
        tvNotiCount = findViewById(R.id.txtMessageCount);
        notiCount.NotificationUnreadCount(idholder);
        btnDelete = findViewById(R.id.coupon_delete_button_id);
        btnEdit = findViewById(R.id.coupon_edit_button);
        btnDelete.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        if (isNetworkConnected()) {
            presenter.getOfferDiscount(offerid);
        }  else {
            showAlert("Please connect to internet.", R.style.DialogAnimation);
        }
    }


    @Override
    public void onClick(View v) {
        if (v==imageViewback){
            onBackPressed();
        }else if (v==couponbutton){
            showdialog(qrcodeimage,couponcode);
        }else if(v==info){
            Intent intent = new Intent(RetalierProductActivity.this, InfoActivity.class);
            startActivity(intent);
        }else if(v==btnDelete){
            showAlertTwoButton("Are you sure?",R.style.DialogAnimation);
        }else if(v==btnEdit){
            startActivity(new Intent(RetalierProductActivity.this,EditOfferActivity.class).putExtra("data",data));
            finish();
        }
    }
    private void showdialog(String image,String code) {
        final Dialog dialog = new Dialog(RetalierProductActivity.this);
        dialog.setContentView(R.layout.coupon_code_activity);
        dialog.setTitle("Custom Dialog");
        qr_code_img_id=dialog.findViewById(R.id.qr_code_img_id);
        coupon_button_apply_id=dialog.findViewById(R.id.coupon_button_apply_id);
        coupon_button_apply_id.setText(code);
        if(image.equals("")){
        }else{
            Picasso.get().load(image).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(qr_code_img_id);
        }

         btnok=(TextView) dialog.findViewById(R.id.coupon_ok_id);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void success(String response) {
        data = response;
        Log.e("details", "response="+response );
        //Toast.makeText(this, ""+response, Toast.LENGTH_SHORT).show();
        try {
            offerLocations = "";
            JSONObject object = new JSONObject(response);
            alOfferLocation = new Gson().fromJson(object.optString("offer_locations"),  new TypeToken<ArrayList<OfferLocation>>(){}.getType());
            for (OfferLocation loc:alOfferLocation){
                if(TextUtils.isEmpty(offerLocations)){
                    offerLocations = loc.getLocalityName();
                }else {
                    offerLocations =offerLocations +", "+ loc.getLocalityName();
                }
            }
            if(object.optString("can_delete").equals("1")){
                btnDelete.setVisibility(View.VISIBLE);
            }else {
                btnDelete.setVisibility(View.GONE);
            }
            ((TextView)findViewById(R.id.storeLocations)).setText(offerLocations);
                    String id=object.getString("id");
                    String offerid=object.getString("offer_id");
                    String offertitle=object.getString("offer_title");
                    txtoffername.setText(offertitle);
                    String brandid=object.getString("offer_brand");
                    String brandname=object.getString("offer_brand_name");
                    txtbrandname.setText(brandname);
                    String offercategory= object.getString("offer_category");
                    String subcategory=object.getString("sub_category");
                    String offertype=object.getString("offer_type");
                    String offervalue=object.getString("offer_value");
                    String offertypename=object.getString("offer_type_name");
                    txtoffertypename.setText(offertypename+" Off "+offervalue);
                    String offerdetail=object.getString("offer_details");
                    String startdate=object.getString("start_date");
                    String enddate=object.getString("end_date");
                    txtenddate.setText(enddate);
                    String time=object.getString("alltime");
                    txttime.setText(time);
                    String description= object.getString("description");
                    txtofferdescription.setText(description);
                    couponcode=object.getString("coupon_code");
                    String postby=object.getString("posted_by");
                    String status=object.getString("status");
                    String offerimage=object.getString("offer_image");
            if(offerimage.equals("")){
            }else{
                Picasso.get().load(offerimage).networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(viewPager_product);
            }
                 qrcodeimage=object.getString("qr_code_image");
        }catch (JSONException e) {
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
    private void showAlertTwoButton(String message, int animationSource){
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
        prettyDialog.addButton("Yes", R.color.black, R.color.white, new PrettyDialogCallback() {
            @Override
            public void onClick() {
                new DeleteOfferDetailPresenter(RetalierProductActivity.this, new DeleteOfferDetailPresenter.DeleteOffer() {
                    @Override
                    public void success(String response) {
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
                }).deleteOffer(idholder,offerid);
                prettyDialog.dismiss();
            }
        });
        prettyDialog.addButton("No", R.color.black, R.color.white, new PrettyDialogCallback() {
            @Override
            public void onClick() {
                prettyDialog.dismiss();
            }
        });
        prettyDialog.show();
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


