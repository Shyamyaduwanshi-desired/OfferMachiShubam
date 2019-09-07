package com.desired.offermachi.retalier.view.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.model.DealsModel;
import com.desired.offermachi.retalier.model.PeopleModel;
import com.desired.offermachi.retalier.presenter.RetailerPeopleListPresenter;
import com.desired.offermachi.retalier.view.activity.RetalierProductActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PeopleListAdapter extends RecyclerView.Adapter<PeopleListAdapter.MyViewHolder> {

    private ArrayList<PeopleModel> peopleModelArrayList;
    private final Context mContext;

    public PeopleListAdapter(Context context,ArrayList<PeopleModel> peopleModelArrayList) {
        this.peopleModelArrayList = peopleModelArrayList;
        this.mContext = context;
    }

    @Override
    public PeopleListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.people_list, parent, false);

        PeopleListAdapter.MyViewHolder myViewHolder = new PeopleListAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final PeopleListAdapter.MyViewHolder holder, final int i) {

        final PeopleModel peopleModel = peopleModelArrayList.get(i);
        holder.username.setText(peopleModel.getPeoplename());
        if(peopleModel.getPeopleimage().equals("")){
        }else{
            Picasso.get().load(peopleModel.getPeopleimage()).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(holder.profileimage);
        }
        holder.btncoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog((Activity) v.getContext());
                dialog.setContentView(R.layout.coupon_code_activity);
                dialog.setTitle("Custom Dialog");
                ImageView image=(ImageView)dialog.findViewById(R.id.qr_code_img_id);
                if(peopleModel.getCouponimage().equals("")){
                }else{
                    Picasso.get().load(peopleModel.getCouponimage()).networkPolicy(NetworkPolicy.NO_CACHE)
                            .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(image);
                }
                Button button=(Button)dialog.findViewById(R.id.coupon_button_apply_id);
                button.setText(peopleModel.getCouponcode());
                TextView textView=(TextView)dialog.findViewById(R.id.coupon_ok_id);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }

                });

                dialog.show();
            }
        });


    }
    @Override
    public int getItemCount() {
        return peopleModelArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileimage;
        TextView username;
        Button btncoupon;

        public MyViewHolder(View itemView) {
            super(itemView);
            profileimage = (CircleImageView) itemView.findViewById(R.id.imgProfileAvatar);
            username = (TextView) itemView.findViewById(R.id.followname);
            btncoupon=(Button)itemView.findViewById(R.id.viewcouponcode_id);

        }
    }
}

