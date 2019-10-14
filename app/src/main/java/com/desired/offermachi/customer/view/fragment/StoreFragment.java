package com.desired.offermachi.customer.view.fragment;


import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.StoreModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.StoreListPresenter;
import com.desired.offermachi.customer.view.activity.StoreCouponCodeActivity;
import com.desired.offermachi.customer.view.adapter.CustomerStoreAdapter;
import com.desired.offermachi.customer.view.adapter.CustomerStoreAdapterNew;
import com.desired.offermachi.customer.view.adapter.StoreViewallAdapter;
import com.desired.offermachi.customer.view.activity.DashBoardActivity;
import com.desired.offermachi.customer.view.activity.FilterShowActivity;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class StoreFragment extends Fragment implements StoreListPresenter.StoreList, View.OnClickListener {
    View v;
    RecyclerView storerecycle;
    private CustomerStoreAdapterNew customerStoreAdapter;
    private StoreListPresenter presenter;
    String idholder;
//    TextView filtertext,sortbytext;
RelativeLayout rlFilter,rlSortBy;
    EditText edTxtSearch;
    TextView tvLoadMore;
    int pagNo=1;
    int diff=1;
    String Catid="";
    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         v=inflater.inflate(R.layout.fragment_store, container, false);
        diff=1;
        initview();
        Listner();
        return v;
    }
    private void Listner() {

        edTxtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = edTxtSearch.getText().toString().trim();
                if(customerStoreAdapter!=null)
                    customerStoreAdapter.filter(searchText);
            }
        });
    }
    private void initview(){
        ((DashBoardActivity)getActivity()).setToolTittle("Stores",2);
        User user = UserSharedPrefManager.getInstance(getContext()).getCustomer();
        idholder= user.getId();
        presenter = new StoreListPresenter(getContext(), StoreFragment.this);
        storerecycle=v.findViewById(R.id.storerecycleview);
        tvLoadMore=v.findViewById(R.id.tv_load_more);
//        filtertext=(TextView)v.findViewById(R.id.filter_text_id);
//        filtertext.setOnClickListener(this);
//        sortbytext=(TextView)v.findViewById(R.id.sortby_text_id);
//        sortbytext.setOnClickListener(this);

        edTxtSearch=v.findViewById(R.id.et_search);
        rlFilter=v.findViewById(R.id.rl_filter);
        rlSortBy=v.findViewById(R.id.rl_shorted_by);

        rlFilter.setOnClickListener(this);
        rlSortBy.setOnClickListener(this);
        tvLoadMore.setOnClickListener(this);

        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
        storerecycle.setLayoutManager(gridLayoutManager2);
        storerecycle.setItemAnimator(new DefaultItemAnimator());

//        if (getActivity()!=null) {
//            if (isNetworkConnected()) {
//                presenter.ViewAllStore(idholder);
//            } else {
//                showAlert("Please connect to internet.", R.style.DialogAnimation);
//            }
//        }

        CallAPI(1);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(StoreReceiver,
                new IntentFilter("StoreFollow"));
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(CatReceiver,
                new IntentFilter("Category"));
    }
    public BroadcastReceiver CatReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
             Catid = intent.getStringExtra("catid");
//            presenter.StoreFilter(idholder,Catid);
            diff=3;
            CallAPI(3);
//            presenter.StoreFilterPagination(idholder,Catid,pagNo);
        }
    };
    public BroadcastReceiver StoreReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = intent.getStringExtra("followstatus");
            String storeid = intent.getStringExtra("storeid");
            presenter.FollowStore(idholder,storeid,status);
        }
    };

    public void CallAPI(int i)
    {
        if (getActivity()!=null) {
            if (isNetworkConnected()) {
                switch (i) {
                    case 1://all store data
//                        presenter.ViewAllStore(idholder);
                        presenter.ViewAllStorePagination(idholder,pagNo);
                        break;
                    case 2:
//                        presenter.ShortBy(idholder,filterByStatus);
                        presenter.ShortByPagination(idholder,filterByStatus,pagNo);

                        break;
                  case 3:
//                        presenter.ShortBy(idholder,filterByStatus);
                      presenter.StoreFilterPagination(idholder,Catid,pagNo);

                        break;

                }
            } else {
                showAlert("Please connect to internet.", R.style.DialogAnimation);
            }
        }
    }

    ArrayList<StoreModel> arStore=new ArrayList<>();
    @Override
    public void success(ArrayList<StoreModel> response,int totalRecords,int totalPages) {

        if(pagNo==1) {
            arStore.clear();
            arStore=response;
        }
        else {
            arStore.addAll(response);
        }
        customerStoreAdapter=new CustomerStoreAdapterNew(getContext(),arStore);
        storerecycle.setAdapter(customerStoreAdapter);

        if(totalPages>=1&&pagNo<totalPages)
        {
            tvLoadMore.setVisibility(View.VISIBLE);
        }
        else
        {
            tvLoadMore.setVisibility(View.GONE);
        }
    }

    @Override
    public void followsuccess(String response) {
        if(TextUtils.isEmpty(Catid)){
            CallAPI(1);
        }else{
            CallAPI(3);
        }


//        if (getActivity()!=null) {
//            if (isNetworkConnected()) {
//                presenter.ViewAllStore(idholder);
//            } else {
//                showAlert("Please connect to internet.", R.style.DialogAnimation);
//            }
//        }
    }

    @Override
    public void error(String response) {
        if(getActivity() != null) {
            showAlert(response, R.style.DialogAnimation);
        }

    }

    @Override
    public void fail(String response) {
        if(getActivity() != null) {
            showAlert(response, R.style.DialogAnimation);
        }

    }
    PrettyDialog prettyDialog;
    private void showAlert(String message, int animationSource){
        if(prettyDialog!=null)
        {
            prettyDialog.dismiss();
            prettyDialog=null;
        }
        prettyDialog = new PrettyDialog(getActivity());
        prettyDialog.setCanceledOnTouchOutside(false);
        TextView title = (TextView) prettyDialog.findViewById(libs.mjn.prettydialog.R.id.tv_title);
        TextView tvmessage = (TextView) prettyDialog.findViewById(libs.mjn.prettydialog.R.id.tv_message);
        title.setTextSize(15);
        tvmessage.setTextSize(15);
        prettyDialog.setIconTint(R.color.colorPrimary);
        prettyDialog.setIcon(R.drawable.pdlg_icon_info);
        prettyDialog.setTitle("");
        prettyDialog.setMessage(message);
        prettyDialog.setAnimationEnabled(false);
        prettyDialog.getWindow().getAttributes().windowAnimations = animationSource;
        prettyDialog.addButton("Ok", R.color.black, R.color.white, new PrettyDialogCallback() {
            @Override
            public void onClick() {
                prettyDialog.dismiss();
            }
        }).show();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
String filterByStatus="";
    @Override
    public void onClick(View v) {
        if (v==rlFilter){//filtertext
            edTxtSearch.setText("");
            Intent intent = new Intent(getActivity(), FilterShowActivity.class);//2
            startActivity(intent);
        }else if (v==rlSortBy){//sortbytext
            edTxtSearch.setText("");
            diff=2;
            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.sort_dialog_activity);
            dialog.setTitle("Custom Dialog");
            RadioButton rdone=(RadioButton) dialog.findViewById(R.id.rdone);
            RadioButton rdtwo=(RadioButton) dialog.findViewById(R.id.rdtwo);
            RadioButton rdthree=(RadioButton) dialog.findViewById(R.id.rdthree);
//            RadioButton rdfour=(RadioButton) dialog.findViewById(R.id.rdfour);
            rdone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
//                    String Status="1";
                    pagNo=1;
                    filterByStatus="1";
                    CallAPI(2);
//                    presenter.ShortBy(idholder,Status);

                }
            });
            rdtwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
//                    String Status="2";
//                    presenter.ShortBy(idholder,Status);
                    pagNo=1;
                    filterByStatus="2";
                    CallAPI(2);

                }
            });
            rdthree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
//                    String Status="3";
//                    presenter.ShortBy(idholder,Status);
                    pagNo=1;
                    filterByStatus="3";
                    CallAPI(2);

                }
            });
//            rdfour.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
////                    String Status="4";
////                    presenter.ShortBy(idholder,Status);
//                    pagNo=1;
//                    filterByStatus="4";
//                    CallAPI(2);
//
//                }
//            });
            dialog.show();
        }
        else if (v==tvLoadMore){
            pagNo=pagNo+1;
            CallAPI(diff);
        }
    }
}
