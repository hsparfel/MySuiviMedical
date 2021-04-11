package com.pouillos.mysuivimedical.activities.afficher;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
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

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mysuivimedical.MyApp;
import com.pouillos.mysuivimedical.R;
import com.pouillos.mysuivimedical.activities.NavDrawerActivity;
import com.pouillos.mysuivimedical.activities.add.AddRdvAnalyseActivity;
import com.pouillos.mysuivimedical.activities.add.AddRdvContactActivity;
import com.pouillos.mysuivimedical.activities.add.AddRdvExamenActivity;
import com.pouillos.mysuivimedical.activities.photo.MakePhotoActivity;
import com.pouillos.mysuivimedical.activities.utils.DateUtils;
import com.pouillos.mysuivimedical.entities.Analyse;
import com.pouillos.mysuivimedical.entities.Contact;
import com.pouillos.mysuivimedical.entities.Examen;
import com.pouillos.mysuivimedical.entities.RdvAnalyse;
import com.pouillos.mysuivimedical.entities.RdvContact;
import com.pouillos.mysuivimedical.entities.RdvExamen;
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

public class AfficherRdvActivity extends NavDrawerActivity implements BasicUtils, AdapterView.OnItemClickListener {


    RdvAnalyse rdvAnalyseSelected;
    RdvExamen rdvExamenSelected;
    RdvContact rdvContactSelected;

    @State
    Date dateEnCours;

    //@State
    //String noteEncours;


    //@State
    //Date date;

    TimePickerDialog picker;

    List<RdvAnalyse> listRdvAnalyse;
    List<RdvExamen> listRdvExamen;
    List<RdvContact> listRdvContact;
    List<?> listRdvBD;
    ArrayAdapter adapter;

    @BindView(R.id.selectRdv)
    AutoCompleteTextView selectedRdv;
    @BindView(R.id.listRdv)
    TextInputLayout listRdv;

    @BindView(R.id.layoutTypeRdv)
    TextInputLayout layoutTypeRdv;
    @BindView(R.id.textTypeRdv)
    TextInputEditText textTypeRdv;
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
    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;
    @BindView(R.id.fabPhoto)
    FloatingActionButton fabPhoto;

    @BindView(R.id.activity_main_toolbar)
    Toolbar toolbar;

    @BindView(R.id.my_progressBar)
    ProgressBar progressBar;

    @State
    boolean booleanContact = false;
    @State
    boolean booleanAnalyse = false;
    @State
    boolean booleanExamen = false;

    @BindView(R.id.chipContact)
    Chip chipContact;
    @BindView(R.id.chipAnalyse)
    Chip chipAnalyse;
    @BindView(R.id.chipExamen)
    Chip chipExamen;
    @BindView(R.id.chipGroupType)
    ChipGroup chipGroupType;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_afficher_rdv);
        // 6 - Configure all views
        this.configureToolBar();
        this.configureBottomView();

        ButterKnife.bind(this);

        //progressBar.setVisibility(View.VISIBLE);

        hideAllFields();
        displayFabs();

        //AfficherRdvActivity.AsyncTaskRunner runner = new AfficherRdvActivity.AsyncTaskRunner();
        //runner.execute();

        setTitle("Mes Rdv");
        //traiterIntent();
        selectedRdv.setOnItemClickListener(this);



        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                textDate.setText(materialDatePicker.getHeaderText());
                //date = new Date();
                int hour = dateEnCours.getHours();
                int minute = dateEnCours.getMinutes();
                dateEnCours.setTime((Long) selection);
                majDate(dateEnCours,hour,minute);
                //layoutHeure.setEnabled(true);
            }
        });

        materialTimePicker.addOnPositiveButtonClickListener(dialog -> {
            int newHour = materialTimePicker.getHour();
            int newMinute = materialTimePicker.getMinute();
            majDate(dateEnCours,newHour,newMinute);
            textHeure.setText(ecrireHeure(newHour,newMinute));
        });

        Menu bottomNavigationViewMenu = bottomNavigationView.getMenu();
        bottomNavigationViewMenu.findItem(R.id.bottom_navigation_rdv).setChecked(true);
    }

    public void majDate(Date jour,int hour,int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(jour);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        dateEnCours = calendar.getTime();
    }

    @OnClick(R.id.chipContact)
    public void chipContactClick() {
        //progressBar.setVisibility(View.VISIBLE);
        booleanContact = !booleanContact;
        booleanAnalyse = false;
        booleanExamen = false;
        chipContact.setEnabled(false);
        chipAnalyse.setEnabled(true);
        chipExamen.setEnabled(true);
        //imageView.setVisibility(View.INVISIBLE);
        //fabShare.hide();
        razRdv();
        resizeFieldName();
        hideAllFields();
        displayFabs();
        fabDelete.hide();
        selectedRdv.setText("");
        AsyncTaskRunnerContact runnerBDContact = new AsyncTaskRunnerContact();
        runnerBDContact.execute();
    }

    @OnClick(R.id.chipAnalyse)
    public void chipAnalyseClick() {
        //progressBar.setVisibility(View.VISIBLE);
        booleanAnalyse = !booleanAnalyse;
        booleanContact = false;
        booleanExamen = false;
        chipContact.setEnabled(true);
        chipAnalyse.setEnabled(false);
        chipExamen.setEnabled(true);
        //imageView.setVisibility(View.INVISIBLE);
        //fabShare.hide();
        razRdv();
        resizeFieldName();
        hideAllFields();
        displayFabs();
        fabDelete.hide();
        selectedRdv.setText("");
        AsyncTaskRunnerAnalyse runnerBDAnalyse = new AsyncTaskRunnerAnalyse();
        runnerBDAnalyse.execute();
    }

    @OnClick(R.id.chipExamen)
    public void chipExamenClick() {
        //progressBar.setVisibility(View.VISIBLE);
        booleanExamen = !booleanExamen;
        booleanAnalyse = false;
        booleanContact = false;
        chipContact.setEnabled(true);
        chipAnalyse.setEnabled(true);
        chipExamen.setEnabled(false);
        //imageView.setVisibility(View.INVISIBLE);
        //fabShare.hide();
        razRdv();
        resizeFieldName();
        hideAllFields();
        displayFabs();
        fabDelete.hide();
        selectedRdv.setText("");
        AsyncTaskRunnerExamen runnerBDExamen = new AsyncTaskRunnerExamen();
        runnerBDExamen.execute();
    }

    public void showTimePicker(View view) {
        materialTimePicker.show(getSupportFragmentManager(),"TIME_PICKER");
    }

    public void showDatePicker(View view) {
        materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");
    }

   /* public void traiterIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("rdvAnalyseId")) {
            Long rdvAnalyseId = intent.getLongExtra("rdvAnalyseId", 0);
            //rdvAnalyseSelected = RdvAnalyse.findById(RdvAnalyse.class, rdvAnalyseId);
            rdvAnalyseSelected = rdvAnalyseDao.load(rdvAnalyseId);
            selectedRdv.setText(rdvAnalyseSelected.toString());
            // rdvContactSelected = listRdvContactBD.get(position);
            dateEnCours = rdvAnalyseSelected.getDate();
            noteEncours = rdvAnalyseSelected.getDetail();
            enableFields(false);
            displayFabs();
            fillAllFields();
            displayAllFields(false);
        } else if (intent.hasExtra("rdvExamenId")) {
            Long rdvExamenId = intent.getLongExtra("rdvExamenId", 0);
            //rdvAnalyseSelected = RdvAnalyse.findById(RdvAnalyse.class, rdvAnalyseId);
            rdvExamenSelected = rdvExamenDao.load(rdvExamenId);
            selectedRdv.setText(rdvExamenSelected.toString());
            // rdvContactSelected = listRdvContactBD.get(position);
            dateEnCours = rdvExamenSelected.getDate();
            noteEncours = rdvExamenSelected.getDetail();
            enableFields(false);
            displayFabs();
            fillAllFields();
            displayAllFields(false);
        } else if (intent.hasExtra("rdvContactId")) {
            Long rdvContactId = intent.getLongExtra("rdvContactId", 0);
            //rdvAnalyseSelected = RdvAnalyse.findById(RdvAnalyse.class, rdvAnalyseId);
            rdvContactSelected = rdvContactDao.load(rdvContactId);
            selectedRdv.setText(rdvContactSelected.toString());
            // rdvContactSelected = listRdvContactBD.get(position);
            dateEnCours = rdvContactSelected.getDate();
            noteEncours = rdvContactSelected.getDetail();
            enableFields(false);
            displayFabs();
            fillAllFields();
            displayAllFields(false);
        }
    }*/

    public class AsyncTaskRunnerAnalyse extends AsyncTask<Void, Integer, Void> {

        protected Void doInBackground(Void...voids) {
            publishProgress(0);

            publishProgress(50);
          //  listRdvBD = RdvAnalyse.find(RdvAnalyse.class,"utilisateur = ?",""+activeUser.getId());
            listRdvAnalyse = rdvAnalyseDao.loadAll();
            Collections.sort(listRdvAnalyse);
            publishProgress(100);
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            listRdv.setVisibility(View.VISIBLE);
            fabAdd.setVisibility(View.VISIBLE);
            if (listRdvAnalyse.size() == 0) {
                Snackbar.make(fabSave, R.string.text_no_matching, Snackbar.LENGTH_LONG).show();
                listRdv.setVisibility(View.GONE);
            } else {
                buildDropdownMenu(listRdvAnalyse, AfficherRdvActivity.this,selectedRdv);

            }
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }

    public class AsyncTaskRunnerExamen extends AsyncTask<Void, Integer, Void> {

        protected Void doInBackground(Void...voids) {
            publishProgress(0);

            publishProgress(50);
            //  listRdvBD = RdvAnalyse.find(RdvAnalyse.class,"utilisateur = ?",""+activeUser.getId());
            listRdvExamen = rdvExamenDao.loadAll();
            Collections.sort(listRdvExamen);
            publishProgress(100);
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            listRdv.setVisibility(View.VISIBLE);
            fabAdd.setVisibility(View.VISIBLE);
            if (listRdvExamen.size() == 0) {
                Snackbar.make(fabSave, R.string.text_no_matching, Snackbar.LENGTH_LONG).show();
                listRdv.setVisibility(View.GONE);
            } else {
                buildDropdownMenu(listRdvExamen, AfficherRdvActivity.this,selectedRdv);

            }
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }

    public class AsyncTaskRunnerContact extends AsyncTask<Void, Integer, Void> {

        protected Void doInBackground(Void...voids) {
            publishProgress(0);

            publishProgress(50);
            //  listRdvBD = RdvAnalyse.find(RdvAnalyse.class,"utilisateur = ?",""+activeUser.getId());
            listRdvContact = rdvContactDao.loadAll();
            Collections.sort(listRdvContact);
            publishProgress(100);
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            listRdv.setVisibility(View.VISIBLE);
            fabAdd.setVisibility(View.VISIBLE);
            if (listRdvContact.size() == 0) {
                Snackbar.make(fabSave, R.string.text_no_matching, Snackbar.LENGTH_LONG).show();
                listRdv.setVisibility(View.GONE);
            } else {
                buildDropdownMenu(listRdvContact, AfficherRdvActivity.this,selectedRdv);

            }
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }

    @OnClick(R.id.fabAdd)
    public void fabAddClick() {
        if (booleanAnalyse) {
            ouvrirActiviteSuivante(AfficherRdvActivity.this, AddRdvAnalyseActivity.class,true);
        } else if (booleanExamen) {
            ouvrirActiviteSuivante(AfficherRdvActivity.this, AddRdvExamenActivity.class,true);
        } else if (booleanContact) {
            ouvrirActiviteSuivante(AfficherRdvActivity.this, AddRdvContactActivity.class,true);
        }

    }

    @OnClick(R.id.fabSave)
    public void fabSaveClick() {
        //rdvAnalyseDao.delete(rdvAnalyseSelected);
        //date = DateUtils.razHeure(date);
        if (booleanAnalyse) {
            rdvAnalyseSelected.setDate(dateEnCours);
            rdvAnalyseSelected.setDateString(dateEnCours.toString());
            if (textNote.getText() != null && !textNote.getText().toString().equalsIgnoreCase(rdvAnalyseSelected.getDetail())) {
                rdvAnalyseSelected.setDetail(textNote.getText().toString());
            }
            rdvAnalyseDao.update(rdvAnalyseSelected);
        } else if (booleanExamen) {
            rdvExamenSelected.setDate(dateEnCours);
            rdvExamenSelected.setDateString(dateEnCours.toString());
            if (textNote.getText() != null && !textNote.getText().toString().equalsIgnoreCase(rdvExamenSelected.getDetail())) {
                rdvExamenSelected.setDetail(textNote.getText().toString());
            }
            rdvExamenDao.update(rdvExamenSelected);
        } else if (booleanContact) {
            rdvContactSelected.setDate(dateEnCours);
            rdvContactSelected.setDateString(dateEnCours.toString());
            if (textNote.getText() != null && !textNote.getText().toString().equalsIgnoreCase(rdvContactSelected.getDetail())) {
                rdvContactSelected.setDetail(textNote.getText().toString());
            }
            rdvContactDao.update(rdvContactSelected);
        }



        enableFields(false);
        displayAllFields(false);
        displayFabs();
        Snackbar.make(fabSave, R.string.modification_saved, Snackbar.LENGTH_LONG).show();
        ouvrirActiviteSuivante(this, AfficherRdvActivity.class,true);
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
        fabAdd.hide();
        fabSave.show();
        fabCancel.show();
    }

    @OnClick(R.id.fabDelete)
    public void fabDeleteClick() {
        if (booleanAnalyse) {
            rdvAnalyseDao.delete(rdvAnalyseSelected);
        } else if (booleanExamen) {
            rdvExamenDao.delete(rdvExamenSelected);
        } else if (booleanContact) {
            rdvContactDao.delete(rdvContactSelected);
        }

        ouvrirActiviteSuivante(this, AfficherRdvActivity.class,true);
    }

    @OnClick(R.id.fabPhoto)
    public void fabPhotoClick() {
        if (booleanAnalyse) {
            ouvrirActiviteSuivante(AfficherRdvActivity.this, MakePhotoActivity.class,"type", TypePhoto.Analyse.toString(),"itemId", rdvAnalyseSelected.getId(),true);
        } else if (booleanExamen) {
            ouvrirActiviteSuivante(AfficherRdvActivity.this, MakePhotoActivity.class,"type", TypePhoto.Examen.toString(),"itemId", rdvExamenSelected.getId(),true);
        } else if (booleanContact) {
            ouvrirActiviteSuivante(AfficherRdvActivity.this, MakePhotoActivity.class,"type", TypePhoto.Ordonnance.toString(),"itemId", rdvContactSelected.getId(),true);
        }



        //ouvrirActiviteSuivante(AfficherRdvActivity.this, MakePhotoActivity.class,"type", TypePhoto.Analyse.toString(),"itemId", rdvAnalyseSelected.getId(),true);
    }

    private void resizeFieldName() {
        if (booleanAnalyse || booleanExamen) {
            layoutTypeRdv.setPadding(150,0,150,0);
        } else if (booleanContact) {
            layoutTypeRdv.setPadding(10,0,10,0);
        }
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
        if (booleanAnalyse) {
            rdvAnalyseSelected = (RdvAnalyse) listRdvAnalyse.get(position);
            dateEnCours = rdvAnalyseSelected.getDate();
            //noteEncours = rdvAnalyseSelected.getDetail();
        } else if (booleanExamen) {
            rdvExamenSelected = (RdvExamen) listRdvExamen.get(position);
            dateEnCours = rdvExamenSelected.getDate();
            //noteEncours = rdvExamenSelected.getDetail();
        } else if (booleanContact) {
            rdvContactSelected = (RdvContact) listRdvContact.get(position);
            dateEnCours = rdvContactSelected.getDate();
            //noteEncours = rdvContactSelected.getDetail();
        }
        enableFields(false);
        displayFabs();
        fillAllFields();
        displayAllFields(false);
    }

    private void clearAllFields() {
        textTypeRdv.setText(null);
        textDate.setText(null);
        textHeure.setText(null);
        textNote.setText(null);
    }

    public void razRdv(){
        rdvAnalyseSelected = null;
        rdvExamenSelected = null;
        rdvContactSelected = null;
    }

    public void displayFabs() {
        fabSave.hide();
        fabCancel.hide();
        //fabAdd.hide();
        if (booleanAnalyse) {
            fabAdd.show();
        } else if (booleanExamen) {
            fabAdd.show();
        } else if (booleanContact) {
            fabAdd.show();
        } else {
            fabAdd.hide();
        }
        if (rdvAnalyseSelected == null && rdvExamenSelected == null && rdvContactSelected == null) {
            fabEdit.hide();
            fabDelete.hide();
            fabPhoto.hide();
            //fabAdd.hide();
        } else {
            fabEdit.show();
            fabDelete.show();
            //fabAdd.show();
            fabPhoto.show();
        }
    }

    private void fillAllFields() {
        String rdvSelectedString = "";
        Date date = new Date();
        String note = "";
        if (booleanContact) {
            rdvSelectedString = rdvContactSelected.getContact().toString();
            date = rdvContactSelected.getDate();
            note = rdvContactSelected.getDetail();
        } else if (booleanExamen) {
            rdvSelectedString = rdvExamenSelected.getExamen().toString();
            date = rdvExamenSelected.getDate();
            note = rdvExamenSelected.getDetail();
        } else if (booleanAnalyse) {
            rdvSelectedString = rdvAnalyseSelected.getAnalyse().toString();
            date = rdvAnalyseSelected.getDate();
            note = rdvAnalyseSelected.getDetail();
        }
        textTypeRdv.setText(rdvSelectedString);
        textDate.setText(DateUtils.ecrireDate(date));
        textHeure.setText(DateUtils.ecrireHeure(date));
        textNote.setText(note);
    }

    private void enableFields(boolean bool) {
        layoutTypeRdv.setEnabled(false);
        layoutDate.setEnabled(bool);
        layoutHeure.setEnabled(bool);
        layoutNote.setEnabled(bool);
    }

    private void displayAllFields(boolean bool) {
        layoutTypeRdv.setVisibility(View.VISIBLE);
        layoutDate.setVisibility(View.VISIBLE);
        layoutHeure.setVisibility(View.VISIBLE);
        String Detail = "";
        if (booleanContact) {
            Detail = rdvContactSelected.getDetail();
            layoutTypeRdv.setStartIconDrawable(MyApp.getRes().getDrawable(R.drawable.avatar_doctor));
            layoutTypeRdv.setHint("Rdv Contact");
        } else if (booleanExamen) {
            Detail = rdvExamenSelected.getDetail();
            layoutTypeRdv.setStartIconDrawable(MyApp.getRes().getDrawable(R.drawable.heart));
            layoutTypeRdv.setHint("Rdv Examen");
        } else if (booleanAnalyse) {
            Detail = rdvAnalyseSelected.getDetail();
            layoutTypeRdv.setStartIconDrawable(MyApp.getRes().getDrawable(R.drawable.syringe_vaccine_icon_127255));
            layoutTypeRdv.setHint("Rdv Analyse");
        }
        if (bool || !TextUtils.isEmpty(Detail)) {
            layoutNote.setVisibility(View.VISIBLE);
        } else {
            layoutNote.setVisibility(View.GONE);
        }

    }

    private void hideAllFields() {
        listRdv.setVisibility(View.GONE);


        layoutTypeRdv.setVisibility(View.GONE);
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

