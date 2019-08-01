package com.desired.offermachi.retalier.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desired.offermachi.R;

public class RetalierPushActivity extends AppCompatActivity {

    Button pushoffer;
    int count=0;
    int countBACK=0;
    ImageView imageViewback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reatalier_push_offer_activity);

        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        pushoffer =(Button)findViewById(R.id.push_offer_button_id);
        pushoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slectfollowdialog();
            }
        });
    }

    private void slectfollowdialog() {

        final Dialog dialog = new Dialog(RetalierPushActivity.this);
        dialog.setContentView(R.layout.select_follow_dialog);
        dialog.setTitle("Custom Dialog");
        LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.selected_spiner_id);
        final LinearLayout hidelinear=(LinearLayout) dialog.findViewById(R.id.name_hide_id);
        TextView selecttext=(TextView)dialog.findViewById(R.id.select_text_id);
        View view=(View)dialog.findViewById(R.id.view_id);
        ImageView spiner=(ImageView)dialog.findViewById(R.id.downarrow_id);
        TextView text1=(TextView)dialog.findViewById(R.id.name_first_id);
        TextView text2=(TextView)dialog.findViewById(R.id.name_second_id);
        TextView text3=(TextView)dialog.findViewById(R.id.name_third_id);
        TextView text4=(TextView)dialog.findViewById(R.id.name_forth_id);
        TextView text5=(TextView)dialog.findViewById(R.id.name_fifth_id);
        TextView text6=(TextView)dialog.findViewById(R.id.name_six_id);
        dialog.show();
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countBACK=1;
                if (count==0){
                    hidelinear.setVisibility(View.VISIBLE);
                    count=1;
                }else{
                    hidelinear.setVisibility(View.GONE);
                    count=0;
                }
            }
        });

    }
}

