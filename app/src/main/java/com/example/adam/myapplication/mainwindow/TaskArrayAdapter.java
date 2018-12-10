package com.example.adam.myapplication.mainwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.data.Task;

import java.util.List;

public class TaskArrayAdapter extends ArrayAdapter<Task> implements View.OnClickListener {
    private List<Task> tasks;

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
        taskTime.setText(tasks.get(position).getTimestamp().toString().substring(11, 16));
        taskName.setText(tasks.get(position).getType());
        return rowView;
    }


    @Override
    public void onClick(View v) {

    }
}
