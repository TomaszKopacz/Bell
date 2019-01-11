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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.Date;
import java.util.List;

public class ChartFragment extends Fragment implements ChartContract.ChartView {

    private static final String TEMPERATURE_STATE = "TEMPERATURE";
    private static final String PRESSURE_STATE = "PRESSURE";

    private static final int GRAPH_VIEWPORT_RANGE = 5;

    private GraphView temperatureGraphView;
    private GraphView pressureGraphView;

    private ChartPresenter presenter;

    private LineGraphSeries<DataPoint> temperatureLineSeries = new LineGraphSeries<>();
    private PointsGraphSeries<DataPoint> temperaturePointSeries = new PointsGraphSeries<>();
    private LineGraphSeries<DataPoint> pressureLineSeries = new LineGraphSeries<>();
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
        setDefaultGraphLayout();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.onViewAttached();
    }

    private void getComponents(View view) {
        temperatureGraphView = view.findViewById(R.id.graph_view_temperature);
        pressureGraphView = view.findViewById(R.id.graph_view_pressure);
    }

    private void setDefaultGraphLayout() {
        setDefaultXAxis();
        setDefaultYAxis();
        setDefaultGraphPerformance();
    }

    private void setDefaultXAxis() {
        setDatesAsXLabels();
        setDefaultDatesRange();
    }

    private void setDefaultYAxis() {
        temperatureGraphView.getGridLabelRenderer().setNumVerticalLabels(5);
        temperatureGraphView.getViewport().setMinY(34);
        temperatureGraphView.getViewport().setMaxY(42);

        pressureGraphView.getGridLabelRenderer().setNumVerticalLabels(6);
        pressureGraphView.getViewport().setMinY(60);
        pressureGraphView.getViewport().setMaxY(210);
    }

    private void setDatesAsXLabels() {
        temperatureGraphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        temperatureGraphView.getGridLabelRenderer().setHorizontalLabelsAngle(135);
        temperatureGraphView.getGridLabelRenderer().setNumHorizontalLabels(6);
        temperatureGraphView.getGridLabelRenderer().setHumanRounding(false);

        pressureGraphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        pressureGraphView.getGridLabelRenderer().setHorizontalLabelsAngle(135);
        pressureGraphView.getGridLabelRenderer().setNumHorizontalLabels(6);
        pressureGraphView.getGridLabelRenderer().setHumanRounding(false);
    }

    private void setDefaultDatesRange() {
        Calendar calendar = Calendar.getInstance();
        Date endDay = calendar.getTime();
        calendar.add(Calendar.DATE, -GRAPH_VIEWPORT_RANGE);
        Date startDay = calendar.getTime();

        temperatureGraphView.getViewport().setMinX(startDay.getTime());
        temperatureGraphView.getViewport().setMaxX(endDay.getTime());

        pressureGraphView.getViewport().setMinX(startDay.getTime());
        pressureGraphView.getViewport().setMaxX(endDay.getTime());
    }

    private void setDefaultGraphPerformance() {
        temperatureGraphView.getViewport().setXAxisBoundsManual(true);
        temperatureGraphView.getViewport().setYAxisBoundsManual(true);
        temperatureGraphView.getViewport().setScrollable(true);
        temperatureGraphView.getViewport().setScalable(true);

        pressureGraphView.getViewport().setXAxisBoundsManual(true);
        pressureGraphView.getViewport().setYAxisBoundsManual(true);
        pressureGraphView.getViewport().setScrollable(true);
        pressureGraphView.getViewport().setScalable(true);
    }

    private void setPresenter() {
        TaskRepository repository = ((App) getActivity().getApplication()).getTaskRepository();
        presenter = new ChartPresenter(this, repository);
    }

    @Override
    public void drawChart(LiveData<List<Task>> tasks) {
        tasks.observe((LifecycleOwner) getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                drawSeries(tasks);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void drawSeries(List<Task> tasks) {
        resetSeriesPoints();
        appendPoints(tasks);
        presentSeries();
    }

    private void resetSeriesPoints() {
        temperatureGraphView.removeAllSeries();
        pressureGraphView.removeAllSeries();

        temperatureLineSeries = new LineGraphSeries<>();
        temperaturePointSeries = new PointsGraphSeries<>();
        pressureLineSeries = new LineGraphSeries<>();
        pressurePointSeries = new PointsGraphSeries<>();
    }

    private void appendPoints(List<Task> tasks) {
        for (Task task : tasks) {

            if (task.getResult() == 0.0f)
                break;

            DataPoint point = new DataPoint(task.getTimestamp(), task.getResult());

            switch (task.getType()) {
                case TEMPERATURE_STATE:
                    appendTemperaturePoint(point);
                    break;

                case PRESSURE_STATE:
                    appendPressurePoint(point);
                    break;
            }
        }
    }

    private void appendTemperaturePoint(DataPoint point) {
        temperatureLineSeries.appendData(point, true, 1000);
        temperaturePointSeries.appendData(point, true, 1000);
    }

    private void appendPressurePoint(DataPoint point) {
        pressureLineSeries.appendData(point, true, 1000);
        pressurePointSeries.appendData(point, true, 1000);
    }

    private void presentSeries() {
        temperatureGraphView.addSeries(temperatureLineSeries);
        temperatureGraphView.addSeries(temperaturePointSeries);

        pressureGraphView.addSeries(pressureLineSeries);
        pressureGraphView.addSeries(pressurePointSeries);
    }
}
