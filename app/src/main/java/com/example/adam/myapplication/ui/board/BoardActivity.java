package com.example.adam.myapplication.ui.board;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.ui.chart.ChartActivity;
import com.example.adam.myapplication.utils.DatetimePicker;

public class BoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_cointainer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Kalendarz pacjenta");
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        changeFragment(new BoardFragment());
    }

    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_chart) {
            showChart();
            return true;

        } else if (id == R.id.action_info) {
            showInfo();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showInfo() {
        AppInfoDialog appInfoDialog = new AppInfoDialog(this);
        appInfoDialog.infoDialog().show();
    }

    private void showChart() {
        Intent intent = new Intent(this, ChartActivity.class);
        startActivity(intent);
    }
}
