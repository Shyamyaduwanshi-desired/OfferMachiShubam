package com.desired.offermachi.customer.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.model.slider_model;


import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    ArrayList<slider_model> arrayList=new ArrayList<>();


    public SliderAdapter(Context context, ArrayList<slider_model> arrayList1) {
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
        View view = layoutInflater.inflate(R.layout.activity_main, null);

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
}





