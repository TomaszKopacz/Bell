package com.example.adam.myapplication.newtaskwindow.drug;

import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;
import com.example.adam.myapplication.utils.DatetimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DrugPresenter implements DrugContract.DrugPresenter {

    private DrugContract.DrugView view;
    private TaskRepository repository;

    DrugPresenter(DrugContract.DrugView view, TaskRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void onSubmitButtonClicked() {
        Task task;

        try {
            task = createTask();
            repository.insert(task);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private Task createTask() throws ParseException {
        String type = Task.DRUG;
        String time = view.getTime();
        String date = view.getDate();
        String drug = view.getDrug();
        String dose = view.getDose();

        Date timestamp = DatetimeFormatter.getTimestamp(date, time);

        Task task = new Task(type, timestamp);
        task.setDrugName(drug);
        task.setDose(Double.parseDouble(dose));

        return task;
    }
}
