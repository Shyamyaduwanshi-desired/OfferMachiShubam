package com.desired.offermachi.customer.view.fragment;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.customer.model.StoreModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.GetCouponPresenter;
import com.desired.offermachi.customer.presenter.HomePresenter;
import com.desired.offermachi.customer.view.activity.DashBoardActivity;
import com.desired.offermachi.customer.view.activity.ViewOfferTrendingActivity;
import com.desired.offermachi.customer.view.activity.ViewStoreOfferActivity;
import com.desired.offermachi.customer.view.adapter.CustomerStoreAdapter;
import com.desired.offermachi.customer.view.adapter.CustomerTrendingAdapter;
import com.desired.offermachi.customer.view.activity.CategoryActivity;
import com.desired.offermachi.customer.view.activity.StoreCouponCodeActivity;
import com.desired.offermachi.customer.view.activity.ViewOfferActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class HomeFragment extends Fragment implements View.OnClickListener, HomePresenter.HomeList, CompoundButton.OnCheckedChangeListener {

    View view;
    RecyclerView categoryrecycle,trendingrecycle,storerecycle;
    private CustomerTrendingAdapter customerTrendingAdapter;
    private CustomerStoreAdapter customerStoreAdapter;
    private HomePresenter presenter;

    TextView trendingviewall,storeviewall,categoryid,couponviewall;
    String catid,catname,catofferimage,idholder;
    ImageView bannerimage;
    Switch smartswitch;

    String Nameholder,EmailHolder,PhoneHolder,AddressHolder,GenderHolder,ImageHolder,SmartShoppingHolder;
    public HomeFragment() {

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);
        ((DashBoardActivity)getActivity()).setToolTittle("",1);
        initview();
        return view;
    }
    private void initview(){
        User user = UserSharedPrefManager.getInstance(getContext()).getCustomer();
        idholder= user.getId();

      Nameholder= user.getUsername();
       EmailHolder=user.getEmail();
        PhoneHolder= user.getMobile();
        AddressHolder= user.getAddress();
        GenderHolder= user.getGender();
       ImageHolder=user.getProfile();
       SmartShoppingHolder=user.getSmartShopping();

        presenter = new HomePresenter(getActivity(), HomeFragment.this);
        Intent intent=getActivity().getIntent();
        catid=intent.getStringExtra("catid");
        catname=intent.getStringExtra("catname");
        catofferimage=intent.getStringExtra("catofferimage");
        TextView trendingdeals=(TextView)view.findViewById(R.id.dealsoftheday_text_id);
        Typeface content2= ResourcesCompat.getFont(getContext(), R.font.ralewaybold);
        trendingdeals.setTypeface(content2);
        TextView trendings=(TextView)view.findViewById(R.id.trendingdeals_id);
        Typeface content3= ResourcesCompat.getFont(getContext(), R.font.ralewaybold);
        trendings.setTypeface(content3);
        smartswitch=view.findViewById(R.id.smartswitch);
        smartswitch.setOnCheckedChangeListener(this);
        couponviewall=view.findViewById(R.id.couponviewall);
        couponviewall.setOnClickListener(this);
        trendingviewall =view.findViewById(R.id.trendingviewall);
        trendingviewall.setOnClickListener(this);
        storeviewall =view.findViewById(R.id.storeviewall);
        storeviewall.setOnClickListener(this);
        categoryid =view.findViewById(R.id.selectcategoryname);
        categoryid.setText(catname);
        categoryid.setOnClickListener(this);
        //categoryrecyle
        categoryrecycle=view.findViewById(R.id.categoryrecycleview);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        categoryrecycle.setLayoutManager(gridLayoutManager1);
        categoryrecycle.setItemAnimator(new DefaultItemAnimator());
        categoryrecycle.setNestedScrollingEnabled(false);
        //trendingrecycle
        trendingrecycle=view.findViewById(R.id.trendingrecycleview);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        trendingrecycle.setLayoutManager(gridLayoutManager2);
        trendingrecycle.setItemAnimator(new DefaultItemAnimator());
        trendingrecycle.setNestedScrollingEnabled(false);
        //storerecycle
        storerecycle=view.findViewById(R.id.storerecycleview);
        storerecycle.setLayoutManager((new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)));
        storerecycle.setItemAnimator(new DefaultItemAnimator());
        storerecycle.setNestedScrollingEnabled(false);
        bannerimage=view.findViewById(R.id.bannerimage);
        if(catofferimage.equals("")){
        }else{
            Picasso.get().load(catofferimage).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(bannerimage);
        }
        if (getActivity()!=null) {
            if (isNetworkConnected()) {
                presenter.GetAllList(catid, idholder);
            } else {
                showAlert("Please connect to internet.", R.style.DialogAnimation);
            }
        }
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(locationReceiver,
                new IntentFilter("Favourite"));
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(StoreReceiver,
                new IntentFilter("StoreFollow"));
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(CouponReceiver,
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

    public BroadcastReceiver StoreReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = intent.getStringExtra("followstatus");
            String storeid = intent.getStringExtra("storeid");
            presenter.AddStoreFollow(idholder,storeid,status);
        }
    };
    public BroadcastReceiver CouponReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent2) {
            if (getActivity()!=null) {
                if (isNetworkConnected()) {
                    presenter.GetAllList(catid, idholder);
                } else {
                    showAlert("Please connect to internet.", R.style.DialogAnimation);
                }
            }

        }
    };

    @Override
    public void onClick(View v) {
        if (v==couponviewall){
            Intent intent = new Intent(getActivity(), ViewOfferActivity.class);
            intent.putExtra("catid",catid);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
        else if (v==trendingviewall){
            Intent intent = new Intent(getActivity(), ViewOfferTrendingActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }else if (v==storeviewall){
            Intent intent = new Intent(getActivity(), StoreCouponCodeActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }else if (v==categoryid){
            Intent intent = new Intent(getActivity(), CategoryActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
    }

    @Override
    public void categorysuccess(ArrayList<SelectCategoryModel> response) {
        customerTrendingAdapter= new CustomerTrendingAdapter(getContext(),response);
        categoryrecycle.setAdapter(customerTrendingAdapter);
    }

    @Override
    public void Offersuccess(ArrayList<SelectCategoryModel> response) {
        customerTrendingAdapter= new CustomerTrendingAdapter(getContext(),response);
        trendingrecycle.setAdapter(customerTrendingAdapter);
    }

    @Override
    public void Storesuccess(ArrayList<StoreModel> response) {
        customerStoreAdapter=new CustomerStoreAdapter(getContext(),response);
        storerecycle.setAdapter(customerStoreAdapter);
    }

    @Override
    public void success(String response) {
        if (getActivity()!=null) {
            if (isNetworkConnected()) {
                presenter.GetAllList(catid, idholder);
            } else {
                showAlert("Please connect to internet.", R.style.DialogAnimation);
            }
        }
        //Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
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
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
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
                    ImageHolder,
                    "1"
            );
            UserSharedPrefManager.getInstance(getActivity()).userLogin(user);
          /*  Fragment fragment = new SmartShoppingFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.framelayout_id, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();*/
          startActivity(new Intent(getContext(),DashBoardActivity.class));
        }else{
            User user = new User(
                    idholder,
                    Nameholder,
                    EmailHolder,
                    PhoneHolder,
                    AddressHolder,
                    GenderHolder,
                    ImageHolder,
                    "0"
            );
            UserSharedPrefManager.getInstance(getActivity()).userLogin(user);
           /* Fragment fragment = new HomeFragment();
            FragmentManager fragmentManager = getChildFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.framelayout_id, fragment).commit();*/
        }
    }

  /*  @Override
    public void couponsuccess(String response) {
       *//* Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();*//*
        Log.e("home", "response========== "+response );
    }

    @Override
    public void couponerror(String response) {
        showAlert(response, R.style.DialogAnimation);
    }

    @Override
    public void couponfail(String response) {
        showAlert(response, R.style.DialogAnimation);
    }*/
}