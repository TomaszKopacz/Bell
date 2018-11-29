package com.example.adam.myapplication.newtaskwindow.drug;

public interface DrugContract {

    interface DrugView {
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
    }

    interface DrugPresenter {
        void onSubmitButtonClicked();
    }
}
