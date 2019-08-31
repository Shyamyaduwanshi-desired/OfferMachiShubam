package com.desired.offermachi.customer.view.fragment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.view.activity.DashBoardActivity;
import com.desired.offermachi.retalier.view.activity.RetalierInviteFriendActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


public class InviteFriendFragment extends Fragment {
    View view;
    ImageView facebook,Watsup,Twitter;

    public InviteFriendFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.invite_friend_activity, container, false);
        ((DashBoardActivity)getActivity()).setToolTittle("Invite a Friend",2);
        Watsup=view.findViewById(R.id.watsup_id);
        Watsup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = getContext().getPackageManager();
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
                    Toast.makeText(getContext(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                    Intent i = new Intent();
                    i.putExtra(Intent.EXTRA_TEXT, "sharelink");
                    i.setAction(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://www.whatsapp.com/" + urlEncode("sharelink")));
                    startActivity(i);

                }
            }
        });
        facebook=view.findViewById(R.id.facebook_id);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = getContext().getPackageManager();
                try {
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String url = "Get Regular updates on the best deals, cashback offers, and discount coupons across retail stores in your loaction. Get it for free at\\n\"+\"https://play.google.com/store/apps/details?id=com.desired.offermachi";
                    PackageInfo info = pm.getPackageInfo("com.facebook.katana", PackageManager.GET_META_DATA);
                    waIntent.setPackage("com.facebook.katana");
                    waIntent.putExtra(Intent.EXTRA_TEXT, url);
                    startActivity(Intent.createChooser(waIntent, "Share with"));
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(getContext(), "Facebook not installed",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    i.putExtra(Intent.EXTRA_TEXT, "sharelink");
                    i.setAction(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://www.facebook.com/" + urlEncode("sharelink")));
                    startActivity(i);
                }
            }
        });
        Twitter=view.findViewById(R.id.twitter_id);
        Twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTwitter("sharelink");
            }
        });
        return  view;
    }
    private void shareTwitter(String message) {
        Intent tweetIntent = new Intent(Intent.ACTION_SEND);
        tweetIntent.putExtra(Intent.EXTRA_TEXT, "Get Regular updates on the best deals, cashback offers, and discount coupons across retail stores in your loaction. Get it for free at\\n\"+\"https://play.google.com/store/apps/details?id=com.desired.offermachi");
        tweetIntent.setType("text/plain");
        PackageManager packManager = getContext().getPackageManager();
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
            Toast.makeText(getContext(), "Twitter app isn't found", Toast.LENGTH_LONG).show();
        }
    }

    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(getContext(), ""+e, Toast.LENGTH_SHORT).show();
            return "";
        }
    }
}

