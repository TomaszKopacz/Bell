package com.example.adam.myapplication.ui.doctor.doctor_task_tab;


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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.app.App;
import com.example.adam.myapplication.data.objects.Task;
import com.example.adam.myapplication.data.db.TaskRepository;
import com.example.adam.myapplication.notification.TaskAlarm;
import com.example.adam.myapplication.ui.main.MainActivity;
import com.example.adam.myapplication.utils.DatetimeFormatter;
import com.example.adam.myapplication.utils.DatetimePicker;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.Date;

public class DoctorTaskFragment extends Fragment implements DoctorTaskContract.DoctorTaskView {

    private DoctorTaskContract.DoctorTaskPresenter presenter;

    private TextView doctorText;
    private TextView timeText;
    private TextView dateText;
    private Switch repeatSwitch;
    private ExpandableLayout expandableDateLayout;
    private TextView endDateText;
    private Button submitButton;

    public DoctorTaskFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_task, container, false);

        getComponents(view);
        setPresenter();
        setListeners();

        return view;
    }

    private void getComponents(View view) {
        doctorText = view.findViewById(R.id.name_text);
        timeText = view.findViewById(R.id.time_text);
        dateText = view.findViewById(R.id.date_text);
        expandableDateLayout = view.findViewById(R.id.date_expandable);
        endDateText = view.findViewById(R.id.date_end_text);
        repeatSwitch = view.findViewById(R.id.switch_repeat);
        submitButton = view.findViewById(R.id.submit_button);
    }

    public void setPresenter() {
        TaskRepository repository = ((App) getActivity().getApplication()).getTaskRepository();
        this.presenter = new DoctorTaskPresenter(this, repository);
        presenter.onViewAttached();
    }

    private void setListeners() {
        timeText.setOnClickListener(timeListener);
        dateText.setOnClickListener(dateListener);
        endDateText.setOnClickListener(endDateListener);
        repeatSwitch.setOnCheckedChangeListener(switchListener);

        submitButton.setOnClickListener(submitListener);
    }

    private View.OnClickListener timeListener
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
            timeText.setText(DatetimeFormatter.getTimeFormatted(hour, minute));
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
            dateText.setText(DatetimeFormatter.getDateFormatted(day, month, year));
        }
    };

    private CompoundButton.OnCheckedChangeListener switchListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b)
                expandableDateLayout.expand();
            else
                expandableDateLayout.collapse();
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
            endDateText.setText(DatetimeFormatter.getDateFormatted(day, month, year));
        }
    };

    private View.OnClickListener submitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.onSubmitButtonClicked();
        }
    };

    @Override
    public String getDoctor() {
        return doctorText.getText().toString();
    }

    @Override
    public String getTime() {
        return timeText.getText().toString();
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
        return repeatSwitch.isChecked();
    }

    @Override
    public void setDoctor(String doctor) {
        doctorText.setText(doctor);
    }

    @Override
    public void setTime(String time) {
        timeText.setText(time);
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
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).goToBoard();
    }
}
