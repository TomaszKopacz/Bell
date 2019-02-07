package com.example.adam.myapplication.ui.doctor.doctor_task_tab;

import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;
import com.example.adam.myapplication.ui.newtask.exceptions.TaskException;
import com.example.adam.myapplication.ui.newtask.measurement.MeasurementContract;
import com.example.adam.myapplication.utils.DatetimeFormatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DoctorTaskPresenter implements DoctorTaskContract.DoctorTaskPresenter {

    private DoctorTaskContract.DoctorTaskView view;
    private TaskRepository repository;

    private static final String EMPTY_DOCTOR_NAME = "Wprowadź nazwisko lekarza!";
    private static final String EMPTY_TASK_TIME = "Wprowadź godzinę!";
    private static final String EMPTY_TASK_DATE = "Wprowadź datę!";
    private static final String EMPTY_TASK_END_DATE = "Wprowadź datę zakończnia cyklu!";
    private static final String DATES_INCORRECT_ORDER = "Data końca cyklu musi być późniejsza niż jego start!";

    DoctorTaskPresenter(DoctorTaskContract.DoctorTaskView view, TaskRepository repository) {
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

    private void insertTasks() throws TaskException, ParseException {
        checkFields();

        if (!view.isCycle())
            insertSingleTask();
        else
            insertManyTasks();

        view.navigateToParentView();
    }

    private void checkFields() throws TaskException, ParseException {
        if (view.getDoctor().isEmpty())
            throw new TaskException(EMPTY_DOCTOR_NAME);

        if (view.getTime().isEmpty())
            throw new TaskException(EMPTY_TASK_TIME);

        if (view.getDate().isEmpty())
            throw new TaskException(EMPTY_TASK_DATE);

        if (view.isCycle()) {
            if (view.getEndDate().isEmpty())
                throw new TaskException(EMPTY_TASK_END_DATE);

            Date currentDate = DatetimeFormatter.getTimestamp(view.getDate(), view.getTime());
            Date endDate = DatetimeFormatter.getTimestamp(view.getEndDate(), view.getTime());

            if (currentDate.after(endDate))
                throw new TaskException(DATES_INCORRECT_ORDER);
        }
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

    private List<Task> getCyclicTasks() throws ParseException {
        Date currentDate = DatetimeFormatter.getTimestamp(view.getDate(), view.getTime());
        Date endDate = DatetimeFormatter.getTimestamp(view.getEndDate(), view.getTime());

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
