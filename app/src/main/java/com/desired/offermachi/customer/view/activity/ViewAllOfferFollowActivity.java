package com.desired.offermachi.customer.view.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.CategoryListModel;
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.customer.model.StoreModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.model.muliplelocationshowmodel;
import com.desired.offermachi.customer.model.slider_viewpager_model;
import com.desired.offermachi.customer.presenter.CustomerOfferDetailPresenter;
import com.desired.offermachi.customer.presenter.NotificationCountPresenter;
import com.desired.offermachi.customer.presenter.StoreDetailPresenter;
import com.desired.offermachi.customer.presenter.ViewStoreOfferPresenter;
import com.desired.offermachi.customer.view.adapter.CustomerStoreAdapter;
import com.desired.offermachi.customer.view.adapter.CustomerTrendingAdapterNew;
import com.desired.offermachi.customer.view.adapter.MultipleshoelocationAdapter;
import com.desired.offermachi.customer.view.adapter.slider_viewpages_adaper;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.model.mylocation_model;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import de.hdodenhof.circleimageview.CircleImageView;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class ViewAllOfferFollowActivity extends AppCompatActivity implements View.OnClickListener, StoreDetailPresenter.StoreDetail, NotificationCountPresenter.NotiUnReadCount , ViewStoreOfferPresenter.ViewOffer{
    ImageView storelogoid;
    ImageView storelogothumb;
    private StoreDetailPresenter presenter;
    TextView txtstorename/*,txtstorenem2*/,txtstoredescription;
//    TextView txtmondayopen,txttuesdayopen,txtwednesdayopen,txtthursdayopen,txtfridayopen,txtsaturdayopen,txtsundayopen;
    String Mondayopentime,Tuesdayopentime,Wednesdayopentime,Thursdayopentime,Fridayopentime,Saturdayopentime,Sundayopentime;
    String Mondayclosetime,Tuesdayclosetime,Wednesdayclosetime,Thursdayclosetime,Fridayclosetime,Saturdayclosetime,Sundayclosetime;
    String category_id,retailer_id;
    String idholder;
    String Storeid;
   ArrayList<StoreModel> storelist;
   private CustomerStoreAdapter customerStoreAdapter;
   RecyclerView storerecycle;
   TextView viewalloffer,btnfollow;
   String followstatus;
   ImageView btnnoti,location_icon;
   TextView txtcontactdetail;
   TextView txtmail,txtphone,txtaddress;
   Button btnok;
    String storeaddress,storeemail,storemobile;
    ImageView imageviewback,info;
    TextView tvNotiCount;
    private NotificationCountPresenter notiCount;
    RecyclerView categoryrecycle ,multiple_location;
    private CustomerTrendingAdapterNew customerTrendingAdapter;
    private ViewStoreOfferPresenter presenters;
    TextView textview,multiplelocation;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    RatingBar rb;
    ArrayList<slider_viewpager_model> arrayList ;
    com.desired.offermachi.customer.view.adapter.slider_viewpages_adaper slider_viewpages_adaper;
    ArrayList<muliplelocationshowmodel> multiplearrayList ;
    MultipleshoelocationAdapter multipleshoelocationAdapter;
    TextView locationname,addressname,mobileno;
    LinearLayout timmerclose;
    String locality_name,location_address,mobile,location_latitude,location_longitude;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_offer_follow_activity);
        initview();
    }
    private void initview(){
        presenter = new StoreDetailPresenter(ViewAllOfferFollowActivity.this, ViewAllOfferFollowActivity.this);
        User user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
        idholder = user.getId();
        Intent intent=getIntent();
        storelist=new ArrayList<>();
        arrayList=new ArrayList<>();
        multiplearrayList=new ArrayList<>();
        retailer_id=intent.getStringExtra("retailer_id");
        category_id=intent.getStringExtra("category_id");
//        storelogoid=findViewById(R.id.iv_main);

        storelogothumb=findViewById(R.id.storelogothumb);
        txtstorename=findViewById(R.id.storename);
//        txtstorenem2=findViewById(R.id.storename2);
        txtstoredescription=findViewById(R.id.storedescription);


//        txtmondayopen=findViewById(R.id.mondayopen);
//        txttuesdayopen=findViewById(R.id.tuesdayopen);
//        txtwednesdayopen=findViewById(R.id.wednesdayopen);
//        txtthursdayopen=findViewById(R.id.thursdayopen);
//        txtfridayopen=findViewById(R.id.fridayopen);
//        txtsaturdayopen=findViewById(R.id.saturdayopen);
//        txtsundayopen=findViewById(R.id.sundayopen);
        storerecycle=findViewById(R.id.storerecycleview);

//        viewalloffer=findViewById(R.id.viewalloffer_id);
//        viewalloffer.setOnClickListener(this);
        btnnoti=findViewById(R.id.btnnoti);
        btnnoti.setOnClickListener(this);
        timmerclose=findViewById(R.id.timmerclose_id);

        imageviewback=findViewById(R.id.imageviewback);
        imageviewback.setOnClickListener(this);
        info=findViewById(R.id.info_id);
        info.setOnClickListener(this);
        locationname=findViewById(R.id.location_address_id);
//        addressname=findViewById(R.id.address_id);
//        mobileno=findViewById(R.id.mobileno_id);


        notiCount = new NotificationCountPresenter(this,this);
        tvNotiCount = findViewById(R.id.txtMessageCount);
        notiCount.NotificationUnreadCount(idholder);

        presenters = new ViewStoreOfferPresenter(ViewAllOfferFollowActivity.this, ViewAllOfferFollowActivity.this);
        categoryrecycle = findViewById(R.id.categoryrecycleview);
//        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
//        categoryrecycle.setLayoutManager(gridLayoutManager1);
        categoryrecycle.setHasFixedSize(true);
        categoryrecycle.setLayoutManager(new LinearLayoutManager(this));
        categoryrecycle.setItemAnimator(new DefaultItemAnimator());
        categoryrecycle.setNestedScrollingEnabled(false);

        location_icon=findViewById(R.id.location_icon_id);
        location_icon.setOnClickListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

         btnfollow=findViewById(R.id.btnfollow);
        btnfollow.setOnClickListener(this);
        Toast.makeText(this, ""+followstatus, Toast.LENGTH_SHORT).show();


        RatingBar ratingbar = (RatingBar) findViewById(R.id.ratingBar);
        ratingbar.setRating(3.67f);



        storerecycle.setLayoutManager((new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false)));
        storerecycle.setItemAnimator(new DefaultItemAnimator());
        storerecycle.setNestedScrollingEnabled(false);
        if (isNetworkConnected()) {
            presenter.StoreDetail(idholder, retailer_id,category_id);
            presenters.ViewAllStoreOffer(idholder, retailer_id);
        } else {
            showAlert("Please connect to internet.", R.style.DialogAnimation);
        }
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(StoreReceiver,
                new IntentFilter("StoreFollow"));

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(locationReceiver,
                new IntentFilter("Favourite"));

    }
    public BroadcastReceiver StoreReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = intent.getStringExtra("followstatus");
            String storeid = intent.getStringExtra("storeid");
            presenter.AddStoreFollow(idholder,storeid,status);
        }
    };
    public BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String fav = intent.getStringExtra("fav");
            String offerid = intent.getStringExtra("offerid");
            presenters.AddFavourite(idholder, offerid, fav);
        }
    };
    @Override
    public void onClick(View v) {
        if (v==btnnoti){
            startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
        }else if (v==location_icon){
            Intent intent = new Intent(ViewAllOfferFollowActivity.this, OfferPageMultipleLocation.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("id",idholder);
            intent.putExtra("locality_name",locality_name);
            intent.putExtra("location_address",location_address);
            intent.putExtra("mobile",mobile);
            intent.putExtra("location_latitude",location_latitude);
            intent.putExtra("location_longitude",location_longitude);
            startActivity(intent);

        }else if (v==imageviewback){
            onBackPressed();
        } else if(v==info){
            Intent intent = new Intent(ViewAllOfferFollowActivity.this, InfoActivity.class);
            startActivity(intent);
        }
        if (v==btnfollow) {
            if (followstatus.equals("0")){
                followstatus="1";
                presenter.AddStoreFollow(idholder,Storeid,followstatus);
            }else if (followstatus.equals("1")){
                followstatus="0";
                presenter.AddStoreFollow(idholder,Storeid,followstatus);
            }
        }

        }

        @Override
        public void success(String response) {
        try {
            JSONObject object = new JSONObject(response);
            Storeid=object.getString("id");
            String Shopname=object.getString("shop_name");
            txtstorename.setText(Shopname);
            followstatus=object.getString("favourite_status");
            if (followstatus.equals("1")){
                btnfollow.setText("Unfollow");
                btnfollow.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.view_red_background));
            }else if (followstatus.equals("0")){
                btnfollow.setText("Follow");
                btnfollow.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.view_background));
            }

            String Shoplogo=object.getString("shop_logo");
            JSONArray jsonArray = object.getJSONArray("retailer_banners_images");
            JSONObject object2;
            arrayList.clear();
            for (int count = 0; count < jsonArray.length(); count++) {
                object2 = jsonArray.getJSONObject(count);
                slider_viewpager_model slider_viewpager_model=new slider_viewpager_model(
                        object2.getString("retailer_banner_image")
                );

                arrayList.add(slider_viewpager_model);
            }

            slider_viewpages_adaper = new slider_viewpages_adaper(this, arrayList);
            viewPager.setAdapter(slider_viewpages_adaper);

            viewPager.setCurrentItem(0);
            slider_viewpages_adaper.setTimer(viewPager,3);
            dotscount = slider_viewpages_adaper.getCount();

            dots = new ImageView[dotscount];
            sliderDotspanel.removeAllViews();
            for (int i = 0; i < dotscount; i++) {

                dots[i] = new ImageView(this);
                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.slider_non_active_dot));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(8, 0, 8, 0);

                sliderDotspanel.addView(dots[i], params);
            }

            dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.slider_active_dot));

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    for (int i = 0; i < dotscount; i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.slider_non_active_dot));
                    }

                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.slider_active_dot));

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            JSONArray jsonArray2 = object.getJSONArray("retailer_locations");
            JSONObject object3;
            for (int count = 0; count < jsonArray2.length(); count++) {
                object3 = jsonArray2.getJSONObject(count);
                idholder  = object3.getString("id");
                 locality_name = object3.getString("locality_name");
                 location_address = object3.getString("address");
                 mobile=object3.getString("mobile");
                 location_latitude=object3.getString("location_latitude");
                 location_longitude=object3.getString("location_longitude");
            }

            if (Shoplogo.equals("")) {
            } else {
                Picasso.get().load(Shoplogo).networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(storelogothumb);
            }

            String ShopCategory=object.getString("shop_category");
            String Shopdes=object.getString("about_store");
            txtstoredescription.setText(Shopdes);
            String storeopentime=object.getString("opening_time");
            String storeclosetime=object.getString("closing_time");
            if (storeopentime.equals("null")){
            }else{
                StringTokenizer time = new StringTokenizer(storeopentime, ",");
                Mondayopentime= time.nextToken();

                Tuesdayopentime= time.nextToken();

                Wednesdayopentime= time.nextToken();

                Thursdayopentime= time.nextToken();

                Fridayopentime= time.nextToken();

                Saturdayopentime= time.nextToken();

                Sundayopentime= time.nextToken();
            }
            if (storeclosetime.equals("null")){

            }else{
                StringTokenizer timeto = new StringTokenizer(storeclosetime, ",");
                timmerclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(ViewAllOfferFollowActivity.this);
                        dialog.setContentView(R.layout.open_close_dialog);
                        dialog.setTitle("Custom Dialog");
                        ImageView cancle=(ImageView)dialog.findViewById(R.id.cancle_img_id);
                        cancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        TextView  txtmondayopen=dialog.findViewById(R.id.mondayopen);
                        TextView   txttuesdayopen=dialog.findViewById(R.id.tuesdayopen);
                        TextView  txtwednesdayopen=dialog.findViewById(R.id.wednesdayopen);
                        TextView  txtthursdayopen=dialog.findViewById(R.id.thursdayopen);
                        TextView  txtfridayopen=dialog.findViewById(R.id.fridayopen);
                        TextView  txtsaturdayopen=dialog.findViewById(R.id.saturdayopen);
                        TextView   txtsundayopen=dialog.findViewById(R.id.sundayopen);

                        Mondayclosetime = timeto.nextToken();
                        txtmondayopen.setText(Mondayopentime+"-"+Mondayclosetime);

                        Tuesdayclosetime= timeto.nextToken();
                        txttuesdayopen.setText(Tuesdayopentime+"-"+Tuesdayclosetime);

                        Wednesdayclosetime= timeto.nextToken();
                        txtwednesdayopen.setText(Wednesdayopentime+"-"+Wednesdayclosetime);

                        Thursdayclosetime= timeto.nextToken();
                        txtthursdayopen.setText(Thursdayopentime+"-"+Thursdayclosetime);

                        Fridayclosetime= timeto.nextToken();
                        txtfridayopen.setText(Fridayopentime+"-"+Fridayclosetime);

                        Saturdayclosetime= timeto.nextToken();
                        txtsaturdayopen.setText(Saturdayopentime+"-"+Saturdayclosetime);

                        Sundayclosetime= timeto.nextToken();
                        txtsundayopen.setText(Sundayopentime+"-"+Sundayclosetime);
                        dialog.show();

                    }
                });

//                Mondayclosetime = timeto.nextToken();
//                txtmondayopen.setText(Mondayopentime+"-"+Mondayclosetime);
//
//                Tuesdayclosetime= timeto.nextToken();
//                txttuesdayopen.setText(Tuesdayopentime+"-"+Tuesdayclosetime);
//
//                Wednesdayclosetime= timeto.nextToken();
//                txtwednesdayopen.setText(Wednesdayopentime+"-"+Wednesdayclosetime);
//
//                Thursdayclosetime= timeto.nextToken();
//                txtthursdayopen.setText(Thursdayopentime+"-"+Thursdayclosetime);
//
//                Fridayclosetime= timeto.nextToken();
//                txtfridayopen.setText(Fridayopentime+"-"+Fridayclosetime);
//
//                Saturdayclosetime= timeto.nextToken();
//                txtsaturdayopen.setText(Saturdayopentime+"-"+Saturdayclosetime);
//
//                Sundayclosetime= timeto.nextToken();
//                txtsundayopen.setText(Sundayopentime+"-"+Sundayclosetime);
            }
            storeaddress=object.getString("address");
            storeemail=object.getString("email");
            storemobile=object.getString("mobile");


          //close time split

           // followstatus=object.getString("favourite_status");

        }
        catch (JSONException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void relatedsuccess(String response) {
        try {
            JSONArray jsonArray=new JSONArray(response);
            JSONObject jsonObject;
            for (int i=0;i<jsonArray.length();i++){
                jsonObject = jsonArray.getJSONObject(i);
                StoreModel storeModel=new StoreModel(
                        jsonObject.getString("id"),
                        jsonObject.getString("shop_name"),
                        jsonObject.getString("shop_logo"),
                        jsonObject.getString("shop_category"),
                        jsonObject.getString("favourite_status")

                );
                storelist.add(storeModel);
                customerStoreAdapter=new CustomerStoreAdapter(getApplicationContext(),storelist,"");
                storerecycle.setAdapter(customerStoreAdapter);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void followsuccess(String response) {
        storelist.clear();
        if (isNetworkConnected()) {
            presenter.StoreDetail(idholder, retailer_id,category_id);
        } else {
            showAlert("Please connect to internet.", R.style.DialogAnimation);
        }
    }

    @Override
    public void success(ArrayList<SelectCategoryModel> response) {
        customerTrendingAdapter = new CustomerTrendingAdapterNew(ViewAllOfferFollowActivity.this, response,"");
        categoryrecycle.setAdapter(customerTrendingAdapter);
    }

    @Override
    public void favsuccess(String response) {

        if (isNetworkConnected()) {
            presenters.ViewAllStoreOffer(idholder, retailer_id);
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

    private void showdialog() {
        final Dialog dialog = new Dialog(ViewAllOfferFollowActivity.this);
        dialog.setContentView(R.layout.contact_dialog);
        dialog.setTitle("Custom Dialog");
        txtmail = dialog.findViewById(R.id.mailid);
        txtphone = dialog.findViewById(R.id.phoneno);
        txtaddress = dialog.findViewById(R.id.address);
        txtmail.setText(storeemail);
        txtaddress.setText(storeaddress);
         txtphone.setText(storemobile);
        btnok = (Button) dialog.findViewById(R.id.btnok);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

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
}
