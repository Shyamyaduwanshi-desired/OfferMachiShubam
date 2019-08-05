package com.desired.offermachi.retalier.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.model.ViewOfferModel;
import com.desired.offermachi.retalier.retalieradapter.PushOfferAdapter;
import com.desired.offermachi.retalier.retalieradapter.ViewOfferAdapter;

import java.util.ArrayList;
import java.util.List;

public class RetalierPushActivity extends AppCompatActivity {

    Button pushoffer;
    int count = 0;
    int countBACK = 0;
    ImageView imageViewback;
    RecyclerView product_recyclerview;
    private PushOfferAdapter pushOfferAdapter;
    private List<ViewOfferModel> viewcategorylistdataset = new ArrayList<>();
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reatalier_push_offer_activity);
        mContext=getApplicationContext();

        imageViewback = findViewById(R.id.imageback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        product_recyclerview = (RecyclerView) findViewById(R.id.pushoffer_recycler_id);
        pushOfferAdapter = new PushOfferAdapter(getApplicationContext(), (ArrayList<ViewOfferModel>) viewcategorylistdataset);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
        product_recyclerview.setLayoutManager(gridLayoutManager);
        product_recyclerview.setItemAnimator(new DefaultItemAnimator());
        product_recyclerview.setAdapter(pushOfferAdapter);
        Categorydata();
    }

    private void Categorydata() {
        ViewOfferModel ViewOfferModel1 = new ViewOfferModel(R.drawable.myvegpizza, "DOMINOZ PIZZA", "Jul 31,2019", "Push Offer");
        viewcategorylistdataset.add(ViewOfferModel1);
        ViewOfferModel ViewOfferModel2 = new ViewOfferModel(R.drawable.catseven, "DOMINOZ PIZZA", "Jul 31,2019", "Push Offer");
        viewcategorylistdataset.add(ViewOfferModel2);
        ViewOfferModel ViewOfferModel3 = new ViewOfferModel(R.drawable.catfifth, "DOMINOZ PIZZA", "Jul 31,2019", "Push Offer");
        viewcategorylistdataset.add(ViewOfferModel3);
        ViewOfferModel ViewOfferModel4 = new ViewOfferModel(R.drawable.productsecond, "DOMINOZ PIZZA", "Jul 31,2019", "Push Offer");
        viewcategorylistdataset.add(ViewOfferModel4);

    }
}



