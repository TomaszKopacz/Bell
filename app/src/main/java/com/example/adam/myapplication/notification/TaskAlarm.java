package com.example.adam.myapplication.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.data.Task;

public class TaskAlarm {

    public static void setAlarm(Context context, Task task){
        AlarmManager manager
                = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent receiverIntent
                = getAlarmReceiverIntent(context, task);

        PendingIntent pendingIntent
                = PendingIntent.getBroadcast(context, 0, receiverIntent, 0);

        long alarmTime = task.getTimestamp().getTime();

        manager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
    }

    @NonNull
    private static Intent getAlarmReceiverIntent(Context context, Task task) {
        Intent receiverIntent = new Intent(context, TaskAlarmReceiver.class);

        receiverIntent.putExtra(TaskAlarmReceiver.ACTION, TaskAlarmReceiver.NOTIFICATION);
        receiverIntent.putExtra(TaskAlarmReceiver.TITLE, task.getType());

        switch (task.getType()){
            case Task.MEASUREMENT_PRESSURE:
                receiverIntent.putExtra(TaskAlarmReceiver.MESSAGE,
                        context.getString(R.string.pressure_reminder));
                break;

            case Task.MEASUREMENT_TEMPERATURE:
                receiverIntent.putExtra(TaskAlarmReceiver.MESSAGE,
                        context.getString(R.string.temperature_reminder));
                break;

            case Task.DRUG:
                receiverIntent.putExtra(TaskAlarmReceiver.MESSAGE,
                        context.getString(R.string.drug_reminder));
                break;

            case Task.EXAMINATION:
                receiverIntent.putExtra(TaskAlarmReceiver.MESSAGE,
                        context.getString(R.string.examination_reminder));
                break;
        }

        return receiverIntent;
    }
}
