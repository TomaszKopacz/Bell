package com.example.adam.myapplication.newtaskwindow;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.app.App;
import com.example.adam.myapplication.data.TaskRepository;
import com.example.adam.myapplication.newtaskwindow.drug.DrugFragment;
import com.example.adam.myapplication.newtaskwindow.examination.ExaminationFragment;
import com.example.adam.myapplication.newtaskwindow.measurement.MeasurementFragment;
import com.example.adam.myapplication.newtaskwindow.measurement.MeasurementPresenterImpl;


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

        measurementView.setOnTouchListener(touchListener);
        pillsView.setOnTouchListener(touchListener);
        examinationView.setOnTouchListener(touchListener);

        measurementView.setOnClickListener(onClickListener);
        pillsView.setOnClickListener(onClickListener);
        examinationView.setOnClickListener(onClickListener);
    }

    private View.OnTouchListener touchListener = new View.OnTouchListener() {

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            shadowView(view, motionEvent);
            return false;
        }
    };

    private void shadowView(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                view.setAlpha(0.9f);
                break;
            case MotionEvent.ACTION_UP:
                view.setAlpha(1f);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == measurementView) {
                createMeasurementContract();

            } else if (view == pillsView) {
                ((AddTaskActivity) getActivity()).changeFragment(new DrugFragment());

            }else if (view == examinationView) {
                ((AddTaskActivity) getActivity()).changeFragment(new ExaminationFragment());
            }
        }
    };

    private void createMeasurementContract() {
        MeasurementFragment fragment = new MeasurementFragment();
        TaskRepository repository = ((App)getActivity().getApplication()).getTaskRepository();
        MeasurementPresenterImpl presenter = new MeasurementPresenterImpl(fragment, repository);

        fragment.setPresenter(presenter);
        ((AddTaskActivity) getActivity()).changeFragment(fragment);
    }
}
