package com.example.adam.myapplication.ui.scores.scores_tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.adam.myapplication.ui.scores.scores_stats_tab.ScoresStatsFragment;
import com.example.adam.myapplication.ui.scores.scores_task_tab.ScoresTaskFragment;

public class ScoresPagerAdapter extends FragmentPagerAdapter {

    private final String[] TAB_TITLES = {"MEASUREMENT", "STATISTICS"};

    ScoresPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ScoresTaskFragment();

            case 1:
                return new ScoresStatsFragment();
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
