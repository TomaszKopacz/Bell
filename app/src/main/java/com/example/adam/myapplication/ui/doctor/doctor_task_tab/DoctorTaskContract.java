package com.example.adam.myapplication.ui.doctor.doctor_task_tab;

import com.example.adam.myapplication.data.Task;

public interface DoctorTaskContract {

    interface DoctorTaskView {

        String SUCCESS = "success";
        String FAILURE = "failure";

        String getDoctor();

        String getLocation();

        String getInfo();

        String getTime();

        String getDate();

        String getEndDate();

        boolean isCycle();

        void setDoctor(String doctor);

        void setLocation(String location);

        void setInfo(String info);

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
