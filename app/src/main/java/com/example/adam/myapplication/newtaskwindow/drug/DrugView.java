package com.example.adam.myapplication.newtaskwindow.drug;

import android.content.Context;

public interface DrugView {
    Context getContext();
    void setPresenter(DrugPresenter presenter);

    String getDrug();
    String getDose();

    String getTime();
    String getDate();
    String getEndDate();

    // TO REMOVE
    void goToCalendar();
}
