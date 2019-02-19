package com.example.adam.myapplication.data.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Task {

    public static final String SCORE = "score";
    public static final String DRUG = "drug";
    public static final String DOCTOR = "doctor";

    private static final String TYPE = "type";
    private static final String TIMESTAMP = "timestamp";
    private static final String INFO = "info";
    private static final String STATUS = "status";
    private static final String NOTE = "note";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = TYPE)
    private String type;

    @ColumnInfo(name = INFO)
    private String info;

    @ColumnInfo(name = TIMESTAMP)
    private Date timestamp;

    @ColumnInfo(name = STATUS)
    private boolean status;

    @ColumnInfo(name = NOTE)
    private String note;

    public Task(String type, Date timestamp) {
        this.type = type;
        this.timestamp = timestamp;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
