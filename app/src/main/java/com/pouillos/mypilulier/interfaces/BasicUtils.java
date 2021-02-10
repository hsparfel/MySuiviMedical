package com.pouillos.mypilulier.interfaces;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

public interface BasicUtils {



    public default int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

    public default void retourPagePrecedente(Intent intent) {};
    public default void retourPagePrecedente() {};

    public default boolean isExistant(TextView textView) { return false; };

    public default boolean isRempli(TextView textView) { return false; };
    public default boolean isRempli(TextView textView, Date date) {
        if (date == null) {
            textView.setError("Sélection Obligatoire");
            return false;
        } else {
            return true;
        }
    };
    public default boolean isRempli(TextView textView, String string) { return false; };
    public default boolean isRempli(Spinner... args) {
        if (args[0].getSelectedItem().toString().equals("sélectionner")) {
            alertOnSpinners();
            return false;
        } else {
            return true;
        }
    };

    public default boolean isValid(TextView textView) { return false; };


    public default boolean isExistant() { return false; };
    public default boolean checkFields() { return false; };
    public default void traiterIntent() {};
    public default void showDatePickerDialog(View v) {};
    public default void showTimePickerDialog(View v) {};
    public default void displayFabs() {};
    public default void alertOnSpinners() {};
    public default void alertOffSpinners() {};
    public default void alertOnSpinner(Spinner spinner) {
        TextView errorText = (TextView) spinner.getSelectedView();
        errorText.setTextColor(Color.RED);
    }
    public default void alertOffSpinner(Spinner spinner) {
        TextView errorText = (TextView)spinner.getSelectedView();
        errorText.setTextColor(Color.BLACK);
    }


    public default void saveToDb(TextView... args) {};
    public default void saveToDb(TextView textNom, Date date, String sexe) {};
    public default void saveToDb() {};
    public default void createSpinners() {};




    public default void enableItems(Boolean bool) {};
}
