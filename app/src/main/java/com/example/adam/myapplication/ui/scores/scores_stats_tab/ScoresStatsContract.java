package com.example.adam.myapplication.ui.scores.scores_stats_tab;

import android.arch.lifecycle.LiveData;

import com.example.adam.myapplication.data.objects.Task;

import java.util.List;

public interface ScoresStatsContract {

    interface ScoresStatsView {
        void drawChart(LiveData<List<Task>> tasks);
    }

    interface ScoresStatsPresenter {
        void onViewAttached();
    }
}
