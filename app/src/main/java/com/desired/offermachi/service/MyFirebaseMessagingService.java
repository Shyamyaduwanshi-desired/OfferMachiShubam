package com.desired.offermachi.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.view.activity.NotificationActivity;
import com.desired.offermachi.retalier.view.activity.RetalierNotificationActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private static final String CHANNEL_ID="com.desired.offermachi.fcm";
    private static final String CHANNEL_NAME="com.desired.offermachi.fcm_name";
    NotificationManager manager;
    String message,title,type;
    String notificationstatus="0";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, "From:" + remoteMessage.getFrom());

        if (remoteMessage == null) {
            Log.e(TAG, "Body: null");
            return;

        }
        Getdata(remoteMessage);
      /*  title=remoteMessage.getNotification().getTitle();
        message=remoteMessage.getNotification().getBody();
        showNotification(message,title);
        Log.e(TAG, "onMessageReceived: "+message );
        Log.e(TAG, "onMessageReceived: "+title );*/

    }
    private void Getdata(RemoteMessage remoteMessage) {

      /*  if (remoteMessage.getData().size() > 0) {*/
            //title = remoteMessage.getData().get("title");
            //message = remoteMessage.getData().get("message");
        title=remoteMessage.getData().get("title");
        Log.e("notification", "remoteMessage=="+remoteMessage.getData() );
        message=remoteMessage.getData().get("body");
            type = remoteMessage.getData().get("type");
        Intent intent = null;
            if (type.equals("customer")){
                User user = UserSharedPrefManager.getInstance(getApplicationContext()).getCustomer();
                notificationstatus=user.getNotificationsound();
                intent = new Intent(this, NotificationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }else if (type.equals("retailer")){
                intent = new Intent(this, RetalierNotificationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
        if (notificationstatus.equals("0")) {
            showNotification(title, message, intent);
        }

    }

    private void showNotification(String title,String message, Intent intent) {

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(R.drawable.bell)
                .setContentIntent(pendingIntent);
            playNotificationSound();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel();
        manager.notify(0,builder.build());

    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            //channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
          //  manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
    }

    public void playNotificationSound() {
        try {
            // Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + this.getPackageName() + "/raw/notification.mp3");

            Uri alarmSound  = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(this, alarmSound);
            r.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
   /* SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
    final String token=sharedPreferences.getString(getString(R.string.FCM_TOKEN),"");*/