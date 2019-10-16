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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.view.activity.InfoActivity;
import com.desired.offermachi.retalier.constant.FileUtil;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.BrandModel;
import com.desired.offermachi.retalier.model.CategoryModel;
import com.desired.offermachi.retalier.model.OfferLocation;
import com.desired.offermachi.retalier.model.OfferTypeModel;
import com.desired.offermachi.retalier.model.RetailerLocation;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.presenter.EditOfferPresenter;
import com.desired.offermachi.retalier.presenter.PostOfferDiscountPresenter;
import com.desired.offermachi.retalier.presenter.RetailerLocationPresenter;
import com.desired.offermachi.retalier.presenter.TypeBrandCategoryPresenter;
import com.desired.offermachi.retalier.view.adapter.BrandAdapter;
import com.desired.offermachi.retalier.view.adapter.CategoryAdapter;
import com.desired.offermachi.retalier.view.adapter.LocationAdapter;
import com.desired.offermachi.retalier.view.adapter.OfferTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import id.zelory.compressor.Compressor;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class EditOfferActivity extends AppCompatActivity implements View.OnClickListener, TypeBrandCategoryPresenter.TypeBrandCategory, EditOfferPresenter.PostOfferDiscount, RetailerLocationPresenter.RetailerLocationInfo,LocationAdapter.ItemClick   {
    ImageView imageViewback,info;
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
    private EditOfferPresenter editOfferPresenter;
    Spinner offerspinner,brandspinner,categoryspinner;
    private OfferTypeAdapter offerTypeAdapter;
    private BrandAdapter brandAdapter;
    private CategoryAdapter categoryAdapter;
    String offerid,categoryid,brandid;
    EditText etoffertitle,etoffervalue,etofferdescription;
    String offertitle,offervalue,offerdescription,offercouponcode,offerstartdate,offerenddate,offerstarttime,offerendtime,alltime;
    TextView txtoffercouponcode;
    String idholder;
    private TextView tvDealOfDayLocation;
    private String offerLocalityId="";
    private String offerLocality="";
    private ArrayList<RetailerLocation> alRetailerLocation = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();
    private ArrayList<BrandModel> alBrand =new ArrayList<>();
    private ArrayList<OfferTypeModel> alOffer = new ArrayList<>();
    private String offerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_offer);
        presenter = new TypeBrandCategoryPresenter(this, this);
        editOfferPresenter = new EditOfferPresenter(this, this);
        init();
        LocalBroadcastManager.getInstance(this).registerReceiver(couponReceiver,
                new IntentFilter("Coupon"));
        setDataOnView(getIntent().getStringExtra("data"));

    }

    public void setDataOnView(String data) {
        //Toast.makeText(this, ""+response, Toast.LENGTH_SHORT).show();
        try {
            JSONObject object = new JSONObject(data);
            ArrayList<OfferLocation> alOfferLocation = new Gson().fromJson(object.optString("offer_locations"),  new TypeToken<ArrayList<OfferLocation>>(){}.getType());
            offerLocalityId = "";
            offerLocality = "";
            for (OfferLocation loc:alOfferLocation){
                ids.add(loc.getId());
                if(TextUtils.isEmpty(offerLocalityId)){
                    offerLocality = loc.getLocalityName();
                    offerLocalityId = loc.getId();
                }else {
                    offerLocality =offerLocality +", "+ loc.getLocalityName();
                    offerLocalityId = loc.getId();
                }
            }
            etoffertitle.setText(object.optString("offer_title"));
            etoffervalue.setText(object.optString("offer_value"));
            etofferdescription.setText(object.optString("description"));
            categoryspinner.setTag(object.optString("sub_category"));
            brandspinner.setTag(object.optString("offer_brand"));
            offerspinner.setTag(object.optString("offer_type"));

            if (offerLocality.length() > 25) {
                tvDealOfDayLocation.setText(offerLocality.substring(0, 25) + "...");
            } else {
                tvDealOfDayLocation.setText(offerLocality);
            }
            Picasso.get().load(object.optString("offer_image")).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(offerimage);

            etstartdate.setText(object.optString("start_date"));
            etenddate.setText(object.optString("end_date"));
            txtoffercouponcode.setText(object.optString("coupon_code"));
            String[] tmp = object.optString("alltime").split("-");
            if(tmp.length==2) {
                start_Time.setText(tmp[0]);
                end_Time.setText(tmp[1]);
            }else if(tmp.length==1) {
                start_Time.setText(tmp[0]);
            }
            offerId = object.optString("id");
            /*if(object.optString("can_delete").equals("1")){
                btnDelete.setVisibility(View.VISIBLE);
            }else {
                btnDelete.setVisibility(View.GONE);
            }
            ((TextView)findViewById(R.id.storeLocations)).setText(offerLocations);
            String id=object.getString("id");
            String offerid=object.getString("offer_id");
            String offertitle=object.getString("offer_title");
            txtoffername.setText(offertitle);
            String brandid=object.getString("offer_brand");
            String brandname=object.getString("offer_brand_name");
            txtbrandname.setText(brandname);
            String offercategory= object.getString("offer_category");
            String subcategory=object.getString("sub_category");
            String offertype=object.getString("offer_type");
            String offervalue=object.getString("offer_value");
            String offertypename=object.getString("offer_type_name");
            txtoffertypename.setText(offertypename+" Off "+offervalue);
            String offerdetail=object.getString("offer_details");
            String startdate=object.getString("start_date");
            String enddate=object.getString("end_date");
            txtenddate.setText(enddate);
            String time=object.getString("alltime");
            txttime.setText(time);
            String description= object.getString("description");
            txtofferdescription.setText(description);
            couponcode=object.getString("coupon_code");
            String postby=object.getString("posted_by");
            String status=object.getString("status");
            String offerimage=object.getString("offer_image");
            if(offerimage.equals("")){
            }else{
                Picasso.get().load(offerimage).networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(viewPager_product);
            }
            qrcodeimage=object.getString("qr_code_image");*/
        }catch (JSONException e) {
            e.printStackTrace();

        }
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void init(){
        UserModel user = SharedPrefManagerLogin.getInstance(this).getUser();
        idholder= user.getId();
        etoffertitle=findViewById(R.id.offertitle);
        imageViewback = findViewById(R.id.imageback);
        info=findViewById(R.id.info_id);
        etoffervalue=findViewById(R.id.offervalue);
        etofferdescription=findViewById(R.id.offerdescription);
        txtoffercouponcode=findViewById(R.id.offercouponcode);
        offerspinner = findViewById(R.id.offertypespinner);
        brandspinner = findViewById(R.id.brandnamespinner);
        categoryspinner = findViewById(R.id.categoryspinner);
        submitbutton =findViewById(R.id.submit_button_id);
        nextbutton=findViewById(R.id.next_button_id);
        fistcircle=findViewById(R.id.firstcircle_id);
        firsthomelinear=findViewById(R.id.firsthome_linear_id);
        secondlinear=findViewById(R.id.second_home_linear_id);
        etstartdate=findViewById(R.id.editText1);
        etenddate=findViewById(R.id.editexpirydate);
        start_Time = findViewById(R.id.time_view_start);
        end_Time =findViewById(R.id.time_view_end);
        offerimage=findViewById(R.id.offerimage);
        imagepickerly=findViewById(R.id.imagepicker);
        tvDealOfDayLocation = findViewById(R.id.tvDealOfDayLocation);
        tvDealOfDayLocation.setOnClickListener(this);

        if (isNetworkConnected()) {
            presenter.sentRequest(idholder);
            new RetailerLocationPresenter(this, this).GetAllRetailerLocation(idholder);
        }  else {
            Toast.makeText(this, "Please connect to internet.", Toast.LENGTH_SHORT).show();
        }
        imageViewback.setOnClickListener(this);
        info.setOnClickListener(this);
        imagepickerly.setOnClickListener(this);
        end_Time.setOnClickListener(this);
        start_Time.setOnClickListener(this);
        etenddate.setOnClickListener(this);
        etstartdate.setOnClickListener(this);
        nextbutton.setOnClickListener(this);
        submitbutton.setOnClickListener(this);

        if (isNetworkConnected()) {
            presenter.sentRequest(idholder);
        }  else {
            Toast.makeText(this, "Please connect to internet.", Toast.LENGTH_SHORT).show();
        }
        offerspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Select Provider
                /*TextView txofferid = (TextView) view.findViewById(R.id.offerid);
                offerid = txofferid.getText().toString();*/
                offerid = alOffer.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        brandspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Select Provider
                /*TextView txbrandid = (TextView) view.findViewById(R.id.offerid);
                brandid = txbrandid.getText().toString();*/
                brandid = alBrand.get(i).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        categoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Select Provider
                categoryid  = alCategory.get(i).getId();
                /*TextView txcategoryid = (TextView) view.findViewById(R.id.offerid);
                categoryid = txcategoryid.getText().toString();*/
                if(!categoryid.equals("0")) {
                    if (isNetworkConnected()) {
                        presenter.sentRequestById(idholder, categoryid);
                    } else {
                        Toast.makeText(EditOfferActivity.this, "Please connect to internet.", Toast.LENGTH_SHORT).show();
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

        /*else if (TextUtils.isEmpty(picture)){
            Toast.makeText(this, "Please select offer & Discount picture", Toast.LENGTH_SHORT).show();
        }*/else if (offerid.equals("0")){
            Toast.makeText(this, "Please select offer", Toast.LENGTH_SHORT).show();

        }
        else if (brandid.equals("0")){
            Toast.makeText(this, "Please select brand", Toast.LENGTH_SHORT).show();

        }
        else if (categoryid.equals("0")){
            Toast.makeText(this, "Please select Category", Toast.LENGTH_SHORT).show();

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
      /*  else if (TextUtils.isEmpty(offerenddate)) {
            etenddate.requestFocus();
            etenddate.setError("Please select Expire date");
        }*/
        else if (TextUtils.isEmpty(offerstarttime)) {
            start_Time.requestFocus();
            start_Time.setError("Please select start time");
        }
        else if (TextUtils.isEmpty(offerendtime)) {
            end_Time.requestFocus();
            end_Time.setError("Please select end time");
        }else{
            alltime= offerstarttime+"-"+offerendtime;
//            Toast.makeText(this, "Comming soon", Toast.LENGTH_SHORT).show();

            if (isNetworkConnected()) {
                editOfferPresenter.editOffer(idholder,offertitle,brandid,offerid,offervalue,picture,categoryid,offerdescription,offerstartdate,offerenddate,offercouponcode,alltime,offerLocalityId,offerId);
            }

        }
    }
    @Override
    public void onClick(View v) {
        if (v==imageViewback){
            onBackPressed();
        }else if(v==info){
            Intent intent = new Intent(EditOfferActivity.this, InfoActivity.class);
            startActivity(intent);
        }else if (v==submitbutton){
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
            new TimePickerDialog(this, mTimeSetListener, mrngtimeHour, mrngtimeMinute, false).show();
        }
        else if (v==end_Time){
            timepick="1";
            new TimePickerDialog(this, mTimeSetListener, mrngtimeHour, mrngtimeMinute, false).show();
        }else if (v==imagepickerly){
            if (Build.VERSION.SDK_INT >= 23) {
                if (isStoragePermissionGranted()) {
                    openGallery();
                } else {
                    ActivityCompat.requestPermissions(this, permissions, 100);
                }
            } else {
                openGallery();
            }
        }else if(v==tvDealOfDayLocation){
            showMultipleLocationDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Please allow permission to open camera")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            ActivityCompat.requestPermissions(EditOfferActivity.this, permissions, 100);
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
                file = FileUtil.from(this, data.getData());
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

    /* Get the real path from the URI */
  /*  private String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }*/

    /*Compress image receive from gallery*/
    private void compress() {
        try {
            compressedImage = new Compressor(this)
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
        picker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
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
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void successtype(ArrayList<OfferTypeModel> response) {
        alOffer = response;
        offerTypeAdapter = new OfferTypeAdapter(this, alOffer);
        offerspinner.setAdapter(offerTypeAdapter);
        if(offerspinner.getTag()!=null){
            String tmp = offerspinner.getTag().toString();
            int pos=0;
            for (int i = 0 ;i<response.size();i++){
                if(response.get(i).getId().equals(tmp)){
                    pos=i;
                    break;
                }
            }
            offerspinner.setSelection(pos);
        }

    }

    @Override
    public void successbrand(ArrayList<BrandModel> response) {
        alBrand = response;
        brandAdapter = new BrandAdapter(this, response);
        brandspinner.setAdapter(brandAdapter);
        if(brandspinner.getTag()!=null){
            String tmp = brandspinner.getTag().toString();
            int pos=0;
            for (int i = 0 ;i<response.size();i++){
                if(response.get(i).getId().equals(tmp)){
                    pos=i;
                    break;
                }
            }
            brandspinner.setSelection(pos);
        }
    }
    ArrayList<CategoryModel> alCategory;
    @Override
    public void successcategory(ArrayList<CategoryModel> response) {
        alCategory = response;
        categoryAdapter = new CategoryAdapter(this, alCategory);
        categoryspinner.setAdapter(categoryAdapter);
        if(categoryspinner.getTag()!=null){
            String tmp = categoryspinner.getTag().toString();
            int pos=0;
            for (int i = 0 ;i<response.size();i++){
                if(response.get(i).getId().equals(tmp)){
                    pos=i;
                    break;
                }
            }
            categoryspinner.setSelection(pos);
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
    private void showAlert(String message, int animationSource){
        final PrettyDialog prettyDialog = new PrettyDialog(this);
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
        Toast.makeText(this, ""+response, Toast.LENGTH_SHORT).show();
      /*  Intent intent = new Intent(this, RetalierViewOfferDiscount.class);
        startActivity(intent);*/
        this.finish();
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
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void failureBrand() {
        ArrayList<BrandModel> tmp = new ArrayList<BrandModel>();
        tmp.add(new BrandModel("0","Select Brand",""));
        brandAdapter = new BrandAdapter(this, tmp);
        brandspinner.setAdapter(brandAdapter);
    }

    @Override
    public void success(ArrayList<RetailerLocation> response, String status) {
        alRetailerLocation = response;
        for (RetailerLocation location:alRetailerLocation){
            if(ids.contains(location.getId())){
                location.setSelected(true);
            }
        }
    }

    Dialog locationDlg = null;
    RecyclerView rvStoreLocation;
    Button btnOkay;

    public void showMultipleLocationDialog() {
        if (locationDlg != null) {
            locationDlg.dismiss();
            locationDlg = null;
        }
        locationDlg = new Dialog(EditOfferActivity.this);
        locationDlg.setContentView(R.layout.store_location_dlg);
        locationDlg.setTitle("");
        rvStoreLocation = locationDlg.findViewById(R.id.rvStoreLocation);

        btnOkay = locationDlg.findViewById(R.id.btStoreLocationProceed);
        /*SearchView searchView = locationDlg.findViewById(R.id.svStoreLocationSearch);
        searchView.setOnQueryTextListener(this);
*/

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(EditOfferActivity.this);
        rvStoreLocation.setLayoutManager(mLayoutManager);
        rvStoreLocation.setItemAnimator(new DefaultItemAnimator());

        locationAdapter = new LocationAdapter(EditOfferActivity.this, alRetailerLocation, this);
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
                    tvDealOfDayLocation.setText(offerLocality.substring(0, 25) + "...");
                } else {
                    tvDealOfDayLocation.setText(offerLocality);
                }
            }
            Log.e("", "sAllCityId= " + offerLocalityId);
            //SetAdapter();
        }
    }
}