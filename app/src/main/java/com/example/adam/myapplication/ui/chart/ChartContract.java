package com.example.adam.myapplication.ui.chart;

import android.arch.lifecycle.LiveData;

import com.example.adam.myapplication.data.Task;

import java.util.List;

public interface ChartContract {

    interface ChartView {
        void drawChart(LiveData<List<Task>> tasks);
    }

    interface ChartPresenter {
        void onViewAttached();
    }
}
