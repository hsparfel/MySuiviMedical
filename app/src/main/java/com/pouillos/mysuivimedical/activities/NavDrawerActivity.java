package com.pouillos.mysuivimedical.activities;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.pouillos.mysuivimedical.R;
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
import com.pouillos.mysuivimedical.entities.AssociationFormeDose;
import com.pouillos.mysuivimedical.entities.Dose;
import com.pouillos.mysuivimedical.entities.Medicament;
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
        return true;
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

    protected Date ActualiserDate(Date date, String time){
        Date dateActualisee = date;
        int nbHour = Integer.parseInt(time.substring(0,2));
        int nbMinute = Integer.parseInt(time.substring(3));
        dateActualisee = DateUtils.ajouterHeure(dateActualisee,nbHour);
        dateActualisee = DateUtils.ajouterMinute(dateActualisee,nbMinute);
        return dateActualisee;
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

    public Dose recupDose(Medicament medoc) {
        long formeId = medoc.getFormePharmaceutiqueId();
        AssociationFormeDose assocFormeDose = associationFormeDoseDao.queryRaw("where forme_pharmaceutique_id = ?",""+formeId).get(0);
        Dose dose = doseDao.load(assocFormeDose.getDoseId());
        return dose;
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

    // ATTENTION pr test on ajoute 1 minute seulement.
    public Date ajouterJour(Date date, int i) {
        GregorianCalendar calendar = new java.util.GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        //calendar.add(Calendar.MINUTE,1);
        date = calendar.getTime();
        return date;
    }

    protected void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MyPilulierNotificationChannel";
            String descripton = "Channel for notification of MyPilulier";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel("notifyPrise", name, importance);
            channel.setDescription(descripton);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }








    protected Date findDateJour() {
        Date date = initDate(new Date());
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
}
