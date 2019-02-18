package com.example.adam.myapplication.ui.main;

import android.support.v4.app.Fragment;

public interface MainContract {
    interface MainView {
        void changeContentView(Fragment fragment);
    }

    interface MainPresenter {
        void onViewAttached();
        void onBoardSelected();
        void onDoctorSelected();
        void onPillsSelected();
        void onScoresSelected();
    }
}
