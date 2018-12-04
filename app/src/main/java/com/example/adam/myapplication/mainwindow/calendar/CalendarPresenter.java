package com.example.adam.myapplication.mainwindow.calendar;

import com.example.adam.myapplication.data.TaskRepository;

public class CalendarPresenter implements CalendarContract.CalendarPresenter {

    private CalendarFragment fragment;
    private TaskRepository repository;

    CalendarPresenter(CalendarFragment fragment, TaskRepository repository) {
        this.fragment = fragment;
        this.repository = repository;
    }

    @Override
    public void onViewAttached() {
        //fragment.setTasks(repository.gatAll());
    }
}
