package com.example.adam.myapplication.data.db.doctor

import android.arch.lifecycle.LiveData
import android.content.Context

import com.example.adam.myapplication.data.objects.Doctor

class DoctorRepository(context: Context) {

    companion object {
        private lateinit var dao: DoctorDao
    }

    init {
        dao = DoctorDatabase.getInstance(context).doctorDao
    }

    fun getAll(): LiveData<List<Doctor>> {
        return dao.getAll()
    }

    fun insert(doctor: Doctor) {
        val task = InsertTask(doctor)
        task.start()
    }

    fun update(doctor: Doctor) {
        val task = UpdateTask(doctor)
        task.start()
    }

    fun delete(doctor: Doctor) {
        val task = DeleteTask(doctor)
        task.start()
    }

    private inner class InsertTask internal constructor(private val doctor: Doctor?) : Thread() {

        override fun run() {
            if (doctor != null)
                dao.insert(doctor)
        }
    }

    private inner class UpdateTask internal constructor(private val doctor: Doctor?) : Thread() {

        override fun run() {
            if (doctor != null)
                dao.update(doctor)
        }
    }

    private inner class DeleteTask internal constructor(private val doctor: Doctor?) : Thread() {

        override fun run() {
            if (doctor != null)
                dao.delete(doctor)
        }
    }
}