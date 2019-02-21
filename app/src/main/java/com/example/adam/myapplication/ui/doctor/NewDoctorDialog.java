package com.example.adam.myapplication.ui.doctor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.adam.myapplication.R;

public class NewDoctorDialog {

    private static final String TITLE = "New doctor";
    private static final String OK_BUTTON = "ADD";
    private static final String CANCEL_BUTTON = "CANCEL";

    private Context context;

    public NewDoctorDialog(Context context) {
        this.context = context;
    }

    public AlertDialog create() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_new_doctor, null);
        EditText specializationText = view.findViewById(R.id.specialization_text);
        EditText nameText = view.findViewById(R.id.name_text);
        EditText locationText = view.findViewById(R.id.location_text);

        return builder
                .setTitle(TITLE)
                .setView(view)
                .setPositiveButton(OK_BUTTON, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(CANCEL_BUTTON, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
    }
}
