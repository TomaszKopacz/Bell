package com.example.adam.myapplication.mainwindow;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.app.App;
import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskArrayAdapter;
import com.example.adam.myapplication.data.TaskRepository;
import com.example.adam.myapplication.newtaskwindow.AddTaskActivity;
import com.example.adam.myapplication.newtaskwindow.TaskTypeFragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
@TargetApi(24)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  SET VIEW
        setContentView(R.layout.activity_main_cointainer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Próbny widok");
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        changeFragment( new MainFragment());
    }

    public void changeFragment(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
        fragment.onAttach(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onStart()
    {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onRestart() {
        super.onRestart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
