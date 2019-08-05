package com.desired.offermachi.customer.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.ui.DashBoardActivity;
import com.desired.offermachi.customer.ui.FilterShowActivity;
import com.desired.offermachi.customer.ui.SmartShoppingRemoveActivity;


public class SmartShoppingFragment extends Fragment {
    View view;


    public SmartShoppingFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.smart_shopping_fragment, container, false);
        ((DashBoardActivity)getActivity()).setToolTittle("Smart Shopping",2);



        LinearLayout electronicslinear=(LinearLayout)view.findViewById(R.id.electronics_linear_id);
        electronicslinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SmartShoppingRemoveActivity.class);
                startActivity(intent);

            }
        });

        return  view;
    }
}



