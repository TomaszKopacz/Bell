package com.example.adam.myapplication.newtaskwindow.examination;

import android.content.Context;

interface ExaminationView {
    Context getContext();
    void setPresenter(ExaminationPresenter presenter);

    String getDoctor();
    String getLocation();
    String getInfo();

    String getTime();
    String getDate();
    String getEndDate();

    // TO REMOVE
    void goToCalendar();
}
