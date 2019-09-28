package com.desired.offermachi.customer.view.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.desired.offermachi.R;

import java.util.Calendar;

public class ActTestDate extends AppCompatActivity {
    ImageView cancle;
    Button b,btmonth,btday;
    static Dialog d ;
    int year = Calendar.getInstance().get(Calendar.YEAR);
    int month = Calendar.getInstance().get(Calendar.MONTH);
    int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test_date);
        b = (Button) findViewById(R.id.year);
        btmonth = (Button) findViewById(R.id.month);
        btday = (Button) findViewById(R.id.day);

        b.setText(""+year);
        btmonth.setText(""+month);
        btday.setText(""+day);


        b.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                showYearDialog();
            }
        });

        btmonth.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                showMonthDialog();
            }
        });
        btday.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                showDaysDialog();
            }
        });
    }


    public void showYearDialog()
    {

        final Dialog d = new Dialog(this);
        d.setTitle("Year Picker");
        d.setContentView(R.layout.yeardialog);
        Button set = (Button) d.findViewById(R.id.button1);
        Button cancel = (Button) d.findViewById(R.id.button2);
        TextView year_text=(TextView)d.findViewById(R.id.year_text);
        year_text.setText(""+year);
        final NumberPicker nopicker = (NumberPicker) d.findViewById(R.id.numberPicker1);

        nopicker.setMaxValue(year+50);
        nopicker.setMinValue(year-50);
        nopicker.setWrapSelectorWheel(false);
        nopicker.setValue(year);
        nopicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        set.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                b.setText(String.valueOf(nopicker.getValue()));
                d.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();


    }
    int daysQty=0;
    public void showMonthDialog()
    {

        final Dialog d = new Dialog(this);
        d.setTitle("Month Picker");
        d.setContentView(R.layout.yeardialog);
        Button set = (Button) d.findViewById(R.id.button1);
        Button cancel = (Button) d.findViewById(R.id.button2);
        TextView year_text=(TextView)d.findViewById(R.id.year_text);
        year_text.setText(""+month);
        final NumberPicker nopicker = (NumberPicker) d.findViewById(R.id.numberPicker1);

        nopicker.setMaxValue(12);
        nopicker.setMinValue(1);
        nopicker.setWrapSelectorWheel(false);
        nopicker.setValue(month);
        nopicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        set.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                btmonth.setText(String.valueOf(nopicker.getValue()));
                d.dismiss();


                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,nopicker.getValue()-1);
//                int daysQty = calendar.getDaysNumber();
                 daysQty = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();


    }
    public void showDaysDialog()
    {

        final Dialog d = new Dialog(this);
        d.setTitle("Day Picker");
        d.setContentView(R.layout.yeardialog);
        Button set = (Button) d.findViewById(R.id.button1);
        Button cancel = (Button) d.findViewById(R.id.button2);
        TextView year_text=(TextView)d.findViewById(R.id.year_text);
        year_text.setText(""+day);
        final NumberPicker nopicker = (NumberPicker) d.findViewById(R.id.numberPicker1);

        nopicker.setMaxValue(daysQty);
        nopicker.setMinValue(1);
        nopicker.setWrapSelectorWheel(false);
        nopicker.setValue(day);
        nopicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        set.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                btday.setText(String.valueOf(nopicker.getValue()));
                d.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();


    }

}

