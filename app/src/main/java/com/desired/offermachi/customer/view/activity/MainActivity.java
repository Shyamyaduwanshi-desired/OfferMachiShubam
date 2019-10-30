package com.desired.offermachi.customer.view.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.model.slider_viewpager_model;
import com.desired.offermachi.customer.presenter.ViewStoreOfferPresenter;
import com.desired.offermachi.customer.view.adapter.CustomerTrendingAdapterNew;
import com.desired.offermachi.customer.view.adapter.slider_viewpages_adaper;

import java.util.ArrayList;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class MainActivity extends AppCompatActivity implements  ViewStoreOfferPresenter.ViewOffer {
    RecyclerView categoryrecycle;
    private CustomerTrendingAdapterNew customerTrendingAdapter;
    private ViewStoreOfferPresenter presenters;
    String idholder, storeid;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    ArrayList<slider_viewpager_model> arrayList = new ArrayList<>();
    slider_viewpages_adaper slider_viewpages_adaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_offer_follow_activity_second_activity);
        initview();
    }

    private void initview() {
        User user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
        idholder = user.getId();
        Intent intent = getIntent();
        storeid = intent.getStringExtra("storeid");
        presenters = new ViewStoreOfferPresenter(MainActivity.this, MainActivity.this);
        categoryrecycle = findViewById(R.id.categoryrecycleview);
//        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
//        categoryrecycle.setLayoutManager(gridLayoutManager1);
        categoryrecycle.setHasFixedSize(true);
        categoryrecycle.setLayoutManager(new LinearLayoutManager(this));
        categoryrecycle.setItemAnimator(new DefaultItemAnimator());
        categoryrecycle.setNestedScrollingEnabled(false);
        TextView textview = findViewById(R.id.timmer_id);

        textview.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 final Dialog dialog = new Dialog(MainActivity.this);
                 dialog.setContentView(R.layout.sort_dialog_activity);
                 dialog.setTitle("Custom Dialog");
                 RadioButton rdone=(RadioButton) dialog.findViewById(R.id.rdone);
                 dialog.show();

             }
         });

        if (isNetworkConnected()) {
            presenters.ViewAllStoreOffer(idholder, storeid);
        } else {
            showAlert("Please connect to internet.", R.style.DialogAnimation);
        }
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(locationReceiver,
                new IntentFilter("Favourite"));


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);


        slider_viewpages_adaper slider_viewpages_adaper = new slider_viewpages_adaper(this, arrayList);

        viewPager.setAdapter(slider_viewpages_adaper);
        viewPager.setCurrentItem(0);
        slider_viewpages_adaper.setTimer(viewPager,3);
        dotscount = slider_viewpages_adaper.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.slider_non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.slider_active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.slider_non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.slider_active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }

    public BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String fav = intent.getStringExtra("fav");
            String offerid = intent.getStringExtra("offerid");
            presenters.AddFavourite(idholder, offerid, fav);
        }
    };

    @Override
    public void success(ArrayList<SelectCategoryModel> response) {
//        customerTrendingAdapter=new CustomerTrendingAdapter(ViewStoreOfferActivity.this,response);
        customerTrendingAdapter = new CustomerTrendingAdapterNew(MainActivity.this, response, "");
        categoryrecycle.setAdapter(customerTrendingAdapter);
    }

    @Override
    public void favsuccess(String response) {
        if (isNetworkConnected()) {
            presenters.ViewAllStoreOffer(idholder, storeid);
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

}









































//{
//
//
//    ViewPager viewPager;
//    LinearLayout sliderDotspanel;
//    private int dotscount;
//    private ImageView[] dots;
//    ArrayList<slider_viewpager_model> arrayList = new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.view_all_offer_follow_activity_second_activity);
//
//        viewPager = (ViewPager) findViewById(R.id.viewPager);
//        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
//
//        arrayList.add(new slider_viewpager_model(R.drawable.store));
//        arrayList.add(new slider_viewpager_model(R.drawable.splash));
//        arrayList.add(new slider_viewpager_model(R.drawable.pizza));
//        arrayList.add(new slider_viewpager_model(R.drawable.splash));
//
//        slider_viewpages_adaper viewPagerAdapter = new slider_viewpages_adaper(this, arrayList);
//
//        viewPager.setAdapter(viewPagerAdapter);
//        viewPager.setCurrentItem(0);
//        viewPagerAdapter.setTimer(viewPager,3);
//        dotscount = viewPagerAdapter.getCount();
//        dots = new ImageView[dotscount];
//
//        for (int i = 0; i < dotscount; i++) {
//
//            dots[i] = new ImageView(this);
//            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.slider_non_active_dot));
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//            params.setMargins(8, 0, 8, 0);
//
//            sliderDotspanel.addView(dots[i], params);
//        }
//
//        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.slider_active_dot));
//
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                for (int i = 0; i < dotscount; i++) {
//                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.slider_non_active_dot));
//                }
//
//                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.slider_active_dot));
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//    }
//}
//
//
//
//
//

