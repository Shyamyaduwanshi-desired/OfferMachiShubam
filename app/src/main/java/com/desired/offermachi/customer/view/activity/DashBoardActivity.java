package com.desired.offermachi.customer.view.activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.User;
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
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


public class DashBoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

         toolbar = findViewById(R.id.toolbar);
        ivTitleLogo=toolbar.findViewById(R.id.logo);
        tvMainTitle=toolbar.findViewById(R.id.tv_title);

        setToolTittle("",1);
        user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
        if (user.getSmartShopping().equals("1")){
            FM = getSupportFragmentManager();
            FT = FM.beginTransaction();
            FT.replace(R.id.framelayout_id, new SmartShoppingFragment()).commit();
        }else if (user.getSmartShopping().equals("0")){
            FM = getSupportFragmentManager();
            FT = FM.beginTransaction();
            FT.replace(R.id.framelayout_id, new HomeFragment()).commit();
        }

        navigationView = findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
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
        if (ImageHolder!= null){
            Picasso.get().load(ImageHolder).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_avatar).into(imageView);
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
                if (user.getSmartShopping().equals("1")){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.framelayout_id, new SmartShoppingFragment())
                            .addToBackStack(null)
                            .commit();
                }
                }
        });

        dealsoftheday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
if (user.getSmartShopping().equals("0")){
    getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.framelayout_id, new DealsoftheDayFragment())
            .addToBackStack(null)
            .commit();
}
                 }
        });


        coupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                if (user.getSmartShopping().equals("0")){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.framelayout_id, new MycouponsFragment())
                            .addToBackStack(null)
                            .commit();
                }

            }
        });


        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                if (user.getSmartShopping().equals("0")){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.framelayout_id, new FavouritesFragment())
                            .addToBackStack(null)
                            .commit();
                }
                }
        });
        ifollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                if (user.getSmartShopping().equals("0")){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.framelayout_id, new FollowFragment())
                            .addToBackStack(null)
                            .commit();
                }
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

    private int dpToPx(Context applicationContext, int i) {
        return 0;
    }

    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();

           // FragmentManager fm = getSupportFragmentManager();
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
                    fragment = new HomeFragment();
                }else{
                    fragment = new SmartShoppingFragment();
                }
                //toolbar.setTitle("");
                break;
                case R.id.nav_profile:
                    if (user.getSmartShopping().equals("0")){
                        fragment = new ProfileFragment();
                    }else{
                        fragment = new SmartShoppingFragment();
                    }


                   // toolbar.setTitle("My Profile");
                break;
            case R.id.nav_feeds:
                if (user.getSmartShopping().equals("0")){
                    fragment = new FeedsFragment();
                }else{
                    fragment = new SmartShoppingFragment();
                }

                break;
            case R.id.nav_smartshopping:
                if (user.getSmartShopping().equals("0")){
                    fragment = new HomeFragment();
                }else{
                    fragment = new SmartShoppingFragment();
                }

                break;
            case  R.id.nav_store:
                if (user.getSmartShopping().equals("0")){
                    fragment = new StoreFragment();
                }else{
                    fragment = new SmartShoppingFragment();
                }

                break;
            case R.id.nav_dealsday:
                if (user.getSmartShopping().equals("0")){
                    fragment = new DealsoftheDayFragment();
                }else{
                    fragment = new SmartShoppingFragment();
                }

                break;
            case  R.id.nav_exclusive:
                if (user.getSmartShopping().equals("0")){
                    fragment = new ExclusiveFragment();
                }else{
                    fragment = new SmartShoppingFragment();
                }

                break;
            case R.id.nav_mycoupons:
                if (user.getSmartShopping().equals("0")){
                    fragment = new MycouponsFragment();
                }else{
                    fragment = new SmartShoppingFragment();
                }

                break;
            case R.id.nav_follow:
                if (user.getSmartShopping().equals("0")){
                    fragment = new FollowFragment();
                }else{
                    fragment = new SmartShoppingFragment();
                }

                break;
            case R.id.nav_fav:
                if (user.getSmartShopping().equals("0")){
                    fragment = new FavouritesFragment();
                }else{
                    fragment = new SmartShoppingFragment();
                }

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
                }else{
                    fragment = new SmartShoppingFragment();
                }

                break;
            case R.id.nav_invite:
                if (user.getSmartShopping().equals("0")){
                    fragment = new InviteFriendFragment();
                }else{
                    fragment = new SmartShoppingFragment();
                }

                break;

            case R.id.nav_help:
                if (user.getSmartShopping().equals("0")){
                    fragment = new HelpFragment();
                }else{
                    fragment = new SmartShoppingFragment();
                }

                break;

            case R.id.nav_customer:
                if (user.getSmartShopping().equals("0")){
                    fragment = new CustomerSupportFragment();
                }else{
                    fragment = new SmartShoppingFragment();
                }

                break;

            case R.id.nav_notification:
                fragment = new HomeFragment();
                startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                break;
            case R.id.nav_terms:
                fragment = new TermConditionFragment();
                break;
            case R.id.nav_logout:
                if (user.getSmartShopping().equals("0")) {
                    fragment = new HomeFragment();
                    UserSharedPrefManager.getInstance(getApplicationContext()).logout();
                }else{
                    fragment = new SmartShoppingFragment();
                    UserSharedPrefManager.getInstance(getApplicationContext()).logout();
                }

                break;
                default:
                    fragment = new HomeFragment();

        }

       /* try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

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
}
