package com.example.adam.myapplication.ui.doctor.doctors_list_tab;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.ui.doctor.NewDoctorDialog;

public class DoctorsFragment extends Fragment implements DoctorsContract.DoctorsView {

    private DoctorsContract.DoctorsPresenter presenter;

    private Button addNewDoctorButton;

    public DoctorsFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctors, container, false);

        getComponents(view);
        setPresenter();
        setListeners();

        return view;
    }

    private void getComponents(View view) {
        addNewDoctorButton = view.findViewById(R.id.add_new_button);
    }

    private void setPresenter() {
        this.presenter = new DoctorsPresenter(this);
        presenter.onViewAttached();
    }

    private void setListeners() {
        addNewDoctorButton.setOnClickListener(addNewDoctorButtonListener);
    }

    private View.OnClickListener addNewDoctorButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.onAddNewDoctorButtonClicked();
        }
    };

    @Override
    public void showNewDoctorDialog() {
        AlertDialog dialog = new NewDoctorDialog(getActivity()).create();
        dialog.show();
    }
}
