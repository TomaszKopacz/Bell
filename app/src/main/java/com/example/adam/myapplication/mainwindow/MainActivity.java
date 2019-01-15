package com.example.adam.myapplication.mainwindow;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.chartwindow.ChartActivity;
import com.example.adam.myapplication.utils.DatetimePicker;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private MainFragment mainFragment;
    private AppInfoDialog appInfoDialog;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  SET VIEW
        setContentView(R.layout.activity_main_cointainer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Kalendarz");
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        changeFragment(mainFragment = new MainFragment());
    }

    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_calendar) {
            DatetimePicker.showDatePicker(this, mainFragment.getDateSetListener());
            return true;

        } else if (id == R.id.action_chart) {
            startChartActivity();
            return true;

        } else if (id == R.id.action_info) {
            appInfoDialog = new AppInfoDialog(this);
            appInfoDialog.infoDialog().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startChartActivity() {
        Intent intent = new Intent(this, ChartActivity.class);
        startActivity(intent);
    }
}
