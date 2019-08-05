package com.desired.offermachi.retalier.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.SubMenu;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

import com.desired.offermachi.R;

import com.desired.offermachi.customer.ui.DashBoardActivity;
import com.desired.offermachi.retalier.fragment.ReatalierHomeFragment;

public class RetalierDashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager FM;
    FragmentTransaction FT;
    protected ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
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


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_profile) {

            startActivity(new Intent(getApplicationContext(), RetalierProfileActivity.class));

        } else if (id == R.id.navigation_sub_item_1) {

            startActivity(new Intent(getApplicationContext(), RetalierDashboard.class));

        }
        else if (id == R.id.navigation_sub_item_2) {
            startActivity(new Intent(getApplicationContext(), RetalierViewOfferDiscount.class));

        }else if (id == R.id.dealsday) {
            startActivity(new Intent(getApplicationContext(), RetalierDealsOftheDayActivity.class));

        } else if (id == R.id.nav_pushoffer) {

            startActivity(new Intent(getApplicationContext(), RetalierPushActivity.class));

        } else if (id == R.id.nav_qrcode) {

        }else if (id == R.id.nav_couponcode) {

        } else if (id == R.id.nav_statistics) {

            startActivity(new Intent(getApplicationContext(), RetalierStatisticsActivity.class));

        } else if (id == R.id.nav_notification) {

            startActivity(new Intent(getApplicationContext(), RetalierNotificationActivity.class));

        } else if (id == R.id.nav_rateapp) {


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
