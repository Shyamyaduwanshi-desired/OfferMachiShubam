package com.desired.offermachi.customer.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.ui.DashBoardActivity;


public class TermConditionFragment extends Fragment {
    View view;


    public TermConditionFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.term_condition_activity, container, false);
        ((DashBoardActivity)getActivity()).setToolTittle("Term & Condition",2);
        return  view;
    }
}

