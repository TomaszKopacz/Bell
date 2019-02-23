package com.example.adam.myapplication.ui.doctor.dialogs

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import com.example.adam.myapplication.R
import com.example.adam.myapplication.data.objects.Doctor

class NewDoctorDialog private constructor(private val dialog: AlertDialog) {

    companion object {
        const val SUCCESS = 1
        const val FAILURE = -1

        private const val TITLE = "New doctor"
        private const val OK_BUTTON = "ADD"
        private const val CANCEL_BUTTON = "CANCEL"
    }

    interface OnResultListener {
        fun onResult(status: Int, doctor: Doctor?, dialog: AlertDialog?, error: String?)
    }

    fun show() {
        dialog.show()
    }

    class Builder(private val fragment: Fragment) {
        private val view: View = LayoutInflater.from(fragment.context).inflate(R.layout.dialog_new_doctor, null)
        private val viewModel: NewDoctorViewModel = ViewModelProviders.of(fragment).get(NewDoctorViewModel::class.java)

        private val specializationText: String
            get() = (view.findViewById<View>(R.id.specialization_text) as EditText).text.toString()

        private val nameText: String
            get() = (view.findViewById<View>(R.id.name_text) as EditText).text.toString()

        private val locationText: String
            get() = (view.findViewById<View>(R.id.location_text) as EditText).text.toString()

        private var dialog: AlertDialog? = null
        private var listener: OnResultListener? = null

        init {
            setObservers()
        }

        private fun setObservers() {
            setDoctorCreatedObserver()
            setErrorObserver()
        }

        private fun setDoctorCreatedObserver() {
            viewModel.doctor.observe(fragment, Observer { doctor ->
                if (doctor != null) {
                    dialog?.dismiss()
                    listener?.onResult(SUCCESS, doctor, dialog, null)
                }
            })
        }

        private fun setErrorObserver() {
            viewModel.error.observe(fragment, Observer { error ->
                listener?.onResult(FAILURE, null, dialog, error)
            })
        }

        fun setResultListener(listener: OnResultListener): NewDoctorDialog.Builder {
            this.listener = listener

            return this
        }

        fun create(): NewDoctorDialog {
            dialog = AlertDialog.Builder(fragment.context)
                    .setTitle(TITLE)
                    .setView(view)
                    .setPositiveButton(OK_BUTTON, positiveButtonListener)
                    .setNegativeButton(CANCEL_BUTTON, negativeButtonListener)
                    .create()

            return NewDoctorDialog(dialog!!)
        }

        private val positiveButtonListener = DialogInterface.OnClickListener { _, _ ->
            viewModel.setSpecialization(specializationText)
            viewModel.setName(nameText)
            viewModel.setLocation(locationText)

            viewModel.submitDoctor()
        }

        private val negativeButtonListener = DialogInterface.OnClickListener { dialog, _ ->
            dialog.dismiss()
        }
    }
}
