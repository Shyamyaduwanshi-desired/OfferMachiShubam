package com.desired.offermachi.retalier.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.desired.offermachi.R;
import com.desired.offermachi.retalier.model.ImageBean;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class SelectedImageAdapter extends RecyclerView.Adapter<SelectedImageAdapter.MyViewHolder> {
    private List<ImageBean> list;
    private Context context;
    private ImageClick planClick;

    public SelectedImageAdapter(Context context, List<ImageBean> list, ImageClick planClick) {
        this.context = context;
        this.list = list;
        this.planClick = planClick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_selected_pic, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


//                Glide.with(context)
//                .load(new File(list.get(position).getImagePath()))
//                .placeholder(R.drawable.shortlogo)
//                .into(holder.ivPic);

        Picasso.get().load(new File(list.get(position).getImagePath())).placeholder(R.drawable.shortlogo).into(holder.ivPic);

       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planClick.onClick(position,2);
            }
        });


        holder.rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planClick.onClick(position,1);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPic;
        public MyViewHolder(View view) {
            super(view);

            ivPic = view.findViewById(R.id.img_pic);

        }
    }

    public interface ImageClick{
        void PhotonClick(int position, int diff);
    }
}
