package com.desired.offermachi.retalier.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.constant.TabLayoutUtils;
import com.desired.offermachi.retalier.view.fragment.RegistrationProfileDetailsFragment;
import com.desired.offermachi.retalier.view.fragment.RegistrationStoreDetailsFrgment;
import com.desired.offermachi.retalier.view.adapter.RetalierTabLayoutAdapter;

public class RetalierRegistration extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    public static TabLayout tabLayout;
    public static ViewPager viewPager1;

    RetalierTabLayoutAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_registration_activity);
//        if (SharedPrefManagerLogin.getInstance(this).isLoggedIn()) {
//            finish();
//            startActivity(new Intent(this, RetalierDashboard.class));
//            return;
//        }
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);

        viewPager1 = (ViewPager) findViewById(R.id.pager);
        tabLayout.setOnTabSelectedListener(RetalierRegistration.this);

        TabLayoutUtils.enableTabs( tabLayout, false );

        viewPager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setScrollPosition(position, 0f, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        Pager adapter = new Pager(RetalierRegistration.this.getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager1.setAdapter(adapter);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        viewPager1.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    public class Pager extends FragmentStatePagerAdapter {

        //integer to count number of tabs
        int tabCount;

        //Constructor to the class
        public Pager(FragmentManager fm, int tabCount) {
            super(fm);
            //Initializing tab count
            this.tabCount = tabCount;
        }

        //Overriding method getItem
        @Override
        public Fragment getItem(int position) {
            //Returning the current tabs
            switch (position) {
                case 0:
                    RegistrationProfileDetailsFragment tab1 = new RegistrationProfileDetailsFragment();
                    return tab1;
                case 1:
                    RegistrationStoreDetailsFrgment tab2 = new RegistrationStoreDetailsFrgment();
                    return tab2;
                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            return tabCount;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        startActivity(new Intent(getApplicationContext(), RetalierLogin.class));
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}


