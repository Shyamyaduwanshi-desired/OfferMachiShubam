package com.desired.offermachi.customer.view.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.SelectCategoryModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.CustomerFeedsPresenter;
import com.desired.offermachi.customer.view.activity.ActFeedsFilterShow;
import com.desired.offermachi.customer.view.adapter.CustomerTrendingAdapter;
import com.desired.offermachi.customer.view.activity.DashBoardActivity;
import com.desired.offermachi.customer.view.activity.FilterShowActivity;
import com.desired.offermachi.customer.view.adapter.CustomerTrendingAdapterNew;

import java.util.ArrayList;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class FeedsFragment extends Fragment implements View.OnClickListener, CustomerFeedsPresenter.FeedsList {
    View view;
    RecyclerView categoryrecycle;
    private CustomerTrendingAdapterNew customerTrendingAdapter;
    private CustomerFeedsPresenter presenter;
    String idholder;
    TextView /*sortbytext,filtertext,*/tvLoadMore;
// boolean checkFlag=false;
    RelativeLayout rlFilter,rlSortBy;
    EditText edTxtSearch;
 int loadPos=0;
    public FeedsFragment() {
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.feeds_activity, container, false);
        ((DashBoardActivity)getActivity()).setToolTittle("Feeds",2);

        initview();
        Listner();
        return  view;
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
                if(customerTrendingAdapter!=null)
                customerTrendingAdapter.filter(searchText);
//                adpt.filter(searchText);
            }
        });
    }

    public void CallAPI(int i)
    {
        if (isNetworkConnected()) {
            switch (i)
            {
                case 1:
                    presenter.ViewAllFeeds(idholder);//,loadPos
                    break;
                case 2:
                    break;
            }
        } else {
            showAlert("Please connect to internet.", R.style.DialogAnimation);
        }
    }


    private void initview(){
        presenter = new CustomerFeedsPresenter(getActivity(),FeedsFragment.this);
        User user = UserSharedPrefManager.getInstance(getActivity()).getCustomer();
        idholder= user.getId();
        tvLoadMore=view.findViewById(R.id.tv_load_more);
        edTxtSearch=view.findViewById(R.id.et_search);
        rlFilter=view.findViewById(R.id.rl_filter);
        rlSortBy=view.findViewById(R.id.rl_shorted_by);

        rlFilter.setOnClickListener(this);
        rlSortBy.setOnClickListener(this);

//        sortbytext=view.findViewById(R.id.sortby_text_id);
//        sortbytext.setOnClickListener(this);
//        filtertext=view.findViewById(R.id.filter_text_id);
//        filtertext.setOnClickListener(this);

        categoryrecycle=view.findViewById(R.id.categoryrecycleview);


//        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
//        categoryrecycle.setLayoutManager(gridLayoutManager1);
//        categoryrecycle.setItemAnimator(new DefaultItemAnimator());
//        categoryrecycle.setNestedScrollingEnabled(false);

        categoryrecycle.setHasFixedSize(true);
        categoryrecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryrecycle.setItemAnimator(new DefaultItemAnimator());
        categoryrecycle.setNestedScrollingEnabled(false);

        if (getActivity()!=null) {

            CallAPI(1);

//            if (isNetworkConnected()) {
//                presenter.ViewAllFeeds(idholder);
//            } else {
//                showAlert("Please connect to internet.", R.style.DialogAnimation);
//            }
        }
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(locationReceiver,
                new IntentFilter("Favourite"));
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(CouponReceiver,
                new IntentFilter("Refresh"));
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(CatReceiver,
                new IntentFilter("Category"));


    }
    public BroadcastReceiver CatReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String Catid = intent.getStringExtra("catid");
            presenter.Filter(idholder,Catid);
        }
    };
    public BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String fav = intent.getStringExtra("fav");
            String offerid = intent.getStringExtra("offerid");
            presenter.Favourite(idholder,offerid,fav);
        }
    };
    public BroadcastReceiver CouponReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (getActivity()!=null) {

                CallAPI(1);

//                if (isNetworkConnected()) {
//                    presenter.ViewAllFeeds(idholder);
//                } else {
//                    showAlert("Please connect to internet.", R.style.DialogAnimation);
//                }
            }
        }
    };
    @Override
    public void onClick(View v) {
        if (v==rlSortBy)
        {
            edTxtSearch.setText("");
            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.sort_dialog_activity);
            dialog.setTitle("Custom Dialog");
            RadioButton rdone=(RadioButton) dialog.findViewById(R.id.rdone);
            RadioButton rdtwo=(RadioButton) dialog.findViewById(R.id.rdtwo);
            RadioButton rdthree=(RadioButton) dialog.findViewById(R.id.rdthree);
            RadioButton rdfour=(RadioButton) dialog.findViewById(R.id.rdfour);
            rdone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    String Status="1";
                    presenter.ShortBy(idholder,Status);

                }
            });
            rdtwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    String Status="2";
                    presenter.ShortBy(idholder,Status);

                }
            });
            rdthree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    String Status="3";
                    presenter.ShortBy(idholder,Status);

                }
            });
            rdfour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    String Status="4";
                    presenter.ShortBy(idholder,Status);

                }
            });
            dialog.show();
        }else if (v==rlFilter){
            edTxtSearch.setText("");
            Intent intent = new Intent(getContext(), ActFeedsFilterShow.class);
//            Intent intent = new Intent(getContext(), FilterShowActivity.class);//6
            startActivity(intent);
        }
//        else if (v==tvLoadMore){
////           CallAPI(1);
//        }

    }
    ArrayList<SelectCategoryModel> arFeedList=new ArrayList<>();
//feeds success
    @Override
    public void success(ArrayList<SelectCategoryModel> response) {
//        customerTrendingAdapter=new CustomerTrendingAdapter(getContext(),response);
//        categoryrecycle.setAdapter(customerTrendingAdapter);
        arFeedList.clear();
        arFeedList=response;

        customerTrendingAdapter=new CustomerTrendingAdapterNew(getContext(),arFeedList);
        categoryrecycle.setAdapter(customerTrendingAdapter);
//        SetLoadmore();
    }


    @Override
    public void favsuc(String response) {
        //Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();
        if (getActivity()!=null) {

            CallAPI(1);

          /*  if (isNetworkConnected()) {
                presenter.ViewAllFeeds(idholder);
            } else {
                showAlert("Please connect to internet.", R.style.DialogAnimation);
            }*/
        }
    }

    @Override
    public void getsuc(String response) {

        if (getActivity()!=null) {

            CallAPI(1);

//            if (isNetworkConnected()) {
//                presenter.ViewAllFeeds(idholder);
//            } else {
//                showAlert("Please connect to internet.", R.style.DialogAnimation);
//            }
        }
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
    private void showAlert(String message, int animationSource){
        final PrettyDialog prettyDialog = new PrettyDialog(getContext());
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


}
