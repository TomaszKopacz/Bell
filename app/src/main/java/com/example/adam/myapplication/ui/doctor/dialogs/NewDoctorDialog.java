package com.example.adam.myapplication.ui.doctor.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.data.objects.Doctor;

public class NewDoctorDialog {

    private static final String TITLE = "New doctor";
    private static final String OK_BUTTON = "ADD";
    private static final String CANCEL_BUTTON = "CANCEL";

    private Context context;
    private OnDoctorCreatedListener listener;
    private View view;

    private static final String FIELDS_IMPROPER_ERROR = "Fill the fields with proper text!";

    @SuppressLint("InflateParams")
    public NewDoctorDialog(Context context, OnDoctorCreatedListener listener) {
        this.context = context;
        this.listener = listener;

        view = LayoutInflater.from(context).inflate(R.layout.dialog_new_doctor, null);
    }

    public interface OnDoctorCreatedListener {
        void onDoctorCreated(Doctor doctor);
    }

    public AlertDialog create() {
        return new AlertDialog.Builder(context)
                .setTitle(TITLE)
                .setView(view)
                .setPositiveButton(OK_BUTTON, positiveButtonListener)
                .setNegativeButton(CANCEL_BUTTON, negativeButtonListener)
                .create();
    }

    private DialogInterface.OnClickListener positiveButtonListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (fieldsCorrect()) {
                listener.onDoctorCreated(getDoctor());
                dialog.dismiss();

            } else {
                showError();
            }
        }
    };

    private DialogInterface.OnClickListener negativeButtonListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };

    private boolean fieldsCorrect() {
        return isSpecializationNotEmpty();
    }

    private boolean isSpecializationNotEmpty() {
        return !getSpecialization().isEmpty();
    }

    private Doctor getDoctor() {
        String specialization = getSpecialization();
        String name = getName();
        String location = getLocation();

        Doctor doctor = new Doctor();
        doctor.setSpecialization(specialization);
        doctor.setName(name);
        doctor.setLocation(location);

        return doctor;
    }

    private String getSpecialization() {
        return ((EditText) view.findViewById(R.id.specialization_text)).getText().toString();
    }

    private String getName() {
        return ((EditText) view.findViewById(R.id.name_text)).getText().toString();
    }

    private String getLocation() {
        return ((EditText) view.findViewById(R.id.location_text)).getText().toString();
    }

    private void showError() {
        Toast.makeText(context, NewDoctorDialog.FIELDS_IMPROPER_ERROR, Toast.LENGTH_LONG).show();
    }
}
