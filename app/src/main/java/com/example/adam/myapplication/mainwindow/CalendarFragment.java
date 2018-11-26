package com.example.adam.myapplication.mainwindow;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.example.adam.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    private CalendarView calendarView;
    private List<EventDay> tasks = new ArrayList<>();

    public CalendarFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarView = view.findViewById(R.id.calendar);

        assignTasksToCalendar();

        return view;
    }

    private void assignTasksToCalendar(){
        initializeTasks();
        calendarView.setEvents(tasks);
    }

    private void initializeTasks(){

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

            Date date1 = formatter.parse("25-11-2018");
            Date date2 = formatter.parse("26-11-2018");
            Date date3 = formatter.parse("27-11-2018");

            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            Calendar calendar3 = Calendar.getInstance();

            calendar1.setTime(date1);
            calendar2.setTime(date2);
            calendar3.setTime(date3);

            EventDay task1 = new EventDay(calendar1, R.drawable.bell);
            EventDay task2 = new EventDay(calendar2, R.drawable.bell);
            EventDay task3 = new EventDay(calendar3, R.drawable.bell);

            tasks.add(task1);
            tasks.add(task2);
            tasks.add(task3);

        } catch (ParseException e) {
            Log.e("TELM", "Cannot parse date");
        }
    }
}
