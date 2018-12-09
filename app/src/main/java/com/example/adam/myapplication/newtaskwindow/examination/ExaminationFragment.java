package com.example.adam.myapplication.newtaskwindow.examination;


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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.app.App;
import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskRepository;
import com.example.adam.myapplication.notification.TaskAlarm;
import com.example.adam.myapplication.utils.DatetimeFormatter;
import com.example.adam.myapplication.utils.DatetimePicker;

import net.cachapa.expandablelayout.ExpandableLayout;

public class ExaminationFragment extends Fragment implements ExaminationContract.ExaminationView {

    private ExaminationContract.ExaminationPresenter presenter;

    private EditText doctorText;
    private EditText locationText;
    private EditText infoText;

    private ImageView timeIcon;
    private TextView timeText;

    private ImageView dateIcon;
    private TextView dateText;

    private ExpandableLayout expandableDateLayout;
    private ImageView endDateIcon;
    private TextView endDateText;

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
        setPresenter();
        setListeners();

        return view;
    }

    private void getComponents(View view) {
        doctorText = view.findViewById(R.id.doctor);
        locationText = view.findViewById(R.id.location);
        infoText = view.findViewById(R.id.info);

        timeIcon = view.findViewById(R.id.time_icon);
        timeText = view.findViewById(R.id.time);

        dateIcon = view.findViewById(R.id.date_icon);
        dateText = view.findViewById(R.id.date);

        expandableDateLayout = view.findViewById(R.id.date_expandable);
        endDateIcon = view.findViewById(R.id.date_end_icon);
        endDateText = view.findViewById(R.id.date_end);

        isCycleCheckBox = view.findViewById(R.id.cycle_check_box);
    }

    public void setPresenter() {
        TaskRepository repository = ((App) getActivity().getApplication()).getTaskRepository();
        this.presenter = new ExaminationPresenter(this, repository);
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
    public String getDoctor() {
        return doctorText.getText().toString();
    }

    @Override
    public String getLocation() {
        return locationText.getText().toString();
    }

    @Override
    public String getInfo() {
        return infoText.getText().toString();
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
        return isCycleCheckBox.isChecked();
    }

    @Override
    public void setDoctor(String doctor) {
        doctorText.setText(doctor);
    }

    @Override
    public void setLocation(String location) {
        locationText.setText(location);
    }

    @Override
    public void setInfo(String info) {
        infoText.setText(info);
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
        if (status.equals(SUCCESS)  && task != null){
            setNotification(task);
        }
    }

    private void setNotification(Task task){
        TaskAlarm.setAlarm(getActivity(), task);
    }

    @Override
    public void navigateToParentView() {
        getActivity().finish();
    }
}
