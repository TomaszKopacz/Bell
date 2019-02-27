package com.example.adam.myapplication.data.db.doctor

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

import com.example.adam.myapplication.data.objects.Doctor

@Database(entities = [Doctor::class], version = 1)
abstract class DoctorDatabase : RoomDatabase() {

    abstract val doctorDao: DoctorDao

    companion object {

        private const val DATABASE_NAME = "doctors_db"
        private var INSTANCE: DoctorDatabase? = null

        fun getInstance(context: Context): DoctorDatabase {
            return INSTANCE
                    ?: Room.databaseBuilder(context, DoctorDatabase::class.java, DATABASE_NAME).build()
        }
    }
}