package com.example.adam.myapplication.ui.board;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.data.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> tasks;

    private static final String TEMPERATURE_TITLE = "Pomiar temperatury";
    private static final String PRESSURE_TITLE = "Pomiar ciśnienia";
    private static final String DRUG_TITLE = "Weź lek";
    private static final String EXAMINATION_TITLE = "Wizyta u lekarza";

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
        holder.setChecked(task.getStatus());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView hour;
        private TextView label;
        private CheckBox checkBox;

        TaskViewHolder(View itemView) {
            super(itemView);

            hour = itemView.findViewById(R.id.task_time);
            label = itemView.findViewById(R.id.task_name);
            checkBox = itemView.findViewById(R.id.checkbox);
        }

        void setHour(String hour) {
            this.hour.setText(hour);
        }

        void setLabel(String text) {
            switch (text){
                case Task.MEASUREMENT_TEMPERATURE:
                    label.setText(TEMPERATURE_TITLE);
                    break;

                case Task.MEASUREMENT_PRESSURE:
                    label.setText(PRESSURE_TITLE);
                    break;

                case Task.DRUG:
                    label.setText(DRUG_TITLE);
                    break;

                case Task.EXAMINATION:
                    label.setText(EXAMINATION_TITLE);
                    break;
            }
        }

        void setChecked(boolean checked) {
            this.checkBox.setChecked(checked);
        }
    }
}
