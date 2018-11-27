package com.example.adam.myapplication.mainwindow;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.data.Task;
import com.example.adam.myapplication.data.TaskArrayAdapter;
import com.example.adam.myapplication.newtaskwindow.AddTaskActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //ZMIENNE
    double input_value = 0;
    ListView list;
    TextView d_m;
    TextView year;
    FloatingActionButton plus;
    ArrayList <Task> tasks;
    private static TaskArrayAdapter adapter;
    final Task task1 = new Task("Badanie", "11:00", "12-12-18");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Próbny widok");
        //VARIABLES
        list = (ListView) findViewById(R.id.lista);
        d_m = (TextView) findViewById(R.id.d_m);
        year = (TextView) findViewById(R.id.rok);
        plus = (FloatingActionButton) findViewById(R.id.fab);
        tasks = new ArrayList<>();

        //ON CLICK LISTENERS
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                startAddTaskActivity();
            }
        });

        d_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputDialog().show();
            }
        });
        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasks.add(task1);
                createList(tasks);

            }
        });
        list.setLongClickable(true);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                inputDialog().show();
                return true;
            }
        });

    }

    private void startAddTaskActivity(){
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    @Override
    public void onStart()
    {
        super.onStart();
        tasks.add(task1);
        createList(tasks);
    }

    public AlertDialog inputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wprowadź wartość");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);
        builder.setPositiveButton("Wprowadź", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try {
                    input_value = Double.parseDouble(input.getText().toString());
                    Log.d("input_value", String.valueOf(input_value));
                } catch (NumberFormatException e) {
                    Log.d("input_value", "ZLA WARTOSC");
                    errorDialog().show();
                }
            }
        });
        builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        return dialog;
    }

    public AlertDialog errorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("BŁĄD!");
        builder.setMessage("DOPUSZCZALNY FORMAT TO LICZBA Z ZAKRESU 0 DO X")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog = builder.create();
        return dialog;
    }

    public void createList (ArrayList<Task> tasks)
    {
        adapter= new TaskArrayAdapter(getApplicationContext(), tasks);
        list.setAdapter(adapter);

    }
}
