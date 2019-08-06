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
import com.desired.offermachi.customer.adapter.CategortListAdapter;
import com.desired.offermachi.customer.adapter.FilterShowAdapter;
import com.desired.offermachi.customer.model.Category_list_model;
import com.desired.offermachi.customer.model.filter_show_model;

import java.util.ArrayList;
import java.util.List;

public class FilterShowActivity extends AppCompatActivity {

    ImageView imageViewback;
    RecyclerView product_recyclerview;
    private FilterShowAdapter filterShowAdapter;
    private List<filter_show_model> filterlistdataset = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_activity);

        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        product_recyclerview = (RecyclerView) findViewById(R.id.filtershow_recycler_id);
        filterShowAdapter = new FilterShowAdapter(getApplicationContext(), (ArrayList<filter_show_model>) filterlistdataset);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4, LinearLayoutManager.VERTICAL, false);
        product_recyclerview.setLayoutManager(gridLayoutManager);
        product_recyclerview.setItemAnimator(new DefaultItemAnimator());
        product_recyclerview.setAdapter(filterShowAdapter);
        Categorydata();
    }
    private void Categorydata() {
        filter_show_model filter_show_model1=new filter_show_model(R.drawable.electronics,"Electronics");
        filterlistdataset.add(filter_show_model1);
        filter_show_model filter_show_model2=new filter_show_model(R.drawable.men,"Men");
        filterlistdataset.add(filter_show_model2);
        filter_show_model filter_show_model3=new filter_show_model(R.drawable.hairstyle,"Women");
        filterlistdataset.add(filter_show_model3);
        filter_show_model filter_show_model4=new filter_show_model(R.drawable.kids,"Baby & Kids");
        filterlistdataset.add(filter_show_model4);
        filter_show_model filter_show_model5=new filter_show_model(R.drawable.furniture,"Home & Furniture");
        filterlistdataset.add(filter_show_model5);
        filter_show_model filter_show_model6=new filter_show_model(R.drawable.fastfood,"Food");
        filterlistdataset.add(filter_show_model6);
        filter_show_model filter_show_model7=new filter_show_model(R.drawable.pharmacy,"Pharmacy");
        filterlistdataset.add(filter_show_model7);
        filter_show_model filter_show_model8=new filter_show_model(R.drawable.beauty,"Beauty");
        filterlistdataset.add(filter_show_model8);
        filter_show_model filter_show_model9=new filter_show_model(R.drawable.car,"Car");
        filterlistdataset.add(filter_show_model9);
    }
}


