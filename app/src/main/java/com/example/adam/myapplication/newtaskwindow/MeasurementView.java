package com.example.adam.myapplication.newtaskwindow;

public interface MeasurementView {

    String TYPE_TEMPERATURE = "tempereature";
    String TYPE_PRESSURE = "pressure";
    String UNIT_C = "C";
    String UNIT_mmHg = "mmHg";

    void setPresenter(MeasurementPresenter presenter);

    String getType();
    String getUnit();
    String getHour();
    String getDate();
    String getEndDate();
    boolean isCycle();

    void navigateToParentView();
}
