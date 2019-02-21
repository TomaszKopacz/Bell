package com.example.adam.myapplication.data.db.doctor;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.adam.myapplication.data.objects.Doctor;

@Database(entities = Doctor.class, version = 1)
public abstract class DoctorDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "doctors_db";
    private static DoctorDatabase INSTANCE;

    public abstract DoctorDao getDoctorDao();

    public static DoctorDatabase getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context, DoctorDatabase.class, DATABASE_NAME).build();

        return INSTANCE;
    }
}
