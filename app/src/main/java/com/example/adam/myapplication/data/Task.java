package com.example.adam.myapplication.data;

public class Task {
    private String date;
    private String hour;
    private String type;
    private boolean status;
    private String result;
    private double dose;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public double getDose() {
        return dose;
    }

    public void setDose(double dose) {
        this.dose = dose;
    }

    public Task(String type, String date) {
        this.type = type;
        this.date = date;
        this.status = false;
        this.result = "empty";
        this.dose = -1;

    }
}
