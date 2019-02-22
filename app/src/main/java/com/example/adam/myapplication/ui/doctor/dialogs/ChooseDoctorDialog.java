package com.example.adam.myapplication.ui.doctor.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
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

        private Context context;
        private View view;
        private AlertDialog dialog;

        @SuppressLint("InflateParams")
        public Builder(Context context) {
            this.context = context;
            this.view = LayoutInflater.from(context).inflate(R.layout.dialog_choose_doctor, null);

            createView();
        }

        private void createView() {
            RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        public Builder setItems(List<Doctor> list, final OnItemClickListener<Doctor> listener) {
            ChooseDoctorAdapter adapter = new ChooseDoctorAdapter(list, new OnItemClickListener<Doctor>() {
                @Override
                public void onItemClick(View view, Doctor doctor) {
                    dialog.dismiss();
                    listener.onItemClick(view, doctor);
                }
            });

            RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
            recyclerView.setAdapter(adapter);

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
            this.dialog = new AlertDialog.Builder(context)
                    .setTitle(TITLE)
                    .setView(view)
                    .create();

            return new ChooseDoctorDialog(dialog);

        }
    }
}

