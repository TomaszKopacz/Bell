package com.example.adam.myapplication.mainwindow.calendar;

import android.app.Fragment;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applandeo.materialcalendarview.EventDay;
import com.example.adam.myapplication.R;
import com.example.adam.myapplication.app.App;
import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment implements CalendarContract.CalendarView {

    private CalendarPresenter presenter;
    private com.applandeo.materialcalendarview.CalendarView calendarView;

    public CalendarFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        getComponents(view);
        setPresenter();

        return view;
    }

    private void getComponents(View view) {
        calendarView = view.findViewById(R.id.calendar);
    }

    private void setPresenter(){
        TaskRepository repository = ((App)getActivity().getApplication()).getTaskRepository();
        this.presenter = new CalendarPresenter(this, repository);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.onViewAttached();
    }

    @Override
    public void setTasks(LiveData<List<Task>> tasks) {
        tasks.observe((LifecycleOwner) getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                displayTasks(parseTasksToDayEvents(tasks));
            }
        });
    }

    private void displayTasks(List<EventDay> tasks) {
        calendarView.setEvents(tasks);
    }

    private List<EventDay> parseTasksToDayEvents(List<Task> tasks) {
        List<EventDay> events = new ArrayList<>();

        for (Task task : tasks)
            events.add(convertTaskToDayEvent(task));

        return events;
    }

    private EventDay convertTaskToDayEvent(Task task) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        try {
            Calendar calendar = convertTaskDateToCalendarInstance(task, formatter);

            return new EventDay(calendar, R.drawable.bell);

        } catch (Exception e) {

            return null;
        }
    }

    @NonNull
    private Calendar convertTaskDateToCalendarInstance(Task task, SimpleDateFormat formatter) throws ParseException {
        Date date = formatter.parse(task.getDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);

        return calendar;
    }
}
