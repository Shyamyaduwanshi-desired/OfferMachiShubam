package com.desired.offermachi.customer.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.adapter.ProductAdapter;
import com.desired.offermachi.customer.model.slider_model;

import java.util.ArrayList;
import java.util.List;

public class ViewAllOfferFollowActivity extends AppCompatActivity {
    ImageView proimg;
    private List<slider_model> productdatasetcategory=new ArrayList<>();
    RecyclerView product_recyclerview;
    private ProductAdapter productAdapter;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_offer_follow_activity);

        mContext=getApplicationContext();
        product_recyclerview = (RecyclerView)findViewById(R.id.related_store_recycler_id);
        productAdapter = new ProductAdapter(mContext, (ArrayList<slider_model>) productdatasetcategory);
        product_recyclerview.setLayoutManager((new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false)));
        product_recyclerview.setItemAnimator(new DefaultItemAnimator());
        product_recyclerview.setAdapter(productAdapter);
        productdata();

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
}
