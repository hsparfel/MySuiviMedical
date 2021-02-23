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
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
                Toast.makeText(AfficherRdvContactActivity.this, "Aucune correspondance, modifier puis appliquer filtre", Toast.LENGTH_LONG).show();
                listRdv.setVisibility(View.GONE);
            } else {
                buildDropdownMenu(listRdvContactBD, AfficherRdvContactActivity.this,selectedRdv);

                //traiterIntent();


            /*    List<String> listRdvString = new ArrayList<>();
                String[] listDeroulanteRdv = new String[listRdvBD.size()];
                for (Rdv rdv : listRdvBD) {
                    String rdvString = DateUtils.ecrireDateHeure(rdv.getDate()) + " - ";
                    if (!rdv.getContact().getCodeCivilite().equalsIgnoreCase("")) {
                        rdvString += rdv.getContact().getCodeCivilite() + " ";
                    }
                    rdvString += rdv.getContact().getNom();
                    listRdvString.add(rdvString);
                }
                listRdvString.toArray(listDeroulanteRdv);
                adapter = new ArrayAdapter(AfficherRdvActivity.this, R.layout.list_item, listDeroulanteRdv);
                selectedRdv.setAdapter(adapter);*/


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
            if (textNote.getText() != null && !textNote.getText().toString().equalsIgnoreCase(rdvContactSelected.getDetail())) {
                rdvContactSelected.setDetail(textNote.getText().toString());
            }

            //rdvContactSelected.save();
            rdvContactDao.update(rdvContactSelected);
        //enregistrer la/les notification(s)
        //activerNotification(rdvContactSelected, AfficherRdvContactActivity.this);

            enableFields(false);
            displayAllFields(false);
            displayFabs();
            Toast.makeText(AfficherRdvContactActivity.this, R.string.modification_saved, Toast.LENGTH_LONG).show();
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


        /*String item = parent.getItemAtPosition(position).toString();

        Date date = new Date();
        String dateRdv = selectedRdv.getText().toString().substring(0, 16);
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
        try{
            date = df.parse(dateRdv);
        }catch(ParseException e){
            System.out.println("ERROR");
        }
        rdvSelected = Rdv.find(Rdv.class, "date = ?", "" + date.getTime()).get(0);

*/
       // Toast.makeText(AfficherRdvActivity.this, "Selected Item is: \t" + DateUtils.ecrireDateHeure(rdvSelected.getDate()) + " - "+ rdvSelected.getContact(), Toast.LENGTH_LONG).show();
        //lancer l'activite avec xtra
        //ouvrirActiviteSuivante(AddRdvActivity.class,"rdvId",rdvSelected.getId());
        //ouvrirActiviteSuivante(AfficherRdvActivity.this, AddRdvActivity.class,"rdvId",rdvSelected.getId());
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


    /*public void showTimePickerDialog(View v) {
        final Calendar cldr = Calendar.getInstance();
        int hour = 8;
        int minutes = 0;
        // time picker dialog
        picker = new TimePickerDialog(AfficherRdvContactActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        String hour = "";
                        String minute = "";
                        if (sHour<10){
                            hour+="0";
                        }
                        if (sMinute<10){
                            minute+="0";
                        }
                        hour+=sHour;
                        minute+=sMinute;

                        textHeure.setText(hour + ":" + minute);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        calendar.add(Calendar.HOUR_OF_DAY, sHour);
                        calendar.add(Calendar.MINUTE, sMinute);
                        date = calendar.getTime();
                    }
                }, hour, minutes, true);
        picker.show();
    }


    public void showDatePickerDialog(View v) {
        DatePickerFragmentDateJour newFragment = new DatePickerFragmentDateJour();
        //newFragment.show(getSupportFragmentManager(), "buttonDate");
        newFragment.show(getSupportFragmentManager(), "editTexteDate");
        newFragment.setOnDateClickListener(new DatePickerFragmentDateJour.onDateClickListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                datePicker.setMinDate(new Date().getTime());
                // TextView tv1= (TextView) findViewById(R.id.textDate);
                String dateJour = ""+datePicker.getDayOfMonth();
                String dateMois = ""+(datePicker.getMonth()+1);
                String dateAnnee = ""+datePicker.getYear();
                if (datePicker.getDayOfMonth()<10) {
                    dateJour = "0"+dateJour;
                }
                if (datePicker.getMonth()+1<10) {
                    dateMois = "0"+dateMois;
                }
                Calendar c1 = Calendar.getInstance();
                // set Month
                // MONTH starts with 0 i.e. ( 0 - Jan)
                c1.set(Calendar.MONTH, datePicker.getMonth());
                // set Date
                c1.set(Calendar.DATE, datePicker.getDayOfMonth());
                // set Year
                c1.set(Calendar.YEAR, datePicker.getYear());
                // creating a date object with specified time.
                date = c1.getTime();

                String dateString = dateJour+"/"+dateMois+"/"+dateAnnee;
                //tv1.setText("date: "+dateString);
                textDate.setText(dateString);
                textDate.setError(null);
                DateFormat df = new SimpleDateFormat("dd/MM/yy");
                try{
                    date = df.parse(dateString);
                }catch(ParseException e){
                    System.out.println("ERROR");
                }
            }
        });
    }*/

}

