package com.desired.offermachi.retalier.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.model.CityBean;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter1 extends ArrayAdapter<CityBean> {
    private Context mContext;
    private ArrayList<CityBean> listState;
    private LocationAdapter1 myAdapter;
    private boolean isFromView = false;

    public LocationAdapter1(Context context, int resource, List<CityBean> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<CityBean>) objects;
        this.myAdapter = this;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_item, null);
            holder = new ViewHolder();
            holder.tvLocNm = (TextView) convertView
                    .findViewById(R.id.tv_loc_nm);
            holder.tvLocId = (TextView) convertView
                    .findViewById(R.id.tv_loc_id);
            holder.tvOkay = (TextView) convertView
                    .findViewById(R.id.tv_okay);
            holder.mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.tvLocId.setText(listState.get(position).getId());
        holder.tvLocNm.setText(listState.get(position).getCity_name());

        // To check weather checked event fire from getview() or user input
        isFromView = true;
        holder.mCheckBox.setChecked(listState.get(position).isSelected());
        isFromView = false;

        if ((position == 0)) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
            holder.tvOkay.setVisibility(View.VISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
            holder.tvOkay.setVisibility(View.GONE);
        }
        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();

                if (!isFromView) {
                    listState.get(position).setSelected(isChecked);
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView tvLocNm,tvLocId,tvOkay;
        private CheckBox mCheckBox;
    }
}
