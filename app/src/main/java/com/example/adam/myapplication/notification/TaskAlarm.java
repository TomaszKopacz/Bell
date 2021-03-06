package com.example.adam.myapplication.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.data.objects.Task;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class TaskAlarm {

    private static int REQUEST_CODE = 0;

    public static void setAlarm(Context context, Task task) {
        AlarmManager manager
                = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent receiverIntent
                = getAlarmReceiverIntent(context, task);

        PendingIntent pendingIntent
                = PendingIntent.getBroadcast(context, ++REQUEST_CODE, receiverIntent, FLAG_UPDATE_CURRENT);

        long alarmTime = task.getTimestamp().getTime();

        manager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
    }

    @NonNull
    private static Intent getAlarmReceiverIntent(Context context, Task task) {
        Intent receiverIntent = new Intent(context, TaskAlarmReceiver.class);

        receiverIntent.putExtra(TaskAlarmReceiver.ACTION, TaskAlarmReceiver.NOTIFICATION);
        receiverIntent.putExtra(TaskAlarmReceiver.TITLE, task.getType());

        switch (task.getType()) {
            case Task.SCORE:
                receiverIntent.putExtra(TaskAlarmReceiver.MESSAGE,
                        context.getString(R.string.score_reminder));
                break;

            case Task.DRUG:
                receiverIntent.putExtra(TaskAlarmReceiver.MESSAGE,
                        context.getString(R.string.drug_reminder));
                break;

            case Task.DOCTOR:
                receiverIntent.putExtra(TaskAlarmReceiver.MESSAGE,
                        context.getString(R.string.examination_reminder));
                break;
        }

        return receiverIntent;
    }
}
