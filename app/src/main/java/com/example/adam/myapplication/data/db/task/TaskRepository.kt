package com.example.adam.myapplication.data.db.task

import android.arch.lifecycle.LiveData
import android.content.Context
import android.os.AsyncTask

import com.example.adam.myapplication.data.objects.Task

import java.util.Date
import java.util.concurrent.ExecutionException

class TaskRepository(context: Context) {

    companion object {
        private lateinit var dao: TaskDao
    }

    init {
        dao = TaskDatabase.getInstance(context).taskDao
    }

    fun getAll(): LiveData<List<Task>> {
        return dao.getAll()
    }

    fun getAllFromDate(start: Date, end: Date): List<Task>? {
        val task = GetTask(start, end)

        try {
            return task.execute().get()

        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return null
    }

    fun insert(task: Task) {
        val insertThread = InsertThread(task, dao)
        insertThread.start()
    }

    fun insert(tasks: List<Task>) {
        val insertManyThread = InsertManyThread(tasks, dao)
        insertManyThread.start()
    }

    fun update(task: Task) {
        val updateThread = UpdateThread(task, dao)
        updateThread.start()
    }

    fun delete(task: Task) {
        val deleteThread = DeleteThread(task, dao)
        deleteThread.start()
    }

    private class GetTask internal constructor(private val start: Date, private val end: Date) : AsyncTask<Void, Void, List<Task>>() {

        override fun doInBackground(vararg voids: Void): List<Task> {
            return dao.getAllFromTimeWindow(start, end)
        }
    }

    private inner class InsertThread internal constructor(private val task: Task?, private val dao: TaskDao) : Thread() {

        override fun run() {
            if (task != null)
                dao.insert(task)
        }
    }

    private inner class InsertManyThread internal constructor(private val tasks: List<Task>, private val dao: TaskDao) : Thread() {

        override fun run() {
            for (task in tasks)
                dao.insert(task)
        }
    }

    private inner class UpdateThread internal constructor(private val task: Task, private val dao: TaskDao) : Thread() {

        override fun run() {
            dao.update(task)
        }
    }

    private inner class DeleteThread internal constructor(private val task: Task, private val dao: TaskDao) : Thread() {

        override fun run() {
            dao.delete(task)
        }
    }
}