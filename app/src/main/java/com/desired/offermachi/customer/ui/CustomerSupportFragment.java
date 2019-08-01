package com.desired.offermachi.customer.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.desired.offermachi.R;

public class CustomerSupportFragment extends Fragment {
    View view;


    public CustomerSupportFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.customer_support_activity, container, false);

       Button submitbutton=(Button)view.findViewById(R.id.submit_button_id);
         submitbutton.setOnClickListener(new View.OnClickListener() {
          @Override
           public void onClick(View v) {
              submitdialog();
           }
        });
         return  view;
    }
    private void submitdialog() {
        final Dialog dialog = new Dialog(getContext());
       dialog.setContentView(R.layout.thanks_activity);
       dialog.setTitle("Custom Dialog");
        TextView text1 = (TextView) dialog.findViewById(R.id.thankkutext_id);
        ImageView image=(ImageView)dialog.findViewById(R.id.cancle_id);
       dialog.show();

    }
}


//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
// extends AppCompatActivity {
//    Button submitbutton;
//    ImageView imageViewback;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.customer_support_activity);
//        submitbutton=(Button)findViewById(R.id.submit_button_id);
//
////
////        imageViewback=findViewById(R.id.imageback);
////        imageViewback.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                onBackPressed();
////            }
////        });
//        submitbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                submitdialog();
//            }
//        });
//
//
//    }
//    private void submitdialog() {
//        final Dialog dialog = new Dialog(CustomerSupportFragment.this);
//        dialog.setContentView(R.layout.thanks_activity);
//        dialog.setTitle("Custom Dialog");
//        TextView text1 = (TextView) dialog.findViewById(R.id.thankkutext_id);
//        ImageView image=(ImageView)dialog.findViewById(R.id.cancle_id);
//        dialog.show();
//    }
//}