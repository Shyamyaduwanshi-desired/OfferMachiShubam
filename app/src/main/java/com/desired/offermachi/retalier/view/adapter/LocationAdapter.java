package com.desired.offermachi.retalier.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.model.RetailerLocation;
import com.desired.offermachi.retalier.model.RetailerLocation;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {

    private ArrayList<RetailerLocation> arMultiLoc ;
    private final Context mContext;

    private ItemClick itemClick;
    public LocationAdapter(Context context, ArrayList<RetailerLocation> data, ItemClick cateClick) {
        this.arMultiLoc = data;
        this.mContext = context;
        this.itemClick = cateClick;
        if(arMultiLoc==null){
            arMultiLoc = new ArrayList<>();
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_multiple_location_retailer_dlg, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textView = holder.textView;
        textView.setText(arMultiLoc.get(listPosition).getLocalityName());
        if(arMultiLoc.get(listPosition).isSelected())
        {
            holder.ivCheck.setImageResource(R.drawable.ic_check_box_40dp);
        }
        else
        {
            holder.ivCheck.setImageResource(R.drawable.ic_un_check_box_40dp);
        }
        holder.rlCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onLocationClick(arMultiLoc.get(listPosition));
            }
        });
    }

/*
    public void updateList(List<RetailerLocation> list ){
        arMultiLoc = (ArrayList<RetailerLocation>) list;
        notifyDataSetChanged();
    }
*/

    @Override
    public int getItemCount() {
        return arMultiLoc.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

//        CheckBox checkBox;
        TextView textView;
        ImageView ivCheck;
        RelativeLayout rlCheck;

        public MyViewHolder(View itemView) {

            super(itemView);
            this.rlCheck=itemView.findViewById(R.id.rlLocationCheckBox);
            this.ivCheck= itemView.findViewById(R.id.ivLocationCheck);
            this.textView =itemView.findViewById(R.id.tvLocationName);
        }
    }

    public interface ItemClick{
        void onLocationClick(RetailerLocation RetailerLocation);
    }
}


