package com.desired.offermachi.customer.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.adapter.StoreViewallAdapter;
import com.desired.offermachi.customer.model.slider_model;

import java.util.ArrayList;
import java.util.List;

public class StoreCouponCodeActivity extends AppCompatActivity {

    ImageView imageViewback;
    ImageView proimg;
    private List<slider_model> productdatasetcategory=new ArrayList<>();
    RecyclerView product_recyclerview;
    private StoreViewallAdapter storeViewallAdapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_coupon_code_activity);

        mContext=getApplicationContext();

        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        product_recyclerview = (RecyclerView)findViewById(R.id.storecoupon_code_recycler_id);
        storeViewallAdapter = new StoreViewallAdapter(getApplicationContext(), (ArrayList<slider_model>) productdatasetcategory);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4, LinearLayoutManager.VERTICAL, false);
        product_recyclerview.setLayoutManager(gridLayoutManager);
        product_recyclerview.setItemAnimator(new DefaultItemAnimator());
        product_recyclerview.setAdapter(storeViewallAdapter);
        productdata();


    }
    private void productdata() {

        slider_model deals_model1=new slider_model(R.drawable.brandfirst,"DOMINOZ");
        productdatasetcategory.add(deals_model1);

        slider_model deals_model2=new slider_model(R.drawable.shortlogo,"Offer");
        productdatasetcategory.add(deals_model2);

        slider_model deals_model3=new slider_model(R.drawable.brandsecond,"Rebook");
        productdatasetcategory.add(deals_model3);

        slider_model deals_model4=new slider_model(R.drawable.forth,"Pater");
        productdatasetcategory.add(deals_model4);

        slider_model deals_model5=new slider_model(R.drawable.fifth,"E Shop");
        productdatasetcategory.add(deals_model5);

        slider_model deals_model6=new slider_model(R.drawable.six,"Wood");
        productdatasetcategory.add(deals_model6);

        slider_model deals_model7=new slider_model(R.drawable.kfclogo,"Kfc");
        productdatasetcategory.add(deals_model7);

        slider_model deals_model8=new slider_model(R.drawable.brandthird,"Puma");
        productdatasetcategory.add(deals_model8);

        slider_model deals_model9=new slider_model(R.drawable.levilogo,"Levi's");
        productdatasetcategory.add(deals_model9);




    }
}

