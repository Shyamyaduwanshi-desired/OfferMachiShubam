package com.desired.offermachi.customer.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.desired.offermachi.R;

public class SmartShoppingRemoveActivity  extends AppCompatActivity {

    ImageView imageViewback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_shopping_text_activity);

        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TextView filtertext=(TextView)findViewById(R.id.filter_text_id);
        filtertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), FilterShowActivity.class);
                startActivity(intent);
            }
        });

        TextView sortbytext=(TextView)findViewById(R.id.sortby_text_id);
        sortbytext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(SmartShoppingRemoveActivity.this);
                dialog.setContentView(R.layout.sort_dialog_activity);
                dialog.setTitle("Custom Dialog");
                RelativeLayout atoz=(RelativeLayout)dialog.findViewById(R.id.atoz_id);
                RelativeLayout ztoa=(RelativeLayout)dialog.findViewById(R.id.ztoa_id);
                dialog.show();

            }
        });
    }
}
