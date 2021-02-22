package com.pouillos.mysuivimedical.activities;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.pouillos.mysuivimedical.R;

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
import com.pouillos.mysuivimedical.dao.PhotoDao;
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
import com.pouillos.mysuivimedical.entities.Prise;
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
    protected PhotoDao photoDao;
    protected ProfessionDao professionDao;
    protected ProfilDao profilDao;
    protected RegionDao regionDao;
    protected SavoirFaireDao savoirFaireDao;
    protected TypeEtablissementDao typeEtablissementDao;
    protected RdvExamenDao rdvExamenDao;
    protected RdvAnalyseDao rdvAnalyseDao;
    protected RdvContactDao rdvContactDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialiserDao();
        createNotificationChannel();
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
        photoDao = daoSession.getPhotoDao();
        professionDao = daoSession.getProfessionDao();
        profilDao = daoSession.getProfilDao();
        regionDao = daoSession.getRegionDao();
        savoirFaireDao = daoSession.getSavoirFaireDao();
        typeEtablissementDao = daoSession.getTypeEtablissementDao();
        rdvAnalyseDao = daoSession.getRdvAnalyseDao();
        rdvExamenDao = daoSession.getRdvExamenDao();
        rdvContactDao = daoSession.getRdvContactDao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
                            case R.id.bottom_navigation_search_doctor:
                                //ouvrirActiviteSuivante(NavDrawerActivity.this, AddPrescriptionActivity.class, true);
                                break;
                            case R.id.bottom_navigation_cancel_alarm:
                                //ouvrirActiviteSuivante(NavDrawerActivity.this, AddPrescriptionActivity.class, true);
                                //cancelAlarmDialog();
                                break;
                        }
                        return true;
                    }
                });
    }

    public void cancelAlarmDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Annuler Alarmes")
                .setMessage("Suppression de toutes les alarmes")
                .setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        razAlarmes();
                    }
                })
                .setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(NavDrawerActivity.this, "RAZ Annulé", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    public void razAlarmes() {
        List<Prise> listPrise = priseDao.loadAll();
        for (Prise prise : listPrise) {
            cancelAlarm(prise,this);
        }
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

    protected void scheduleAlarm(Prise prise, Context context) {
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent= new Intent(context, Alarm.class);
        int requestId = ((Long) prise.getDate().getTime()).intValue()+prise.getId().intValue();
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,requestId,intent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,prise.getDate().getTime(),pendingIntent);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent);
        //Toast.makeText(this,"Your Alarm is Set",Toast.LENGTH_LONG).show();
    }

    protected void cancelAlarm(Prise prise, Context context) {
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent= new Intent(context, Alarm.class);
        int requestId = ((Long) prise.getDate().getTime()).intValue()+prise.getId().intValue();
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,requestId,intent,0);
        alarmManager.cancel(pendingIntent);
        //Toast.makeText(this,"Your Alarm is Cancel",Toast.LENGTH_LONG).show();

    }


    protected void notifSchedule(Prise prise, Context context) {
        Intent intent = new Intent(context, ReminderBroadcast.class);
        //int requestId = ((Long) new Date().getTime()).intValue();
        int requestId = ((Long) prise.getDate().getTime()).intValue()+prise.getId().intValue();
        String string = "";
        if (BasicUtils.isInteger(prise.getQteDose())) {
            string += Math.round(prise.getQteDose());
        } else {
            string += prise.getQteDose();
        }
        string +=" "+prise.getDose().getName() + " - ";
        if (prise.getMedicament().getDenomination().length()>17) {
            string += prise.getMedicament().getDenomination().substring(0,16)+"...";
        } else {
            string += prise.getMedicament().getDenomination();
        }
        intent.putExtra("content",string);
        intent.putExtra("requestId",requestId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestId, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, prise.getDate().getTime(),pendingIntent);
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
}
