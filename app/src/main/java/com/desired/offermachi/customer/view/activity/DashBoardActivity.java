package com.desired.offermachi.customer.view.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.BottomDealsoftheCountPresenter;
import com.desired.offermachi.customer.presenter.NotificationCountPresenter;
import com.desired.offermachi.customer.view.fragment.CustomerSupportFragment;
import com.desired.offermachi.customer.view.fragment.DealsoftheDayFragment;
import com.desired.offermachi.customer.view.fragment.ExclusiveFragment;
import com.desired.offermachi.customer.view.fragment.FavouritesFragment;
import com.desired.offermachi.customer.view.fragment.FeedsFragment;
import com.desired.offermachi.customer.view.fragment.FollowFragment;
import com.desired.offermachi.customer.view.fragment.HelpFragment;
import com.desired.offermachi.customer.view.fragment.HomeFragment;
import com.desired.offermachi.customer.view.fragment.InviteFriendFragment;
import com.desired.offermachi.customer.view.fragment.MycouponsFragment;
import com.desired.offermachi.customer.view.fragment.ProfileFragment;
import com.desired.offermachi.customer.view.fragment.SmartShoppingFragment;
import com.desired.offermachi.customer.view.fragment.StoreFragment;
import com.desired.offermachi.customer.view.fragment.TermConditionFragment;
import com.google.android.gms.common.api.Status;
//import com.google.android.gms.location.places.Place;
//import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

public class DashBoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener,NotificationCountPresenter.NotiUnReadCount, BottomDealsoftheCountPresenter.BottomNotiRead{
    FragmentManager FM;
    FragmentTransaction FT;
    LinearLayout smartshopping,dealsoftheday,coupons,favourites,ifollow;
    ImageView smartshoppingimg,dealsofthedayimg,couponsimg,favouritesimg,ifollowimg;
    TextView smartshoppingtext,dealsofthedaytext,couponstext,favouritestext,ifollowtext;
    Toolbar toolbar;
    DrawerLayout drawer;
    ImageView ivTitleLogo;
    TextView tvMainTitle;
    ImageView info;
    private View navHeader;
    NavigationView navigationView;
    TextView name,email;
    ImageView imageView;
    String ImageHolder,username,useremail;
   User user;
   ImageView btnnotification;
   int count=0;
   ImageView ivLocation;
    ImageView cancle;

    LocationManager locationManager;
   public String lati ;
    public String longi,idholder ;

    TextView tvNotiCount;
    private NotificationCountPresenter notiCount;
//    PlacesClient placesClient;

    TextView BottomtvNotiCountdeal,BottomtvNotiCountcoupons,BottomtvNotiCountfavorites;
    private BottomDealsoftheCountPresenter BottomNotiRead;
    /////BOttomNotificationCount


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
//        getLocation();
         toolbar = findViewById(R.id.toolbar);
        ivTitleLogo=toolbar.findViewById(R.id.logo);
        tvMainTitle=toolbar.findViewById(R.id.tv_title);
        ivLocation=toolbar.findViewById(R.id.location_id);
        tvNotiCount = toolbar.findViewById(R.id.txtMessageCount);
//        notiCount = new NotificationCountPresenter(this,this);


        BottomtvNotiCountdeal = findViewById(R.id.category_txtMessageCount_id);
        BottomtvNotiCountcoupons = findViewById(R.id.coupons_txtMessageCount);
        BottomtvNotiCountfavorites = findViewById(R.id.fav_txtMessageCount);

        notiCount = new NotificationCountPresenter(this,this);

        BottomNotiRead = new BottomDealsoftheCountPresenter(this,this);
//        BottomNotiRead.BottomNotificationUnreadCount(idholder);

        Places.initialize(this,getString(R.string.google_api_key1));
//        placesClient = Places.createClient(this);
        setToolTittle("",1);
        user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
       String pos= UserSharedPrefManager.GetClickNoti(this);
        idholder=user.getId();


        String diffNavi= UserSharedPrefManager.GetClickNotiPos(this);
        if (pos.equals("1")) {


            FM = getSupportFragmentManager();
            FT = FM.beginTransaction();

            switch (diffNavi)
            {
                case "0":
                    FT.replace(R.id.framelayout_id, new HomeFragment()).commit();
                break;

                case "1":
//                    GoDealOfTheDay(1);
                    FT.replace(R.id.framelayout_id, new DealsoftheDayFragment()).commit();
                break;
                case "2":
                    FT.replace(R.id.framelayout_id, new ExclusiveFragment()).commit();
                break;
                default:
                    FT.replace(R.id.framelayout_id, new HomeFragment()).commit();
                    break;
            }
//            FT.replace(R.id.framelayout_id, new DealsoftheDayFragment()).commit();


            UserSharedPrefManager.SaveClickNoti(this,"0","");
        }
else {
            if (user.getSmartShopping().equals("1")) {
                FM = getSupportFragmentManager();
                FT = FM.beginTransaction();
                FT.replace(R.id.framelayout_id, new SmartShoppingFragment()).commit();
            } else if (user.getSmartShopping().equals("0")) {
                FM = getSupportFragmentManager();
                FT = FM.beginTransaction();
                FT.replace(R.id.framelayout_id, new HomeFragment()).commit();
            }
        }
        navigationView = findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        navigationView.getMenu().getItem(1).setActionView(R.layout.menu_image);
        navigationView.getMenu().getItem(2).setVisible(false);
        navigationView.getMenu().getItem(3).setVisible(false);
        navigationView.getMenu().getItem(4).setVisible(false);
        navigationView.getMenu().getItem(5).setVisible(false);

        name = (TextView) navHeader.findViewById(R.id.navname);
        email = (TextView) navHeader.findViewById(R.id.navemail);
        imageView = (ImageView) navHeader.findViewById(R.id.imageView);

        username = user.getUsername();
        useremail = user.getEmail();
        ImageHolder = user.getProfile();
        Log.e("main", "ImageHolder=="+ImageHolder );
        if (username.equals("null")){

        }else{
            name.setText(username);
        }
        if (useremail.equals("null")){

        }else{
            email.setText(useremail);
        }
        if (ImageHolder!= null)
        {
            try {
                Picasso.get().load(ImageHolder).networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_avatar).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        smartshopping=(LinearLayout)findViewById(R.id.homelinear);
        dealsoftheday=(LinearLayout)findViewById(R.id.category_linear_id);
        coupons=(LinearLayout)findViewById(R.id.account_linear_id);
        favourites=(LinearLayout)findViewById(R.id.cart_linear_id);
        ifollow=(LinearLayout)findViewById(R.id.more_linear_id);

        smartshoppingimg=(ImageView)findViewById(R.id.home_img);
        dealsofthedayimg=(ImageView)findViewById(R.id.categoryimg_id);
        couponsimg=(ImageView)findViewById(R.id.account_img);
        favouritesimg=(ImageView)findViewById(R.id.cart_img);
        ifollowimg=(ImageView)findViewById(R.id.more_img);

        smartshoppingtext=(TextView) findViewById(R.id.hometext_id);
        dealsofthedaytext=(TextView)findViewById(R.id.categorytext_id);
        couponstext=(TextView)findViewById(R.id.accountext_id);
        favouritestext=(TextView)findViewById(R.id.carttext_id);
        ifollowtext=(TextView)findViewById(R.id.moretext_id);

        info=(ImageView)findViewById(R.id.info_id);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });

        btnnotification=findViewById(R.id.btnnotification);
        btnnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        ivLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogLocation();
            }
        });


        FloatingActionButton fab = findViewById(R.id.floting_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DashBoardActivity.this, SearchActivity.class);
                startActivity(intent);

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        smartshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user.getSmartShopping().equals("0")){
                    showdialog();
                }

                }
        });

        dealsoftheday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoDealOfTheDay(1);
                 }
        });


        coupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoMyCoupon(1);
            }
        });


        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoFavorite(1);
                }
        });
        ifollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoIfollow(1);
                }
        });


        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        drawer.closeDrawers();
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void GoMyCoupon(int diff) {
        if (user.getSmartShopping().equals("0")){
                smartshoppingimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowonlineshop));
                dealsofthedayimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowdeals));
                couponsimg.setImageDrawable(getResources().getDrawable(R.drawable.whitecoupon));
                favouritesimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfavorite));
                ifollowimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfollow));
                smartshoppingtext.setTextColor(getResources().getColor(R.color.yellow));
                dealsofthedaytext.setTextColor(getResources().getColor(R.color.yellow));
                couponstext.setTextColor(getResources().getColor(R.color.white));
                favouritestext.setTextColor(getResources().getColor(R.color.yellow));
                ifollowtext.setTextColor(getResources().getColor(R.color.yellow));

            switch (diff)
            {
                case 1:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.framelayout_id, new MycouponsFragment())
                            .addToBackStack(null)
                            .commit();
                    break;
                default:
                    break;
            }


                }

    }
    private void GoIfollow(int diff) {
        if (user.getSmartShopping().equals("0")){
                smartshoppingimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowonlineshop));
                dealsofthedayimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowdeals));
                couponsimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowcoupon));
                favouritesimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfavorite));
                ifollowimg.setImageDrawable(getResources().getDrawable(R.drawable.whitefollow));
                smartshoppingtext.setTextColor(getResources().getColor(R.color.yellow));
                dealsofthedaytext.setTextColor(getResources().getColor(R.color.yellow));
                couponstext.setTextColor(getResources().getColor(R.color.yellow));
                favouritestext.setTextColor(getResources().getColor(R.color.yellow));
                ifollowtext.setTextColor(getResources().getColor(R.color.white));
            switch (diff)
            {
                case 1:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.framelayout_id, new FollowFragment())
                            .addToBackStack(null)
                            .commit();
                    break;
                default:
                    break;
            }

                }

    }
    private void GoFavorite(int diff) {
        if (user.getSmartShopping().equals("0")){
            smartshoppingimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowonlineshop));
                dealsofthedayimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowdeals));
                couponsimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowcoupon));
                favouritesimg.setImageDrawable(getResources().getDrawable(R.drawable.whitefavorite));
                ifollowimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfollow));

                smartshoppingtext.setTextColor(getResources().getColor(R.color.yellow));
                dealsofthedaytext.setTextColor(getResources().getColor(R.color.yellow));
                couponstext.setTextColor(getResources().getColor(R.color.yellow));
                favouritestext.setTextColor(getResources().getColor(R.color.white));
                ifollowtext.setTextColor(getResources().getColor(R.color.yellow));

            switch (diff)
            {
                case 1:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.framelayout_id, new FavouritesFragment())
                            .addToBackStack(null)
                            .commit();
                    break;
                default:
                    break;
            }

                }

    }

    private void GoDealOfTheDay(int diff) {

        if (user.getSmartShopping().equals("0")){

            smartshoppingimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowonlineshop));
            dealsofthedayimg.setImageDrawable(getResources().getDrawable(R.drawable.whitedeals));
            couponsimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowcoupon));
            favouritesimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfavorite));
            ifollowimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfollow));
            smartshoppingtext.setTextColor(getResources().getColor(R.color.yellow));
            dealsofthedaytext.setTextColor(getResources().getColor(R.color.white));
            couponstext.setTextColor(getResources().getColor(R.color.yellow));
            favouritestext.setTextColor(getResources().getColor(R.color.yellow));
            ifollowtext.setTextColor(getResources().getColor(R.color.yellow));
            switch (diff)
            {
                case 1:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.framelayout_id, new DealsoftheDayFragment())
                            .addToBackStack(null)
                            .commit();
                    break;
                default:
                    break;
            }

        }
    }

    private int dpToPx(Context applicationContext, int i) {
        return 0;
    }

    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (FM.getBackStackEntryCount() > 0) {
                FM.popBackStack();
                if(FM.getBackStackEntryCount() ==1)
                {
                    setToolTittle("",1);
                }
            }
            else
            {
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to exit?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                     UserSharedPrefManager.SaveStoreFilter(DashBoardActivity.this,"");//only for not show privious selected category
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                                startActivity(intent);
                                finish();
                                System.exit(0);
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        Fragment fragment = null;
       // Class fragmentClass;
        switch(menuItem.getItemId()) {

            case R.id.nav_home:
                if (user.getSmartShopping().equals("0")){
                    UserSharedPrefManager.SaveStoreFilter(this,"");
                    fragment = new HomeFragment();
                    drawer.closeDrawer(GravityCompat.START);
                }
                else{
                    Toast.makeText(this, "You cannot access when Smart Shopping is ON", Toast.LENGTH_SHORT).show();
                    drawer.closeDrawer(GravityCompat.START);
//                    fragment = new SmartShoppingFragment();
                }

                break;
                case R.id.nav_account:
                    if (count==0){
                        navigationView.getMenu().getItem(2).setVisible(true);
                        navigationView.getMenu().getItem(3).setVisible(true);
                        navigationView.getMenu().getItem(4).setVisible(true);
                        navigationView.getMenu().getItem(5).setVisible(true);
                        count=1;
                    }else{
                        navigationView.getMenu().getItem(2).setVisible(false);
                        navigationView.getMenu().getItem(3).setVisible(false);
                        navigationView.getMenu().getItem(4).setVisible(false);
                        navigationView.getMenu().getItem(5).setVisible(false);

                        count=0;
                    }
              break;

            case R.id.nav_profile:

                if (user.getSmartShopping().equals("0")){
                    UserSharedPrefManager.SaveStoreFilter(this,"");
                      fragment = new ProfileFragment();
                     drawer.closeDrawer(GravityCompat.START);
                  }
                else{
                    Toast.makeText(this, "You cannot access when Smart Shopping is ON", Toast.LENGTH_SHORT).show();
                    drawer.closeDrawer(GravityCompat.START);
//                    fragment = new SmartShoppingFragment();
                }
                    break;

            case R.id.nav_feeds:
                if (user.getSmartShopping().equals("0")){
                    UserSharedPrefManager.SaveStoreFilter(this,"");
                    fragment = new FeedsFragment();
                }
                else{
                    Toast.makeText(this, "You cannot access when Smart Shopping is ON", Toast.LENGTH_SHORT).show();
                    drawer.closeDrawer(GravityCompat.START);
//                    fragment = new SmartShoppingFragment();
                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }

                break;
            case R.id.nav_smartshopping:

                if (user.getSmartShopping().equals("0")){
                    UserSharedPrefManager.SaveStoreFilter(this,"");
                    showdialog();
                    drawer.closeDrawer(GravityCompat.START);
                }
              /*
                if (user.getSmartShopping().equals("0")){
                    fragment = new HomeFragment();
                }
                else{
                    fragment = new SmartShoppingFragment();
                }*/

                break;
            case  R.id.nav_store:
                if (user.getSmartShopping().equals("0")){
                    UserSharedPrefManager.SaveStoreFilter(this,"");
                    fragment = new StoreFragment();
                    drawer.closeDrawer(GravityCompat.START);
                }
                else{
                    Toast.makeText(this, "You cannot access when Smart Shopping is ON", Toast.LENGTH_SHORT).show();
                    drawer.closeDrawer(GravityCompat.START);
//                    fragment = new SmartShoppingFragment();
                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }

                break;
            case R.id.nav_dealsday:
                if (user.getSmartShopping().equals("0")){
                    UserSharedPrefManager.SaveStoreFilter(this,"");
                    fragment = new DealsoftheDayFragment();
                    GoDealOfTheDay(0);
                    drawer.closeDrawer(GravityCompat.START);
                }
                else{
                    Toast.makeText(this, "You cannot access when Smart Shopping is ON", Toast.LENGTH_SHORT).show();
                    drawer.closeDrawer(GravityCompat.START);
//                    fragment = new SmartShoppingFragment();
                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }

                break;
            case  R.id.nav_exclusive:
                if (user.getSmartShopping().equals("0")){
                    UserSharedPrefManager.SaveStoreFilter(this,"");
                    fragment = new ExclusiveFragment();
                    drawer.closeDrawer(GravityCompat.START);

                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }

                break;
            case R.id.nav_mycoupons:
                if (user.getSmartShopping().equals("0")){
                    UserSharedPrefManager.SaveStoreFilter(this,"");
                    fragment = new MycouponsFragment();
                    GoMyCoupon(0);
                    drawer.closeDrawer(GravityCompat.START);
                }
                else{
                    Toast.makeText(this, "You cannot access when Smart Shopping is ON", Toast.LENGTH_SHORT).show();
                    drawer.closeDrawer(GravityCompat.START);
                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }

                break;
            case R.id.nav_follow:
                if (user.getSmartShopping().equals("0")){
                    UserSharedPrefManager.SaveStoreFilter(this,"");
                    fragment = new FollowFragment();
                    GoIfollow(0);
                    drawer.closeDrawer(GravityCompat.START);
                }
                else{
                    Toast.makeText(this, "You cannot access when Smart Shopping is ON", Toast.LENGTH_SHORT).show();
                    drawer.closeDrawer(GravityCompat.START);
//                    fragment = new SmartShoppingFragment();
                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }

                break;
            case R.id.nav_fav:
                if (user.getSmartShopping().equals("0")){
                    UserSharedPrefManager.SaveStoreFilter(this,"");
                    fragment = new FavouritesFragment();
                    GoFavorite(0);
                    drawer.closeDrawer(GravityCompat.START);
                }
                else{
                    Toast.makeText(this, "You cannot access when Smart Shopping is ON", Toast.LENGTH_SHORT).show();
                    drawer.closeDrawer(GravityCompat.START);
                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }

                break;
            case R.id.nav_rate:
                if (user.getSmartShopping().equals("0")){
                    fragment = new HomeFragment();
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    // To count with Play market backstack, After pressing back button,
                    // to taken back to our application, we need to add following flags to intent.
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                            Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    try {
                        startActivity(goToMarket);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                    }
                    drawer.closeDrawer(GravityCompat.START);
                }
                else{
                    Toast.makeText(this, "You cannot access when Smart Shopping is ON", Toast.LENGTH_SHORT).show();
                    drawer.closeDrawer(GravityCompat.START);
//                    fragment = new SmartShoppingFragment();
                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }

                break;
            case R.id.nav_invite:
                if (user.getSmartShopping().equals("0")){
                    fragment = new InviteFriendFragment();  drawer.closeDrawer(GravityCompat.START);

                }
                else{
                    Toast.makeText(this, "You cannot access when Smart Shopping is ON", Toast.LENGTH_SHORT).show();
                    drawer.closeDrawer(GravityCompat.START);
//                    fragment = new SmartShoppingFragment();
                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }

                break;

            case R.id.nav_help:
                if (user.getSmartShopping().equals("0")){
                    fragment = new HelpFragment();
                    drawer.closeDrawer(GravityCompat.START);

                }
                else{
                    Toast.makeText(this, "You cannot access when Smart Shopping is ON", Toast.LENGTH_SHORT).show();
                    drawer.closeDrawer(GravityCompat.START);
//                    fragment = new SmartShoppingFragment();
                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }

                break;

            case R.id.nav_customer:
                if (user.getSmartShopping().equals("0")){
                    fragment = new CustomerSupportFragment();
                    drawer.closeDrawer(GravityCompat.START);

                }
                else{
                    Toast.makeText(this, "You cannot access when Smart Shopping is ON", Toast.LENGTH_SHORT).show();
                    drawer.closeDrawer(GravityCompat.START);
//                    fragment = new SmartShoppingFragment();
                }
//                else{
//                    fragment = new SmartShoppingFragment();
//                }

                break;

            case R.id.nav_notification:
//                fragment = new HomeFragment();
                if (user.getSmartShopping().equals("0")) {
                    startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                    drawer.closeDrawer(GravityCompat.START);
                }
                else{
                    Toast.makeText(this, "You cannot access when Smart Shopping is ON", Toast.LENGTH_SHORT).show();
                    drawer.closeDrawer(GravityCompat.START);
//                    fragment = new SmartShoppingFragment();
                }
                break;
            case R.id.nav_terms:
                if (user.getSmartShopping().equals("0")) {
                    fragment = new TermConditionFragment();
                    drawer.closeDrawer(GravityCompat.START);
                }
                else{
                    Toast.makeText(this, "You cannot access when Smart Shopping is ON", Toast.LENGTH_SHORT).show();
                    drawer.closeDrawer(GravityCompat.START);
//                    fragment = new SmartShoppingFragment();
                }
                break;
            case R.id.nav_logout:
                if (user.getSmartShopping().equals("0")) {
                    fragment = new HomeFragment();
                    UserSharedPrefManager.getInstance(getApplicationContext()).logout();
                }else{
                    fragment = new SmartShoppingFragment();
                    UserSharedPrefManager.getInstance(getApplicationContext()).logout();
                }
//             finish();
                break;
                default:
                    fragment = new HomeFragment();

        }

       /* try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

       if(fragment==null)//only for not refresh
       {
           DrawerLayout drawer = findViewById(R.id.drawer_layout);
//           drawer.closeDrawer(GravityCompat.START);
       }
       else {
           // Insert the fragment by replacing any existing fragment
           FragmentManager fragmentManager = getSupportFragmentManager();
           fragmentManager.beginTransaction().replace(R.id.framelayout_id, fragment).commit();
           // Highlight the selected item has been done by NavigationView
           menuItem.setChecked(true);
           // Set action bar title
           setTitle(menuItem.getTitle());
           // Close the navigation drawer
           DrawerLayout drawer = findViewById(R.id.drawer_layout);
           drawer.closeDrawer(GravityCompat.START);
       }
        return true;
    }
    public  void setToolTittle(String tittle,int diff)
    {
        switch (diff)
        {
            case 1:
                ivTitleLogo.setVisibility(View.VISIBLE);
                tvMainTitle.setVisibility(View.GONE);

                break;
            case 2:
                ivTitleLogo.setVisibility(View.GONE);
                tvMainTitle.setVisibility(View.VISIBLE);
                break;
            case 3:
//                toolbar.
                ivTitleLogo.setVisibility(View.GONE);
                tvMainTitle.setVisibility(View.VISIBLE);
                break;

        }
        tvMainTitle.setText(tittle);
    }

    private void showdialog(){

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.switch_layout);
        dialog.setTitle("Smart shopping Dialog");
        Switch Switch =(Switch) dialog.findViewById(R.id.smartswitch);
        Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {




                // Toast.makeText(DashBoardActivity.this, "smart shoping", Toast.LENGTH_SHORT).show();
                smartshoppingimg.setImageDrawable(getResources().getDrawable(R.drawable.whiteonlineshop));
                dealsofthedayimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowdeals));
                couponsimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowcoupon));
                favouritesimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfavorite));
                ifollowimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfollow));

                smartshoppingtext.setTextColor(getResources().getColor(R.color.white));
                dealsofthedaytext.setTextColor(getResources().getColor(R.color.yellow));
                couponstext.setTextColor(getResources().getColor(R.color.yellow));
                favouritestext.setTextColor(getResources().getColor(R.color.yellow));
                ifollowtext.setTextColor(getResources().getColor(R.color.yellow));


                if (user.getSmartShopping().equals("1")) {

                    info.setVisibility(View.INVISIBLE);
                    btnnotification.setVisibility(View.INVISIBLE);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.framelayout_id, new SmartShoppingFragment())
                            .addToBackStack(null)
                            .commit();
                } else {
                    info.setVisibility(View.VISIBLE);
                    btnnotification.setVisibility(View.VISIBLE);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.framelayout_id, new HomeFragment())
                            .addToBackStack(null)
                            .commit();
                }

                String Nameholder,EmailHolder,PhoneHolder,AddressHolder,GenderHolder,ImageHolder,Dobholder,SmartShoppingHolder,SoundHolder,idholder;
                User user = UserSharedPrefManager.getInstance(DashBoardActivity.this).getCustomer();
                idholder= user.getId();

                Nameholder= user.getUsername();
                EmailHolder=user.getEmail();
                PhoneHolder= user.getMobile();
                AddressHolder= user.getAddress();
                GenderHolder= user.getGender();
                Dobholder=user.getDob();
                ImageHolder=user.getProfile();
                SmartShoppingHolder=user.getSmartShopping();
                SoundHolder=user.getNotificationsound();


                if (isChecked){
                    User user1 = new User(
                            idholder,
                            Nameholder,
                            EmailHolder,
                            PhoneHolder,
                            AddressHolder,
                            GenderHolder,
                            Dobholder,
                            ImageHolder,
                            "1",
                            SoundHolder,
                            "1"//shyam 11/9/19
                    );
                    UserSharedPrefManager.getInstance(DashBoardActivity.this).userLogin(user1);

                    startActivity(new Intent(DashBoardActivity.this,DashBoardActivity.class));
                    finish();

                }
                dialog.dismiss();

            }

        });

        dialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (user.getSmartShopping().equals("1")) {
            info.setVisibility(View.INVISIBLE);
            btnnotification.setVisibility(View.INVISIBLE);

            smartshoppingimg.setImageDrawable(getResources().getDrawable(R.drawable.whiteonlineshop));
            dealsofthedayimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowdeals));
            couponsimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowcoupon));
            favouritesimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfavorite));
            ifollowimg.setImageDrawable(getResources().getDrawable(R.drawable.yellowfollow));

            smartshoppingtext.setTextColor(getResources().getColor(R.color.white));
            dealsofthedaytext.setTextColor(getResources().getColor(R.color.yellow));
            couponstext.setTextColor(getResources().getColor(R.color.yellow));
            favouritestext.setTextColor(getResources().getColor(R.color.yellow));
            ifollowtext.setTextColor(getResources().getColor(R.color.yellow));
        }
        else
        {
            info.setVisibility(View.GONE);
            btnnotification.setVisibility(View.VISIBLE);
        }
        notiCount.NotificationUnreadCount(idholder);
        BottomNotiRead.BottomNotificationUnreadCount(idholder);




    }
     Dialog DlgLoc=null;
    TextView tvCurloc;
    LinearLayout lyCurrLocation;
    public void DialogLocation()
    {
        if(DlgLoc!=null)
        {
            DlgLoc.dismiss();
            DlgLoc=null;
        }
        DlgLoc = new Dialog(this);
        DlgLoc.setContentView(R.layout.dialog_select_location);
        DlgLoc.setTitle("Select your Location");


        RelativeLayout rlCurrent=(RelativeLayout)DlgLoc.findViewById(R.id.rl_current_location);

        RelativeLayout rlOther=(RelativeLayout)DlgLoc.findViewById(R.id.rl_other);
        ImageView cancle=(ImageView)DlgLoc.findViewById(R.id.cancle_img_id);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this,DashBoardActivity.class));
                finish();
            }
        });

        lyCurrLocation=(LinearLayout)DlgLoc.findViewById(R.id.ly_cur_loc);
        Button btOkay=(Button)DlgLoc.findViewById(R.id.bt_okay);
         tvCurloc=(TextView) DlgLoc.findViewById(R.id.tv_cur_loc);

        rlCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFlag=true;
                getLocation();
//                tvCurloc.setText(sCurrentLocation);
//                lyCurrLocation.setVisibility(View.VISIBLE);
            }

        });
        rlOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DlgLoc.dismiss();
                startAutocompleteActivity();

            }

        });
        btOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFlag=true;
                DlgLoc.dismiss();

            }

        });

        DlgLoc.show();

    }

//for current location
void getLocation() {
    try {
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, this);
    }
    catch(SecurityException e) {
        e.printStackTrace();
    }
}

String sCurrentLocation="";
    boolean checkFlag=false;
    @Override
    public void onLocationChanged(Location location) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String address = addresses.get(0).getSubLocality();
        String city = addresses.get(0).getLocality();
        sCurrentLocation=address + ", " + city;
//        tvLocation.setText(address + ", " + city);
        lati= String.valueOf(location.getLatitude());
        longi= String.valueOf(location.getLongitude());
//        Toast.makeText(this, "lati= "+lati+" longi= "+longi, Toast.LENGTH_SHORT).show();
        if(checkFlag) {
            if(tvCurloc!=null) {

                tvCurloc.setText(sCurrentLocation);
                lyCurrLocation.setVisibility(View.VISIBLE);
            }
            RefreshLocWithHomeView();
           /* UserSharedPrefManager.SaveCurrentLatLongAndLocNm(this,lati,longi,sCurrentLocation);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.framelayout_id, new HomeFragment()).commit();
            // Highlight the selected item has been done by NavigationView
//            menuItem.setChecked(true);
            // Set action bar title
//            setTitle(menuItem.getTitle());
            // Close the navigation drawer
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            checkFlag=false;*/
        }
    }
    public void RefreshLocWithHomeView()
    {
        UserSharedPrefManager.SaveCurrentLatLongAndLocNm(this,lati,longi,sCurrentLocation);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.framelayout_id, new HomeFragment()).commit();
        // Highlight the selected item has been done by NavigationView
//            menuItem.setChecked(true);
        // Set action bar title
//            setTitle(menuItem.getTitle());
        // Close the navigation drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        checkFlag=false;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    private void startAutocompleteActivity() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.RATING, Place.Field.ADDRESS,
                Place.Field.TYPES, Place.Field.OPENING_HOURS, Place.Field.USER_RATINGS_TOTAL,
                Place.Field.LAT_LNG, Place.Field.PLUS_CODE, Place.Field.WEBSITE_URI);
//        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        this.startActivityForResult(intent, 103);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 100) {
//            if (resultCode == RESULT_OK) {
//                RequestOptions requestOptions = new RequestOptions()
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .error(R.drawable.persion)
//                        .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())))
//                        .skipMemoryCache(true);
//
//                Glide.with(getActivity()).load(appData.getProfile())
//                        .thumbnail(0.5f)
//                        .apply(requestOptions)
//                        .into(imgProfile);
//            }
//        } else if (requestCode == 101 && resultCode == 0) {
//            LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                Toast toast = Toast.makeText(getContext(), "Please turn on GPS.", Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivityForResult(intent, 100);
//            } else {
//                initLocation();
//            }
//        } else if (requestCode == 102) {
//            if (resultCode == RESULT_OK) {
//                presenter.getVendor(latitude, longitude);
//            }
//        } else

            if (requestCode == 103) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                String addressname="";
                if(TextUtils.isEmpty(place.getAddress())||place.getAddress().equals("null")||place.getAddress()=="null")
                {
                    addressname=place.getName();
//                    addressname="";
                }
                else
                {
                    addressname=place.getAddress()+","+place.getName();
                }


//                edtSearchLocation.setText(addressname);
//                tvLocation.setText(addressname);
                LatLng latLng = place.getLatLng();
              String  latitude = "" + latLng.latitude;
                String longitude = "" + latLng.longitude;

                lati = "" + latLng.latitude;
                longi = "" + latLng.longitude;
                sCurrentLocation = addressname;
                Toast.makeText(this, ""+addressname+" latitude= "+latitude+" longitude= "+longitude, Toast.LENGTH_SHORT).show();
                RefreshLocWithHomeView();
//                if (isNetworkConnected()) {
//                    presenter.getVendor(latitude, longitude);
//                } else {
//                    showDialog("Please connect to internet.");
//                }
              /*  Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    Address obj = addresses.get(0);
                    String add = obj.getSubLocality();
                   // edtSearchLocation.setText(add);
                    //tvLocation.setText(add);
                } catch (IOException e) {
                    e.printStackTrace();
                    showDialog(e.getMessage());
                }*/
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                showDialog(status.getStatusMessage());
            }
        }
    }
    private void showDialog(String message) {
        new android.support.v7.app.AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                }).show();
    }
//notification count
    @Override
    public void successnoti(String response) {
        if(TextUtils.isEmpty(response))
        {
            tvNotiCount.setText("0");
        }
        else {
//            tvNotiCount.setText(push_notifications_count);
            tvNotiCount.setText(response);
//            Log.e("","count= "+tvNotiCount);
        }
    }

    @Override
    public void errornoti(String response) {

    }

    @Override
    public void failnoti(String response) {

    }

    @Override
    public void successbottomnoti(String response,String deal,String coupon,String fav) {


        if(TextUtils.isEmpty(deal))
        {
            BottomtvNotiCountdeal.setText("0");
        }
        else if(TextUtils.isEmpty(coupon))
        {
            BottomtvNotiCountcoupons.setText("0");
        }
        else  if(TextUtils.isEmpty(fav))
        {

            BottomtvNotiCountfavorites.setText("0");

        }
        else {
            BottomtvNotiCountdeal.setText(deal);
            BottomtvNotiCountcoupons.setText(coupon);
            BottomtvNotiCountfavorites.setText(fav);
        }
    }

    @Override
    public void errorbottomnoti(String response) {

    }

    @Override
    public void failbottomnoti(String response) {













    }
  /*  @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationManager manager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
                    if (!manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, 101);
                    } else {
                        initLocation();
                    }
                } else {
                    new android.support.v7.app.AlertDialog.Builder(this)
                            .setTitle("")
                            .setMessage("Please allow permission")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                                }
                            }).show();
                }
            }
        }
    }*/
}
