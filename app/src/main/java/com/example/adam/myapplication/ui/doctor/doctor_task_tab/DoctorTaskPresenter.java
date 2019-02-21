package com.example.adam.myapplication.ui.doctor.doctor_task_tab;

import android.arch.lifecycle.LiveData;

import com.example.adam.myapplication.data.db.doctor.DoctorRepository;
import com.example.adam.myapplication.data.objects.Doctor;
import com.example.adam.myapplication.data.objects.Task;
import com.example.adam.myapplication.data.db.task.TaskRepository;
import com.example.adam.myapplication.exceptions.TaskException;
import com.example.adam.myapplication.utils.DatetimeFormatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DoctorTaskPresenter implements DoctorTaskContract.DoctorTaskPresenter {

    private DoctorTaskContract.DoctorTaskView view;
    private TaskRepository taskRepository;
    private DoctorRepository doctorRepository;

    private static final String EMPTY_DOCTOR_NAME = "Wprowadź nazwisko lekarza!";
    private static final String EMPTY_TASK_TIME = "Wprowadź godzinę!";
    private static final String EMPTY_TASK_DATE = "Wprowadź datę!";
    private static final String EMPTY_TASK_END_DATE = "Wprowadź datę zakończnia cyklu!";
    private static final String DATES_INCORRECT_ORDER = "Data końca cyklu musi być późniejsza niż jego start!";

    DoctorTaskPresenter(DoctorTaskContract.DoctorTaskView view,
                        TaskRepository taskRepository,
                        DoctorRepository doctorRepository) {
        this.view = view;
        this.taskRepository = taskRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public void onViewAttached() {
        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String time = DatetimeFormatter.getTimeFormatted(hour, minute);
        String date = DatetimeFormatter.getDateFormatted(day, month, year);

        view.setTime(time);
        view.setDate(date);
        view.setEndDate(date);
    }

    @Override
    public void onDoctorViewClicked() {
        List<Doctor> doctors = doctorRepository.getAll();
        view.showChooseDoctorDialog(doctors);
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
//        view.onTaskCreated(ScoresTaskContract.ScoresTaskView.SUCCESS, task);
        taskRepository.insert(task);
    }

    private void insertManyTasks() throws ParseException {
        List<Task> tasks = getCyclicTasks();

//        for (Task task : tasks)
//            view.onTaskCreated(ScoresTaskContract.ScoresTaskView.SUCCESS, task);

        taskRepository.insert(tasks);
    }

    private Task getTaskFromLayout() throws ParseException {
        String type = Task.DOCTOR;
        String time = view.getTime();
        String date = view.getDate();
        String info = view.getDoctor();

        Date timestamp = DatetimeFormatter.getTimestamp(date, time);

        Task task = new Task(type, timestamp);
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
