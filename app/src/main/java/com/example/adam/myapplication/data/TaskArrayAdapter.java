package com.example.adam.myapplication.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.adam.myapplication.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaskArrayAdapter extends ArrayAdapter<Task> implements View.OnClickListener {
    private List<Task> tasks;

    private static Comparator<Task> tasksComparator = new Comparator<Task>() {

        public int compare(Task task1, Task task2) {
            String task1Hour = task1.getHour().toUpperCase();
            String task2Hour = task2.getHour().toUpperCase();
            return task1Hour.compareTo(task2Hour);

        }
    };

    private void sortTasks(List<Task> tasks) {
        Collections.sort(tasks, tasksComparator);
    }

    public TaskArrayAdapter(Context context, List<Task> tasks) {
        super(context, R.layout.list_view_line, tasks);
        this.tasks = tasks;
        sortTasks(tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView = inflater.inflate(R.layout.list_view_line, parent, false);
        TextView taskTime = (TextView) rowView.findViewById(R.id.task_time);
        TextView taskName = (TextView) rowView.findViewById(R.id.task_name);
        taskTime.setText(tasks.get(position).getHour());
        taskName.setText(tasks.get(position).getType());
        return rowView;
    }


    @Override
    public void onClick(View v) {

    }
}
