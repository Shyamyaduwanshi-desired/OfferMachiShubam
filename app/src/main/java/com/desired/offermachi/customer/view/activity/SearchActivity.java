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
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.TrendingListPresenter;
import com.desired.offermachi.customer.view.adapter.CustomerTrendingAdapter;
import com.desired.offermachi.customer.view.adapter.CustomerTrendingAdapterNew;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class SearchActivity extends AppCompatActivity implements TrendingListPresenter.TrendingList, SearchView.OnQueryTextListener {

    ImageView cancle;
    RecyclerView categoryrecycle;
    private CustomerTrendingAdapterNew customerTrendingAdapter;
//    private CustomerTrendingAdapter customerTrendingAdapter;
    private TrendingListPresenter presenter;
    String idholder;
    android.widget.SearchView searchView;
    private ArrayList<SelectCategoryModel> selectCategoryModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        cancle=(ImageView)findViewById(R.id.cancle_img_id);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initview();
    }

    private void initview() {
        User user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
        idholder = user.getId();
        selectCategoryModelList=new ArrayList<>();
        presenter = new TrendingListPresenter(SearchActivity.this, SearchActivity.this);
        categoryrecycle = findViewById(R.id.categoryrecycleview);
//        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
//        categoryrecycle.setLayoutManager(gridLayoutManager1);
//        categoryrecycle.setItemAnimator(new DefaultItemAnimator());
//        categoryrecycle.setNestedScrollingEnabled(false);

        categoryrecycle.setHasFixedSize(true);
        categoryrecycle.setLayoutManager(new LinearLayoutManager(this));
        categoryrecycle.setItemAnimator(new DefaultItemAnimator());
        categoryrecycle.setNestedScrollingEnabled(false);


        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);
        if (isNetworkConnected()) {
            presenter.ViewAllTrending(idholder);
        } else {
            showAlert("Please connect to internet.", R.style.DialogAnimation);
        }
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(locationReceiver,
                new IntentFilter("Favourite"));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(CouponReceiver,
                new IntentFilter("Refresh"));
    }
    public BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String fav = intent.getStringExtra("fav");
            String offerid = intent.getStringExtra("offerid");
            presenter.AddFavourite(idholder,offerid,fav);
        }
    };
    public BroadcastReceiver CouponReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent2) {
            if (isNetworkConnected()) {
                presenter.ViewAllTrending(idholder);
            } else {
                showAlert("Please connect to internet.", R.style.DialogAnimation);
            }

        }
    };
    @Override
    public void success(ArrayList<SelectCategoryModel> response) {

//        customerTrendingAdapter = new CustomerTrendingAdapter(SearchActivity.this, response);
        customerTrendingAdapter = new CustomerTrendingAdapterNew(SearchActivity.this, response);
        categoryrecycle.setAdapter(customerTrendingAdapter);

        for (SelectCategoryModel onsale : response) {
            selectCategoryModelList.add(onsale);
        }
    }

    @Override
    public void favsuccess(String response) {
        if (isNetworkConnected()) {
            presenter.ViewAllTrending(idholder);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<SelectCategoryModel> product = new ArrayList<>();
        for (SelectCategoryModel onsale : selectCategoryModelList) {
            String productname = onsale.getOffername().toLowerCase().replace(" ", "");
            String brandname = onsale.getOfferbrandname().toLowerCase().replace(" ", "");
            if (productname.contains(newText) || brandname.contains(newText))
                product.add(onsale);
        }
        customerTrendingAdapter.setfilter(product);
        return true;
    }
}