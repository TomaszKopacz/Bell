package com.example.adam.myapplication.app

import android.app.Application

import com.example.adam.myapplication.data.db.doctor.DoctorDao
import com.example.adam.myapplication.data.db.doctor.DoctorDatabase
import com.example.adam.myapplication.data.db.doctor.DoctorRepository
import com.example.adam.myapplication.data.db.task.TaskDao
import com.example.adam.myapplication.data.db.task.TaskDatabase
import com.example.adam.myapplication.data.db.task.TaskRepository

class App : Application()
