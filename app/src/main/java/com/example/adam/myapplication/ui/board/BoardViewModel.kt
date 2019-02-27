package com.example.adam.myapplication.ui.board

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.example.adam.myapplication.data.db.task.TaskRepository
import com.example.adam.myapplication.data.objects.Task
import java.util.*

class BoardViewModel(application: Application) : AndroidViewModel(application) {

    val repository: TaskRepository = TaskRepository(application)

    var calendar: MutableLiveData<Calendar> = MutableLiveData()
    var list: MutableLiveData<List<Task>> = MutableLiveData()

    init {
        initCalendar()
        initTasks()
    }

    private fun initCalendar() {
        calendar.value = Calendar.getInstance()
    }

    private fun initTasks() {
        val start = getStartOfDay(calendar.value!!).time
        val end = getEndOfDay(calendar.value!!).time
        list.value = repository.getAllFromDate(start, end)
    }

    fun dateSet(newCalendar: Calendar) {
        setCalendar(newCalendar)
        setTasks(newCalendar)
    }

    private fun setCalendar(newCalendar: Calendar) {
        calendar.value = newCalendar
    }

    private fun setTasks(newCalendar: Calendar) {

        val start = getStartOfDay(newCalendar).time
        val end = getEndOfDay(newCalendar).time

        list.value = repository.getAllFromDate(start, end)
    }

    private fun getStartOfDay(calendar: Calendar): Calendar {
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return calendar
    }

    private fun getEndOfDay(calendar: Calendar): Calendar {
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)

        return calendar
    }
}