package com.example.adam.myapplication.ui.doctor.doctor_task_tab;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.example.adam.myapplication.data.db.doctor.DoctorRepository;
import com.example.adam.myapplication.data.db.task.TaskRepository;
import com.example.adam.myapplication.data.objects.Doctor;
import com.example.adam.myapplication.data.objects.Task;
import com.example.adam.myapplication.notification.TaskAlarm;
import com.example.adam.myapplication.ui.OnItemClickListener;
import com.example.adam.myapplication.ui.doctor.dialogs.ChooseDoctorDialog;
import com.example.adam.myapplication.ui.doctor.dialogs.NewDoctorDialog;
import com.example.adam.myapplication.ui.main.MainActivity;
import com.example.adam.myapplication.utils.DatetimeFormatter;
import com.example.adam.myapplication.utils.DatetimePicker;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DoctorTaskFragment extends Fragment implements DoctorTaskContract.DoctorTaskView {

    private DoctorTaskContract.DoctorTaskPresenter presenter;

    private TextView doctorText;
    private TextView timeText;
    private TextView dateText;
    private Switch repeatSwitch;
    private ExpandableLayout expandableDateLayout;
    private TextView endDateText;
    private TextView moreLabel;
    private ExpandableLayout moreExpandable;
    private Button submitButton;

    private List<Doctor> doctorsList;

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
        repeatSwitch = view.findViewById(R.id.switch_repeat);
        expandableDateLayout = view.findViewById(R.id.date_expandable);
        endDateText = view.findViewById(R.id.date_end_text);
        moreLabel = view.findViewById(R.id.more_label);
        moreExpandable = view.findViewById(R.id.more_expandable);
        submitButton = view.findViewById(R.id.submit_button);
    }

    public void setPresenter() {
        TaskRepository taskRepository = ((App) getActivity().getApplication()).getTaskRepository();
        DoctorRepository doctorRepository = ((App) getActivity().getApplication()).getDoctorRepository();
        this.presenter = new DoctorTaskPresenter(this, taskRepository, doctorRepository);
        presenter.onViewAttached();
    }

    private void setListeners() {
        doctorText.setOnClickListener(doctorListener);
        timeText.setOnClickListener(timeListener);
        dateText.setOnClickListener(dateListener);
        endDateText.setOnClickListener(endDateListener);
        repeatSwitch.setOnCheckedChangeListener(switchListener);
        moreLabel.setOnClickListener(moreListener);
        submitButton.setOnClickListener(submitListener);
    }

    private View.OnClickListener doctorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.onDoctorViewClicked();
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

    private View.OnClickListener moreListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            toggleMoreExpandable();
        }

        private void toggleMoreExpandable() {
            if (moreExpandable.isExpanded())
                moreExpandable.collapse();
            else
                moreExpandable.expand();
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
    public void updateDoctorsList(LiveData<List<Doctor>> list) {
        list.observe(this, new Observer<List<Doctor>>() {
            @Override
            public void onChanged(@Nullable List<Doctor> list) {
                if (doctorsList == null)
                    doctorsList = new ArrayList<>();

                doctorsList = list;
            }
        });
    }

    @Override
    public void showChooseDoctorDialog() {
        new ChooseDoctorDialog.Builder(getActivity())
                .setItems(doctorsList, itemListener)
                .showNewDoctorButton(newDoctorButtonListener)
                .create()
                .show();
    }

    private OnItemClickListener<Doctor> itemListener = new OnItemClickListener<Doctor>() {
        @Override
        public void onItemClick(View view, Doctor doctor) {
            presenter.onDoctorSelected(doctor);
        }
    };

    private View.OnClickListener newDoctorButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new NewDoctorDialog(getActivity(), new NewDoctorDialog.OnDoctorCreatedListener() {
                @Override
                public void onDoctorCreated(Doctor doctor) {
                    Log.i("MEDIBELL", "Doctor created");
                }
            }).create().show();
        }
    };

    @Override
    public void onDoctorSelected(Doctor doctor) {
        doctorText.setText(doctor.getSpecialization() + ", " + doctor.getName());
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
