package com.example.adam.myapplication.ui.doctor.list;

import android.arch.lifecycle.LiveData;

import com.example.adam.myapplication.data.objects.Doctor;

import java.util.List;

public interface DoctorsContract {

    interface DoctorsView {
        void updateDoctorsList(LiveData<List<Doctor>> list);
        void showNewDoctorDialog();
    }

    interface DoctorsPresenter {
        void onViewAttached();
        void onAddNewDoctorButtonClicked();
        void onDoctorCreated(Doctor doctor);
    }
}
