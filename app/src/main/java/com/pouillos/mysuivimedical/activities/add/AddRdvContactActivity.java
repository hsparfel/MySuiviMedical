package com.pouillos.mysuivimedical.activities.add;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import com.pouillos.mysuivimedical.entities.Contact;
import com.pouillos.mysuivimedical.entities.Contact;
import com.pouillos.mysuivimedical.entities.RdvContact;
import com.pouillos.mysuivimedical.entities.RdvContact;

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
import icepick.State;

public class AddRdvContactActivity extends NavDrawerActivity implements BasicUtils, AdapterView.OnItemClickListener {

    Date date;

    Contact contactSelected;

    TimePickerDialog picker;

    @BindView(R.id.selectionContact)
    AutoCompleteTextView selectedContact;
    @BindView(R.id.listContact)
    TextInputLayout listContact;

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

    private List<Contact> listContactBD;

    @BindView(R.id.fabSave)
    FloatingActionButton fabSave;

    @BindView(R.id.my_progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_add_rdv_contact);

        this.configureToolBar();
        this.configureBottomView();

        ButterKnife.bind(this);

        progressBar.setVisibility(View.VISIBLE);

        AddRdvContactActivity.AsyncTaskRunnerBD runnerBD = new AddRdvContactActivity.AsyncTaskRunnerBD();
        runnerBD.execute();

        selectedContact.setOnItemClickListener(this);
        displayFabs();

        setTitle(getString(R.string.add_meeting_contact));

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
        contactSelected = listContactBD.get(position);
        layoutDate.setEnabled(true);
    }

    public class AsyncTaskRunnerBD extends AsyncTask<Void, Integer, Void> {

        protected Void doInBackground(Void...voids) {
            publishProgress(0);

            publishProgress(10);

            //listContactBD = Contact.listAll(Contact.class);
            listContactBD = contactDao.loadAll();
            Collections.sort(listContactBD);

            publishProgress(100);
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            buildDropdownMenu(listContactBD, AddRdvContactActivity.this,selectedContact);
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
        //List<RdvContact> listRdv = RdvContact.find(RdvContact.class,"utilisateur = ? and contact = ? and date = ?",""+activeUser.getId(),""+contactSelected.getId(),""+date.getTime());
        List<RdvContact> listRdv = rdvContactDao.queryRaw("where contact_Id = ? and date = ?",""+contactSelected.getId(),""+date.getTime());
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
        RdvContact rdvContact = new RdvContact();
        rdvContact.setDetail(textNote.getText().toString());
        rdvContact.setContact(contactSelected);
        rdvContact.setDate(date);
        rdvContact.setDateString(date.toString());
        rdvContactDao.insert(rdvContact);
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
