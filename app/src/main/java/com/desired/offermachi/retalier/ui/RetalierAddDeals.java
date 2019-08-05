package com.desired.offermachi.retalier.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.adapter.CategoryAdapter;
import com.desired.offermachi.customer.model.category_model;
import com.desired.offermachi.retalier.model.retalier_category_model;
import com.desired.offermachi.retalier.retalieradapter.RetalierCategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class RetalierAddDeals extends AppCompatActivity {
    ImageView imageViewback;
    Button buttonviewoffer;
    RecyclerView product_recyclerview;
    private RetalierCategoryAdapter retalierCategoryAdapter;
    private List<retalier_category_model> categorylistdataset = new ArrayList<>();
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_add_deals);

        mContext=getApplicationContext();

        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        product_recyclerview = (RecyclerView)findViewById(R.id.addtodeals_recycler_id);
        retalierCategoryAdapter = new RetalierCategoryAdapter(getApplicationContext(), (ArrayList<retalier_category_model>) categorylistdataset);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
        product_recyclerview.setLayoutManager(gridLayoutManager);
        product_recyclerview.setItemAnimator(new DefaultItemAnimator());
        product_recyclerview.setAdapter(retalierCategoryAdapter);
        Categorydata();

    }

    private void Categorydata() {
        retalier_category_model retalier_category_model1=new retalier_category_model(R.drawable.myvegpizza,"DOMINOZ PIZZA","Jul 31,2019");
        categorylistdataset.add(retalier_category_model1);
        retalier_category_model retalier_category_model2=new retalier_category_model(R.drawable.catseven,"DOMINOZ PIZZA","Jul 31,2019");
        categorylistdataset.add(retalier_category_model2);
        retalier_category_model retalier_category_model3=new retalier_category_model(R.drawable.catfifth,"DOMINOZ PIZZA","Jul 31,2019");
        categorylistdataset.add(retalier_category_model3);
        retalier_category_model retalier_category_model4=new retalier_category_model(R.drawable.productsecond,"DOMINOZ PIZZA","Jul 31,2019");
        categorylistdataset.add(retalier_category_model4);

    }
}