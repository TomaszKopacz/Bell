package com.example.adam.myapplication.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;

import java.util.Calendar;

public class DatetimePicker {

    public static void showDatePicker(Context context, DatePickerDialog.OnDateSetListener listener) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(context, listener, year, month, day);
        dialog.show();
    }

    public static void showTimePicker(Context context, TimePickerDialog.OnTimeSetListener listener) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(context, listener, hour, minute, true);
        dialog.show();
    }
}
