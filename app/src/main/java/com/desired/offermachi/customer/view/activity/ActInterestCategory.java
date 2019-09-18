package com.desired.offermachi.customer.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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
import com.desired.offermachi.customer.view.adapter.MultiChoiceCategortListAdapter;
import com.desired.offermachi.retalier.view.activity.RetalierDashboard;

import java.util.ArrayList;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class ActInterestCategory extends AppCompatActivity implements View.OnClickListener, CustomerCategoryListPresenter.CustomerCategoryList,MultiChoiceCategortListAdapter.AdapterClick {
    ImageView imageViewback,info;
    TextView tvTitle;
    RecyclerView product_recyclerview;
    private MultiChoiceCategortListAdapter categortListAdapter=null;
    private CustomerCategoryListPresenter presenter;
    private String idholder,followsatus,Catid;
    int adptrPos=0;
    Button btProceed;
    hand handobj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dashboard_category);
        handobj = hand.getintance();
        initview();

    }

    private void initview(){

          presenter = new CustomerCategoryListPresenter(ActInterestCategory.this, ActInterestCategory.this);
          User user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
          idholder= user.getId();
          tvTitle = findViewById(R.id.tv_title);
          imageViewback = findViewById(R.id.imageback);
          info=findViewById(R.id.info_id);
          info.setOnClickListener(this);
          btProceed = findViewById(R.id.bt_proceed);
          imageViewback.setOnClickListener(this);
          btProceed.setOnClickListener(this);
          tvTitle.setVisibility(View.VISIBLE);

          product_recyclerview = (RecyclerView) findViewById(R.id.category_recycler_id);
          GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4, LinearLayoutManager.VERTICAL, false);
          product_recyclerview.setLayoutManager(gridLayoutManager);
          product_recyclerview.setItemAnimator(new DefaultItemAnimator());
          if (isNetworkConnected()) {
              presenter.GetCategoryList(idholder);//for all category
          }  else {
              showAlert("Please connect to internet.", R.style.DialogAnimation);
          }

//          LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(followReceiver,
//                  new IntentFilter("Follow"));
      }

//    public BroadcastReceiver followReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            followsatus = intent.getStringExtra("followstatus");
//            Catid = intent.getStringExtra("catid");
//            adptrPos = intent.getIntExtra("pos",0);
//            if (isNetworkConnected()) {
//                presenter.CategoryFollow(idholder,Catid,followsatus);
//            }  else {
//                showAlert("Please connect to internet.", R.style.DialogAnimation);
//            }
//        }
//    };

    @Override
    public void onClick(View v) {
        if (v==imageViewback){
            onBackPressed();
        }
        else if (v==btProceed){
            getAllSelectedId();
        }else if(v==info){
            Intent intent = new Intent(ActInterestCategory.this, InfoActivity.class);
            startActivity(intent);
        }
    }
 String sAllCatId="";
 String sAllCatNm="";
 String sAllCatBannerimage="";

    public void getAllSelectedId()
    {
        sAllCatId="";
        sAllCatNm="";
        sAllCatBannerimage="";

        for(int i=0;i<arCatList.size();i++)
        {
            if(arCatList.get(i).isCheckStatus()) {
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
        Log.e("","sAllCatId= "+sAllCatId);

        if(TextUtils.isEmpty(sAllCatId))
        {
            Toast.makeText(this, "please select a category", Toast.LENGTH_SHORT).show();
        }
        else {
            handobj.setCatid(sAllCatId/*sSingleCateId*/);//sAllCatId
            handobj.setCatname(sAllCatNm/*sSingleCateNm*/);//sAllCatNm
            handobj.setCatimage(sAllCatBannerimage/*sSingleCateBannerimage*/);//sAllCatBannerimage

           /* Intent myIntent = new Intent(this, DashBoardActivity.class);

            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(myIntent);
            finish();*/
        }
    }
    @Override
    public void followsuccess(String response) {

        arCatList.get(adptrPos).setFollowstatus(followsatus);
        if (categortListAdapter != null)
        {
            categortListAdapter.notifyDataSetChanged();
        }

    }
    ArrayList<CategoryListModel> arCatList=new ArrayList<>();
    @Override
    public void success(ArrayList<CategoryListModel> response) {
        arCatList.clear();
        arCatList=response;
        categortListAdapter = new MultiChoiceCategortListAdapter(getApplicationContext(),arCatList,this);
        product_recyclerview.setAdapter(categortListAdapter);
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
        super.onBackPressed();
        finish();
    }
//adapter click
    @Override
    public void onClick(int position) {
        if(arCatList.get(position).isCheckStatus())
        {
            arCatList.get(position).setCheckStatus(false);
        }
        else
        {
            arCatList.get(position).setCheckStatus(true);
        }
        categortListAdapter.notifyDataSetChanged();

    }
}
