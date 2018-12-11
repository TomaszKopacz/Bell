package com.example.adam.myapplication.chartwindow;

public interface ChartContract {

    interface ChartView {
        void drawChart();
    }

    interface ChartPresenter {
        void onTimeRangeChanged(int range);
    }
}
