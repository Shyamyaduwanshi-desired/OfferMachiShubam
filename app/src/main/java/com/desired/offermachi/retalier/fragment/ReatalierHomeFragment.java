package com.desired.offermachi.retalier.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.fragment.HomeFragment;
import com.desired.offermachi.retalier.ui.RetalierViewOfferDiscount;

import java.util.Calendar;

public class ReatalierHomeFragment  extends Fragment {
    View view;
    int count=0;
    int countBACK=0;
    DatePickerDialog picker;
    EditText eText,editexpirydate;
    public ReatalierHomeFragment() {
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.retalier_home_fragment, container, false);
        LinearLayout selecttypelinear =(LinearLayout)view.findViewById(R.id.select_type_linear_hide_id);
        final LinearLayout fistcircle =(LinearLayout)view.findViewById(R.id.firstcircle_id);
        final LinearLayout firsthomelinear=(LinearLayout)view.findViewById(R.id.firsthome_linear_id);
        final LinearLayout secondlinear=(LinearLayout)view.findViewById(R.id.second_home_linear_id);

        eText=(EditText)view.findViewById(R.id.editText1);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        eText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        editexpirydate=(EditText)view.findViewById(R.id.editexpirydate);
        editexpirydate.setInputType(InputType.TYPE_NULL);
        editexpirydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        editexpirydate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });


        final EditText start_Time = (EditText)view.findViewById(R.id.time_view_start);

        start_Time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        start_Time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        final EditText end_Time = (EditText)view.findViewById(R.id.time_view_end);

        end_Time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        end_Time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        Button nextbutton=(Button)view.findViewById(R.id.next_button_id);
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countBACK=1;
                if (count==0){
                    firsthomelinear.setVisibility(View.GONE);
                    secondlinear.setVisibility(View.VISIBLE);
                    fistcircle.setBackgroundResource(R.drawable.colour_background);

                    count=1;
                }else{
                    firsthomelinear.setVisibility(View.VISIBLE);
                    secondlinear.setVisibility(View.GONE);
                    fistcircle.setBackgroundResource(R.drawable.simple_circle_background);
                    count=0;
                }
            }
        });

        Button submitbutton =(Button)view.findViewById(R.id.submit_button_id);
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RetalierViewOfferDiscount.class);
                startActivity(intent);
            }
        });
        return  view;
    }
}


