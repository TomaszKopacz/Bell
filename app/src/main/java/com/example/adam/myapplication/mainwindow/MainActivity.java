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

/**
 * Main class which is created as first in the application
 * Requires KITKAT version of Android
 */
public class MainActivity extends AppCompatActivity {

    private MainFragment mainFragment;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    /**
     * Method called when the activity is creating.
     * It sets all views and set the default Fragment object
     */
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
    /**
     * Function creates and Fragment object and then set it as the default
     */
    private void setDefaultFragment() {
        changeFragment(mainFragment = new MainFragment());
    }
    /**
     * Function set the fragment visible
     * @param fragment Fragment object to be displayed
     */
    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
    /**
     * Function sets up a menuBar
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    /**
     * Callback function for doing some activity after choosing a menuItem
     * @param item Choosen MenuItem object
     * @return true
     */
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
            Log.i("TELM", "INFO");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /**
     * Private function for changing an activity into a ChartActivity
     */
    private void startChartActivity() {
        Intent intent = new Intent(this, ChartActivity.class);
        startActivity(intent);
    }
}
