package com.desired.offermachi.retalier.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.model.slider_model;
import com.desired.offermachi.customer.ui.ProductActivity;
import com.desired.offermachi.customer.ui.ViewStoreOfferActivity;

import java.util.ArrayList;

public class RetalierProductActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    ArrayList<slider_model> arrayList = new ArrayList<>();
    private Context mContext;
    ImageView imageViewback;
    Button couponbutton;
    LinearLayout shareiconimge;
    FragmentManager FM;
    FragmentTransaction FT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_product_activity);


        imageViewback=findViewById(R.id.imageviewback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        couponbutton =(Button)findViewById(R.id.coupon_button_id);
        couponbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog();
            }
        });

//        LinearLayout viewalloffer =(LinearLayout)findViewById(R.id.viewalloffer_id);
//        viewalloffer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(RetalierProductActivity.this, RetalierViewOfferActivity.class);
//                startActivity(intent);
//
//            }
//        });
    }
    private void showdialog() {
        final Dialog dialog = new Dialog(RetalierProductActivity.this);
        dialog.setContentView(R.layout.coupon_code_activity);
        dialog.setTitle("Custom Dialog");
        Button submitbutton=(Button)dialog.findViewById(R.id.coupon_button_apply_id);
        TextView checkbox=(TextView) dialog.findViewById(R.id.coupon_ok_id);
        dialog.show();

    }

}


