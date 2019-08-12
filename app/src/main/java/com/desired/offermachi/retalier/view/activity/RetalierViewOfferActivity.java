package com.desired.offermachi.retalier.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.model.ViewOfferModel;
import com.desired.offermachi.retalier.view.adapter.ViewOfferDiscountAdapter;

import java.util.ArrayList;
import java.util.List;

public class RetalierViewOfferActivity extends AppCompatActivity {

    ImageView imageViewback;
    RecyclerView product_recyclerview;
    private ViewOfferDiscountAdapter viewOfferAdapter;
    private List<ViewOfferModel> viewcategorylistdataset = new ArrayList<>();
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_view_offers_activity);
        mContext=getApplicationContext();

        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        product_recyclerview = (RecyclerView)findViewById(R.id.viewalloffer_recycler_id);
        viewOfferAdapter = new ViewOfferDiscountAdapter(getApplicationContext(), (ArrayList<ViewOfferModel>) viewcategorylistdataset);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
        product_recyclerview.setLayoutManager(gridLayoutManager);
        product_recyclerview.setItemAnimator(new DefaultItemAnimator());
        product_recyclerview.setAdapter(viewOfferAdapter);
    }

}

