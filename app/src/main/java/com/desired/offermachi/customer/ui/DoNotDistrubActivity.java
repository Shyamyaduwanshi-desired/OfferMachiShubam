package com.desired.offermachi.customer.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.desired.offermachi.R;

public class DoNotDistrubActivity extends AppCompatActivity {

    RadioButton selectallbuttomn;
    LinearLayout dayslinear;
    int count=0;
    int countBACK=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.do_not_distrub_activity);

        selectallbuttomn=(RadioButton)findViewById(R.id.selectday_id);
        dayslinear=(LinearLayout)findViewById(R.id.days_check_box_id);
        selectallbuttomn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                countBACK=1;
                if (count==0){
                    dayslinear.setVisibility(View.VISIBLE);

                    count=1;
                }else{
                    dayslinear.setVisibility(View.GONE);
                    count=0;
                }
            }
        });

        Button donotdistrubbutton=(Button)findViewById(R.id.submit_button_id);
        donotdistrubbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donotdistrubmessagedialog();
            }
        });
    }
    private void donotdistrubmessagedialog() {
        final Dialog dialog = new Dialog(DoNotDistrubActivity.this);
        dialog.setContentView(R.layout.disturb_dialog_activity);
        dialog.setTitle("Custom Dialog");
        TextView text1 = (TextView) dialog.findViewById(R.id.distrub_text_id);
        LinearLayout checkbox=(LinearLayout) dialog.findViewById(R.id.checkbox_id);
        Button submitbutton=(Button)dialog.findViewById(R.id.submit_button_id);
        dialog.show();

    }
}
