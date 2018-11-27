package com.example.adam.myapplication.mainwindow.calendar;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
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

public class CalendarPresenterImpl implements CalendarPresenter{

    private CalendarFragment fragment;
    private TaskRepository repository;

    CalendarPresenterImpl(CalendarFragment fragment, TaskRepository repository) {
        this.fragment = fragment;
        this.repository = repository;
    }

    @Override
    public void presentTasks() {
        if (fragment.getContext() != null) {
            repository.gatAll().observe((LifecycleOwner) fragment.getContext(), new Observer<List<Task>>() {
                @Override
                public void onChanged(List<Task> tasks) {
                    fragment.displayTasks(makeDayEventsList(tasks));
                }
            });
        }
    }

    private List<EventDay> makeDayEventsList(List<Task> tasks) {
        List<EventDay> events = new ArrayList<>();

        for (Task task : tasks)
            events.add(parseTaskToDayEvent(task));

        return events;
    }

    private EventDay parseTaskToDayEvent(Task task) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        try {
            Date date = formatter.parse(task.getDate());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, 1);

            return new EventDay(calendar, R.drawable.bell);

        } catch (Exception e) {
            Log.e("TELM", e.getMessage());
        }

        return null;
    }
}
