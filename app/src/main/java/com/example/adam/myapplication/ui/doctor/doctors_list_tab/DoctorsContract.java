package com.example.adam.myapplication.ui.doctor.doctors_list_tab;

public interface DoctorsContract {

    interface DoctorsView {
        void showNewDoctorDialog();
    }

    interface DoctorsPresenter {
        void onViewAttached();
        void onAddNewDoctorButtonClicked();
    }
}
