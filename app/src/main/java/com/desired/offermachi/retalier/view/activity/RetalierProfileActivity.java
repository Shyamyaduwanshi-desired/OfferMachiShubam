package com.desired.offermachi.retalier.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.constant.FileUtil;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.presenter.ProfileImagePresenter;
import com.desired.offermachi.retalier.presenter.ProfilePresenter;
import com.desired.offermachi.retalier.view.fragment.ProfileDetailsFragment;
import com.desired.offermachi.retalier.view.fragment.PersonaltoreFragment;
import com.desired.offermachi.retalier.view.adapter.RetalierTabLayoutAdapter;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class RetalierProfileActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, View.OnClickListener, ProfileImagePresenter.ProfileImage {

    public TabLayout tabLayout;
    RetalierTabLayoutAdapter adapter;
    private ViewPager viewPager1;
    ImageView imageViewback;
    CircleImageView imgProfileAvatar;
    LinearLayout imagepicker;
    private String picture = "";
    String ImageHolder,idholder;
    private File file, compressedImage;
    private ProfileImagePresenter presenter;
    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_my_profile_activity);
        presenter = new ProfileImagePresenter(this, RetalierProfileActivity.this);
        init();
    }
    private void init(){
        UserModel user = SharedPrefManagerLogin.getInstance(getApplicationContext()).getUser();
        idholder=user.getId();
        ImageHolder=user.getProfile();
        Log.e("profilepicture", "ImageHolder="+ImageHolder );
        imageViewback=findViewById(R.id.imageback);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager1 = (ViewPager) findViewById(R.id.pager);
        imgProfileAvatar=findViewById(R.id.imgProfileAvatar);
        imagepicker=findViewById(R.id.imagepicker);
        tabLayout.setOnTabSelectedListener(this);
        imageViewback.setOnClickListener(this);
        imagepicker.setOnClickListener(this);
        if (ImageHolder.equals("")||ImageHolder.equals("NA")){

        }else{
            Picasso.get().load(ImageHolder).into(imgProfileAvatar);

        }
        viewPager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                tabLayout.setScrollPosition(position, 0f, true);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        RetalierProfileActivity.Pager adapter = new RetalierProfileActivity.Pager(RetalierProfileActivity.this.getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager1.setAdapter(adapter);
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
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 100);
    }
    @Override
    public void onClick(View v) {
        if (v==imagepicker){
            if (Build.VERSION.SDK_INT >= 23) {
                if (isStoragePermissionGranted()) {
                    openGallery();
                } else {
                    ActivityCompat.requestPermissions(getParent(), permissions, 101);
                }
            } else {
                openGallery();
            }
        }else if (v==imageViewback){
            onBackPressed();
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
            uploadimage(picture);
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
     private void uploadimage(String image){
         try {
      presenter.updateProfileImage(idholder,image);
     } catch (Exception e) {
        e.printStackTrace();
    }

      }
    @Override
    public void success(String response) {
        Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void error(String response) {
        showDialog(response);
    }

    @Override
    public void fail(String response) {
        showDialog(response);
    }
    private void showDialog(String message) {
        new AlertDialog.Builder(RetalierProfileActivity.this)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                }).show();
    }
    //tablayout
    public class Pager extends FragmentStatePagerAdapter {

        //integer to count number of tabs
        int tabCount;

        //Constructor to the class
        public Pager(FragmentManager fm, int tabCount) {
            super(fm);
            //Initializing tab count
            this.tabCount = tabCount;
        }

        //Overriding method getItem
        @Override
        public Fragment getItem(int position) {
            //Returning the current tabs
            switch (position) {
                case 0:
                    ProfileDetailsFragment tab1 = new ProfileDetailsFragment();
                    return tab1;
                case 1:
                    PersonaltoreFragment tab2 = new PersonaltoreFragment();
                    return tab2;
                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            return tabCount;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager1.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    public void showError(String errorMessage) {
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }
}


