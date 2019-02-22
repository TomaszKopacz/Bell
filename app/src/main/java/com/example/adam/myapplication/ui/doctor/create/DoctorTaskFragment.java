package com.example.adam.myapplication.ui.doctor.create;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.data.objects.Doctor;
import com.example.adam.myapplication.ui.doctor.dialogs.ChooseDoctorDialog;
import com.example.adam.myapplication.utils.Formatter;
import com.example.adam.myapplication.utils.Picker;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.text.Normalizer;
import java.util.Calendar;

public class DoctorTaskFragment extends Fragment {

    private View view;
    private DoctorTaskViewModel viewModel;

    private TextView doctorText;
    private TextView timeText;
    private TextView dateText;
    private Switch repeatSwitch;
    private ExpandableLayout expandableDateLayout;
    private TextView endDateText;
    private TextView moreLabel;
    private ExpandableLayout moreExpandable;
    private EditText noteText;
    private Button submitButton;

    public DoctorTaskFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_doctor_task, container, false);
        viewModel = ViewModelProviders.of(this).get(DoctorTaskViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getComponents();
        setObservers();
        setListeners();
    }

    private void getComponents() {
        doctorText = view.findViewById(R.id.name_text);
        timeText = view.findViewById(R.id.time_text);
        dateText = view.findViewById(R.id.date_text);
        repeatSwitch = view.findViewById(R.id.switch_repeat);
        expandableDateLayout = view.findViewById(R.id.date_expandable);
        endDateText = view.findViewById(R.id.date_end_text);
        moreLabel = view.findViewById(R.id.more_label);
        moreExpandable = view.findViewById(R.id.more_expandable);
        noteText = view.findViewById(R.id.note_input);
        submitButton = view.findViewById(R.id.submit_button);
    }

    private void setObservers() {
        viewModel.getDoctor().observe(this, doctorObserver);
        viewModel.getDate().observe(this, dateObserver);
        viewModel.getEndDate().observe(this, endDateObserver);
    }

    private Observer<Doctor> doctorObserver = new Observer<Doctor>() {
        @Override
        public void onChanged(@Nullable Doctor doctor) {
            if (doctor != null)
                doctorText.setText(doctor.getSpecialization());
        }
    };

    private Observer<Calendar> dateObserver = new Observer<Calendar>() {
        @Override
        public void onChanged(@Nullable Calendar calendar) {
            if (calendar != null) {
                dateText.setText(Formatter.getDateString(calendar));
                timeText.setText(Formatter.getTimeString(calendar));
            }
        }
    };

    private Observer<Calendar> endDateObserver = new Observer<Calendar>() {
        @Override
        public void onChanged(@Nullable Calendar calendar) {
            if (calendar != null) {
                endDateText.setText(Formatter.getDateString(calendar));
            }
        }
    };

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

        }
    };

    private View.OnClickListener dateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Picker.showDatePicker(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    viewModel.dateSet(year, month, day);
                }
            });
        }
    };

    private View.OnClickListener timeListener
            = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Picker.showTimePicker(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hour, int minute) {
                    viewModel.timeSet(hour, minute);
                }
            });
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
            Picker.showDatePicker(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    viewModel.endDateSet(year, month, day);
                }
            });
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
            viewModel.noteSet(noteText.getText().toString());
            viewModel.insertTask();
        }
    };
}
