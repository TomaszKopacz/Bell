package com.example.adam.myapplication.ui.lists

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.adam.myapplication.R
import com.example.adam.myapplication.data.objects.Doctor

import java.util.ArrayList

class ChooseDoctorAdapter internal constructor(private val listener: OnItemClickListener<Doctor>) : RecyclerView.Adapter<ChooseDoctorAdapter.DoctorViewHolder>() {

    private var list: List<Doctor> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): DoctorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_choose_doctor, parent, false)

        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = list[position]

        holder.setSpecialization(doctor.specialization ?: "")
        holder.setName(doctor.name ?: "")
        holder.setListener(listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun loadDoctors(list: List<Doctor>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val specialization: TextView = itemView.findViewById(R.id.specialization_text)
        private val name: TextView = itemView.findViewById(R.id.name_text)

        fun setListener(listener: OnItemClickListener<Doctor>) {
            itemView.setOnClickListener { listener.onItemClick(itemView, list[adapterPosition]) }
        }

        fun setSpecialization(specialization: String) {
            this.specialization.text = specialization
        }

        fun setName(name: String) {
            this.name.text = name
        }
    }
}
