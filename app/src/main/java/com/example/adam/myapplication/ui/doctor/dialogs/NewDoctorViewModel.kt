package com.example.adam.myapplication.ui.doctor.dialogs

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.example.adam.myapplication.data.db.doctor.DoctorRepository

import com.example.adam.myapplication.data.objects.Doctor

class NewDoctorViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: DoctorRepository = DoctorRepository(application)

    private var specialization: String? = null
    private var name: String? = null
    private var location: String? = null

    val doctor: MutableLiveData<Doctor> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()

    companion object {
        private const val SPECIALIZATION_NOT_SET_ERROR = "Specialization is not set"
    }

    fun setSpecialization(specialization: String) {
        this.specialization = specialization
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setLocation(location: String) {
        this.location = location
    }

    fun submitDoctor() {
        try {
            validateInput()
            insertDoctor(getDoctor())

        } catch (e: Exception) {
            error.value = e.message
        }

    }

    private fun validateInput() {
        validateSpecialization()
    }

    private fun validateSpecialization() {
        if (specialization!!.isEmpty())
            throw Exception(SPECIALIZATION_NOT_SET_ERROR)
    }

    private fun insertDoctor(newDoctor: Doctor) {
        doctor.value = newDoctor
        repository.insert(doctor.value!!)
    }

    private fun getDoctor(): Doctor {
        val doctor = Doctor()
        doctor.specialization = specialization
        doctor.name = name
        doctor.location = location

        return doctor
    }
}
