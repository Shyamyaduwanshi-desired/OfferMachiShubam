package com.desired.offermachi.customer.ui;

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


import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

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
        setContentView(R.layout.product_activity);


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

        LinearLayout viewalloffer =(LinearLayout)findViewById(R.id.viewalloffer_id);
        viewalloffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, ViewStoreOfferActivity.class);
                startActivity(intent);

            }
        });
        shareiconimge =(LinearLayout)findViewById(R.id.shareicon_image_id);
        shareiconimge.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Itemshardialog();
         }
     });


      final ImageView likeimg =(ImageView)findViewById(R.id.heartfirst_image_id);

        likeimg.setOnClickListener(new View.OnClickListener() {
            String wish = "0";
            @Override
            public void onClick(View v) {

                if (wish.equals("0")) {
                    wish = "1";
                    likeimg.setImageResource(R.drawable.heart);
                }else if (wish.equals("1")) {

                    wish = "0";
                    likeimg.setImageResource(R.drawable.ic_like);

                }
            }
        });

        TextView productname =(TextView)findViewById(R.id.product_name_id);
        productname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, ViewAllOfferFollowActivity.class);
                startActivity(intent);
            }
        });

    }

    private void Itemshardialog() {
        final Dialog dialog = new Dialog(ProductActivity.this);
        dialog.setContentView(R.layout.share_item_activity);
        dialog.setTitle("Custom Dialog");
        LinearLayout message = (LinearLayout) dialog.findViewById(R.id.message_linear_id);
        LinearLayout whatsapp = (LinearLayout) dialog.findViewById(R.id.whatsapp_linear_id);
        LinearLayout facebook=(LinearLayout) dialog.findViewById(R.id.facebook_linear_id);
        dialog.show();

    }
    private void showdialog() {
        final Dialog dialog = new Dialog(ProductActivity.this);
        dialog.setContentView(R.layout.coupon_code_activity);
        dialog.setTitle("Custom Dialog");
        Button submitbutton=(Button)dialog.findViewById(R.id.coupon_button_apply_id);
        TextView checkbox=(TextView) dialog.findViewById(R.id.coupon_ok_id);
        dialog.show();
    }}


