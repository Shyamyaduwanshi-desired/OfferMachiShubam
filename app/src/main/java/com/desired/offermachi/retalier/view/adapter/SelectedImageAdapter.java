package com.desired.offermachi.retalier.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;


import com.desired.offermachi.R;
import com.desired.offermachi.retalier.model.ImageBean;
import com.desired.offermachi.retalier.view.fragment.GetItemView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SelectedImageAdapter extends RecyclerView.Adapter<SelectedImageAdapter.MyViewHolder>  {
    private List<ImageBean> list;
    private Context context;
    private ImageClick planClick;


    public SelectedImageAdapter(Context context, List<ImageBean> list, ImageClick planClick ) {
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


        Picasso.get().load(new File(list.get(position).getImagePath())).placeholder(R.drawable.shortlogo).into(holder.ivPic);

        holder.img_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the item on remove/button click
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,list.size());
                // Show the removed item label`enter code here`

            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPic ,img_cross;
        public MyViewHolder(View view) {
            super(view);

            ivPic = view.findViewById(R.id.img_pic);
            img_cross=view.findViewById(R.id.ccancel_action_id);
//            img_cross = view.findViewById(R.id.cancel_action_id);

        }
    }
    public interface ImageClick{
        void PhotonClick(int position, int diff);
    }
}