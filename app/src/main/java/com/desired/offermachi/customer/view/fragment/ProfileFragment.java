package com.desired.offermachi.customer.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.view.activity.DashBoardActivity;
import com.desired.offermachi.customer.view.activity.EditProfileActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    LinearLayout edittextlinear;
    EditText etname,etgender,etemail,etmobile,etaddress;
    CircleImageView imgProfileAvatar;
    View view;
    TextView editdob;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_profile_activity, container, false);
       ((DashBoardActivity)getActivity()).setToolTittle("My Account",2);
        initview();
        return  view;
    }
    private void initview(){
        User user = UserSharedPrefManager.getInstance(getContext()).getCustomer();
        etname=view.findViewById(R.id.name);
        etgender=view.findViewById(R.id.gender);
        etemail=view.findViewById(R.id.email);
        etmobile=view.findViewById(R.id.mobile);
        editdob=view.findViewById(R.id.dob_id);
        etaddress=view.findViewById(R.id.address);
        imgProfileAvatar=view.findViewById(R.id.imgProfileAvatar);
        if (user.getUsername().equals("null")){
            etname.setText("");
        }else{
            etname.setText(user.getUsername());
        }
        if (user.getGender().equals("null")){
            etgender.setText("");
        }else{
            etgender.setText(user.getGender());
        }
        if (user.getEmail().equals("null")){
            etemail.setText("");
        }else{
            etemail.setText(user.getEmail());
        }
        if (user.getMobile().equals("null")){
            etmobile.setText("");
        }else{
            etmobile.setText(user.getMobile());
        }

        if (user.getDob().equals("null")){
            editdob.setText("");
        }else{
            editdob.setText(user.getDob());
        }

        if (user.getAddress().equals("null")){
            etaddress.setText("");
        }else{
            etaddress.setText(user.getAddress());
        }
        if (user.getProfile().equals("null")){

        }else{
            Picasso.get().load(user.getProfile()).into(imgProfileAvatar);
        }
        edittextlinear=view.findViewById(R.id.edit_text_linear_id);
        edittextlinear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==edittextlinear){
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(intent);
        }
    }
}




