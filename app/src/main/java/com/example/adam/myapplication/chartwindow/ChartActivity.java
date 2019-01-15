package com.example.adam.myapplication.chartwindow;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.adam.myapplication.R;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        changeFragment(new ChartFragment());
    }

    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.chart_fragment_container, fragment);
        transaction.commit();
    }
}
