package com.example.adam.myapplication.ui.doctor.create

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adam.myapplication.R
import com.example.adam.myapplication.data.objects.Doctor
import com.example.adam.myapplication.ui.doctor.dialogs.ChooseDoctorDialog
import com.example.adam.myapplication.ui.doctor.dialogs.NewDoctorDialog
import com.example.adam.myapplication.utils.Formatter
import com.example.adam.myapplication.utils.Picker
import kotlinx.android.synthetic.main.fragment_doctor_task.*
import java.util.*

class DoctorTaskFragment : Fragment() {

    private lateinit var layout: View
    private lateinit var viewModel: DoctorTaskViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        layout = inflater.inflate(R.layout.fragment_doctor_task, container, false)
        viewModel = ViewModelProviders.of(this).get(DoctorTaskViewModel::class.java)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        setListeners()
    }

    private fun setObservers() {
        setDoctorObserver()
        setDateObserver()
        setEndDateObserver()
    }

    private fun setDoctorObserver() {
        viewModel.doctor.observe(this, Observer<Doctor> { doctor ->
            name_text.text = doctor?.specialization ?: ""
        })
    }

    private fun setDateObserver() {
        viewModel.date.observe(this, Observer<Calendar> { calendar ->
            date_text.text = Formatter.getDateString(calendar)
            time_text.text = Formatter.getTimeString(calendar)
        })
    }

    private fun setEndDateObserver() {
        viewModel.endDate.observe(this, Observer<Calendar> { calendar ->
            date_end_text.text = Formatter.getDateString(calendar)
        })
    }

    private fun setListeners() {
        setDoctorListener()
        setDateListener()
        setTimeListener()
        setSwitchListener()
        setEndDateListener()
        setMoreListener()
        setSubmitListener()
    }

    private fun setDoctorListener() {
        name_text.setOnClickListener {
            showChooseDoctorDialog()
        }
    }

    private fun setDateListener() {
        date_text.setOnClickListener {
            Picker.showDatePicker(activity) { _, year, month, dayOfMonth ->
                viewModel.dateSet(year, month, dayOfMonth)
            }
        }
    }

    private fun setTimeListener() {
        time_text.setOnClickListener {
            Picker.showTimePicker(activity) { _, hour, minute ->
                viewModel.timeSet(hour, minute)
            }
        }
    }

    private fun setSwitchListener() {
        switch_repeat.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                date_expandable.expand()
            else
                date_expandable.collapse()
        }
    }

    private fun setEndDateListener() {
        date_end_text.setOnClickListener {
            Picker.showDatePicker(activity) { _, year, month, day ->
                viewModel.endDateSet(year, month, day)
            }
        }
    }

    private fun setMoreListener() {
        more_label.setOnClickListener {
            toggleMoreExpandable()
        }
    }

    private fun toggleMoreExpandable() {
        if (more_expandable.isExpanded)
            more_expandable.collapse()
        else
            more_expandable.expand()
    }

    private fun setSubmitListener() {
        submit_button!!.setOnClickListener {
            viewModel.noteSet(note_input.text.toString())
            viewModel.insertTask()
        }
    }

    private fun showChooseDoctorDialog() {
        val builder = ChooseDoctorDialog.Builder(this)

        builder.setItemClickListener { _, doctor ->
            viewModel.doctorChosen(doctor)
        }

        builder.showNewDoctorButton {
            showCreateDoctorDialog()
        }

        builder.create().show()
    }

    private fun showCreateDoctorDialog() {
        NewDoctorDialog(activity) { doctor ->
            viewModel.doctorChosen(doctor)
        }.create().show()
    }
}
