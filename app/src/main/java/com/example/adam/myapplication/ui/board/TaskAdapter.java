package com.example.adam.myapplication.ui.board;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.data.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> tasks;

    TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.task_item, parent, false);

        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);

        holder.setHour(task.getTimestamp().toString().substring(11, 16));
        holder.setLabel(task.getType());
        holder.setResult(task.getResult());
        holder.setInfo(task.getInfo());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView hour;
        private TextView label;
        private TextView result;
        private TextView info;

        TaskViewHolder(View itemView) {
            super(itemView);

            hour = itemView.findViewById(R.id.task_time);
            label = itemView.findViewById(R.id.task_name);
            result = itemView.findViewById(R.id.task_result);
            info = itemView.findViewById(R.id.task_info);
        }

        void setHour(String hour) {
            this.hour.setText(hour);
        }

        void setLabel(String text) {
            this.label.setText(text);
        }

        void setResult(double result) {
            if (result == 0.0d)
                this.result.setText(R.string.no_result_text);
            else
                this.result.setText(String.valueOf(result));
        }

        void setInfo(String text) {
            if (text == null || text.isEmpty())
                this.info.setVisibility(View.INVISIBLE);

            else {
                this.info.setVisibility(View.VISIBLE);
                this.info.setText(text);
            }
        }
    }
}
