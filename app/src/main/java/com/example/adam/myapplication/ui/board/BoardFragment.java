package com.example.adam.myapplication.ui.board;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.data.db.task.TaskRepository;
import com.example.adam.myapplication.data.objects.Task;
import com.example.adam.myapplication.ui.lists.TaskAdapter;
import com.example.adam.myapplication.utils.Picker;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BoardFragment extends Fragment implements BoardContract.BoardView {

    private BoardPresenter presenter;

    private TextView dayOfMonthLabel;
    private TextView yearLabel;

    private View emptyListView;
    private RecyclerView listView;

    private FloatingActionButton calendarButton;

    private List<Task> tasks = new ArrayList<>();

    public BoardFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_board, container, false);
        setLayout(view);

        return view;
    }

    private void setLayout(View view) {
        getAllComponents(view);
        setUpList();
    }

    private void getAllComponents(View view) {

        dayOfMonthLabel = view.findViewById(R.id.day_month_label);
        yearLabel = view.findViewById(R.id.year_label);

        emptyListView = view.findViewById(R.id.no_tasks_view);
        listView = view.findViewById(R.id.list_view);

        calendarButton = view.findViewById(R.id.fab_calendar);
    }

    private void setUpList() {
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setPresenter();
    }

    private void setPresenter() {
        Activity parent = getActivity();

        if (parent != null) {
            TaskRepository repository = new TaskRepository(getActivity().getApplication());
            this.presenter = new BoardPresenter(this, repository);

            setListeners();
        }

    }

    private void setListeners() {
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.calendarButtonClicked();
            }
        });

        presenter.onViewAttached();
    }

    @Override
    public void displayDate(Calendar calendar) {
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        String currentYear = currentDate.substring(currentDate.length() - 4);
        String currentDayOfMonth = currentDate.substring(0, currentDate.length() - 5);

        dayOfMonthLabel.setText(currentDayOfMonth);
        yearLabel.setText(currentYear);
    }

    @Override
    public void displayList(LiveData<List<Task>> liveTasks) {
        liveTasks.observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                updateTasksList(tasks);
                showList(tasks);
            }
        });
    }

    @Override
    public Task getTaskFromPosition(int position) {
        Task task;

        try {
            task = tasks.get(position);
            return task;

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void showCalendarView(DatePickerDialog.OnDateSetListener listener) {
        Picker.showDatePicker(getActivity(), listener);
    }

    private void updateTasksList(List<Task> tasks) {
        this.tasks = tasks;
    }

    private void showList(List<Task> tasks) {
        showViewForEmptyBoard(tasks.isEmpty());
        resetAdapter(tasks);
    }

    private void showViewForEmptyBoard(boolean b) {
        if (b)
            emptyListView.setVisibility(View.VISIBLE);
        else
            emptyListView.setVisibility(View.INVISIBLE);
    }

    private void resetAdapter(List<Task> tasks) {
        TaskAdapter adapter = new TaskAdapter(tasks);
        listView.setAdapter(adapter);
    }
}
