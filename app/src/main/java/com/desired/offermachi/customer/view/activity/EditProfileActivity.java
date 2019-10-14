package com.desired.offermachi.customer.view.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.CustomerUpdateProfilePresenter;
import com.desired.offermachi.retalier.constant.FileUtil;
import com.desired.offermachi.retalier.presenter.ProfileImagePresenter;
import com.desired.offermachi.retalier.view.activity.RetalierProfileActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, CustomerUpdateProfilePresenter.UpdateProfile {
    private CustomerUpdateProfilePresenter presenter;
    LinearLayout male,female;
    ImageView  imageViewback,info;
    TextView changepasswordtext,tvYear,tvMonth,tvDay/*,editdob*/;
    EditText etname,etgender,etemail,etmobile,etaddress;
    CircleImageView imgProfileAvatar;
    LinearLayout imagepicker;
    String nameholder,emailholder,phoneholder,genderholder,addressholder,idholder,dobholder;
    Button btnsave;
    DatePickerDialog picker;
    String datepick ;

    private String picture = "";
    private File file, compressedImage;
    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    int year = Calendar.getInstance().get(Calendar.YEAR);
    int month = Calendar.getInstance().get(Calendar.MONTH);
    int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

//    Button btYear,btMonth,btDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);
        initview();
        Listener();
    }

    private void Listener() {

    }

    //    DatePicker dpDate;
    private void initview(){
        presenter=new CustomerUpdateProfilePresenter(EditProfileActivity.this,EditProfileActivity.this);
        User user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
        idholder=user.getId();
//        dpDate = (DatePicker)findViewById(R.id.dpDate);

        tvYear=findViewById(R.id.tv_year);
        tvMonth=findViewById(R.id.tv_month);
        tvDay=findViewById(R.id.tv_day);

//        btYear=findViewById(R.id.tv_year);
//        tvDay=findViewById(R.id.tv_month);
//        tvDay=findViewById(R.id.tv_day);


        etname=findViewById(R.id.name);
        etgender=findViewById(R.id.gender);
        etemail=findViewById(R.id.email);

        etmobile=findViewById(R.id.mobile);
        etaddress=findViewById(R.id.address);
        imgProfileAvatar=findViewById(R.id.imgProfileAvatar);
        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(this);
        info=findViewById(R.id.info_id);
        info.setOnClickListener(this);
        male=findViewById(R.id.lymale);
        male.setOnClickListener(this);
        female=findViewById(R.id.lyfemale);
        female.setOnClickListener(this);
        changepasswordtext=findViewById(R.id.changepassword_id);
        changepasswordtext.setOnClickListener(this);
        imagepicker=findViewById(R.id.imagepicker);
        imagepicker.setOnClickListener(this);
//        editdob=findViewById(R.id.dateofbirth);
//        editdob.setOnClickListener(this);



        if (user.getUsername().equals("null")){
            etname.setText("");
        }else{
            etname.setText(user.getUsername());
        }
        if (user.getGender().equals("null")){
            etgender.setText("");
        }else{
            etgender.setText(user.getGender());
        }
        if (user.getEmail().equals("null")){
            etemail.setText("");
        }else{
            etemail.setText(user.getEmail());
        }
        if (user.getMobile().equals("null")){

            etmobile.setText("");

        }else{
            etmobile.setText(user.getMobile());
        }

        Log.e("","user.getDob()= "+user.getDob());
//        if (user.getDob().equals("")){
//            editdob.setText("");
//
//        }else{
//            editdob.setText(user.getDob());
//        }

        if (user.getAddress().equals("null")){
            etaddress.setText("");
        }else{
            etaddress.setText(user.getAddress());
        }
        if (user.getProfile().equals("null")){

        }else{
//            Picasso.get().load(user.getProfile()).into(imgProfileAvatar);
            Picasso.get().load(user.getProfile()).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE).into(imgProfileAvatar);
        }
        if (user.getGender().equals("male")){
            male.setBackgroundResource(R.drawable.maleblue);
        }else if (user.getGender().equals("female")){
            female.setBackgroundResource(R.drawable.femailepurple);
        }
        btnsave=findViewById(R.id.btnsave);
        btnsave.setOnClickListener(this);
        if (user.getDob().equals("")){

        tvYear.setText(""+year);
        tvMonth.setText(""+month);
        tvDay.setText(""+day);

        }else{
//            editdob.setText(user.getDob());

            String arr[] = user.getDob().split("-");//25-9-2019
            String sDay = arr[0];//day
            String sMonth = arr[1];//month
            String sYear = arr[2];//Year

            year=Integer.parseInt(sYear);
            month=Integer.parseInt(sMonth);
            day=Integer.parseInt(sDay);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month-1);
            daysQty = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            tvYear.setText(""+year);
            tvMonth.setText(""+month);
            tvDay.setText(""+day);

//            tvYear.setText(sYear);
//            tvMonth.setText(sMonth);
//            tvDay.setText(sDay);

        }
        tvYear.setOnClickListener(this);
        tvMonth.setOnClickListener(this);
        tvDay.setOnClickListener(this);

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isStoragePermissionGranted() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
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
        if (v==imageViewback){
            onBackPressed();
        }

       else if(v==info){
            Intent intent = new Intent(EditProfileActivity.this, InfoActivity.class);
            startActivity(intent);
        }
        else if (v==tvYear){
            showYearDialog();
        }else if (v==tvMonth){
            showMonthDialog();
        }else if (v==tvDay){
            showDaysDialog();
        }

       /* if(v==editdob){
            datepick="0";
//            datepicker();

            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getSupportFragmentManager(), "datePicker");
        }*/
        else if (v==male){
            genderholder ="male";
            male.setBackgroundResource(R.drawable.maleblue);
            female.setBackgroundResource(R.drawable.signupbutton);
            etgender.setText(genderholder);
        }else if (v==female){
            genderholder = "female";
            female.setBackgroundResource(R.drawable.femailepurple);
            male.setBackgroundResource(R.drawable.signinsignup);
            etgender.setText(genderholder);
        }else if (v==imagepicker){
            if (Build.VERSION.SDK_INT >= 23) {
                if (isStoragePermissionGranted()) {
                    openGallery();
                } else {
                    ActivityCompat.requestPermissions(getParent(), permissions, 101);
                }
            } else {
                openGallery();
            }
        }else if (v==btnsave){
            validation();
        }else if (v==changepasswordtext){
            Intent intent = new Intent(EditProfileActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
    }

    private void datepicker() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (datepick.equals("0")){
//                    editdob.setText(dayOfMonth + "-" + (month + 1) + "-" +year );
                }
            }
        }, year, month, day);
        picker.show();

    }

    private void validation() {
        nameholder = etname.getText().toString();
        emailholder = etemail.getText().toString();
        phoneholder = etmobile.getText().toString().trim();
        genderholder = etgender.getText().toString();
        addressholder=etaddress.getText().toString();
//        dobholder=day+"-"+month+"-"+year;
        dobholder=tvDay.getText().toString()+"-"+tvMonth.getText().toString()+"-"+tvYear.getText().toString();

//        dobholder=editdob.getText().toString();//25-9-2019


        if (TextUtils.isEmpty(nameholder)){
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(emailholder)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneholder)) {
            Toast.makeText(this, "Please enter your mobile", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(genderholder)) {
            Toast.makeText(this, "Please select Gender", Toast.LENGTH_SHORT).show();
        }
        else if (!isValidMail(emailholder)) {
            Toast.makeText(this, "Please enter valid email id", Toast.LENGTH_SHORT).show();
        }
        else if (!isValidMobile(phoneholder)){

            Toast.makeText(this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(dobholder)){

            Toast.makeText(this, "Please enter Date of Birth", Toast.LENGTH_SHORT).show();
        }
        else if (phoneholder.length() < 10){
            Toast.makeText(this, "Please enter 10 digit mobile number", Toast.LENGTH_SHORT).show();
        }
        else {
            if (isNetworkConnected()) {
                presenter.UpdateCustomerProfile(idholder,nameholder,emailholder,phoneholder,genderholder,dobholder,addressholder,picture);
            }
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            new AlertDialog.Builder(getApplicationContext())
                    .setMessage("Please allow permission to open camera")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            ActivityCompat.requestPermissions(getParent(), permissions, 101);
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


           /* Uri imageUri = data.getData();
            file = new File(getPathFromURI(imageUri));
            compress();
        } else {
            Toast.makeText(getApplicationContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }*/
        }
    }

    /* Get the real path from the URI */
   /* private String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
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
            compressedImage = new Compressor(getApplicationContext())
                    .setMaxWidth(640)
                    .setMaxHeight(480)
                    .setQuality(50)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFile(file);
            String filePath = compressedImage.getPath();
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            imgProfileAvatar.setImageBitmap(bitmap);
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
    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    public void showError(String errorMessage) {
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void success(String response) {
        Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();
       /* onBackPressed();*/
        startActivity(new Intent(getApplicationContext(),DashBoardActivity.class));
        finish();
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
        final PrettyDialog prettyDialog = new PrettyDialog(EditProfileActivity.this);
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

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
        }
    }


    public void showYearDialog()
    {

        final Dialog d = new Dialog(this);
        d.setTitle("Year Picker");
        d.setContentView(R.layout.yeardialog);
        Button set = (Button) d.findViewById(R.id.button1);
        Button cancel = (Button) d.findViewById(R.id.button2);
        TextView year_text=(TextView)d.findViewById(R.id.year_text);
        year_text.setText(""+year);
        final NumberPicker nopicker = (NumberPicker) d.findViewById(R.id.numberPicker1);

        nopicker.setMaxValue(year+50);
        nopicker.setMinValue(year-50);
        nopicker.setWrapSelectorWheel(false);
        nopicker.setValue(year);
        nopicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        set.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                tvYear.setText(String.valueOf(nopicker.getValue()));
                d.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();


    }
    int daysQty=0;
    public void showMonthDialog()
    {

        final Dialog d = new Dialog(this);
        d.setTitle("Month Picker");
        d.setContentView(R.layout.yeardialog);
        Button set = (Button) d.findViewById(R.id.button1);
        Button cancel = (Button) d.findViewById(R.id.button2);
        TextView year_text=(TextView)d.findViewById(R.id.year_text);
        year_text.setText(""+month);
        final NumberPicker nopicker = (NumberPicker) d.findViewById(R.id.numberPicker1);

        nopicker.setMaxValue(12);
        nopicker.setMinValue(1);
        nopicker.setWrapSelectorWheel(false);
        nopicker.setValue(month);
        nopicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        set.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                tvMonth.setText(String.valueOf(nopicker.getValue()));
                tvDay.setText(""+1);
                d.dismiss();


                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,nopicker.getValue()-1);
//                int daysQty = calendar.getDaysNumber();
                daysQty = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();


    }
    public void showDaysDialog()
    {

        final Dialog d = new Dialog(this);
        d.setTitle("Day Picker");
        d.setContentView(R.layout.yeardialog);
        Button set = (Button) d.findViewById(R.id.button1);
        Button cancel = (Button) d.findViewById(R.id.button2);
        TextView year_text=(TextView)d.findViewById(R.id.year_text);
        year_text.setText(""+day);
        final NumberPicker nopicker = (NumberPicker) d.findViewById(R.id.numberPicker1);

        nopicker.setMaxValue(daysQty);
        nopicker.setMinValue(1);
        nopicker.setWrapSelectorWheel(false);
        nopicker.setValue(day);
        nopicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        set.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                tvDay.setText(String.valueOf(nopicker.getValue()));
                d.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();


    }


}

  /*
       changepasswordtext.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(EditProfileActivity.this, ChangePasswordActivity.class);
               startActivity(intent);
           }
       });*/
