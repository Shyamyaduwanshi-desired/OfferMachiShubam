package com.desired.offermachi.customer.view.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.desired.offermachi.R;
import com.desired.offermachi.customer.model.muliplelocationshowmodel;
import com.desired.offermachi.customer.view.activity.OfferPageMultipleLocation;
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.model.mylocation_model;
import com.desired.offermachi.retalier.view.activity.MyLocationEditActivity;
import com.desired.offermachi.retalier.view.activity.PickLocation;
import com.desired.offermachi.retalier.view.adapter.MyLOcationADapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MultipleshoelocationAdapter extends RecyclerView.Adapter<MultipleshoelocationAdapter.ViewHolder> {
    Context context;
    ArrayList<muliplelocationshowmodel> mylocation_modelslist;
    private Dialog dialog = null;


    public MultipleshoelocationAdapter(Context favouriteListActivity, ArrayList<muliplelocationshowmodel> mylocation_modelslist) {
        this.context = favouriteListActivity;
        this.mylocation_modelslist = mylocation_modelslist;
    }

    @NonNull
    @Override
    public MultipleshoelocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.multiple_location_show_activity, viewGroup, false);
        MultipleshoelocationAdapter.ViewHolder viewHolder = new MultipleshoelocationAdapter.ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final MultipleshoelocationAdapter.ViewHolder viewHolder, int i) {
        final muliplelocationshowmodel mylocation_model = mylocation_modelslist.get(i);
        viewHolder.locationaddress.setText(mylocation_model.getLocation_address());
        viewHolder.locality_name.setText(mylocation_model.getLocality_name());
        viewHolder.locality_mobile.setText(mylocation_model.getLocation_mobile());
        viewHolder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PickLocation.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });

    }
    @Override
    public int getItemCount() {
        return mylocation_modelslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView locationaddress, locality_name,locality_mobile;
        ImageView location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            locationaddress = itemView.findViewById(R.id.location_address_id);
            locality_name = itemView.findViewById(R.id.location_contact_person);
            locality_mobile=itemView.findViewById(R.id.locationpersonmobile);
            location=itemView.findViewById(R.id.location_id);



        }
    }

}
