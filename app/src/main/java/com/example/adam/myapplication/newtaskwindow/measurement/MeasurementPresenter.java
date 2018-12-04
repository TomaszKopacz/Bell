package com.example.adam.myapplication.newtaskwindow.measurement;

import android.support.annotation.NonNull;

import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;

public class MeasurementPresenter implements MeasurementContract.MeasurementPresenter {

    private MeasurementContract.MeasurementView view;
    private TaskRepository repository;

    MeasurementPresenter(MeasurementContract.MeasurementView view, TaskRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void onSubmitButtonClicked() {
        Task task = createTask();
        repository.insert(task);
    }

    @NonNull
    private Task createTask() {
        String type = view.getType();
        String hour = view.getHour();
        String date = view.getDate();
        String unit = view.getUnit();

        Task task = new Task(type, date, hour);
        task.setUnit(unit);

        return task;
    }
}
