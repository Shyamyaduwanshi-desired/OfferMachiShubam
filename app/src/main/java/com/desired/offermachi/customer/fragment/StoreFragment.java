package com.desired.offermachi.customer.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.ui.DashBoardActivity;
import com.desired.offermachi.customer.ui.FilterShowActivity;
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

        ((DashBoardActivity)getActivity()).setToolTittle("Stores",2);



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
