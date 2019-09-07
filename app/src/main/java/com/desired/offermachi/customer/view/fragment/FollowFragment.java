package com.desired.offermachi.customer.view.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.CategoryListModel;
import com.desired.offermachi.customer.model.FollowStoreModel;
import com.desired.offermachi.customer.model.StoreModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.CustomerFollowCategoryRetailerPresenter;
import com.desired.offermachi.customer.presenter.MyCouponPresenter;
import com.desired.offermachi.customer.view.activity.DashBoardActivity;
import com.desired.offermachi.customer.view.activity.FilterShowActivity;
import com.desired.offermachi.customer.view.activity.ViewOfferActivity;
import com.desired.offermachi.customer.view.adapter.CategortListAdapter;
import com.desired.offermachi.customer.view.adapter.CustomerStoreAdapter;
import com.desired.offermachi.customer.view.adapter.FollowCategoryListAdapter;
import com.desired.offermachi.customer.view.adapter.FollowRetailerListAdapter;

import java.util.ArrayList;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


public class FollowFragment extends Fragment implements View.OnClickListener, CustomerFollowCategoryRetailerPresenter.FollowList {
    View view;
    TextView sortbytext,filtertext;
    RecyclerView categoryrecycle,storerecycle;
    String idholder;
    private FollowCategoryListAdapter followCategoryListAdapter;
    private FollowRetailerListAdapter followRetailerListAdapter;
    private CustomerFollowCategoryRetailerPresenter presenter;
    String followsatus,Catid;

    public FollowFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.follow_activity, container, false);
        ((DashBoardActivity)getActivity()).setToolTittle("iFollow",2);
        initview();
        return  view;
    }
    private void initview(){
        presenter = new CustomerFollowCategoryRetailerPresenter(getActivity(),FollowFragment.this);
        User user = UserSharedPrefManager.getInstance(getActivity()).getCustomer();
        idholder= user.getId();
        sortbytext=view.findViewById(R.id.sortby_text_id);
        sortbytext.setOnClickListener(this);
        filtertext=view.findViewById(R.id.filter_text_id);
        filtertext.setOnClickListener(this);
        categoryrecycle=view.findViewById(R.id.categoryrecycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),  LinearLayoutManager.HORIZONTAL, false);
        categoryrecycle.setLayoutManager(linearLayoutManager);
        categoryrecycle.setItemAnimator(new DefaultItemAnimator());
        categoryrecycle.setNestedScrollingEnabled(false);

        storerecycle=view.findViewById(R.id.followrecycleview);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(),  LinearLayoutManager.VERTICAL, false);
        storerecycle.setLayoutManager(linearLayoutManager2);
        storerecycle.setItemAnimator(new DefaultItemAnimator());
        storerecycle.setNestedScrollingEnabled(false);
        if (getActivity()!=null) {
            if (isNetworkConnected()) {
                presenter.ViewAllCategoryRetailer(idholder);
            } else {
                showAlert("Please connect to internet.", R.style.DialogAnimation);
            }
        }
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(followReceiver,
                new IntentFilter("Follow"));
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(StoreReceiver,
                new IntentFilter("StoreFollow"));
    }
    public BroadcastReceiver followReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            followsatus = intent.getStringExtra("followstatus");
            Catid = intent.getStringExtra("catid");
            if (getActivity()!=null) {
                if (isNetworkConnected()) {
                    presenter.CategoryFollow(idholder, Catid, followsatus);
                } else {
                    showAlert("Please connect to internet.", R.style.DialogAnimation);
                }
            }

        }
    };
    public BroadcastReceiver StoreReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = intent.getStringExtra("followstatus");
            String storeid = intent.getStringExtra("storeid");
            presenter.FollowStore(idholder,storeid,status);
        }
    };
    @Override
    public void onClick(View v) {
        if (v==sortbytext){
            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.sort_dialog_activity);
            dialog.setTitle("Custom Dialog");

            dialog.show();
        }else if (v==filtertext){
            Intent intent = new Intent(getContext(), FilterShowActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void catsuccess(ArrayList<CategoryListModel> response) {
        followCategoryListAdapter = new FollowCategoryListAdapter(getActivity(),response);
        categoryrecycle.setAdapter(followCategoryListAdapter);
    }

    @Override
    public void followsuccess(String response) {
        if (getActivity()!=null) {
            if (isNetworkConnected()) {
                presenter.ViewAllCategoryRetailer(idholder);
            } else {
                showAlert("Please connect to internet.", R.style.DialogAnimation);
            }
        }
    }

    @Override
    public void retsuccess(ArrayList<FollowStoreModel> response) {
        followRetailerListAdapter=new FollowRetailerListAdapter(getContext(),response);
        storerecycle.setAdapter(followRetailerListAdapter);
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
        final PrettyDialog prettyDialog = new PrettyDialog(getContext());
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


}





