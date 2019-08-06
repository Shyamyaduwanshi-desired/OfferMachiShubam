package com.desired.offermachi.customer.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.ui.DashBoardActivity;


public class HelpFragment extends Fragment {
    View view;
    TextView drop1,drop2,drop3,drop4;
    LinearLayout ly1,ly2,ly3,ly4;
    int count=0;
    int count2=0;
    int count3=0;
    int count4=0;


    public HelpFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.help_activity, container, false);
        ((DashBoardActivity)getActivity()).setToolTittle("HELP & FAQ",2);
        drop1=view.findViewById(R.id.drop1);
        drop2=view.findViewById(R.id.drop2);
        drop3=view.findViewById(R.id.drop3);
        drop4=view.findViewById(R.id.drop4);
        ly1=view.findViewById(R.id.para1);
        ly2=view.findViewById(R.id.para2);
        ly3=view.findViewById(R.id.para3);
        ly4=view.findViewById(R.id.para4);

        drop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count==0){
                    ly1.setVisibility(View.VISIBLE);
                    count=1;
                }else{
                    ly1.setVisibility(View.GONE);
                    count=0;
                }


            }
        });


        drop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count2==0){
                    ly2.setVisibility(View.VISIBLE);
                    count2=1;
                }else{
                    ly2.setVisibility(View.GONE);
                    count2=0;
                }

            }
        });
        drop3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count3==0){
                    ly3.setVisibility(View.VISIBLE);
                    count3=1;
                }else{
                    ly3.setVisibility(View.GONE);
                    count3=0;
                }
            }
        });
        drop4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count4==0){
                    ly4.setVisibility(View.VISIBLE);
                    count4=1;
                }else{
                    ly4.setVisibility(View.GONE);
                    count4=0;
                }
            }
        });

        return  view;
    }
}



