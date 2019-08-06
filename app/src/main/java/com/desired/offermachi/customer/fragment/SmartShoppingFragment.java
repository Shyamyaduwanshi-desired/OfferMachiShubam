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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.adapter.CategoryAdapter;
import com.desired.offermachi.customer.adapter.SmartShoppingAdapter;
import com.desired.offermachi.customer.model.Category_list_model;
import com.desired.offermachi.customer.model.category_model;
import com.desired.offermachi.customer.model.smart_shopping_model;
import com.desired.offermachi.customer.ui.DashBoardActivity;
import com.desired.offermachi.customer.ui.FilterShowActivity;
import com.desired.offermachi.customer.ui.SmartShoppingRemoveActivity;

import java.util.ArrayList;
import java.util.List;


public class SmartShoppingFragment extends Fragment {
    View view;
    RecyclerView product_recyclerview;
    private SmartShoppingAdapter smartShoppingAdapter;
    private List<smart_shopping_model> categorylistdataset = new ArrayList<>();

    public SmartShoppingFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.smart_shopping_fragment, container, false);
        ((DashBoardActivity)getActivity()).setToolTittle("Smart Shopping",2);

        product_recyclerview = (RecyclerView) view.findViewById(R.id.smartshopping_recycler_id);
        smartShoppingAdapter = new SmartShoppingAdapter(getContext(), (ArrayList<smart_shopping_model>) categorylistdataset);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4, LinearLayoutManager.VERTICAL, false);
        product_recyclerview.setLayoutManager(gridLayoutManager);
        product_recyclerview.setItemAnimator(new DefaultItemAnimator());
        product_recyclerview.setAdapter(smartShoppingAdapter);
        Categorydata();

        return  view;
    }
    private void Categorydata() {
        smart_shopping_model smart_shopping_model1=new smart_shopping_model(R.drawable.electronics,"Electronics");
        categorylistdataset.add(smart_shopping_model1);
        smart_shopping_model smart_shopping_model2=new smart_shopping_model(R.drawable.men,"Men");
        categorylistdataset.add(smart_shopping_model2);
        smart_shopping_model smart_shopping_model3=new smart_shopping_model(R.drawable.hairstyle,"Women");
        categorylistdataset.add(smart_shopping_model3);
        smart_shopping_model smart_shopping_model4=new smart_shopping_model(R.drawable.kids,"Baby & Kids");
        categorylistdataset.add(smart_shopping_model4);
        smart_shopping_model smart_shopping_model5=new smart_shopping_model(R.drawable.furniture,"Home & Furniture");
        categorylistdataset.add(smart_shopping_model5);
        smart_shopping_model smart_shopping_model6=new smart_shopping_model(R.drawable.fastfood,"Food");
        categorylistdataset.add(smart_shopping_model6);
        smart_shopping_model smart_shopping_model7=new smart_shopping_model(R.drawable.pharmacy,"Pharmacy");
        categorylistdataset.add(smart_shopping_model7);
        smart_shopping_model smart_shopping_model8=new smart_shopping_model(R.drawable.beauty,"Beauty");
        categorylistdataset.add(smart_shopping_model8);
        smart_shopping_model smart_shopping_model9=new smart_shopping_model(R.drawable.car,"Car");
        categorylistdataset.add(smart_shopping_model9);

    }
}



