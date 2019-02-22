package com.example.adam.myapplication.ui.doctor.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.data.objects.Doctor;
import com.example.adam.myapplication.ui.OnItemClickListener;

import java.util.List;

public class ChooseDoctorDialog {

    private static final String TITLE = "Choose doctor";

    private AlertDialog dialog;
    private OnItemClickListener<Doctor> itemListener;

    @SuppressLint("InflateParams")
    public ChooseDoctorDialog(Context context, List<Doctor> list, OnItemClickListener<Doctor> itemListener){
        View view = createView(context, list);

        this.itemListener = itemListener;
        this.dialog = new AlertDialog.Builder(context).setTitle(TITLE).setView(view).create();
    }

    private View createView(Context context, List<Doctor> list) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_choose_doctor, null);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ChooseDoctorAdapter adapter = new ChooseDoctorAdapter(list, listener);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private OnItemClickListener<Doctor> listener = new OnItemClickListener<Doctor>() {
        @Override
        public void onItemClick(View view, Doctor object) {
            dialog.dismiss();
            itemListener.onItemClick(view, object);
        }
    };

    public void show() {
        dialog.show();
    }
}

