package com.desired.offermachi.retalier.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.R;
import com.desired.offermachi.retalier.constant.AppData;
import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

public class QRcodeScannerActivity extends AppCompatActivity implements BarcodeRetriever {
    private BarcodeCapture barcodeCapture;
    String userid;
    String status="2";
    String offer_id,offer_title, offer_category_name, offer_type_name, offer_brand_name, offer_value,coupon_code,start_date,end_date,offer_image,coustatus,description,alltime,offer_category,offer_type,sub_category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scanner);
        initView();
    }
    private void initView() {
        Intent intent=getIntent();
        userid=intent.getStringExtra("userid");
        barcodeCapture = (BarcodeCapture) getSupportFragmentManager().findFragmentById(R.id.barcode);
        barcodeCapture.setRetrieval(this);
        barcodeCapture.setShowDrawRect(true);
    }
    @Override
    protected void onDestroy() {
        barcodeCapture.stopScanning();
        super.onDestroy();
    }
    @Override
    public void onRetrieved(final Barcode barcode) {
        barcodeCapture.stopScanning();
        //Log.d(TAG, "Barcode read: " + barcode.displayValue);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //String[] arr = barcode.displayValue.split("_");
                Log.e("values", "array="+ barcode.displayValue );
                String baroode=barcode.displayValue;
                StringTokenizer time = new StringTokenizer(baroode, ",");
                offer_id=time.nextToken();
                offer_title=time.nextToken();
                offer_category_name=time.nextToken();
                offer_type_name=time.nextToken();
                offer_brand_name=time.nextToken();
                offer_value=time.nextToken();
                coupon_code=time.nextToken();
                start_date=time.nextToken();
                end_date= time.nextToken();
                offer_image=time.nextToken();
                coustatus=time.nextToken();
                description=time.nextToken();
                alltime=time.nextToken();
                offer_category=time.nextToken();
                offer_type=time.nextToken();
                sub_category=time.nextToken();
                diloge(offer_title,offer_category_name,offer_type_name,offer_brand_name,offer_value,coupon_code,start_date,
                        end_date,offer_image,description);

              //  transferMoneyFriend.usingQR(arr[1], getIntent().getStringExtra("amount"));
            }
        });
    }
    @Override
    public void onRetrievedMultiple(final Barcode barcode, final List<BarcodeGraphic> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String message = "Code selected : " + barcode.displayValue + "\n\nother " +
                        "codes in frame include : \n";
                Log.e("values", "array===="+ list.size() );
                for (int index = 0; index < list.size(); index++) {
                    Barcode barcode = list.get(index).getBarcode();
                    message += (index + 1) + ". " + barcode.displayValue + "\n";
            }

                AlertDialog.Builder builder = new AlertDialog.Builder(QRcodeScannerActivity.this)
                        .setTitle("code retrieved")
                        .setMessage(message);
                builder.show();
            }
        });
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onRetrievedFailed(String s) {

    }

    @Override
    public void onPermissionRequestDenied() {
        new AlertDialog.Builder(QRcodeScannerActivity.this)
                .setTitle("Alert")
                .setMessage("Please accept permission to scan QR code.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
    private void diloge(String offer_title, String offer_category_name, String offer_type_name, String offer_brand_name, String offer_value, String coupon_code, String start_date, String end_date, String offer_image, String description){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.scandata_activity);
        dialog.setTitle("Custom Dialog");
        Button btnverify=dialog.findViewById(R.id.btnverify);
        ImageView offeriamge=dialog.findViewById(R.id.offerimage);
        TextView offertitle=dialog.findViewById(R.id.offertitle);
        offertitle.setText(offer_title);
        TextView catname=dialog.findViewById(R.id.catname);
        catname.setText(offer_category_name);
        TextView offertype=dialog.findViewById(R.id.offertype);
        offertype.setText(offer_type_name+" Off "+offer_value);
        TextView brandname=dialog.findViewById(R.id.brandname);
        brandname.setText(offer_brand_name);
        TextView couponcode=dialog.findViewById(R.id.couponcode);
        couponcode.setText(coupon_code);
        TextView offerdate=dialog.findViewById(R.id.offerdate);
        offerdate.setText(start_date+" - "+end_date);
        TextView offerdetail=dialog.findViewById(R.id.offerdetail);
        offerdetail.setText(description);
        if(offer_image.equals("")){
        }else{
            Picasso.get().load(offer_image).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_broken).into(offeriamge);
        }
        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                getcoupon(userid,offer_id,status);

            }
        });
        dialog.show();

    }
    private void getcoupon(final String id, final String offerid, final String getcoupon){
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "customer_get_coupon_code_data", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if(status == 200){
                        Log.e("home", "response========== "+reader.getString("message") );
                        startActivity(new Intent(getApplicationContext(),RetalierDashboard.class));
                        finish();

                    }else if(status == 404){
                        Toast.makeText(getApplicationContext(), ""+reader.getString("message"), Toast.LENGTH_SHORT).show();

                        Log.e("home", "response========== "+reader.getString("message") );
                        //getCoupon.couponerror(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Something went wrong. Please try after some time.", Toast.LENGTH_SHORT).show();
                    // getCoupon.couponfail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Server Error.\\n Please try after some time.", Toast.LENGTH_SHORT).show();
                // getCoupon.couponfail("Server Error.\n Please try after some time.");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", id);
                params.put("offer_id",offerid);
                params.put("active", getcoupon);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(postRequest);
    }

}

