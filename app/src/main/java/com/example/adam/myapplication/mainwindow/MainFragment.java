package com.example.adam.myapplication.mainwindow;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class MainFragment extends Fragment {

    //ZMIENNE
    double input_value = 0;
    private ListView list;
    private TextView d_m;
    private TextView year;
    private FloatingActionButton plus;
    private static TaskArrayAdapter adapter;
    private FragmentActivity mFragAct;
    private Intent mIntent;

    public MainFragment() {

    }

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        getLayoutViews(view);
        setListeners();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFragAct = (FragmentActivity) getActivity();
        mIntent = mFragAct.getIntent();
    }

    private void getLayoutViews(View view)
    {
        list = (ListView) view.findViewById(R.id.lista);
        d_m = (TextView) view.findViewById(R.id.d_m);
        year = (TextView) view.findViewById(R.id.rok);
        plus = (FloatingActionButton) view.findViewById(R.id.fab);
    }

    private void setListeners()
    {
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
        Intent intent = new Intent(mFragAct, AddTaskActivity.class);
        startActivity(intent);
    }
        public void downloadTasks(){
            TaskRepository repository = ((App)mFragAct.getApplication()).getTaskRepository();
            repository.gatAll().observe(mFragAct, new Observer<List<Task>>() {
                @Override
                public void onChanged(@Nullable List<Task> tasks) {
                    createList(tasks);
                }
            });
        }

        public void createList(List<Task> tasks)
        {
            adapter = new TaskArrayAdapter(mFragAct.getApplicationContext(), tasks);
            list.setAdapter(adapter);
        }


        public AlertDialog inputDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(mFragAct);
            builder.setTitle("Wprowadź wartość");
            final EditText input = new EditText(mFragAct);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(mFragAct);
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
