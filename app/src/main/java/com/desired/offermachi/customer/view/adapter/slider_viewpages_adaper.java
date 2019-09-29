package com.desired.offermachi.customer.view.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.model.slider_viewpager_model;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class slider_viewpages_adaper extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    final Handler handler = new Handler();
    public Timer swipeTimer;

    ArrayList<slider_viewpager_model> arrayList = new ArrayList<>();

    public slider_viewpages_adaper(Context context, ArrayList<slider_viewpager_model> arrayList1) {
        this.context = context;
        this.arrayList = arrayList1;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_custom_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        imageView.setImageResource(arrayList.get(position).getImg());
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
    public void setTimer(final ViewPager myPager, int time) {
        final int size = arrayList.size();


        final Runnable Update = new Runnable() {
            int NUM_PAGES = size;
            int currentPage = 0;

            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                myPager.setCurrentItem(currentPage++, true);
            }
        };

        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 500, time * 500);


    }
}