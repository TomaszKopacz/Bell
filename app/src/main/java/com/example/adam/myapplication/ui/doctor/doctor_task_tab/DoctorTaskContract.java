package com.example.adam.myapplication.ui.doctor.doctor_task_tab;

import android.arch.lifecycle.LiveData;

import com.example.adam.myapplication.data.objects.Doctor;
import com.example.adam.myapplication.data.objects.Task;

import java.util.List;

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

        void showChooseDoctorDialog(List<Doctor> list);

        void onTaskCreated(String status, Task task);

        void showError(String error);

        void navigateToParentView();
    }

    interface DoctorTaskPresenter {
        void onViewAttached();
        void onDoctorViewClicked();
        void onSubmitButtonClicked();
    }
}
