package com.example.adam.myapplication.mainwindow;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.adam.myapplication.R;

public class CalendarFragment extends Fragment {

    private FragmentActivity mFragAct;
    private Intent mIntent;

    public CalendarFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.empty, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFragAct = (FragmentActivity) getActivity();
        mIntent = mFragAct.getIntent();
        //DatetimePicker.showDatePickerFullScreen(mFragAct, dateSetListener);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            //dateText.setText(DatetimeFormatter.getDateFormatted(year, month, day));
        }
    };


}
