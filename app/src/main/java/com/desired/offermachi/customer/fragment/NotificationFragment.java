package com.desired.offermachi.customer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.ui.DashBoardActivity;
import com.desired.offermachi.customer.ui.DoNotDistrubActivity;


public class NotificationFragment extends Fragment {
    View view;


    public NotificationFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.notification_activity, container, false);
        ((DashBoardActivity)getActivity()).setToolTittle("Notification",2);

        Switch simpleSwitch =(Switch)view.findViewById(R.id.simpleSwitch);
        simpleSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), DoNotDistrubActivity.class);
                startActivity(intent);

            }
        });

        return  view;
    }
}













//
//        extends AppCompatActivity {
//    Switch simpleSwitch;
//    ImageView imageViewback;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.notification_activity);
//
//        imageViewback=findViewById(R.id.imageback);
//        imageViewback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//        simpleSwitch =(Switch)findViewById(R.id.simpleSwitch);
//        simpleSwitch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(NotificationFragment.this, DoNotDistrubActivity.class);
//                startActivity(intent);
//
//            }
//        });
//    }
//
//
//
//



















//    private void showdilog() {
//        final Dialog dialog = new Dialog(NotificationFragment.this);
//        dialog.setContentView(R.layout.do_not_distrub_activity);
//        dialog.setTitle("Custom Dialog");
//        TextView text1 = (TextView) dialog.findViewById(R.id.donotdistrub_text_id);
//        LinearLayout checkbox=(LinearLayout) dialog.findViewById(R.id.checkbox_id);
//        Button submitbutton=(Button)dialog.findViewById(R.id.submit_button_id);
//        dialog.show();
//        submitbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showdilogsecond();
//            }
//        });
//    }
//    private void showdilogsecond() {
//
//        final Dialog dialog = new Dialog(NotificationFragment.this);
//        dialog.setContentView(R.layout.disturb_dialog_activity);
//        dialog.setTitle("Custom Dialog");
//        TextView text1 = (TextView) dialog.findViewById(R.id.distrub_text_id);
//        LinearLayout checkbox=(LinearLayout) dialog.findViewById(R.id.checkbox_id);
//        Button submitbutton=(Button)dialog.findViewById(R.id.submit_button_id);
//        dialog.show();
//
//    }




