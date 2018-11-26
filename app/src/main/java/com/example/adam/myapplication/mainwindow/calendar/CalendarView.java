package com.example.adam.myapplication.mainwindow.calendar;

import android.content.Context;

import com.applandeo.materialcalendarview.EventDay;

import java.util.List;

public interface CalendarView {
    Context getContext();
    void displayTasks(List<EventDay> tasks);
}
