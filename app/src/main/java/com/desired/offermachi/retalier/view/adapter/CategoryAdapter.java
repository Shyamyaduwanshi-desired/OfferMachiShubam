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
import com.desired.offermachi.retalier.model.CategoryModel;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<CategoryModel> categoryModelArrayList;

    public CategoryAdapter(Context context,ArrayList<CategoryModel> categoryModelArrayList) {
        this.context = context;
        this.categoryModelArrayList=categoryModelArrayList;

    }

    @Override
    public int getCount() {
        return categoryModelArrayList.size();
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

        holder.Offerid.setText(categoryModelArrayList.get(position).getId());
        holder.Offertype.setText(categoryModelArrayList.get(position).getCategoryname());
        return convertView;
    }
}
