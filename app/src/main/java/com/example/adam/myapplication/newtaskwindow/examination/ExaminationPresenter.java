package com.example.adam.myapplication.newtaskwindow.examination;

import android.support.annotation.NonNull;

import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;
import com.example.adam.myapplication.utils.DatetimeFormatter;

import java.text.ParseException;
import java.util.Date;

public class ExaminationPresenter implements ExaminationContract.ExaminationPresenter {

    private ExaminationContract.ExaminationView view;
    private TaskRepository repository;

    ExaminationPresenter(ExaminationContract.ExaminationView view, TaskRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void onSubmitButtonClicked() {
        Task task;

        try {
            task = createTask();
            repository.insert(task);
            view.onTaskCreated(ExaminationContract.ExaminationView.SUCCESS, task);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private Task createTask() throws ParseException {
        String type = Task.EXAMINATION;
        String time = view.getTime();
        String date = view.getDate();
        String doctor = view.getDoctor();
        String location = view.getLocation();
        String info = view.getInfo();

        Date timestamp = DatetimeFormatter.getTimestamp(date, time);

        Task task = new Task(type, timestamp);
        task.setDoctor(doctor);
        task.setLocation(location);
        task.setInfo(info);

        return task;
    }
}
