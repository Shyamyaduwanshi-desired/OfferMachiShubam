package com.desired.offermachi.customer.view.activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.CustomerOfferDetailPresenter;
import com.desired.offermachi.customer.presenter.NotificationCountPresenter;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.presenter.ViewOfferDetailPresenter;
import com.desired.offermachi.retalier.view.activity.RetalierProductActivity;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener, CustomerOfferDetailPresenter.OfferDetail, NotificationCountPresenter.NotiUnReadCount {
    ImageView imageViewback,info;
    Button couponbutton;
    LinearLayout shareiconimge, viewalloffer;
    ImageView likeimg;
    int count=0;
    private CustomerOfferDetailPresenter presenter;
    String offerid, idholder;
    ImageView viewPager_product;
    TextView txtoffername, txtoffertypename, txtofferdescription, txtenddate, txttime, txtbrandname;
    ImageView qr_code_img_id;
    String couponcode, qrcodeimage;
    Button coupon_button_apply_id;
    TextView btnok,tvNotiCount;
    TextView txtstorename,txtstoredescription,txtstorename2;
    ImageView storeimage;
    CircleImageView storelogothumb;
    String Storeid;
    String offercategory;
    String fav,Favstatus;
    TextView btnfollow;
    String followstatus;
    String couponstatus;
    ImageView imgNotiBell;
    ImageView imgshare;
    private NotificationCountPresenter notiCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity);
        initview();

    }

    private void initview() {
        presenter = new CustomerOfferDetailPresenter(ProductActivity.this, ProductActivity.this);
        Intent intent = getIntent();
        offerid = intent.getStringExtra("offerid");
        User user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
        idholder = user.getId();
        imageViewback = findViewById(R.id.imageviewback);
        imageViewback.setOnClickListener(this);
        info= findViewById(R.id.info_id);
        info.setOnClickListener(this);
        couponbutton = (Button) findViewById(R.id.coupon_button_id);
        couponbutton.setOnClickListener(this);
        imgNotiBell=findViewById(R.id.imgNotiBell);
        imgNotiBell.setOnClickListener(this);
        imgshare=findViewById(R.id.imgshare);
        imgshare.setOnClickListener(this);

        notiCount = new NotificationCountPresenter(this,this);
        tvNotiCount = findViewById(R.id.txtMessageCount);
        notiCount.NotificationUnreadCount(idholder);
       /* shareiconimge =findViewById(R.id.shareicon_image_id);
        shareiconimge.setOnClickListener(this);*/
        viewalloffer = findViewById(R.id.viewalloffer_id);
        viewalloffer.setOnClickListener(this);
        txtstorename = findViewById(R.id.storename2);
        txtstorename2 = findViewById(R.id.storename);
        txtstorename.setOnClickListener(this);
        storeimage = findViewById(R.id.storeimage);
        storelogothumb = findViewById(R.id.storelogothumb);
        txtstoredescription=findViewById(R.id.storedescription);
        likeimg = findViewById(R.id.heartfirst_image_id);
        likeimg.setOnClickListener(this);
        viewPager_product = findViewById(R.id.viewPager_product);
        txtoffername = findViewById(R.id.offername);
        txtoffertypename = findViewById(R.id.offertypename);
        txtofferdescription = findViewById(R.id.offerdescription);
        txtenddate = findViewById(R.id.enddate);
        txttime = findViewById(R.id.time);
        txtbrandname = findViewById(R.id.brandname);
        btnfollow=findViewById(R.id.btnfollow);
        btnfollow.setOnClickListener(this);
        if (isNetworkConnected()) {
            presenter.OfferDetail(idholder, offerid);
        } else {
            showAlert("Please connect to internet.", R.style.DialogAnimation);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == imageViewback) {
            onBackPressed();
        } else if(v==info){
            Intent intent = new Intent(ProductActivity.this, InfoActivity.class);
            startActivity(intent);
        }else if (v == couponbutton) {
            if (couponstatus.equals("0")){
                couponstatus="1";
                presenter.GetCoupons(idholder, offerid,couponstatus);
            }else if (couponstatus.equals("1")){
                showdialog(qrcodeimage, couponcode);
            }else if (couponstatus.equals("2")){

            }

        }/*else if (v==shareiconimge){

        }*/ else if (v == viewalloffer) {
            Intent intent = new Intent(ProductActivity.this, ViewStoreOfferActivity.class);
            intent.putExtra("storeid",Storeid);
            startActivity(intent);
        } else if (v == txtstorename) {
            Intent intent = new Intent(ProductActivity.this, ViewAllOfferFollowActivity.class);
            intent.putExtra("retailer_id",Storeid);
            intent.putExtra("category_id",offercategory);
            startActivity(intent);
        } else if (v == likeimg) {
            if (fav.equals("0")) {
                likeimg.setImageResource(R.drawable.ic_like);
                Favstatus="1";
                if (isNetworkConnected()) {
                    presenter.AddFavourite(idholder,offerid,Favstatus);
                }  else {
                    showAlert("Please connect to internet.", R.style.DialogAnimation);
                }
            } else if (fav.equals("1")) {
                likeimg.setImageResource(R.drawable.heart);
                Favstatus="0";
                if (isNetworkConnected()) {
                    presenter.AddFavourite(idholder,offerid,Favstatus);
                }  else {
                    showAlert("Please connect to internet.", R.style.DialogAnimation);
                }
            }
        }else if (v==btnfollow){
            if (isNetworkConnected()) {
                if (followstatus.equals("0")){
                    followstatus="1";
                    presenter.AddStoreFollow(idholder,Storeid,followstatus);
                }else if (followstatus.equals("1")){
                    followstatus="0";
                    presenter.AddStoreFollow(idholder,Storeid,followstatus);
                }
            } else {
                showAlert("Please connect to internet.", R.style.DialogAnimation);
            }

        }else if (v==imgNotiBell){
            startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
        }else if (v== imgshare){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "OfferMachi");
            String shareMessage= "\nGet regular updates on the best deals, cashback offers, and discount coupons across retail stores in your location. Get it for free at.\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + "com.desired.offermachi"  +"\n\n";
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "choose one"));
        }

    }

    @Override
    public void success(String response) {
        try {
            JSONObject object = new JSONObject(response);
            String id = object.getString("id");
            String offerid = object.getString("offer_id");
            String offertitle = object.getString("offer_title");
            txtoffername.setText(offertitle);
            String brandid = object.getString("offer_brand");
            String brandname = object.getString("offer_brand_name");
            txtbrandname.setText(brandname);
            offercategory = object.getString("offer_category");
            String subcategory = object.getString("sub_category");
            String offertype = object.getString("offer_type");
            String offervalue = object.getString("offer_value");
            String offertypename = object.getString("offer_type_name");
            txtoffertypename.setText(offertypename + " Off " + offervalue);
            String offerdetail = object.getString("offer_details");
            String startdate = object.getString("start_date");
            String enddate = object.getString("end_date");
            txtenddate.setText(enddate);
            String time = object.getString("alltime");
            txttime.setText(time);
            String description = object.getString("description");

            if(TextUtils.isEmpty(description))
            {
                txtofferdescription.setText("");
            }
            else {
//                txtofferdescription.setText(description);
                txtofferdescription.setText(Html.fromHtml(description));
            }

            couponcode = object.getString("coupon_code");
            String postby = object.getString("posted_by");
            String status = object.getString("status");
             fav=object.getString("favourite_status");
            if (fav.equals("1")){
                likeimg.setImageResource(R.drawable.ic_like);
                count=1;
            }else{
                likeimg.setImageResource(R.drawable.heart);
                count=0;
            }
            String offerimage = object.getString("offer_image");
            if (offerimage.equals("")) {
            } else {
                Picasso.get().load(offerimage).networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(viewPager_product);
            }
            qrcodeimage = object.getString("qr_code_image");
            couponstatus = object.getString("coupon_code_status");
            if (couponstatus.equals("1")){
                couponbutton.setText("View Coupon Code");
            }else if (couponstatus.equals("2")){
                couponbutton.setText("Redeemed");
            }else if (couponstatus.equals("0")){
                couponbutton.setText("Get Coupon Code");
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void storesuccess(String response) {
        Log.e("details", "response=" + response);
        try {
            JSONObject object = new JSONObject(response);
                    Storeid=object.getString("id");
                    String Shopname=object.getString("shop_name");
                    txtstorename.setText(Shopname);
                    txtstorename2.setText(Shopname);
                    String Shoplogo=object.getString("shop_logo");
            if (Shoplogo.equals("")) {
            } else {
                Picasso.get().load(Shoplogo).networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(storeimage);
            }
            if (Shoplogo.equals("")) {
            } else {
                Picasso.get().load(Shoplogo).networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(storelogothumb);
            }
                    String ShopCategory=object.getString("shop_category");

                    String Shopdes=object.getString("about_store");

                    txtstoredescription.setText(Shopdes);
          followstatus=object.getString("favourite_status");
          if (followstatus.equals("1")){
              btnfollow.setText("Unfollow");
              btnfollow.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.view_red_background));
          }else if (followstatus.equals("0")){
            btnfollow.setText("Follow");
              btnfollow.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.view_background));
        }

        }
        catch (JSONException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void favsuccess(String response) {
        if (isNetworkConnected()) {
            presenter.OfferDetail(idholder, offerid);
        } else {
            showAlert("Please connect to internet.", R.style.DialogAnimation);
        }
    }

    @Override
    public void couponsucess(String response) {
        if (isNetworkConnected()) {
            presenter.OfferDetail(idholder, offerid);
        } else {
            showAlert("Please connect to internet.", R.style.DialogAnimation);
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

    private void showAlert(String message, int animationSource) {
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

    private void showdialog(String image, String code) {
        final Dialog dialog = new Dialog(ProductActivity.this);
        dialog.setContentView(R.layout.coupon_code_activity);
        dialog.setTitle("Custom Dialog");
        qr_code_img_id = dialog.findViewById(R.id.qr_code_img_id);
        coupon_button_apply_id = dialog.findViewById(R.id.coupon_button_apply_id);
        coupon_button_apply_id.setText(code);
        if (image.equals("")) {
        } else {
            Picasso.get().load(image).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(qr_code_img_id);
        }

        btnok = (TextView) dialog.findViewById(R.id.coupon_ok_id);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
//notification count
    @Override
    public void successnoti(String response) {
        if(TextUtils.isEmpty(response))
        {
            tvNotiCount.setText("0");
        }
        else {
//            tvNotiCount.setText(push_notifications_count);
            tvNotiCount.setText(response);
//            Log.e("","count= "+tvNotiCount);
        }
    }

    @Override
    public void errornoti(String response) {

    }

    @Override
    public void failnoti(String response) {

    }

     /*  private void Itemshardialog() {
        final Dialog dialog = new Dialog(ProductActivity.this);
        dialog.setContentView(R.layout.share_item_activity);
        dialog.setTitle("Custom Dialog");
        LinearLayout message = (LinearLayout) dialog.findViewById(R.id.message_linear_id);
        LinearLayout whatsapp = (LinearLayout) dialog.findViewById(R.id.whatsapp_linear_id);
        LinearLayout facebook=(LinearLayout) dialog.findViewById(R.id.facebook_linear_id);
        dialog.show();

    }*/

}


