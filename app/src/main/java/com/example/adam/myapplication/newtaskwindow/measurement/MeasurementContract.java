package com.example.adam.myapplication.newtaskwindow.measurement;

public interface MeasurementContract {

    interface MeasurementView {
        String UNIT_C = "C";
        String UNIT_mmHg = "mmHg";

        String getType();
        String getUnit();
        String getHour();
        String getDate();
        String getEndDate();
        boolean isCycle();

        void setType(String type);
        void setUnit(String unit);
        void setHour(String hour);
        void setDate(String date);
        void setEndDate(String endDate);
        void setIsCycle(boolean b);
    }

    interface MeasurementPresenter {
        void onSubmitButtonClicked();
    }
}
