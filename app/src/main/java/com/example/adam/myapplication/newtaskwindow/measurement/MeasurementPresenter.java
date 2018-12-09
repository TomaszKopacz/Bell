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
        insertTasks();
    }

    private void insertTasks() {
        try {
            Task task = getTaskFromLayout();

            if (!view.isCycle())
                makeOneTask(task);
            else
                makeManyTasks(task);

            view.navigateToParentView();

        } catch (ParseException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
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

    private void makeOneTask(Task task) {
        repository.insert(task);
        view.onTaskCreated(MeasurementContract.MeasurementView.SUCCESS, task);
    }

    private void makeManyTasks(Task task) throws ParseException, InterruptedException {
        Date currentDate = getCurrentDate();
        Date endDate = getEndDate(currentDate);

        for (; currentDate.before(endDate) || currentDate.equals(endDate); ) {
            Thread.sleep(100);

            task.setTimestamp(currentDate);

            makeOneTask(task);

            incrementDay(currentDate);
        }
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
