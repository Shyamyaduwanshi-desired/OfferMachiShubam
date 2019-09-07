package com.desired.offermachi.retalier.view.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.desired.offermachi.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class RetalierInviteFriendActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageViewback;
    ImageView facebook,Watsup,Twitter;
    ImageView imgNotiBell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retalier_invite_activity);
        imgNotiBell=findViewById(R.id.imgNotiBell);
        imgNotiBell.setOnClickListener(this);
        imageViewback=findViewById(R.id.imageback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Watsup=findViewById(R.id.watsup_id);
        Watsup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = getApplicationContext().getPackageManager();
                try {
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String text = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                        text = "Get Regular updates on the best deals, cashback offers, and discount coupons across retail stores in your loaction. Get it for free at\n"+"https://play.google.com/store/apps/details?id=com.desired.offermachi";
                    }
                    PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    waIntent.setPackage("com.whatsapp");
                    waIntent.putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(Intent.createChooser(waIntent, "Share with"));

                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(RetalierInviteFriendActivity.this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                    Intent i = new Intent();
                    i.putExtra(Intent.EXTRA_TEXT, "sharelink");
                    i.setAction(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://www.whatsapp.com/" + urlEncode("sharelink")));
                    startActivity(i);

                }
            }
        });
        facebook=findViewById(R.id.facebook_id);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = getApplicationContext().getPackageManager();
                try {
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String url = "Get Regular updates on the best deals, cashback offers, and discount coupons across retail stores in your loaction. Get it for free at\\n\"+\"https://play.google.com/store/apps/details?id=com.desired.offermachi";
                    PackageInfo info = pm.getPackageInfo("com.facebook.katana", PackageManager.GET_META_DATA);
                    waIntent.setPackage("com.facebook.katana");
                    waIntent.putExtra(Intent.EXTRA_TEXT, url);
                    startActivity(Intent.createChooser(waIntent, "Share with"));
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(RetalierInviteFriendActivity.this, "Facebook not installed",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    i.putExtra(Intent.EXTRA_TEXT, "sharelink");
                    i.setAction(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://www.facebook.com/" + urlEncode("sharelink")));
                    startActivity(i);
                }
            }
        });
        Twitter=findViewById(R.id.twitter_id);
        Twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTwitter("sharelink");
            }
        });
    }
    private void shareTwitter(String message) {
        Intent tweetIntent = new Intent(Intent.ACTION_SEND);
        tweetIntent.putExtra(Intent.EXTRA_TEXT, "Get Regular updates on the best deals, cashback offers, and discount coupons across retail stores in your loaction. Get it for free at\\n\"+\"https://play.google.com/store/apps/details?id=com.desired.offermachi");
        tweetIntent.setType("text/plain");
        PackageManager packManager = getApplicationContext().getPackageManager();
        List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);
        boolean resolved = false;
        for (ResolveInfo resolveInfo : resolvedInfoList) {
            if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
                tweetIntent.setClassName(
                        resolveInfo.activityInfo.packageName,
                        resolveInfo.activityInfo.name);
                resolved = true;
                break;
            }
        }
        if (resolved) {
            startActivity(tweetIntent);
        } else {
            Intent i = new Intent();
            i.putExtra(Intent.EXTRA_TEXT, message);
            i.setAction(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://twitter.com/intent/tweet?text=" + urlEncode(message)));
            startActivity(i);
            Toast.makeText(RetalierInviteFriendActivity.this, "Twitter app isn't found", Toast.LENGTH_LONG).show();
        }
    }

    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(RetalierInviteFriendActivity.this, ""+e, Toast.LENGTH_SHORT).show();
            return "";
        }
    }

    @Override
    public void onClick(View v) {
        if (v==imgNotiBell){
            startActivity(new Intent(getApplicationContext(), RetalierNotificationActivity.class));
        }
    }
}
