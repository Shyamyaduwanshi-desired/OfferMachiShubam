package com.desired.offermachi.customer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desired.offermachi.R;

public class EditProfileActivity extends AppCompatActivity {

    LinearLayout male,female;
    TextView changepasswordtext;
    ImageView  imageViewback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);

        male=findViewById(R.id.lymale);
        female=findViewById(R.id.lyfemale);

        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male.setBackgroundResource(R.drawable.maleblue);
                female.setBackgroundResource(R.drawable.signupbutton);

            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                female.setBackgroundResource(R.drawable.femailepurple);
                male.setBackgroundResource(R.drawable.signinsignup);

            }
        });

       changepasswordtext=(TextView)findViewById(R.id.changepassword_id);
       changepasswordtext.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(EditProfileActivity.this, ChangePasswordActivity.class);
               startActivity(intent);
           }
       });

    }
}

