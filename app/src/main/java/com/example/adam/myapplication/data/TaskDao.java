package com.example.adam.myapplication.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAll();

    @Query("SELECT * FROM Task WHERE timestamp BETWEEN :start AND :end ORDER BY timestamp, type ASC")
    LiveData<List<Task>> getAllFromTimeWindow(Date start, Date end);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Task task);
}
