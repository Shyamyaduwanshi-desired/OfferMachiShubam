package com.desired.offermachi.retalier.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.desired.offermachi.R;

public class ProfileStoreDetailsFragment extends Fragment {
    View view;


    public ProfileStoreDetailsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.retalier_personal_profile_activity, container, false);

        return  view;
    }
}

