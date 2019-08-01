package com.desired.offermachi.customer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.desired.offermachi.R;

public class SmartShoppigActivity extends AppCompatActivity {
    Switch simpleSwitchsmartshopping;
    ImageView imageViewback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_shopping_fragment);

        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        simpleSwitchsmartshopping=(Switch)findViewById(R.id.simpleSwitch_smart_shopping);
        simpleSwitchsmartshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SmartShoppigActivity.this, SmartShoppingSecondActivity.class);
                startActivity(intent);
            }
        });

    }
}

