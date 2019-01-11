package com.example.adam.myapplication.newtaskwindow.measurement;

import android.support.annotation.NonNull;

import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;
import com.example.adam.myapplication.newtaskwindow.TaskException;
import com.example.adam.myapplication.utils.DatetimeFormatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeasurementPresenter implements MeasurementContract.MeasurementPresenter {

    private MeasurementContract.MeasurementView view;
    private TaskRepository repository;

    private static final String EMPTY_TASK_TIME = "Wprowadź godzinę!";
    private static final String EMPTY_TASK_DATE = "Wprowadź datę!";
    private static final String EMPTY_TASK_END_DATE = "Wprowadź datę zakończnia cyklu!";
    private static final String DATES_INCORRECT_ORDER = "Data końca cyklu musi być późniejsza niż jego start!";

    MeasurementPresenter(MeasurementContract.MeasurementView view, TaskRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void onSubmitButtonClicked() {
        try {
            insertTasks();

        } catch (Exception e) {
            view.showError(e.getMessage());
        }
    }

    private void insertTasks() throws ParseException, TaskException {
        checkFields();

        if (!view.isCycle())
            insertSingleTask();
        else
            insertManyTasks();

        view.navigateToParentView();
    }

    private void checkFields() throws TaskException, ParseException {
        if (view.getTime().isEmpty())
            throw new TaskException(EMPTY_TASK_TIME);

        if (view.getDate().isEmpty())
            throw new TaskException(EMPTY_TASK_DATE);

        if (view.isCycle() && view.getEndDate().isEmpty())
            throw new TaskException(EMPTY_TASK_END_DATE);

        Date currentDate = DatetimeFormatter.getTimestamp(view.getDate(), view.getTime());
        Date endDate = DatetimeFormatter.getTimestamp(view.getEndDate(), view.getTime());
        if (currentDate.after(endDate))
            throw new TaskException(DATES_INCORRECT_ORDER);
    }

    private void insertSingleTask() throws ParseException {
        Task task = getTaskFromLayout();
        view.onTaskCreated(MeasurementContract.MeasurementView.SUCCESS, task);
        repository.insert(task);
    }

    private void insertManyTasks() throws ParseException {
        List<Task> tasks = getCyclicTasks();

        for (Task task : tasks)
            view.onTaskCreated(MeasurementContract.MeasurementView.SUCCESS, task);

        repository.insert(tasks);
    }

    @NonNull
    private Task getTaskFromLayout() throws ParseException {
        String type = view.getType();
        String time = view.getTime();
        String date = view.getDate();
        String unit = view.getUnit();

        Date timestamp = DatetimeFormatter.getTimestamp(date, time);

        Task task = new Task(type, timestamp);
        task.setUnit(unit);

        return task;
    }

    private List<Task> getCyclicTasks() throws ParseException {
        Date currentDate = DatetimeFormatter.getTimestamp(view.getDate(), view.getTime());
        Date endDate =  DatetimeFormatter.getTimestamp(view.getEndDate(), view.getTime());

        List<Task> tasks = new ArrayList<>();

        while (currentDate.before(endDate) || currentDate.equals(endDate)) {
            Task task = getTaskFromLayout();

            Date timestamp = new Date();
            timestamp.setTime(currentDate.getTime());
            task.setTimestamp(timestamp);

            tasks.add(task);

            incrementDay(currentDate);
        }

        return tasks;
    }

    private void incrementDay(Date date) {
        date.setTime(date.getTime() + (1000 * 60 * 60 * 24));
    }
}
