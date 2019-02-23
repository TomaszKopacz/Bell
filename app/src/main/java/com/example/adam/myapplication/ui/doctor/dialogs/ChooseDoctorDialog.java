package com.example.adam.myapplication.ui.doctor.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.data.objects.Doctor;
import com.example.adam.myapplication.ui.OnItemClickListener;

import java.util.List;

public class ChooseDoctorDialog {
    private AlertDialog dialog;

    private ChooseDoctorDialog(AlertDialog dialog) {
        this.dialog = dialog;
    }

    public void show() {
        dialog.show();
    }

    public static class Builder {
        private static final String TITLE = "Choose doctor";

        private Fragment fragment;
        private View view;
        private ChooseDoctorViewModel viewModel;

        private ChooseDoctorAdapter adapter;
        private OnItemClickListener<Doctor> listener;

        private AlertDialog dialog;

        @SuppressLint("InflateParams")
        public Builder(Fragment fragment) {
            this.fragment = fragment;
            this.view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.dialog_choose_doctor, null);

            viewModel = ViewModelProviders.of(fragment).get(ChooseDoctorViewModel.class);

            RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(fragment.getContext()));

            adapter = new ChooseDoctorAdapter(itemListener);
            recyclerView.setAdapter(adapter);

            viewModel.getAllDoctors().observe(fragment, new Observer<List<Doctor>>() {
                @Override
                public void onChanged(@Nullable List<Doctor> list) {
                    adapter.loadDoctors(list);
                }
            });
        }

        private OnItemClickListener<Doctor> itemListener = new OnItemClickListener<Doctor>() {
            @Override
            public void onItemClick(View view, Doctor object) {
                dialog.dismiss();

                if (listener != null)
                    listener.onItemClick(view, object);
            }
        };

        public Builder setItemClickListener(final OnItemClickListener<Doctor> listener) {
            this.listener = listener;

            return this;
        }

        public Builder showNewDoctorButton(final View.OnClickListener listener) {
            TextView newDoctorView = view.findViewById(R.id.add_new_doctor_view);
            newDoctorView.setVisibility(View.VISIBLE);

            newDoctorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    listener.onClick(view);
                }
            });

            return this;
        }

        public ChooseDoctorDialog create() {
            this.dialog = new AlertDialog.Builder(fragment.getContext())
                    .setTitle(TITLE)
                    .setView(view)
                    .create();

            return new ChooseDoctorDialog(dialog);

        }
    }
}

