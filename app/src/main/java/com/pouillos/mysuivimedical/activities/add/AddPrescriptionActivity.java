package com.pouillos.mysuivimedical.activities.add;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mysuivimedical.R;
import com.pouillos.mysuivimedical.activities.AccueilActivity;
import com.pouillos.mysuivimedical.activities.NavDrawerActivity;
import com.pouillos.mysuivimedical.entities.Dose;
import com.pouillos.mysuivimedical.entities.Medicament;
import com.pouillos.mysuivimedical.entities.MedicamentLight;
import com.pouillos.mysuivimedical.entities.Prescription;
import com.pouillos.mysuivimedical.entities.Prise;
import com.pouillos.mysuivimedical.enumeration.Frequence;
import com.pouillos.mysuivimedical.fragments.DatePickerFragmentDateJour;
import com.pouillos.mysuivimedical.interfaces.BasicUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;
import icepick.State;

public class AddPrescriptionActivity extends NavDrawerActivity implements BasicUtils {

    @State
    Date date;

    Medicament medicamentSelected;
    Prescription prescription;

    @BindView(R.id.textMedicament)
    AutoCompleteTextView selectedMedicament;
    @BindView(R.id.layoutMedicament)
    TextInputLayout listMedicament;

    List<MedicamentLight> listMedicamentLightBD;

    @BindView(R.id.layoutQuantite)
    TextInputLayout layoutQuantite;
    @BindView(R.id.textQuantite)
    TextInputEditText textQuantite;
    @BindView(R.id.layoutDose)
    TextInputLayout layoutDose;
    @BindView(R.id.textDose)
    TextInputEditText textDose;
    @BindView(R.id.layoutDate)
    TextInputLayout layoutDate;
    @BindView(R.id.textDate)
    TextInputEditText textDate;

    private Frequence frequence;
    private Dose dose;

    @BindView(R.id.fabSave)
    FloatingActionButton fabSave;

    @BindView(R.id.my_progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_add_prescription);

        this.configureToolBar();
        this.configureBottomView();

        ButterKnife.bind(this);

        progressBar.setVisibility(View.VISIBLE);

        AddPrescriptionActivity.AsyncTaskRunnerBDMedicament runnerBDMedicament = new AddPrescriptionActivity.AsyncTaskRunnerBDMedicament();
        runnerBDMedicament.execute();
        hideAll();
        frequence = Frequence.EveryXDays;
        setTitle("+ Prescription");
        listMedicament.setEnabled(false);
        layoutDose.setEnabled(false);
        selectedMedicament.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selection = (String) adapterView.getItemAtPosition(position);
                List<MedicamentLight> listMedicamentLight = medicamentLightDao.queryRaw("where denomination = ?",selection);
                MedicamentLight medicamentLight = null;
                if (listMedicamentLight.size() > 0) {
                    medicamentLight = listMedicamentLight.get(0);
                }
                medicamentSelected = medicamentDao.load(medicamentLight.getId());
                dose = recupDose(medicamentSelected);
                textDose.setText(dose.getName());
                showAll();
            }
        });

    }

    @OnClick(R.id.fabSave)
    public void fabSaveClick() {
        saveToDb();
    }

    private void showAll(){
        fabSave.show();
        layoutDate.setVisibility(View.VISIBLE);
        layoutQuantite.setVisibility(View.VISIBLE);
        layoutDose.setVisibility(View.VISIBLE);
    }

    private void hideAll(){
        fabSave.hide();
        layoutDate.setVisibility(View.GONE);
        layoutQuantite.setVisibility(View.GONE);
        layoutDose.setVisibility(View.GONE);
    }

    public class AsyncTaskRunnerBDMedicament extends AsyncTask<Void, Integer, Void> {

        protected Void doInBackground(Void...voids) {
            publishProgress(0);
            publishProgress(50);
            listMedicamentLightBD = medicamentLightDao.loadAll();
            Collections.sort(listMedicamentLightBD);
            publishProgress(100);
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            buildDropdownMenu(listMedicamentLightBD, AddPrescriptionActivity.this,selectedMedicament);
            listMedicament.setEnabled(true);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }

    public void saveToDb() {
        if (verifChamps()) {
            prescription = new Prescription();
            prescription.setMedicament(medicamentSelected);
            prescription.setFrequence(frequence);
            prescription.setDateFin(initDate(date));
            prescription.setDateFinString(initDate(date).toString());
            prescription.setQte(Float.parseFloat(textQuantite.getText().toString()));
            prescription.setId(prescriptionDao.insert(prescription));
            remplirDbPrise();
            ouvrirActiviteSuivante(AddPrescriptionActivity.this, AccueilActivity.class,false);
        }
    }

    public void remplirDbPrise() {
            Date dateDebut = initDate(new Date());
            Date dateFin = initDate(ajouterJour(date,1));
            for (Date date = dateDebut; !date.equals(dateFin);) {
                Prise prise = new Prise();
                prise.setDate(date);
                prise.setDateString(date.toString());
                prise.setDose(dose);
                prise.setEffectue(false);
                prise.setMedicament(medicamentSelected);
                prise.setQteDose(Float.parseFloat(textQuantite.getText().toString()));
                priseDao.insert((prise));
                //startAlert(prise,this);
                String shortDenomination = prise.getMedicament().getDenomination().length()>19 ? prise.getMedicament().getDenomination().substring(0,19) : prise.getMedicament().getDenomination();
                String textNotif = prise.getQteDose()+" "+recupDose(prise.getMedicament()).getName()+" "+shortDenomination;
               // scheduleNotification(this,prise.getDate().getTime(),"MyPilulier",textNotif);
                //scheduleNotification(getNotification(textNotif),prise.getDate().getTime()) ;
                /*WorkRequest work = new PeriodicWorkRequest.Builder(MyWorker.class, 1, TimeUnit.DAYS)
                        .setInitialDelay(1, TimeUnit.MINUTES)
                        .build()
                        ;
                WorkManager.getInstance(this).enqueue(work);*/
                notifSchedule(prise,this);
                //scheduleAlarm(prise,this);
                //notifSchedule(prescription,this);
                date = ajouterJour(date,1);
            }
    }

    public boolean verifChamps() {
        boolean bool = true;
        if (!isFilled(textDate)) {
            bool = false;
            textDate.setError("Sélection Obligatoire");
        }
        if (!isFilled(textQuantite)) {
            bool = false;
            textQuantite.setError("Sélection Obligatoire");
        }
        return bool;
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

    public void showDatePickerDialog(View v) {
        DatePickerFragmentDateJour newFragment = new DatePickerFragmentDateJour();
        newFragment.show(getSupportFragmentManager(), "editTexteDate");
        newFragment.setOnDateClickListener(new DatePickerFragmentDateJour.onDateClickListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                datePicker.setMinDate(new Date().getTime());
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
                c1.set(Calendar.MONTH, datePicker.getMonth());
                c1.set(Calendar.DATE, datePicker.getDayOfMonth());
                c1.set(Calendar.YEAR, datePicker.getYear());
                date = c1.getTime();
                String dateString = dateJour+"/"+dateMois+"/"+dateAnnee;
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
    }
}
