package com.example.adam.myapplication.ui.doctor.doctor_tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.adam.myapplication.ui.doctor.create.DoctorTaskFragment;
import com.example.adam.myapplication.ui.doctor.doctors_list_tab.DoctorsFragment;

public class DoctorPagerAdapter extends FragmentPagerAdapter {

    private final String[] TAB_TITLES = {"TASK", "DOCTORS", "HISTORY"};

    DoctorPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DoctorTaskFragment();

            case 1:
                return new DoctorsFragment();

            case 2:
                return new DoctorTaskFragment();
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }

    @Override
    public int getCount() {
        return 3;
    }
}
