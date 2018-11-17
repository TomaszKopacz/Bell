package com.example.adam.myapplication.newtaskwindow;

public interface MeasurementPresenter {

    void onMeasurementTypeChosen(String type);
    void onUnitChanged(String unit);
    void onHourPicked(String hour);
    void onDatePicked(String date);
    void onEndDatePicked(String endDate);
}
