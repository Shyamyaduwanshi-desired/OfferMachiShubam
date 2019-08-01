package com.desired.offermachi.customer.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.ui.DashBoardActivity;
import com.desired.offermachi.customer.ui.ProductActivity;


public class MycouponsFragment extends Fragment {
    View view;


    public MycouponsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_coupons_activity, container, false);
        ((DashBoardActivity)getActivity()).setToolTittle("My Coupons",2);
        Button viewcodebutton=(Button)view.findViewById(R.id.viewcode_button_id);
        viewcodebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewcodedialog();
            }
        });

        Button viewcodebutton1=(Button)view.findViewById(R.id.viewcode_buttonfirst_id);
        viewcodebutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewcodedialog();
            }
        });

        Button viewcodebutton2=(Button)view.findViewById(R.id.viewcode_buttonsecond_id);
        viewcodebutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewcodedialog();
            }
        });

        Button viewcodebutton3=(Button)view.findViewById(R.id.viewcode_buttonthird_id);
        viewcodebutton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewcodedialog();
            }
        });

        TextView share =(TextView)view.findViewById(R.id.share_id);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedialog();
            }
        });
        TextView share1 =(TextView)view.findViewById(R.id.sharesecond);
        share1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedialog();
            }
        });

        TextView share2 =(TextView)view.findViewById(R.id.share_third);
        share2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedialog();
            }
        });
        TextView share3 =(TextView)view.findViewById(R.id.share_forth);
        share3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedialog();
            }
        });

        TextView firsttext=(TextView)view.findViewById(R.id.textname_first_id);
        TextView firsttext1=(TextView)view.findViewById(R.id.textname_second_id);
        TextView firsttext2=(TextView)view.findViewById(R.id.textname_third_id);
        TextView firsttext3=(TextView)view.findViewById(R.id.textname_forth_id);

        firsttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ProductActivity.class);
                startActivity(intent);
            }
        });
        firsttext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ProductActivity.class);
                startActivity(intent);
            }
        });

        firsttext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ProductActivity.class);
                startActivity(intent);
            }
        });

        firsttext3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ProductActivity.class);
                startActivity(intent);
            }
        });


        final ImageView like1=(ImageView)view.findViewById(R.id.heart_image_id);
        final ImageView like2=(ImageView)view.findViewById(R.id.heart_imagesecond_id);
        final ImageView like3=(ImageView)view.findViewById(R.id.heart_imagethird_id);
        final ImageView like4=(ImageView)view.findViewById(R.id.heart_imageforth_id);

        like1.setOnClickListener(new View.OnClickListener() {
            String wish = "0";
            @Override
            public void onClick(View v) {

                if (wish.equals("0")) {
                    wish = "1";
                    like1.setImageResource(R.drawable.heart);
                }else if (wish.equals("1")) {

                    wish = "0";
                    like1.setImageResource(R.drawable.ic_like);

                }
            }
        });

        like2.setOnClickListener(new View.OnClickListener() {
            String wish = "0";
            @Override
            public void onClick(View v) {

                if (wish.equals("0")) {
                    wish = "1";
                    like2.setImageResource(R.drawable.heart);
                }else if (wish.equals("1")) {

                    wish = "0";
                    like2.setImageResource(R.drawable.ic_like);

                }
            }
        });



        like3.setOnClickListener(new View.OnClickListener() {
            String wish = "0";
            @Override
            public void onClick(View v) {

                if (wish.equals("0")) {
                    wish = "1";
                    like3.setImageResource(R.drawable.heart);
                }else if (wish.equals("1")) {

                    wish = "0";
                    like3.setImageResource(R.drawable.ic_like);

                }
            }
        });


        like4.setOnClickListener(new View.OnClickListener() {
            String wish = "0";
            @Override
            public void onClick(View v) {

                if (wish.equals("0")) {
                    wish = "1";
                    like4.setImageResource(R.drawable.heart);
                }else if (wish.equals("1")) {

                    wish = "0";
                    like4.setImageResource(R.drawable.ic_like);

                }
            }
        });








        return  view;
    }

    private void sharedialog() {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.share_item_activity);
        dialog.setTitle("Custom Dialog");
        LinearLayout text1 = (LinearLayout) dialog.findViewById(R.id.message_linear_id);
        LinearLayout text2 = (LinearLayout) dialog.findViewById(R.id.whatsapp_linear_id);
        LinearLayout text3 = (LinearLayout) dialog.findViewById(R.id.facebook_linear_id);
        dialog.show();

    }


    private void viewcodedialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.coupon_code_activity);
        dialog.setTitle("Custom Dialog");

        TextView text1 = (TextView) dialog.findViewById(R.id.code_text_id);
        ImageView image=(ImageView)dialog.findViewById(R.id.qr_code_img_id);
        LinearLayout linaer=(LinearLayout)dialog.findViewById(R.id.or_linear_id);
        TextView couponcodetext=(TextView)dialog.findViewById(R.id.couponcode_text_id);
        Button button=(Button)dialog.findViewById(R.id.coupon_button_apply_id);

        TextView textView=(TextView)dialog.findViewById(R.id.coupon_ok_id);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messagedialog();
            }
        });

        dialog.show();

    }

    private void messagedialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.share_item_activity);
        dialog.setTitle("Custom Dialog");
        LinearLayout text1 = (LinearLayout) dialog.findViewById(R.id.message_linear_id);
        LinearLayout text2 = (LinearLayout) dialog.findViewById(R.id.whatsapp_linear_id);
        LinearLayout text3 = (LinearLayout) dialog.findViewById(R.id.facebook_linear_id);
        dialog.show();

    }
}




