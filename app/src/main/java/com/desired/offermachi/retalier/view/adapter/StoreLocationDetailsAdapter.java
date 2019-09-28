package com.desired.offermachi.retalier.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.model.AddStoreLocBean;


import java.util.List;

public class StoreLocationDetailsAdapter extends RecyclerView.Adapter<StoreLocationDetailsAdapter.MyViewHolder> {
    private List<AddStoreLocBean> list;
    private Context context;
    private LocDetailClick planClick;

    public StoreLocationDetailsAdapter(Context context, List<AddStoreLocBean> list, LocDetailClick planClick) {
        this.context = context;
        this.list = list;
        this.planClick = planClick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_store_location_detail, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.etLocNm.setText(list.get(position).getsLocNm());

      /*  holder.etLocNm.setKeyListener(null);
        holder.etLocNm.setFocusable(false);
        holder.etLocNm.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        holder.etLocNm.setClickable(true);*/


        if(position==0)
        {
            holder.rlAdd.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.rlAdd.setVisibility(View.GONE);
        }

//        holder.etLocNm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                planClick.onClick(position,2);
//            }
//        });

        holder.rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                planClick.onClick(position,1);
            }
        });
        holder.etLocNm.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }



            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {
                list.get(position).setsLocNm(s.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView tvLocNm;
        EditText etLocNm;
        LinearLayout lyMain;
        RelativeLayout rlAdd;
        public MyViewHolder(View view) {
            super(view);
//            tvLocNm = view.findViewById(R.id.tv_loc_nm);
            etLocNm = view.findViewById(R.id.et_loction_name);
            lyMain = view.findViewById(R.id.ly_main);
            rlAdd = view.findViewById(R.id.rl_add_location);
        }
    }

    public interface LocDetailClick{
        void onClick(int position, int diff);
    }
}
