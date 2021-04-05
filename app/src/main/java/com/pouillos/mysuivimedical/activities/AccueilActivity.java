package com.pouillos.mysuivimedical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.pouillos.mysuivimedical.R;
import com.pouillos.mysuivimedical.activities.utils.DateUtils;
import com.pouillos.mysuivimedical.entities.Analyse;
import com.pouillos.mysuivimedical.entities.Examen;
import com.pouillos.mysuivimedical.interfaces.BasicUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.Icepick;

public class AccueilActivity extends NavDrawerActivity implements BasicUtils {

    @BindView(R.id.my_progressBar)
    ProgressBar progressBar;

    @BindView(R.id.simpleProgressBar)
    ProgressBar simpleProgressBar;

    @BindView(R.id.textView)
    TextView textView;

    //String uriFindDoctor = "content://com.pouillos.finddoctor.provider/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Stetho.initializeWithDefaults(this);

        this.configureToolBar();
        this.configureBottomView();

        ButterKnife.bind(this);

        //progressBar.setVisibility(View.VISIBLE);

        textView.setText(DateUtils.ecrireDateLettre(new Date()));

        traiterIntent();

        remplirDB();
    }

    /*public void testProgressBar(View view) {
        if (simpleProgressBar.getVisibility() == View.GONE) {
            simpleProgressBar.setVisibility(View.VISIBLE);
        } else {
            simpleProgressBar.setVisibility(View.GONE);
        }
    }*/

    public void traiterIntent() {
        Intent intent = getIntent();
        if (!intent.hasExtra("isSecondLaunch")) {
            simpleProgressBar.setVisibility(View.VISIBLE);
            SynchroApps();
            //simpleProgressBar.setVisibility(View.GONE);
        }
        simpleProgressBar.setVisibility(View.GONE);
    }

    /*public void addRdvAnalyse(View view) {
        ouvrirActiviteSuivante(this, AddRdvAnalyseActivity.class,false);
    }

    public void afficherRdvAnalyse(View view) {
        ouvrirActiviteSuivante(this, AfficherRdvAnalyseActivity.class,false);
    }

    public void addRdvExamen(View view) {
        ouvrirActiviteSuivante(this, AddRdvExamenActivity.class,false);
    }

    public void afficherRdvExamen(View view) {
        ouvrirActiviteSuivante(this, AfficherRdvExamenActivity.class,false);
    }

    public void addRdvContact(View view) {
        ouvrirActiviteSuivante(this, AddRdvContactActivity.class,false);
    }

    public void afficherRdvContact(View view) {
        ouvrirActiviteSuivante(this, AfficherRdvContactActivity.class,false);
    }

    public void afficherRdv(View view) {
        ouvrirActiviteSuivante(this, AfficherRdvActivity.class,false);
    }

    public void addAnalyse(View view) {
        ouvrirActiviteSuivante(this,AddAnalyseActivity.class,false);
    }

    public void afficherAnalyse(View view) {
        ouvrirActiviteSuivante(this, AfficherAnalyseActivity.class,false);
    }

    public void addExamen(View view) {
        ouvrirActiviteSuivante(this, AddExamenActivity.class,false);
    }

    public void afficherExamen(View view) {
        ouvrirActiviteSuivante(this, AfficherExamenActivity.class,false);
    }

    public void addProfil(View view) {
        ouvrirActiviteSuivante(this, AddProfilActivity.class,false);
    }

    public void afficherProfil(View view) {
        ouvrirActiviteSuivante(this, AfficherProfilActivity.class,false);
    }

    public void afficherPhoto(View view) {
        ouvrirActiviteSuivante(this, AfficherPhotoActivity.class,false);
    }

    public void afficherGraphique(View view) {
        ouvrirActiviteSuivante(this, AfficherGraphiqueActivity.class,false);
    }*/


    public void remplirDB() {
        Long count = analyseDao.count();
        if (count ==0) {
            analyseDao.insert(new Analyse(1l,"sang"));
            analyseDao.insert(new Analyse(2l,"urine"));
            analyseDao.insert(new Analyse(3l,"selle"));
            analyseDao.insert(new Analyse(4l,"adn"));
            analyseDao.insert(new Analyse(5l,"autre"));
        }
        count = examenDao.count();
        if (count ==0) {
            examenDao.insert(new Examen(1l,"radiologie"));
            examenDao.insert(new Examen(2l,"échographie"));
            examenDao.insert(new Examen(3l,"irm"));
            examenDao.insert(new Examen(4l,"scanner"));
            examenDao.insert(new Examen(5l,"électrocardiogramme (ECG)"));
            examenDao.insert(new Examen(6l,"électroencéphalogramme (EEG)"));
            examenDao.insert(new Examen(7l,"tomographie à émission de positron"));
            examenDao.insert(new Examen(8l,"urographie intra-veineuse"));
            examenDao.insert(new Examen(9l,"ultrasons"));
            examenDao.insert(new Examen(10l,"doppler"));
            examenDao.insert(new Examen(11l,"endoscopie"));
            examenDao.insert(new Examen(12l,"lavement"));
            examenDao.insert(new Examen(13l,"biopsie"));
            examenDao.insert(new Examen(14l,"ponction lombaire"));
            examenDao.insert(new Examen(15l,"autre"));
        }
    }

    /*public void majDatas(View view) {
        simpleProgressBar.setVisibility(View.VISIBLE);
        synchroDatas(true);
        simpleProgressBar.setVisibility(View.GONE);
        Snackbar.make(textView,"MAJ OK",Snackbar.LENGTH_LONG);
        textView.setText("MAJ OVER");
    }*/

    public void SynchroApps() {
        //lancer findDoctor
        Intent findDoctorIntent = getPackageManager().getLaunchIntentForPackage("com.pouillos.finddoctor");
        if (findDoctorIntent != null) {
            startActivityIfNeeded(findDoctorIntent,0);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //lancer myPilulier
        Intent myPilulierIntent = getPackageManager().getLaunchIntentForPackage("com.pouillos.mypilulier");
        if (myPilulierIntent != null) {
            startActivityIfNeeded(myPilulierIntent,0);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Remettre MySuiviMedical en Front
        Intent mySuiviMedicalIntent = getPackageManager().getLaunchIntentForPackage("com.pouillos.mysuivimedical");
        mySuiviMedicalIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(mySuiviMedicalIntent,0);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchroDatas(false);

    }







}
