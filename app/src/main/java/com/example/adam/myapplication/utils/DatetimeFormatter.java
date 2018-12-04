package com.example.adam.myapplication.utils;

import java.util.Calendar;

public class DatetimeFormatter {

    public static String getDateFormatted(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String yearString = Integer.toString(year);

        String monthString = (month < 10) ?
                "0" + month : Integer.toString(month);

        String dayString = (day < 10) ?
                "0" + day : Integer.toString(day);

        return dayString + "-" + monthString + "-" + yearString;
    }

    public static String getTimeFormatted(int hour, int minute) {
        String hourString = (hour < 10) ?
                ("0" + hour) : Integer.toString(hour);

        String minuteString = (minute < 10) ?
                ("0" + minute) : Integer.toString(minute);

        return hourString + ":" + minuteString;
    }
}
