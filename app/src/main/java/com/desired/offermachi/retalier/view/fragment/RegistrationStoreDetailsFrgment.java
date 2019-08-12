package com.desired.offermachi.retalier.view.fragment;

import android.Manifest;
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
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.desired.offermachi.R;
import com.desired.offermachi.retalier.constant.FileUtil;
import com.desired.offermachi.retalier.presenter.SignupPresenter;
import com.desired.offermachi.retalier.view.activity.RetalierDashboard;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import id.zelory.compressor.Compressor;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

import static android.app.Activity.RESULT_OK;

public class RegistrationStoreDetailsFrgment extends Fragment implements View.OnClickListener,SignupPresenter.SignUp  {
    View view;
    private SignupPresenter presenter;
    EditText storename,storecontact,storeaddress,cityedt,aboutstore;
    Button registerbutton;
    String shop_name,shop_contact_number,address,city,about_store,shopopentime,shopclosetime;
    String name,mobile,email;
    private String picture = "";
    private File file, compressedImage;
    ImageView imagestore;
    RelativeLayout imagepick;
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
    public RegistrationStoreDetailsFrgment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.retalier_store_activity, container, false);
        presenter = new SignupPresenter(getContext(), RegistrationStoreDetailsFrgment.this);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(locationReceiver,
                new IntentFilter("Data"));
        initView() ;
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
        storename=(EditText)view.findViewById(R.id.store_name_edt_id);
        storecontact=(EditText)view.findViewById(R.id.store_contact_edt_id);
        storeaddress=(EditText)view.findViewById(R.id.store_address_edt_id);
        cityedt=(EditText)view.findViewById(R.id.city_edt_id);
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
        imagepick=view.findViewById(R.id.pickly);
        registerbutton=(Button)view.findViewById(R.id.registerbutton_button_id);
        registerbutton.setOnClickListener(this);
        imagepick.setOnClickListener(this);




    }
    public BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            name = intent.getStringExtra("name");
            email = intent.getStringExtra("email");
            mobile = intent.getStringExtra("mobile");
        }
    };

    private void openGallery(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        photoPickerIntent.setType("image/*");
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
        else if (v==imagepick){
            if (Build.VERSION.SDK_INT >= 23) {
                if (isStoragePermissionGranted()) {
                    openGallery();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), permissions, 101);
                }
            } else {
                openGallery();
            }
        }

    }
    private void Registraionvalid() {
        shop_name = storename.getText().toString();
        shop_contact_number = storecontact.getText().toString().trim();
        address = storeaddress.getText().toString();
        city = cityedt.getText().toString();
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
        else if (TextUtils.isEmpty(city)) {
            cityedt.requestFocus();
            cityedt.setError("Please enter your city");
        }
        else if (TextUtils.isEmpty(about_store)) {
            aboutstore.requestFocus();
            aboutstore.setError("Please enter your About Store");
        }else if (TextUtils.isEmpty(picture)){
            Toast.makeText(getContext(), "Please select store picture", Toast.LENGTH_SHORT).show();
        }
        else{
            if (isNetworkConnected()) {
                presenter.sentRequest(name,mobile,email,shop_name,shop_contact_number,address,city,picture,shopdays,shopopentime,shopclosetime,about_store);
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
                            ActivityCompat.requestPermissions(getActivity(), permissions, 101);
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

   /* *//* Get the real path from the URI *//*
    private String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
*/
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
            imagestore.setImageBitmap(bitmap);
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
}


