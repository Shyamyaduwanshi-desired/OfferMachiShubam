package com.desired.offermachi.retalier.ui;

import android.content.Intent;
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

import com.desired.offermachi.R;

import com.desired.offermachi.retalier.fragment.ReatalierHomeFragment;

public class RetalierDashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager FM;
    FragmentTransaction FT;
    int count=0;
    int countBACK=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retalier_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FM = getSupportFragmentManager();
        FT = FM.beginTransaction();
        FT.replace(R.id.screenarearetalier, new ReatalierHomeFragment()).commit();



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(1).setActionView(R.layout.custom_navigation_icon_activity);
        navigationView.getMenu().getItem(4).setActionView(R.layout.custom_navigation_icon_activity);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.home_nav) {

            startActivity(new Intent(getApplicationContext(), RetalierDashboard.class));

        } else if (id == R.id.nav_profile) {

            startActivity(new Intent(getApplicationContext(), RetalierProfileActivity.class));

        } else if (id == R.id.nav_offer) {

            startActivity(new Intent(getApplicationContext(), RetalierViewOfferActivity.class));

        }else if (id == R.id.dealsday) {

            startActivity(new Intent(getApplicationContext(), RetalierDealsOftheDayActivity.class));

        } else if (id == R.id.nav_pushoffer) {

            startActivity(new Intent(getApplicationContext(), RetalierPushActivity.class));

        } else if (id == R.id.nav_redeemcode) {

            startActivity(new Intent(getApplicationContext(), RetalierQrActivity.class));

        }else if (id == R.id.nav_statistics) {

            startActivity(new Intent(getApplicationContext(), RetalierStatisticsActivity.class));

        } else if (id == R.id.nav_notification) {

            startActivity(new Intent(getApplicationContext(), RetalierNotificationActivity.class));

        } else if (id == R.id.nav_rateapp) {

//            startActivity(new Intent(getApplicationContext(), FollowActivity.class));

        } else if (id == R.id.nav_invite) {

            startActivity(new Intent(getApplicationContext(), RetalierInviteFriendActivity.class));

        }else if (id == R.id.nav_help) {

            startActivity(new Intent(getApplicationContext(), RetalierHelpActivity.class));

        }else if (id == R.id.nav_customer) {

            startActivity(new Intent(getApplicationContext(), RetalierCustomerSupportActivity.class));

        }else if (id == R.id.nav_terms) {

            startActivity(new Intent(getApplicationContext(), RetalierTermsConditionActivity.class));

        }else if (id == R.id.nav_logout) {


        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
