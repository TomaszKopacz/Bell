package com.example.adam.myapplication.chartwindow;


import android.app.Fragment;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.app.App;
import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChartFragment extends Fragment implements ChartContract.ChartView {

    private ChartPresenter presenter;

    private TextView timeRangeView;
    private SeekBar timeRangeBar;
    private Switch typeSwitch;
    private GraphView graphView;

    private String state = TEMPERATURE_STATE;
    BarGraphSeries<DataPoint> temperatureSeries = new BarGraphSeries<>();
    LineGraphSeries<DataPoint> pressureSeries = new LineGraphSeries<>();

    private static final String TEMPERATURE_STATE = "TEMPERATURE";
    private static final String PRESSURE_STATE = "PRESSURE";

    public ChartFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        getComponents(view);
        setPresenter();
        setListeners();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.onViewAttached();
    }

    private void getComponents(View view) {
        timeRangeView = view.findViewById(R.id.time_range_label);
        timeRangeBar = view.findViewById(R.id.time_range_bar);
        typeSwitch = view.findViewById(R.id.type_switch);
        graphView = view.findViewById(R.id.graph_view);

        makeGraph();
    }

    private void makeGraph() {
        Viewport viewport = graphView.getViewport();

        viewport.setYAxisBoundsManual(true);
        viewport.setXAxisBoundsManual(true);

        viewport.setMinX(0);
        viewport.setMaxX(30);
        viewport.setMinY(34);
        viewport.setMaxY(42);
    }

    private void setPresenter() {
        TaskRepository repository = ((App) getActivity().getApplication()).getTaskRepository();
        presenter = new ChartPresenter(this, repository);
    }

    private void setListeners() {
        timeRangeBar.setOnSeekBarChangeListener(timeRangeBarListener);
        typeSwitch.setOnCheckedChangeListener(switchListener);
    }

    private SeekBar.OnSeekBarChangeListener timeRangeBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            displayTimeRange();
            setGraphXScale(i);
            presenter.onTimeRangeChanged(i);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void displayTimeRange() {
        timeRangeView.setText(String.valueOf(timeRangeBar.getProgress()));
    }

    private void setGraphXScale(int i) {
        Viewport viewport = graphView.getViewport();

        viewport.setYAxisBoundsManual(true);
        viewport.setXAxisBoundsManual(true);
        viewport.setMaxX(i);

        graphView.onDataChanged(false, false);
    }

    private CompoundButton.OnCheckedChangeListener switchListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (!b){
                state = TEMPERATURE_STATE;
                showTemperatureGraph();

            } else {
                state = PRESSURE_STATE;
                showPressureGraph();
            }
        }
    };

    private void showTemperatureGraph() {
        graphView.removeAllSeries();
        graphView.addSeries(temperatureSeries);
    }

    private void showPressureGraph() {
        graphView.removeAllSeries();
        graphView.addSeries(pressureSeries);
    }

    @Override
    public void drawChart(LiveData<List<Task>> tasks) {
        tasks.observe((LifecycleOwner) getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                setPoints(tasks);
            }
        });
    }

    private void setPoints(List<Task> tasks) {
        List<List<Task>> temperatureDayTasks = getDayTasks(tasks, Task.MEASUREMENT_TEMPERATURE);
        List<List<Task>> pressureDayTasks = getDayTasks(tasks, Task.MEASUREMENT_PRESSURE);

        int chartBegin = timeRangeBar.getProgress() - temperatureDayTasks.size();

        int indexT = 0;
        for (List<Task> list : temperatureDayTasks) {

            for (int j = 0; j < list.size(); j++) {
                Task task = list.get(j);
                double x = (double) chartBegin + (double) indexT + ((double) j / (double) list.size());

                DataPoint point = new DataPoint(x, task.getResult());

                temperatureSeries.appendData(point, true, 1000);
            }

            indexT++;
        }

        int indexP = 0;
        for (List<Task> list : pressureDayTasks) {

            for (int j = 0; j < list.size(); j++) {
                Task task = list.get(j);
                double x = (double) chartBegin + (double) indexP + ((double) j / (double) list.size());

                DataPoint point = new DataPoint(x, task.getResult());

                pressureSeries.appendData(point, true, 1000);
            }

            indexP++;
        }

        if (state.equals(TEMPERATURE_STATE)){
            graphView.removeAllSeries();
            graphView.addSeries(temperatureSeries);

        } else {
            graphView.removeAllSeries();
            graphView.addSeries(pressureSeries);
        }
    }

    private List<List<Task>> getDayTasks(List<Task> tasks, String type) {
        List<List<Task>> result = new ArrayList<>();

        if (tasks.isEmpty())
            return result;

        Date start = new Date();
        start.setTime(tasks.get(0).getTimestamp().getTime());

        Date end = new Date();
        end.setTime(tasks.get(tasks.size() - 1).getTimestamp().getTime());

        Date startOfDay = start;
        startOfDay.setHours(0);
        startOfDay.setMinutes(0);
        startOfDay.setSeconds(0);

        Date endOfDay = incrementDay(startOfDay);

        while (startOfDay.before(end) || startOfDay.equals(end)) {

            List<Task> dayTasks = new ArrayList<>();
            for (Task task : tasks) {

                if ((task.getTimestamp().after(startOfDay) || task.getTimestamp().equals(startOfDay))
                        && task.getTimestamp().before(endOfDay)) {

                    if (task.getType().equals(type))
                        dayTasks.add(task);
                }
            }

            result.add(dayTasks);

            startOfDay = endOfDay;
            endOfDay = incrementDay(startOfDay);
        }

        return result;
    }

    private Date incrementDay(Date date) {
        Date day = new Date();
        day.setTime(date.getTime() + (24 * 60 * 60 * 1000));

        return day;
    }

    @Override
    public int getRange() {
        return timeRangeBar.getProgress();
    }
}
