package com.example.adam.myapplication.chartwindow;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChartPresenter implements ChartContract.ChartPresenter {

    private ChartContract.ChartView view;
    private TaskRepository repository;

    ChartPresenter(ChartContract.ChartView view, TaskRepository repository){
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void onViewAttached() {
        int range = view.getRange();

        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, -range);
        Date start = calendar.getTime();

        Date end = new Date();
        end.setTime(currentDate.getTime());

        LiveData<List<Task>> tasks = repository.getAllFromDate(start, end);
        view.drawChart(tasks);
    }

    @Override
    public void onTimeRangeChanged(int range) {
        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, -range);
        Date start = calendar.getTime();

        Date end = new Date();
        end.setTime(currentDate.getTime());

        LiveData<List<Task>> tasks = repository.getAllFromDate(start, end);
        view.drawChart(tasks);
    }
}
