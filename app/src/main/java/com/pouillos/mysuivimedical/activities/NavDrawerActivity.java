package com.pouillos.mysuivimedical.activities;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.pouillos.mysuivimedical.R;
import com.pouillos.mysuivimedical.activities.afficher.AfficherAnalyseActivity;
import com.pouillos.mysuivimedical.activities.afficher.AfficherExamenActivity;
import com.pouillos.mysuivimedical.activities.afficher.AfficherPhotoActivity;
import com.pouillos.mysuivimedical.activities.afficher.AfficherProfilActivity;
import com.pouillos.mysuivimedical.activities.afficher.AfficherRdvActivity;
import com.pouillos.mysuivimedical.activities.utils.DateUtils;
import com.pouillos.mysuivimedical.dao.AnalyseDao;
import com.pouillos.mysuivimedical.dao.AssociationContactLightEtablissementLightDao;
import com.pouillos.mysuivimedical.dao.AssociationFormeDoseDao;
import com.pouillos.mysuivimedical.dao.ContactDao;
import com.pouillos.mysuivimedical.dao.ContactLightDao;
import com.pouillos.mysuivimedical.dao.DaoMaster;
import com.pouillos.mysuivimedical.dao.DaoSession;
import com.pouillos.mysuivimedical.dao.DepartementDao;
import com.pouillos.mysuivimedical.dao.DoseDao;
import com.pouillos.mysuivimedical.dao.EtablissementDao;
import com.pouillos.mysuivimedical.dao.EtablissementLightDao;
import com.pouillos.mysuivimedical.dao.ExamenDao;
import com.pouillos.mysuivimedical.dao.FormePharmaceutiqueDao;
import com.pouillos.mysuivimedical.dao.MedicamentDao;
import com.pouillos.mysuivimedical.dao.MedicamentLightDao;
import com.pouillos.mysuivimedical.dao.PhotoAnalyseDao;

import com.pouillos.mysuivimedical.dao.PhotoExamenDao;
import com.pouillos.mysuivimedical.dao.PhotoOrdonnanceDao;
import com.pouillos.mysuivimedical.dao.PrescriptionDao;
import com.pouillos.mysuivimedical.dao.PriseDao;
import com.pouillos.mysuivimedical.dao.ProfessionDao;
import com.pouillos.mysuivimedical.dao.ProfilDao;
import com.pouillos.mysuivimedical.dao.RdvAnalyseDao;
import com.pouillos.mysuivimedical.dao.RdvContactDao;
import com.pouillos.mysuivimedical.dao.RdvExamenDao;
import com.pouillos.mysuivimedical.dao.RegionDao;
import com.pouillos.mysuivimedical.dao.SavoirFaireDao;
import com.pouillos.mysuivimedical.dao.TypeEtablissementDao;
import com.pouillos.mysuivimedical.entities.AssociationContactLightEtablissementLight;
import com.pouillos.mysuivimedical.entities.AssociationFormeDose;
import com.pouillos.mysuivimedical.entities.Contact;
import com.pouillos.mysuivimedical.entities.ContactLight;
import com.pouillos.mysuivimedical.entities.Departement;
import com.pouillos.mysuivimedical.entities.Dose;
import com.pouillos.mysuivimedical.entities.Etablissement;
import com.pouillos.mysuivimedical.entities.EtablissementLight;
import com.pouillos.mysuivimedical.entities.FormePharmaceutique;
import com.pouillos.mysuivimedical.entities.Medicament;
import com.pouillos.mysuivimedical.entities.MedicamentLight;
import com.pouillos.mysuivimedical.entities.Prescription;
import com.pouillos.mysuivimedical.entities.Prise;
import com.pouillos.mysuivimedical.entities.Profession;
import com.pouillos.mysuivimedical.entities.Region;
import com.pouillos.mysuivimedical.entities.SavoirFaire;
import com.pouillos.mysuivimedical.entities.TypeEtablissement;
import com.pouillos.mysuivimedical.enumeration.Frequence;
import com.pouillos.mysuivimedical.interfaces.BasicUtils;

import org.greenrobot.greendao.database.Database;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.Executor;

import icepick.Icepick;


public class NavDrawerActivity extends AppCompatActivity implements BasicUtils {

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    protected Toolbar toolbar;
    protected DrawerLayout drawerLayout;
    protected BottomNavigationView bottomNavigationView;

    protected DaoSession daoSession;

    protected AssociationFormeDoseDao associationFormeDoseDao;
    protected DoseDao doseDao;
    protected FormePharmaceutiqueDao formePharmaceutiqueDao;
    protected MedicamentDao medicamentDao;
    protected MedicamentLightDao medicamentLightDao;
    protected PrescriptionDao prescriptionDao;
    protected PriseDao priseDao;
    protected AnalyseDao analyseDao;
    protected AssociationContactLightEtablissementLightDao associationContactLightEtablissementLightDao;
    protected ContactDao contactDao;
    protected ContactLightDao contactLightDao;
    protected DepartementDao departementDao;
    protected EtablissementDao etablissementDao;
    protected EtablissementLightDao etablissementLightDao;
    protected ExamenDao examenDao;
    protected PhotoAnalyseDao photoAnalyseDao;
    protected PhotoExamenDao photoExamenDao;
    protected PhotoOrdonnanceDao photoOrdonnanceDao;
    protected ProfessionDao professionDao;
    protected ProfilDao profilDao;
    protected RegionDao regionDao;
    protected SavoirFaireDao savoirFaireDao;
    protected TypeEtablissementDao typeEtablissementDao;
    protected RdvExamenDao rdvExamenDao;
    protected RdvAnalyseDao rdvAnalyseDao;
    protected RdvContactDao rdvContactDao;

    protected MaterialDatePicker materialDatePicker;
    protected MaterialTimePicker materialTimePicker;
    protected Long today;

    String uriFindDoctorRegion = "content://com.pouillos.finddoctor.provider.region/";
    String uriFindDoctorContact = "content://com.pouillos.finddoctor.provider.contact/";
    String uriFindDoctorContactLight = "content://com.pouillos.finddoctor.provider.contactlight/";
    String uriFindDoctorEtablissement = "content://com.pouillos.finddoctor.provider.etablissement/";
    String uriFindDoctorEtablissementLight = "content://com.pouillos.finddoctor.provider.etablissementlight/";
    String uriFindDoctorDepartement = "content://com.pouillos.finddoctor.provider.departement/";
    String uriFindDoctorSavoirFaire = "content://com.pouillos.finddoctor.provider.savoirfaire/";
    String uriFindDoctorProfession = "content://com.pouillos.finddoctor.provider.profession/";
    String uriFindDoctorTypeEtablissement = "content://com.pouillos.finddoctor.provider.typeetablissement/";
    String uriFindDoctorAssociation = "content://com.pouillos.finddoctor.provider.association/";

    String uriMyPilulierMedicament = "content://com.pouillos.mypilulier.provider.medicament/";
    String uriMyPilulierMedicamentLight = "content://com.pouillos.mypilulier.provider.medicamentlight/";
    String uriMyPilulierFormePharmaceutique = "content://com.pouillos.mypilulier.provider.formepharmaceutique/";
    String uriMyPilulierPrescription = "content://com.pouillos.mypilulier.provider.prescription/";
    String uriMyPilulierPrise = "content://com.pouillos.mypilulier.provider.prise/";
    String uriMyPilulierDose = "content://com.pouillos.mypilulier.provider.dose/";
    String uriMyPilulierAssociation = "content://com.pouillos.mypilulier.provider.association/";

    Uri CONTENT_URI_MYPILULIER_MEDICAMENT = Uri.parse(uriMyPilulierMedicament+"Medicament");
    Uri CONTENT_URI_MYPILULIER_MEDICAMENT_LIGHT = Uri.parse(uriMyPilulierMedicamentLight+"MedicamentLight");
    Uri CONTENT_URI_MYPILULIER_FORME_PHARMACEUTIQUE = Uri.parse(uriMyPilulierFormePharmaceutique+"FormePharmaceutique");
    Uri CONTENT_URI_MYPILULIER_PRESCRIPTION = Uri.parse(uriMyPilulierPrescription+"Prescription");
    Uri CONTENT_URI_MYPILULIER_PRISE = Uri.parse(uriMyPilulierPrise+"Prise");
    Uri CONTENT_URI_MYPILULIER_ASSOCIATION = Uri.parse(uriMyPilulierAssociation+"AssociationFormeDose");
    Uri CONTENT_URI_MYPILULIER_DOSE = Uri.parse(uriMyPilulierDose+"Dose");

    Uri CONTENT_URI_FINDDOCTOR_CONTACT = Uri.parse(uriFindDoctorContact+"Contact");
    Uri CONTENT_URI_FINDDOCTOR_CONTACT_LIGHT = Uri.parse(uriFindDoctorContactLight+"ContactLight");
    Uri CONTENT_URI_FINDDOCTOR_ETABLISSEMENT = Uri.parse(uriFindDoctorEtablissement+"Etablissement");
    Uri CONTENT_URI_FINDDOCTOR_ETABLISSEMENT_LIGHT = Uri.parse(uriFindDoctorEtablissementLight+"EtablissementLight");
    Uri CONTENT_URI_FINDDOCTOR_DEPARTEMENT = Uri.parse(uriFindDoctorDepartement+"Departement");
    Uri CONTENT_URI_FINDDOCTOR_REGION = Uri.parse(uriFindDoctorRegion+Region.class.getSimpleName());
    Uri CONTENT_URI_FINDDOCTOR_SAVOIR_FAIRE = Uri.parse(uriFindDoctorSavoirFaire+"SavoirFaire");
    Uri CONTENT_URI_FINDDOCTOR_PROFESSION = Uri.parse(uriFindDoctorProfession+"Profession");
    Uri CONTENT_URI_FINDDOCTOR_TYPE_ETABLISSEMENT = Uri.parse(uriFindDoctorTypeEtablissement+"TypeEtablissement");
    Uri CONTENT_URI_FINDDOCTOR_ASSOCIATION = Uri.parse(uriFindDoctorAssociation+"AssociationContactLightEtablissementLight");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialiserDao();
        //createNotificationChannel();
        associationFormeDoseDao = daoSession.getAssociationFormeDoseDao();
        doseDao = daoSession.getDoseDao();
        formePharmaceutiqueDao = daoSession.getFormePharmaceutiqueDao();
        medicamentDao = daoSession.getMedicamentDao();
        medicamentLightDao = daoSession.getMedicamentLightDao();
        prescriptionDao = daoSession.getPrescriptionDao();
        priseDao = daoSession.getPriseDao();
        analyseDao = daoSession.getAnalyseDao();
        associationContactLightEtablissementLightDao = daoSession.getAssociationContactLightEtablissementLightDao();
        contactDao = daoSession.getContactDao();
        contactLightDao = daoSession.getContactLightDao();
        departementDao = daoSession.getDepartementDao();
        etablissementDao = daoSession.getEtablissementDao();
        etablissementLightDao = daoSession.getEtablissementLightDao();
        examenDao = daoSession.getExamenDao();
        photoAnalyseDao = daoSession.getPhotoAnalyseDao();
        photoExamenDao = daoSession.getPhotoExamenDao();
        photoOrdonnanceDao = daoSession.getPhotoOrdonnanceDao();
        professionDao = daoSession.getProfessionDao();
        profilDao = daoSession.getProfilDao();
        regionDao = daoSession.getRegionDao();
        savoirFaireDao = daoSession.getSavoirFaireDao();
        typeEtablissementDao = daoSession.getTypeEtablissementDao();
        rdvAnalyseDao = daoSession.getRdvAnalyseDao();
        rdvExamenDao = daoSession.getRdvExamenDao();
        rdvContactDao = daoSession.getRdvContactDao();

        createMaterialDatePicker(false);
        createMaterialTimePicker();
    }


    public void createMaterialDatePicker(boolean chooseConstraint) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        today = materialDatePicker.todayInUtcMilliseconds();
        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        if (!chooseConstraint) {
            constraintBuilder.setValidator(DateValidatorPointForward.now());
        } else {
            constraintBuilder.setValidator(DateValidatorPointBackward.now());
        }
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Choisir une Date");
        builder.setSelection(today);
        builder.setCalendarConstraints(constraintBuilder.build());
        materialDatePicker = builder.build();
    }

    public void createMaterialTimePicker() {
        MaterialTimePicker.Builder builder = new MaterialTimePicker.Builder();
        builder.setTitleText("Choisir une Heure")
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(00);
        materialTimePicker = builder.build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
      /*  if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myProfilActivity;
        switch (item.getItemId()) {
            case R.id.listAllAnalyse:
                myProfilActivity = new Intent(NavDrawerActivity.this, AfficherAnalyseActivity.class);
                startActivity(myProfilActivity);
                return true;
            case R.id.listAllExamen:
                myProfilActivity = new Intent(NavDrawerActivity.this, AfficherExamenActivity.class);
                startActivity(myProfilActivity);
                return true;
            case R.id.majDatas:
                //myProfilActivity = new Intent(NavDrawerActivity.this, AfficherExamenActivity.class);
                //startActivity(myProfilActivity);
                majDatas();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void majDatas() {
        //simpleProgressBar.setVisibility(View.VISIBLE);
        synchroDatas(true);
        //simpleProgressBar.setVisibility(View.GONE);
        Snackbar.make(bottomNavigationView,"MAJ OK",Snackbar.LENGTH_LONG);
        //textView.setText("MAJ OVER");
    }

    public void configureBottomView(){
        this.bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bottom_navigation_home:
                                //ouvrirActiviteSuivante(NavDrawerActivity.this, AccueilActivity.class, true);
                                rouvrirActiviteAccueil(NavDrawerActivity.this,true);
                                break;
                            case R.id.bottom_navigation_rdv:
                                ouvrirActiviteSuivante(NavDrawerActivity.this, AfficherRdvActivity.class, true);
                                //cancelAlarmDialog();
                                break;
                            case R.id.bottom_navigation_photo:
                                ouvrirActiviteSuivante(NavDrawerActivity.this, AfficherPhotoActivity.class, true);
                                //cancelAlarmDialog();
                                break;
                            case R.id.bottom_navigation_profil:
                                ouvrirActiviteSuivante(NavDrawerActivity.this, AfficherProfilActivity.class, true);
                                //cancelAlarmDialog();
                                break;
                        }
                        return true;
                    }
                });
    }




    public void configureToolBar() {
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    public void ouvrirActiviteSuivante(Context context, Class classe, boolean bool) {
        Intent intent = new Intent(context, classe);
        startActivity(intent);
        if (bool) {
            finish();
        }
    }

    public void ouvrirActiviteSuivante(Context context, Class classe, String nomExtra, Long objetIdExtra, boolean bool) {
        Intent intent = new Intent(context, classe);
        intent.putExtra(nomExtra, objetIdExtra);
        startActivity(intent);
        if (bool) {
            finish();
        }
    }

    public void ouvrirActiviteSuivante(Context context, Class classe, String nomExtra, String objetExtra, String nomExtra2, Long objetIdExtra2, boolean bool) {
        Intent intent = new Intent(context, classe);
        intent.putExtra(nomExtra, objetExtra);
        intent.putExtra(nomExtra2, objetIdExtra2);
        startActivity(intent);
        if (bool) {
            finish();
        }
    }



    public void rouvrirActiviteAccueil(Context context, boolean bool) {
        Intent intent = new Intent(context, AccueilActivity.class);
        intent.putExtra("isSecondLaunch", true);
        startActivity(intent);
        if (bool) {
            finish();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    protected <T> void buildDropdownMenu(List<T> listObj, Context context, AutoCompleteTextView textView) {
        List<String> listString = new ArrayList<>();
        String[] listDeroulante;
        listDeroulante = new String[listObj.size()];
        for (T object : listObj) {
            listString.add(object.toString());
        }
        listString.toArray(listDeroulante);
        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.list_item, listDeroulante);
        textView.setAdapter(adapter);
    }

    @Override
    public Executor getMainExecutor() {
        return super.getMainExecutor();
    }

    /*protected Date ActualiserDate(Date date, String time){
        Date dateActualisee = date;
        int nbHour = Integer.parseInt(time.substring(0,2));
        int nbMinute = Integer.parseInt(time.substring(3));
        dateActualisee = DateUtils.ajouterHeure(dateActualisee,nbHour);
        dateActualisee = DateUtils.ajouterMinute(dateActualisee,nbMinute);
        return dateActualisee;
    }*/

    protected boolean isFilled(TextInputEditText textInputEditText){
        boolean bool;
        if (textInputEditText.length()>0) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }

    protected boolean isFilled(Object object){
        boolean bool;
        if (object!=null) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void initialiserDao() {
        //Base pendant dev
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "my_suivi_medical_db");
        //Base de prod
        //AppOpenHelper helper = new AppOpenHelper(this, "my_suivi_medical_db", null);
        Database db = helper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    /*public Dose recupDose(Medicament medoc) {
        long formeId = medoc.getFormePharmaceutiqueId();
        AssociationFormeDose assocFormeDose = associationFormeDoseDao.queryRaw("where forme_pharmaceutique_id = ?",""+formeId).get(0);
        Dose dose = doseDao.load(assocFormeDose.getDoseId());
        return dose;
    }*/

    public Date initDate(Date date) {
        GregorianCalendar calendar = new java.util.GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,8);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        date = calendar.getTime();
        return date;
    }

    // ATTENTION pr test on ajoute 1 minute seulement.
    /*public Date ajouterJour(Date date, int i) {
        GregorianCalendar calendar = new java.util.GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        //calendar.add(Calendar.MINUTE,1);
        date = calendar.getTime();
        return date;
    }*/

    /*protected void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MyPilulierNotificationChannel";
            String descripton = "Channel for notification of MyPilulier";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel("notifyPrise", name, importance);
            channel.setDescription(descripton);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }*/

    /*protected Date findDateJour() {
        Date date = initDate(new Date());
        return date;
    }*/

    protected static float floatArrondi(float number, int decimalPlace) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    protected String ecrireHeure(int heure,int minute) {
        String hour = "";
        String min = "";
        if (heure<10){
            hour+="0";
        }
        if (minute<10){
            min+="0";
        }
        hour+=heure;
        min+=minute;

        return hour + ":" + min;
    }

    public void synchroDatas(boolean bool) {
        Log.i("synchroDatas: ","start");
        //false = simple & true = forcé
        if (bool) {
            //synchro forcé
            synchroFindDoctor01Region(bool);
            Log.i("synchroDatas: ","region ok");
            synchroFindDoctor02Departement(bool);
            Log.i("synchroDatas: ","departement ok");
            synchroFindDoctor03SavoirFaire(bool);
            Log.i("synchroDatas: ","savoir faire ok");
            synchroFindDoctor04Profession(bool);
            Log.i("synchroDatas: ","profession ok");
            synchroFindDoctor05TypeEtablissement(bool);
            Log.i("synchroDatas: ","type etablissement ok");
            synchroFindDoctor06Contact(bool);
            Log.i("synchroDatas: ","contact ok");
            synchroFindDoctor07ContactLight(bool);
            Log.i("synchroDatas: ","contact light ok");
            synchroFindDoctor08Etablissement(bool);
            Log.i("synchroDatas: ","etablissement ok");
            synchroFindDoctor09EtablissementLight(bool);
            Log.i("synchroDatas: ","etab light ok");
            synchroFindDoctor10AssociationContactLightEtablissementLight(bool);
            Log.i("synchroDatas: ","assoc contact light etab light");
            synchroMyPilulier01FormePharmaceutique(bool);
            Log.i("synchroDatas: ","forma pharma ok");
            synchroMyPilulier02Dose(bool);
            Log.i("synchroDatas: ","dose ok");
            synchroMyPilulier03AssociationFormeDose(bool);
            Log.i("synchroDatas: ","assoc forme dose ok");
            synchroMyPilulier04Medicament(bool);
            Log.i("synchroDatas: ","medoc ok");
            synchroMyPilulier05MedicamentLight(bool);
            Log.i("synchroDatas: ","medoc light ok");
            synchroMyPilulier06Prescription(bool);
            Log.i("synchroDatas: ","prescription ok");
            synchroMyPilulier07Prise(bool);
            Log.i("synchroDatas: ","prise ok");

        } else {
            //synchro simple
            synchroFindDoctor06Contact(bool);
            synchroFindDoctor07ContactLight(bool);
            synchroFindDoctor08Etablissement(bool);
            synchroFindDoctor09EtablissementLight(bool);
            synchroFindDoctor10AssociationContactLightEtablissementLight(bool);
            synchroMyPilulier06Prescription(bool);
            synchroMyPilulier07Prise(bool);
        }
        Log.i("synchroDatas: ","end");
    }

    public void synchroFindDoctor01Region(boolean bool) {
        List<Region> listRegionSynchronized = recupererRegionFromFindDoctor();
        if (bool) {
            regionDao.deleteAll();
            for (Region current : listRegionSynchronized){
                regionDao.insert(current);
            }
        } else {
            for (Region current : listRegionSynchronized){
                if (regionDao.load(current.getId())==null) {
                    regionDao.insert(current);
                }
            }
        }
    }

    public void synchroFindDoctor02Departement(boolean bool) {
        List<Departement> listDepartementSynchronized = recupererDepartementFromFindDoctor();
        if (bool) {
            departementDao.deleteAll();
            for (Departement current : listDepartementSynchronized) {
                departementDao.insert(current);
            }
        } else {
            for (Departement current : listDepartementSynchronized) {
                if (departementDao.load(current.getId()) == null) {
                    departementDao.insert(current);
                }
            }
        }
    }

    public void synchroFindDoctor03SavoirFaire(boolean bool) {
        List<SavoirFaire> listSavoirFaireSynchronized = recupererSavoirFaireFromFindDoctor();
        if (bool) {
            savoirFaireDao.deleteAll();
            for (SavoirFaire current : listSavoirFaireSynchronized) {
                savoirFaireDao.insert(current);
            }
        } else {
            for (SavoirFaire current : listSavoirFaireSynchronized) {
                if (savoirFaireDao.load(current.getId()) == null) {
                    savoirFaireDao.insert(current);
                }
            }
        }
    }

    public void synchroFindDoctor04Profession(boolean bool) {
        List<Profession> listProfessionSynchronized = recupererProfessionFromFindDoctor();
        if (bool) {
            professionDao.deleteAll();
            for (Profession current : listProfessionSynchronized) {
                professionDao.insert(current);
            }
        } else {
            for (Profession current : listProfessionSynchronized) {
                if (professionDao.load(current.getId()) == null) {
                    professionDao.insert(current);
                }
            }
        }
    }

    public void synchroFindDoctor05TypeEtablissement(boolean bool) {
        List<TypeEtablissement> listTypeEtablissementSynchronized = recupererTypeEtablissementFromFindDoctor();
        if (bool) {
            typeEtablissementDao.deleteAll();
            for (TypeEtablissement current : listTypeEtablissementSynchronized) {
                typeEtablissementDao.insert(current);
            }
        } else {
            for (TypeEtablissement current : listTypeEtablissementSynchronized) {
                if (typeEtablissementDao.load(current.getId()) == null) {
                    typeEtablissementDao.insert(current);
                }
            }
        }
    }

    public void synchroFindDoctor06Contact(boolean bool) {
        List<Contact> listContactSynchronized = recupererContactFromFindDoctor();
        if (bool) {
            contactDao.deleteAll();
            for (Contact current : listContactSynchronized) {
                contactDao.insert(current);
            }
        } else {
            for (Contact current : listContactSynchronized) {
                if (contactDao.load(current.getId()) == null) {
                    contactDao.insert(current);
                }
            }
        }
    }

    public void synchroFindDoctor07ContactLight(boolean bool) {
        List<ContactLight> listContactLightSynchronized = recupererContactLightFromFindDoctor();
        if (bool) {
            contactLightDao.deleteAll();
            for (ContactLight current : listContactLightSynchronized) {
                contactLightDao.insert(current);
            }
        } else {
            for (ContactLight current : listContactLightSynchronized) {
                if (contactLightDao.load(current.getId()) == null) {
                    contactLightDao.insert(current);
                }
            }
        }
    }

    public void synchroFindDoctor08Etablissement(boolean bool) {
        List<Etablissement> listEtablissementSynchronized = recupererEtablissementFromFindDoctor();
        if (bool) {
            etablissementDao.deleteAll();
            for (Etablissement current : listEtablissementSynchronized) {
                etablissementDao.insert(current);
            }
        } else {
            for (Etablissement current : listEtablissementSynchronized) {
                if (etablissementDao.load(current.getId()) == null) {
                    etablissementDao.insert(current);
                }
            }
        }
    }

    public void synchroFindDoctor09EtablissementLight(boolean bool) {
        List<EtablissementLight> listEtablissementLightSynchronized = recupererEtablissementLightFromFindDoctor();
        if (bool) {
            etablissementLightDao.deleteAll();
            for (EtablissementLight current : listEtablissementLightSynchronized) {
                etablissementLightDao.insert(current);
            }
        } else {
            for (EtablissementLight current : listEtablissementLightSynchronized) {
                if (etablissementLightDao.load(current.getId()) == null) {
                    etablissementLightDao.insert(current);
                }
            }
        }
    }

    public void synchroFindDoctor10AssociationContactLightEtablissementLight(boolean bool) {
        List<AssociationContactLightEtablissementLight> listAssociationContactLightEtablissementLightSynchronized = recupererAssociationContactLightEtablissementLightFromFindDoctor();
        if (bool) {
            associationContactLightEtablissementLightDao.deleteAll();
            for (AssociationContactLightEtablissementLight current : listAssociationContactLightEtablissementLightSynchronized) {
                associationContactLightEtablissementLightDao.insert(current);
            }
        } else {
            for (AssociationContactLightEtablissementLight current : listAssociationContactLightEtablissementLightSynchronized) {
                if (associationContactLightEtablissementLightDao.load(current.getId()) == null) {
                    associationContactLightEtablissementLightDao.insert(current);
                }
            }
        }
    }

    public void synchroMyPilulier01FormePharmaceutique(boolean bool) {
        List<FormePharmaceutique> listFormePharmaceutiqueSynchronized = recupererFormePharmaceutiqueFromMyPilulier();
        if (bool) {
            formePharmaceutiqueDao.deleteAll();
            for (FormePharmaceutique current : listFormePharmaceutiqueSynchronized) {
                formePharmaceutiqueDao.insert(current);
            }
        } else {
            for (FormePharmaceutique current : listFormePharmaceutiqueSynchronized) {
                if (formePharmaceutiqueDao.load(current.getId()) == null) {
                    formePharmaceutiqueDao.insert(current);
                }
            }
        }
    }

    public void synchroMyPilulier02Dose(boolean bool) {
        List<Dose> listDoseSynchronized = recupererDoseFromMyPilulier();
        if (bool) {
            doseDao.deleteAll();
            for (Dose current : listDoseSynchronized) {
                doseDao.insert(current);
            }
        } else {
            for (Dose current : listDoseSynchronized) {
                if (doseDao.load(current.getId()) == null) {
                    doseDao.insert(current);
                }
            }
        }
    }

    public void synchroMyPilulier03AssociationFormeDose(boolean bool) {
        List<AssociationFormeDose> listAssociationFormeDoseSynchronized = recupererAssociationFormeDoseFromMyPilulier();
        if (bool) {
            associationFormeDoseDao.deleteAll();
            for (AssociationFormeDose current : listAssociationFormeDoseSynchronized) {
                associationFormeDoseDao.insert(current);
            }
        } else {
            for (AssociationFormeDose current : listAssociationFormeDoseSynchronized) {
                if (associationFormeDoseDao.load(current.getId()) == null) {
                    associationFormeDoseDao.insert(current);
                }
            }
        }
    }

    public void synchroMyPilulier04Medicament(boolean bool) {
        List<Medicament> listMedicamentSynchronized = recupererMedicamentFromMyPilulier();
        if (bool) {
            medicamentDao.deleteAll();
            for (Medicament current : listMedicamentSynchronized) {
                medicamentDao.insert(current);
            }
        } else {
            for (Medicament current : listMedicamentSynchronized) {
                if (medicamentDao.load(current.getId()) == null) {
                    medicamentDao.insert(current);
                }
            }
        }
    }

    public void synchroMyPilulier05MedicamentLight(boolean bool) {
        List<MedicamentLight> listMedicamentLightSynchronized = recupererMedicamentLightFromMyPilulier();
        if (bool) {
            medicamentLightDao.deleteAll();
            for (MedicamentLight current : listMedicamentLightSynchronized) {
                medicamentLightDao.insert(current);
            }
        } else {
            for (MedicamentLight current : listMedicamentLightSynchronized) {
                if (medicamentLightDao.load(current.getId()) == null) {
                    medicamentLightDao.insert(current);
                }
            }
        }
    }

    public void synchroMyPilulier06Prescription(boolean bool) {
        List<Prescription> listPrescriptionSynchronized = recupererPrescriptionFromMyPilulier();
        if (bool) {
            prescriptionDao.deleteAll();
            for (Prescription current : listPrescriptionSynchronized) {
                prescriptionDao.insert(current);
            }
        } else {
            for (Prescription current : listPrescriptionSynchronized) {
                if (prescriptionDao.load(current.getId()) == null) {
                    prescriptionDao.insert(current);
                }
            }
        }
    }

    public void synchroMyPilulier07Prise(boolean bool) {
        List<Prise> listPriseSynchronized = recupererPriseFromMyPilulier();
        if (bool) {
            priseDao.deleteAll();
            for (Prise current : listPriseSynchronized) {
                priseDao.insert(current);
            }
        } else {
            for (Prise current : listPriseSynchronized) {
                if (priseDao.load(current.getId()) == null) {
                    priseDao.insert(current);
                }
            }
        }
    }

    public List<Contact> recupererContactFromFindDoctor() {
        Cursor cursor = getContentResolver().query(CONTENT_URI_FINDDOCTOR_CONTACT, null, "selected", null, null);
        List<Contact> myList = new ArrayList<>();
        while(cursor.moveToNext()) {
            Contact currentContact = new Contact();
            currentContact.setId(cursor.getLong(cursor.getColumnIndex("_id")));
            currentContact.setIdPP(cursor.getString(cursor.getColumnIndex("idPP")));
            currentContact.setCodeCivilite(cursor.getString(cursor.getColumnIndex("codeCivilite")));
            currentContact.setNom(cursor.getString(cursor.getColumnIndex("nom")));
            currentContact.setPrenom(cursor.getString(cursor.getColumnIndex("prenom")));
            currentContact.setProfessionId(cursor.getLong(cursor.getColumnIndex("professionId")));
            currentContact.setSavoirFaireId(cursor.getLong(cursor.getColumnIndex("savoirFaireId")));
            currentContact.setRaisonSocial(cursor.getString(cursor.getColumnIndex("raisonSocial")));
            currentContact.setComplement(cursor.getString(cursor.getColumnIndex("complement")));
            currentContact.setAdresse(cursor.getString(cursor.getColumnIndex("adresse")));
            currentContact.setCp(cursor.getString(cursor.getColumnIndex("cp")));
            currentContact.setVille(cursor.getString(cursor.getColumnIndex("ville")));
            currentContact.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
            currentContact.setFax(cursor.getString(cursor.getColumnIndex("fax")));
            currentContact.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            currentContact.setDepartementId(cursor.getLong(cursor.getColumnIndex("departementId")));
            currentContact.setRegionId(cursor.getLong(cursor.getColumnIndex("regionId")));
            currentContact.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
            currentContact.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
            currentContact.setIsSelected(cursor.getInt(cursor.getColumnIndex("isSelected"))==1);
            myList.add(currentContact);
        }
        cursor.close();
        return myList;
    }

    public List<ContactLight> recupererContactLightFromFindDoctor() {
        Cursor cursor = getContentResolver().query(CONTENT_URI_FINDDOCTOR_CONTACT_LIGHT, null, "selected", null, null);
        List<ContactLight> myList = new ArrayList<>();
        while(cursor.moveToNext()) {
            ContactLight currentContactLight = new ContactLight();
            currentContactLight.setId(cursor.getLong(cursor.getColumnIndex("_id")));
            currentContactLight.setIdPP(cursor.getString(cursor.getColumnIndex("idPP")));
            currentContactLight.setCodeCivilite(cursor.getString(cursor.getColumnIndex("codeCivilite")));
            currentContactLight.setNom(cursor.getString(cursor.getColumnIndex("nom")));
            currentContactLight.setPrenom(cursor.getString(cursor.getColumnIndex("prenom")));
            currentContactLight.setRaisonSocial(cursor.getString(cursor.getColumnIndex("raisonSocial")));
            currentContactLight.setComplement(cursor.getString(cursor.getColumnIndex("complement")));
            currentContactLight.setAdresse(cursor.getString(cursor.getColumnIndex("adresse")));
            currentContactLight.setCp(cursor.getString(cursor.getColumnIndex("cp")));
            currentContactLight.setVille(cursor.getString(cursor.getColumnIndex("ville")));
            currentContactLight.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
            currentContactLight.setFax(cursor.getString(cursor.getColumnIndex("fax")));
            currentContactLight.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            myList.add(currentContactLight);
        }
        cursor.close();
        return myList;
    }

    public List<Etablissement> recupererEtablissementFromFindDoctor() {
        Cursor cursor = getContentResolver().query(CONTENT_URI_FINDDOCTOR_ETABLISSEMENT, null, "selected", null, null);
        List<Etablissement> myList = new ArrayList<>();
        while(cursor.moveToNext()) {
            Etablissement currentEtablissement = new Etablissement();
            currentEtablissement.setId(cursor.getLong(cursor.getColumnIndex("_id")));
            currentEtablissement.setNumeroFinessET(cursor.getString(cursor.getColumnIndex("numeroFinessET")));
            currentEtablissement.setRaisonSocial(cursor.getString(cursor.getColumnIndex("raisonSocial")));
            currentEtablissement.setAdresse(cursor.getString(cursor.getColumnIndex("adresse")));
            currentEtablissement.setCp(cursor.getString(cursor.getColumnIndex("cp")));
            currentEtablissement.setVille(cursor.getString(cursor.getColumnIndex("ville")));
            currentEtablissement.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
            currentEtablissement.setFax(cursor.getString(cursor.getColumnIndex("fax")));
            currentEtablissement.setDepartementId(cursor.getLong(cursor.getColumnIndex("departementId")));
            currentEtablissement.setRegionId(cursor.getLong(cursor.getColumnIndex("regionId")));
            currentEtablissement.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
            currentEtablissement.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
            currentEtablissement.setTypeEtablissementId(cursor.getLong(cursor.getColumnIndex("typeEtablissementId")));
            currentEtablissement.setIsSelected(cursor.getInt(cursor.getColumnIndex("isSelected"))==1);
            myList.add(currentEtablissement);
        }
        cursor.close();
        return myList;
    }

    public List<EtablissementLight> recupererEtablissementLightFromFindDoctor() {
        Cursor cursor = getContentResolver().query(CONTENT_URI_FINDDOCTOR_ETABLISSEMENT_LIGHT, null, "selected", null, null);
        List<EtablissementLight> myList = new ArrayList<>();
        while(cursor.moveToNext()) {
            EtablissementLight currentEtablissementLight = new EtablissementLight();
            currentEtablissementLight.setId(cursor.getLong(cursor.getColumnIndex("_id")));
            currentEtablissementLight.setNumeroFinessET(cursor.getString(cursor.getColumnIndex("numeroFinessET")));
            currentEtablissementLight.setRaisonSocial(cursor.getString(cursor.getColumnIndex("raisonSocial")));
            currentEtablissementLight.setAdresse(cursor.getString(cursor.getColumnIndex("adresse")));
            currentEtablissementLight.setCp(cursor.getString(cursor.getColumnIndex("cp")));
            currentEtablissementLight.setVille(cursor.getString(cursor.getColumnIndex("ville")));
            currentEtablissementLight.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
            currentEtablissementLight.setFax(cursor.getString(cursor.getColumnIndex("fax")));
            myList.add(currentEtablissementLight);
        }
        cursor.close();
        return myList;
    }

    public List<Departement> recupererDepartementFromFindDoctor() {
        Cursor cursor = getContentResolver().query(CONTENT_URI_FINDDOCTOR_DEPARTEMENT, null, null, null, null);
        List<Departement> myList = new ArrayList<>();
        while(cursor.moveToNext()) {
            Departement currentDepartement = new Departement();
            currentDepartement.setId(cursor.getLong(cursor.getColumnIndex("_id")));
            currentDepartement.setNumero(cursor.getString(cursor.getColumnIndex("numero")));
            currentDepartement.setNom(cursor.getString(cursor.getColumnIndex("nom")));
            currentDepartement.setRegionId(cursor.getLong(cursor.getColumnIndex("regionId")));
            myList.add(currentDepartement);
        }
        cursor.close();
        return myList;
    }

    public List<Region> recupererRegionFromFindDoctor() {
        Cursor cursor = getContentResolver().query(CONTENT_URI_FINDDOCTOR_REGION, null, null, null, null);
        List<Region> myList = new ArrayList<>();
        while(cursor.moveToNext()) {
            Region currentRegion = new Region();
            currentRegion.setId(cursor.getLong(cursor.getColumnIndex("_id")));
            currentRegion.setNom(cursor.getString(cursor.getColumnIndex("nom")));
            myList.add(currentRegion);
        }
        cursor.close();
        return myList;
    }

    public List<SavoirFaire> recupererSavoirFaireFromFindDoctor() {
        Cursor cursor = getContentResolver().query(CONTENT_URI_FINDDOCTOR_SAVOIR_FAIRE, null, null, null, null);
        List<SavoirFaire> myList = new ArrayList<>();
        while(cursor.moveToNext()) {
            SavoirFaire currentSavoirFaire = new SavoirFaire();
            currentSavoirFaire.setId(cursor.getLong(cursor.getColumnIndex("_id")));
            currentSavoirFaire.setName(cursor.getString(cursor.getColumnIndex("name")));
            myList.add(currentSavoirFaire);
        }
        cursor.close();
        return myList;
    }

    public List<Profession> recupererProfessionFromFindDoctor() {
        Cursor cursor = getContentResolver().query(CONTENT_URI_FINDDOCTOR_PROFESSION, null, null, null, null);
        List<Profession> myList = new ArrayList<>();
        while(cursor.moveToNext()) {
            Profession currentProfession = new Profession();
            currentProfession.setId(cursor.getLong(cursor.getColumnIndex("_id")));
            currentProfession.setName(cursor.getString(cursor.getColumnIndex("name")));
            myList.add(currentProfession);
        }
        cursor.close();
        return myList;
    }

    public List<TypeEtablissement> recupererTypeEtablissementFromFindDoctor() {
        Cursor cursor = getContentResolver().query(CONTENT_URI_FINDDOCTOR_TYPE_ETABLISSEMENT, null, null, null, null);
        List<TypeEtablissement> myList = new ArrayList<>();
        while(cursor.moveToNext()) {
            TypeEtablissement currentTypeEtablissement = new TypeEtablissement();
            currentTypeEtablissement.setId(cursor.getLong(cursor.getColumnIndex("_id")));
            currentTypeEtablissement.setName(cursor.getString(cursor.getColumnIndex("name")));
            myList.add(currentTypeEtablissement);
        }
        cursor.close();
        return myList;
    }

    public List<AssociationContactLightEtablissementLight> recupererAssociationContactLightEtablissementLightFromFindDoctor() {
        Cursor cursor = getContentResolver().query(CONTENT_URI_FINDDOCTOR_ASSOCIATION, null, "selected", null, null);
        List<AssociationContactLightEtablissementLight> myList = new ArrayList<>();
        while(cursor.moveToNext()) {
            AssociationContactLightEtablissementLight currentAssociationContactLightEtablissementLight = new AssociationContactLightEtablissementLight();
            currentAssociationContactLightEtablissementLight.setId(cursor.getLong(cursor.getColumnIndex("_id")));
            currentAssociationContactLightEtablissementLight.setContactLightId(cursor.getLong(cursor.getColumnIndex("contactLightId")));
            currentAssociationContactLightEtablissementLight.setEtablissementLightId(cursor.getLong(cursor.getColumnIndex("etablissementLightId")));
            myList.add(currentAssociationContactLightEtablissementLight);
        }
        cursor.close();
        return myList;
    }

    public List<FormePharmaceutique> recupererFormePharmaceutiqueFromMyPilulier() {
        Cursor cursor = getContentResolver().query(CONTENT_URI_MYPILULIER_FORME_PHARMACEUTIQUE, null, null, null, null);
        List<FormePharmaceutique> myList = new ArrayList<>();
        while(cursor.moveToNext()) {
            FormePharmaceutique currentFormePharmaceutique = new FormePharmaceutique();
            currentFormePharmaceutique.setId(cursor.getLong(cursor.getColumnIndex("_id")));
            currentFormePharmaceutique.setName(cursor.getString(cursor.getColumnIndex("name")));
            myList.add(currentFormePharmaceutique);
        }
        cursor.close();
        return myList;
    }

    public List<Dose> recupererDoseFromMyPilulier() {
        Cursor cursor = getContentResolver().query(CONTENT_URI_MYPILULIER_DOSE, null, null, null, null);
        List<Dose> myList = new ArrayList<>();
        while(cursor.moveToNext()) {
            Dose currentDose = new Dose();
            currentDose.setId(cursor.getLong(cursor.getColumnIndex("_id")));
            currentDose.setName(cursor.getString(cursor.getColumnIndex("name")));
            myList.add(currentDose);
        }
        cursor.close();
        return myList;
    }

    public List<AssociationFormeDose> recupererAssociationFormeDoseFromMyPilulier() {
        Cursor cursor = getContentResolver().query(CONTENT_URI_MYPILULIER_ASSOCIATION, null, null, null, null);
        List<AssociationFormeDose> myList = new ArrayList<>();
        while(cursor.moveToNext()) {
            AssociationFormeDose currentAssociationFormeDose = new AssociationFormeDose();
            currentAssociationFormeDose.setId(cursor.getLong(cursor.getColumnIndex("_id")));
            currentAssociationFormeDose.setFormePharmaceutiqueId(cursor.getLong(cursor.getColumnIndex("formePharmaceutiqueId")));
            currentAssociationFormeDose.setDoseId(cursor.getLong(cursor.getColumnIndex("doseId")));
            myList.add(currentAssociationFormeDose);
        }
        cursor.close();
        return myList;
    }

    public List<Medicament> recupererMedicamentFromMyPilulier() {
        Cursor cursor = getContentResolver().query(CONTENT_URI_MYPILULIER_MEDICAMENT, null, null, null, null);
        List<Medicament> myList = new ArrayList<>();
        while(cursor.moveToNext()) {
            Medicament currentMedicament = new Medicament();
            currentMedicament.setId(cursor.getLong(cursor.getColumnIndex("_id")));
            currentMedicament.setCodeCIS(cursor.getLong(cursor.getColumnIndex("codeCIS")));
            currentMedicament.setDenomination(cursor.getString(cursor.getColumnIndex("denomination")));
            currentMedicament.setDenominationShort(cursor.getString(cursor.getColumnIndex("denominationShort")));
            currentMedicament.setFormePharmaceutiqueId(cursor.getLong(cursor.getColumnIndex("formePharmaceutiqueId")));
            currentMedicament.setTitulaire(cursor.getString(cursor.getColumnIndex("titulaire")));
            myList.add(currentMedicament);
        }
        cursor.close();
        return myList;
    }

    public List<MedicamentLight> recupererMedicamentLightFromMyPilulier() {
        Cursor cursor = getContentResolver().query(CONTENT_URI_MYPILULIER_MEDICAMENT_LIGHT, null, null, null, null);
        List<MedicamentLight> myList = new ArrayList<>();
        while(cursor.moveToNext()) {
            MedicamentLight currentMedicamentLight = new MedicamentLight();
            currentMedicamentLight.setId(cursor.getLong(cursor.getColumnIndex("_id")));
            currentMedicamentLight.setCodeCIS(cursor.getLong(cursor.getColumnIndex("codeCIS")));
            currentMedicamentLight.setDenomination(cursor.getString(cursor.getColumnIndex("denomination")));
            myList.add(currentMedicamentLight);
        }
        cursor.close();
        return myList;
    }

    public List<Prescription> recupererPrescriptionFromMyPilulier() {
        Cursor cursor = getContentResolver().query(CONTENT_URI_MYPILULIER_PRESCRIPTION, null, null, null, null);
        List<Prescription> myList = new ArrayList<>();
        while(cursor.moveToNext()) {
            Prescription currentPrescription = new Prescription();
            currentPrescription.setId(cursor.getLong(cursor.getColumnIndex("_id")));
            currentPrescription.setQte(cursor.getFloat(cursor.getColumnIndex("qte")));
            currentPrescription.setMedicamentId(cursor.getLong(cursor.getColumnIndex("medicamentId")));
            currentPrescription.setFrequence(Frequence.fromString(cursor.getString(cursor.getColumnIndex("frequence"))));
            Date date = new Date();
            date.setTime(cursor.getLong(cursor.getColumnIndex("dateFin")));
            currentPrescription.setDateFin(date);
            currentPrescription.setDateFinString(cursor.getString(cursor.getColumnIndex("dateFinString")));
            myList.add(currentPrescription);
        }
        cursor.close();
        return myList;
    }

    public List<Prise> recupererPriseFromMyPilulier() {
        Cursor cursor = getContentResolver().query(CONTENT_URI_MYPILULIER_PRISE, null, null, null, null);
        List<Prise> myList = new ArrayList<>();
        while(cursor.moveToNext()) {
            Prise currentPrise = new Prise();
            currentPrise.setId(cursor.getLong(cursor.getColumnIndex("_id")));
            Date date = new Date();
            date.setTime(cursor.getLong(cursor.getColumnIndex("date")));
            currentPrise.setDate(date);
            currentPrise.setDateString(cursor.getString(cursor.getColumnIndex("dateString")));
            currentPrise.setEffectue(cursor.getInt(cursor.getColumnIndex("effectue"))==1);
            currentPrise.setMedicamentId(cursor.getLong(cursor.getColumnIndex("medicamentId")));
            currentPrise.setDoseId(cursor.getLong(cursor.getColumnIndex("doseId")));
            currentPrise.setQteDose(cursor.getFloat(cursor.getColumnIndex("qteDose")));
            myList.add(currentPrise);
        }
        cursor.close();
        return myList;
    }
}
