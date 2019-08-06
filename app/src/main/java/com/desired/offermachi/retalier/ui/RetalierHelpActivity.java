package com.desired.offermachi.retalier.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desired.offermachi.R;

public class RetalierHelpActivity extends AppCompatActivity {

    ImageView imageViewback;
    TextView drop1,drop2,drop3,drop4;
    LinearLayout ly1,ly2,ly3,ly4;
    int count=0;
    int count2=0;
    int count3=0;
    int count4=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_help_activity);

        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        drop1=findViewById(R.id.drop1);
        drop2=findViewById(R.id.drop2);
        drop3=findViewById(R.id.drop3);
        drop4=findViewById(R.id.drop4);
        ly1= findViewById(R.id.para1);
        ly2=findViewById(R.id.para2);
        ly3=findViewById(R.id.para3);
        ly4=findViewById(R.id.para4);

        drop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count==0){
                    ly1.setVisibility(View.VISIBLE);
                    count=1;
                }else{
                    ly1.setVisibility(View.GONE);
                    count=0;
                }


            }
        });


        drop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count2==0){
                    ly2.setVisibility(View.VISIBLE);
                    count2=1;
                }else{
                    ly2.setVisibility(View.GONE);
                    count2=0;
                }

            }
        });
        drop3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count3==0){
                    ly3.setVisibility(View.VISIBLE);
                    count3=1;
                }else{
                    ly3.setVisibility(View.GONE);
                    count3=0;
                }
            }
        });
        drop4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count4==0){
                    ly4.setVisibility(View.VISIBLE);
                    count4=1;
                }else{
                    ly4.setVisibility(View.GONE);
                    count4=0;
                }
            }
        });

    }
}