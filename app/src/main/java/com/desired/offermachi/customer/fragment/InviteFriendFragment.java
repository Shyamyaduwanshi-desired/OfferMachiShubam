package com.desired.offermachi.customer.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.ui.DashBoardActivity;


public class InviteFriendFragment extends Fragment {
    View view;


    public InviteFriendFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.invite_friend_activity, container, false);
        ((DashBoardActivity)getActivity()).setToolTittle("Invite a Friend",2);
        return  view;
    }
}

