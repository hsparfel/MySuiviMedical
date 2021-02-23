package com.pouillos.mysuivimedical.activities.add;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mysuivimedical.R;
import com.pouillos.mysuivimedical.activities.NavDrawerActivity;
import com.pouillos.mysuivimedical.entities.Contact;
import com.pouillos.mysuivimedical.entities.RdvContact;

import com.pouillos.mysuivimedical.interfaces.BasicUtils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;
import icepick.State;

public class AddRdvContactActivity extends NavDrawerActivity implements Serializable, BasicUtils {

    Contact contact;
    @State
    Date date = new Date();

    RdvContact rdvContactToCreate;

    TimePickerDialog picker;

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

    @BindView(R.id.fabSave)
    FloatingActionButton fabSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_add_rdv_contact);

        this.configureToolBar();
        this.configureBottomView();

        ButterKnife.bind(this);

        traiterIntent();
        displayFabs();
        layoutContact.setEnabled(false);
        setTitle(getString(R.string.my_meeting));

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

    public void displayFabs() {
            fabSave.show();
    }


    public boolean isExistant() {
        boolean bool;
        bool = false;
        //List<RdvContact> listRdvContact = RdvContact.find(RdvContact.class,"utilisateur = ? and contact = ? and date = ?",""+activeUser.getId(),""+contact.getId(),""+date.getTime());
        List<RdvContact> listRdvContact = rdvContactDao.queryRaw("where contact_Id = ? and date = ?",""+contact.getId(),""+date.getTime());
        if (listRdvContact.size() != 0) {
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


    public void traiterIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("contactId")) {
            Long contactId = intent.getLongExtra("contactId", 0);
            //contact = Contact.findById(Contact.class, contactId);
            contact = contactDao.load(contactId);
            textContact.setText(contact.toString());
            rdvContactToCreate = new RdvContact();
            rdvContactToCreate.setContact(contact);
        }
    }

    @OnClick(R.id.fabSave)
    public void fabSaveClick() {
        if (checkFields()) {
            if (!isExistant()) {
                saveToDb();
               // ouvrirActiviteSuivante(AddRdvContactActivity.this, AccueilActivity.class,true);
                rouvrirActiviteAccueil(this,true);
            } else {
                Toast.makeText(AddRdvContactActivity.this, "Rdv déjà existant", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(AddRdvContactActivity.this, "Saisie non valide", Toast.LENGTH_LONG).show();
        }

    }


    public void saveToDb() {
        RdvContact rdvContact = new RdvContact();
        rdvContact.setDetail(textNote.getText().toString());
        rdvContact.setContact(contact);

        rdvContact.setDate(date);
        rdvContact.setId(rdvContactDao.insert(rdvContact));


        Toast.makeText(AddRdvContactActivity.this, "Rdv Enregistré", Toast.LENGTH_LONG).show();
        //enregistrer la/les notification(s)
       // activerNotification(RdvContactNotificationBroadcastReceiver.class, rdvContact.getDate(), rdvContact.getContact(), AddRdvContactActivity.this);
        //activerNotification(rdvContact, AddRdvContactActivity.this);

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
        picker = new TimePickerDialog(AddRdvContactActivity.this,
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
                    if (textHeure != null && !textHeure.getText().toString().equalsIgnoreCase("")) {
                        date = ActualiserDate(date, textHeure.getText().toString());
                    }
                    layoutHeure.setEnabled(true);
                }catch(ParseException e){
                    System.out.println("ERROR");
                }
            }
        });
    }*/

}
