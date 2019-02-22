package com.example.adam.myapplication.ui.doctor.create

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.example.adam.myapplication.R
import com.example.adam.myapplication.data.db.task.TaskRepository
import com.example.adam.myapplication.data.objects.Doctor
import java.util.*

class DoctorTaskViewModel(application: Application) : AndroidViewModel(application) {

    private var taskRepository: TaskRepository = TaskRepository(application)

    val doctor: MutableLiveData<Doctor> = MutableLiveData()
    val date: MutableLiveData<Calendar> = MutableLiveData()
    val endDate: MutableLiveData<Calendar> = MutableLiveData()
    val note: MutableLiveData<String> = MutableLiveData()

    init {
        setDefaultDoctor(application)
        setDefaultDates()
    }

    private fun setDefaultDoctor(application: Application) {
        val emptyDoctor = Doctor()
        emptyDoctor.specialization = application.resources.getString(R.string.not_chosen_text)
        doctor.value = emptyDoctor
    }

    private fun setDefaultDates() {
        val calendar = Calendar.getInstance()
        date.value = calendar
        endDate.value = calendar
    }

    fun doctorChosen(doctor: Doctor) {

    }

    fun dateSet(year: Int, month: Int, day: Int) {
        val newDate = date.value!!.clone() as Calendar

        newDate.set(Calendar.YEAR, year)
        newDate.set(Calendar.MONTH, month)
        newDate.set(Calendar.DAY_OF_MONTH, day)

        date.value = newDate
    }

    fun timeSet(hour: Int, minute: Int) {
        val newDate = date.value!!.clone() as Calendar

        newDate.set(Calendar.HOUR_OF_DAY, hour)
        newDate.set(Calendar.MINUTE, minute)

        date.value = newDate
    }

    fun endDateSet(year: Int, month: Int, day: Int) {
        val newDate = endDate.value!!.clone() as Calendar

        newDate.set(Calendar.YEAR, year)
        newDate.set(Calendar.MONTH, month)
        newDate.set(Calendar.DAY_OF_MONTH, day)

        endDate.value = newDate
    }

    fun noteSet(note: String) {
        this.note.value = note
    }

    fun insertTask() {

    }
}
