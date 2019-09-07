package com.desired.offermachi.retalier.view.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.desired.offermachi.R;

import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.presenter.RetailerNotificationPresenter;
import com.desired.offermachi.retalier.view.fragment.ReatalierHomeFragment;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class RetalierDashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    FragmentManager FM;
    FragmentTransaction FT;
    protected ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    DrawerLayout drawer;
    private View navHeader;
    NavigationView navigationView;
    int count=0;
    TextView name,email;
    ImageView imageView;
    String ImageHolder,username,useremail;
    ImageView imgNotiBell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retalier_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FM = getSupportFragmentManager();
        FT = FM.beginTransaction();
        FT.replace(R.id.screenarearetalier, new ReatalierHomeFragment()).commit();

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        navigationView.getMenu().getItem(1).setActionView(R.layout.menu_image);
        navigationView.getMenu().getItem(2).setVisible(false);
        navigationView.getMenu().getItem(3).setVisible(false);
        navigationView.getMenu().getItem(6).setActionView(R.layout.menu_image);
        navigationView.getMenu().getItem(7).setVisible(false);
        navigationView.getMenu().getItem(8).setVisible(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        name = (TextView) navHeader.findViewById(R.id.navname);
        email = (TextView) navHeader.findViewById(R.id.navemail);
        imageView = (ImageView) navHeader.findViewById(R.id.imageView);
        UserModel user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        username = user.getUsername();
        useremail = user.getEmail();
        ImageHolder = user.getProfile();
        name.setText(username);
        email.setText(useremail);
        if (ImageHolder!= null){
            Picasso.get().load(ImageHolder).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_avatar).into(imageView);
        }
        imgNotiBell=findViewById(R.id.imgNotiBell);
        imgNotiBell.setOnClickListener(this);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_profile) {
            drawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), RetalierProfileActivity.class));

        } else if (id == R.id.nav_offer){
            if (count==0){
                navigationView.getMenu().getItem(2).setVisible(true);
                navigationView.getMenu().getItem(3).setVisible(true);
                count=1;
            }else{
                navigationView.getMenu().getItem(2).setVisible(false);
                navigationView.getMenu().getItem(3).setVisible(false);
                count=0;
            }


        }
        else if (id == R.id.navigation_sub_item_1) {
            drawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), RetalierDashboard.class));

        }
        else if (id == R.id.navigation_sub_item_2) {
            drawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), RetalierViewOfferDiscount.class));

        }else if (id == R.id.dealsday) {
            drawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), RetalierDealsOftheDayActivity.class));

        } else if (id == R.id.nav_pushoffer) {
            drawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), RetalierPushActivity.class));

        } else if (id == R.id.nav_redeemcode) {
            if (count==0){
                navigationView.getMenu().getItem(7).setVisible(true);
                navigationView.getMenu().getItem(8).setVisible(true);
                count=1;
            }else{
                navigationView.getMenu().getItem(7).setVisible(false);
                navigationView.getMenu().getItem(8).setVisible(false);
                count=0;
            }

        }
        else if (id == R.id.nav_qrcode) {
            drawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), MobileVerifyActivity.class));

        }else if (id == R.id.nav_couponcode) {
            drawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), VerifyCouponcodeActivity.class));


        } else if (id == R.id.nav_statistics) {
            drawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), RetalierStatisticsActivity.class));

        } else if (id == R.id.nav_notification) {
            drawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), RetalierNotificationActivity.class));

        } else if (id == R.id.nav_rateapp) {
            drawer.closeDrawer(GravityCompat.START);
            Uri uri = Uri.parse("market://details?id=" +getPackageName());
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
                        Uri.parse("http://play.google.com/store/apps/details?id=" +getPackageName())));
            }


        } else if (id == R.id.nav_invite) {
            drawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), RetalierInviteFriendActivity.class));

        }else if (id == R.id.nav_help) {
            drawer.closeDrawer(GravityCompat.START);

            startActivity(new Intent(getApplicationContext(), RetalierHelpActivity.class));

        }else if (id == R.id.nav_customer) {
            drawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), RetalierCustomerSupportActivity.class));

        }else if (id == R.id.nav_terms) {
            drawer.closeDrawer(GravityCompat.START);
          /*  Intent viewIntent =
                    new Intent("android.intent.action.VIEW",
                            Uri.parse("http://offermachi.in/pages/terms_condition"));
            startActivity(viewIntent);*/
            startActivity(new Intent(getApplicationContext(), RetalierTermsConditionActivity.class));

        }else if (id == R.id.nav_logout) {
            drawer.closeDrawer(GravityCompat.START);
            SharedPrefManagerLogin.getInstance(getApplicationContext()).logout();

        }
        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);*/
      /*  drawer.closeDrawer(GravityCompat.START);*/
        return true;
    }


    @Override
    public void onClick(View v) {
        if (v==imgNotiBell){
            startActivity(new Intent(getApplicationContext(), RetalierNotificationActivity.class));
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
        super.onBackPressed();
    }
}
