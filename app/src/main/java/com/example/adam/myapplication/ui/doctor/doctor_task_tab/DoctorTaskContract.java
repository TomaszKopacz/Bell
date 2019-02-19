package com.example.adam.myapplication.ui.doctor.doctor_task_tab;

import com.example.adam.myapplication.data.objects.Task;

public interface DoctorTaskContract {

    interface DoctorTaskView {

        String SUCCESS = "success";
        String FAILURE = "failure";

        String getDoctor();

        String getTime();

        String getDate();

        String getEndDate();

        boolean isCycle();

        void setDoctor(String doctor);

        void setTime(String time);

        void setDate(String date);

        void setEndDate(String endDate);

        void onTaskCreated(String status, Task task);

        void showError(String error);

        void navigateToParentView();
    }

    interface DoctorTaskPresenter {
        void onSubmitButtonClicked();
    }
}
