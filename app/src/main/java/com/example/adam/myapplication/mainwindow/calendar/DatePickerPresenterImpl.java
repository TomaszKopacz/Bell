package com.example.adam.myapplication.mainwindow.calendar;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.applandeo.materialcalendarview.EventDay;
import com.example.adam.myapplication.R;
import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatePickerPresenterImpl {

    private CalendarFragment fragment;
    private TaskRepository repository;

    public DatePickerPresenterImpl(CalendarFragment fragment, TaskRepository repository){
        this.fragment = fragment;
        this.repository = repository;
    }

    void initializeTasks() {

        if (fragment.getContext() != null) {
            repository.gatAll().observe((LifecycleOwner) fragment.getContext(), new Observer<List<Task>>() {
                @Override
                public void onChanged(@Nullable List<Task> tasks) {
                    fragment.displayTasks(parseTasksToDayEvents(tasks));
                }
            });
        }
    }

    @NonNull
    private List<EventDay> parseTasksToDayEvents(List<Task> tasks) {
        List<EventDay> events = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        try {
            for (Task task : tasks) {
                Date date = formatter.parse(task.getDate());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.MONTH, 1);
                EventDay event = new EventDay(calendar, R.drawable.bell);
                events.add(event);
            }

        } catch (Exception e) {
            Log.e("TELM", e.getMessage());

        }
        return events;
    }
}
