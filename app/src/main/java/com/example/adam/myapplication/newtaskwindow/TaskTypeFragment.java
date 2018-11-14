package com.example.adam.myapplication.newtaskwindow;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adam.myapplication.R;


public class TaskTypeFragment extends Fragment {

    private View measurementView;
    private View pillsView;
    private View examinationView;

    public TaskTypeFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_type, container, false);

        getLayoutViews(view);
        setListeners();

        return view;
    }

    private void getLayoutViews(View view) {
        measurementView = view.findViewById(R.id.measurement_view);
        pillsView = view.findViewById(R.id.pills_view);
        examinationView = view.findViewById(R.id.examination_view);
    }

    private void setListeners() {
        measurementView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AddTaskActivity)getActivity()).changeFragment(new AddTaskFragment());
            }
        });

        pillsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TELM", "pills");
            }
        });

        examinationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TELM", "examination");
            }
        });
    }
}
