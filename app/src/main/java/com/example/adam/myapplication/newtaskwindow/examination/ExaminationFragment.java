package com.example.adam.myapplication.newtaskwindow.examination;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.mainwindow.calendar.CalendarFragment;
import com.example.adam.myapplication.newtaskwindow.AddTaskActivity;
import com.example.adam.myapplication.utils.DatetimeFormatter;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.Calendar;

public class ExaminationFragment extends Fragment implements ExaminationView {

    private ExaminationPresenter presenter;

    private EditText doctorView;
    private EditText locationView;
    private EditText infoView;

    private ImageView timeIcon;
    private TextView timeView;

    private ImageView dateIcon;
    private TextView dateView;

    private ExpandableLayout expandableDateLayout;
    private ImageView endDateIcon;
    private TextView endDateView;

    private CheckBox isCycleCheckBox;

    public ExaminationFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_examination, container, false);

        getComponents(view);
        setListeners();

        return view;
    }

    private void getComponents(View view) {
        doctorView = view.findViewById(R.id.doctor);
        locationView = view.findViewById(R.id.location);
        infoView = view.findViewById(R.id.info);

        timeIcon = view.findViewById(R.id.time_icon);
        timeView = view.findViewById(R.id.time);

        dateIcon = view.findViewById(R.id.date_icon);
        dateView = view.findViewById(R.id.date);

        expandableDateLayout = view.findViewById(R.id.date_expandable);
        endDateIcon = view.findViewById(R.id.date_end_icon);
        endDateView = view.findViewById(R.id.date_end);

        isCycleCheckBox = view.findViewById(R.id.cycle_check_box);
    }

    private void setListeners() {
        timeIcon.setOnClickListener(timeListener);
        dateIcon.setOnClickListener(dateListener);
        endDateIcon.setOnClickListener(endDateListener);
        isCycleCheckBox.setOnCheckedChangeListener(boxListener);
    }

    private View.OnClickListener timeListener
            = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showTimePicker();
        }
    };

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(getActivity(),
                timeSetListener, hour, minute, true);
        dialog.show();
    }

    private TimePickerDialog.OnTimeSetListener timeSetListener
            = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            timeView.setText(DatetimeFormatter.getTimeFormatted(hour, minute));
        }
    };

    private View.OnClickListener dateListener
            = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showDatePicker(dateSetListener);
        }
    };

    private View.OnClickListener endDateListener
            = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showDatePicker(endDateSetListener);
        }
    };

    private void showDatePicker(DatePickerDialog.OnDateSetListener listener) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), listener, year, month, day);
        dialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            dateView.setText(DatetimeFormatter.getDateFormatted(year, month, day));
        }
    };

    private DatePickerDialog.OnDateSetListener endDateSetListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            endDateView.setText(DatetimeFormatter.getDateFormatted(year, month, day));
        }
    };

    private CompoundButton.OnCheckedChangeListener boxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b)
                expandableDateLayout.expand();
            else
                expandableDateLayout.collapse();
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_task, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_submit) {
            if (presenter != null)
                presenter.onSubmitButtonClicked();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(ExaminationPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public String getDoctor() {
        return doctorView.getText().toString();
    }

    @Override
    public String getLocation() {
        return locationView.getText().toString();
    }

    @Override
    public String getInfo() {
        return infoView.getText().toString();
    }

    @Override
    public String getTime() {
        return timeView.getText().toString();
    }

    @Override
    public String getDate() {
        return dateView.getText().toString();
    }

    @Override
    public String getEndDate() {
        return endDateView.getText().toString();
    }

    @Override
    public void goToCalendar() {
        createCalendarContract();
    }

    private void createCalendarContract() {
        CalendarFragment fragment = new CalendarFragment();
        ((AddTaskActivity) getActivity()).changeFragment(fragment);
    }
}
