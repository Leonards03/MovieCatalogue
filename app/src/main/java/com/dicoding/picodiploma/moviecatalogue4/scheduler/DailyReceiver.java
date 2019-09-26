package com.dicoding.picodiploma.moviecatalogue4.scheduler;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class DailyReceiver extends BroadcastReceiver {
    private static final String EXTRA_TITLE = "extra_title";
    private static final String EXTRA_CONTENT = "extra_content";
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra(EXTRA_TITLE);
        String content = intent.getStringExtra(EXTRA_CONTENT);
        showMissingNotification(context,title,content,0);
    }

    private void showMissingNotification(Context context, String title, String message, int notifId){
        String CHANNEL_ID = "Channel_daily";
        String CHANNEL_NAME = "Daily Reminder";

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message);

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);

            builder.setChannelId(CHANNEL_ID);

            if(notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if(notificationManager != null){
            notificationManager.notify(notifId,notification);
        }
    }
}
