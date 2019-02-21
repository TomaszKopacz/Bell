package com.example.adam.myapplication.data.db.doctor;

import android.os.AsyncTask;

import com.example.adam.myapplication.data.objects.Doctor;

import java.util.List;

public class DoctorRepository {

    private DoctorDao dao;

    public DoctorRepository(DoctorDao dao) {
        this.dao = dao;
    }

    public List<Doctor> getAll() {
        List<Doctor> list;
        try {
            list = new GetAllTask(dao).execute().get();

        } catch (Exception e) {
            list = null;
        }

        return list;
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

    private static class GetAllTask extends AsyncTask<Void, Void, List<Doctor>> {
        private DoctorDao dao;

        GetAllTask(DoctorDao dao) {
            this.dao = dao;
        }

        @Override
        protected List<Doctor> doInBackground(Void... voids) {
            return dao.getAll();
        }
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
