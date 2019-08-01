package com.desired.offermachi.customer.fragment;

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

import com.desired.offermachi.R;

import com.desired.offermachi.customer.adapter.CategoryAdapter;
import com.desired.offermachi.customer.model.category_model;
import com.desired.offermachi.customer.ui.DashBoardActivity;

import java.util.ArrayList;
import java.util.List;

public class FavouritesFragment extends Fragment {
    View view;
    RecyclerView product_recyclerview;
    private CategoryAdapter categoryAdapter;
    private List<category_model> productdataset = new ArrayList<>();

    public FavouritesFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.favourites_activity, container, false);

        ((DashBoardActivity)getActivity()).setToolTittle("Favourites",2);

        product_recyclerview = (RecyclerView) view.findViewById(R.id.favourites_recycler_id);
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
        category_model category_model2=new category_model(R.drawable.catseven,"DOMINOZ PIZZA","Jul 31,2019","Share");
        productdataset.add(category_model2);
        category_model category_model3=new category_model(R.drawable.catfifth,"DOMINOZ PIZZA","Jul 31,2019","Share");
        productdataset.add(category_model3);
        category_model category_model4=new category_model(R.drawable.productsecond,"DOMINOZ PIZZA","Jul 31,2019","Share");
        productdataset.add(category_model4);

    }
}