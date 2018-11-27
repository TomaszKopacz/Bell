package com.example.adam.myapplication.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Task {

    public static final String MEASUREMENT_TEMPERATURE = "TEMPERATURE";
    public static final String MEASUREMENT_PRESSURE = "PRESSURE";
    public static final String DRUG = "DRUG";
    public static final String EXAMINATION = "EXAMINATION";

    private static final String TYPE = "type";
    private static final String HOUR = "hour";
    private static final String DATE = "date";
    private static final String STATUS = "status";
    private static final String RESULT = "result";

    private static final String DRUG_NAME = "drug_name";
    private static final String DOSE = "dose";
    private static final String UNIT = "unit";

    private static final String DOCTOR = "doctor";
    private static final String LOCATION = "location";
    private static final String INFO = "INFO";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = TYPE)
    private String type;

    @ColumnInfo(name = HOUR)
    private String hour;

    @ColumnInfo(name = DATE)
    private String date;

    @ColumnInfo(name = STATUS)
    private boolean status;

    @ColumnInfo(name = RESULT)
    private String result;

    @ColumnInfo(name = DRUG_NAME)
    private String drugName;

    @ColumnInfo(name = DOSE)
    private double dose;

    @ColumnInfo(name = UNIT)
    private String unit;

    @ColumnInfo(name = DOCTOR)
    private String doctor;

    @ColumnInfo(name = LOCATION)
    private String location;

    @ColumnInfo(name = INFO)
    private String info;

    public Task() {

    }

    public Task(String type, String date, String hour) {
        this.type = type;
        this.date = date;
        this.hour = hour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public double getDose() {
        return dose;
    }

    public void setDose(double dose) {
        this.dose = dose;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
