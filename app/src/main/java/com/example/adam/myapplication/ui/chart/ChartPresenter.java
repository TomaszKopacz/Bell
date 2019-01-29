package com.example.adam.myapplication.ui.chart;

import android.arch.lifecycle.LiveData;

import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;

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
        LiveData<List<Task>> tasks = repository.getAll();
        view.drawChart(tasks);
    }
}
