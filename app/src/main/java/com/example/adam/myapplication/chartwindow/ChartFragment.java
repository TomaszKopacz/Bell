package com.example.adam.myapplication.chartwindow;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.app.App;
import com.example.adam.myapplication.data.TaskRepository;

public class ChartFragment extends Fragment implements ChartContract.ChartView {

    private ChartPresenter presenter;

    public ChartFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        getComponents(view);
        setPresenter();
        setListeners();

        return view;
    }

    private void getComponents(View view) {

    }

    private void setPresenter() {
        TaskRepository repository = ((App) getActivity().getApplication()).getTaskRepository();
        presenter = new ChartPresenter(this, repository);
    }

    private void setListeners() {

    }

    @Override
    public void drawChart() {

    }
}
