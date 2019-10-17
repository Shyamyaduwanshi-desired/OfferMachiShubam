package com.desired.offermachi.customer.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.model.CityBean;

import java.util.ArrayList;
import java.util.List;

public class MultipleLocAdapter extends RecyclerView.Adapter<MultipleLocAdapter.MyViewHolder> {

    private ArrayList<CityBean> arMultiLoc;
    private final Context mContext;

    private MultipleLocClick itemClick;
    private boolean isSelectedAll;
    private boolean clickItem;

    public MultipleLocAdapter(Context context, ArrayList<CityBean> data, MultipleLocClick cateClick) {
        this.arMultiLoc = data;
        this.mContext = context;
        this.itemClick = cateClick;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_multiple_location_selection_dlg, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textView = holder.textView;
        textView.setText(arMultiLoc.get(listPosition).getCity_name());

        if(!clickItem){
            if (!isSelectedAll) {
                holder.ivCheck.setImageResource(R.drawable.ic_un_check_box_40dp);
                for (int i = 0; i < arMultiLoc.size(); i++) {
                    arMultiLoc.get(i).setSelected(false);

                    //  arMultiLoc.get(i).setSelected(false);
                }
                // holder.ivCheck.setChecked(false);
            } else {
                holder.ivCheck.setImageResource(R.drawable.ic_check_box_40dp);
                for (int i = 0; i < arMultiLoc.size(); i++) {
                    //   if (arMultiLoc.get(i).isSelected()) {
                    arMultiLoc.get(i).setSelected(true);
                }

            }
        }
       //holder.checkbox.setChecked(true);
        if (arMultiLoc.get(listPosition).isSelected()) {
            holder.ivCheck.setImageResource(R.drawable.ic_check_box_40dp);
        } else {
            holder.ivCheck.setImageResource(R.drawable.ic_un_check_box_40dp);
        }

        holder.rlCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  isSelectedAll=false;
                clickItem=true;
                itemClick.onMultipleLocClick(arMultiLoc.get(listPosition));
            }
        });
    }


    public void updateList(List<CityBean> list) {
        arMultiLoc = (ArrayList<CityBean>) list;
        notifyDataSetChanged();
    }

    public void selectAll() {
        isSelectedAll = true;
        clickItem=false;
        notifyDataSetChanged();
    }

    public void unselectall() {
        isSelectedAll = false;
        clickItem=false;

        notifyDataSetChanged();
    }

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
            this.rlCheck = itemView.findViewById(R.id.rl_check_box);
            this.ivCheck = itemView.findViewById(R.id.iv_check);
            this.textView = itemView.findViewById(R.id.tv_loc_nm);
        }
    }

    public interface MultipleLocClick {
        void onMultipleLocClick(CityBean cityBean);
    }
}


