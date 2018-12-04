package com.example.adam.myapplication.utils;

public class DatetimeFormatter {

    public static String getDateFormatted(int year, int month, int day) {
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
