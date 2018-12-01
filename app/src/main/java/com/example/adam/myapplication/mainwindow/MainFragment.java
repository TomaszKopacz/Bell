package com.example.adam.myapplication.mainwindow;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.app.App;
import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskArrayAdapter;
import com.example.adam.myapplication.data.TaskRepository;
import com.example.adam.myapplication.newtaskwindow.AddTaskActivity;
import com.example.adam.myapplication.utils.DatetimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class MainFragment extends Fragment {

    //ZMIENNE
    double input_value = 0;
    private ListView list;
    private TextView d_m;
    private TextView year;
    private FloatingActionButton plus;
    private static TaskArrayAdapter adapter;

    public MainActivity getmFragAct() {
        return mFragAct;
    }

    private MainActivity mFragAct;
    private Intent mIntent;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    public MainFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        getLayoutViews(view);
        setListeners();
        setCurrentDate();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFragAct = (MainActivity) getActivity();
        mIntent = mFragAct.getIntent();
        downloadTasks();
    }

    private void getLayoutViews(View view) {
        list = (ListView) view.findViewById(R.id.lista);
        d_m = (TextView) view.findViewById(R.id.d_m);
        year = (TextView) view.findViewById(R.id.rok);
        plus = (FloatingActionButton) view.findViewById(R.id.fab);
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
                //dateText.setText(DatetimeFormatter.getDateFormatted(year, month, day));
            }
        };

        d_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatetimePicker.showDatePicker(mFragAct, dateSetListener);
            }
        });
        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatetimePicker.showDatePicker(mFragAct, dateSetListener);
            }
        });

        list.setLongClickable(true);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new InputDialog(mFragAct.getMainFragment()).inputDialog().show();
                return true;
            }
        });
    }

    private void startAddTaskActivity() {
        Intent intent = new Intent(mFragAct, AddTaskActivity.class);
        startActivity(intent);
    }

    public void downloadTasks() {
        TaskRepository repository = ((App) mFragAct.getApplication()).getTaskRepository();
        repository.gatAll().observe(mFragAct, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                createList(tasks);
            }
        });
    }

    public void createList(List<Task> tasks) {
        adapter = new TaskArrayAdapter(mFragAct.getApplicationContext(), tasks);
        list.setAdapter(adapter);
    }

    private void setCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        String currentYear = currentDate.substring(currentDate.length() - 4, currentDate.length());
        currentDate = currentDate.substring(0, currentDate.length() - 6);
        d_m.setText(currentDate);
        year.setText(currentYear);
    }
}
