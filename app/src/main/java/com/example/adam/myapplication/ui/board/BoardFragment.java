package com.example.adam.myapplication.ui.board;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.adam.myapplication.R;
import com.example.adam.myapplication.app.App;
import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;
import com.example.adam.myapplication.ui.newtask.types.AddTaskActivity;
import com.example.adam.myapplication.utils.DatetimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class BoardFragment extends Fragment implements BoardContract.BoardView {

    private BoardPresenter presenter;

    private TextView dayOfMonthLabel;
    private TextView yearLabel;

    private View emptyListView;
    private SwipeMenuListView listView;

    private FloatingActionButton calendarButton;
    private FloatingActionButton addButton;

    public BoardFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        getComponents(view);
        setPresenter();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.onViewAttached();
    }

    private void getComponents(View view) {
        listView = view.findViewById(R.id.lista);
        dayOfMonthLabel = view.findViewById(R.id.d_m);
        yearLabel = view.findViewById(R.id.rok);
        calendarButton = view.findViewById(R.id.fab_calendar);
        addButton = view.findViewById(R.id.fab_add);
        emptyListView = view.findViewById(R.id.no_tasks_view);
    }

    private void setPresenter() {
        TaskRepository repository = ((App) getActivity().getApplication()).getTaskRepository();
        this.presenter = new BoardPresenter(this, repository);

        setListeners();
    }

    private void setListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.onItemClicked(position);
            }
        });

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.calendarButtonClicked();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addButtonClicked();
            }
        });
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
    public void showList(LiveData<List<Task>> liveTasks) {
        liveTasks.observe((LifecycleOwner) getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                makeView(tasks);
            }
        });
    }

    @Override
    public Task getTaskFromPosition(int position) {
        return (Task) listView.getAdapter().getItem(position);
    }

    @Override
    public void showCalendarView(DatePickerDialog.OnDateSetListener listener) {
        DatetimePicker.showDatePicker(getActivity(), listener);
    }

    @Override
    public void showNewTaskView() {
        Intent intent = new Intent(getActivity(), AddTaskActivity.class);
        startActivity(intent);
    }

    private void makeView(List<Task> tasks){
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
        TaskArrayAdapter adapter = new TaskArrayAdapter(getActivity(), tasks);
        listView.setAdapter(adapter);
    }
}

