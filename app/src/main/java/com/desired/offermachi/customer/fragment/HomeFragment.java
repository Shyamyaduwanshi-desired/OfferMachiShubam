package com.desired.offermachi.customer.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.desired.offermachi.R;

import com.desired.offermachi.customer.adapter.CategoryAdapter;
import com.desired.offermachi.customer.adapter.ProductAdapter;
import com.desired.offermachi.customer.model.category_model;
import com.desired.offermachi.customer.model.slider_model;
import com.desired.offermachi.customer.ui.DashBoardActivity;
import com.desired.offermachi.customer.ui.StoreCouponCodeActivity;
import com.desired.offermachi.customer.ui.ViewOfferActivity;


import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    View view;
    private List<slider_model> productdatasetcategory=new ArrayList<>();
    RecyclerView product_recyclerview;
    private ProductAdapter productAdapter;
    private Context mContext;

    RecyclerView homeproduct_recyclerview;
    private CategoryAdapter categoryAdapter;
    private List<category_model> productdataset = new ArrayList<>();


    public HomeFragment() {

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home_fragment, container, false);

        ((DashBoardActivity)getActivity()).setToolTittle("",1);
        TextView contentfirst=(TextView)view.findViewById(R.id.contentfirst_id);
        Typeface content= ResourcesCompat.getFont(getContext(), R.font.nunitoregular);
        contentfirst.setTypeface(content);

        TextView contentsecond=(TextView)view.findViewById(R.id.contentsecond_id);
        Typeface content1= ResourcesCompat.getFont(getContext(), R.font.nunitoregular);
        contentsecond.setTypeface(content1);

       TextView trendingdeals=(TextView)view.findViewById(R.id.dealsoftheday_text_id);
        Typeface content2= ResourcesCompat.getFont(getContext(), R.font.ralewaybold);
        trendingdeals.setTypeface(content2);

        TextView trendings=(TextView)view.findViewById(R.id.trendingdeals_id);
        Typeface content3= ResourcesCompat.getFont(getContext(), R.font.ralewaybold);
        trendings.setTypeface(content3);


        TextView viewtext=(TextView)view.findViewById(R.id.view_text_id);
        viewtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewOfferActivity.class);
                startActivity(intent);
            }
        });


        homeproduct_recyclerview = (RecyclerView) view.findViewById(R.id.home_product_linear_id);
        categoryAdapter = new CategoryAdapter(getContext(), (ArrayList<category_model>) productdataset);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        homeproduct_recyclerview.setLayoutManager(gridLayoutManager);
        homeproduct_recyclerview.setItemAnimator(new DefaultItemAnimator());
        homeproduct_recyclerview.setAdapter(categoryAdapter);
        Categorydata();


       TextView viewall =(TextView)view.findViewById(R.id.viewall_text_id);
       viewall .setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent = new Intent(getActivity(), StoreCouponCodeActivity.class);
               startActivity(intent);

           }
       });

        product_recyclerview = (RecyclerView) view.findViewById(R.id.recommendede_recycler_id);
        productAdapter = new ProductAdapter(mContext, (ArrayList<slider_model>) productdatasetcategory);
        product_recyclerview.setLayoutManager((new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)));
        product_recyclerview.setItemAnimator(new DefaultItemAnimator());
        product_recyclerview.setAdapter(productAdapter);
         productdata();

         return view;
    }
    private void productdata() {

        slider_model deals_model1=new slider_model(R.drawable.brandfirst,"DOMINOZ PIZZA");
        productdatasetcategory.add(deals_model1);

        slider_model deals_model2=new slider_model(R.drawable.shortlogo,"TEST");
        productdatasetcategory.add(deals_model2);

        slider_model deals_model3=new slider_model(R.drawable.brandsecond,"REEBOK");
        productdatasetcategory.add(deals_model3);

        slider_model deals_model4=new slider_model(R.drawable.brandfirst,"TEST");
        productdatasetcategory.add(deals_model4);

        slider_model deals_model5=new slider_model(R.drawable.shortlogo,"TEST");
        productdatasetcategory.add(deals_model5);

        slider_model deals_model6=new slider_model(R.drawable.brandsecond,"TEST");
        productdatasetcategory.add(deals_model6);

    }
    private void Categorydata() {
        category_model category_model1=new category_model(R.drawable.myvegpizza,"DOMINOZ PIZZA","Jul 31,2019","Share");
        productdataset.add(category_model1);
        category_model category_model2=new category_model(R.drawable.productsecond,"DOMINOZ PIZZA","Jul 31,2019","Share");
        productdataset.add(category_model2);
        category_model category_model3=new category_model(R.drawable.productfirst,"DOMINOZ PIZZA","Jul 31,2019","Share");
        productdataset.add(category_model3);
        category_model category_model4=new category_model(R.drawable.catfifth,"DOMINOZ PIZZA","Jul 31,2019","Share");
        productdataset.add(category_model4);

    }
}


