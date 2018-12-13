package com.example.adam.myapplication.mainwindow;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.utils.DatetimePicker;

public class MainActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener dateSetListener;

    public MainFragment getMainFragment() {
        return mainFragment;
    }

    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  SET VIEW
        setContentView(R.layout.activity_main_cointainer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Próbny widok");
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_calendar) {
            DatetimePicker.showDatePicker(this, mainFragment.getDateSetListener());
        }
        if (id == R.id.action_calendar) {
            Log.i("TELM", "CALENDAR");
            return true;

        } else if (id == R.id.action_chart){
            Log.i("TELM", "CHART");
            return true;

        } else if (id == R.id.action_info){
            Log.i("TELM", "INFO");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
