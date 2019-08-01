package com.desired.offermachi.customer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.ui.DashBoardActivity;
import com.desired.offermachi.customer.ui.ViewOfferActivity;


public class FollowFragment extends Fragment {
    View view;


    public FollowFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.follow_activity, container, false);
        ((DashBoardActivity)getActivity()).setToolTittle("iFollow",2);

        LinearLayout viewofferslinear=(LinearLayout)view.findViewById(R.id.viewoffers_linear_id);
        viewofferslinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewOfferActivity.class);
               startActivity(intent);
            }
        });


        LinearLayout viewofferslinear1=(LinearLayout)view.findViewById(R.id.view_offset_linear_second_id);
        viewofferslinear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewOfferActivity.class);
                startActivity(intent);
            }
        });
        return  view;
    }
}





