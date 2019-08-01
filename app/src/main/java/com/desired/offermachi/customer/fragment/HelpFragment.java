package com.desired.offermachi.customer.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.ui.DashBoardActivity;


public class HelpFragment extends Fragment {
    View view;


    public HelpFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.help_activity, container, false);
        ((DashBoardActivity)getActivity()).setToolTittle("Help",2);
        return  view;
    }
}



