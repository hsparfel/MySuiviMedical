package com.pouillos.mysuivimedical.activities.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.pouillos.mysuivimedical.R;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //int requestId = intent.getIntExtra("requestId",1);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyPrise")
                .setSmallIcon(R.drawable.icons8_main_pilule_18)
                .setContentTitle("Rappel Medicament")
                .setContentText(intent.getStringExtra("content"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(intent.getIntExtra("requestId",1),builder.build());
    }
}
