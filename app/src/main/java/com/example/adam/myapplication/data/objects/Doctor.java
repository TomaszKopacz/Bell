package com.example.adam.myapplication.data.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Doctor {

    private static final String SPECIALIZATION = "specialization";
    private static final String NAME = "name";
    private static final String LOCATION = "location";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = SPECIALIZATION)
    private String specialization;

    @ColumnInfo(name = NAME)
    private String name;

    @ColumnInfo(name = LOCATION)
    private String location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}