package com.example.adam.myapplication.mainwindow.calendar;

import android.app.Activity;
import android.app.Fragment;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.example.adam.myapplication.R;
import com.example.adam.myapplication.app.App;
import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        initializeTasks();
    }

    public void initializeTasks() {

        if (activity != null) {
            TaskRepository repository = ((App) activity.getApplication()).getTaskRepository();
            repository.gatAll().observe((LifecycleOwner) activity, new Observer<List<Task>>() {
                @Override
                public void onChanged(@Nullable List<Task> tasks) {
                    calendarView.setEvents(parseTasksToDayEvents(tasks));
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
