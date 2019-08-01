package com.desired.offermachi.customer.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.desired.offermachi.R;

import com.desired.offermachi.customer.adapter.CategoryAdapter;
import com.desired.offermachi.customer.model.category_model;


import java.util.ArrayList;
import java.util.List;

public class ViewOfferActivity extends AppCompatActivity {

    ImageView imageViewback;
    RecyclerView product_recyclerview;
    private CategoryAdapter categoryAdapter;
    private List<category_model> productdataset = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_offers_activity);

        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        product_recyclerview = (RecyclerView) findViewById(R.id.viewalloffer_recycler_id);
        categoryAdapter = new CategoryAdapter(getApplicationContext(), (ArrayList<category_model>) productdataset);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
        product_recyclerview.setLayoutManager(gridLayoutManager);
        product_recyclerview.setItemAnimator(new DefaultItemAnimator());
        product_recyclerview.setAdapter(categoryAdapter);
        Categorydata();
    }
    private void Categorydata() {
        category_model category_model1=new category_model(R.drawable.myvegpizza,"DOMINOZ PIZZA","Jul 31,2019","Share");
        productdataset.add(category_model1);
        category_model category_model2=new category_model(R.drawable.myvegpizza,"DOMINOZ PIZZA","Jul 31,2019","Share");
        productdataset.add(category_model2);
        category_model category_model3=new category_model(R.drawable.myvegpizza,"DOMINOZ PIZZA","Jul 31,2019","Share");
        productdataset.add(category_model3);
        category_model category_model4=new category_model(R.drawable.myvegpizza,"DOMINOZ PIZZA","Jul 31,2019","Share");
        productdataset.add(category_model4);

    }
}
