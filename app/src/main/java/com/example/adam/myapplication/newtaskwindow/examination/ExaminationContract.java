package com.example.adam.myapplication.newtaskwindow.examination;

public interface ExaminationContract {

    interface ExaminationView {
        String getDoctor();

        String getLocation();

        String getInfo();

        String getTime();

        String getDate();

        String getEndDate();

        void setDoctor(String doctor);

        void setLocation(String location);

        void setInfo(String info);

        void setTime(String time);

        void setDate(String date);

        void setEndDate(String endDate);
    }

    interface ExaminationPresenter {
        void onSubmitButtonClicked();
    }
}
