package com.example.adam.myapplication.ui.doctor.dialogs;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adam.myapplication.R;
import com.example.adam.myapplication.data.objects.Doctor;

import java.util.List;

public class ChooseDoctorAdapter extends RecyclerView.Adapter<ChooseDoctorAdapter.DoctorViewHolder> {
    private List<Doctor> list;
    private OnItemClickedListener listener;

    ChooseDoctorAdapter(List<Doctor> list, OnItemClickedListener listener) {
        this.list = list;
        this.listener = listener;
    }

    interface OnItemClickedListener {
        void itemClicked(View view, Doctor doctor);
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
        final Doctor doctor = list.get(position);
        setItemClickListener(holder, doctor);
        setItemFields(holder, doctor);
    }

    private void setItemClickListener(@NonNull DoctorViewHolder holder, final Doctor doctor) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClicked(v, doctor);
            }
        });
    }

    private void setItemFields(@NonNull DoctorViewHolder holder, Doctor doctor) {
        holder.setSpecialization(doctor.getSpecialization());
        holder.setName(doctor.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DoctorViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView specialization;
        private TextView name;

        DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;

            specialization = itemView.findViewById(R.id.specialization_text);
            name = itemView.findViewById(R.id.name_text);
        }

        public View getView() {
            return view;
        }

        void setSpecialization(String specialization) {
            this.specialization.setText(specialization);
        }

        public void setName(String name) {
            this.name.setText(name);
        }
    }
}
