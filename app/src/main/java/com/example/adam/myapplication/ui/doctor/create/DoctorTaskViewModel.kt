package com.example.adam.myapplication.ui.doctor.create

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.example.adam.myapplication.data.db.task.TaskRepository
import com.example.adam.myapplication.data.objects.Doctor
import com.example.adam.myapplication.data.objects.Task
import java.util.*

class DoctorTaskViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: TaskRepository = TaskRepository(application)

    val task: MutableLiveData<Task?> = MutableLiveData()

    val doctor: MutableLiveData<Doctor> = MutableLiveData()
    val date: MutableLiveData<Calendar> = MutableLiveData()
    var isCyclic: Boolean = false
    val endDate: MutableLiveData<Calendar> = MutableLiveData()
    val note: MutableLiveData<String> = MutableLiveData()

    val error: MutableLiveData<String?> = MutableLiveData()

    companion object {
        private const val NOT_CHOSEN_TEXT = "not chosen"
        const val IMPROPER_DOCTOR_ERROR = "Doctor not set"
        const val DATES_ORDER_ERROR = "End date cannot be earlier than date"
    }

    init {
        setDefaultDoctor()
        setDefaultDates()
    }

    private fun setDefaultDoctor() {
        doctor.value = Doctor()
        doctor.value!!.specialization = NOT_CHOSEN_TEXT
    }

    private fun setDefaultDates() {
        val calendar = Calendar.getInstance()
        date.value = calendar
        endDate.value = calendar
    }

    fun doctorChosen(chosen: Doctor) {
        doctor.value = chosen
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

    fun isCyclic(isCyclic: Boolean) {
        this.isCyclic = isCyclic
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

    fun submitTask() {
        try {
            validateInput()
            insertTask(newTask())

        } catch (e: Exception) {
            error.value = e.message
        }
    }

    private fun validateInput() {
        validateDoctor()
        validateDates()
    }

    private fun validateDoctor() {
        if (doctor.value!!.specialization == NOT_CHOSEN_TEXT)
            throw Exception(IMPROPER_DOCTOR_ERROR)
    }

    private fun validateDates() {
        if (isCyclic && endDate.value!!.before(date.value))
            throw Exception(DATES_ORDER_ERROR)
    }

    private fun insertTask(newTask: Task) {
        task.value = newTask
        repository.insert(task.value!!)
    }

    private fun newTask(): Task {
        val task = Task(Task.DOCTOR, date.value!!.time)
        task.info = doctor.value!!.specialization
        task.note = note.value

        return task
    }
}
