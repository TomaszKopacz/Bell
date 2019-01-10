package com.example.adam.myapplication.chartwindow;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.app.App;
import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ChartFragment extends Fragment implements ChartContract.ChartView {

    private static final String TEMPERATURE_STATE = "TEMPERATURE";
    private static final String PRESSURE_STATE = "PRESSURE";

    private Switch typeSwitch;
    private GraphView graphView;

    private ChartPresenter presenter;

    private String state = TEMPERATURE_STATE;

    private LineGraphSeries<DataPoint> temperatureSeries = new LineGraphSeries<>();
    private PointsGraphSeries<DataPoint> temperaturePointSeries = new PointsGraphSeries<>();
    private LineGraphSeries<DataPoint> pressureSeries = new LineGraphSeries<>();
    private PointsGraphSeries<DataPoint> pressurePointSeries = new PointsGraphSeries<>();

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
        typeSwitch = view.findViewById(R.id.type_switch);
        graphView = view.findViewById(R.id.graph_view);

        makeGraph();
    }

    private void makeGraph() {

    }

    private void setPresenter() {
        TaskRepository repository = ((App) getActivity().getApplication()).getTaskRepository();
        presenter = new ChartPresenter(this, repository);
    }

    private void setListeners() {
        typeSwitch.setOnCheckedChangeListener(switchListener);
    }

    private CompoundButton.OnCheckedChangeListener switchListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (!b){
                state = TEMPERATURE_STATE;

            } else {
                state = PRESSURE_STATE;
            }
        }
    };

    @Override
    public void drawChart(LiveData<List<Task>> tasks) {
        tasks.observe((LifecycleOwner) getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                setPoints(tasks);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void setPoints(List<Task> tasks) {
        temperaturePointSeries = new PointsGraphSeries<>();

        for(Task task : tasks){
            DataPoint point = new DataPoint(task.getTimestamp(), task.getResult());
            temperatureSeries.appendData(point, true, 1000);
            temperaturePointSeries.appendData(point, true, 1000);
        }

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.DATE, -5);
        Date twoDaysBefore = calendar.getTime();

        graphView.addSeries(temperatureSeries);
        graphView.addSeries(temperaturePointSeries);
        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graphView.getGridLabelRenderer().setNumHorizontalLabels(5);
        graphView.getGridLabelRenderer().setNumVerticalLabels(9);
        graphView.getGridLabelRenderer().setHorizontalLabelsAngle(135);
        graphView.getViewport().setMinX(twoDaysBefore.getTime());
        graphView.getViewport().setMaxX(today.getTime());
        graphView.getViewport().setMinY(34);
        graphView.getViewport().setMaxY(42);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScalable(true);
        graphView.getGridLabelRenderer().setHumanRounding(false);
    }
}
