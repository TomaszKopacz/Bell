package com.example.adam.myapplication.ui.board;

import android.app.DatePickerDialog;
import android.arch.lifecycle.LiveData;
import android.util.Log;
import android.widget.DatePicker;

import com.example.adam.myapplication.app.App;
import com.example.adam.myapplication.data.AnatomyLimits;
import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BoardPresenter implements BoardContract.BoardPresenter {

    private BoardContract.BoardView view;
    private TaskRepository repository;

    BoardPresenter(BoardContract.BoardView view, TaskRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void onViewAttached() {
        Calendar date = Calendar.getInstance();

        displayDay(date);
        displayDayTasks(date);
    }

    @Override
    public void onItemClicked(int position) {
        Task task = view.getTaskFromPosition(position);
    }

    @Override
    public void calendarButtonClicked() {
        view.showCalendarView(dateSetListener);
    }

    @Override
    public void addButtonClicked() {
        view.showNewTaskView();
    }

    private void displayDay(Calendar calendar) {
        view.displayDate(calendar);
    }

    private void displayDayTasks(Calendar calendar) {
        downloadTasks(calendar.getTime());
    }

    private void downloadTasks(Date date) {
        Date start = getStartOfDay(date);
        Date end = getEndOfDay(date);

        LiveData<List<Task>> tasks = repository.getAllFromDate(start, end);
        view.showList(tasks);
    }

    private Date getStartOfDay(Date date) {
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return (Date) date.clone();
    }

    private Date getEndOfDay(Date date) {
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
        return (Date) date.clone();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar date = Calendar.getInstance();
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, month);
            date.set(Calendar.DAY_OF_MONTH, day);

            displayDay(date);
            displayDayTasks(date);
        }
    };

//    public void setTemperatureResult(int position, double result) {
//        Task task = (Task) listView.getAdapter().getItem(position);
//        if (result < AnatomyLimits.LIMIT_MIN_TEMPERATURE
//                || result > AnatomyLimits.LIMIT_MAX_TEMPERATURE) {
//
//            new InputDialog(getActivity()).errorTemperatureDialog().show();
//
//        } else {
//            task.setResult(result);
//            task.setStatus(true);
//
//            TaskRepository repository = ((App) getActivity().getApplication()).getTaskRepository();
//            repository.update(task);
//        }
//    }
//
//    public void setPressureResult(int position, double result1, double result2) {
//        Task task = (Task) listView.getAdapter().getItem(position);
//
//        if (result1 < AnatomyLimits.LIMIT_MIN_PRESSURE
//                || result2 < AnatomyLimits.LIMIT_MIN_PRESSURE
//                || result1 > AnatomyLimits.LIMIT_MAX_PRESSURE
//                || result2 > AnatomyLimits.LIMIT_MAX_PRESSURE) {
//
//            new InputDialog(getActivity()).errorPressureDialog().show();
//
//        } else {
//            task.setResult(result1);
//            task.setResult2(result2);
//            task.setStatus(true);
//
//            TaskRepository repository = ((App) getActivity().getApplication()).getTaskRepository();
//            repository.update(task);
//        }
//    }
//
//    public void setStatus(int position, boolean status) {
//        Task task = (Task) listView.getAdapter().getItem(position);
//        task.setStatus(status);
//
//        TaskRepository repository = ((App) getActivity().getApplication()).getTaskRepository();
//        repository.update(task);
//    }
}