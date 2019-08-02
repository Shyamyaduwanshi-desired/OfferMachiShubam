package com.desired.offermachi.customer.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.ui.DashBoardActivity;
import com.desired.offermachi.customer.ui.StoreCouponCodeActivity;
import com.desired.offermachi.customer.ui.ViewAllOfferFollowActivity;
import com.desired.offermachi.customer.ui.ViewStoreOfferActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment {

ImageView proimg;
    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_store, container, false);
        ((DashBoardActivity)getActivity()).setToolTittle("Store",2);
        proimg=v.findViewById(R.id.proimg);
        proimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewAllOfferFollowActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout productlinearunfollow =(LinearLayout)v.findViewById(R.id.product_linear_unfollow_id);
        productlinearunfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //    Intent intent = new Intent(StoreCouponCodeActivity.this, ViewAllOfferFollowActivity.class);
                Intent intent = new Intent(getContext(), ViewStoreOfferActivity.class);
                startActivity(intent);

            }
        });


        return v;

    }

}
