package com.example.adam.myapplication.ui.board;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class AppInfoDialog {
    private Context context;

    public AppInfoDialog(Context context) {
        this.context = context;
    }

    public AlertDialog infoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Info");
        builder.setMessage("Wersja 1.0\n"
        +"Autorzy:\n"
        +"inż. Tomasz Kopacz\n"
        +"inż. Adam Jackowski");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog dialog = builder.create();
        return dialog;
    }

}

