package com.desired.offermachi.customer.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.adapter.CategortListAdapter;
import com.desired.offermachi.customer.adapter.ProductAdapter;
import com.desired.offermachi.customer.adapter.StoreViewallAdapter;
import com.desired.offermachi.customer.model.Category_list_model;
import com.desired.offermachi.customer.model.slider_model;
import com.desired.offermachi.customer.ui.DashBoardActivity;
import com.desired.offermachi.customer.ui.FilterShowActivity;
import com.desired.offermachi.customer.ui.StoreCouponCodeActivity;
import com.desired.offermachi.customer.ui.ViewAllOfferFollowActivity;
import com.desired.offermachi.customer.ui.ViewStoreOfferActivity;

import java.util.ArrayList;
import java.util.List;

public class StoreFragment extends Fragment {
    private List<slider_model> productdatasetcategory=new ArrayList<>();
    RecyclerView product_recyclerview;
    private StoreViewallAdapter storeViewallAdapter;
    private Context mContext;

ImageView proimg;
    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_store, container, false);

        ((DashBoardActivity)getActivity()).setToolTittle("Stores",2);
        mContext=getActivity();


        TextView filtertext=(TextView)v.findViewById(R.id.filter_text_id);
        filtertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), FilterShowActivity.class);
                startActivity(intent);
            }
        });

        TextView sortbytext=(TextView)v.findViewById(R.id.sortby_text_id);
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

        product_recyclerview = (RecyclerView)v.findViewById(R.id.store_recycler_id);
        storeViewallAdapter = new StoreViewallAdapter(getContext(), (ArrayList<slider_model>) productdatasetcategory);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4, LinearLayoutManager.VERTICAL, false);
        product_recyclerview.setLayoutManager(gridLayoutManager);
        product_recyclerview.setItemAnimator(new DefaultItemAnimator());
        product_recyclerview.setAdapter(storeViewallAdapter);
        productdata();

        return v;

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
