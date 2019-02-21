package com.example.adam.myapplication.ui.doctor.doctors_list_tab;


import android.app.AlertDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.app.App;
import com.example.adam.myapplication.data.db.doctor.DoctorRepository;
import com.example.adam.myapplication.data.objects.Doctor;
import com.example.adam.myapplication.ui.doctor.dialogs.NewDoctorDialog;

import java.util.List;

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
        DoctorRepository repository = ((App) getActivity().getApplication()).getDoctorRepository();

        this.presenter = new DoctorsPresenter(this, repository);
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
    public void showDoctorsList(List<Doctor> list) {

    }

    @Override
    public void showNewDoctorDialog() {
        AlertDialog dialog = new NewDoctorDialog(getActivity(), doctorCreatedListener).create();
        dialog.show();
    }

    private NewDoctorDialog.OnDoctorCreatedListener doctorCreatedListener = new NewDoctorDialog.OnDoctorCreatedListener() {
        @Override
        public void onDoctorCreated(Doctor doctor) {
            presenter.onDoctorCreated(doctor);
        }
    };
}
