package com.example.adam.myapplication.data.db.doctor

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

import com.example.adam.myapplication.data.objects.Doctor

@Dao
interface DoctorDao {

    @Query("SELECT * FROM Doctor")
    fun getAll(): LiveData<List<Doctor>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(doctor: Doctor)

    @Update
    fun update(doctor: Doctor)

    @Delete
    fun delete(doctor: Doctor)
}