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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.TrendingListPresenter;
import com.desired.offermachi.customer.view.adapter.CustomerTrendingAdapter;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class ActSearchNew extends AppCompatActivity implements TrendingListPresenter.TrendingList, SearchView.OnQueryTextListener {

    ImageView cancle;
    RecyclerView categoryrecycle;
    private CustomerTrendingAdapter customerTrendingAdapter;
    private TrendingListPresenter presenter;
    String idholder;
    SearchView searchView;
    private ArrayList<SelectCategoryModel> selectCategoryModelList;
    private AutoCompleteTextView actv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search_new);
        actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        cancle=(ImageView)findViewById(R.id.cancle_img_id);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initview();
        setAutoCompleteAdpt();
        GetIntentData();
    }
String sSearchCatId,sSearchNm;
    private void GetIntentData() {
        sSearchCatId=getIntent().getStringExtra("cat_id");
        sSearchNm=getIntent().getStringExtra("cat_name");
        Log.e("","sSearchCatId= "+sSearchCatId+" sSearchNm= "+sSearchNm);
    }


    ArrayAdapter<String> adapter;
public void setAutoCompleteAdpt()
{

    String[] countries = getResources().getStringArray(R.array.list_of_location);
    adapter = new ArrayAdapter<String>
            (this,android.R.layout.simple_list_item_1,countries);
    actv.setAdapter(adapter);

    actv.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
//                    selectedText.setText(autoSuggestAdapter.getObject(position));
                    Toast.makeText(ActSearchNew.this, ""+adapter.getItem(position), Toast.LENGTH_SHORT).show();
                }
            });
}
    private void initview() {
        User user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
        idholder = user.getId();
        selectCategoryModelList=new ArrayList<>();
        presenter = new TrendingListPresenter(ActSearchNew.this, ActSearchNew.this);
        categoryrecycle = findViewById(R.id.categoryrecycleview);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        categoryrecycle.setLayoutManager(gridLayoutManager1);
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
        customerTrendingAdapter = new CustomerTrendingAdapter(ActSearchNew.this, response);
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