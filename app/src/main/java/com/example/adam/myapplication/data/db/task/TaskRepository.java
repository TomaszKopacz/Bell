package com.example.adam.myapplication.data.db.task;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.example.adam.myapplication.data.objects.Task;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TaskRepository {

    private static TaskDao dao;

    public TaskRepository(Context context) {
        dao = TaskDatabase.getInstance(context).getTaskDao();
    }

    public LiveData<List<Task>> getAll() {
        return dao.getAll();
    }

    public List<Task> getAllFromDate(Date start, Date end) {
        GetTask task = new GetTask(start, end);

        try {
            return task.execute().get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void insert(final Task task) {
        InsertThread insertThread = new InsertThread(task, dao);
        insertThread.start();
    }

    public void insert(List<Task> tasks) {
        InsertManyThread insertManyThread = new InsertManyThread(tasks, dao);
        insertManyThread.start();
    }

    public void update(Task task){
        UpdateThread updateThread = new UpdateThread(task, dao);
        updateThread.start();
    }

    public void delete(Task task) {
        DeleteThread deleteThread = new DeleteThread(task, dao);
        deleteThread.start();
    }

    private static class GetTask extends AsyncTask<Void, Void, List<Task>> {

        private Date start;
        private Date end;

        GetTask(Date start, Date end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected List<Task> doInBackground(Void... voids) {
            return dao.getAllFromTimeWindow(start, end);
        }
    }

    private class InsertThread extends Thread {
        private Task task;
        private TaskDao dao;

        InsertThread(Task task, TaskDao dao) {
            this.task = task;
            this.dao = dao;
        }

        @Override
        public void run() {
            if (task != null)
                dao.insert(task);
        }
    }

    private class InsertManyThread extends Thread {
        private List<Task> tasks;
        private TaskDao dao;

        InsertManyThread(List<Task> tasks, TaskDao dao) {
            this.tasks = tasks;
            this.dao = dao;
        }

        @Override
        public void run() {
            for (Task task : tasks)
                dao.insert(task);
        }
    }

    private class UpdateThread extends Thread {
        private Task task;
        private TaskDao dao;

        UpdateThread(Task task, TaskDao dao){
            this.task = task;
            this.dao = dao;
        }

        @Override
        public void run() {
            dao.update(task);
        }
    }

    private class DeleteThread extends Thread {
        private Task task;
        private TaskDao dao;

        DeleteThread(Task task, TaskDao dao) {
            this.task = task;
            this.dao = dao;
        }

        @Override
        public void run() {
            dao.delete(task);
        }
    }
}