package com.desired.offermachi.retalier.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.model.OfferTypeModel;
import com.desired.offermachi.retalier.model.ViewOfferModel;

import java.util.ArrayList;

public class OfferTypeAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<OfferTypeModel> offerTypeModelArrayList;

    public OfferTypeAdapter(Context context,ArrayList<OfferTypeModel> offerTypeModelArrayList) {
        this.context = context;
        this.offerTypeModelArrayList=offerTypeModelArrayList;

    }

    @Override
    public int getCount() {
        return offerTypeModelArrayList.size();
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

        holder.Offerid.setText(offerTypeModelArrayList.get(position).getId());
        holder.Offertype.setText(offerTypeModelArrayList.get(position).getOffertype());
        return convertView;
    }
}
