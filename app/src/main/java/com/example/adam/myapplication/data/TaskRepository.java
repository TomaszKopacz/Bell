package com.example.adam.myapplication.data;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Date;
import java.util.List;

public class TaskRepository {

    private TaskDao dao;

    public TaskRepository(TaskDao dao) {
        this.dao = dao;
    }

    public LiveData<List<Task>> getAll() {
        return dao.getAll();
    }

    public LiveData<List<Task>> getAllFromDate(Date date){
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        Date startOfDay = (Date) date.clone();

        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
        Date endOfDay = (Date) date.clone();

        return dao.getAllFromTimeWindow(startOfDay, endOfDay);
    }

    public void insert(final Task task) {
        new InsertAsyncTask(dao).execute(task);
    }

    private static class InsertAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao dao;

        InsertAsyncTask(TaskDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {

            dao.insert(tasks[0]);
            return null;
        }
    }
}
