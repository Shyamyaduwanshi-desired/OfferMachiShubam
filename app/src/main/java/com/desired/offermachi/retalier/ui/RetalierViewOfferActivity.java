package com.desired.offermachi.retalier.ui;

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
import com.desired.offermachi.retalier.model.retalier_category_model;
import com.desired.offermachi.retalier.retalieradapter.RetalierCategoryAdapter;
import com.desired.offermachi.retalier.retalieradapter.ViewOfferAdapter;

import java.util.ArrayList;
import java.util.List;

public class RetalierViewOfferActivity extends AppCompatActivity {

    ImageView imageViewback;
    RecyclerView product_recyclerview;
    private ViewOfferAdapter viewOfferAdapter;
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
        viewOfferAdapter = new ViewOfferAdapter(getApplicationContext(), (ArrayList<ViewOfferModel>) viewcategorylistdataset);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
        product_recyclerview.setLayoutManager(gridLayoutManager);
        product_recyclerview.setItemAnimator(new DefaultItemAnimator());
        product_recyclerview.setAdapter(viewOfferAdapter);
        Categorydata();
    }

    private void Categorydata() {
        ViewOfferModel ViewOfferModel1=new ViewOfferModel(R.drawable.myvegpizza,"DOMINOZ PIZZA","Jul 31,2019","Added");
        viewcategorylistdataset.add(ViewOfferModel1);
        ViewOfferModel ViewOfferModel2=new ViewOfferModel(R.drawable.catseven,"DOMINOZ PIZZA","Jul 31,2019","Added");
        viewcategorylistdataset.add(ViewOfferModel2);
        ViewOfferModel ViewOfferModel3=new ViewOfferModel(R.drawable.catfifth,"DOMINOZ PIZZA","Jul 31,2019","Added");
        viewcategorylistdataset.add(ViewOfferModel3);
        ViewOfferModel ViewOfferModel4=new ViewOfferModel(R.drawable.productsecond,"DOMINOZ PIZZA","Jul 31,2019","Added");
        viewcategorylistdataset.add(ViewOfferModel4);

    }
}

