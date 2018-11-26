package com.example.adam.myapplication.newtaskwindow.measurement;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.mainwindow.calendar.CalendarFragment;
import com.example.adam.myapplication.newtaskwindow.AddTaskActivity;
import com.example.adam.myapplication.utils.DatetimeFormatter;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.Calendar;

public class MeasurementFragment extends Fragment implements MeasurementView {

    private MeasurementPresenter presenter;

    private RadioButton temperatureButton;
    private RadioButton pressureButton;

    private ImageView unitIcon;
    private TextView unitText;

    private ImageView hourIcon;
    private TextView hourText;

    private ImageView dateIcon;
    private TextView dateText;

    private ImageView endDateIcon;
    private TextView endDateText;

    private CheckBox box;
    private ExpandableLayout expandableDateLayout;


    public MeasurementFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_measurement, container, false);

        getComponents(view);
        setListeners();

        return view;
    }

    private void getComponents(View view) {
        temperatureButton = view.findViewById(R.id.temperature_button);
        pressureButton = view.findViewById(R.id.pressure_button);

        unitIcon = view.findViewById(R.id.unit_icon);
        unitText = view.findViewById(R.id.unit_value_label);

        hourIcon = view.findViewById(R.id.time_icon);
        hourText = view.findViewById(R.id.hour_value_label);

        dateIcon = view.findViewById(R.id.date_icon);
        dateText = view.findViewById(R.id.date_value_label);

        endDateIcon = view.findViewById(R.id.date_end_icon);
        endDateText = view.findViewById(R.id.end_date_value_label);

        expandableDateLayout = view.findViewById(R.id.date_expandable);
        box = view.findViewById(R.id.cycle_check_box);
    }

    private void setListeners() {
        temperatureButton.setOnCheckedChangeListener(radioListener);
        pressureButton.setOnCheckedChangeListener(radioListener);

        unitIcon.setOnClickListener(unitListener);
        hourIcon.setOnClickListener(hourListener);
        dateIcon.setOnClickListener(dateListener);
        endDateIcon.setOnClickListener(endDateListener);

        box.setOnCheckedChangeListener(boxListener);
    }

    private CompoundButton.OnCheckedChangeListener radioListener
            = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b && compoundButton == temperatureButton) {
                unitText.setText(MeasurementView.UNIT_C);

            } else if (b && compoundButton == pressureButton) {
                unitText.setText(MeasurementView.UNIT_mmHg);
            }
        }
    };

    private View.OnClickListener unitListener
            = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener hourListener
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
            hourText.setText(DatetimeFormatter.getTimeFormatted(hour, minute));
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
            dateText.setText(DatetimeFormatter.getDateFormatted(year, month, day));
        }
    };

    private DatePickerDialog.OnDateSetListener endDateSetListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            endDateText.setText(DatetimeFormatter.getDateFormatted(year, month, day));
        }
    };

    private CompoundButton.OnCheckedChangeListener boxListener
            = new CompoundButton.OnCheckedChangeListener() {
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
            presenter.onSubmitButtonClicked();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(MeasurementPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public String getType() {
        if (temperatureButton.isChecked())
            return MeasurementView.TYPE_TEMPERATURE;

        else if (pressureButton.isChecked())
            return MeasurementView.TYPE_PRESSURE;

        else
            return null;
    }

    @Override
    public String getUnit() {
        return unitText.getText().toString();
    }

    @Override
    public String getHour() {
        return hourText.getText().toString();
    }

    @Override
    public String getDate() {
        return dateText.getText().toString();
    }

    @Override
    public String getEndDate() {
        return endDateText.getText().toString();
    }

    @Override
    public boolean isCycle() {
        return box.isChecked();
    }

    @Override
    public void navigateToParentView() {
        getActivity().finish();
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
