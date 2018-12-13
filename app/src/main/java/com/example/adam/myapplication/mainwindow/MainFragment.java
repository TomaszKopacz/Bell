package com.example.adam.myapplication.mainwindow;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.adam.myapplication.R;
import com.example.adam.myapplication.app.App;
import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;
import com.example.adam.myapplication.newtaskwindow.AddTaskActivity;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainFragment extends Fragment {

    public SwipeMenuListView getList() {
        return list;
    }

    private SwipeMenuListView list;
    private SwipeMenuCreator creator;
    private TextView d_m;
    private TextView year;
    private FloatingActionButton plus;
    private CheckBox checkBox;
    private InputDialog inputDialog;
    private MainFragment mainFragment = this;

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    private double result = 0;


    private DatePickerDialog.OnDateSetListener dateSetListener;

    public MainFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        getLayoutViews(view);
        setListeners();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayCurrentDay();
        createSwipeList();
    }

    private void getLayoutViews(View view) {
        list = (SwipeMenuListView) view.findViewById(R.id.lista);
        d_m = view.findViewById(R.id.d_m);
        year = view.findViewById(R.id.rok);
        plus = view.findViewById(R.id.fab);
        checkBox = view.findViewById(R.id.checkbox);
    }

    private void setListeners() {
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddTaskActivity();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                displayDay(calendar);
            }
        };

        list.setLongClickable(true);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Task t = (Task) list.getAdapter().getItem(position);
                if (t.getType().equals("TEMPERATURE") || t.getType().equals("PRESSURE")) {

                    inputDialog = new InputDialog(getActivity());
                    inputDialog.inputDialog(mainFragment, position).show();

                    return true;
                } else if (t.getType().equals("DRUG") || t.getType().equals("EXAMINATION")) {
                    new DoneDialog(getActivity()).doneDialog(mainFragment, position).show();
                    return true;
                }
                return false;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task t = (Task) list.getAdapter().getItem(position);
                new InfoDialog(getActivity()).infoDialog(t).show();
            }
        });

        list.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Task taskToDelete = (Task) list.getAdapter().getItem(position);
                        TaskRepository repository = ((App) getActivity().getApplication()).getTaskRepository();

                        repository.delete(taskToDelete);
                        break;
                }

                return false;
            }
        });
    }

    private void displayCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        displayDay(calendar);
    }

    private void displayDay(Calendar calendar) {
        displayDayText(calendar);
        displayDayTasks(calendar);
    }

    private void displayDayText(Calendar calendar) {
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        String currentYear = currentDate.substring(currentDate.length() - 4, currentDate.length());
        String currentDayOfMonth = currentDate.substring(0, currentDate.length() - 5);

        d_m.setText(currentDayOfMonth);
        year.setText(currentYear);
    }

    private void displayDayTasks(Calendar calendar) {
        downloadTasks(calendar.getTime());
    }

    private void startAddTaskActivity() {
        Intent intent = new Intent(getActivity(), AddTaskActivity.class);
        startActivity(intent);
    }

    public void downloadTasks(Date date) {
        TaskRepository repository = ((App) getActivity().getApplication()).getTaskRepository();

        Date start = getStartDate(date);
        Date end = getEndDate(date);

        repository.getAllFromDate(start, end).observe((LifecycleOwner) getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                createList(tasks);
            }
        });
    }

    private Date getStartDate(Date date) {
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return (Date) date.clone();
    }

    private Date getEndDate(Date date) {
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
        return (Date) date.clone();
    }

    public void createList(List<Task> tasks) {
        TaskArrayAdapter adapter = new TaskArrayAdapter(getActivity(), tasks);
        list.setAdapter(adapter);
    }

    public void createSwipeList() {
        creator = new SwipeMenuCreator() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void create(SwipeMenu menu) {

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        list.setMenuCreator(creator);
    }

    public DatePickerDialog.OnDateSetListener getDateSetListener() {
        return dateSetListener;
    }

    public void setResult(int position, double result) {
        Task task = (Task) list.getAdapter().getItem(position);
        task.setResult(result);
        task.setStatus(true);

        TaskRepository repository = ((App)getActivity().getApplication()).getTaskRepository();
        repository.update(task);
    }

    public void setStatus(int position, boolean status) {
        Task task = (Task) list.getAdapter().getItem(position);
        task.setStatus(status);

        TaskRepository repository = ((App)getActivity().getApplication()).getTaskRepository();
        repository.update(task);
    }
}

