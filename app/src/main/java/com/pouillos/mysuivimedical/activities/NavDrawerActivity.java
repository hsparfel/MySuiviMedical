package com.pouillos.mysuivimedical.activities;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.pouillos.mysuivimedical.dao.AnalyseDao;
import com.pouillos.mysuivimedical.dao.ContactDao;
import com.pouillos.mysuivimedical.dao.DaoMaster;
import com.pouillos.mysuivimedical.dao.DaoSession;
import com.pouillos.mysuivimedical.dao.DepartementDao;

import com.pouillos.mysuivimedical.dao.ExamenDao;

import com.pouillos.mysuivimedical.dao.PhotoAnalyseDao;

import com.pouillos.mysuivimedical.dao.PhotoExamenDao;
import com.pouillos.mysuivimedical.dao.PhotoOrdonnanceDao;

import com.pouillos.mysuivimedical.dao.ProfessionDao;
import com.pouillos.mysuivimedical.dao.ProfilDao;
import com.pouillos.mysuivimedical.dao.RdvAnalyseDao;
import com.pouillos.mysuivimedical.dao.RdvContactDao;
import com.pouillos.mysuivimedical.dao.RdvExamenDao;
import com.pouillos.mysuivimedical.dao.RegionDao;
import com.pouillos.mysuivimedical.dao.SavoirFaireDao;
import com.pouillos.mysuivimedical.entities.Contact;
import com.pouillos.mysuivimedical.entities.Departement;
import com.pouillos.mysuivimedical.entities.Profession;
import com.pouillos.mysuivimedical.entities.Region;
import com.pouillos.mysuivimedical.entities.SavoirFaire;
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

    protected AnalyseDao analyseDao;
    protected ContactDao contactDao;
    protected DepartementDao departementDao;
    protected ExamenDao examenDao;
    protected PhotoAnalyseDao photoAnalyseDao;
    protected PhotoExamenDao photoExamenDao;
    protected PhotoOrdonnanceDao photoOrdonnanceDao;
    protected ProfessionDao professionDao;
    protected ProfilDao profilDao;
    protected RegionDao regionDao;
    protected SavoirFaireDao savoirFaireDao;

    protected RdvExamenDao rdvExamenDao;
    protected RdvAnalyseDao rdvAnalyseDao;
    protected RdvContactDao rdvContactDao;

    protected MaterialDatePicker materialDatePicker;
    protected MaterialTimePicker materialTimePicker;
    protected Long today;

    String uriFindDoctorRegion = "content://com.pouillos.finddoctor.provider.region/";
    String uriFindDoctorContact = "content://com.pouillos.finddoctor.provider.contact/";

    String uriFindDoctorDepartement = "content://com.pouillos.finddoctor.provider.departement/";
    String uriFindDoctorSavoirFaire = "content://com.pouillos.finddoctor.provider.savoirfaire/";
    String uriFindDoctorProfession = "content://com.pouillos.finddoctor.provider.profession/";


    Uri CONTENT_URI_FINDDOCTOR_CONTACT = Uri.parse(uriFindDoctorContact+"Contact");

    Uri CONTENT_URI_FINDDOCTOR_DEPARTEMENT = Uri.parse(uriFindDoctorDepartement+"Departement");
    Uri CONTENT_URI_FINDDOCTOR_REGION = Uri.parse(uriFindDoctorRegion+Region.class.getSimpleName());
    Uri CONTENT_URI_FINDDOCTOR_SAVOIR_FAIRE = Uri.parse(uriFindDoctorSavoirFaire+"SavoirFaire");
    Uri CONTENT_URI_FINDDOCTOR_PROFESSION = Uri.parse(uriFindDoctorProfession+"Profession");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialiserDao();

        analyseDao = daoSession.getAnalyseDao();
        contactDao = daoSession.getContactDao();
        departementDao = daoSession.getDepartementDao();

        examenDao = daoSession.getExamenDao();
        photoAnalyseDao = daoSession.getPhotoAnalyseDao();
        photoExamenDao = daoSession.getPhotoExamenDao();
        photoOrdonnanceDao = daoSession.getPhotoOrdonnanceDao();
        professionDao = daoSession.getProfessionDao();
        profilDao = daoSession.getProfilDao();
        regionDao = daoSession.getRegionDao();
        savoirFaireDao = daoSession.getSavoirFaireDao();

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

            synchroFindDoctor06Contact(bool);
            Log.i("synchroDatas: ","contact ok");



        } else {
            //synchro simple
            synchroFindDoctor06Contact(bool);
            Log.i("synchroDatas: ","contact ok");

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




}
