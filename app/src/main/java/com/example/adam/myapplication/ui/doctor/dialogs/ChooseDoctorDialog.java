package com.example.adam.myapplication.ui.doctor.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.data.objects.Doctor;

import java.util.List;

public class ChooseDoctorDialog {

    private static final String TITLE = "Choose doctor";
    private static final String CANCEL_BUTTON = "CANCEL";

    private Context context;
    private View view;

    @SuppressLint("InflateParams")
    public ChooseDoctorDialog(Context context){
        this.context = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_choose_doctor, null);

        prepareView(context);
    }

    private void prepareView(Context context) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public ChooseDoctorDialog setItems(List<Doctor> list) {
        ChooseDoctorAdapter adapter = new ChooseDoctorAdapter(list, itemListener);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);

        return this;
    }

    private ChooseDoctorAdapter.OnItemClickedListener itemListener = new ChooseDoctorAdapter.OnItemClickedListener() {
        @Override
        public void itemClicked(View view, Doctor doctor) {
            Log.i("MEDIBELL", doctor.getSpecialization());
        }
    };

    public AlertDialog create() {
        return new AlertDialog.Builder(context)
                .setTitle(TITLE)
                .setView(view)
                .create();
    }
}

