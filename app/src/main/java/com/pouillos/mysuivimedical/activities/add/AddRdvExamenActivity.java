package com.pouillos.mysuivimedical.activities.add;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;


import androidx.annotation.RequiresApi;

import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mysuivimedical.R;
import com.pouillos.mysuivimedical.activities.NavDrawerActivity;
import com.pouillos.mysuivimedical.entities.Examen;
import com.pouillos.mysuivimedical.entities.RdvExamen;

import com.pouillos.mysuivimedical.interfaces.BasicUtils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;

public class AddRdvExamenActivity extends NavDrawerActivity implements Serializable, BasicUtils, AdapterView.OnItemClickListener {

    Date date;

    Examen examenSelected;

    TimePickerDialog picker;

    @BindView(R.id.selectionExamen)
    AutoCompleteTextView selectedExamen;
    @BindView(R.id.listExamen)
    TextInputLayout listExamen;

    @BindView(R.id.layoutDate)
    TextInputLayout layoutDate;
    @BindView(R.id.textDate)
    TextInputEditText textDate;
    @BindView(R.id.layoutHeure)
    TextInputLayout layoutHeure;
    @BindView(R.id.textHeure)
    TextInputEditText textHeure;
    @BindView(R.id.layoutNote)
    TextInputLayout layoutNote;
    @BindView(R.id.textNote)
    TextInputEditText textNote;

    private List<Examen> listExamenBD;

    @BindView(R.id.fabSave)
    FloatingActionButton fabSave;

    @BindView(R.id.my_progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_add_rdv_examen);

        this.configureToolBar();
        this.configureBottomView();

        ButterKnife.bind(this);

        progressBar.setVisibility(View.VISIBLE);

        AddRdvExamenActivity.AsyncTaskRunnerBD runnerBD = new AddRdvExamenActivity.AsyncTaskRunnerBD();
        runnerBD.execute();

        selectedExamen.setOnItemClickListener(this);
        displayFabs();
        
        setTitle(getString(R.string.add_meeting_exam));

        layoutDate.setEnabled(false);
        layoutHeure.setEnabled(false);

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                textDate.setText(materialDatePicker.getHeaderText());
                date = new Date();
                date.setTime((Long) selection);
                layoutHeure.setEnabled(true);
            }
        });

        materialTimePicker.addOnPositiveButtonClickListener(dialog -> {
            int newHour = materialTimePicker.getHour();
            int newMinute = materialTimePicker.getMinute();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, newHour);
            calendar.set(Calendar.MINUTE, newMinute);
            date = calendar.getTime();
            textHeure.setText(ecrireHeure(newHour,newMinute));
        });
    }

    public void showTimePicker(View view) {
        materialTimePicker.show(getSupportFragmentManager(),"TIME_PICKER");
    }

    public void showDatePicker(View view) {
        materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        examenSelected = listExamenBD.get(position);
        layoutDate.setEnabled(true);

    }

    public class AsyncTaskRunnerBD extends AsyncTask<Void, Integer, Void> {

        protected Void doInBackground(Void...voids) {
            publishProgress(0);

            publishProgress(10);
            listExamenBD = examenDao.loadAll();
            Collections.sort(listExamenBD);

            publishProgress(100);
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
                buildDropdownMenu(listExamenBD, AddRdvExamenActivity.this,selectedExamen);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }


    public void displayFabs() {
            fabSave.show();
    }


    public boolean isExistant() {
        boolean bool;
        bool = false;
        List<RdvExamen> listRdv = rdvExamenDao.queryRaw("where examen_Id = ? and date = ?", ""+examenSelected.getId(),""+date.getTime());
        if (listRdv.size() != 0) {
            bool = true;
        }
        return bool;
    }

    public boolean checkFields(){
        boolean bool;
        if (!isFilled(textDate)){
            layoutDate.setError("Obligatoire");
        } else {
            layoutDate.setError(null);
        }
        if (!isFilled(textHeure)){
            layoutHeure.setError("Obligatoire");
        } else {
            layoutHeure.setError(null);
        }

        bool = isFilled(date) && isFilled(textHeure);
        return bool;
    }

    @OnClick(R.id.fabSave)
    public void fabSaveClick() {
        if (checkFields()) {
            if (!isExistant()) {
                saveToDb();
                rouvrirActiviteAccueil(this,true);
            } else {
                Snackbar.make(fabSave, "Rdv déjà existant", Snackbar.LENGTH_LONG).show();
            }
        } else {
            Snackbar.make(fabSave, "Saisie non valide", Snackbar.LENGTH_LONG).show();
        }

    }


    public void saveToDb() {
        RdvExamen rdvExamen = new RdvExamen();
        rdvExamen.setDetail(textNote.getText().toString());
        rdvExamen.setExamen(examenSelected);
        rdvExamen.setDate(date);
        rdvExamen.setDateString(date.toString());
        rdvExamenDao.insert(rdvExamen);
        Snackbar.make(fabSave, "Rdv Enregistré", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
