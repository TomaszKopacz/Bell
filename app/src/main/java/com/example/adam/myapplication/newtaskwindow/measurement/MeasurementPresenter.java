package com.example.adam.myapplication.newtaskwindow.measurement;

import android.support.annotation.NonNull;

import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;
import com.example.adam.myapplication.utils.DatetimeFormatter;

import java.text.ParseException;
import java.util.Date;

public class MeasurementPresenter implements MeasurementContract.MeasurementPresenter {

    private MeasurementContract.MeasurementView view;
    private TaskRepository repository;

    MeasurementPresenter(MeasurementContract.MeasurementView view, TaskRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void onSubmitButtonClicked() {
        Task task;

        try {
            task = createTask();
            repository.insert(task);
            view.onTaskCreated(MeasurementContract.MeasurementView.SUCCESS, task);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private Task createTask() throws ParseException {
        String type = view.getType();
        String time = view.getHour();
        String date = view.getDate();
        String unit = view.getUnit();

        Date timestamp = DatetimeFormatter.getTimestamp(date, time);

        Task task = new Task(type, timestamp);
        task.setUnit(unit);

        return task;
    }
}
