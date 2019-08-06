package com.desired.offermachi.customer.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.fragment.CustomerSupportFragment;
import com.desired.offermachi.customer.fragment.DealsoftheDayFragment;
import com.desired.offermachi.customer.fragment.FavouritesFragment;
import com.desired.offermachi.customer.fragment.FeedsFragment;
import com.desired.offermachi.customer.fragment.FollowFragment;
import com.desired.offermachi.customer.fragment.HelpFragment;
import com.desired.offermachi.customer.fragment.HomeFragment;
import com.desired.offermachi.customer.fragment.InviteFriendFragment;
import com.desired.offermachi.customer.fragment.MycouponsFragment;
import com.desired.offermachi.customer.fragment.NotificationFragment;
import com.desired.offermachi.customer.fragment.ProfileFragment;
import com.desired.offermachi.customer.fragment.SmartShoppingFragment;
import com.desired.offermachi.customer.fragment.StoreFragment;
import com.desired.offermachi.customer.fragment.TermConditionFragment;


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

         toolbar = findViewById(R.id.toolbar);
        ivTitleLogo=toolbar.findViewById(R.id.logo);
        tvMainTitle=toolbar.findViewById(R.id.tv_title);

        setToolTittle("",1);
        FM = getSupportFragmentManager();
        FT = FM.beginTransaction();
        FT.replace(R.id.framelayout_id, new HomeFragment()).commit();

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

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.framelayout_id, new SmartShoppingFragment())
                        .addToBackStack(null)
                        .commit(); }
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

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.framelayout_id, new DealsoftheDayFragment())
                        .addToBackStack(null)
                        .commit(); }
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

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.framelayout_id, new MycouponsFragment())
                        .addToBackStack(null)
                        .commit(); }
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

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.framelayout_id, new FavouritesFragment())
                        .addToBackStack(null)
                        .commit(); }
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


                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.framelayout_id, new FollowFragment())
                        .addToBackStack(null)
                        .commit(); }
        });


        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();

            FragmentManager fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
                if(fm.getBackStackEntryCount() ==1)
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
                                finish();
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
        Class fragmentClass;
        switch(menuItem.getItemId()) {

            case R.id.nav_home:
                fragmentClass = HomeFragment.class;
                toolbar.setTitle("");
                break;
                case R.id.nav_profile:
                fragmentClass = ProfileFragment.class;

                   // toolbar.setTitle("My Profile");
                break;
            case R.id.nav_feeds:
                fragmentClass = FeedsFragment.class;
                break;
            case R.id.nav_smartshopping:
                fragmentClass = SmartShoppingFragment.class;
                break;
            case  R.id.nav_store:
                fragmentClass = StoreFragment.class;
                break;
            case R.id.nav_dealsday:
                fragmentClass = DealsoftheDayFragment.class;
                break;

            case R.id.nav_mycoupons:
                fragmentClass = MycouponsFragment.class;
                break;
            case R.id.nav_follow:
                fragmentClass = FollowFragment.class;
                break;
            case R.id.nav_fav:
                fragmentClass = FavouritesFragment.class;
                break;
            case R.id.nav_rate:
                fragmentClass = MycouponsFragment.class;
                break;
            case R.id.nav_invite:
                fragmentClass = InviteFriendFragment.class;
                break;

            case R.id.nav_help:
                fragmentClass = HelpFragment.class;
                break;

            case R.id.nav_customer:
                fragmentClass = CustomerSupportFragment.class;
                break;

            case R.id.nav_notification:
                fragmentClass = NotificationFragment.class;
                break;

            case R.id.nav_terms:
                fragmentClass = TermConditionFragment.class;
                break;
            case R.id.nav_logout:
                fragmentClass = MycouponsFragment.class;
                break;
                default:
                fragmentClass = MycouponsFragment.class;

        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
