package com.example.adam.myapplication.newtaskwindow.drug;

import android.support.annotation.NonNull;

import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;

public class DrugPresenterImpl implements DrugPresenter{

    private DrugView view;
    private TaskRepository repository;

    public DrugPresenterImpl(DrugView view, TaskRepository repository){
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
        String type = Task.DRUG;
        String hour = view.getTime();
        String date = view.getDate();
        String drug = view.getDrug();
        String dose = view.getDose();

        Task task = new Task(type, date, hour);
        task.setDrugName(drug);
        task.setDose(Double.parseDouble(dose));

        return task;
    }
}
