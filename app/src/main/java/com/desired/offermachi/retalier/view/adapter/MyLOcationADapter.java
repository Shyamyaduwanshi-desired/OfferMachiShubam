package com.desired.offermachi.retalier.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.desired.offermachi.R;
import com.desired.offermachi.retalier.model.FAQ;
import com.desired.offermachi.retalier.model.mylocation_model;

import java.util.ArrayList;

public class MyLOcationADapter extends RecyclerView.Adapter<MyLOcationADapter.ViewHolder> {
    Context context;
    ArrayList<mylocation_model> mylocation_modelslist;

    public MyLOcationADapter(Context favouriteListActivity, ArrayList<mylocation_model> mylocation_modelslist) {
        this.context = favouriteListActivity;
        this.mylocation_modelslist = mylocation_modelslist;
    }

    @NonNull
    @Override
    public MyLOcationADapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.my_location_data_activity,viewGroup,false);
        MyLOcationADapter.ViewHolder viewHolder = new MyLOcationADapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyLOcationADapter.ViewHolder viewHolder, int i) {
        final mylocation_model mylocation_model = mylocation_modelslist.get(i);
        viewHolder.locationaddress.setText(mylocation_model.getLocation_address());
        viewHolder. locationcontactperson.setText(Html.fromHtml(mylocation_model.getLocation_contact_persion()));
        viewHolder.locationpersonmobileno.setText(mylocation_model.getLocationpersonmobileno());

    }
    @Override
    public int getItemCount() {
        return mylocation_modelslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView locationaddress, locationcontactperson,locationpersonmobileno;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            locationaddress=itemView.findViewById(R.id.location_address_id);
            locationcontactperson=itemView.findViewById(R.id.location_contact_person);
            locationpersonmobileno=itemView.findViewById(R.id.locationpersonmobile);

        }
    }
}
