package com.desired.offermachi.customer.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.adapter.CategortListAdapter;
import com.desired.offermachi.customer.adapter.SmartShoppingAdapter;
import com.desired.offermachi.customer.adapter.SmartShoppingRemoveAdapter;
import com.desired.offermachi.customer.model.Category_list_model;
import com.desired.offermachi.customer.model.category_model;

import java.util.ArrayList;
import java.util.List;

public class SmartShoppingRemoveActivity  extends AppCompatActivity {

    ImageView imageViewback;
    RecyclerView product_recyclerview;
    private SmartShoppingRemoveAdapter smartShoppingRemoveAdapter;
    private List<category_model> productdataset = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_shopping_text_activity);

        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TextView filtertext=(TextView)findViewById(R.id.filter_text_id);
        filtertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), FilterShowActivity.class);
                startActivity(intent);
            }
        });

        TextView sortbytext=(TextView)findViewById(R.id.sortby_text_id);
        sortbytext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(SmartShoppingRemoveActivity.this);
                dialog.setContentView(R.layout.sort_dialog_activity);
                dialog.setTitle("Custom Dialog");
                RelativeLayout atoz=(RelativeLayout)dialog.findViewById(R.id.atoz_id);
                RelativeLayout ztoa=(RelativeLayout)dialog.findViewById(R.id.ztoa_id);
                dialog.show();

            }
        });

        product_recyclerview = (RecyclerView) findViewById(R.id.smart_shopping_remove_recycler_id);
        smartShoppingRemoveAdapter = new SmartShoppingRemoveAdapter(getApplicationContext(), (ArrayList<category_model>) productdataset);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
        product_recyclerview.setLayoutManager(gridLayoutManager);
        product_recyclerview.setItemAnimator(new DefaultItemAnimator());
        product_recyclerview.setAdapter(smartShoppingRemoveAdapter);
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

