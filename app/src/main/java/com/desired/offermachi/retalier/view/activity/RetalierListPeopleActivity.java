package com.desired.offermachi.retalier.view.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.desired.offermachi.R;

public class RetalierListPeopleActivity  extends AppCompatActivity {

    Button viewcouponbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_list_of_people_activity);

        viewcouponbutton=(Button)findViewById(R.id.viewcouponcode_id);
        viewcouponbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Qrcodedialog();
            }
        });
    }
    private void Qrcodedialog() {
        final Dialog dialog = new Dialog(RetalierListPeopleActivity.this);
        dialog.setContentView(R.layout.coupon_code_activity);
        dialog.setTitle("Custom Dialog");
        Button submitbutton=(Button)dialog.findViewById(R.id.coupon_button_apply_id);
        TextView checkbox=(TextView) dialog.findViewById(R.id.coupon_ok_id);
        dialog.show();
    }
}
