package com.example.adam.myapplication.app;

import android.app.Application;

import com.example.adam.myapplication.data.db.doctor.DoctorDao;
import com.example.adam.myapplication.data.db.doctor.DoctorDatabase;
import com.example.adam.myapplication.data.db.doctor.DoctorRepository;
import com.example.adam.myapplication.data.db.task.TaskDao;
import com.example.adam.myapplication.data.db.task.TaskDatabase;
import com.example.adam.myapplication.data.db.task.TaskRepository;

public class App extends Application {

    public TaskRepository getTaskRepository() {
        TaskDatabase db = TaskDatabase.getInstance(getApplicationContext());
        TaskDao dao = db.getTaskDao();

        return new TaskRepository(dao);
    }

    public DoctorRepository getDoctorRepository() {
        DoctorDatabase db = DoctorDatabase.getInstance(getApplicationContext());
        DoctorDao dao = db.getDoctorDao();

        return new DoctorRepository(dao);
    }
}
