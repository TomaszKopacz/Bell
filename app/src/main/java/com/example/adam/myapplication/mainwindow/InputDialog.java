package com.example.adam.myapplication.mainwindow;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Calendar;

public class InputDialog {

    private Context context;


    private double input_value = 0;
    private double input_value2 = 0;

    public InputDialog(Context context) {
        this.context = context;
    }

    public AlertDialog inputTemperatureDialog(final MainFragment mainFragment, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Wprowadź wartość");
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);
        builder.setPositiveButton("Wprowadź", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                try {
                    input_value = Double.parseDouble(input.getText().toString());
                    mainFragment.setTemperatureResult(position, input_value);

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

    public AlertDialog inputPressureDialog(final MainFragment mainFragment, final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Wprowadź wartość");

        final LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText inputSystolic = new EditText(context);
        inputSystolic.setHint("Ciśnienie skurczowe");
        inputSystolic.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

        final EditText inputDiastolic = new EditText(context);
        inputDiastolic.setHint("Ciśnienie rozkurczowe");
        inputDiastolic.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

        layout.addView(inputSystolic);
        layout.addView(inputDiastolic);

        builder.setView(layout);
        builder.setPositiveButton("Wprowadź", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    input_value = Double.parseDouble(inputSystolic.getText().toString());
                    input_value2 = Double.parseDouble(inputDiastolic.getText().toString());

                    mainFragment.setPressureResult(position, input_value, input_value2);

                } catch (Exception e){
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

