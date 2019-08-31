package com.desired.offermachi.customer.view.fragment;


import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.StoreModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.StoreListPresenter;
import com.desired.offermachi.customer.view.activity.StoreCouponCodeActivity;
import com.desired.offermachi.customer.view.adapter.CustomerStoreAdapter;
import com.desired.offermachi.customer.view.adapter.StoreViewallAdapter;
import com.desired.offermachi.customer.view.activity.DashBoardActivity;
import com.desired.offermachi.customer.view.activity.FilterShowActivity;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class StoreFragment extends Fragment implements StoreListPresenter.StoreList, View.OnClickListener {
    View v;
    RecyclerView storerecycle;
    private CustomerStoreAdapter customerStoreAdapter;
    private StoreListPresenter presenter;
    String idholder;
    TextView filtertext,sortbytext;

    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         v=inflater.inflate(R.layout.fragment_store, container, false);
        initview();
        return v;
    }
    private void initview(){
        ((DashBoardActivity)getActivity()).setToolTittle("Stores",2);
        User user = UserSharedPrefManager.getInstance(getContext()).getCustomer();
        idholder= user.getId();
        presenter = new StoreListPresenter(getContext(), StoreFragment.this);
        storerecycle=v.findViewById(R.id.storerecycleview);
        filtertext=(TextView)v.findViewById(R.id.filter_text_id);
        filtertext.setOnClickListener(this);
        sortbytext=(TextView)v.findViewById(R.id.sortby_text_id);
        sortbytext.setOnClickListener(this);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        storerecycle.setLayoutManager(gridLayoutManager2);
        storerecycle.setItemAnimator(new DefaultItemAnimator());
        if (isNetworkConnected()) {
            presenter.ViewAllStore(idholder);
        }  else {
            showAlert("Please connect to internet.", R.style.DialogAnimation);
        }
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(StoreReceiver,
                new IntentFilter("StoreFollow"));
    }
    public BroadcastReceiver StoreReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = intent.getStringExtra("followstatus");
            String storeid = intent.getStringExtra("storeid");
            presenter.FollowStore(idholder,storeid,status);
        }
    };


    @Override
    public void success(ArrayList<StoreModel> response) {
        customerStoreAdapter=new CustomerStoreAdapter(getContext(),response);
        storerecycle.setAdapter(customerStoreAdapter);
    }

    @Override
    public void followsuccess(String response) {
        if (isNetworkConnected()) {
            presenter.ViewAllStore(idholder);
        }  else {
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
    private void showAlert(String message, int animationSource){
        final PrettyDialog prettyDialog = new PrettyDialog(getActivity());
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
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onClick(View v) {
        if (v==filtertext){
            Intent intent = new Intent(getActivity(), FilterShowActivity.class);
            startActivity(intent);
        }else if (v==sortbytext){
            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.sort_dialog_activity);
            dialog.setTitle("Custom Dialog");
            RelativeLayout atoz=(RelativeLayout)dialog.findViewById(R.id.atoz_id);
            RelativeLayout ztoa=(RelativeLayout)dialog.findViewById(R.id.ztoa_id);
            dialog.show();
        }
    }
}
