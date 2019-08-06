package com.desired.offermachi.customer.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.adapter.CategoryAdapter;
import com.desired.offermachi.customer.model.category_model;
import com.desired.offermachi.customer.ui.DashBoardActivity;
import com.desired.offermachi.customer.ui.FilterShowActivity;
import com.desired.offermachi.customer.ui.ProductActivity;

import java.util.ArrayList;
import java.util.List;


public class MycouponsFragment  extends Fragment {
    View view;
    RecyclerView product_recyclerview;
    private CategoryAdapter categoryAdapter;
    private List<category_model> productdataset = new ArrayList<>();

    public MycouponsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_coupons_activity, container, false);
        ((DashBoardActivity)getActivity()).setToolTittle("My Coupons",2);


        TextView filtertext=(TextView)view.findViewById(R.id.filter_text_id);
        filtertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), FilterShowActivity.class);
                startActivity(intent);
            }
        });

        TextView sortbytext=(TextView)view.findViewById(R.id.sortby_text_id);
        sortbytext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.sort_dialog_activity);
                dialog.setTitle("Custom Dialog");
                RelativeLayout atoz=(RelativeLayout)dialog.findViewById(R.id.atoz_id);
                RelativeLayout ztoa=(RelativeLayout)dialog.findViewById(R.id.ztoa_id);
                dialog.show();

            }
        });

        product_recyclerview = (RecyclerView)view.findViewById(R.id.coupons_recycler_id);
        categoryAdapter = new CategoryAdapter(getContext(), (ArrayList<category_model>) productdataset);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        product_recyclerview.setLayoutManager(gridLayoutManager);
        product_recyclerview.setItemAnimator(new DefaultItemAnimator());
        product_recyclerview.setAdapter(categoryAdapter);
        Categorydata();
        return  view;
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




