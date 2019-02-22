package com.example.adam.myapplication.utils;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Formatter {

    private static final String TIMESTAMP_FORMAT = "dd-MM-yyyy HH:mm";
    private static final SimpleDateFormat formatter = new SimpleDateFormat(TIMESTAMP_FORMAT, Locale.ENGLISH);

    public static String getDateString(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return getDateString(day, month, year);
    }

    public static String getDateString(int day, int month, int year) {
        String yearString = getYearString(year);
        String monthString = getMonthString(month);
        String dayString = getDayString(day);

        return dayString + "-" + monthString + "-" + yearString;
    }

    public static String getTimeString(Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return getTimeString(hour, minute);
    }

    public static String getTimeString(int hour, int minute) {
        String hourString = getHourString(hour);
        String minuteString = getMinuteString(minute);

        return hourString + ":" + minuteString;
    }

    public static Date getTimestamp(String date, String time) throws ParseException {
        return formatter.parse(date + " " + time);
    }

    @NonNull
    private static String getDayString(int day) {
        return (day < 10) ?
                "0" + day : Integer.toString(day);
    }

    @NonNull
    private static String getMonthString(int month) {
        // month is count from 0
        month++;
        return (month < 10) ?
                "0" + month : Integer.toString(month);
    }

    @NonNull
    private static String getYearString(int year) {
        return Integer.toString(year);
    }

    @NonNull
    private static String getMinuteString(int minute) {
        return (minute < 10) ?
                ("0" + minute) : Integer.toString(minute);
    }

    @NonNull
    private static String getHourString(int hour) {
        return (hour < 10) ?
                ("0" + hour) : Integer.toString(hour);
    }
}
