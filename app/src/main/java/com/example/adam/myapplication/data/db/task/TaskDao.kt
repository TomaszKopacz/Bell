package com.example.adam.myapplication.data.db.task

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

import com.example.adam.myapplication.data.objects.Task

import java.util.Date

@Dao
interface TaskDao {

    @Query("SELECT * FROM Task ORDER BY timestamp, type ASC")
    fun getAll(): LiveData<List<Task>>

    @Query("SELECT * FROM Task WHERE timestamp BETWEEN :start AND :end ORDER BY timestamp, type ASC")
    fun getAllFromTimeWindow(start: Date, end: Date): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)
}