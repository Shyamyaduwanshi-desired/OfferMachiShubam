package com.desired.offermachi.retalier.view.fragment;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.desired.offermachi.R;
import com.desired.offermachi.retalier.constant.FileUtil;
import com.desired.offermachi.retalier.model.AddStoreLocBean;
import com.desired.offermachi.retalier.model.BrandModel;
import com.desired.offermachi.retalier.model.CategoryModel;
import com.desired.offermachi.retalier.model.CityBean;
import com.desired.offermachi.retalier.model.ImageBean;
import com.desired.offermachi.retalier.model.OfferTypeModel;
import com.desired.offermachi.retalier.presenter.CityPresenter;
import com.desired.offermachi.retalier.presenter.SignupPresenter;
import com.desired.offermachi.retalier.presenter.TypeBrandCategoryPresenter;
import com.desired.offermachi.retalier.view.adapter.CityAdapter;
import com.desired.offermachi.retalier.view.adapter.SelectedImageAdapter;
import com.desired.offermachi.retalier.view.adapter.StoreLocationDetailsAdapter;
import com.desired.offermachi.retalier.view.adapter.CategoryAdapter;
import com.desired.offermachi.service.GeocodingLocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

import static android.app.Activity.RESULT_OK;

public class RegistrationStoreDetailsFrgment extends Fragment implements LocationListener, View.OnClickListener,SignupPresenter.SignUp, TypeBrandCategoryPresenter.TypeBrandCategory, StoreLocationDetailsAdapter.LocDetailClick, CityPresenter.CityInfo,SelectedImageAdapter.ImageClick {
    View view;
    String android_id;
    private SignupPresenter presenter;
    EditText storename,storecontact,storeaddress/*,cityedt*/,aboutstore;
    Button registerbutton;
    String shop_name,shop_contact_number,address,city,sLocation,about_store,shopopentime,shopclosetime;
    String name,mobile,email,password;
    private String picture = "",pictureLogo = "";
    private File file, compressedImage;
    ImageView imagestore,ivStoreLogo;
    RelativeLayout imagepick,rlStoreLogo;
    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private static int mrngtimeHour;
    private static int mrngtimeMinute;
    private static int mrngtoHour;
    private static int mrngtoMinute;
    String starttimeset,closetimeset;
    String shopdays="Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday";
    String Mondayopentime="00:00 AM",Tuesdayopentime="00:00 AM",Wednesdayopentime="00:00 AM",Thursdayopentime="00:00 AM",Fridayopentime="00:00 AM",Saturdayopentime="00:00 AM",Sundayopentime="00:00 AM" ;
    String Mondayclosetime="00:00 PM",Tuesdayclosetime="00:00 PM",Wednesdayclosetime="00:00 PM",Thursdayclosetime="00:00 PM",Fridayclosetime="00:00 PM",Saturdayclosetime="00:00 PM",Sundayclosetime="00:00 PM";
    LinearLayout lyMonday,lyTuesday,lyWednesday,lyThursday,lyFriday,lySaturday,lySunday;
    ImageView monplus,tuesplus,wedplus,thurplus,friplus,saturplus;
    EditText etMonday,etTuesday,etWednesday,etThursday,etFriday,etSaturday,etSunday;
    TextView Mondaystart_Time,Mondayend_Time,Tuesdaystart_Time,Tuesdayend_Time,Wednesdaystart_Time,Wednesdayend_Time,Thursdaystart_Time,Thursdayend_Time,Fridaystart_Time,Fridayend_Time,Saturdaystart_Time,Saturdayend_Time,Sundaystart_Time,Sundayend_Time;
    TextView txtstorecategory;
    android.app.AlertDialog alertDialog;
    LocationManager locationManager;
    String lati ;
    String longi ;
    Spinner categoryspinner,spCity,spLocation;
    String categoryid;
    private CategoryAdapter categoryAdapter;
    private CityAdapter cityAdapter;
    private LocationAdapter locationAdapter;
    private TypeBrandCategoryPresenter typeBrandCategoryPresenter;
    private CityPresenter cityPresenter;

    RecyclerView rvLocationNm;
//    RecyclerView rvImage;
    private RecyclerView.Adapter mAdapter;
    int diffLogoPicture=1;
    ArrayList<ImageBean>arImageNm=new ArrayList<>();
    RecyclerView rvImage;
    String allImagNm="";
    public RegistrationStoreDetailsFrgment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.retalier_store_activity, container, false);
        typeBrandCategoryPresenter=new TypeBrandCategoryPresenter(getContext(),RegistrationStoreDetailsFrgment.this);
        presenter = new SignupPresenter(getContext(), RegistrationStoreDetailsFrgment.this);
        cityPresenter = new CityPresenter(getContext(), RegistrationStoreDetailsFrgment.this);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(locationReceiver,
                new IntentFilter("Data"));
        getLocation();
        initView() ;
        AddItem();
        return  view;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isStoragePermissionGranted() {
        if (ActivityCompat.checkSelfPermission(getActivity(),android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void initView() {
       /* android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);*/
        storename=(EditText)view.findViewById(R.id.store_name_edt_id);
        categoryspinner = view.findViewById(R.id.categoryspinner);
        spCity = view.findViewById(R.id.sp_city);
        spLocation = view.findViewById(R.id.sp_location);
        storecontact=(EditText)view.findViewById(R.id.store_contact_edt_id);
        storeaddress=(EditText)view.findViewById(R.id.store_address_edt_id);
//        cityedt=(EditText)view.findViewById(R.id.city_edt_id);

        //days
        etMonday=(EditText)view.findViewById(R.id.editmonday);
        etTuesday=(EditText)view.findViewById(R.id.edittuesday);
        etWednesday=(EditText)view.findViewById(R.id.editwednesday);
        etThursday=(EditText)view.findViewById(R.id.editthursday);
        etFriday=(EditText)view.findViewById(R.id.editfriday);
        etSaturday=(EditText)view.findViewById(R.id.editsaturday);
        etSunday=(EditText)view.findViewById(R.id.editsunday);
        //starttime
        Mondaystart_Time = (TextView) view.findViewById(R.id.editmondaytime);
        Mondaystart_Time.setOnClickListener(this);

        Tuesdaystart_Time = (TextView) view.findViewById(R.id.edittuesdaytime);
        Tuesdaystart_Time.setOnClickListener(this);

        Wednesdaystart_Time = (TextView) view.findViewById(R.id.editwednesdaytime);
        Wednesdaystart_Time.setOnClickListener(this);

        Thursdaystart_Time = (TextView) view.findViewById(R.id.editthursdaytime);
        Thursdaystart_Time.setOnClickListener(this);

        Fridaystart_Time = (TextView) view.findViewById(R.id.editfridaytime);
        Fridaystart_Time.setOnClickListener(this);

        Saturdaystart_Time = (TextView) view.findViewById(R.id.editsaturdaytime);
        Saturdaystart_Time.setOnClickListener(this);

        Sundaystart_Time = (TextView) view.findViewById(R.id.editsundaytime);
        Sundaystart_Time.setOnClickListener(this);
        //endtime
        Mondayend_Time = (TextView) view.findViewById(R.id.editmondaytimeto);
        Mondayend_Time.setOnClickListener(this);

        Tuesdayend_Time = (TextView) view.findViewById(R.id.edittuesdaytimeto);
        Tuesdayend_Time.setOnClickListener(this);

        Wednesdayend_Time = (TextView) view.findViewById(R.id.editwednesdaytimeto);
        Wednesdayend_Time.setOnClickListener(this);
        Thursdayend_Time = (TextView) view.findViewById(R.id.editthursdaytimeto);
        Thursdayend_Time.setOnClickListener(this);
        Fridayend_Time = (TextView) view.findViewById(R.id.editfridaytimeto);
        Fridayend_Time.setOnClickListener(this);
        Saturdayend_Time = (TextView) view.findViewById(R.id.editsaturdaytimeto);
        Saturdayend_Time.setOnClickListener(this);
        Sundayend_Time = (TextView) view.findViewById(R.id.editsundaytimeto);
        Sundayend_Time.setOnClickListener(this);
        //layouts
        lyMonday=view.findViewById(R.id.lymonday);
        lyTuesday=view.findViewById(R.id.lytuesday);
        lyWednesday=view.findViewById(R.id.lywednesday);
        lyThursday=view.findViewById(R.id.lythursday);
        lyFriday=view.findViewById(R.id.lyfriday);
        lySaturday=view.findViewById(R.id.lysaturday);
        lySunday=view.findViewById(R.id.lysunday);
        //hideshowbtn

        monplus=view.findViewById(R.id.monplus);
        monplus.setOnClickListener(this);
        tuesplus=view.findViewById(R.id.tuesplus);
        tuesplus.setOnClickListener(this);
        wedplus=view.findViewById(R.id.wedplus);
        wedplus.setOnClickListener(this);
        thurplus=view.findViewById(R.id.thurplus);
        thurplus.setOnClickListener(this);
        friplus=view.findViewById(R.id.friplus);
        friplus.setOnClickListener(this);
        saturplus=view.findViewById(R.id.saturplus);
        saturplus.setOnClickListener(this);
        aboutstore=(EditText)view.findViewById(R.id.about_store_name_id);
        imagestore=view.findViewById(R.id.imagestore);
        ivStoreLogo=view.findViewById(R.id.iv_store_logo);
        imagepick=view.findViewById(R.id.pickly);
        rlStoreLogo=view.findViewById(R.id.rl_store_logo);
        registerbutton=(Button)view.findViewById(R.id.registerbutton_button_id);
        registerbutton.setOnClickListener(this);
        imagepick.setOnClickListener(this);
        rlStoreLogo.setOnClickListener(this);

        rvImage = view.findViewById(R.id.rv_image_list);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvImage.setLayoutManager(mLayoutManager1);
        rvImage.setItemAnimator(new DefaultItemAnimator());
        rvImage.setHasFixedSize(true);
        rvImage.setNestedScrollingEnabled(true);


        rvLocationNm = view.findViewById(R.id.rv_location_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvLocationNm.setLayoutManager(mLayoutManager);
        rvLocationNm.setItemAnimator(new DefaultItemAnimator());

        categoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Select Provider
                TextView txcategoryid = (TextView) view.findViewById(R.id.offerid);
                categoryid = txcategoryid.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Select Provider
                TextView txCityid = (TextView) view.findViewById(R.id.offerid);
                TextView txccityNm = (TextView) view.findViewById(R.id.offertype);
                city = txccityNm.getText().toString();
                String cityId=txCityid.getText().toString();
                cityPresenter.GetLocationViaCity(cityId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Select Provider
                TextView txCityid = (TextView) view.findViewById(R.id.tv_loc_id);
                TextView txccityNm = (TextView) view.findViewById(R.id.tv_loc_nm);
                TextView tvOkay = (TextView) view.findViewById(R.id.tv_okay);
                tvOkay.setVisibility(View.GONE);
                sLocation = txccityNm.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (isNetworkConnected()) {
            typeBrandCategoryPresenter.sentRequestRegistration();
            cityPresenter.GetAllCity();
        }  else {
            Toast.makeText(getContext(), "Please connect to internet.", Toast.LENGTH_SHORT).show();
        }
    }
    public BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            name = intent.getStringExtra("name");
            email = intent.getStringExtra("email");
            mobile = intent.getStringExtra("mobile");
            password = intent.getStringExtra("password");
        }
    };

    private void openGallery(){
        Intent photoPickerIntent = new Intent();
        photoPickerIntent.setType("image/*");
        photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);//
        // photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 100);
    }
    @Override
    public void onClick(View v) {
        if (v == registerbutton) {
            Registraionvalid();
        }else if (v==monplus){
            lyTuesday.setVisibility(View.VISIBLE);

        }else if (v==tuesplus){
            lyWednesday.setVisibility(View.VISIBLE);
        }
        else if (v==wedplus){
            lyThursday.setVisibility(View.VISIBLE);
        }
        else if (v==thurplus){
            lyFriday.setVisibility(View.VISIBLE);
        }
        else if (v==friplus){
            lySaturday.setVisibility(View.VISIBLE);
        }
        else if (v==saturplus){
            lySunday.setVisibility(View.VISIBLE);
        }
        else if (v== Mondaystart_Time){
            starttimeset="0";
            new TimePickerDialog(getContext(), mTimeSetListener, mrngtimeHour, mrngtimeMinute, false).show();
        }
        else if (v==Mondayend_Time){
            closetimeset="0";
            new TimePickerDialog(getContext(), mTimeSetListener1, mrngtoHour, mrngtoMinute, false).show();
        }
        else if (v== Tuesdaystart_Time){
            starttimeset="1";
            new TimePickerDialog(getContext(), mTimeSetListener, mrngtimeHour, mrngtimeMinute, false).show();
        }
        else if (v==Tuesdayend_Time){
            closetimeset="1";
            new TimePickerDialog(getContext(), mTimeSetListener1, mrngtoHour, mrngtoMinute, false).show();
        }
        else if (v== Wednesdaystart_Time){
            starttimeset="2";
            new TimePickerDialog(getContext(), mTimeSetListener, mrngtimeHour, mrngtimeMinute, false).show();
        }
        else if (v==Wednesdayend_Time){
            closetimeset="2";
            new TimePickerDialog(getContext(), mTimeSetListener1, mrngtoHour, mrngtoMinute, false).show();
        }
        else if (v== Thursdaystart_Time){
            starttimeset="3";
            new TimePickerDialog(getContext(), mTimeSetListener, mrngtimeHour, mrngtimeMinute, false).show();
        }
        else if (v==Thursdayend_Time){
            closetimeset="3";
            new TimePickerDialog(getContext(), mTimeSetListener1, mrngtoHour, mrngtoMinute, false).show();
        }
        else if (v== Fridaystart_Time){
            starttimeset="4";
            new TimePickerDialog(getContext(), mTimeSetListener, mrngtimeHour, mrngtimeMinute, false).show();
        }
        else if (v==Fridayend_Time){
            closetimeset="4";
            new TimePickerDialog(getContext(), mTimeSetListener1, mrngtoHour, mrngtoMinute, false).show();
        }
        else if (v== Saturdaystart_Time){
            starttimeset="5";
            new TimePickerDialog(getContext(), mTimeSetListener, mrngtimeHour, mrngtimeMinute, false).show();
        }
        else if (v==Saturdayend_Time){
            closetimeset="5";
            new TimePickerDialog(getContext(), mTimeSetListener1, mrngtoHour, mrngtoMinute, false).show();
        }
        else if (v== Sundaystart_Time){
            starttimeset="6";
            new TimePickerDialog(getContext(), mTimeSetListener, mrngtimeHour, mrngtimeMinute, false).show();
        }
        else if (v==Sundayend_Time){
            closetimeset="6";
            new TimePickerDialog(getContext(), mTimeSetListener1, mrngtoHour, mrngtoMinute, false).show();
        }
        else if (v==imagepick){//for banner
            diffLogoPicture=2;
            if (Build.VERSION.SDK_INT >= 23) {
                if (isStoragePermissionGranted()) {
                    openGallery();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), permissions, 100);
                }
            } else {
                openGallery();
            }
        }
        else if (v==rlStoreLogo){//for logo
            diffLogoPicture=1;
            if (Build.VERSION.SDK_INT >= 23) {
                if (isStoragePermissionGranted()) {
                    openGallery();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), permissions, 100);
                }
            } else {
                openGallery();
            }
        }

        /*else if (v==txtstorecategory){
            selectcategory();
        }*/

    }
    private void Registraionvalid() {
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        android_id=sharedPreferences.getString(getString(R.string.FCM_TOKEN),"");
        Log.e("register", "android_id==="+android_id );
        shop_name = storename.getText().toString();
        shop_contact_number = storecontact.getText().toString().trim();
        address = storeaddress.getText().toString();
//        city = cityedt.getText().toString();
        about_store= aboutstore.getText().toString();
        shopopentime=Mondayopentime+","+Tuesdayopentime+","+Wednesdayopentime+","+Thursdayopentime+","+Fridayopentime+","+Saturdayopentime+","+Sundayopentime;
        shopclosetime=Mondayclosetime+","+Tuesdayclosetime+","+Wednesdayclosetime+","+Thursdayclosetime+","+Fridayclosetime+","+Saturdayclosetime+","+Sundayclosetime;
        if (TextUtils.isEmpty(shop_name)){
            storename.requestFocus();
            storename.setError("Please enter your shop name");
        }
        else if (TextUtils.isEmpty(shop_contact_number)){
            storecontact.requestFocus();
            storecontact.setError("Please enter your mobile");
        }
        else if (!isValidMobile(shop_contact_number)){
            storecontact.requestFocus();
            storecontact.setError("Please enter valid mobile number");
        }
        else if (shop_contact_number.length() < 10){
            storecontact.requestFocus();
            storecontact.setError("Please enter 10 digit mobile number");
        }
        else if (TextUtils.isEmpty(address)) {
            storeaddress.requestFocus();
            storeaddress.setError("Please enter your address");
        }
        else if (TextUtils.isEmpty(city)||city.equals("Select City")) {
//            cityedt.requestFocus();
//            cityedt.setError("Please enter your city");
            Toast.makeText(getActivity(), "Please select your city", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(about_store)) {
            aboutstore.requestFocus();
            aboutstore.setError("Please enter your About Store");
        }
        else if (TextUtils.isEmpty(picture)){
            Toast.makeText(getContext(), "Please select store picture", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pictureLogo)){
            Toast.makeText(getContext(), "Please select store logo", Toast.LENGTH_SHORT).show();
        }
        else{
            if (isNetworkConnected()) {
                GeocodingLocation locationAddress = new GeocodingLocation();
                locationAddress.getAddressFromLocation(address, getContext(), new GeocoderHandler());
              //  presenter.sentRequest(name,mobile,email,password,shop_name,shop_contact_number,address,city,picture,shopdays,shopopentime,shopclosetime,about_store,android_id);
            }
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
    @Override
    public void success(String response) {
      //  Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
       // showDialog(response);
    }

    @Override
    public void successtype(ArrayList<OfferTypeModel> response) {

    }

    @Override
    public void successbrand(ArrayList<BrandModel> response) {

    }

    @Override
    public void successcategory(ArrayList<CategoryModel> response) {
        categoryAdapter = new CategoryAdapter(getContext(), response);
        categoryspinner.setAdapter(categoryAdapter);

    }
//city success;
ArrayList<CityBean> arLocation=new ArrayList<>();
    @Override
    public void success(ArrayList<CityBean> response,String status) {
        switch (status)
        {
            case "1":
                cityAdapter = new CityAdapter(getContext(), response);
                spCity.setAdapter(cityAdapter);
                break;
           case "2":
               arLocation.clear();
               arLocation=response;
               locationAdapter = new LocationAdapter(getContext(),0, arLocation);
               spLocation.setAdapter(locationAdapter);
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
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            new AlertDialog.Builder(getContext())
                    .setMessage("Please allow permission to open camera")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            ActivityCompat.requestPermissions(getActivity(), permissions, 100);
                        }
                    }).show();
        }
    }
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (reqCode == 100 && resultCode == RESULT_OK) {
            if (data == null) {
                showError("Failed to open picture!");
                return;
            }
            try {
                file = FileUtil.from(getContext(), data.getData());
                if (file == null) {
                    showError("Please choose an image!");
                } else {
                    compress();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void compress() {
        try {
            compressedImage = new Compressor(getContext())
                    .setMaxWidth(640)
                    .setMaxHeight(480)
                    .setQuality(50)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFile(file);
            String filePath = compressedImage.getPath();
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//            imagestore.setImageBitmap(bitmap);
            switch (diffLogoPicture)
            {
                case 1://for store logo
                    pictureLogo = getEncoded64(bitmap);
                    ivStoreLogo.setImageBitmap(bitmap);
                    break;
                case 2://for store banner
                    picture = getEncoded64(bitmap);
                    imagestore.setImageBitmap(bitmap);
                    String fileNm = file.getName();
                    if(arImageNm.size()<5) {
//                       arImage.add(filePath);

                        ImageBean bean=new ImageBean();
                        bean.setData(picture);
                        bean.setName(fileNm);
                        bean.setImagePath(filePath);
                        arImageNm.add(bean);

                        SetImageAdapter();
                    }

                    break;
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    SelectedImageAdapter mImgAdapter;
    public void SetImageAdapter()
    {
        int size=arImageNm.size();
        Log.e("","size= "+size);
        mImgAdapter = new SelectedImageAdapter(getActivity(),arImageNm,this);
        rvImage.setAdapter(mImgAdapter);

    }
    /*encode compress image into base64*/
    private String getEncoded64(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {

                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mrngtimeHour = hourOfDay;
                    mrngtimeMinute = minute;
                    String timeSet = "";
                    if (mrngtimeHour > 12) {
                        mrngtimeHour -= 12;
                        timeSet = "PM";
                    } else if (mrngtimeHour == 0) {
                        mrngtimeHour += 12;
                        timeSet = "AM";
                    } else if (mrngtimeHour == 12){
                        timeSet = "PM";
                    }else{
                        timeSet = "AM";
                    }
                    String min = "";
                    if (mrngtimeMinute < 10)
                        min = "0" + mrngtimeMinute ;
                    else
                        min = String.valueOf(mrngtimeMinute);

                    // Append in a StringBuilder
                    String aTime = new StringBuilder().append(mrngtimeHour).append(':')
                            .append(min ).append(" ").append(timeSet).toString();
                    if (starttimeset.equals("0")){
                        Mondaystart_Time.setText(aTime);
                        Mondayopentime=Mondaystart_Time.getText().toString();
                    }
                    else if (starttimeset.equals("1")){
                        Tuesdaystart_Time.setText(aTime);
                        Tuesdayopentime=Tuesdaystart_Time.getText().toString();
                    }
                    else if (starttimeset.equals("2")){
                        Wednesdaystart_Time.setText(aTime);
                        Wednesdayopentime=Wednesdaystart_Time.getText().toString();

                    }
                    else if (starttimeset.equals("3")){
                        Thursdaystart_Time.setText(aTime);
                        Thursdayopentime=Thursdaystart_Time.getText().toString();
                    }
                    else if (starttimeset.equals("4")){
                        Fridaystart_Time.setText(aTime);
                       Fridayopentime= Fridaystart_Time.getText().toString();

                    }
                    else if (starttimeset.equals("5")){
                        Saturdaystart_Time.setText(aTime);
                        Saturdayopentime=Saturdaystart_Time.getText().toString();

                    }else if (starttimeset.equals("6")){
                        Sundaystart_Time.setText(aTime);
                       Sundayopentime= Sundaystart_Time.getText().toString();
                    }

                }
            };

    private TimePickerDialog.OnTimeSetListener mTimeSetListener1 =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mrngtoHour = hourOfDay;
                    mrngtoMinute = minute;
                    String timeSet = "";
                    if (mrngtoHour > 12) {
                        mrngtoHour -= 12;
                        timeSet = "PM";
                    } else if (mrngtoHour == 0) {
                        mrngtoHour += 12;
                        timeSet = "AM";
                    } else if (mrngtoHour == 12){
                        timeSet = "PM";
                    }else{
                        timeSet = "AM";
                    }
                    String min = "";
                    if (mrngtoMinute < 10)
                        min = "0" + mrngtoMinute ;
                    else
                        min = String.valueOf(mrngtoMinute);

                    // Append in a StringBuilder
                    String aTime = new StringBuilder().append(mrngtoHour).append(':')
                            .append(min ).append(" ").append(timeSet).toString();
                    if (closetimeset.equals("0")){
                        Mondayend_Time.setText(aTime);
                        Mondayclosetime=Mondayend_Time.getText().toString();
                    }
                    else if (closetimeset.equals("1")){
                        Tuesdayend_Time.setText(aTime);
                        Tuesdayclosetime=Tuesdayend_Time.getText().toString();
                    }
                    else if (closetimeset.equals("2")){
                        Wednesdayend_Time.setText(aTime);
                        Wednesdayclosetime=Wednesdayend_Time.getText().toString();
                    }
                    else if (closetimeset.equals("3")){
                        Thursdayend_Time.setText(aTime);
                        Thursdayclosetime=Thursdayend_Time.getText().toString();
                    }
                    else if (closetimeset.equals("4")){
                        Fridayend_Time.setText(aTime);
                        Fridayclosetime= Fridayend_Time.getText().toString();
                    }
                    else if (closetimeset.equals("5")){
                        Saturdayend_Time.setText(aTime);
                        Saturdayclosetime=Saturdayend_Time.getText().toString();

                    }else if (closetimeset.equals("6")){
                        Sundayend_Time.setText(aTime);
                        Sundayclosetime= Sundayend_Time.getText().toString();
                    }
                }
            };
    public void showError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        lati= String.valueOf(location.getLatitude());
        longi= String.valueOf(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getContext(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }
    void getLocation() {
        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

//    locatation adprer click
    @Override
    public void onClick(int position, int diff) {
        switch (diff)
        {
            case 1:
                AddItem();
                break;
        }
    }


    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    String arr[] = locationAddress.split(",");
                    String lat = arr[0];
                    String lng = arr[1];
                    if (isNetworkConnected()) {
                        presenter.sentRequest(name,mobile,email,password,shop_name,shop_contact_number,address,city,picture,shopdays,shopopentime,shopclosetime,about_store,android_id,lat,lng,categoryid);
                    }
                    break;
                case 0:
                    // Bundle bundle1 = message.getData();
                    // locationAddress = bundle1.getString("address");
                    //String arr[] = locationAddress.split(",");

                    JSONArray jsonArrayImage = new JSONArray();
                    for(int i=0;i<arImageNm.size();i++) {
                        JSONObject locObj = new JSONObject();
                        try {
                            locObj.put("data", arImageNm.get(i).getData());
                            locObj.put("name", arImageNm.get(i).getName());

                            jsonArrayImage.put(locObj);

                            if(TextUtils.isEmpty(allImagNm))
                            {
                                allImagNm=arImageNm.get(i).getName();
                            }
                            else
                            {
                                allImagNm=allImagNm+","+arImageNm.get(i).getName();
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }

                    if (isNetworkConnected()) {//for register
                        presenter.sentRequest(name,mobile,email,password,shop_name,shop_contact_number,address,city,picture,shopdays,shopopentime,shopclosetime,about_store,android_id,lati,longi,categoryid);
                    }
            }

        }
    }

    ArrayList<AddStoreLocBean> arLocDetail=new ArrayList<>();
    public void AddItem()
    {
        if(arLocDetail.size()<5)
        {
            AddStoreLocBean bean = new AddStoreLocBean();
            bean.setsLocNm("");//Compaign Location name
            bean.setsLocLat("22.71246");
            bean.setsLocLong("75.86491");
            bean.setAddress("");
            bean.setPhoneNumber("");
            arLocDetail.add(bean);
        }
        else
        {
            Toast.makeText(getActivity(), "Your max number of cab filled", Toast.LENGTH_SHORT).show();
        }

        SetAdapter();
    }
    public void SetAdapter()
    {
        int size=arLocDetail.size();
        Log.e("","size= "+size);
        mAdapter = new StoreLocationDetailsAdapter(getActivity(),arLocDetail,this);
        rvLocationNm.setAdapter(mAdapter);
    }

    //multiple image click listner
    @Override
    public void PhotonClick(int position, int diff) {


    }

    public class LocationAdapter extends ArrayAdapter<CityBean> {
        private Context mContext;
        private ArrayList<CityBean> listState;
        private LocationAdapter myAdapter;
        private boolean isFromView = false;

        public LocationAdapter(Context context, int resource, List<CityBean> objects) {
            super(context, resource, objects);
            this.mContext = context;
            this.listState = (ArrayList<CityBean>) objects;
            this.myAdapter = this;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(final int position, View convertView,
                                  ViewGroup parent) {

            final ViewHolder holder;
            if (convertView == null) {
                LayoutInflater layoutInflator = LayoutInflater.from(mContext);
                convertView = layoutInflator.inflate(R.layout.spinner_item, null);
                holder = new ViewHolder();
                holder.tvLocNm = (TextView) convertView
                        .findViewById(R.id.tv_loc_nm);
                holder.tvLocId = (TextView) convertView
                        .findViewById(R.id.tv_loc_id);
                holder.tvOkay = (TextView) convertView
                        .findViewById(R.id.tv_okay);
                holder.mCheckBox = (CheckBox) convertView
                        .findViewById(R.id.checkbox);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.tvLocId.setText(listState.get(position).getId());
            holder.tvLocNm.setText(listState.get(position).getCity_name());

            // To check weather checked event fire from getview() or user input
            isFromView = true;
            holder.mCheckBox.setChecked(listState.get(position).isSelected());
            isFromView = false;

            if ((position == 0)) {
                holder.mCheckBox.setVisibility(View.INVISIBLE);
                holder.tvOkay.setVisibility(View.VISIBLE);
            } else {
                holder.mCheckBox.setVisibility(View.VISIBLE);
                holder.tvOkay.setVisibility(View.GONE);
            }
            holder.mCheckBox.setTag(position);
            holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();

                    if (!isFromView) {
//                        listState.get(position).setSelected(isChecked);
                        arLocation.get(position).setSelected(isChecked);
                        notifyDataSetChanged();
                    }
                }
            });
            return convertView;
        }

        private class ViewHolder {
            private TextView tvLocNm,tvLocId,tvOkay;
            private CheckBox mCheckBox;
        }
    }

//    String uploadBase64="";
//    String fileNm="";
    //    ArrayList<String>arImage=new ArrayList<>();



   /* private void selectcategory (){
        LayoutInflater li = LayoutInflater.from(getContext());
        View confirmDialog = li.inflate(R.layout.dialog_followers, null);
        RecyclerView recyclerView = (RecyclerView) confirmDialog.findViewById(R.id.recyclerViewrate);
        Button btnsend = (Button) confirmDialog.findViewById(R.id.sendoffer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        multiAdapter = new MultiAdapter(getContext(),followerModels);
        recyclerView.setAdapter(multiAdapter);
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getContext());
        alert.setView(confirmDialog);
        alertDialog = alert.create();
        WindowManager.LayoutParams wmlp =  alertDialog.getWindow().getAttributes();
        wmlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        alertDialog.getWindow().setAttributes(wmlp);
        alertDialog.show();
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (multiAdapter.getSelected().size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < multiAdapter.getSelected().size(); i++) {
                        stringBuilder.append(multiAdapter.getSelected().get(i).getId());
                        stringBuilder.append(",");
                    }
                    String followerid=stringBuilder.toString();
                    if (isNetworkConnected()) {
                        followerpresenter.SendOffer(idholder,PushOfferid,followerid);
                        //  Toast.makeText(RetalierPushActivity.this, ""+followerid, Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }  else {
                        alertDialog.dismiss();
                        showAlert("Please connect to internet.", R.style.DialogAnimation);
                    }

                } else {
                    Toast.makeText(getContext(), "No Selection", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });
    }*/
}


