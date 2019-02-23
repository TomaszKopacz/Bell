package com.example.adam.myapplication.ui.doctor.list;

import android.arch.lifecycle.LiveData;

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
        LiveData<List<Doctor>> doctors = repository.getAll();
        view.updateDoctorsList(doctors);
    }

    @Override
    public void onAddNewDoctorButtonClicked() {
        view.showNewDoctorDialog();
    }

    @Override
    public void onDoctorCreated(Doctor doctor) {

    }
}
