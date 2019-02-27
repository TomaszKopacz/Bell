package com.example.adam.myapplication.data.db.task

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context

import com.example.adam.myapplication.data.objects.Task

@Database(entities = [Task::class], version = 1)
@TypeConverters(Converters::class)
abstract class TaskDatabase : RoomDatabase() {

    abstract val taskDao: TaskDao

    companion object {

        private const val DATABASE_NAME = "tasks_db"
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase {
            return INSTANCE
                    ?: Room.databaseBuilder(context, TaskDatabase::class.java, DATABASE_NAME).build()
        }
    }
}