package com.example.adam.myapplication.data;

import android.arch.lifecycle.LiveData;

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

    public LiveData<List<Task>> getAllFromDate(Date date) {
        Date startOfDay = getStartDate(date);
        Date endOfDay = getEndDate(date);

        return dao.getAllFromTimeWindow(startOfDay, endOfDay);
    }

    public void insert(final Task task) {
        InsertThread insertThread = new InsertThread(task, dao);
        insertThread.start();
    }

    public void insert(List<Task> tasks) {
        InsertManyThread insertManyThread = new InsertManyThread(tasks, dao);
        insertManyThread.start();
    }

    public void delete(Task task) {
        DeleteThread deleteThread = new DeleteThread(task, dao);
        deleteThread.start();
    }

    private Date getStartDate(Date date) {
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return (Date) date.clone();
    }

    private Date getEndDate(Date date) {
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
        return (Date) date.clone();
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
