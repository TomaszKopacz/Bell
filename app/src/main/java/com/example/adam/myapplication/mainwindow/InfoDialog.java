package com.example.adam.myapplication.mainwindow;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

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
        if (t.getType().equals("TEMPERATURE")) {
            return "Mierzona wartość:" + "\n" + t.getType() + "\n" +
                    "Jednostka:" + "\n" + t.getUnit() + "\n" +
                    "Wynik pomiaru " + "\n" +
                    t.getResult();

        } else if ( t.getType().equals("PRESSURE")){

            return "Mierzona wartość:" + "\n" + t.getType() + "\n" +
                    "Jednostka:" + "\n" + t.getUnit() + "\n" +
                    "Wynik pomiaru " + "\n" +
                    "Ciśnienie skurczowe: " + t.getResult() + "\n" +
                    "Ciśnienie rozkurczowe: " + t.getResult2();

        } else if (t.getType().equals("DRUG")) {
            return "Nazwa leku:" + "\n" + t.getDrugName() + "\n" +
                    "Dawka:" + "\n" + t.getDose();
        } else {
            return "Imię i nazwisko lekarza:" + "\n" +
                    t.getDoctor() + "\n" +
                    "Lokalizacja: " + "\n" +
                    t.getLocation() + "\n" +
                    "Dodatkowe informacje:" + "\n" +
                    t.getInfo();
        }
    }
}
