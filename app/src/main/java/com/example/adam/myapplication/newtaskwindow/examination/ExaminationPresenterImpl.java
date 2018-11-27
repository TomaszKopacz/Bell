package com.example.adam.myapplication.newtaskwindow.examination;

import android.support.annotation.NonNull;

import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;

public class ExaminationPresenterImpl implements ExaminationPresenter {

    private ExaminationView view;
    private TaskRepository repository;

    public ExaminationPresenterImpl(ExaminationView view, TaskRepository repository){
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void onSubmitButtonClicked() {
        Task task = createTask();
        repository.insert(task);

//        view.navigateToParentView();
        view.goToCalendar();
    }

    @NonNull
    private Task createTask() {
        String type = Task.EXAMINATION;
        String hour = view.getTime();
        String date = view.getDate();
        String doctor = view.getDoctor();
        String location = view.getLocation();
        String info = view.getInfo();

        Task task = new Task(type, date, hour);
        task.setDoctor(doctor);
        task.setLocation(location);
        task.setInfo(info);

        return task;
    }
}
