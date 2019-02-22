package com.example.adam.myapplication.data.db.doctor;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.example.adam.myapplication.data.objects.Doctor;

import java.util.List;

public class DoctorRepository {

    private DoctorDao dao;

    public DoctorRepository(Context context) {
        this.dao = DoctorDatabase.getInstance(context).getDoctorDao();
    }

    public LiveData<List<Doctor>> getAll() {
        return dao.getAll();
    }

    public void insert(Doctor doctor) {
        InsertTask task = new InsertTask(doctor);
        task.start();
    }

    public void update(Doctor doctor) {
        UpdateTask task = new UpdateTask(doctor);
        task.start();
    }

    public void delete(Doctor doctor) {
        DeleteTask task = new DeleteTask(doctor);
        task.start();
    }

    private class InsertTask extends Thread {
        private Doctor doctor;

        InsertTask(Doctor doctor) {
            this.doctor = doctor;
        }

        @Override
        public void run() {
            if (doctor != null)
                dao.insert(doctor);
        }
    }

    private class UpdateTask extends Thread {
        private Doctor doctor;

        UpdateTask(Doctor doctor) {
            this.doctor = doctor;
        }

        @Override
        public void run() {
            if (doctor != null)
                dao.update(doctor);
        }
    }

    private class DeleteTask extends Thread {
        private Doctor doctor;

        DeleteTask(Doctor doctor) {
            this.doctor = doctor;
        }

        @Override
        public void run() {
            if (doctor != null)
                dao.delete(doctor);
        }
    }
}
