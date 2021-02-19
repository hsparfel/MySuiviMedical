package com.pouillos.mypilulier.activities;


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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.pouillos.mypilulier.R;
import com.pouillos.mypilulier.activities.add.AddPrescriptionActivity;
import com.pouillos.mypilulier.activities.tools.Alarm;
import com.pouillos.mypilulier.activities.tools.ReminderBroadcast;

import com.pouillos.mypilulier.dao.AssociationFormeDoseDao;
import com.pouillos.mypilulier.dao.DaoMaster;
import com.pouillos.mypilulier.dao.DaoSession;
import com.pouillos.mypilulier.dao.DoseDao;
import com.pouillos.mypilulier.dao.FormePharmaceutiqueDao;
import com.pouillos.mypilulier.dao.ImportMedicamentDao;
import com.pouillos.mypilulier.dao.MedicamentDao;
import com.pouillos.mypilulier.dao.MedicamentLightDao;
import com.pouillos.mypilulier.dao.PrescriptionDao;
import com.pouillos.mypilulier.dao.PriseDao;
import com.pouillos.mypilulier.entities.AssociationFormeDose;
import com.pouillos.mypilulier.entities.Dose;
import com.pouillos.mypilulier.entities.Medicament;
import com.pouillos.mypilulier.entities.Prise;
import com.pouillos.mypilulier.interfaces.BasicUtils;

import org.greenrobot.greendao.database.Database;

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
    protected ImportMedicamentDao importMedicamentDao;
    protected MedicamentDao medicamentDao;
    protected MedicamentLightDao medicamentLightDao;
    protected PrescriptionDao prescriptionDao;
    protected PriseDao priseDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialiserDao();
        createNotificationChannel();
        associationFormeDoseDao = daoSession.getAssociationFormeDoseDao();
        doseDao = daoSession.getDoseDao();
        formePharmaceutiqueDao = daoSession.getFormePharmaceutiqueDao();
        importMedicamentDao = daoSession.getImportMedicamentDao();
        medicamentDao = daoSession.getMedicamentDao();
        medicamentLightDao = daoSession.getMedicamentLightDao();
        prescriptionDao = daoSession.getPrescriptionDao();
        priseDao = daoSession.getPriseDao();
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
                                ouvrirActiviteSuivante(NavDrawerActivity.this, AccueilActivity.class, true);
                                break;
                            case R.id.bottom_navigation_search_doctor:
                                ouvrirActiviteSuivante(NavDrawerActivity.this, AddPrescriptionActivity.class, true);
                                break;
                            case R.id.bottom_navigation_cancel_alarm:
                                //ouvrirActiviteSuivante(NavDrawerActivity.this, AddPrescriptionActivity.class, true);
                                cancelAlarmDialog();
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

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void initialiserDao() {
        //Base pendant dev
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "my_pilulier_db");
        //Base de prod
        //AppOpenHelper helper = new AppOpenHelper(this, "my_pilulier_db", null);
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
}
