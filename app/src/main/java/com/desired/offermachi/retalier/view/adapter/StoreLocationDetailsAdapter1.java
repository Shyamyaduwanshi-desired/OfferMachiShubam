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

public class StoreLocationDetailsAdapter1 extends RecyclerView.Adapter<StoreLocationDetailsAdapter1.MyViewHolder> {
    private List<AddStoreLocBean> list;
    private Context context;
    private LocDetailClick planClick;

    public StoreLocationDetailsAdapter1(Context context, List<AddStoreLocBean> list, LocDetailClick planClick) {
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

        holder.etLocNm.setKeyListener(null);
        holder.etLocNm.setFocusable(false);
        holder.etLocNm.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        holder.etLocNm.setClickable(true);

//        if(position==0)
//        {
//            holder.rlAdd.setVisibility(View.GONE);
//        }
//        else
//        {
//            holder.rlAdd.setVisibility(View.GONE);
//        }

        holder.etPerNm.setText(list.get(position).getPersonNm());
        holder.etAddress.setText(list.get(position).getAddress());
        holder.etPhoneNumber.setText(list.get(position).getPhoneNumber());

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

        holder.etPerNm.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }



            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

                list.get(position).setPersonNm(s.toString());
//                notifyDataSetChanged();
            }
        });
        holder.etAddress.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }



            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

                list.get(position).setAddress(s.toString());
//                notifyDataSetChanged();
            }
        });

        holder.etPhoneNumber.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }



            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

                list.get(position).setPhoneNumber(s.toString());
//                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView tvLocNm;
        EditText etLocNm,etPerNm,etAddress,etPhoneNumber;
        LinearLayout lyMain;
        RelativeLayout rlAdd;
        public MyViewHolder(View view) {
            super(view);
//            tvLocNm = view.findViewById(R.id.tv_loc_nm);
            etLocNm = view.findViewById(R.id.et_loction_name);
            etPerNm = view.findViewById(R.id.et_contact_person);
            etAddress = view.findViewById(R.id.et_address);
            etPhoneNumber = view.findViewById(R.id.et_phone_number);
            lyMain = view.findViewById(R.id.ly_main);
            rlAdd = view.findViewById(R.id.rl_add_location);
        }
    }

    public interface LocDetailClick{
        void onClick(int position, int diff);
    }
}
