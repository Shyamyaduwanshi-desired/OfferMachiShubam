package com.desired.offermachi.customer.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.view.activity.DashBoardActivity;


public class TermConditionFragment extends Fragment {
    View view;


    public TermConditionFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.term_condition_activity, container, false);
        ((DashBoardActivity)getActivity()).setToolTittle("Term & Condition",2);
        WebView mywebview = (WebView) view.findViewById(R.id.webView1);
        mywebview.loadUrl("http://offermachi.in/pages/terms_condition");
      /*  Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse("http://offermachi.in/pages/terms_condition"));
        startActivity(viewIntent);*/
        return  view;
    }
}

