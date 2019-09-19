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
import com.desired.offermachi.customer.model.SearchBean;
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.SearchPresenter;
import com.desired.offermachi.customer.presenter.TrendingListPresenter;
import com.desired.offermachi.customer.view.adapter.CustomerTrendingAdapter;
import com.desired.offermachi.customer.view.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class ActSearchNew extends AppCompatActivity implements TrendingListPresenter.TrendingList, SearchView.OnQueryTextListener, SearchPresenter.SearchListInfo {

    ImageView cancle;
    RecyclerView categoryrecycle;
    private CustomerTrendingAdapter customerTrendingAdapter;
    private TrendingListPresenter presenter;
    String idholder;
    SearchView searchView;
    private SearchPresenter searchPresenter;
    private ArrayList<SelectCategoryModel> selectCategoryModelList;
    private AutoCompleteTextView actv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search_new);
        searchPresenter = new SearchPresenter(this, this);
        GetIntentData();
        initview();
        Listener();
        CallAPI(1);

    }

    private void CallAPI(int i) {
        if (isNetworkConnected()) {
            switch (i)
            {
                case 1:
                    searchPresenter.GetSearchList("", "");//for all searchable list data
                    break;
               case 2:
                   Log.e("","sSearchCatId= "+sSearchCatId+" sSearType= "+sSearType);
                   presenter.SearchAllTrending(idholder,sSearchCatId,sSearType);

                    break;
            }
        } else {
            showAlert("Please connect to internet.", R.style.DialogAnimation);
        }
    }


    private void Listener() {
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
//                        FindMoreData(adapter.getItem(position));
//                        Toast.makeText(ActSearchNew.this, ""+adapter.getItem(position), Toast.LENGTH_SHORT).show();
                        FindMoreData(adapter.getItem(position));
                    }
                });
    }

    String sSearchCatId,sSearchNm,sSearType;
    private void GetIntentData() {
        sSearchCatId=getIntent().getStringExtra("cat_id");
        sSearchNm=getIntent().getStringExtra("cat_name");
        sSearType=getIntent().getStringExtra("cat_type");
        Log.e("","sSearchCatId= "+sSearchCatId+" sSearchNm= "+sSearchNm+" sSearType= "+sSearType);
    }


    ArrayAdapter<String> adapter;
    ArrayList<String>arrayAutoCompleteText=new ArrayList<>();
public void SetAutoAdapter()
{
    try {
        adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,arrayAutoCompleteText);
        actv.setAdapter(adapter);

    } catch (Exception e) {
        e.printStackTrace();
    }

}
    private void initview() {
        actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        cancle=(ImageView)findViewById(R.id.cancle_img_id);
        actv.setThreshold(1);

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
//        if (isNetworkConnected()) {
//            presenter.SearchAllTrending(idholder);
//        } else {
//            showAlert("Please connect to internet.", R.style.DialogAnimation);
//        }
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
          /*  if (isNetworkConnected()) {
                presenter.ViewAllTrending(idholder);
            } else {
                showAlert("Please connect to internet.", R.style.DialogAnimation);
            }*/
          CallAPI(2);

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
        CallAPI(2);
       /* if (isNetworkConnected()) {
            presenter.ViewAllTrending(idholder);
        } else {
            showAlert("Please connect to internet.", R.style.DialogAnimation);
        }*/
    }

    @Override
    public void error(String response) {
        showAlert(response, R.style.DialogAnimation);
//        CallAPI(2);
    }

    @Override
    public void fail(String response) {
        showAlert(response, R.style.DialogAnimation);
//        CallAPI(2);
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
    ArrayList<SearchBean> AllSearchData=new ArrayList<>();
    @Override
    public void successSearch(ArrayList<SearchBean> responseByStore, ArrayList<SearchBean> responseByOffer, ArrayList<SearchBean> responseByLocation, ArrayList<String> response, String status) {
        AllSearchData.clear();

        for (SearchBean bean : responseByStore)
        {
            AllSearchData.add(bean);
        }
        for (SearchBean bean : responseByOffer)
        {
            AllSearchData.add(bean);
        }
        for (SearchBean bean : responseByLocation)
        {
            AllSearchData.add(bean);
        }

        CallAPI(2);

        arrayAutoCompleteText.clear();
        arrayAutoCompleteText=response;
        SetAutoAdapter();
    }

    private void FindMoreData(String name) {

//        String sFindCatId=""/*,catName=""*/,sType="";

        try {
            Log.e("","name= "+name);

            for(int i=0;i<AllSearchData.size();i++)
            {
                if(AllSearchData.get(i).getName().equals(name))
                {
                    sSearchCatId=AllSearchData.get(i).getId();
                    sSearType=AllSearchData.get(i).getType();
                    sSearchNm=AllSearchData.get(i).getName();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        CallAPI(2);
//        Intent intent = new Intent(getActivity(), ActSearchNew.class);
//        intent.putExtra("cat_id",sFindCatId);
//        intent.putExtra("cat_name",name);
//        intent.putExtra("cat_type",sType);
//        startActivity(intent);
//        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

}