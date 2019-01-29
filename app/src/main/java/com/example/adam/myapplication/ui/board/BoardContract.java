package com.example.adam.myapplication.ui.board;

import android.app.DatePickerDialog;
import android.arch.lifecycle.LiveData;

import com.example.adam.myapplication.data.Task;

import java.util.Calendar;
import java.util.List;

public interface BoardContract {

    interface BoardView {
        void displayDate(Calendar calendar);
        void showList(LiveData<List<Task>> liveTasks);
        Task getTaskFromPosition(int position);
        void showCalendarView(DatePickerDialog.OnDateSetListener listener);
        void showNewTaskView();
    }

    interface BoardPresenter {
        void onViewAttached();
        void onItemClicked(int position);
        void calendarButtonClicked();
        void addButtonClicked();
    }
}
