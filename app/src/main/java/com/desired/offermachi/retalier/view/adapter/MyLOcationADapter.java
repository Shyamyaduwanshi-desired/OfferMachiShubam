package com.desired.offermachi.retalier.view.adapter;

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
import android.widget.Button;
import android.widget.EditText;
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
import com.desired.offermachi.retalier.constant.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.model.UserModel;
import com.desired.offermachi.retalier.model.mylocation_model;
import com.desired.offermachi.retalier.view.activity.MyLocationEditActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyLOcationADapter extends RecyclerView.Adapter<MyLOcationADapter.ViewHolder> {
    private static final String ROOT_URL = "http://offermachi.in/api/retailer_delete_locationby_reatailer_id_and_locality_id";
    private final String idholder;
    Context context;
    ArrayList<mylocation_model> mylocation_modelslist;
    private Dialog dialog = null;

    public void simpleAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Simple Alert");
        builder.setMessage("We have a message");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public MyLOcationADapter(Context favouriteListActivity, ArrayList<mylocation_model> mylocation_modelslist) {
        this.context = favouriteListActivity;
        this.mylocation_modelslist = mylocation_modelslist;
        UserModel user = SharedPrefManagerLogin.getInstance(context).getUser();
        idholder = user.getId();
    }

    public void editLocationDialog(View v) {
        /*if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
        dialog = new Dialog((Activity) v.getContext());
        dialog.setContentView(R.layout.dialog_edit_location_detail);
        dialog.setTitle("Location");
        EditText et_loction_name = dialog.findViewById(R.id.et_loction_name);
        EditText et_address = dialog.findViewById(R.id.et_address);
        EditText et_phone_number = dialog.findViewById(R.id.et_phone_number);

        Button btnOkay = dialog.findViewById(R.id.bt_proceed);
        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });
        dialog.show();*/
    }

    @NonNull
    @Override
    public MyLOcationADapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.my_location_data_activity, viewGroup, false);
        MyLOcationADapter.ViewHolder viewHolder = new MyLOcationADapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyLOcationADapter.ViewHolder viewHolder, int i) {
        final mylocation_model mylocation_model = mylocation_modelslist.get(i);
        viewHolder.locationaddress.setText(mylocation_model.getLocation_address());
        viewHolder.locality_name.setText(mylocation_model.getLocality_name());
        viewHolder.locationpersonmobileno.setText(mylocation_model.getLocationpersonmobileno());

        viewHolder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  simpleAlert();
                Intent i = new Intent(context, MyLocationEditActivity.class);
                i.putExtra("LocationName", mylocation_model.getLocality_name());
                i.putExtra("LocationId", mylocation_model.getId());
                context.startActivity(i);
                /*    editLocationDialog(mylocation_model.getLocality_name(), mylocation_model.getId());*/
            }
        });
        viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Deletelocationdata(mylocation_model.getId(), i);
                AlertDialog.Builder builder = new AlertDialog.Builder((AppCompatActivity) v.getContext());
                builder.setTitle("Delete Alert");
                builder.setMessage("Are you sure you want to delete this location?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Deletelocationdata(mylocation_model.getId(), i);
                            }
                        });
                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mylocation_modelslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView locationaddress, locality_name, locationpersonmobileno;
        ImageView iv_edit, iv_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            locationaddress = itemView.findViewById(R.id.location_address_id);
            locality_name = itemView.findViewById(R.id.location_contact_person);
            locationpersonmobileno = itemView.findViewById(R.id.locationpersonmobile);
            iv_edit = itemView.findViewById(R.id.iv_edit);
            iv_delete = itemView.findViewById(R.id.iv_delete);

        }
    }

    private void Deletelocationdata(String locationId, int positon) {
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);

        // Use HttpURLConnection as the HTTP client
        Network network = new BasicNetwork(new HurlStack());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (null == response || response.length() == 0) {
                            Toast.makeText(context, "Something Wrong", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                                JSONObject mainJson = new JSONObject(response);
                                String status = mainJson.getString("status");
                                String message = mainJson.getString("message");
                                Log.e("Response....",message);

                                if (status.equals("200")) {
                                    Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                                    //notifyItemChanged(positon);
                                    mylocation_modelslist.remove(positon);
                                    notifyItemRemoved(positon);
                                  //  MyLOcationADapter.this.notify();
                                   /* String result = mainJson.getString("result");

                                    JSONArray jsonArray = new JSONArray(result);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jobj = jsonArray.getJSONObject(i);
                                        String id = jobj.getString("id");
                                        String user_id = jobj.getString("user_id");
                                        String locality_name = jobj.getString("locality_name");
                                        String location_address = jobj.getString("location_address");
                                        String location_contact_phone = jobj.getString("location_contact_phone");

                                        mylocation_model mylocation_model = new mylocation_model(
                                                id, locality_name, location_address, location_contact_phone
                                        );

                                        mylocation_modelslist.add(mylocation_model);

                                    }*/
                                }
                                else {
                                    Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // displayData1();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", idholder);
                params.put("locality_id", locationId);
                Log.e("params...", params.toString());
                return params;
            }
        };
        RequestQueue queue = new RequestQueue(cache, network);
        queue.start();
        queue.add(stringRequest);
    }


}
