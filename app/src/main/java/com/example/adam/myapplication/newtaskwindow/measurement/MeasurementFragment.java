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
import com.example.adam.myapplication.app.App;
import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;
import com.example.adam.myapplication.utils.DatetimeFormatter;
import com.example.adam.myapplication.utils.DatetimePicker;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.Calendar;

public class MeasurementFragment extends Fragment implements MeasurementContract.MeasurementView {

    private MeasurementContract.MeasurementPresenter presenter;

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
        setPresenter();
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

    public void setPresenter() {
        TaskRepository repository = ((App) getActivity().getApplication()).getTaskRepository();
        this.presenter = new MeasurementPresenter(this, repository);
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
                setUnit(MeasurementContract.MeasurementView.UNIT_C);

            } else if (b && compoundButton == pressureButton) {
                setUnit(MeasurementContract.MeasurementView.UNIT_mmHg);
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
            DatetimePicker.showTimePicker(getActivity(), timeSetListener);
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener
            = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            setHour(DatetimeFormatter.getTimeFormatted(hour, minute));
        }
    };

    private View.OnClickListener dateListener
            = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DatetimePicker.showDatePicker(getActivity(), dateSetListener);
        }
    };

    private DatePickerDialog.OnDateSetListener dateSetListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            setDate(DatetimeFormatter.getDateFormatted(calendar));
        }
    };

    private View.OnClickListener endDateListener
            = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DatetimePicker.showDatePicker(getActivity(), endDateSetListener);
        }
    };

    private DatePickerDialog.OnDateSetListener endDateSetListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            setEndDate(DatetimeFormatter.getDateFormatted(calendar));
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
            if (presenter != null) {
                presenter.onSubmitButtonClicked();
                navigateToParentView();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getType() {
        if (temperatureButton.isChecked())
            return Task.MEASUREMENT_TEMPERATURE;

        else if (pressureButton.isChecked())
            return Task.MEASUREMENT_PRESSURE;

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
    public void setType(String type) {
        switch (type) {
            case Task.MEASUREMENT_TEMPERATURE:
                temperatureButton.setChecked(true);
                break;

            case Task.MEASUREMENT_PRESSURE:
                pressureButton.setChecked(true);
                break;
        }
    }

    @Override
    public void setUnit(String unit) {
        unitText.setText(unit);
    }

    @Override
    public void setHour(String hour) {
        hourText.setText(hour);
    }

    @Override
    public void setDate(String date) {
        dateText.setText(date);
    }

    @Override
    public void setEndDate(String endDate) {
        endDateText.setText(endDate);
    }

    @Override
    public void setIsCycle(boolean b) {
        box.setChecked(b);
    }

    private void navigateToParentView() {
        getActivity().finish();
    }
}
