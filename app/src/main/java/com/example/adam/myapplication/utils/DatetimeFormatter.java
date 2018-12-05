package com.example.adam.myapplication.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DatetimeFormatter {

    private static final String TIMESTAMP_FORMAT = "dd-MM-yyyy hh:mm";
    private static final SimpleDateFormat formatter = new SimpleDateFormat(TIMESTAMP_FORMAT, Locale.ENGLISH);

    public static Date getTimestamp(String date, String time) throws ParseException {
        return formatter.parse(date + " " + time);
    }

    public static String getDateFormatted(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
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

    public static String getTimeFormatted(Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return getTimeFormatted(hour, minute);
    }
}
