package com.example.adam.myapplication.ui.doctor.dialogs;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.data.objects.Doctor;
import com.example.adam.myapplication.ui.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class ChooseDoctorAdapter extends RecyclerView.Adapter<ChooseDoctorAdapter.DoctorViewHolder> {

    private List<Doctor> list = new ArrayList<>();
    private OnItemClickListener<Doctor> listener;

    ChooseDoctorAdapter(OnItemClickListener<Doctor> listener) {
        this.listener = listener;
    }

    void loadDoctors(List<Doctor> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_choose_doctor, parent, false);

        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = list.get(position);

        holder.setSpecialization(doctor.getSpecialization());
        holder.setName(doctor.getName());
        holder.setListener(listener);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class DoctorViewHolder extends RecyclerView.ViewHolder {

        private TextView specialization;
        private TextView name;

        DoctorViewHolder(@NonNull View itemView) {
            super(itemView);

            specialization = itemView.findViewById(R.id.specialization_text);
            name = itemView.findViewById(R.id.name_text);
        }

        void setListener(final OnItemClickListener<Doctor> listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, list.get(getAdapterPosition()));
                }
            });
        }

        void setSpecialization(String specialization) {
            this.specialization.setText(specialization);
        }

        public void setName(String name) {
            this.name.setText(name);
        }
    }
}
