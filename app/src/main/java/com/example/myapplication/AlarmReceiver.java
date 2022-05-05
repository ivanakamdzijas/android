package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import com.example.myapplication.ui.activities.CameraActivity;

public class AlarmReceiver extends BroadcastReceiver {

    //NotificationCompat.Builder constructor requires that you provide a channel ID.
    // This is required for compatibility with Android 8.0 (API level 26) and higher,
    // but is ignored by older versions.
    private static final String CHANNEL_ID = "CHANNEL_SAMPLE";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get id & message
        //notificationId is actually meal id
        int notificationId = intent.getIntExtra("notificationId", 0);
        String message = intent.getStringExtra("message");
        Bundle bundle = intent.getBundleExtra("bundle");
        // Call CameraActivity when notification is tapped.
        Intent mainIntent = new Intent(context, CameraActivity.class);
        mainIntent.putExtra("bundle",bundle);

        //create a basic intent to open an activity when the user taps the notification
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent,  PendingIntent.FLAG_CANCEL_CURRENT);
        //PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);
        // NotificationManager
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // For API 26 and above
            CharSequence channelName = "My Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            notificationManager.createNotificationChannel(channel);
        }

        // Prepare Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Enjoy your meal!")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setContentIntent(contentIntent)
                //prioritet odredjuje koliko notifikacija treba da bude nametljiva za Android 7.1 i starije
                //za novije se podesava CHANNEL IMPORTANCE
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // To make the notification appear
        notificationManager.notify(notificationId, builder.build());
    }
}