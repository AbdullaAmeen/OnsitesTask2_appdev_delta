package com.example.eventscheduler;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class NotificationManagement extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentBack = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentBack, 0);
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(intent.getStringExtra("Name"),intent.getStringExtra("Desc"), pendingIntent);
        notificationHelper.getManager().notify(1, nb.build());
    }
}
