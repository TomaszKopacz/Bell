package com.example.adam.myapplication.mainwindow;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class DoneDialog {
    private Context context;

    public DoneDialog(Context context) {
        this.context = context;
    }

    public AlertDialog doneDialog(final MainFragment mainFragment, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Uwaga!");
        builder.setMessage("Czy zadanie zostało wykonane?");
        builder.setPositiveButton("TAK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mainFragment.setStatus(position, true);
                        //TOMEK TUTAJ ZAPIS
                    }
                });
        builder.setNegativeButton("NIE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mainFragment.setStatus(position, false);
                        //TOMEK TUTAJ ZAPIS
                    }
                });
        AlertDialog dialog = builder.create();
        return dialog;
    }

    public AlertDialog doneDialogWrongDate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Uwaga!");
        builder.setMessage("Zadanie nie może zostać wykonane."
        + "\n" + "Proszę wybrać poprawną datę.");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        return dialog;
    }
}
