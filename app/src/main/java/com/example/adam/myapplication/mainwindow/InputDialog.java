package com.example.adam.myapplication.mainwindow;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;

import java.util.Calendar;

public class InputDialog {

    private Context context;


    private double input_value = 0;

    public InputDialog(Context context) {
        this.context = context;
    }

    public AlertDialog inputDialog(final MainFragment mainFragment, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Wprowadź wartość");
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);
        builder.setPositiveButton("Wprowadź", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                try {
                    input_value = Double.parseDouble(input.getText().toString());
                    // TOMEK TUTAJ ZAPIS
                         mainFragment.setResult(position, input_value);

                } catch (NumberFormatException e) {
                    errorDialog().show();
                }

            }
        });
        builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        return dialog;
    }

    public AlertDialog errorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("BŁĄD!");
        builder.setMessage("W polu można wpisać jedynie liczbę")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        return dialog;
    }

    public AlertDialog errorTemperatureDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("BŁĄD!");
        builder.setMessage("Błędna wartość temperatury.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        return dialog;
    }

    public AlertDialog errorPressureDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("BŁĄD!");
        builder.setMessage("Błędna wartość ciśnienia.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        return dialog;
    }

    public double getInput_value() {
        return input_value;
    }
}

