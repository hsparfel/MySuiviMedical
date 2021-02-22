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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mysuivimedical.R;
import com.pouillos.mysuivimedical.activities.AccueilActivity;
import com.pouillos.mysuivimedical.activities.NavDrawerActivity;
import com.pouillos.mysuivimedical.entities.Analyse;
import com.pouillos.mysuivimedical.entities.RdvAnalyse;

import com.pouillos.mysuivimedical.fragments.DatePickerFragmentDateJour;
import com.pouillos.mysuivimedical.interfaces.BasicUtils;

import java.io.Serializable;
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

public class AddRdvAnalyseActivity extends NavDrawerActivity implements BasicUtils, AdapterView.OnItemClickListener {

    Date date;

    Analyse analyseSelected;

    TimePickerDialog picker;

    @BindView(R.id.selectionAnalyse)
    AutoCompleteTextView selectedAnalyse;
    @BindView(R.id.listAnalyse)
    TextInputLayout listAnalyse;

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

    private List<Analyse> listAnalyseBD;

    @BindView(R.id.fabSave)
    FloatingActionButton fabSave;

    @BindView(R.id.my_progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_add_rdv_analyse);
        // 6 - Configure all views
        this.configureToolBar();
        this.configureBottomView();

        ButterKnife.bind(this);


        progressBar.setVisibility(View.VISIBLE);

        AddRdvAnalyseActivity.AsyncTaskRunnerBD runnerBD = new AddRdvAnalyseActivity.AsyncTaskRunnerBD();
        runnerBD.execute();

        selectedAnalyse.setOnItemClickListener(this);
        displayFabs();

        setTitle(getString(R.string.add_meeting_analysis));

        layoutDate.setEnabled(false);
        layoutHeure.setEnabled(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        analyseSelected = listAnalyseBD.get(position);
        layoutDate.setEnabled(true);

    }

    public class AsyncTaskRunnerBD extends AsyncTask<Void, Integer, Void> {

        protected Void doInBackground(Void...voids) {
            publishProgress(0);

            publishProgress(10);

            //listAnalyseBD = Analyse.listAll(Analyse.class);
            listAnalyseBD = analyseDao.loadAll();
            Collections.sort(listAnalyseBD);

            publishProgress(100);
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
                buildDropdownMenu(listAnalyseBD, AddRdvAnalyseActivity.this,selectedAnalyse);
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
        //List<RdvAnalyse> listRdv = RdvAnalyse.find(RdvAnalyse.class,"utilisateur = ? and analyse = ? and date = ?",""+activeUser.getId(),""+analyseSelected.getId(),""+date.getTime());
        List<RdvAnalyse> listRdv = rdvAnalyseDao.queryRaw("where analyse_Id = ? and date = ?",""+analyseSelected.getId(),""+date.getTime());
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
                //ouvrirActiviteSuivante(AddRdvAnalyseActivity.this, AccueilActivity.class,true);
                rouvrirActiviteAccueil(this,true);
            } else {
                Toast.makeText(AddRdvAnalyseActivity.this, "Rdv déjà existant", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(AddRdvAnalyseActivity.this, "Saisie non valide", Toast.LENGTH_LONG).show();
        }

    }


    public void saveToDb() {
        RdvAnalyse rdvAnalyse = new RdvAnalyse();
        rdvAnalyse.setDetail(textNote.getText().toString());
        rdvAnalyse.setAnalyse(analyseSelected);
        rdvAnalyse.setDate(date);
        //rdvAnalyse.save();
        rdvAnalyseDao.insert(rdvAnalyse);
        Toast.makeText(AddRdvAnalyseActivity.this, "Rdv Enregistré", Toast.LENGTH_LONG).show();
        //enregistrer la/les notification(s)
        //activerNotification(rdvAnalyse,AddRdvAnalyseActivity.this);

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


    public void showTimePickerDialog(View v) {
        final Calendar cldr = Calendar.getInstance();
        int hour = 8;
        int minutes = 0;
        // time picker dialog
        picker = new TimePickerDialog(AddRdvAnalyseActivity.this,
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
    }

}
