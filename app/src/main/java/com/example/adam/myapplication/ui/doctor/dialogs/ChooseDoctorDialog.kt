package com.example.adam.myapplication.ui.doctor.dialogs

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.adam.myapplication.R
import com.example.adam.myapplication.data.objects.Doctor
import com.example.adam.myapplication.ui.lists.ChooseDoctorAdapter
import com.example.adam.myapplication.ui.lists.OnItemClickListener

class ChooseDoctorDialog private constructor(private val dialog: AlertDialog) {

    companion object {
        private const val TITLE = "Choose doctor"
    }

    fun show() {
        dialog.show()
    }

    class Builder (private val fragment: Fragment) {

        private val view: View = LayoutInflater.from(fragment.context).inflate(R.layout.dialog_choose_doctor, null)
        private val viewModel: ChooseDoctorViewModel = ViewModelProviders.of(fragment).get(ChooseDoctorViewModel::class.java)

        private var listener: OnItemClickListener<Doctor>? = null
        private var dialog: AlertDialog? = null

        private val itemListener = OnItemClickListener<Doctor> { view, doctor ->
            dialog?.dismiss()
            listener?.onItemClick(view, doctor)
        }

        private val adapter: ChooseDoctorAdapter = ChooseDoctorAdapter(itemListener)

        init {
            initRecyclerView()
            setObservers()
        }

        private fun initRecyclerView() {
            val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
            recyclerView.layoutManager = LinearLayoutManager(fragment.context)
            recyclerView.adapter = adapter
        }

        private fun setObservers() {
            setDoctorsObserver()
        }

        private fun setDoctorsObserver() {
            viewModel.allDoctors.observe(fragment, Observer { list -> adapter.loadDoctors(list ?: ArrayList()) })
        }

        fun setItemClickListener(listener: OnItemClickListener<Doctor>): Builder {
            this.listener = listener

            return this
        }

        fun showNewDoctorButton(listener: View.OnClickListener): Builder {
            val newDoctorView = view.findViewById<TextView>(R.id.add_new_doctor_view)
            newDoctorView.visibility = View.VISIBLE

            newDoctorView.setOnClickListener { view ->
                dialog?.dismiss()
                listener.onClick(view)
            }

            return this
        }

        fun create(): ChooseDoctorDialog {
            dialog = AlertDialog.Builder(fragment.context)
                    .setTitle(TITLE)
                    .setView(view)
                    .create()

            return ChooseDoctorDialog(dialog!!)
        }
    }
}

