package com.example.adam.myapplication.chartwindow;

import com.example.adam.myapplication.data.TaskRepository;

public class ChartPresenter implements ChartContract.ChartPresenter {

    private ChartContract.ChartView view;
    private TaskRepository repository;

    ChartPresenter(ChartContract.ChartView view, TaskRepository repository){
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void onTimeRangeChanged(int range) {

    }
}
