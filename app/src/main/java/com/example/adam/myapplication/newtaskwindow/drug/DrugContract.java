package com.example.adam.myapplication.newtaskwindow.drug;

import com.example.adam.myapplication.data.Task;

public interface DrugContract {

    interface DrugView {

        String SUCCESS = "success";
        String FAILURE = "failure";

        String getDrug();
        String getDose();
        String getTime();
        String getDate();
        String getEndDate();

        void setDrug(String drug);
        void setDose(String dose);
        void setTime(String time);
        void setDate(String date);
        void setEndDate(String endDate);

        void onTaskCreated(String status, Task task);
    }

    interface DrugPresenter {
        void onSubmitButtonClicked();
    }
}
