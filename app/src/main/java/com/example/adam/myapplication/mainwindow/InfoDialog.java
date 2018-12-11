package com.example.adam.myapplication.mainwindow;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

import com.example.adam.myapplication.data.Task;

public class InfoDialog {
    private Context context;

    public InfoDialog(Context context) {
        this.context = context;
    }

    public AlertDialog infoDialog(Task t) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Szczegóły");
        builder.setMessage(taskInfoText(t));
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog dialog = builder.create();
        return dialog;
    }

    private String taskInfoText(Task t) {
        if(t.getType().equals("TEMPERATURE") || t.getType().equals("PRESSURE"))
        {
            return "POMIAR";
        }
        else if (t.getType().equals("DRUG"))
        {
            return "LEKI";
        }
        else
        {
            return "BADANIE";
        }
    }
}
