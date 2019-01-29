package com.example.adam.myapplication.ui.board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.data.Task;

import java.util.List;

public class TaskArrayAdapter extends ArrayAdapter<Task> implements View.OnClickListener {
    private List<Task> tasks;

    private static final String TEMPERATURE_TITLE = "Pomiar temperatury";
    private static final String PRESSURE_TITLE = "Pomiar ciśnienia";
    private static final String DRUG_TITLE = "Weź lek";
    private static final String EXAMINATION_TITLE = "Wizyta u lekarza";

    public TaskArrayAdapter(Context context, List<Task> tasks) {
        super(context, R.layout.list_view_line, tasks);
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView = inflater.inflate(R.layout.list_view_line, parent, false);

        TextView taskTime = (TextView) rowView.findViewById(R.id.task_time);
        TextView taskName = (TextView) rowView.findViewById(R.id.task_name);
        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkbox);

        Task tempTask = tasks.get(position);

        taskTime.setText(tempTask.getTimestamp().toString().substring(11, 16));

        switch (tempTask.getType()){
            case Task.MEASUREMENT_TEMPERATURE:
                taskName.setText(TEMPERATURE_TITLE);
                break;

            case Task.MEASUREMENT_PRESSURE:
                taskName.setText(PRESSURE_TITLE);
                break;

            case Task.DRUG:
                taskName.setText(DRUG_TITLE);
                break;

            case Task.EXAMINATION:
                taskName.setText(EXAMINATION_TITLE);
                break;
        }

        checkBox.setChecked(tempTask.getStatus());

        return rowView;
    }


    @Override
    public void onClick(View v) {

    }
}
