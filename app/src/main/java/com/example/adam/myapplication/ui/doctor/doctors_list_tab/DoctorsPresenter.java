package com.example.adam.myapplication.ui.doctor.doctors_list_tab;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.adam.myapplication.data.db.doctor.DoctorRepository;
import com.example.adam.myapplication.data.objects.Doctor;

import java.util.List;

public class DoctorsPresenter implements DoctorsContract.DoctorsPresenter {

    private DoctorsContract.DoctorsView view;
    private DoctorRepository repository;

    DoctorsPresenter(DoctorsContract.DoctorsView view, DoctorRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void onViewAttached() {
        Log.i("MEDIBELL", "onViewAttached");
        LiveData<List<Doctor>> doctors = repository.getAll();
        view.showDoctorsList(doctors);
    }

    @Override
    public void onAddNewDoctorButtonClicked() {
        view.showNewDoctorDialog();
    }

    @Override
    public void onDoctorCreated(Doctor doctor) {
        repository.insert(doctor);
    }
}
