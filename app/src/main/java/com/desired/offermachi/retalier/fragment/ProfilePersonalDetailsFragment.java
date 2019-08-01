package com.desired.offermachi.retalier.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.ui.RetalierOtpActivity;

public class ProfilePersonalDetailsFragment extends Fragment {
    View view;


    public ProfilePersonalDetailsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.retalier_personal_detalis_activity, container, false);

        Button nextbutton=(Button)view.findViewById(R.id.next_button_id);
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RetalierOtpActivity.class);
                startActivity(intent);
            }
        });

        return  view;
    }
}



