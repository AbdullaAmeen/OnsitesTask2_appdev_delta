package com.example.eventscheduler;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class NotificationManagement extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentBack = new Intent(context, EventActivity.class);
        String name = intent.getStringExtra("Name");
        String desc = intent.getStringExtra("Desc");
        int id = intent.getIntExtra("Id",-1);

        if(intent.getBooleanExtra("fifteen",false)) {
            Log.v("afterTImeTest","Aftertimetest2");
            desc=desc.concat("- Event in 15 minutes");
        }

        intentBack.putExtra("Cancel", true);
        intentBack.putExtra("Id",id);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,uniqueInt, intentBack, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(name,desc, pendingIntent);
        notificationHelper.getManager().notify(1, nb.build());
    }
}
