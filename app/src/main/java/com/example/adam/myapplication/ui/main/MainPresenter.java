package com.example.adam.myapplication.ui.main;

import com.example.adam.myapplication.ui.board.BoardFragment;
import com.example.adam.myapplication.ui.doctor.tabs.DoctorTabsFragment;
import com.example.adam.myapplication.ui.drugs.drugs_tabs.DrugsTabsFragment;
import com.example.adam.myapplication.ui.scores.scores_tabs.ScoresTabsFragment;

public class MainPresenter implements MainContract.MainPresenter {

    private MainContract.MainView view;

    MainPresenter(MainContract.MainView view) {
        this.view = view;
    }

    @Override
    public void onViewAttached() {
        view.changeContentView(new BoardFragment());
    }

    @Override
    public void onBoardSelected() {
        view.changeContentView(new BoardFragment());
    }

    @Override
    public void onDoctorSelected() {
        view.changeContentView(new DoctorTabsFragment());
    }

    @Override
    public void onPillsSelected() {
        view.changeContentView(new DrugsTabsFragment());
    }

    @Override
    public void onScoresSelected() {
        view.changeContentView(new ScoresTabsFragment());
    }
}
