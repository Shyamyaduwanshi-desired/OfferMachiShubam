package com.desired.offermachi.customer.view.activity;

import android.Manifest;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.CustomerUpdateProfilePresenter;
import com.desired.offermachi.retalier.constant.FileUtil;
import com.desired.offermachi.retalier.presenter.ProfileImagePresenter;
import com.desired.offermachi.retalier.view.activity.RetalierProfileActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, CustomerUpdateProfilePresenter.UpdateProfile {
    private CustomerUpdateProfilePresenter presenter;
    LinearLayout male,female;
    ImageView  imageViewback;
    TextView changepasswordtext;
    EditText etname,etgender,etemail,etmobile,etaddress;
    CircleImageView imgProfileAvatar;
    LinearLayout imagepicker;
    String nameholder,emailholder,phoneholder,genderholder,addressholder,idholder;
    Button btnsave;
    private String picture = "";
    private File file, compressedImage;
    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);
        initview();
    }
    private void initview(){
        presenter=new CustomerUpdateProfilePresenter(EditProfileActivity.this,EditProfileActivity.this);
        User user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
        idholder=user.getId();
        etname=findViewById(R.id.name);
        etgender=findViewById(R.id.gender);
        etemail=findViewById(R.id.email);
        etmobile=findViewById(R.id.mobile);
        etaddress=findViewById(R.id.address);
        imgProfileAvatar=findViewById(R.id.imgProfileAvatar);
        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(this);
        male=findViewById(R.id.lymale);
        male.setOnClickListener(this);
        female=findViewById(R.id.lyfemale);
        female.setOnClickListener(this);
        changepasswordtext=findViewById(R.id.changepassword_id);
        changepasswordtext.setOnClickListener(this);
        imagepicker=findViewById(R.id.imagepicker);
        imagepicker.setOnClickListener(this);
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
        if (user.getAddress().equals("null")){
            etaddress.setText("");
        }else{
            etaddress.setText(user.getAddress());
        }
        if (user.getProfile().equals("null")){

        }else{
            Picasso.get().load(user.getProfile()).into(imgProfileAvatar);
        }
        if (user.getGender().equals("male")){
            male.setBackgroundResource(R.drawable.maleblue);
        }else if (user.getGender().equals("female")){
            female.setBackgroundResource(R.drawable.femailepurple);
        }
        btnsave=findViewById(R.id.btnsave);
        btnsave.setOnClickListener(this);
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
        }else if (v==male){
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

    private void validation() {
        nameholder = etname.getText().toString();
        emailholder = etemail.getText().toString();
        phoneholder = etmobile.getText().toString().trim();
        genderholder = etgender.getText().toString();
        addressholder=etaddress.getText().toString();
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
        }
        else if (phoneholder.length() < 10){
            Toast.makeText(this, "Please enter 10 digit mobile number", Toast.LENGTH_SHORT).show();
        }
        else {
            if (isNetworkConnected()) {
                presenter.UpdateCustomerProfile(idholder,nameholder,emailholder,phoneholder,genderholder,addressholder,picture);
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
        onBackPressed();
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
}

  /*
       changepasswordtext.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(EditProfileActivity.this, ChangePasswordActivity.class);
               startActivity(intent);
           }
       });*/
