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
import com.example.adam.myapplication.data.AnatomyLimits;
import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;
import com.example.adam.myapplication.newtaskwindow.AddTaskActivity;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/**
 * Class which creates a Fragment obcject for mainWindow
 */
public class MainFragment extends Fragment {
    /**
     * Function gets List displayed list of tasks
     * @return list
     */
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
    /**
     * Function gets current displayed day on the screen
     * @return displayedDay
     */
    public Calendar getDisplayedDay() {
        return displayedDay;
    }

    private Calendar displayedDay;

    private DatePickerDialog.OnDateSetListener dateSetListener;
    /**
     * Constructor
     */
    public MainFragment() {

    }
    /**
     * Function called while the fragment is displayed.
     * It sets all views and sets up listeners for touch screen actions
     * @param inflater LayoutInflater object
     * @param container ViewGroup object
     * @param savedInstanceState Bundle object
     * @return view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        getLayoutViews(view);
        setListeners();
        return view;
    }
    /**
     * Function called when the view is created
     * It displayes all tasks for a current day and create list of tasks
     * @param view view Object
     * @param savedInstanceState Bundle object
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayCurrentDay();
        createSwipeList();
    }

    private void getLayoutViews(View view) {
        list = view.findViewById(R.id.lista);
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
                if (!(mainFragment.getDisplayedDay().after(Calendar.getInstance()))) {
                    Task t = (Task) list.getAdapter().getItem(position);
                    switch (t.getType()) {

                        case "TEMPERATURE":
                            inputDialog = new InputDialog(getActivity());
                            inputDialog.inputTemperatureDialog(mainFragment, position).show();
                            break;

                        case "PRESSURE":
                            inputDialog = new InputDialog(getActivity());
                            inputDialog.inputPressureDialog(mainFragment, position).show();
                            break;

                        case "DRUG":
                        case "EXAMINATION":
                            new DoneDialog(getActivity()).doneDialog(mainFragment, position).show();
                            break;
                    }

                } else {
                    new DoneDialog(getActivity()).doneDialogWrongDate().show();
                }

                return true;
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
        displayedDay = Calendar.getInstance();
        displayDay(displayedDay);
    }

    private void displayDay(Calendar calendar) {
        displayedDay = calendar;
        displayDayText(displayedDay);
        displayDayTasks(displayedDay);
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

    public void setTemperatureResult(int position, double result) {
        Task task = (Task) list.getAdapter().getItem(position);
        if (result < AnatomyLimits.LIMIT_MIN_TEMPERATURE
                || result > AnatomyLimits.LIMIT_MAX_TEMPERATURE) {

            new InputDialog(getActivity()).errorTemperatureDialog().show();

        }  else {
            task.setResult(result);
            task.setStatus(true);

            TaskRepository repository = ((App) getActivity().getApplication()).getTaskRepository();
            repository.update(task);
        }
    }

    public void setPressureResult(int position, double result1, double result2) {
        Task task = (Task) list.getAdapter().getItem(position);

        if (result1 < AnatomyLimits.LIMIT_MIN_PRESSURE
                || result2 < AnatomyLimits.LIMIT_MIN_PRESSURE
                || result1 > AnatomyLimits.LIMIT_MAX_PRESSURE
                || result2 > AnatomyLimits.LIMIT_MAX_PRESSURE) {

            new InputDialog(getActivity()).errorPressureDialog().show();

        } else {
            task.setResult(result1);
            task.setResult2(result2);
            task.setStatus(true);

            TaskRepository repository = ((App) getActivity().getApplication()).getTaskRepository();
            repository.update(task);
        }
    }

    public void setStatus(int position, boolean status) {
        Task task = (Task) list.getAdapter().getItem(position);
        task.setStatus(status);

        TaskRepository repository = ((App) getActivity().getApplication()).getTaskRepository();
        repository.update(task);
    }
}

