package com.example.adam.myapplication.data;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class TaskRepository {

    private TaskDao dao;

    public TaskRepository(TaskDao dao){
        this.dao = dao;
    }

    public LiveData<List<Task>> gatAll(){
        return dao.getAll();
    }

    public void insert(final Task task){
        new InsertAsyncTask(dao).execute(task);
    }

    private static class InsertAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao dao;

        public InsertAsyncTask(TaskDao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {

            dao.insert(tasks[0]);
            return null;
        }
    }
}
