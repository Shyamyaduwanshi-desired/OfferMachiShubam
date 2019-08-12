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
import com.desired.offermachi.customer.model.Category_list_model;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    ImageView imageViewback;
    RecyclerView product_recyclerview;
    private CategortListAdapter categortListAdapter;
    private List<Category_list_model> categorylistdataset = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);

        imageViewback = findViewById(R.id.imageback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        product_recyclerview = (RecyclerView) findViewById(R.id.category_recycler_id);
        categortListAdapter = new CategortListAdapter(getApplicationContext(), (ArrayList<Category_list_model>) categorylistdataset);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4, LinearLayoutManager.VERTICAL, false);
        product_recyclerview.setLayoutManager(gridLayoutManager);
        product_recyclerview.setItemAnimator(new DefaultItemAnimator());
        product_recyclerview.setAdapter(categortListAdapter);
        Categorydata();
    }
        private void Categorydata() {
            Category_list_model Category_list_model1=new Category_list_model(R.drawable.electronics,"Electronics","Follow");
            categorylistdataset.add(Category_list_model1);
            Category_list_model Category_list_model2=new Category_list_model(R.drawable.men,"Men","Follow");
            categorylistdataset.add(Category_list_model2);
            Category_list_model Category_list_model3=new Category_list_model(R.drawable.hairstyle,"Women","Follow");
            categorylistdataset.add(Category_list_model3);
            Category_list_model Category_list_model4=new Category_list_model(R.drawable.kids,"Baby & Kids","Follow");
            categorylistdataset.add(Category_list_model4);
            Category_list_model Category_list_model5=new Category_list_model(R.drawable.furniture,"Home & Furniture","Follow");
            categorylistdataset.add(Category_list_model5);
            Category_list_model Category_list_model6=new Category_list_model(R.drawable.fastfood,"Food","Follow");
            categorylistdataset.add(Category_list_model6);
            Category_list_model Category_list_model7=new Category_list_model(R.drawable.pharmacy,"Pharmacy","Follow");
            categorylistdataset.add(Category_list_model7);
            Category_list_model Category_list_model8=new Category_list_model(R.drawable.beauty,"Beauty","Follow");
            categorylistdataset.add(Category_list_model8);
            Category_list_model Category_list_model9=new Category_list_model(R.drawable.car,"Car","Follow");
            categorylistdataset.add(Category_list_model9);
    }

}


