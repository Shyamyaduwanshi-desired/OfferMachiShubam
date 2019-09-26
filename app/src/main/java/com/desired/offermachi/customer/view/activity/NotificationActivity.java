package com.desired.offermachi.customer.view.activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.NotificationModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.model.days_model;
import com.desired.offermachi.customer.model.hours_model;
import com.desired.offermachi.customer.presenter.CustomerNotificationPresenter;
import com.desired.offermachi.customer.presenter.DonoDisturbPresenter;
import com.desired.offermachi.customer.view.adapter.CustomerNotificationAdapter;
import com.desired.offermachi.customer.view.adapter.CustomerTrendingAdapter;
import com.desired.offermachi.customer.view.fragment.DealsoftheDayFragment;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener,
        CustomerNotificationPresenter.NotificationList, DonoDisturbPresenter.DoNotDisInfo,CustomerNotificationAdapter.NotiAdapterClick {
    ImageView imageViewback,info;
    Switch simpleSwitch;
    private CustomerNotificationAdapter customerNotificationAdapter;
    private CustomerNotificationPresenter presenter;
    RecyclerView categoryrecycle;
    String idholder;
    String Nameholder,EmailHolder,PhoneHolder,AddressHolder,GenderHolder,Dobholder,ImageHolder,SmartShoppingHolder;
    String SoundHolder;
TextView tvDONodist;
    private DonoDisturbPresenter dndPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        InitDoNoDisturbData();
        init();
    }
    private void init(){
        presenter=new CustomerNotificationPresenter(NotificationActivity.this,NotificationActivity.this);
        dndPresenter=new DonoDisturbPresenter(NotificationActivity.this,NotificationActivity.this);
        User user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
        idholder=user.getId();
        Nameholder= user.getUsername();
        EmailHolder=user.getEmail();
        PhoneHolder= user.getMobile();
        AddressHolder= user.getAddress();
        GenderHolder= user.getGender();
        ImageHolder=user.getProfile();
        SmartShoppingHolder=user.getSmartShopping();
        SoundHolder=user.getNotificationsound();
        imageViewback=findViewById(R.id.imageback);
        info=findViewById(R.id.info_id);
        simpleSwitch =findViewById(R.id.donotswitch);
        tvDONodist =findViewById(R.id.tv_disturb);
        categoryrecycle = findViewById(R.id.categoryrecycleview);


        imageViewback.setOnClickListener(this);
        info.setOnClickListener(this);
        tvDONodist.setOnClickListener(this);

        simpleSwitch.setOnCheckedChangeListener(this);
        if (SoundHolder.equals("1")){
            simpleSwitch.setChecked(true);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        categoryrecycle.setLayoutManager(linearLayoutManager);
        categoryrecycle.setItemAnimator(new DefaultItemAnimator());
        if (isNetworkConnected()){
            presenter.sentRequest(idholder);
            dndPresenter.GetDoNoDisStatus(idholder);
        } else {
//            ShowAlert(this, "Please connect to internet.");
            showAlert("Please connect to internet.", R.style.DialogAnimation);
        }

        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked)
               {
//                   ShowAlertStatusDlg();
                   if(dndStatus.equals("1"))
                   {
                       Toast.makeText(NotificationActivity.this, "You have already set DND setting Start date from"+dnd_start_time+" to "+dnd_end_time, Toast.LENGTH_SHORT).show();
                   }
                   Intent intent=new Intent(NotificationActivity.this,ActDoNotDisturbSetting.class);
                   intent.putExtra("dnd_id",dndId);//dndStatus="0",dnd_start_time="",dnd_end_time="",dndId=""
                   intent.putExtra("dnd_status",dndStatus);
                   intent.putExtra("dnd_start_time",dnd_start_time);
                   intent.putExtra("dnd_end_time",dnd_end_time);

                   startActivity(intent);
               }
               else
               {
                   if(mDialog!=null)
                   {
                       mDialog.dismiss();
                   }
               }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v==imageViewback){
//            onBackPressed();
            finish();
        }else if(v==info){
            Intent intent = new Intent(NotificationActivity.this, InfoActivity.class);
            startActivity(intent);
        }
        else if (v==tvDONodist){
//startActivity(new Intent(this,ActDoNotDisturbSetting.class));

        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            User user = new User(
                    idholder,
                    Nameholder,
                    EmailHolder,
                    PhoneHolder,
                    AddressHolder,
                    GenderHolder,
                    Dobholder,
                    ImageHolder,
                    SmartShoppingHolder,
                    "1"
                    ,
                    "1"//shyam 11/9/19
            );
            UserSharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
        }else{
            User user = new User(
                    idholder,
                    Nameholder,
                    EmailHolder,
                    PhoneHolder,
                    AddressHolder,
                    GenderHolder,
                    Dobholder,
                    ImageHolder,
                    SmartShoppingHolder,
                    "0"
                    ,
                    "1"//shyam 11/9/19
            );
            UserSharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
        }
    }
    ArrayList<NotificationModel> arNoti=new ArrayList<>();
    @Override
    public void success(ArrayList<NotificationModel> response) {
        arNoti.clear();
        arNoti=response;
        customerNotificationAdapter = new CustomerNotificationAdapter(response,NotificationActivity.this,this);
        categoryrecycle.setAdapter(customerNotificationAdapter);
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
    String[] getStatus;
    public void InitDoNoDisturbData()
    {
        getStatus= this.getResources().getStringArray(R.array.status_item);
    }
    int locPos=0;
    String status="";
    AlertDialog mDialog=null;
    public  void ShowAlertStatusDlg()
    {
        if(mDialog!=null)
        {
            mDialog=null;
        }
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Select Time");
        mBuilder.setSingleChoiceItems(getStatus, locPos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                locPos=i;
                status=getStatus[i];
                dialogInterface.dismiss();
            }
        });

         mDialog = mBuilder.create();
         mDialog.show();
    }
    ///////////////////////only for get dnd data///////////////////////
    @Override
    public void success(ArrayList<days_model> dayResponse, ArrayList<hours_model> hoursResponse, String status) {

    }
    String dndStatus="0",dnd_start_time="",dnd_end_time="",dndId="";
    @Override
    public void successDND(JSONObject objJson, String status) {
        switch (status)
        {
            case "3":
                try {
//                    JSONObject objJson =new JSONObject(status);
                        String id = objJson.getString("id");
                         dndId = objJson.getString("dnd_id");
                         dnd_start_time = objJson.getString("dnd_start_time");
                         dnd_end_time = objJson.getString("dnd_end_time");
                    dndStatus = objJson.getString("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }

    }
//adapter click
    @Override
    public void onNotiClick(int position) {

       String Custom_offertype=arNoti.get(position).getCustom_offertype();

        UserSharedPrefManager.SaveClickNoti(this,"1",Custom_offertype);

        Intent myIntent = new Intent(this, DashBoardActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(myIntent);
        finish();

//        Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
//        DashBoardActivity dash=new DashBoardActivity();
//        dash.GoDealOfTheDay(1);

//        getSupportFragmentManager().beginTransaction().
//                replace(R.id.root_layout, new DealsoftheDayFragment()).commit();

    }
    //////////////////////////////////////////////////
}
