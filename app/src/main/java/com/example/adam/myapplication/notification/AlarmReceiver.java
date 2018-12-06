package com.example.adam.myapplication.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.mainwindow.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String ACTION = "action";
    public static final String NOTIFICATION = "notification";

    public static final String TITLE = "title";
    public static final String MESSAGE = "message";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getStringExtra(ACTION) != null && intent.getStringExtra(ACTION).equals(NOTIFICATION)){

            String title = intent.getStringExtra(TITLE);
            String message = intent.getStringExtra(MESSAGE);

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.bell)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            Intent destinationIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    destinationIntent, PendingIntent.FLAG_ONE_SHOT);

            builder.setLights(context.getResources().getColor(R.color.colorPrimary), 500, 500);
            builder.setVibrate(new long[] {500, 500});
            builder.setContentIntent(pendingIntent);

            manager.notify(1, builder.build());
        }
    }
}
