package com.example.nhohidrata;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "canal_alarme")
                        .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                        .setContentTitle("Hora de beber água 💧")
                        .setContentText("Mantém-te hidratado!")
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager manager =
                (NotificationManager)
                        context.getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(1, builder.build());
    }
}

