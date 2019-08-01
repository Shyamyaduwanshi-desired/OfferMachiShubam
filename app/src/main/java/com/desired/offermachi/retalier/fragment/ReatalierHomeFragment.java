package com.desired.offermachi.retalier.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.ui.RetalierViewOfferDiscount;

public class ReatalierHomeFragment  extends Fragment {
    View view;
    int count=0;
    int countBACK=0;

    public ReatalierHomeFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.retalier_home_fragment, container, false);
        LinearLayout selecttypelinear =(LinearLayout)view.findViewById(R.id.select_type_linear_hide_id);
        final LinearLayout selecttypehidelinear =(LinearLayout)view.findViewById(R.id.select_typehide_linear_id);
        final LinearLayout fistcircle =(LinearLayout)view.findViewById(R.id.firstcircle_id);
        selecttypelinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countBACK=1;
                if (count==0){
                    selecttypehidelinear.setVisibility(View.VISIBLE);
                    count=1;
                }else{
                    selecttypehidelinear.setVisibility(View.GONE);
                    count=0;
                }

            }
        });
        final LinearLayout firsthomelinear=(LinearLayout)view.findViewById(R.id.firsthome_linear_id);
        final LinearLayout secondlinear=(LinearLayout)view.findViewById(R.id.second_home_linear_id);

        Button nextbutton=(Button)view.findViewById(R.id.next_button_id);
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countBACK=1;
                if (count==0){
                    firsthomelinear.setVisibility(View.GONE);
                    secondlinear.setVisibility(View.VISIBLE);
                    fistcircle.setBackgroundResource(R.drawable.colour_background);

                    count=1;
                }else{
                    firsthomelinear.setVisibility(View.VISIBLE);
                    secondlinear.setVisibility(View.GONE);
                    fistcircle.setBackgroundResource(R.drawable.simple_circle_background);
                    count=0;
                }
            }
        });

        Button submitbutton =(Button)view.findViewById(R.id.submit_button_id);
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RetalierViewOfferDiscount.class);
                startActivity(intent);
            }
        });
        return  view;
    }
}


