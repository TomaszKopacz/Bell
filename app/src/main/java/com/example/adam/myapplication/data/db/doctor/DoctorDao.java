package com.example.adam.myapplication.data.db.doctor;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.adam.myapplication.data.objects.Doctor;

import java.util.List;

@Dao
public interface DoctorDao {

    @Query("SELECT * FROM Doctor")
    LiveData<List<Doctor>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Doctor doctor);

    @Update
    void update(Doctor doctor);

    @Delete
    void delete(Doctor doctor);
}