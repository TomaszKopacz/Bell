package com.example.adam.myapplication.mainwindow;

import android.annotation.TargetApi;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
@TargetApi(24)
public class MainActivity extends AppCompatActivity {
    //ZMIENNE
    double input_value = 0;
    ListView list;
    TextView d_m;
    TextView year;
    FloatingActionButton plus;
    ArrayList <Task> tasks;
    private static TaskArrayAdapter adapter;
    final Task task1 = new Task("Badanie1", "12:00");
    final Task task2 = new Task("Badanie2", "11:00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  SET VIEW
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Próbny widok");

        //  VARIABLES
        list = (ListView) findViewById(R.id.lista);
        d_m = (TextView) findViewById(R.id.d_m);
        year = (TextView) findViewById(R.id.rok);
        plus = (FloatingActionButton) findViewById(R.id.fab);
        tasks = new ArrayList<>();

        //  ON CLICK LISTENERS
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                //tasks.add(task1);
                //createList(tasks);
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
    public void onStart()
    {
        super.onStart();
        tasks.add(task1);
        tasks.add(task2);
        createList(tasks);
        setCurrentDate();
    }

    @Override
    public void onResume() {
        super.onResume();
        createList(tasks);
        setCurrentDate();
    }
    @Override
    public void onRestart() {
        super.onRestart();
        createList(tasks);
        setCurrentDate();
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

    public void createList (ArrayList<Task> tasks)
    {
        //sortTasks(tasks);
        adapter= new TaskArrayAdapter(getApplicationContext(), tasks);
        list.setAdapter(adapter);

    }

    public void sortTasks( ArrayList <Task> tasks)
    {
        tasks.sort(tasksComparator);
    }
    public static Comparator<Task> tasksComparator = new Comparator<Task>() {

        public int compare(Task task1, Task task2) {
           // String StudentName1 = s1.getStudentname().toUpperCase();
            //String StudentName2 = s2.getStudentname().toUpperCase();
            String task1Hour = task1.getHour().toUpperCase();
            String task2Hour = task2.getHour().toUpperCase();
            //ascending order
            return task1Hour.compareTo(task2Hour);

        }};
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
    private void setCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        String currentYear = currentDate.substring(currentDate.length()-4, currentDate.length());
        currentDate = currentDate.substring(0, currentDate.length()-6);
        d_m.setText(currentDate);
        year.setText(currentYear);
    }

}
