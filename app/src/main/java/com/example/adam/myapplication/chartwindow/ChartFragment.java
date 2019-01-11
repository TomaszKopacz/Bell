package com.example.adam.myapplication.chartwindow;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.app.App;
import com.example.adam.myapplication.data.AnatomyLimits;
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

    private static final int NUM_OF_TEMP_LABELS_X = 6;
    private static final int NUM_OF_PRESSURE_LABELS_X = 8;
    private static final int NUM_OF_LABELS_Y = 9;
    private static final int LABELS_ANGLE = 135;

    private GraphView temperatureGraphView;
    private GraphView pressureGraphView;

    private ChartPresenter presenter;

    private LineGraphSeries<DataPoint> temperatureLineSeries = new LineGraphSeries<>();
    private PointsGraphSeries<DataPoint> temperaturePointSeries = new PointsGraphSeries<>();

    private LineGraphSeries<DataPoint> pressureSystolicLineSeries = new LineGraphSeries<>();
    private PointsGraphSeries<DataPoint> pressureSystolicPointSeries = new PointsGraphSeries<>();

    private LineGraphSeries<DataPoint> pressureDiastolicLineSeries = new LineGraphSeries<>();
    private PointsGraphSeries<DataPoint> pressureDiastolicPointSeries = new PointsGraphSeries<>();

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
        temperatureGraphView.getGridLabelRenderer().setNumVerticalLabels(NUM_OF_TEMP_LABELS_X);
        temperatureGraphView.getViewport().setMinY(AnatomyLimits.LIMIT_MIN_TEMPERATURE);
        temperatureGraphView.getViewport().setMaxY(AnatomyLimits.LIMIT_MAX_TEMPERATURE);

        pressureGraphView.getGridLabelRenderer().setNumVerticalLabels(NUM_OF_PRESSURE_LABELS_X);
        pressureGraphView.getViewport().setMinY(AnatomyLimits.LIMIT_MIN_PRESSURE);
        pressureGraphView.getViewport().setMaxY(AnatomyLimits.LIMIT_MAX_PRESSURE);
    }

    private void setDatesAsXLabels() {
        temperatureGraphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        temperatureGraphView.getGridLabelRenderer().setHorizontalLabelsAngle(LABELS_ANGLE);
        temperatureGraphView.getGridLabelRenderer().setNumHorizontalLabels(NUM_OF_LABELS_Y);
        temperatureGraphView.getGridLabelRenderer().setHumanRounding(false);

        pressureGraphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        pressureGraphView.getGridLabelRenderer().setHorizontalLabelsAngle(LABELS_ANGLE);
        pressureGraphView.getGridLabelRenderer().setNumHorizontalLabels(NUM_OF_LABELS_Y);
        pressureGraphView.getGridLabelRenderer().setHumanRounding(false);
    }

    private void setDefaultDatesRange() {
        Calendar calendar = Calendar.getInstance();
        Date endDay = calendar.getTime();
        calendar.add(Calendar.DATE, -NUM_OF_LABELS_Y);
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
        temperatureLineSeries.setColor(Color.RED);
        temperaturePointSeries.setColor(Color.RED);

        pressureSystolicLineSeries = new LineGraphSeries<>();
        pressureSystolicPointSeries = new PointsGraphSeries<>();
        pressureSystolicLineSeries.setColor(Color.RED);
        pressureSystolicPointSeries.setColor(Color.RED);

        pressureDiastolicLineSeries = new LineGraphSeries<>();
        pressureDiastolicPointSeries = new PointsGraphSeries<>();
        pressureDiastolicLineSeries.setColor(Color.BLUE);
        pressureDiastolicPointSeries.setColor(Color.BLUE);
    }

    private void appendPoints(List<Task> tasks) {
        for (Task task : tasks) {

            if (task.getResult() == 0.0f)
                continue;

            switch (task.getType()) {
                case TEMPERATURE_STATE:
                    DataPoint point = new DataPoint(task.getTimestamp(), task.getResult());
                    appendTemperaturePoint(point);
                    break;

                case PRESSURE_STATE:
                    DataPoint pointSystolic = new DataPoint(task.getTimestamp(), task.getResult());
                    DataPoint pointDiastolic = new DataPoint(task.getTimestamp(), task.getResult2());
                    appendPressurePoint(pointSystolic, pointDiastolic);
                    break;
            }
        }
    }

    private void appendTemperaturePoint(DataPoint point) {
        temperatureLineSeries.appendData(point, true, 1000);
        temperaturePointSeries.appendData(point, true, 1000);
    }

    private void appendPressurePoint(DataPoint pointSystolic, DataPoint pointDiastolic) {
        pressureSystolicLineSeries.appendData(pointSystolic, true, 1000);
        pressureSystolicPointSeries.appendData(pointSystolic, true, 1000);

        pressureDiastolicLineSeries.appendData(pointDiastolic, true, 1000);
        pressureDiastolicPointSeries.appendData(pointDiastolic, true, 1000);
    }

    private void presentSeries() {
        temperatureGraphView.addSeries(temperatureLineSeries);
        temperatureGraphView.addSeries(temperaturePointSeries);

        pressureGraphView.addSeries(pressureSystolicLineSeries);
        pressureGraphView.addSeries(pressureSystolicPointSeries);

        pressureGraphView.addSeries(pressureDiastolicLineSeries);
        pressureGraphView.addSeries(pressureDiastolicPointSeries);
    }
}
