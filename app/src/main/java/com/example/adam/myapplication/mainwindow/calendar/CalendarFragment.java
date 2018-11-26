package com.example.adam.myapplication.mainwindow.calendar;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.example.adam.myapplication.R;
import com.example.adam.myapplication.app.App;

import java.util.List;

public class CalendarFragment extends Fragment {

    private Activity activity;
    private CalendarView calendarView;

    public CalendarFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarView = view.findViewById(R.id.calendar);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatePickerPresenterImpl presenter = new DatePickerPresenterImpl(this,
                ((App)activity.getApplication()).getTaskRepository());
        presenter.initializeTasks();
    }

    @Override
    public Context getContext() {
        return activity;
    }

    public void displayTasks(List<EventDay> tasks){
        calendarView.setEvents(tasks);
    }
}
