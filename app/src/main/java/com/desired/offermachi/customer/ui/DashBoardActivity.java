package com.desired.offermachi.customer.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
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
import com.desired.offermachi.customer.fragment.TermConditionFragment;


public class DashBoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager FM;
    FragmentTransaction FT;
    LinearLayout home,category,account,cart,more;
    ImageView homeimg,categoryimg,accountimage,cartimg,moreimg;
    TextView hometext,categorytext,accounttext,carttext,moretext;
    Toolbar toolbar;
    DrawerLayout drawer;
    ImageView ivTitleLogo;
    TextView tvMainTitle;
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

        home=(LinearLayout)findViewById(R.id.homelinear);
        category=(LinearLayout)findViewById(R.id.category_linear_id);
        account=(LinearLayout)findViewById(R.id.account_linear_id);
        cart=(LinearLayout)findViewById(R.id.cart_linear_id);
        more=(LinearLayout)findViewById(R.id.more_linear_id);


        homeimg=(ImageView)findViewById(R.id.home_img);
        categoryimg=(ImageView)findViewById(R.id.categoryimg_id);
        accountimage=(ImageView)findViewById(R.id.account_img);
        cartimg=(ImageView)findViewById(R.id.cart_img);
        moreimg=(ImageView)findViewById(R.id.more_img);


        hometext=(TextView) findViewById(R.id.hometext_id);
        categorytext=(TextView)findViewById(R.id.categorytext_id);
        accounttext=(TextView)findViewById(R.id.accountext_id);
        carttext=(TextView)findViewById(R.id.carttext_id);
        moretext=(TextView)findViewById(R.id.moretext_id);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homeimg.setImageDrawable(getResources().getDrawable(R.drawable.onlineshoppurple));
                categoryimg.setImageDrawable(getResources().getDrawable(R.drawable.deals));
                accountimage.setImageDrawable(getResources().getDrawable(R.drawable.coupon));
                cartimg.setImageDrawable(getResources().getDrawable(R.drawable.favorite));
                moreimg.setImageDrawable(getResources().getDrawable(R.drawable.follow));


                hometext.setTextColor(getResources().getColor(R.color.purple));
                categorytext.setTextColor(getResources().getColor(R.color.black));
                accounttext.setTextColor(getResources().getColor(R.color.black));
                carttext.setTextColor(getResources().getColor(R.color.black));
                moretext.setTextColor(getResources().getColor(R.color.black));

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.framelayout_id, new SmartShoppingFragment())
                        .addToBackStack(null)
                        .commit(); }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homeimg.setImageDrawable(getResources().getDrawable(R.drawable.onlineshop));
                categoryimg.setImageDrawable(getResources().getDrawable(R.drawable.dealspurple));
                accountimage.setImageDrawable(getResources().getDrawable(R.drawable.coupon));
                cartimg.setImageDrawable(getResources().getDrawable(R.drawable.favorite));
                moreimg.setImageDrawable(getResources().getDrawable(R.drawable.follow));


                hometext.setTextColor(getResources().getColor(R.color.black));
                categorytext.setTextColor(getResources().getColor(R.color.purple));
                accounttext.setTextColor(getResources().getColor(R.color.black));
                carttext.setTextColor(getResources().getColor(R.color.black));
                moretext.setTextColor(getResources().getColor(R.color.black));

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.framelayout_id, new DealsoftheDayFragment())
                        .addToBackStack(null)
                        .commit(); }
        });


        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homeimg.setImageDrawable(getResources().getDrawable(R.drawable.onlineshop));
                categoryimg.setImageDrawable(getResources().getDrawable(R.drawable.deals));
                accountimage.setImageDrawable(getResources().getDrawable(R.drawable.couponpurple));
                cartimg.setImageDrawable(getResources().getDrawable(R.drawable.favorite));
                moreimg.setImageDrawable(getResources().getDrawable(R.drawable.follow));


                hometext.setTextColor(getResources().getColor(R.color.black));
                categorytext.setTextColor(getResources().getColor(R.color.black));
                accounttext.setTextColor(getResources().getColor(R.color.purple));
                carttext.setTextColor(getResources().getColor(R.color.black));
                moretext.setTextColor(getResources().getColor(R.color.black));

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.framelayout_id, new MycouponsFragment())
                        .addToBackStack(null)
                        .commit(); }
        });


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homeimg.setImageDrawable(getResources().getDrawable(R.drawable.onlineshop));
                categoryimg.setImageDrawable(getResources().getDrawable(R.drawable.deals));
                accountimage.setImageDrawable(getResources().getDrawable(R.drawable.coupon));
                cartimg.setImageDrawable(getResources().getDrawable(R.drawable.favoritepurple));
                moreimg.setImageDrawable(getResources().getDrawable(R.drawable.follow));

                hometext.setTextColor(getResources().getColor(R.color.black));
                categorytext.setTextColor(getResources().getColor(R.color.black));
                accounttext.setTextColor(getResources().getColor(R.color.black));
                carttext.setTextColor(getResources().getColor(R.color.purple));
                moretext.setTextColor(getResources().getColor(R.color.black));

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.framelayout_id, new FavouritesFragment())
                        .addToBackStack(null)
                        .commit(); }
        });
           more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homeimg.setImageDrawable(getResources().getDrawable(R.drawable.onlineshop));
                categoryimg.setImageDrawable(getResources().getDrawable(R.drawable.deals));
                accountimage.setImageDrawable(getResources().getDrawable(R.drawable.coupon));
                cartimg.setImageDrawable(getResources().getDrawable(R.drawable.favorite));
                moreimg.setImageDrawable(getResources().getDrawable(R.drawable.followpurple));

                hometext.setTextColor(getResources().getColor(R.color.black));
                categorytext.setTextColor(getResources().getColor(R.color.black));
                accounttext.setTextColor(getResources().getColor(R.color.black));
                carttext.setTextColor(getResources().getColor(R.color.black));
                moretext.setTextColor(getResources().getColor(R.color.purple));


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
                break;
                case R.id.nav_profile:
                fragmentClass = ProfileFragment.class;
                break;
            case R.id.nav_feeds:
                fragmentClass = FeedsFragment.class;
                break;
            case R.id.nav_smartshopping:
                fragmentClass = SmartShoppingFragment.class;
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
