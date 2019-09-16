package com.desired.offermachi.customer.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.CategoryListModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.CustomerCategoryListPresenter;
import com.desired.offermachi.customer.view.activity.CategoryActivity;
import com.desired.offermachi.customer.view.activity.MapActivity;
import com.desired.offermachi.customer.view.adapter.CategortListAdapter;
import com.desired.offermachi.customer.view.adapter.MultiChoiceCategortListAdapter;
import com.desired.offermachi.customer.view.adapter.SmartShoppingAdapter;
import com.desired.offermachi.customer.model.smart_shopping_model;
import com.desired.offermachi.customer.view.activity.DashBoardActivity;
import com.desired.offermachi.customer.view.adapter.SmartShoppingCategoryListAdapter;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


public class SmartShoppingFragment extends Fragment implements View.OnClickListener,CustomerCategoryListPresenter.CustomerCategoryList, CompoundButton.OnCheckedChangeListener,MultiChoiceCategortListAdapter.AdapterClick {
    View view;
    RecyclerView product_recyclerview;
    private SmartShoppingCategoryListAdapter smartShoppingCategoryListAdapter;
    private CustomerCategoryListPresenter presenter;
    private String idholder, followsatus, Catid;
    Switch smartswitch;
    String Nameholder, EmailHolder, PhoneHolder, AddressHolder, GenderHolder, ImageHolder, SmartShoppingHolder,SoundHolder;
Context context;
    Button btProceed;
    public SmartShoppingFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.smart_shopping_fragment, container, false);
        ((DashBoardActivity) getActivity()).setToolTittle("Smart Shopping", 2);
        context=getActivity();
        initview();
        return view;
    }

    private void initview() {
        btProceed = view.findViewById(R.id.bt_proceed);
        presenter = new CustomerCategoryListPresenter(getContext(), SmartShoppingFragment.this);
        User user = UserSharedPrefManager.getInstance(getContext()).getCustomer();
        idholder = user.getId();
        Nameholder = user.getUsername();
        EmailHolder = user.getEmail();
        PhoneHolder = user.getMobile();
        AddressHolder = user.getAddress();
        GenderHolder = user.getGender();
        ImageHolder = user.getProfile();
        SmartShoppingHolder = user.getSmartShopping();
        SoundHolder=user.getNotificationsound();
        product_recyclerview = (RecyclerView) view.findViewById(R.id.category_recycler_id);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4, LinearLayoutManager.VERTICAL, false);
        product_recyclerview.setLayoutManager(gridLayoutManager);
        product_recyclerview.setItemAnimator(new DefaultItemAnimator());
        smartswitch = view.findViewById(R.id.smartswitch);
        smartswitch.setOnCheckedChangeListener(this);
        btProceed.setOnClickListener(this);
        if (SmartShoppingHolder.equals("1")) {
            smartswitch.setChecked(true);
        }
        if (isNetworkConnected()) {
            presenter.GetCategoryList(idholder);//for show all category
        } else {
            showAlert("Please connect to internet.", R.style.DialogAnimation);
        }
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(followReceiver,
                new IntentFilter("Follow"));
    }

    public BroadcastReceiver followReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            followsatus = intent.getStringExtra("followstatus");
            Catid = intent.getStringExtra("catid");
            if (isNetworkConnected()) {
                presenter.CategoryFollow(idholder, Catid, followsatus);
            } else {
                showAlert("Please connect to internet.", R.style.DialogAnimation);
            }

        }
    };

    @Override
    public void followsuccess(String response) {
     /*   finish();
        startActivity(getIntent());*/
        if (isNetworkConnected()) {
            presenter.GetCategoryList(idholder);//for show all category
        } else {
            showAlert("Please connect to internet.", R.style.DialogAnimation);
        }
    }



    @Override
    public void error(String response) {
        showAlert(response, R.style.DialogAnimation);
    }

    @Override
    public void fail(String response) {
        showAlert(response, R.style.DialogAnimation);
    }

    private void showAlert(String message, int animationSource) {
        final PrettyDialog prettyDialog = new PrettyDialog(getActivity());
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
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            User user = new User(
                    idholder,
                    Nameholder,
                    EmailHolder,
                    PhoneHolder,
                    AddressHolder,
                    GenderHolder,
                    ImageHolder,
                    "1",
                    SoundHolder
                    ,
                    "1"//shyam 11/9/19
            );
            UserSharedPrefManager.getInstance(getActivity()).userLogin(user);
        } else {
            User user = new User(
                    idholder,
                    Nameholder,
                    EmailHolder,
                    PhoneHolder,
                    AddressHolder,
                    GenderHolder,
                    ImageHolder,
                    "0",
                    SoundHolder,
                    "1"//shyam 11/9/19
            );
            UserSharedPrefManager.getInstance(getActivity()).userLogin(user);
          startActivity(new Intent(getActivity(),CategoryActivity.class));

          getActivity().finish();
        }
    }
    ArrayList<CategoryListModel> arCatList=new ArrayList<>();
    private MultiChoiceCategortListAdapter categortListAdapter=null;
    @Override
    public void success(ArrayList<CategoryListModel> response) {
//        smartShoppingCategoryListAdapter = new SmartShoppingCategoryListAdapter(getActivity(), response);
//        product_recyclerview.setAdapter(smartShoppingCategoryListAdapter);

        arCatList.clear();
        arCatList=response;
        categortListAdapter = new MultiChoiceCategortListAdapter(context,arCatList,this);
        product_recyclerview.setAdapter(categortListAdapter);


    }
    //adapter click
    @Override
    public void onClick(int position) {
        if(arCatList.get(position).isCheckStatus())
        {
            arCatList.get(position).setCheckStatus(false);
        }
        else
        {
            arCatList.get(position).setCheckStatus(true);
        }
        categortListAdapter.notifyDataSetChanged();

    }
//view click listern
    @Override
    public void onClick(View v) {

        if (v==btProceed){
            getAllSelectedId();
        }
    }
    String sAllCatId="",sSingleCateId;
    String sAllCatNm="",sSingleCateNm;
    String sAllCatBannerimage="",sSingleCateBannerimage;

    public void getAllSelectedId()
    {
        sAllCatId="";
        sAllCatNm="";
        sAllCatBannerimage="";

        sSingleCateId="";
        sSingleCateNm="";
        sSingleCateBannerimage="";

        for(int i=0;i<arCatList.size();i++)
        {
            if(arCatList.get(i).isCheckStatus()) {
                if (TextUtils.isEmpty(sAllCatId)) {
                    sAllCatId = arCatList.get(i).getCatid();
                    sAllCatNm = arCatList.get(i).getCatname();
                    sAllCatBannerimage = arCatList.get(i).getBannerimage();

                    sSingleCateId = arCatList.get(i).getCatid();
                    sSingleCateNm= arCatList.get(i).getCatname();
                    sSingleCateBannerimage= arCatList.get(i).getBannerimage();

                } else {
                    sAllCatId = sAllCatId + "," + arCatList.get(i).getCatid();
                    sAllCatNm = sAllCatNm + "," + arCatList.get(i).getCatname();
                    sAllCatBannerimage = sAllCatBannerimage + "," + arCatList.get(i).getBannerimage();
                }
            }
        }
        Log.e("","sAllCatId= "+sAllCatId);

        if(TextUtils.isEmpty(sAllCatId))
        {
            Toast.makeText(getActivity(), "please select a category", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent myIntent = new Intent(getActivity(), MapActivity.class);
            myIntent.putExtra("catid",sSingleCateId);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(myIntent);
        }
    }
}
