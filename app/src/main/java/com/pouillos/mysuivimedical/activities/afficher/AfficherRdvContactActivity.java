package com.pouillos.mysuivimedical.activities.afficher;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;


import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mysuivimedical.R;
import com.pouillos.mysuivimedical.activities.NavDrawerActivity;
import com.pouillos.mysuivimedical.activities.photo.MakePhotoActivity;
import com.pouillos.mysuivimedical.activities.utils.DateUtils;

import com.pouillos.mysuivimedical.entities.RdvContact;

import com.pouillos.mysuivimedical.enumeration.TypePhoto;
import com.pouillos.mysuivimedical.interfaces.BasicUtils;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;
import icepick.State;

public class AfficherRdvContactActivity extends NavDrawerActivity implements BasicUtils, AdapterView.OnItemClickListener {
//todo reflechir sur les modifs des alertes en cas de modif. (sur les 3 types de rdv)

    RdvContact rdvContactSelected;
    @State
    Date date;

    //RdvContact rdvContact;
    TimePickerDialog picker;

    List<RdvContact> listRdvContactBD;
    ArrayAdapter adapter;

    @BindView(R.id.selectRdv)
    AutoCompleteTextView selectedRdv;
    @BindView(R.id.listRdv)
    TextInputLayout listRdv;

    @BindView(R.id.layoutContact)
    TextInputLayout layoutContact;
    @BindView(R.id.textContact)
    TextInputEditText textContact;
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

    @BindView(R.id.fabDelete)
    FloatingActionButton fabDelete;
    @BindView(R.id.fabEdit)
    FloatingActionButton fabEdit;
    @BindView(R.id.fabSave)
    FloatingActionButton fabSave;
    @BindView(R.id.fabCancel)
    FloatingActionButton fabCancel;
    @BindView(R.id.fabPhoto)
    FloatingActionButton fabPhoto;

    @BindView(R.id.activity_main_toolbar)
    Toolbar toolbar;

    @BindView(R.id.my_progressBar)
    ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_afficher_rdv_contact);

        this.configureToolBar();
        this.configureBottomView();

        ButterKnife.bind(this);

        progressBar.setVisibility(View.VISIBLE);

        hideAllFields();
        displayFabs();

        AfficherRdvContactActivity.AsyncTaskRunner runner = new AfficherRdvContactActivity.AsyncTaskRunner();
        runner.execute();

        setTitle("Mes Rdv Contact");

        traiterIntent();
        selectedRdv.setOnItemClickListener(this);

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

    public void traiterIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("rdvContactId")) {
            Long rdvContactId = intent.getLongExtra("rdvContactId", 0);
           // rdvContactSelected = RdvContact.findById(RdvContact.class, rdvContactId);
            rdvContactSelected = rdvContactDao.load(rdvContactId);
            selectedRdv.setText(rdvContactSelected.toString());
           // rdvContactSelected = listRdvContactBD.get(position);
            enableFields(false);
            displayFabs();
            fillAllFields();
            displayAllFields(false);
        }
    }


    public class AsyncTaskRunner extends AsyncTask<Void, Integer, Void> {

        protected Void doInBackground(Void...voids) {
            publishProgress(0);

            publishProgress(50);
            //listRdvContactBD = RdvContact.find(RdvContact.class,"utilisateur = ?",""+activeUser.getId());
            listRdvContactBD = rdvContactDao.loadAll();
            Collections.sort(listRdvContactBD);

            publishProgress(100);
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            if (listRdvContactBD.size() == 0) {
                Snackbar.make(fabSave, "Aucune correspondance", Snackbar.LENGTH_LONG).show();
                listRdv.setVisibility(View.GONE);
            } else {
                buildDropdownMenu(listRdvContactBD, AfficherRdvContactActivity.this,selectedRdv);
            }
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }

    @OnClick(R.id.fabPhoto)
    public void fabPhotoClick() {
        ouvrirActiviteSuivante(AfficherRdvContactActivity.this, MakePhotoActivity.class,"type", TypePhoto.Ordonnance.toString(),"itemId",rdvContactSelected.getId(),true);
    }

    @OnClick(R.id.fabSave)
    public void fabSaveClick() {
        //deleteItem(AfficherRdvContactActivity.this, rdvContactSelected, AfficherRdvContactActivity.class,false);
        rdvContactDao.delete(rdvContactSelected);
        rdvContactSelected.setDate(date);
        rdvContactSelected.setDateString(date.toString());
            if (textNote.getText() != null && !textNote.getText().toString().equalsIgnoreCase(rdvContactSelected.getDetail())) {
                rdvContactSelected.setDetail(textNote.getText().toString());
            }

            rdvContactDao.update(rdvContactSelected);
            enableFields(false);
            displayAllFields(false);
            displayFabs();
            Snackbar.make(fabSave, R.string.modification_saved, Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.fabCancel)
    public void fabCancelClick() {
        clearAllFields();
        resizeAllFields(false);
        enableFields(false);
        displayFabs();
        fillAllFields();
        displayAllFields(false);
    }

    @OnClick(R.id.fabEdit)
    public void fabEditClick() {
        enableFields(true);
        displayAllFields(true);
        resizeAllFields(true);


        fabEdit.hide();
        fabDelete.hide();
        fabSave.show();
        fabCancel.show();
    }

    @OnClick(R.id.fabDelete)
    public void fabDeleteClick() {

        rdvContactDao.delete(rdvContactSelected);
        //deleteItem(AfficherRdvContactActivity.this, rdvContactSelected, AfficherRdvContactActivity.class,true);
        //supprimer la/les notification(s)

           // supprimerNotification(rdvContactSelected, AfficherRdvContactActivity.this);
            //supprimerNotification(RdvNotificationBroadcastReceiver.class,rdvSelected.getDate(), rdvSelected.getContact(),AddRdvActivity.this);

    }



    private void resizeAllFields(boolean bool) {
        if (bool) {
            textNote.setMinWidth(getResources().getDimensionPixelOffset(R.dimen.field_min_width));

        } else {
            textNote.setMinWidth(0);

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        rdvContactSelected = listRdvContactBD.get(position);
        enableFields(false);
        displayFabs();
        fillAllFields();
        displayAllFields(false);
    }

    private void clearAllFields() {
        textContact.setText(null);
        textDate.setText(null);
        textHeure.setText(null);
        textNote.setText(null);
    }

    public void displayFabs() {
        fabSave.hide();
        fabCancel.hide();
        if (rdvContactSelected == null) {
            fabEdit.hide();
            fabDelete.hide();
            fabPhoto.hide();
        } else {
            fabEdit.show();
            fabDelete.show();
            fabPhoto.show();
        }
    }

    private void fillAllFields() {
        textContact.setText(rdvContactSelected.getContact().toString());
        textDate.setText(DateUtils.ecrireDate(rdvContactSelected.getDate()));
        textHeure.setText(DateUtils.ecrireHeure(rdvContactSelected.getDate()));
        textNote.setText(rdvContactSelected.getDetail());
    }

    private void enableFields(boolean bool) {
        layoutContact.setEnabled(false);
        layoutDate.setEnabled(bool);
        layoutHeure.setEnabled(bool);
        layoutNote.setEnabled(bool);
    }

    private void displayAllFields(boolean bool) {
        layoutContact.setVisibility(View.VISIBLE);
        layoutDate.setVisibility(View.VISIBLE);
        layoutHeure.setVisibility(View.VISIBLE);
        if (bool || !TextUtils.isEmpty(rdvContactSelected.getDetail())) {
            layoutNote.setVisibility(View.VISIBLE);
        } else {
            layoutNote.setVisibility(View.GONE);
        }

    }

    private void hideAllFields() {
        layoutContact.setVisibility(View.GONE);
        layoutDate.setVisibility(View.GONE);
        layoutHeure.setVisibility(View.GONE);
        layoutNote.setVisibility(View.GONE);
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

