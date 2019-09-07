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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.ExclusiveOffersPresenter;
import com.desired.offermachi.customer.presenter.MyCouponPresenter;
import com.desired.offermachi.customer.view.activity.DashBoardActivity;
import com.desired.offermachi.customer.view.activity.FilterShowActivity;
import com.desired.offermachi.customer.view.adapter.CustomerTrendingAdapter;

import java.util.ArrayList;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExclusiveFragment extends Fragment implements View.OnClickListener, ExclusiveOffersPresenter.ExclusiveList {

    View view;
    RecyclerView categoryrecycle;
    private CustomerTrendingAdapter customerTrendingAdapter;
    private ExclusiveOffersPresenter presenter;
    String idholder;
    TextView sortbytext,filtertext;
    public ExclusiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_exclusive, container, false);
        ((DashBoardActivity)getActivity()).setToolTittle("Exclusive Offers",2);
        initview();
        return  view;
    }
    private void initview(){
        presenter = new ExclusiveOffersPresenter(getActivity(),ExclusiveFragment.this);
        User user = UserSharedPrefManager.getInstance(getActivity()).getCustomer();
        idholder= user.getId();
        sortbytext=view.findViewById(R.id.sortby_text_id);
        sortbytext.setOnClickListener(this);
        filtertext=view.findViewById(R.id.filter_text_id);
        filtertext.setOnClickListener(this);
        categoryrecycle=view.findViewById(R.id.categoryrecycleview);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        categoryrecycle.setLayoutManager(gridLayoutManager1);
        categoryrecycle.setItemAnimator(new DefaultItemAnimator());
        categoryrecycle.setNestedScrollingEnabled(false);
        if (getActivity()!=null) {
            if (isNetworkConnected()) {
                presenter.ViewAllExclusive(idholder);
            } else {
                showAlert("Please connect to internet.", R.style.DialogAnimation);
            }
        }
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(locationReceiver,
                new IntentFilter("Favourite"));
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(CouponReceiver,
                new IntentFilter("Refresh"));
    }
    public BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String fav = intent.getStringExtra("fav");
            String offerid = intent.getStringExtra("offerid");
            presenter.Favourite(idholder,offerid,fav);
        }
    };
    public BroadcastReceiver CouponReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (getActivity()!=null) {
                if (isNetworkConnected()) {
                    presenter.ViewAllExclusive(idholder);
                } else {
                    showAlert("Please connect to internet.", R.style.DialogAnimation);
                }
            }
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
    public void success(ArrayList<SelectCategoryModel> response) {
        customerTrendingAdapter=new CustomerTrendingAdapter(getContext(),response);
        categoryrecycle.setAdapter(customerTrendingAdapter);
    }

    @Override
    public void favsuc(String response) {
        //Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();
        if (getActivity()!=null) {
            if (isNetworkConnected()) {
                presenter.ViewAllExclusive(idholder);
            } else {
                showAlert("Please connect to internet.", R.style.DialogAnimation);
            }
        }
    }

    @Override
    public void getsuc(String response) {
        if (getActivity()!=null) {
            if (isNetworkConnected()) {
                presenter.ViewAllExclusive(idholder);
            } else {
                showAlert("Please connect to internet.", R.style.DialogAnimation);
            }
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



