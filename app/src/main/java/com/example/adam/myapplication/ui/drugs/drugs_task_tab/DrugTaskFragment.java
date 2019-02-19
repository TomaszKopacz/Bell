package com.example.adam.myapplication.ui.drugs.drugs_task_tab;


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
import android.widget.EditText;
import android.widget.ImageView;
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

public class DrugTaskFragment extends Fragment implements DrugTaskContract.DrugTaskView {

    private DrugTaskContract.DrugTaskPresenter presenter;

    private EditText drugText;
    private EditText doseText;

    private ImageView unitIcon;

    private ImageView timeIcon;
    private TextView timeText;

    private ImageView dateIcon;
    private TextView dateText;

    private ExpandableLayout expandableDateLayout;
    private ImageView endDateIcon;
    private TextView endDateText;

    private CheckBox isCycleCheckBox;

    private Button submitButton;

    public DrugTaskFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_drugs_task, container, false);

        getComponents(view);
        setPresenter();
        setListeners();

        return view;
    }

    private void getComponents(View view) {
        drugText = view.findViewById(R.id.drug);
        doseText = view.findViewById(R.id.dose);

        unitIcon = view.findViewById(R.id.unit_icon);

        timeIcon = view.findViewById(R.id.time_icon);
        timeText = view.findViewById(R.id.time_text);

        dateIcon = view.findViewById(R.id.date_icon);
        dateText = view.findViewById(R.id.date_text);

        expandableDateLayout = view.findViewById(R.id.date_expandable);
        endDateIcon = view.findViewById(R.id.date_end_icon);
        endDateText = view.findViewById(R.id.date_end_text);

        isCycleCheckBox = view.findViewById(R.id.switch_repeat);

        submitButton = view.findViewById(R.id.submit_button);
    }

    public void setPresenter() {
        TaskRepository repository = ((App) getActivity().getApplication()).getTaskRepository();
        this.presenter = new DrugTaskPresenter(this, repository);
    }


    private void setListeners() {
        unitIcon.setOnClickListener(unitListener);
        timeIcon.setOnClickListener(timeListener);
        dateIcon.setOnClickListener(dateListener);
        endDateIcon.setOnClickListener(endDateListener);
        isCycleCheckBox.setOnCheckedChangeListener(boxListener);
        submitButton.setOnClickListener(submitListener);
    }

    private View.OnClickListener unitListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

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
            setTime(DatetimeFormatter.getTimeFormatted(hour, minute));
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

    private CompoundButton.OnCheckedChangeListener boxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b)
                expandableDateLayout.expand();
            else
                expandableDateLayout.collapse();
        }
    };

    private View.OnClickListener submitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.onSubmitButtonClicked();
        }
    };

    @Override
    public String getDrug() {
        return drugText.getText().toString();
    }

    @Override
    public String getDose() {
        return doseText.getText().toString();
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
    public void setDrug(String drug) {
        drugText.setText(drug);
    }

    @Override
    public void setDose(String dose) {
        doseText.setText(dose);
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
