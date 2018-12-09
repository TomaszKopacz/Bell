package com.example.adam.myapplication.newtaskwindow.measurement;

import android.support.annotation.Nullable;

import com.example.adam.myapplication.data.Task;

public interface MeasurementContract {

    interface MeasurementView {
        String SUCCESS = "success";
        String FAILURE = "failure";

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

        void onTaskCreated(String status, @Nullable Task task);
        void navigateToParentView();
    }

    interface MeasurementPresenter {
        void onSubmitButtonClicked();
    }
}
