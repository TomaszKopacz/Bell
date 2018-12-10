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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.adam.myapplication.R;
import com.example.adam.myapplication.app.App;
import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskArrayAdapter;
import com.example.adam.myapplication.data.TaskRepository;
import com.example.adam.myapplication.newtaskwindow.AddTaskActivity;
import com.example.adam.myapplication.utils.DatetimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.adam.myapplication.data.Task.MEASUREMENT_PRESSURE;
import static com.example.adam.myapplication.data.Task.MEASUREMENT_TEMPERATURE;

public class MainFragment extends Fragment {

    private SwipeMenuListView list;
    private SwipeMenuCreator creator;
    private TextView d_m;
    private TextView year;
    private FloatingActionButton plus;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    public MainFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        getLayoutViews(view);
        createSwipeList();
        setListeners();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayCurrentDay();
    }

    private void getLayoutViews(View view) {
        list = (SwipeMenuListView) view.findViewById(R.id.lista);
        d_m = view.findViewById(R.id.d_m);
        year = view.findViewById(R.id.rok);
        plus = view.findViewById(R.id.fab);
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

        d_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatetimePicker.showDatePicker(getActivity(), dateSetListener);
            }
        });

        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatetimePicker.showDatePicker(getActivity(), dateSetListener);
            }
        });

        list.setLongClickable(true);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Task t = (Task) list.getAdapter().getItem(position);
                if(t.getType().equals("TEMPERATURE") || t.getType().equals("PRESSURE")) {
                    new InputDialog(getActivity()).inputDialog().show();
                    return true;
                }
                else
                    return false;
            }
        });
        list.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // delete
                        // TUTAJ USUWANIE
                        break;
                }
                // false : close the menu; true : not close the menu
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
        repository.getAllFromDate(date).observe((LifecycleOwner) getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                createList(tasks);
            }
        });
    }

    public void createList(List<Task> tasks) {
        TaskArrayAdapter adapter = new TaskArrayAdapter(getActivity(), tasks);
        list.setAdapter(adapter);
    }

    public void createSwipeList()
    {
         creator = new SwipeMenuCreator() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void create(SwipeMenu menu) {

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
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
}

