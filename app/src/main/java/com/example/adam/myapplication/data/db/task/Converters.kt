package com.example.adam.myapplication.data.db.task

import android.arch.persistence.room.TypeConverter

import java.util.Date

class Converters {

    @TypeConverter
    fun dateFromLong(timestamp: Long?): Date {
        return Date(timestamp!!)
    }

    @TypeConverter
    fun longFromDate(date: Date): Long? {
        return date.time
    }
}