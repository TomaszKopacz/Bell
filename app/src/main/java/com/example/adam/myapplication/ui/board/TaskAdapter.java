package com.example.adam.myapplication.ui.board;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.data.objects.Task;

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
        View itemView = inflater.inflate(R.layout.item_task, parent, false);

        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);

        holder.setHour(task.getTimestamp().toString().substring(11, 16));
        holder.setInfo(task.getInfo());
        holder.setStatus(task.getStatus());
        holder.setType(task.getType());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView hour;
        private TextView info;
        private TextView status;
        private TextView type;

        TaskViewHolder(View itemView) {
            super(itemView);

            hour = itemView.findViewById(R.id.task_time);
            info = itemView.findViewById(R.id.task_info);
            type = itemView.findViewById(R.id.task_type);
            status = itemView.findViewById(R.id.task_status);
        }

        void setHour(String hour) {
            this.hour.setText(hour);
        }

        void setInfo(String text) {
            this.info.setText(text);
        }

        void setType(String text) {
            this.type.setText(text);
        }

        void setStatus(boolean status) {
            if (!status)
                this.status.setText(R.string.status_not_done);
            else
                this.status.setText(R.string.status_done);
        }
    }
}
