package com.desired.offermachi.retalier.view.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;

import com.desired.offermachi.R;
import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

public class QRcodeScannerActivity extends AppCompatActivity implements BarcodeRetriever {
    private BarcodeCapture barcodeCapture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scanner);
        initView();
    }
    private void initView() {
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

}
