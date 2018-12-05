package com.example.adam.myapplication.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = Task.class, version = 1)
@TypeConverters(Converters.class)
public abstract class TaskDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "tasks_db";
    private static TaskDatabase INSTANCE;

    public abstract TaskDao getTaskDao();

    public static TaskDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, TaskDatabase.class, DATABASE_NAME).build();
        }

        return INSTANCE;
    }
}
