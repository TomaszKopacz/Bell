package com.example.adam.myapplication.mainwindow;

import com.example.adam.myapplication.data.TaskRepository;

public class MainPresenter {

    private MainFragment view;
    private TaskRepository repository;

    public MainPresenter(TaskRepository repository, MainFragment fragment){
        this.view = fragment;
        this.repository = repository;
    }

    public void onViewAttached(){

    }
}
