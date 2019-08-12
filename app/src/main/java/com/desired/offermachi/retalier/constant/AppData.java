package com.desired.offermachi.retalier.constant;

import android.content.Context;
import android.widget.TextView;

import com.desired.offermachi.R;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class AppData {
    public static String url = "http://offermachi.in/api/";
    PrettyDialog prettyDialog=null;
    private void ShowNewAlert(Context context, String message) {
        if(prettyDialog!=null)
        {
            prettyDialog.dismiss();
        }
        prettyDialog = new PrettyDialog(context);
        prettyDialog.setCanceledOnTouchOutside(false);
        TextView title = (TextView) prettyDialog.findViewById(libs.mjn.prettydialog.R.id.tv_title);
        TextView tvmessage = (TextView) prettyDialog.findViewById(libs.mjn.prettydialog.R.id.tv_message);
        title.setTextSize(15);
        tvmessage.setTextSize(15);
        prettyDialog.setIconTint(R.color.colorPrimary);
        prettyDialog.setIcon(R.drawable.pdlg_icon_info);
        prettyDialog.setTitle("");
        prettyDialog.setMessage(message);
        prettyDialog.setAnimationEnabled(false);
        prettyDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        prettyDialog.addButton("Cancel", R.color.black, R.color.white, new PrettyDialogCallback() {
            @Override
            public void onClick() {
                prettyDialog.dismiss();

            }
        }).show();

        prettyDialog.addButton("Search again", R.color.black, R.color.white, new PrettyDialogCallback() {
            @Override
            public void onClick() {
                prettyDialog.dismiss();

            }
        }).show();
    }
}
