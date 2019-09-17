package com.desired.offermachi.customer.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.constant.hand;
import com.desired.offermachi.customer.model.CategoryListModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.CustomerCategoryListPresenter;
import com.desired.offermachi.customer.view.adapter.CategortListAdapter;
import com.desired.offermachi.customer.view.adapter.InterestCategortListAdapter;

import java.util.ArrayList;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class ActInterestCategoryNew extends AppCompatActivity implements View.OnClickListener, CustomerCategoryListPresenter.CustomerCategoryList {
    ImageView imageViewback;
    RecyclerView product_recyclerview;
    private InterestCategortListAdapter categortListAdapter=null;
    private CustomerCategoryListPresenter presenter;
    private String idholder,followsatus,Catid;
    int adptrPos=0;
    Button btProceed;
    TextView tvTitle;
    hand handobj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_interest_category);
        handobj = hand.getintance();
        initview();
    }

      private void initview(){
          presenter = new CustomerCategoryListPresenter(ActInterestCategoryNew.this, ActInterestCategoryNew.this);
          User user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
          idholder= user.getId();
          imageViewback = findViewById(R.id.imageback);
          btProceed = findViewById(R.id.bt_proceed);
          tvTitle = findViewById(R.id.tv_title_main);

          btProceed.setOnClickListener(this);
          imageViewback.setOnClickListener(this);
          product_recyclerview = (RecyclerView) findViewById(R.id.category_recycler_id);
          GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4, LinearLayoutManager.VERTICAL, false);
          product_recyclerview.setLayoutManager(gridLayoutManager);
          product_recyclerview.setItemAnimator(new DefaultItemAnimator());
          if (isNetworkConnected()) {
              presenter.GetCategoryList(idholder);//for show all category
          }  else {
              showAlert("Please connect to internet.", R.style.DialogAnimation);
          }

          LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(followReceiver,
                  new IntentFilter("Follow"));
      }

    public BroadcastReceiver followReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            followsatus = intent.getStringExtra("followstatus");
            Catid = intent.getStringExtra("catid");
            adptrPos = intent.getIntExtra("pos",0);

            if (isNetworkConnected()) {
                presenter.CategoryFollow(idholder,Catid,followsatus);
            }  else {
                showAlert("Please connect to internet.", R.style.DialogAnimation);
            }

        }
    };
    String sAllCatId="";
    String sAllCatNm="";
    String sAllCatBannerimage="";

    @Override
    public void onClick(View v) {
        if (v==imageViewback){
            onBackPressed();
        }
        else if (v==btProceed){


            for(int i=0;i<arCatList.size();i++)
            {
                if(arCatList.get(i).getFollowstatus().equals("1")) {
                    if (TextUtils.isEmpty(sAllCatId)) {
                        sAllCatId = arCatList.get(i).getCatid();
                        sAllCatNm = arCatList.get(i).getCatname();
                        sAllCatBannerimage = arCatList.get(i).getBannerimage();


                    } else {
                        sAllCatId = sAllCatId + "," + arCatList.get(i).getCatid();
                        sAllCatNm = sAllCatNm + "," + arCatList.get(i).getCatname();
                        sAllCatBannerimage = sAllCatBannerimage + "," + arCatList.get(i).getBannerimage();
                    }
                }
            }

            if(TextUtils.isEmpty(sAllCatId))
            {
                Toast.makeText(this, "please select at least one category for your interest", Toast.LENGTH_SHORT).show();
            }
            else {
//                SaveInterestCat();
                handobj.setCatid(sAllCatId);
                handobj.setCatname(sAllCatNm);
                handobj.setCatimage(sAllCatBannerimage);

//                handobj.setCatid("");
//                handobj.setCatname("");
//                handobj.setCatimage("");

                Intent myIntent = new Intent(this, DashBoardActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);
                finish();
            }
        }
    }

    @Override
    public void followsuccess(String response) {


        arCatList.get(adptrPos).setFollowstatus(followsatus);
        if (categortListAdapter != null)
        {
            categortListAdapter.notifyDataSetChanged();
        }

        SetCountinuButton();

    }
    ArrayList<CategoryListModel> arCatList=new ArrayList<>();
    @Override
    public void success(ArrayList<CategoryListModel> response) {
        arCatList.clear();
        arCatList=response;

        categortListAdapter = new InterestCategortListAdapter(getApplicationContext(),arCatList);
        product_recyclerview.setAdapter(categortListAdapter);

        SetCountinuButton();
//        if(categortListAdapter==null)
//        {
//            categortListAdapter = new CategortListAdapter(getApplicationContext(),arCatList);
//            product_recyclerview.setAdapter(categortListAdapter);
//
//        }
//        else {
//            categortListAdapter.notifyDataSetChanged();
//        }

//        categortListAdapter = new CategortListAdapter(getApplicationContext(),response);
//        product_recyclerview.setAdapter(categortListAdapter);

    }
public void SetCountinuButton()
{
    boolean checkFlag=false;
    for(int i=0;i<arCatList.size();i++) {
        if (arCatList.get(i).getFollowstatus().equals("1")) {
            checkFlag=true;
        }
    }

    if(checkFlag) {
        btProceed.setVisibility(View.VISIBLE);
    }
    else
    {
        btProceed.setVisibility(View.GONE);
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
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
        super.onBackPressed();
    }

   /* @Override
    protected void onRestart() {
        super.onRestart();
        initview();
    }*/
}
