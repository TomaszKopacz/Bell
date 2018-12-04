package com.example.adam.myapplication.mainwindow;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

public class InputDialog {

    private MainFragment fragment;

    public InputDialog(MainFragment fragment) {
        this.fragment = fragment;
    }

    public AlertDialog inputDialog() {
        MainActivity mFragAct = fragment.getmFragAct();
        AlertDialog.Builder builder = new AlertDialog.Builder(mFragAct);
        builder.setTitle("Wprowadź wartość");
        final EditText input = new EditText(mFragAct);
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);
        builder.setPositiveButton("Wprowadź", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try {
                    fragment.input_value = Double.parseDouble(input.getText().toString());
                    Log.d("input_value", String.valueOf(fragment.input_value));
                } catch (NumberFormatException e) {
                    Log.d("input_value", "ZLA WARTOSC");
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
        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getmFragAct());
        builder.setTitle("BŁĄD!");
        builder.setMessage("DOPUSZCZALNY FORMAT TO LICZBA Z ZAKRESU 0 DO X")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog = builder.create();
        return dialog;
    }
}

