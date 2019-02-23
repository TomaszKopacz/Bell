package com.example.adam.myapplication.ui.doctor.dialogs

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.util.Log
import com.example.adam.myapplication.data.db.doctor.DoctorRepository

class ChooseDoctorViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DoctorRepository(application)
    val allDoctors = repository.all!!
}
