package com.example.adam.myapplication.newtaskwindow.measurement;

import android.support.annotation.NonNull;

import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;
import com.example.adam.myapplication.utils.DatetimeFormatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeasurementPresenter implements MeasurementContract.MeasurementPresenter {

    private MeasurementContract.MeasurementView view;
    private TaskRepository repository;

    MeasurementPresenter(MeasurementContract.MeasurementView view, TaskRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void onSubmitButtonClicked() {
        insertTasks();
    }

    private void insertTasks() {
        try {
            if (!view.isCycle()) {
                Task task = getTaskFromLayout();
                view.onTaskCreated(MeasurementContract.MeasurementView.SUCCESS, task);
                repository.insert(task);

            } else {
                List<Task> tasks = getCyclicTasks();

                for (Task task : tasks)
                    view.onTaskCreated(MeasurementContract.MeasurementView.SUCCESS, task);

                repository.insert(tasks);
            }

            view.navigateToParentView();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private Task getTaskFromLayout() throws ParseException {
        String type = view.getType();
        String time = view.getHour();
        String date = view.getDate();
        String unit = view.getUnit();

        Date timestamp = DatetimeFormatter.getTimestamp(date, time);

        Task task = new Task(type, timestamp);
        task.setUnit(unit);

        return task;
    }

    private List<Task> getCyclicTasks() throws ParseException {
        Date currentDate = getCurrentDate();
        Date endDate = getEndDate(currentDate);

        List<Task> tasks = new ArrayList<>();

        for (; currentDate.before(endDate) || currentDate.equals(endDate); ) {
            Task task = getTaskFromLayout();

            Date timestamp = new Date();
            timestamp.setTime(currentDate.getTime());
            task.setTimestamp(timestamp);

            tasks.add(task);

            incrementDay(currentDate);
        }

        return tasks;
    }

    private Date getCurrentDate() throws ParseException {
        return DatetimeFormatter.getTimestamp(view.getDate(), view.getHour());
    }

    private Date getEndDate(Date startDate) throws ParseException {
        return view.isCycle() ?
                DatetimeFormatter.getTimestamp(view.getEndDate(), view.getHour()) : startDate;
    }

    private void incrementDay(Date date){
        date.setTime(date.getTime() + (1000 * 60 * 60 * 24));
    }
}
