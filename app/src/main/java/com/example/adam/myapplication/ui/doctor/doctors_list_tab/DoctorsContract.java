package com.example.adam.myapplication.ui.doctor.doctors_list_tab;

import android.arch.lifecycle.LiveData;

import com.example.adam.myapplication.data.objects.Doctor;

import java.util.List;

public interface DoctorsContract {

    interface DoctorsView {
        void showDoctorsList(LiveData<List<Doctor>> list);
        void showNewDoctorDialog();
    }

    interface DoctorsPresenter {
        void onViewAttached();
        void onAddNewDoctorButtonClicked();
        void onDoctorCreated(Doctor doctor);
    }
}