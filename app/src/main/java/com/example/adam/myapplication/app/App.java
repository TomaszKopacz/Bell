package com.example.adam.myapplication.app;

import android.app.Application;

import com.example.adam.myapplication.data.TaskDao;
import com.example.adam.myapplication.data.TaskDatabase;
import com.example.adam.myapplication.data.TaskRepository;

public class App extends Application {

    public TaskRepository getTaskRepository() {
        TaskDatabase db = TaskDatabase.getInstance(getApplicationContext());
        TaskDao dao = db.getTaskDao();

        return new TaskRepository(dao);
    }
}
