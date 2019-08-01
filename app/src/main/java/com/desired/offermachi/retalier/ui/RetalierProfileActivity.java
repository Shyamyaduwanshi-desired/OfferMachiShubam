package com.desired.offermachi.retalier.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.fragment.ProfileStoreDetailsFragment;
import com.desired.offermachi.retalier.fragment.RetalierPersonalDetailsStoreFragment;
import com.desired.offermachi.retalier.retalieradapter.RetalierTabLayoutAdapter;

public class RetalierProfileActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    public TabLayout tabLayout;
    RetalierTabLayoutAdapter adapter;
    private ViewPager viewPager1;
    ImageView imageViewback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_my_profile_activity);


        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager1 = (ViewPager) findViewById(R.id.pager);
        tabLayout.setOnTabSelectedListener(RetalierProfileActivity.this);
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

        RetalierProfileActivity.Pager adapter = new RetalierProfileActivity.Pager(RetalierProfileActivity.this.getSupportFragmentManager(), tabLayout.getTabCount());

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
                    ProfileStoreDetailsFragment tab1 = new ProfileStoreDetailsFragment();
                    return tab1;
                case 1:
                    RetalierPersonalDetailsStoreFragment tab2 = new RetalierPersonalDetailsStoreFragment();
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
}


