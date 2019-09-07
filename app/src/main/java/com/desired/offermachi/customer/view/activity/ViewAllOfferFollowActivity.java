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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.StoreModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.CustomerOfferDetailPresenter;
import com.desired.offermachi.customer.presenter.StoreDetailPresenter;
import com.desired.offermachi.customer.view.adapter.CustomerStoreAdapter;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.UserModel;
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

public class ViewAllOfferFollowActivity extends AppCompatActivity implements View.OnClickListener, StoreDetailPresenter.StoreDetail {
    ImageView storelogo;
    CircleImageView storelogothumb;
    private StoreDetailPresenter presenter;
    TextView txtstorename,txtstorenem2,txtstoredescription;
    TextView txtmondayopen,txttuesdayopen,txtwednesdayopen,txtthursdayopen,txtfridayopen,txtsaturdayopen,txtsundayopen;
    String Mondayopentime,Tuesdayopentime,Wednesdayopentime,Thursdayopentime,Fridayopentime,Saturdayopentime,Sundayopentime;
    String Mondayclosetime,Tuesdayclosetime,Wednesdayclosetime,Thursdayclosetime,Fridayclosetime,Saturdayclosetime,Sundayclosetime;
    String category_id,retailer_id;
    String idholder;
    String Storeid;
   ArrayList<StoreModel> storelist;
   private CustomerStoreAdapter customerStoreAdapter;
   RecyclerView storerecycle;
   TextView viewalloffer,btnfollow;
   //String followstatus;
   ImageView btnnoti;
   TextView txtcontactdetail;
   TextView txtmail,txtphone,txtaddress;
   Button btnok;
    String storeaddress,storeemail,storemobile;
    ImageView imageviewback;
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
        retailer_id=intent.getStringExtra("retailer_id");
        category_id=intent.getStringExtra("category_id");
        storelogo=findViewById(R.id.storelogo);
        storelogothumb=findViewById(R.id.storelogothumb);
        txtstorename=findViewById(R.id.storename);
        txtstorenem2=findViewById(R.id.storename2);
        txtstoredescription=findViewById(R.id.storedescription);
        txtmondayopen=findViewById(R.id.mondayopen);
        txttuesdayopen=findViewById(R.id.tuesdayopen);
        txtwednesdayopen=findViewById(R.id.wednesdayopen);
        txtthursdayopen=findViewById(R.id.thursdayopen);
        txtfridayopen=findViewById(R.id.fridayopen);
        txtsaturdayopen=findViewById(R.id.saturdayopen);
        txtsundayopen=findViewById(R.id.sundayopen);
        storerecycle=findViewById(R.id.storerecycleview);
        viewalloffer=findViewById(R.id.viewalloffer_id);
        viewalloffer.setOnClickListener(this);
        btnnoti=findViewById(R.id.btnnoti);
        btnnoti.setOnClickListener(this);
        txtcontactdetail=findViewById(R.id.contactdetail);
        txtcontactdetail.setOnClickListener(this);
        imageviewback=findViewById(R.id.imageviewback);
        imageviewback.setOnClickListener(this);
       /* btnfollow=findViewById(R.id.btnfollow);
        btnfollow.setOnClickListener(this);*/
        /*Toast.makeText(this, ""+followstatus, Toast.LENGTH_SHORT).show();*/
       /* if (followstatus.equals("1")){
            btnfollow.setText("Unfollow");
           btnfollow.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.view_red_background));
        }else{
            btnfollow.setText("follow");
            btnfollow.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.view_background));
        }*/

        storerecycle.setLayoutManager((new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false)));
        storerecycle.setItemAnimator(new DefaultItemAnimator());
        storerecycle.setNestedScrollingEnabled(false);
        if (isNetworkConnected()) {
            presenter.StoreDetail(idholder, retailer_id,category_id);
        } else {
            showAlert("Please connect to internet.", R.style.DialogAnimation);
        }
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(StoreReceiver,
                new IntentFilter("StoreFollow"));

    }
    public BroadcastReceiver StoreReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = intent.getStringExtra("followstatus");
            String storeid = intent.getStringExtra("storeid");
            presenter.AddStoreFollow(idholder,storeid,status);
        }
    };

    @Override
    public void onClick(View v) {
        if (v==viewalloffer){
            Intent myIntent = new Intent(getApplicationContext(), ViewStoreOfferActivity.class);
            myIntent.putExtra("storeid",retailer_id);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(myIntent);
        }else if (v==btnnoti){
            startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
        }else if (v==txtcontactdetail){
            showdialog();

        }else if (v==imageviewback){
            onBackPressed();
        }


        /*else if (v==btnfollow){
            if (followstatus.equals("0")) {
                followstatus = "1";
                presenter.AddStoreFollow(idholder, retailer_id,followstatus );

            }else if (followstatus.equals("1")){
                followstatus="0";
                presenter.AddStoreFollow(idholder, retailer_id,followstatus );
            }


        }*/

    }

    @Override
    public void success(String response) {
        try {
            JSONObject object = new JSONObject(response);
            Storeid=object.getString("id");
            String Shopname=object.getString("shop_name");
            txtstorename.setText(Shopname);
            txtstorenem2.setText(Shopname);

            String Shoplogo=object.getString("shop_logo");
            if (Shoplogo.equals("")) {
            } else {
                Picasso.get().load(Shoplogo).networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(storelogo);
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
                customerStoreAdapter=new CustomerStoreAdapter(getApplicationContext(),storelist);
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
}
