package com.example.adam.myapplication.ui.scores.scores_task_tab;

import android.support.annotation.NonNull;

import com.example.adam.myapplication.data.objects.Task;
import com.example.adam.myapplication.data.db.task.TaskRepository;
import com.example.adam.myapplication.exceptions.TaskException;
import com.example.adam.myapplication.utils.Formatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScoresTaskPresenter implements ScoresTaskContract.ScoresTaskPresenter {

    private ScoresTaskContract.ScoresTaskView view;
    private TaskRepository repository;

    private static final String EMPTY_TASK_TIME = "Wprowadź godzinę!";
    private static final String EMPTY_TASK_DATE = "Wprowadź datę!";
    private static final String EMPTY_TASK_END_DATE = "Wprowadź datę zakończnia cyklu!";
    private static final String DATES_INCORRECT_ORDER = "Data końca cyklu musi być późniejsza niż jego start!";

    ScoresTaskPresenter(ScoresTaskContract.ScoresTaskView view, TaskRepository repository) {
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

        if (view.isCycle()) {
            if (view.getEndDate().isEmpty())
                throw new TaskException(EMPTY_TASK_END_DATE);

            Date currentDate = Formatter.getTimestamp(view.getDate(), view.getTime());
            Date endDate = Formatter.getTimestamp(view.getEndDate(), view.getTime());

            if (currentDate.after(endDate))
                throw new TaskException(DATES_INCORRECT_ORDER);
        }
    }

    private void insertSingleTask() throws ParseException {
        Task task = getTaskFromLayout();
        view.onTaskCreated(ScoresTaskContract.ScoresTaskView.SUCCESS, task);
        repository.insert(task);
    }

    private void insertManyTasks() throws ParseException {
        List<Task> tasks = getCyclicTasks();

        for (Task task : tasks)
            view.onTaskCreated(ScoresTaskContract.ScoresTaskView.SUCCESS, task);

        repository.insert(tasks);
    }

    @NonNull
    private Task getTaskFromLayout() throws ParseException {
        String type = view.getType();
        String time = view.getTime();
        String date = view.getDate();

        Date timestamp = Formatter.getTimestamp(date, time);

        return new Task(type, timestamp);
    }

    private List<Task> getCyclicTasks() throws ParseException {
        Date currentDate = Formatter.getTimestamp(view.getDate(), view.getTime());
        Date endDate =  Formatter.getTimestamp(view.getEndDate(), view.getTime());

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
