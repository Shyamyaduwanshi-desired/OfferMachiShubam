package com.desired.offermachi.retalier.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.model.BrandModel;
import com.desired.offermachi.retalier.model.OfferTypeModel;

import java.util.ArrayList;

public class BrandAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<BrandModel> brandModelArrayList;

    public BrandAdapter(Context context,ArrayList<BrandModel> brandModelArrayList) {
        this.context = context;
        this.brandModelArrayList=brandModelArrayList;

    }

    @Override
    public int getCount() {
        return brandModelArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView Offerid,Offertype;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
     ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.offer_type_list, null);
            holder = new ViewHolder();
            holder.Offerid = (TextView) convertView.findViewById(R.id.offerid);
            holder.Offertype = (TextView) convertView.findViewById(R.id.offertype);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // RowItem rowItem = (RowItem) getItem(position);

        holder.Offerid.setText(brandModelArrayList.get(position).getId());
        holder.Offertype.setText(brandModelArrayList.get(position).getBrandname());
        return convertView;
    }
}
