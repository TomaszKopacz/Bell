package com.example.adam.myapplication.ui.drugs.drugs_task_tab;

import com.example.adam.myapplication.data.objects.Task;

public interface DrugTaskContract {

    interface DrugTaskView {

        String SUCCESS = "success";
        String FAILURE = "failure";

        String getDrug();

        String getDose();

        String getTime();

        String getDate();

        String getEndDate();

        boolean isCycle();

        void setDrug(String drug);

        void setDose(String dose);

        void setTime(String time);

        void setDate(String date);

        void setEndDate(String endDate);

        void onTaskCreated(String status, Task task);

        void showError(String error);

        void navigateToParentView();
    }

    interface DrugTaskPresenter {
        void onSubmitButtonClicked();
    }
}
