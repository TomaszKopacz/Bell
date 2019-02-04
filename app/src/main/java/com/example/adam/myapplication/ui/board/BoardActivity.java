package com.example.adam.myapplication.ui.board;

import android.app.DatePickerDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.app.App;
import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;
import com.example.adam.myapplication.ui.chart.ChartActivity;
import com.example.adam.myapplication.ui.newtask.types.AddTaskActivity;
import com.example.adam.myapplication.utils.DatetimePicker;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BoardActivity extends AppCompatActivity implements BoardContract.BoardView {

    private BoardPresenter presenter;

    private Toolbar toolbar;

    private TextView dayOfMonthLabel;
    private TextView yearLabel;

    private View emptyListView;
    private RecyclerView listView;

    private FloatingActionButton calendarButton;
    private FloatingActionButton addButton;

    private List<Task> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_board);

        setLayout();
        setPresenter();
    }

    private void setLayout() {
        getAllComponents();
        createToolbar();
        setUpList();
    }

    private void getAllComponents() {
        toolbar = findViewById(R.id.toolbar);

        dayOfMonthLabel = findViewById(R.id.day_month_label);
        yearLabel = findViewById(R.id.year_label);

        emptyListView = findViewById(R.id.no_tasks_view);
        listView = findViewById(R.id.list_view);

        calendarButton = findViewById(R.id.fab_calendar);
        addButton = findViewById(R.id.fab_add);
    }

    private void createToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("Kalendarz pacjenta");
    }

    private void setUpList() {
        listView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setPresenter() {
        TaskRepository repository = ((App) getApplication()).getTaskRepository();
        this.presenter = new BoardPresenter(this, repository);

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

        presenter.onViewAttached();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_chart) {
            showChart();
            return true;

        } else if (id == R.id.action_info) {
            showInfo();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showInfo() {
        AppInfoDialog appInfoDialog = new AppInfoDialog(this);
        appInfoDialog.infoDialog().show();
    }

    private void showChart() {
        Intent intent = new Intent(this, ChartActivity.class);
        startActivity(intent);
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
        liveTasks.observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {

                updateTasksList(tasks);
                generateList(tasks);
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
        DatetimePicker.showDatePicker(this, listener);
    }

    @Override
    public void showTaskCreationView() {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
    }

    private void updateTasksList(List<Task> tasks) {
        this.tasks = tasks;
    }

    private void generateList(List<Task> tasks) {
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
