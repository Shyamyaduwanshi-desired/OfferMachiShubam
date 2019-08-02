package com.desired.offermachi.customer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.ui.DashBoardActivity;
import com.desired.offermachi.customer.ui.EditProfileActivity;


public class ProfileFragment extends Fragment {

    LinearLayout edittextlinear;
    ImageView imageViewback;

    View view;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_profile_activity, container, false);
       ((DashBoardActivity)getActivity()).setToolTittle("My Profile",2);

        edittextlinear=(LinearLayout)view.findViewById(R.id.edit_text_linear_id);
        edittextlinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

//        imageViewback=(ImageView)view.findViewById(R.id.imageback);
//        imageViewback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
        return  view;
    }
}




