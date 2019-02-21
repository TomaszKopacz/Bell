package com.example.adam.myapplication.ui.doctor.doctors_list_tab;

public class DoctorsPresenter implements DoctorsContract.DoctorsPresenter {

    private DoctorsContract.DoctorsView view;

    DoctorsPresenter(DoctorsContract.DoctorsView view) {
        this.view = view;
    }

    @Override
    public void onViewAttached() {

    }

    @Override
    public void onAddNewDoctorButtonClicked() {
        view.showNewDoctorDialog();
    }
}
