package com.desired.offermachi.retalier.view.fragment;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.desired.offermachi.R;
import com.desired.offermachi.retalier.constant.FileUtil;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.presenter.RetailerstorePresenter;
import com.desired.offermachi.retalier.presenter.SignupPresenter;
import com.squareup.picasso.Picasso;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;
import id.zelory.compressor.Compressor;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;
import static android.app.Activity.RESULT_OK;


public class PersonaltoreFragment extends Fragment implements View.OnClickListener , RetailerstorePresenter.UpdateRetailerstore {
    View view;
    EditText etstorename,etstorecontact,etaddress,etaboutstore;
    String storeaddress,storename,storecontact,storeimage,storeday,storeopentime,storeclosetime,aboutstore;
    int editState = 0;
    TextView txtedit;
    LinearLayout btnedit;
    ImageView imgstore;
    TextView txtmondayopen,txtmondayclose,txttuesdayopen,txttuesdayclose,txtwednesdayopen,txtwednesdayclose,txtthursdayopen,txtthursdayclose,
    txtfridayopen,txtfridayclose,txtsaturdayopen,txtsaturdayclose,txtsundayopen,txtsundayclose;
    String Mondayopentime,Tuesdayopentime,Wednesdayopentime,Thursdayopentime,Fridayopentime,Saturdayopentime,Sundayopentime;
    String Mondayclosetime,Tuesdayclosetime,Wednesdayclosetime,Thursdayclosetime,Fridayclosetime,Saturdayclosetime,Sundayclosetime;
    String starttimeset,closetimeset;
    private static int mrngtimeHour;
    private static int mrngtimeMinute;
    private static int mrngtoHour;
    private static int mrngtoMinute;
    String shopopentime,shopclosetime;
    String shopdays="Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday";
    String idholder;
    private String picture = "";
    private File file, compressedImage;
    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private RetailerstorePresenter presenter;
    LinearLayout imagepicker;
    public PersonaltoreFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = new RetailerstorePresenter(getContext(), PersonaltoreFragment.this);
        view = inflater.inflate(R.layout.retalier_personal_store_detalis_activity, container, false);
        init();
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
    private void init(){
        etstorename=view.findViewById(R.id.editstorename);
        etstorecontact=view.findViewById(R.id.editstorecontact);
        etaddress=view.findViewById(R.id.editstoreaddress);
        btnedit=view.findViewById(R.id.btnedit);
        txtedit=view.findViewById(R.id.edit);
        imgstore=view.findViewById(R.id.storeimage);
        etaboutstore=view.findViewById(R.id.aboutstore);
        txtmondayopen=view.findViewById(R.id.mondayopen);
        txtmondayopen.setOnClickListener(this);
        txtmondayclose=view.findViewById(R.id.mondayclose);
        txtmondayclose.setOnClickListener(this);
        txttuesdayopen=view.findViewById(R.id.tuesdayopen);
        txttuesdayopen.setOnClickListener(this);
        txttuesdayclose=view.findViewById(R.id.tuesdayclose);
        txttuesdayclose.setOnClickListener(this);
        txtwednesdayopen=view.findViewById(R.id.wednesdayopen);
        txtwednesdayopen.setOnClickListener(this);
        txtwednesdayclose=view.findViewById(R.id.wednesdayclose);
        txtwednesdayclose.setOnClickListener(this);
        txtthursdayopen=view.findViewById(R.id.thursdayopen);
        txtthursdayopen.setOnClickListener(this);
        txtthursdayclose=view.findViewById(R.id.thursdayclose);
        txtthursdayclose.setOnClickListener(this);
        txtfridayopen=view.findViewById(R.id.fridayopen);
        txtfridayopen.setOnClickListener(this);
        txtfridayclose=view.findViewById(R.id.fridayclose);
        txtfridayclose.setOnClickListener(this);
        txtsaturdayopen=view.findViewById(R.id.saturdayopen);
        txtsaturdayopen.setOnClickListener(this);
        txtsaturdayclose=view.findViewById(R.id.saturdayclose);
        txtsaturdayclose.setOnClickListener(this);
        txtsundayopen=view.findViewById(R.id.sundayopen);
        txtsundayopen.setOnClickListener(this);
        txtsundayclose=view.findViewById(R.id.sundayclose);
        txtsundayclose.setOnClickListener(this);
        imagepicker=view.findViewById(R.id.imagepicker);
        imagepicker.setOnClickListener(this);
        UserModel user = SharedPrefManagerLogin.getInstance(getContext()).getUser();
        idholder= user.getId();
        storename= user.getStore_name();
        storecontact=user.getStore_contact_number();
        storeaddress=user.getStore_address();
        storeimage=user.getStoreimage();
        storeday =user.getStore_day();
        //open time split
        storeopentime=user.getStore_opentime();
         StringTokenizer time = new StringTokenizer(storeopentime, ",");
         Mondayopentime= time.nextToken();
         txtmondayopen.setText(Mondayopentime);
         Tuesdayopentime= time.nextToken();
         txttuesdayopen.setText(Tuesdayopentime);
         Wednesdayopentime= time.nextToken();
         txtwednesdayopen.setText(Wednesdayopentime);
         Thursdayopentime= time.nextToken();
         txtthursdayopen.setText(Thursdayopentime);
         Fridayopentime= time.nextToken();
         txtfridayopen.setText(Fridayopentime);
         Saturdayopentime= time.nextToken();
         txtsaturdayopen.setText(Saturdayopentime);
         Sundayopentime= time.nextToken();
         txtsundayopen.setText(Sundayopentime);
         //close time split
         storeclosetime=user.getStore_closetime();
         StringTokenizer timeto = new StringTokenizer(storeclosetime, ",");
         Mondayclosetime = timeto.nextToken();
         txtmondayclose.setText(Mondayclosetime);
         Tuesdayclosetime= timeto.nextToken();
         txttuesdayclose.setText(Tuesdayclosetime);
         Wednesdayclosetime= timeto.nextToken();
         txtwednesdayclose.setText(Wednesdayclosetime);
         Thursdayclosetime= timeto.nextToken();
         txtthursdayclose.setText(Thursdayclosetime);
         Fridayclosetime= timeto.nextToken();
         txtfridayclose.setText(Fridayclosetime);
         Saturdayclosetime= timeto.nextToken();
         txtsaturdayclose.setText(Saturdayclosetime);
         Sundayclosetime= timeto.nextToken();
         txtsundayclose.setText(Sundayclosetime);

        aboutstore=user.getAbout_store();



        etstorename.setText(storename);
        etstorecontact.setText(storecontact);
        etaddress.setText(storeaddress);
        etaboutstore.setText(aboutstore);

        if (storeimage.equals("")||storeimage.equals("NA")){

        }else{
            Picasso.get().load(storeimage).into(imgstore);

        }
        btnedit.setOnClickListener(this);
    }
    private void openGallery(){
        Intent photoPickerIntent = new Intent();
        photoPickerIntent.setType("image/*");
        photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);//
        // photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 100);
    }

    @Override
    public void onClick(View v) {
        if (v==btnedit){
            if (editState == 0){
                txtedit.setText("Save");
                editProfile();
                editState = 1;

            }else{
                txtedit.setText("Edit");
                Updateprodfile();
                editState = 0;
            }
        }
        else if (v==txtmondayopen){
            if (editState == 1){
                starttimeset="0";
                new TimePickerDialog(getContext(), mTimeSetListener, mrngtimeHour, mrngtimeMinute, false).show();
            }
        }
        else if (v==txtmondayclose){
            if (editState == 1) {
                closetimeset = "0";
                new TimePickerDialog(getContext(), mTimeSetListener1, mrngtoHour, mrngtoMinute, false).show();
            }
        }
        else if (v==txttuesdayopen){
            if (editState == 1) {
                starttimeset = "1";
                new TimePickerDialog(getContext(), mTimeSetListener, mrngtimeHour, mrngtimeMinute, false).show();
            }
        }
        else if (v==txttuesdayclose){
            if (editState == 1) {
            closetimeset="1";
            new TimePickerDialog(getContext(), mTimeSetListener1, mrngtoHour, mrngtoMinute, false).show();
            }
        }
        else if (v==txtwednesdayopen){
            if (editState == 1) {
                starttimeset = "2";
                new TimePickerDialog(getContext(), mTimeSetListener, mrngtimeHour, mrngtimeMinute, false).show();
            }
        }
        else if (v==txtwednesdayclose){
            if (editState == 1) {
                closetimeset = "2";
                new TimePickerDialog(getContext(), mTimeSetListener1, mrngtoHour, mrngtoMinute, false).show();
            }
        }
        else if (v==txtthursdayopen){
            if (editState == 1) {
                starttimeset = "3";
                new TimePickerDialog(getContext(), mTimeSetListener, mrngtimeHour, mrngtimeMinute, false).show();
            }
        }
        else if (v==txtthursdayclose){
            if (editState == 1) {
                closetimeset = "3";
                new TimePickerDialog(getContext(), mTimeSetListener1, mrngtoHour, mrngtoMinute, false).show();
            }
        }
        else if (v==txtfridayopen){
            if (editState == 1) {
                starttimeset = "4";
                new TimePickerDialog(getContext(), mTimeSetListener, mrngtimeHour, mrngtimeMinute, false).show();
            }
        }
        else if (v==txtfridayclose){
            if (editState == 1) {
                closetimeset = "4";
                new TimePickerDialog(getContext(), mTimeSetListener1, mrngtoHour, mrngtoMinute, false).show();
            }
        }
        else if (v==txtsaturdayopen){
            if (editState == 1) {
                starttimeset = "5";
                new TimePickerDialog(getContext(), mTimeSetListener, mrngtimeHour, mrngtimeMinute, false).show();
            }
        }
        else if (v==txtsaturdayclose){
            if (editState == 1) {
                closetimeset = "5";
                new TimePickerDialog(getContext(), mTimeSetListener1, mrngtoHour, mrngtoMinute, false).show();
            }
        }
        else if (v==txtsundayopen){
            if (editState == 1) {
                starttimeset = "6";
                new TimePickerDialog(getContext(), mTimeSetListener, mrngtimeHour, mrngtimeMinute, false).show();
            }
        }
        else if (v==txtsundayclose){
            if (editState == 1) {
                closetimeset = "6";
                new TimePickerDialog(getContext(), mTimeSetListener1, mrngtoHour, mrngtoMinute, false).show();
            }
        }else if (v==imagepicker) {
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
    private void editProfile(){
        etstorename.setEnabled(true);
        etstorecontact.setEnabled(true);
        etaddress.setEnabled(true);
        etaboutstore.setEnabled(true);
        imagepicker.setVisibility(View.VISIBLE);
    }
    private void Updateprodfile(){
        storename = etstorename.getText().toString();
        storecontact = etstorecontact.getText().toString().trim();
        storeaddress = etaddress.getText().toString();
        aboutstore= etaboutstore.getText().toString();
        shopopentime=Mondayopentime+","+Tuesdayopentime+","+Wednesdayopentime+","+Thursdayopentime+","+Fridayopentime+","+Saturdayopentime+","+Sundayopentime;
        shopclosetime=Mondayclosetime+","+Tuesdayclosetime+","+Wednesdayclosetime+","+Thursdayclosetime+","+Fridayclosetime+","+Saturdayclosetime+","+Sundayclosetime;
        if (TextUtils.isEmpty(storename)){
            etstorename.requestFocus();
            etstorename.setError("Please enter your shop name");
        }
        else if (TextUtils.isEmpty(storecontact)){
            etstorecontact.requestFocus();
            etstorecontact.setError("Please enter your mobile");
        }
        else if (!isValidMobile(storecontact)){
            etstorecontact.requestFocus();
            etstorecontact.setError("Please enter valid mobile number");
        }
        else if (storecontact.length() < 10){
            etstorecontact.requestFocus();
            etstorecontact.setError("Please enter 10 digit mobile number");
        }
        else if (TextUtils.isEmpty(storeaddress)) {
            etaddress.requestFocus();
            etaddress.setError("Please enter your address");
        }
        else if (TextUtils.isEmpty(aboutstore)) {
            etaboutstore.requestFocus();
            etaboutstore.setError("Please enter your About Store");
        }
        else{
            if (isNetworkConnected()) {
                presenter.sentRequest(idholder,storename,storecontact,storeaddress,picture,shopdays,shopopentime,shopclosetime,aboutstore);
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
    private void compress() {
        try {
            compressedImage = new Compressor(getContext())
                    .setMaxWidth(640)
                    .setMaxHeight(480)
                    .setQuality(50)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFile(file);
            String filePath = compressedImage.getAbsolutePath();
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            imgstore.setImageBitmap(bitmap);
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
                        txtmondayopen.setText(aTime);
                        Mondayopentime=txtmondayopen.getText().toString();
                    }
                    else if (starttimeset.equals("1")){
                        txttuesdayopen.setText(aTime);
                        Tuesdayopentime=txttuesdayopen.getText().toString();
                    }
                    else if (starttimeset.equals("2")){
                        txtwednesdayopen.setText(aTime);
                        Wednesdayopentime=txtwednesdayopen.getText().toString();

                    }
                    else if (starttimeset.equals("3")){
                        txtthursdayopen.setText(aTime);
                        Thursdayopentime=txtthursdayopen.getText().toString();
                    }
                    else if (starttimeset.equals("4")){
                        txtfridayopen.setText(aTime);
                        Fridayopentime= txtfridayopen.getText().toString();

                    }
                    else if (starttimeset.equals("5")){
                        txtsaturdayopen.setText(aTime);
                        Saturdayopentime=txtsaturdayopen.getText().toString();

                    }else if (starttimeset.equals("6")){
                        txtsundayopen.setText(aTime);
                        Sundayopentime= txtsundayopen.getText().toString();
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
                        txtmondayclose.setText(aTime);
                        Mondayclosetime=txtmondayclose.getText().toString();
                    }
                    else if (closetimeset.equals("1")){
                        txttuesdayclose.setText(aTime);
                        Tuesdayclosetime=txttuesdayclose.getText().toString();
                    }
                    else if (closetimeset.equals("2")){
                        txtwednesdayclose.setText(aTime);
                        Wednesdayclosetime=txtwednesdayclose.getText().toString();
                    }
                    else if (closetimeset.equals("3")){
                        txtthursdayclose.setText(aTime);
                        Thursdayclosetime=txtthursdayclose.getText().toString();
                    }
                    else if (closetimeset.equals("4")){
                        txtfridayclose.setText(aTime);
                        Fridayclosetime= txtfridayclose.getText().toString();
                    }
                    else if (closetimeset.equals("5")){
                        txtsaturdayclose.setText(aTime);
                        Saturdayclosetime=txtsaturdayclose.getText().toString();

                    }else if (closetimeset.equals("6")){
                        txtsundayclose.setText(aTime);
                        Sundayclosetime= txtsundayclose.getText().toString();
                    }
                }
            };
    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void success(String response) {
        Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();

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
    public void showError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }
}


