package com.example.adam.myapplication.mainwindow.calendar;

import android.arch.lifecycle.LiveData;

import com.example.adam.myapplication.data.Task;

import java.util.List;

public interface CalendarContract {

    interface CalendarView {
        void setTasks(LiveData<List<Task>> tasks);
    }

    interface CalendarPresenter {
        void onViewAttached();
    }
}
