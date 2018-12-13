package com.example.adam.myapplication.chartwindow;

import android.arch.lifecycle.LiveData;

import com.example.adam.myapplication.data.Task;

import java.util.List;

public interface ChartContract {

    interface ChartView {
        int getRange();
        void drawChart(LiveData<List<Task>> tasks);
    }

    interface ChartPresenter {
        void onViewAttached();
        void onTimeRangeChanged(int range);
    }
}
