package com.example.adam.myapplication.data.db.doctor;

import android.arch.lifecycle.LiveData;

import com.example.adam.myapplication.data.objects.Doctor;

import java.util.List;

public class DoctorRepository {

    private DoctorDao dao;

    public DoctorRepository(DoctorDao dao) {
        this.dao = dao;
    }

    public LiveData<List<Doctor>> getAll() {
        return dao.getAll();
    }

    public void insert(Doctor doctor) {
        InsertThread thread = new InsertThread(doctor, dao);
        thread.start();
    }

    public void update(Doctor doctor) {
        UpdateThread thread = new UpdateThread(doctor, dao);
        thread.start();
    }

    public void delete(Doctor doctor) {
        DeleteThread thread = new DeleteThread(doctor, dao);
        thread.start();
    }

    private class InsertThread extends Thread {
        private Doctor doctor;
        private DoctorDao dao;

        InsertThread(Doctor doctor, DoctorDao dao) {
            this.doctor = doctor;
            this.dao = dao;
        }

        @Override
        public void run() {
            if (doctor != null)
                dao.insert(doctor);
        }
    }

    private class UpdateThread extends Thread {
        private Doctor doctor;
        private DoctorDao dao;

        UpdateThread(Doctor doctor, DoctorDao dao) {
            this.doctor = doctor;
            this.dao = dao;
        }

        @Override
        public void run() {
            if (doctor != null)
                dao.update(doctor);
        }
    }

    private class DeleteThread extends Thread {
        private Doctor doctor;
        private DoctorDao dao;

        DeleteThread(Doctor doctor, DoctorDao dao) {
            this.doctor = doctor;
            this.dao = dao;
        }

        @Override
        public void run() {
            if (doctor != null)
                dao.delete(doctor);
        }
    }
}
