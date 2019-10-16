package com.desired.offermachi.retalier.view.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.desired.offermachi.R;
import com.desired.offermachi.customer.view.adapter.MultipleLocAdapter;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.AddStoreLocBean;
import com.desired.offermachi.retalier.model.CityBean;
import com.desired.offermachi.retalier.model.ImageBean;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.model.mylocation_model;
import com.desired.offermachi.retalier.presenter.CityPresenter;
import com.desired.offermachi.retalier.view.adapter.CityAdapter;
import com.desired.offermachi.retalier.view.adapter.MyLOcationADapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class MyLocationEditActivity extends AppCompatActivity implements View.OnClickListener, CityPresenter.CityInfo, SearchView.OnQueryTextListener, MultipleLocAdapter.MultipleLocClick {

    String idholder;
    private ArrayList<mylocation_model> mylocation_modelslist;
    private ProgressDialog pDialog;
    private static final String ROOT_URL = "http://offermachi.in/api/store_location_updateby_location_id";
    RecyclerView recyclerView;
    Spinner spCity/*,spLocation*/;
    private CityAdapter cityAdapter;
    private CityPresenter cityPresenter;
    private String city;
    private TextView tvMultiLocation;
    Dialog multipleLocDlg = null;
    RecyclerView rlStoreMultiLocation;
    Button btnOkay;
    private RecyclerView.Adapter mAdapter;
    Button bt_proceed;
    private String allAddressId = "";
    private EditText storeaddress, et_loction_name, et_address, et_phone_number;
    private String locationId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_location_detail);
        cityPresenter = new CityPresenter(MyLocationEditActivity.this, MyLocationEditActivity.this);

        init();
        mylocation_modelslist = new ArrayList<>();
    }

    private void init() {
        UserModel user = SharedPrefManagerLogin.getInstance(this).getUser();
        idholder = user.getId();
       /* recyclerView = (RecyclerView) findViewById(R.id.rv_location_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyLocationEditActivity.this));*/

/*
        rvLocationNm = view.findViewById(R.id.rv_location_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvLocationNm.setLayoutManager(mLayoutManager);
        rvLocationNm.setItemAnimator(new DefaultItemAnimator());
        rvLocationNm.setNestedScrollingEnabled(true);*/


     /*   spCity = findViewById(R.id.sp_city);
        tvMultiLocation = findViewById(R.id.tv_location);
        tvMultiLocation.setOnClickListener(this);*/
        bt_proceed = findViewById(R.id.bt_proceed);
        bt_proceed.setOnClickListener(this);
        storeaddress = findViewById(R.id.et_address);
        et_loction_name = findViewById(R.id.et_loction_name);
        et_phone_number = findViewById(R.id.et_phone_number);

        //  storeaddress = (EditText)findViewById(R.id.store_address_edt_id);
        storeaddress.setOnClickListener(this);
        storeaddress.setKeyListener(null);
        storeaddress.setFocusable(false);
        storeaddress.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        storeaddress.setClickable(true);
        Intent i = getIntent();
        locationId = i.getExtras().getString("LocationId");

        et_loction_name.setText(i.getExtras().getString("LocationName"));
        et_loction_name.setEnabled(false);
/*
        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Select Provider
                TextView txCityid = (TextView) view.findViewById(R.id.offerid);
                TextView txccityNm = (TextView) view.findViewById(R.id.offertype);
                city = txccityNm.getText().toString();
                String cityId = txCityid.getText().toString();
                cityPresenter.GetLocationViaCity(cityId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (isNetworkConnected()) {
            // typeBrandCategoryPresenter.sentRequestRegistration();
            cityPresenter.GetAllCity();
        } else {
            Toast.makeText(MyLocationEditActivity.this, "Please connect to internet.", Toast.LENGTH_SHORT).show();
        }
*/



/*

        if (isNetworkConnected()) {
            Fetchlocationdata();
        } else {
            Toast.makeText(MyLocationAddActivity.this, "Please connect to internet", Toast.LENGTH_SHORT).show();
        }
*/

    }

    private void Fetchlocationdata() {
        Cache cache = new DiskBasedCache(MyLocationEditActivity.this.getCacheDir(), 1024 * 1024);

        // Use HttpURLConnection as the HTTP client
        Network network = new BasicNetwork(new HurlStack());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (null == response || response.length() == 0) {
                            Toast.makeText(MyLocationEditActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                                JSONObject mainJson = new JSONObject(response);
                                String status = mainJson.getString("status");
                                String message = mainJson.getString("message");
                                if (status.equals("200")) {
                                    String result = mainJson.getString("result");

                                    JSONArray jsonArray = new JSONArray(result);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jobj = jsonArray.getJSONObject(i);
                                        String id = jobj.getString("id");
                                        String user_id = jobj.getString("user_id");
                                        String locality_name = jobj.getString("locality_name");
                                        String location_address = jobj.getString("location_address");
                                        String location_contact_phone = jobj.getString("location_contact_phone");

                                        mylocation_model mylocation_model = new mylocation_model(
                                                id, locality_name, location_address, location_contact_phone
                                        );

                                        mylocation_modelslist.add(mylocation_model);

                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            displayData1();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MyLocationEditActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", idholder);
                return params;
            }
        };
        RequestQueue queue = new RequestQueue(cache, network);
        queue.start();
        queue.add(stringRequest);

    }

    private void displayData1() {
        MyLOcationADapter myLOcationADapter = new MyLOcationADapter(getApplicationContext(), mylocation_modelslist);
        recyclerView.setAdapter(myLOcationADapter);
        if (myLOcationADapter.getItemCount() == 0) {
            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
        } else {

        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) MyLocationEditActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onClick(View v) {
        if (v == bt_proceed) {
            Editlocationdata(locationId,et_phone_number.getText().toString().trim());
          /*  JSONArray jsonArrayLocation = new JSONArray();

            for (int i = 0; i < arLocDetail.size(); i++) {

                if (TextUtils.isEmpty(allAddressId)) {//
                    allAddressId = arLocDetail.get(i).getLocId().trim();
                } else {
                    allAddressId = allAddressId + "," + arLocDetail.get(i).getLocId().trim();
                }

                JSONObject locObj = new JSONObject();
                try {
                    locObj.put("locality_id", arLocDetail.get(i).getLocId().trim());
                    locObj.put("address", arLocDetail.get(i).getAddress().trim());
                    locObj.put("persion_name", arLocDetail.get(i).getPersonNm().trim());
                    locObj.put("persion_contact", arLocDetail.get(i).getPhoneNumber().trim());
                    locObj.put("latitude", arLocDetail.get(i).getsLocLat().trim());
                    locObj.put("longitude", arLocDetail.get(i).getsLocLong().trim());
                    jsonArrayLocation.put(locObj);
                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }*/
        }
        if (v == storeaddress) {
            Intent intent = new Intent(MyLocationEditActivity.this, PickLocation.class);
            startActivityForResult(intent, 120);
        }
    }

    private void Editlocationdata(String locationId, String phone) {
        Cache cache = new DiskBasedCache(MyLocationEditActivity.this.getCacheDir(), 1024 * 1024);

        // Use HttpURLConnection as the HTTP client
        Network network = new BasicNetwork(new HurlStack());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (null == response || response.length() == 0) {
                            Toast.makeText(MyLocationEditActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                                JSONObject mainJson = new JSONObject(response);
                                String status = mainJson.getString("status");
                                String message = mainJson.getString("message");

                                if (status.equals("200")) {
                                    Toast.makeText(MyLocationEditActivity.this, "resonse...." + message, Toast.LENGTH_SHORT).show();
                                    finish();
                                    Intent i = new Intent(MyLocationEditActivity.this, MyLocationActivity.class);
                                    startActivity(i);
                                    finish();
                                   /* String result = mainJson.getString("result");

                                    JSONArray jsonArray = new JSONArray(result);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jobj = jsonArray.getJSONObject(i);
                                        String id = jobj.getString("id");
                                        String user_id = jobj.getString("user_id");
                                        String locality_name = jobj.getString("locality_name");
                                        String location_address = jobj.getString("location_address");
                                        String location_contact_phone = jobj.getString("location_contact_phone");

                                        mylocation_model mylocation_model = new mylocation_model(
                                                id, locality_name, location_address, location_contact_phone
                                        );

                                        mylocation_modelslist.add(mylocation_model);

                                    }*/
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // displayData1();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MyLocationEditActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", idholder);
                params.put("locality_id", locationId);
                params.put("address", storeaddress.getText().toString().trim());
                params.put("persion_contact", phone);
                params.put("latitude", lati);
                params.put("longitude", longi);
                Log.e("params...", params.toString());
                return params;
            }
        };
        /*{
	   "user_id":"197",
		"id":"1",
		"locality_id":"12",
		"address":"d2",
		"persion_name":"name",
		"persion_contact":"persion_contact",
		"latitude":"2",
		"longitude":"2"
}*/
        RequestQueue queue = new RequestQueue(cache, network);
        queue.start();
        queue.add(stringRequest);
    }

    public void MulitiSelectionDlg() {
        if (multipleLocDlg != null) {
            multipleLocDlg.dismiss();
            multipleLocDlg = null;
        }
        multipleLocDlg = new Dialog(MyLocationEditActivity.this);
        multipleLocDlg.setContentView(R.layout.multiple_location_selection_dlg);
        multipleLocDlg.setTitle("Select Store Location Dialog");
        rlStoreMultiLocation = multipleLocDlg.findViewById(R.id.cv_location);

        btnOkay = multipleLocDlg.findViewById(R.id.bt_proceed);
        SearchView searchView = multipleLocDlg.findViewById(R.id.search);
        searchView.setOnQueryTextListener(MyLocationEditActivity.this);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyLocationEditActivity.this);
        rlStoreMultiLocation.setLayoutManager(mLayoutManager);
        rlStoreMultiLocation.setItemAnimator(new DefaultItemAnimator());

        SetmultiLocAdapter();

        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetAllSelectedLocation();

            }

        });

        multipleLocDlg.show();
    }

    String sAllCityId = "";
    String sAllCityNm = "";
    ArrayList<AddStoreLocBean> arLocDetail = new ArrayList<>();


    public void GetAllSelectedLocation() {
        if (arLocation != null) {
            sAllCityId = "";
            sAllCityNm = "";
            AddStoreLocBean bean;
            arLocDetail.clear();
            for (int i = 0; i < arLocation.size(); i++) {
                if (arLocation.get(i).isSelected()) {
                    if (TextUtils.isEmpty(sAllCityId)) {
                        sAllCityId = arLocation.get(i).getId();
                        sAllCityNm = arLocation.get(i).getCity_name();

                    } else {
                        sAllCityId = sAllCityId + "," + arLocation.get(i).getId();
                        sAllCityNm = sAllCityNm + "," + arLocation.get(i).getCity_name();
                    }

                    bean = new AddStoreLocBean();
                    bean.setsLocNm(arLocation.get(i).getCity_name());//Compaign Location name
                    bean.setLocId(arLocation.get(i).getId());//Compaign Location name
                    bean.setsLocLat("22.71246");
                    bean.setsLocLong("75.86491");
                    bean.setAddress("");
                    bean.setPhoneNumber("");
                    bean.setPersonNm("");
                    arLocDetail.add(bean);
                }
            }
            if (multipleLocDlg != null) {
                multipleLocDlg.dismiss();
                if (sAllCityNm.length() > 70) {
                    tvMultiLocation.setText(sAllCityNm.substring(0, 70) + "...");
                } else {
                    tvMultiLocation.setText(sAllCityNm);
                }
            }
            Log.e("", "sAllCityId= " + sAllCityId);
            SetAdapter();
        }
    }

    public void SetAdapter() {

        int size = arLocDetail.size();
        Log.e("", "size= " + size);
        mAdapter = new StoreLocationDetailsAdapter(MyLocationEditActivity.this, arLocDetail);//,this
        recyclerView.setAdapter(mAdapter);
    }

    MultipleLocAdapter multiLocAdpter;
    ArrayList<ImageBean> arImageNm = new ArrayList<>();
    ArrayList<CityBean> arLocation = new ArrayList<>();

    public void SetmultiLocAdapter() {
        int size = arImageNm.size();
        Log.e("", "size= " + size);
        multiLocAdpter = new MultipleLocAdapter(MyLocationEditActivity.this, arLocation, this);
        rlStoreMultiLocation.setAdapter(multiLocAdpter);

    }

    @Override
    public void success(ArrayList<CityBean> response, String status) {
        switch (status) {
            case "1":
                cityAdapter = new CityAdapter(MyLocationEditActivity.this, response);
                spCity.setAdapter(cityAdapter);
                break;
            case "2":
                arLocation.clear();
                arLocation = response;
                tvMultiLocation.setText(arLocation.get(0).getCity_name());

//               locationAdapter = new LocationAdapter(getContext(),0, arLocation);
//               spLocation.setAdapter(locationAdapter);
                break;
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<CityBean> temp = new ArrayList();
        for (CityBean d : arLocation) {
            String cityname = d.getCity_name().toLowerCase().replace(" ", "");

            if (cityname.contains(newText) || cityname.contains(newText)) {
                temp.add(d);

            }
        }
        multiLocAdpter.updateList(temp);

        return true;
    }

    @Override
    public void onMultipleLocClick(CityBean cityBean) {
        for (int i = 0; i < arLocation.size(); i++) {
            if (arLocation.get(i).getId() == cityBean.getId()) {
                if (arLocation.get(i).isSelected()) {
                    arLocation.get(i).setSelected(false);
                } else {
                    arLocation.get(i).setSelected(true);
                }
                break;
            }
        }
        multiLocAdpter.notifyDataSetChanged();
    }


    private void showAlert(String message, int animationSource) {
        final PrettyDialog prettyDialog = new PrettyDialog(MyLocationEditActivity.this);
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


    private void Editlocationdata(JSONArray jsonArrayLocation) {
        Cache cache = new DiskBasedCache(MyLocationEditActivity.this.getCacheDir(), 1024 * 1024);

        // Use HttpURLConnection as the HTTP client
        Network network = new BasicNetwork(new HurlStack());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (null == response || response.length() == 0) {
                            Toast.makeText(MyLocationEditActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                                JSONObject mainJson = new JSONObject(response);
                                String status = mainJson.getString("status");
                                String message = mainJson.getString("message");

                                if (status.equals("200")) {
                                    Toast.makeText(MyLocationEditActivity.this, "resonse...." + message, Toast.LENGTH_SHORT).show();
                                    finish();
                                    Intent i = new Intent(MyLocationEditActivity.this, MyLocationActivity.class);
                                    startActivity(i);
                                    finish();
                                   /* String result = mainJson.getString("result");

                                    JSONArray jsonArray = new JSONArray(result);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jobj = jsonArray.getJSONObject(i);
                                        String id = jobj.getString("id");
                                        String user_id = jobj.getString("user_id");
                                        String locality_name = jobj.getString("locality_name");
                                        String location_address = jobj.getString("location_address");
                                        String location_contact_phone = jobj.getString("location_contact_phone");

                                        mylocation_model mylocation_model = new mylocation_model(
                                                id, locality_name, location_address, location_contact_phone
                                        );

                                        mylocation_modelslist.add(mylocation_model);

                                    }*/
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // displayData1();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MyLocationEditActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", idholder);
                params.put("store_locations", jsonArrayLocation.toString());
                Log.e("params...", params.toString());
                return params;
            }
        };
        RequestQueue queue = new RequestQueue(cache, network);
        queue.start();
        queue.add(stringRequest);

    }

    String lati;
    String longi;

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == 120) {
            if(data!=null){
                lati = data.getStringExtra("loc_lati");
                longi = data.getStringExtra("loc_longi");
                storeaddress.setText(data.getStringExtra("loc_name"));
            }

        } else if (reqCode == 130) {
//             lati = data.getStringExtra("loc_lati");
//             longi = data.getStringExtra("loc_longi");
            Log.e("", "lat= " + data.getStringExtra("loc_lati") + " long= " + data.getStringExtra("loc_longi"));
            arLocDetail.get(postionApt).setAddress(data.getStringExtra("loc_name"));
            arLocDetail.get(postionApt).setsLocLat(data.getStringExtra("loc_lati"));
            arLocDetail.get(postionApt).setsLocLong(data.getStringExtra("loc_longi"));
            mAdapter.notifyDataSetChanged();
        }
    }


    //for multiple location
    int postionApt = 0;

    public class StoreLocationDetailsAdapter extends RecyclerView.Adapter<StoreLocationDetailsAdapter.MyViewHolder> {
        private List<AddStoreLocBean> list;
        private Context context;
//        private LocDetailClick planClick;

        public StoreLocationDetailsAdapter(Context context, List<AddStoreLocBean> list/*, LocDetailClick planClick*/) {
            this.context = context;
            this.list = list;
//            this.planClick = planClick;
        }

        @Override
        public StoreLocationDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_add_location_detail, parent, false);

            return new StoreLocationDetailsAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(StoreLocationDetailsAdapter.MyViewHolder holder, final int position) {

            holder.etLocNm.setText(list.get(position).getsLocNm());

            holder.etLocNm.setKeyListener(null);
            holder.etLocNm.setFocusable(false);
            holder.etLocNm.setFocusableInTouchMode(false);
            holder.etLocNm.setClickable(true);

            holder.etPerNm.setText(list.get(position).getPersonNm());
            holder.etAddress.setText(list.get(position).getAddress());
            holder.etPhoneNumber.setText(list.get(position).getPhoneNumber());

            holder.etAddress.setKeyListener(null);
            holder.etAddress.setFocusable(false);
            holder.etAddress.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            holder.etAddress.setClickable(true);


            holder.etPerNm.addTextChangedListener(new TextWatcher() {

                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {

                }


                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {

                }

                public void afterTextChanged(Editable s) {

                    list.get(position).setPersonNm(s.toString());
                    arLocDetail.get(position).setPersonNm(s.toString());
                }
            });

            holder.etAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postionApt = position;
                    Intent intent = new Intent(MyLocationEditActivity.this, PickLocation.class);
                    startActivityForResult(intent, 130);
                }
            });
           /* holder.etAddress.addTextChangedListener(new TextWatcher() {
                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {
                }
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }
                public void afterTextChanged(Editable s) {
                    list.get(position).setAddress(s.toString());
                }
            });*/

            holder.etPhoneNumber.addTextChangedListener(new TextWatcher() {

                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {
                }

                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                public void afterTextChanged(Editable s) {

                    list.get(position).setPhoneNumber(s.toString());
                    arLocDetail.get(position).setPhoneNumber(s.toString());
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            //        TextView tvLocNm;
            EditText etLocNm, etPerNm, etAddress, etPhoneNumber;
            LinearLayout lyMain;
            RelativeLayout rlAdd;

            public MyViewHolder(View view) {
                super(view);
//            tvLocNm = view.findViewById(R.id.tv_loc_nm);
                etLocNm = view.findViewById(R.id.et_loction_name);
                etPerNm = view.findViewById(R.id.et_contact_person);
                etAddress = view.findViewById(R.id.et_address);
                etPhoneNumber = view.findViewById(R.id.et_phone_number);
                lyMain = view.findViewById(R.id.ly_main);
                rlAdd = view.findViewById(R.id.rl_add_location);

            }
        }
//        public interface AddAddressLocClick{
//            void onAddAddressLocClick(int possition);
//        }
    }
}