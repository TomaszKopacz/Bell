package com.example.adam.myapplication.ui.scores.scores_stats_tab;

import android.arch.lifecycle.LiveData;

import com.example.adam.myapplication.data.objects.Task;
import com.example.adam.myapplication.data.db.TaskRepository;

import java.util.List;

public class ScoresStatsPresenter implements ScoresStatsContract.ScoresStatsPresenter {

    private ScoresStatsContract.ScoresStatsView view;
    private TaskRepository repository;

    ScoresStatsPresenter(ScoresStatsContract.ScoresStatsView view, TaskRepository repository){
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void onViewAttached() {
        LiveData<List<Task>> tasks = repository.getAll();
        view.drawChart(tasks);
    }
}
