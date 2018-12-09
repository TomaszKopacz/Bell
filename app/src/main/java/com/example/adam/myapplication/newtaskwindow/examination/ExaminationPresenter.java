package com.example.adam.myapplication.newtaskwindow.examination;

import android.support.annotation.NonNull;

import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;
import com.example.adam.myapplication.newtaskwindow.measurement.MeasurementContract;
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
        return DatetimeFormatter.getTimestamp(view.getDate(), view.getTime());
    }

    private Date getEndDate(Date startDate) throws ParseException {
        return view.isCycle() ?
                DatetimeFormatter.getTimestamp(view.getEndDate(), view.getTime()) : startDate;
    }

    private void incrementDay(Date date){
        date.setTime(date.getTime() + (1000 * 60 * 60 * 24));
    }
}
