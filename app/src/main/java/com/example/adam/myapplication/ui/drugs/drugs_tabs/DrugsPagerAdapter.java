package com.example.adam.myapplication.ui.drugs.drugs_tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.adam.myapplication.ui.drugs.drugs_task_tab.DrugTaskFragment;

public class DrugsPagerAdapter extends FragmentPagerAdapter {

    private final String[] TAB_TITLES = {"TASK", "DRUGS", "HISTORY"};

    DrugsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DrugTaskFragment();

            case 1:
                return new DrugTaskFragment();

            case 2:
                return new DrugTaskFragment();
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
