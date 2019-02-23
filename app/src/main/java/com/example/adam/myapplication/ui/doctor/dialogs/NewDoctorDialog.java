package com.example.adam.myapplication.ui.doctor.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.data.objects.Doctor;

public class NewDoctorDialog {

    private AlertDialog dialog;

    public static final int SUCCESS = 1;
    public static final int FAILURE = -1;

    @SuppressLint("InflateParams")
    private NewDoctorDialog(AlertDialog dialog) {
        this.dialog = dialog;
    }

    public void show() {
        dialog.show();
    }

    public static class Builder {
        private static final String TITLE = "New doctor";
        private static final String OK_BUTTON = "ADD";
        private static final String CANCEL_BUTTON = "CANCEL";

        private Fragment fragment;
        private View view;
        private NewDoctorViewModel viewModel;

        private AlertDialog dialog;
        private OnResultListener listener;

        @SuppressLint("InflateParams")
        public Builder(Fragment fragment) {
            this.fragment = fragment;
            this.view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.dialog_new_doctor, null);
            this.viewModel = ViewModelProviders.of(fragment).get(NewDoctorViewModel.class);

            setObservers();
        }

        private void setObservers() {
            setDoctorCreatedObserver();
            setErrorObserver();
        }

        private void setDoctorCreatedObserver() {
            viewModel.getDoctor().observe(fragment, new Observer<Doctor>() {
                @Override
                public void onChanged(@Nullable Doctor doctor) {

                    if (doctor != null) {
                        if (dialog != null)
                            dialog.dismiss();

                        if (listener != null)
                            listener.onResult(SUCCESS, doctor, dialog, null);
                    }
                }
            });
        }

        private void setErrorObserver() {
            viewModel.getError().observe(fragment, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String error) {
                    if (listener != null)
                        listener.onResult(FAILURE, null, dialog, error);
                }
            });
        }

        public NewDoctorDialog.Builder setResultListener(OnResultListener listener) {
            this.listener = listener;

            return this;
        }

        public NewDoctorDialog create() {
            this.dialog = new AlertDialog.Builder(fragment.getContext())
                    .setTitle(TITLE)
                    .setView(view)
                    .setPositiveButton(OK_BUTTON, positiveButtonListener)
                    .setNegativeButton(CANCEL_BUTTON, negativeButtonListener)
                    .create();

            return new NewDoctorDialog(dialog);
        }

        private DialogInterface.OnClickListener positiveButtonListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                viewModel.setSpecialization(getSpecializationText());
                viewModel.setName(getNameText());
                viewModel.setLocation(getLocationText());

                viewModel.submitDoctor();
            }
        };

        private DialogInterface.OnClickListener negativeButtonListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        private String getSpecializationText() {
            return ((EditText) view.findViewById(R.id.specialization_text)).getText().toString();
        }

        private String getNameText() {
            return ((EditText) view.findViewById(R.id.name_text)).getText().toString();
        }

        private String getLocationText() {
            return ((EditText) view.findViewById(R.id.location_text)).getText().toString();
        }

        public interface OnResultListener {
            void onResult(int status, Doctor doctor, AlertDialog dialog, String error);
        }
    }
}
