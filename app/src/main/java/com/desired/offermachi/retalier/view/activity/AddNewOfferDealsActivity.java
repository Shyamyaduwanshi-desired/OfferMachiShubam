package com.desired.offermachi.retalier.view.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.presenter.NotificationCountPresenter;
import com.desired.offermachi.customer.view.activity.InfoActivity;
import com.desired.offermachi.retalier.constant.FileUtil;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.BrandModel;
import com.desired.offermachi.retalier.model.CategoryModel;
import com.desired.offermachi.retalier.model.OfferTypeModel;
import com.desired.offermachi.retalier.model.RetailerLocation;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.presenter.FollowerPresenter;
import com.desired.offermachi.retalier.presenter.PostOfferDiscountPresenter;
import com.desired.offermachi.retalier.presenter.RetailerLocationPresenter;
import com.desired.offermachi.retalier.presenter.TypeBrandCategoryPresenter;
import com.desired.offermachi.retalier.presenter.ViewOfferPresenter;
import com.desired.offermachi.retalier.view.adapter.BrandAdapter;
import com.desired.offermachi.retalier.view.adapter.CategoryAdapter;
import com.desired.offermachi.retalier.view.adapter.LocationAdapter;
import com.desired.offermachi.retalier.view.adapter.MultiAdapter;
import com.desired.offermachi.retalier.view.adapter.OfferTypeAdapter;
import com.desired.offermachi.retalier.view.adapter.PushOfferAdapter;
import com.desired.offermachi.retalier.view.fragment.ReatalierHomeFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import id.zelory.compressor.Compressor;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class AddNewOfferDealsActivity extends AppCompatActivity implements View.OnClickListener, TypeBrandCategoryPresenter.TypeBrandCategory, PostOfferDiscountPresenter.PostOfferDiscount , RetailerLocationPresenter.RetailerLocationInfo, LocationAdapter.ItemClick{
    View view;
    DatePickerDialog picker;
    TextView etstartdate,etenddate,start_Time,end_Time;
    Button submitbutton;
    Button nextbutton;
    LinearLayout fistcircle,firsthomelinear,secondlinear;
    String datepick,timepick;
    private static int mrngtimeHour;
    private static int mrngtimeMinute;
    private String picture = "";
    private File file, compressedImage;
    ImageView offerimage;
    LinearLayout imagepickerly;
    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private TypeBrandCategoryPresenter presenter;
    private PostOfferDiscountPresenter postpresenter;
    Spinner offerspinner,brandspinner,categoryspinner;
    private OfferTypeAdapter offerTypeAdapter;
    private BrandAdapter brandAdapter;
    private CategoryAdapter categoryAdapter;
    String offerid,categoryid,brandid;
    EditText etoffertitle,etoffervalue,etofferdescription;
    String offertitle,offervalue,offerdescription,offercouponcode,offerstartdate,offerenddate,offerstarttime,offerendtime,alltime;
    TextView txtoffercouponcode;
    TextView btngenerate;
    String idholder;
    private ArrayList<RetailerLocation> alRetailerLocation;
    private TextView tvRetailerLocation;
    private String offerLocalityId = "";
    private String offerLocality="";
    ImageView imageViewback,info;
    private CheckBox cb_selectall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_home_fragment);
        presenter = new TypeBrandCategoryPresenter(this, AddNewOfferDealsActivity.this);
        postpresenter = new PostOfferDiscountPresenter(this, AddNewOfferDealsActivity.this);
        init();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(couponReceiver,
                new IntentFilter("Coupon"));

    }
    public BroadcastReceiver couponReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            offercouponcode = intent.getStringExtra("couponcode");
            txtoffercouponcode.setText(offercouponcode);
        }
    };
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isStoragePermissionGranted() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void init() {
        UserModel user = SharedPrefManagerLogin.getInstance(this).getUser();
        idholder= user.getId();
        etoffertitle=findViewById(R.id.offertitle);
        etoffervalue=findViewById(R.id.offervalue);
        etofferdescription=findViewById(R.id.offerdescription);
        txtoffercouponcode=findViewById(R.id.offercouponcode);
        offerspinner = findViewById(R.id.offertypespinner);
        brandspinner = findViewById(R.id.brandnamespinner);
        categoryspinner = findViewById(R.id.categoryspinner);
        submitbutton =findViewById(R.id.submit_button_id);
        submitbutton.setOnClickListener(this);
        nextbutton=findViewById(R.id.next_button_id);
        nextbutton.setOnClickListener(this);
        fistcircle=findViewById(R.id.firstcircle_id);
        firsthomelinear=findViewById(R.id.firsthome_linear_id);
        secondlinear=findViewById(R.id.second_home_linear_id);
        etstartdate=findViewById(R.id.editText1);
        etstartdate.setOnClickListener(this);
        etenddate=findViewById(R.id.editexpirydate);
        etenddate.setOnClickListener(this);
        start_Time = findViewById(R.id.time_view_start);
        start_Time.setOnClickListener(this);
        end_Time =findViewById(R.id.time_view_end);
        end_Time.setOnClickListener(this);
        offerimage=findViewById(R.id.offerimage);
        imagepickerly=findViewById(R.id.imagepicker);
        imagepickerly.setOnClickListener(this);
        btngenerate=findViewById(R.id.btngenerate);
        btngenerate.setOnClickListener(this);
        tvRetailerLocation=findViewById(R.id.tvRetailerLocation);
        tvRetailerLocation.setOnClickListener(this);
        imageViewback = findViewById(R.id.imageback);
        info=findViewById(R.id.info_id);
        imageViewback.setOnClickListener(this);
        info.setOnClickListener(this);

        if (isNetworkConnected()) {
            presenter.sentRequest(idholder);
            new RetailerLocationPresenter(AddNewOfferDealsActivity.this, AddNewOfferDealsActivity.this).GetAllRetailerLocation(idholder);
        }  else {
            Toast.makeText(AddNewOfferDealsActivity.this, "Please connect to internet.", Toast.LENGTH_SHORT).show();
        }
        offerspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Select Provider
                TextView txofferid = (TextView) view.findViewById(R.id.offerid);
                offerid = txofferid.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        brandspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Select Provider
                TextView txbrandid = (TextView) view.findViewById(R.id.offerid);
                brandid = txbrandid.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        categoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Select Provider
                TextView txcategoryid = (TextView) view.findViewById(R.id.offerid);
                categoryid = txcategoryid.getText().toString();
                if(!categoryid.equals("0")) {
                    if (isNetworkConnected()) {
                        presenter.sentRequestById(idholder, categoryid);
                    } else {
                        Toast.makeText(AddNewOfferDealsActivity.this, "Please connect to internet.", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void openGallery(){
        Intent photoPickerIntent = new Intent();
        photoPickerIntent.setType("image/*");
        photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);//
        // photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 100);
    }
    private void PostOfferValidation() {
        offertitle=etoffertitle.getText().toString();
        offervalue=etoffervalue.getText().toString().trim();
        offerdescription=etofferdescription.getText().toString();

        if (TextUtils.isEmpty(offertitle)){
            etoffertitle.requestFocus();
            etoffertitle.setError("Please enter your offer title");
        }
        else if (TextUtils.isEmpty(offervalue)){
            etoffervalue.requestFocus();
            etoffervalue.setError("Please enter your offer value");
        }
        else if (TextUtils.isEmpty(offerdescription)) {
            etofferdescription.requestFocus();
            etofferdescription.setError("Please enter your offer description");
        }

        else if (TextUtils.isEmpty(picture)){
            Toast.makeText(AddNewOfferDealsActivity.this, "Please select offer & Discount picture", Toast.LENGTH_SHORT).show();
        }else if (offerid.equals("0")){
            Toast.makeText(AddNewOfferDealsActivity.this, "Please select offer", Toast.LENGTH_SHORT).show();

        }
        else if (brandid.equals("0")){
            Toast.makeText(AddNewOfferDealsActivity.this, "Please select brand", Toast.LENGTH_SHORT).show();

        }
        else if (categoryid.equals("0")){
            Toast.makeText(AddNewOfferDealsActivity.this, "Please select Category", Toast.LENGTH_SHORT).show();

        }
        else{
            firsthomelinear.setVisibility(View.GONE);
            secondlinear.setVisibility(View.VISIBLE);
            fistcircle.setBackgroundResource(R.drawable.colour_background);
        }

    }

    private void PostOfferValidation2() {
        offercouponcode=txtoffercouponcode.getText().toString();
        offerstartdate=etstartdate.getText().toString();
        offerenddate=etenddate.getText().toString();
        offerstarttime=start_Time.getText().toString();
        offerendtime=end_Time.getText().toString();
        if (TextUtils.isEmpty(offercouponcode)) {
            txtoffercouponcode.requestFocus();
            txtoffercouponcode.setError("Please generate Couponcode");
        }
        else if (TextUtils.isEmpty(offerstartdate)) {
            etstartdate.requestFocus();
            etstartdate.setError("Please select start date");
        }
        else if (TextUtils.isEmpty(offerenddate)) {
            etenddate.requestFocus();
            etenddate.setError("Please select Expire date");
        }
        else if (TextUtils.isEmpty(offerstarttime)) {
            start_Time.requestFocus();
            start_Time.setError("Please select start time");
        }
        else if (TextUtils.isEmpty(offerendtime)) {
            end_Time.requestFocus();
            end_Time.setError("Please select end time");
        }else{
            alltime= offerstarttime+"-"+offerendtime;
            if (isNetworkConnected()) {

                Log.e("","categoryid= "+categoryid);

                postpresenter.sentRequest(idholder,offertitle,brandid,offerid,offervalue,picture,categoryid,offerdescription,offerstartdate,offerenddate,offercouponcode,alltime,1,offerLocalityId);
            }
        }
    }
    @Override
    public void onClick(View v) {
        if (v==submitbutton){
            PostOfferValidation2();
        }
        else if (v==nextbutton){
            PostOfferValidation();
        }
        else if (v==etstartdate){
            datepick="0";
            datepicker();
        }
        else if (v==etenddate){
            datepick="1";
            datepicker();;
        }
        else if (v==start_Time){
            timepick="0";
            new TimePickerDialog(AddNewOfferDealsActivity.this, mTimeSetListener, mrngtimeHour, mrngtimeMinute, false).show();
        }
        else if (v==end_Time){
            timepick="1";
            new TimePickerDialog(AddNewOfferDealsActivity.this, mTimeSetListener, mrngtimeHour, mrngtimeMinute, false).show();
        }else if (v==imagepickerly){
            if (Build.VERSION.SDK_INT >= 23) {
                if (isStoragePermissionGranted()) {
                    openGallery();
                } else {
                    ActivityCompat.requestPermissions(AddNewOfferDealsActivity.this, permissions, 100);
                }
            } else {
                openGallery();
            }
        }else if (v==btngenerate){
            if (isNetworkConnected()) {
                postpresenter.GenerateCouponcode();
            }  else {
                showAlert("Please connect to internet.", R.style.DialogAnimation);
            }
        }else if(v==tvRetailerLocation){
            showMultipleLocationDialog();
        }
        else if (v==imageViewback){
            onBackPressed();
        }else if(v==info){
            Intent intent = new Intent(AddNewOfferDealsActivity.this, InfoActivity.class);
            startActivity(intent);

        }
        if (v == cb_selectall) {
//            selectcategory();
            if(cb_selectall.isChecked()){
                locationAdapter.selectAll();
            }
            else {
                locationAdapter.unselectall();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            new AlertDialog.Builder(AddNewOfferDealsActivity.this)
                    .setMessage("Please allow permission to open camera")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            ActivityCompat.requestPermissions(AddNewOfferDealsActivity.this, permissions, 100);
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
                file = FileUtil.from(AddNewOfferDealsActivity.this, data.getData());
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


    /*Compress image receive from gallery*/
    private void compress() {
        try {
            compressedImage = new Compressor(AddNewOfferDealsActivity.this)
                    .setMaxWidth(640)
                    .setMaxHeight(480)
                    .setQuality(50)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFile(file);
            String filePath = compressedImage.getPath();
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            offerimage.setImageBitmap(bitmap);
            picture = getEncoded64(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void datepicker(){
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(AddNewOfferDealsActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (datepick.equals("0")){
                    etstartdate.setText(year + "-" + (month + 1) + "-" +dayOfMonth );
                }
                else if (datepick.equals("1")){
                    etenddate.setText( year+ "-" + (month + 1) + "-" + dayOfMonth);
                }
            }
        }, year, month, day);
        picker.show();
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
                    if (timepick.equals("0")){
                        start_Time.setText(aTime);
                    }else if (timepick.equals("1")){
                        end_Time.setText(aTime);
                    }

                }
            };
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) AddNewOfferDealsActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void successtype(ArrayList<OfferTypeModel> response) {
        offerTypeAdapter = new OfferTypeAdapter(AddNewOfferDealsActivity.this, response);
        offerspinner.setAdapter(offerTypeAdapter);

    }

    @Override
    public void successbrand(ArrayList<BrandModel> response) {
        brandAdapter = new BrandAdapter(AddNewOfferDealsActivity.this, response);
        brandspinner.setAdapter(brandAdapter);
    }

    @Override
    public void successcategory(ArrayList<CategoryModel> response) {
        categoryAdapter = new CategoryAdapter(AddNewOfferDealsActivity.this, response);
        categoryspinner.setAdapter(categoryAdapter);
    }

    @Override
    public void success(ArrayList<RetailerLocation> response, String status) {
        alRetailerLocation = response;
    }

    @Override
    public void error(String response) {
        showAlert(response, R.style.DialogAnimation);
    }

    @Override
    public void fail(String response) {
        showAlert(response, R.style.DialogAnimation);
    }
    private void showAlert(String message, int animationSource){
        final PrettyDialog prettyDialog = new PrettyDialog(AddNewOfferDealsActivity.this);
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
    public void successoffer(String response) {
        Toast.makeText(AddNewOfferDealsActivity.this, ""+response, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddNewOfferDealsActivity.this, RetalierViewOfferDiscount.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void successGenerate(String response) {
        Toast.makeText(AddNewOfferDealsActivity.this, ""+response, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void erroroffer(String response) {
        showAlert(response, R.style.DialogAnimation);
    }

    @Override
    public void failoffer(String response) {
        showAlert(response, R.style.DialogAnimation);
    }

    public void showError(String errorMessage) {
        Toast.makeText(AddNewOfferDealsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    Dialog locationDlg = null;
    RecyclerView rvStoreLocation;
    Button btnOkay;

    public void showMultipleLocationDialog() {
        if (locationDlg != null) {
            locationDlg.dismiss();
            locationDlg = null;
        }
        locationDlg = new Dialog(AddNewOfferDealsActivity.this);
        locationDlg.setContentView(R.layout.store_location_dlg);
        locationDlg.setTitle("");
        rvStoreLocation = locationDlg.findViewById(R.id.rvStoreLocation);
        cb_selectall =  locationDlg.findViewById(R.id.cb_selectall);
        cb_selectall.setOnClickListener(this);
        btnOkay = locationDlg.findViewById(R.id.btStoreLocationProceed);
        /*SearchView searchView = locationDlg.findViewById(R.id.svStoreLocationSearch);
        searchView.setOnQueryTextListener(this);
*/

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AddNewOfferDealsActivity.this);
        rvStoreLocation.setLayoutManager(mLayoutManager);
        rvStoreLocation.setItemAnimator(new DefaultItemAnimator());

        locationAdapter = new LocationAdapter(AddNewOfferDealsActivity.this, alRetailerLocation, this);
        rvStoreLocation.setAdapter(locationAdapter);


        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetAllSelectedLocation();

            }

        });

        locationDlg.show();
    }

    LocationAdapter locationAdapter;

    @Override
    public void onLocationClick(RetailerLocation retailerLocation) {
        cb_selectall.setChecked(false);
        for (RetailerLocation loc:alRetailerLocation) {
            if(loc.getId()==retailerLocation.getId()){
                if (retailerLocation.isSelected()) {
                    retailerLocation.setSelected(false);
                } else {
                    retailerLocation.setSelected(true);
                }
                break;
            }
        }
        locationAdapter.notifyDataSetChanged();
    }
    public void GetAllSelectedLocation() {
        if(alRetailerLocation!=null) {
            offerLocalityId = "";
            offerLocality = "";
            for (int i = 0; i < alRetailerLocation.size(); i++) {
                if (alRetailerLocation.get(i).isSelected()) {
                    if (TextUtils.isEmpty(offerLocalityId)) {
                        offerLocalityId = alRetailerLocation.get(i).getId();
                        offerLocality = alRetailerLocation.get(i).getLocalityName();

                    } else {
                        offerLocalityId = offerLocalityId + "," + alRetailerLocation.get(i).getId();
                        offerLocality = offerLocality + "," + alRetailerLocation.get(i).getLocalityName();
                    }
                }
            }
            if (locationDlg != null) {
                locationDlg.dismiss();
                if (offerLocality.length() > 25) {
                    tvRetailerLocation.setText(offerLocality.substring(0, 25) + "...");
                } else {
                    tvRetailerLocation.setText(offerLocality);
                }
            }
            Log.e("", "sAllCityId= " + offerLocalityId);
            //SetAdapter();
        }
    }
    @Override
    public void failureBrand() {
        ArrayList<BrandModel> tmp = new ArrayList<BrandModel>();
        tmp.add(new BrandModel("0","Select Brand",""));
        brandAdapter = new BrandAdapter(AddNewOfferDealsActivity.this, tmp);
        brandspinner.setAdapter(brandAdapter);
    }

}