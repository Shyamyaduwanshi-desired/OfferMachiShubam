package com.desired.offermachi.retalier.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.desired.offermachi.R;

public class RetalierCustomerSupportActivity extends AppCompatActivity {

    Button submitbutton;
    ImageView imageViewback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_customer_support_activity);

        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        submitbutton=(Button)findViewById(R.id.submit_customer_button_id);
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitdialog();
            }
        });
    }

    private void submitdialog() {
        final Dialog dialog = new Dialog(RetalierCustomerSupportActivity.this);
        dialog.setContentView(R.layout.thanks_activity);
        dialog.setTitle("Custom Dialog");
        TextView text1 = (TextView) dialog.findViewById(R.id.thankkutext_id);
        ImageView image=(ImageView)dialog.findViewById(R.id.cancle_id);
        dialog.show();
    }

}
