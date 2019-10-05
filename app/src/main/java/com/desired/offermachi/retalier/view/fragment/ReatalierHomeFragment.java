package com.desired.offermachi.retalier.view.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.desired.offermachi.retalier.constant.FileUtil;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.BrandModel;
import com.desired.offermachi.retalier.model.CategoryModel;
import com.desired.offermachi.retalier.model.OfferTypeModel;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.presenter.PostOfferDiscountPresenter;
import com.desired.offermachi.retalier.presenter.TypeBrandCategoryPresenter;
import com.desired.offermachi.retalier.view.activity.RetalierViewOfferDiscount;
import com.desired.offermachi.retalier.view.adapter.BrandAdapter;
import com.desired.offermachi.retalier.view.adapter.CategoryAdapter;
import com.desired.offermachi.retalier.view.adapter.OfferTypeAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import id.zelory.compressor.Compressor;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

import static android.app.Activity.RESULT_OK;

public class ReatalierHomeFragment extends Fragment implements View.OnClickListener, TypeBrandCategoryPresenter.TypeBrandCategory, PostOfferDiscountPresenter.PostOfferDiscount {
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

    public ReatalierHomeFragment() {
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = new TypeBrandCategoryPresenter(getContext(), ReatalierHomeFragment.this);
        postpresenter = new PostOfferDiscountPresenter(getContext(), ReatalierHomeFragment.this);
        view = inflater.inflate(R.layout.retalier_home_fragment, container, false);
        init();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(couponReceiver,
                new IntentFilter("Coupon"));
        return  view;
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
        if (ActivityCompat.checkSelfPermission(getActivity(),android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void init(){
        UserModel user = SharedPrefManagerLogin.getInstance(getContext()).getUser();
        idholder= user.getId();
        etoffertitle=view.findViewById(R.id.offertitle);
        etoffervalue=view.findViewById(R.id.offervalue);
        etofferdescription=view.findViewById(R.id.offerdescription);
        txtoffercouponcode=view.findViewById(R.id.offercouponcode);
        offerspinner = view.findViewById(R.id.offertypespinner);
        brandspinner = view.findViewById(R.id.brandnamespinner);
        categoryspinner = view.findViewById(R.id.categoryspinner);
        submitbutton =view.findViewById(R.id.submit_button_id);
        submitbutton.setOnClickListener(this);
        nextbutton=view.findViewById(R.id.next_button_id);
        nextbutton.setOnClickListener(this);
        fistcircle=view.findViewById(R.id.firstcircle_id);
        firsthomelinear=view.findViewById(R.id.firsthome_linear_id);
        secondlinear=view.findViewById(R.id.second_home_linear_id);
        etstartdate=view.findViewById(R.id.editText1);
        etstartdate.setOnClickListener(this);
        etenddate=view.findViewById(R.id.editexpirydate);
        etenddate.setOnClickListener(this);
        start_Time = view.findViewById(R.id.time_view_start);
        start_Time.setOnClickListener(this);
        end_Time =view.findViewById(R.id.time_view_end);
        end_Time.setOnClickListener(this);
        offerimage=view.findViewById(R.id.offerimage);
        imagepickerly=view.findViewById(R.id.imagepicker);
        imagepickerly.setOnClickListener(this);
        btngenerate=view.findViewById(R.id.btngenerate);
        btngenerate.setOnClickListener(this);
        if (isNetworkConnected()) {
            presenter.sentRequest(idholder);
        }  else {
            Toast.makeText(getContext(), "Please connect to internet.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), "Please select offer & Discount picture", Toast.LENGTH_SHORT).show();
        }else if (offerid.equals("0")){
            Toast.makeText(getContext(), "Please select offer", Toast.LENGTH_SHORT).show();

        }
        else if (brandid.equals("0")){
            Toast.makeText(getContext(), "Please select brand", Toast.LENGTH_SHORT).show();

        }
        else if (categoryid.equals("0")){
            Toast.makeText(getContext(), "Please select Category", Toast.LENGTH_SHORT).show();

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

                postpresenter.sentRequest(idholder,offertitle,brandid,offerid,offervalue,picture,categoryid,offerdescription,offerstartdate,offerenddate,offercouponcode,alltime,1);
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
            new TimePickerDialog(getContext(), mTimeSetListener, mrngtimeHour, mrngtimeMinute, false).show();
        }
        else if (v==end_Time){
            timepick="1";
            new TimePickerDialog(getContext(), mTimeSetListener, mrngtimeHour, mrngtimeMinute, false).show();
        }else if (v==imagepickerly){
            if (Build.VERSION.SDK_INT >= 23) {
                if (isStoragePermissionGranted()) {
                    openGallery();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), permissions, 100);
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
        }
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


    /*Compress image receive from gallery*/
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
        picker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void successtype(ArrayList<OfferTypeModel> response) {
        offerTypeAdapter = new OfferTypeAdapter(getContext(), response);
        offerspinner.setAdapter(offerTypeAdapter);

    }

    @Override
    public void successbrand(ArrayList<BrandModel> response) {
        brandAdapter = new BrandAdapter(getContext(), response);
        brandspinner.setAdapter(brandAdapter);
    }

    @Override
    public void successcategory(ArrayList<CategoryModel> response) {
        categoryAdapter = new CategoryAdapter(getContext(), response);
        categoryspinner.setAdapter(categoryAdapter);
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
    public void successoffer(String response) {
        Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), RetalierViewOfferDiscount.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void successGenerate(String response) {
        Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }
}

