package com.example.adam.myapplication.ui.scores.scores_task_tab;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.app.App;
import com.example.adam.myapplication.data.objects.Task;
import com.example.adam.myapplication.data.db.TaskRepository;
import com.example.adam.myapplication.notification.TaskAlarm;
import com.example.adam.myapplication.utils.DatetimeFormatter;
import com.example.adam.myapplication.utils.DatetimePicker;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.Date;

public class ScoresTaskFragment extends Fragment implements ScoresTaskContract.ScoresTaskView {

    private ScoresTaskContract.ScoresTaskPresenter presenter;

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

    private Button submitButton;

    public ScoresTaskFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scores_task, container, false);

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
        hourText = view.findViewById(R.id.time);

        dateIcon = view.findViewById(R.id.date_icon);
        dateText = view.findViewById(R.id.date);

        endDateIcon = view.findViewById(R.id.date_end_icon);
        endDateText = view.findViewById(R.id.date_end);

        expandableDateLayout = view.findViewById(R.id.date_expandable);
        box = view.findViewById(R.id.cycle_check_box);

        submitButton = view.findViewById(R.id.submit_button);
    }

    public void setPresenter() {
        TaskRepository repository = ((App) getActivity().getApplication()).getTaskRepository();
        this.presenter = new ScoresTaskPresenter(this, repository);
    }


    private void setListeners() {
        temperatureButton.setOnCheckedChangeListener(radioListener);
        pressureButton.setOnCheckedChangeListener(radioListener);

        unitIcon.setOnClickListener(unitListener);
        hourIcon.setOnClickListener(hourListener);
        dateIcon.setOnClickListener(dateListener);
        endDateIcon.setOnClickListener(endDateListener);

        box.setOnCheckedChangeListener(boxListener);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSubmitButtonClicked();
            }
        });
    }

    private CompoundButton.OnCheckedChangeListener radioListener
            = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b && compoundButton == temperatureButton) {
                setUnit(ScoresTaskContract.ScoresTaskView.UNIT_C);

            } else if (b && compoundButton == pressureButton) {
                setUnit(ScoresTaskContract.ScoresTaskView.UNIT_mmHg);
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
            setDate(DatetimeFormatter.getDateFormatted(day, month, year));
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
            setEndDate(DatetimeFormatter.getDateFormatted(day, month, year));
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
    public String getType() {
        return Task.SCORE;
    }

    @Override
    public String getUnit() {
        return unitText.getText().toString();
    }

    @Override
    public String getTime() {
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

    @Override
    public void onTaskCreated(String status, @Nullable Task task) {
        if (status.equals(SUCCESS) && task != null) {
            Date currentDate = new Date();
            if (!task.getTimestamp().before(currentDate))
                setNotification(task);
        }
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    private void setNotification(Task task) {
        TaskAlarm.setAlarm(getActivity(), task);
    }

    @Override
    public void navigateToParentView() {
        getActivity().finish();
    }
}
