package com.example.adam.myapplication.data.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class Converters {

    @TypeConverter
    public Date dateFromLong(Long timestamp) {
        return new Date(timestamp);
    }

    @TypeConverter
    public Long longFromDate(Date date) {
        return date.getTime();
    }
}
