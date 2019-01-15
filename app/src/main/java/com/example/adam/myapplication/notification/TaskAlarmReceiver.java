package com.example.adam.myapplication.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.mainwindow.MainActivity;

public class TaskAlarmReceiver extends BroadcastReceiver {

    public static final String ACTION = "action";
    public static final String NOTIFICATION = "notification";

    public static final String TITLE = "title";
    public static final String MESSAGE = "message";

    private static int NOTIFICATION_ID = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getStringExtra(ACTION);

        if (action != null && action.equals(NOTIFICATION))
            sendNotification(context, intent);
    }

    private void sendNotification(Context context, Intent intent) {
        String title = intent.getStringExtra(TITLE);
        String message = intent.getStringExtra(MESSAGE);

        NotificationManager manager
                = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification
                = createNotification(context, title, message);

        manager.notify(NOTIFICATION_ID, notification);
    }

    @NonNull
    private Notification createNotification(Context context, String title, String message) {
        Intent destinationIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                destinationIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.bell)
                .setContentTitle(title)
                .setContentText(message)
                .setLights(context.getResources().getColor(R.color.colorPrimary), 500, 500)
                .setVibrate(new long[]{200, 200, 200, 200})
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        return builder.build();
    }
}
