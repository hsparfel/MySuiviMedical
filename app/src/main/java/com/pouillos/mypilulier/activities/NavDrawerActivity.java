package com.pouillos.mypilulier.activities;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.orm.SugarRecord;
import com.pouillos.mypilulier.R;
import com.pouillos.mypilulier.activities.tools.ImportMedicamentActivity;
import com.pouillos.mypilulier.activities.tools.PriseNotificationBroadcastReceiver;
import com.pouillos.mypilulier.activities.utils.DateUtils;
import com.pouillos.mypilulier.dao.AlarmRdvDao;
import com.pouillos.mypilulier.dao.AppOpenHelper;
import com.pouillos.mypilulier.dao.AssociationAlarmRdvDao;
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
import com.pouillos.mypilulier.dao.RappelDao;
import com.pouillos.mypilulier.entities.AlarmRdv;
import com.pouillos.mypilulier.entities.AssociationAlarmRdv;
import com.pouillos.mypilulier.entities.Prise;
import com.pouillos.mypilulier.interfaces.BasicUtils;

import org.greenrobot.greendao.database.Database;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import icepick.Icepick;
import icepick.State;

public class NavDrawerActivity extends AppCompatActivity implements BasicUtils {

    protected Toolbar toolbar;
    protected DrawerLayout drawerLayout;
    protected BottomNavigationView bottomNavigationView;

    protected DaoSession daoSession;

    protected AlarmRdvDao alarmRdvDao;
    protected AssociationAlarmRdvDao associationAlarmRdvDao;
    protected AssociationFormeDoseDao associationFormeDoseDao;
    protected DoseDao doseDao;
    protected FormePharmaceutiqueDao formePharmaceutiqueDao;
    protected ImportMedicamentDao importMedicamentDao;
    protected MedicamentDao medicamentDao;
    protected MedicamentLightDao medicamentLightDao;
    protected PrescriptionDao prescriptionDao;
    protected PriseDao priseDao;
    protected RappelDao rappelDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //a redefinit à chq fois
        super.onCreate(savedInstanceState);
        initialiserDao();

        alarmRdvDao = daoSession.getAlarmRdvDao();
        associationAlarmRdvDao = daoSession.getAssociationAlarmRdvDao();
        associationFormeDoseDao = daoSession.getAssociationFormeDoseDao();
        doseDao = daoSession.getDoseDao();
        formePharmaceutiqueDao = daoSession.getFormePharmaceutiqueDao();
        importMedicamentDao = daoSession.getImportMedicamentDao();
        medicamentDao = daoSession.getMedicamentDao();
        medicamentLightDao = daoSession.getMedicamentLightDao();
        prescriptionDao = daoSession.getPrescriptionDao();
        priseDao = daoSession.getPriseDao();
        rappelDao = daoSession.getRappelDao();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        //getMenuInflater().inflate(R.menu.menu_add_item_to_db, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myProfilActivity;
        //3 - Handle actions on menu items
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
                                //ouvrirActiviteSuivante(NavDrawerActivity.this, ChercherContactActivity.class, true);
                                break;
                            case R.id.bottom_navigation_search_etablissement:
                               // ouvrirActiviteSuivante(NavDrawerActivity.this, ChercherEtablissementActivity.class, true);
                                break;
                            case R.id.bottom_navigation_list_doctor:
                               // ouvrirActiviteSuivante(NavDrawerActivity.this, AfficherMesContactsActivity.class, true);
                                break;
                            case R.id.bottom_navigation_list_etablissement:
                               // ouvrirActiviteSuivante(NavDrawerActivity.this, AfficherMesEtablissementsActivity.class, true);
                                break;
                        }
                        return true;
                    }
                });
    }




    // ---------------------
    // CONFIGURATION
    // ---------------------

    // 1 - Configure Toolbar
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
    public void revenirActivitePrecedente(String sharedName, String dataName, Long itemId) {
        SharedPreferences preferences=getSharedPreferences(sharedName,MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putLong(dataName,itemId);
        editor.commit();
        finish();
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    protected Date ActualiserDate(Date date, String time){
        Date dateActualisee = date;
        int nbHour = Integer.parseInt(time.substring(0,2));
        int nbMinute = Integer.parseInt(time.substring(3));
        dateActualisee = DateUtils.ajouterHeure(dateActualisee,nbHour);
        dateActualisee = DateUtils.ajouterMinute(dateActualisee,nbMinute);
        return dateActualisee;
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





    protected boolean isChecked(ChipGroup chipGroup) {
        boolean bool;
        if (chipGroup.getCheckedChipId() != -1) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
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

    protected boolean isValidTel(TextView textView) {
        if (!TextUtils.isEmpty(textView.getText()) && textView.getText().length() <10) {
            //textView.requestFocus();
            //textView.setError("Saisie Non Valide  (10 chiffres)");
            return false;
        } else {
            return true;
        }
    }

    protected boolean isValidZip(TextView textView) {
        if (!TextUtils.isEmpty(textView.getText()) && textView.getText().length() <5) {
            //textView.requestFocus();
            //textView.setError("Saisie Non Valide  (5 chiffres)");
            return false;
        } else {
            return true;
        }
    }

    protected boolean isEmailAdress(String email){
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
        Matcher m = p.matcher(email.toUpperCase());
        return m.matches();
    }
    protected boolean isValidEmail(TextView textView) {
        if (!TextUtils.isEmpty(textView.getText()) && !isEmailAdress(textView.getText().toString())) {
            //textView.requestFocus();
            //textView.setError("Saisie Non Valide (email)");
            return false;
        } else {
            return true;
        }
    }

    protected static float floatArrondi(float number, int decimalPlace) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }


    protected<T> void activerNotification(T object, Context context) {
        if (object instanceof Prise) {
           // startAlert((Prise) object, context);
            startAlert((Prise) object,"test recurrence", context);
        }

    }
    /*protected<T> void activerNotification(Class classe, Date dateRdv, T object, Context context) {
        if (classe == RdvContactNotificationBroadcastReceiver.class) {
            startAlert(classe, DateUtils.ajouterHeure(dateRdv,-1), ((Contact) object).toStringShort(), Echeance.OneHourAfter.toString(),context);
            startAlert(classe, DateUtils.ajouterJourArrondi(dateRdv,-1,7), ((Contact) object).toStringShort(), Echeance.OneDayAfter.toString(),context);
        } else if (classe == RdvAnalyseNotificationBroadcastReceiver.class) {
            startAlert(classe, DateUtils.ajouterHeure(dateRdv,-1), ((Analyse) object).toString(), Echeance.OneHourAfter.toString(),context);
            startAlert(classe, DateUtils.ajouterJourArrondi(dateRdv,-1,7), ((Analyse) object).toString(), Echeance.OneDayAfter.toString(),context);
        } else if (classe == RdvExamenNotificationBroadcastReceiver.class) {
            startAlert(classe, DateUtils.ajouterHeure(dateRdv,-1), ((Examen) object).toString(), Echeance.OneHourAfter.toString(),context);
            startAlert(classe, DateUtils.ajouterJourArrondi(dateRdv,-1,7), ((Examen) object).toString(), Echeance.OneDayAfter.toString(),context);
        }

    }*/

    protected<T> void supprimerNotification(T object, Context context) {
        /*if (object instanceof RdvContact) {
            List<AssociationAlarmRdv> listAssociationAlarmRdv = AssociationAlarmRdv.find(AssociationAlarmRdv.class,"rdv_contact = ?", ((RdvContact) object).getId().toString());
            AlarmRdv alarmRdv = new AlarmRdv();
            for (AssociationAlarmRdv current : listAssociationAlarmRdv) {
                alarmRdv = AlarmRdv.findById(AlarmRdv.class,current.getAlarmRdv().getId());
                if (alarmRdv.getEcheance().equalsIgnoreCase(Echeance.OneHourAfter.toString())) {
                    cancelAlert((RdvContact) object, Echeance.OneHourAfter.toString(),context, alarmRdv.getRequestCode());
                } else if (alarmRdv.getEcheance().equalsIgnoreCase(Echeance.OneDayAfter.toString())) {
                    cancelAlert((RdvContact) object, Echeance.OneDayAfter.toString(),context, alarmRdv.getRequestCode());
                }
            }
        } else if (object instanceof RdvAnalyse) {
            List<AssociationAlarmRdv> listAssociationAlarmRdv = AssociationAlarmRdv.find(AssociationAlarmRdv.class,"rdv_analyse = ?", ((RdvAnalyse) object).getId().toString());
            AlarmRdv alarmRdv = new AlarmRdv();
            for (AssociationAlarmRdv current : listAssociationAlarmRdv) {
                alarmRdv = AlarmRdv.findById(AlarmRdv.class,current.getAlarmRdv().getId());
                if (alarmRdv.getEcheance().equalsIgnoreCase(Echeance.OneHourAfter.toString())) {
                    cancelAlert((RdvAnalyse) object, Echeance.OneHourAfter.toString(),context, alarmRdv.getRequestCode());
                } else if (alarmRdv.getEcheance().equalsIgnoreCase(Echeance.OneDayAfter.toString())) {
                    cancelAlert((RdvAnalyse) object, Echeance.OneDayAfter.toString(),context, alarmRdv.getRequestCode());
                }
            }
        } else if (object instanceof RdvExamen) {
            List<AssociationAlarmRdv> listAssociationAlarmRdv = AssociationAlarmRdv.find(AssociationAlarmRdv.class,"rdv_examen = ?", ((RdvExamen) object).getId().toString());
            AlarmRdv alarmRdv = new AlarmRdv();
            for (AssociationAlarmRdv current : listAssociationAlarmRdv) {
                alarmRdv = AlarmRdv.findById(AlarmRdv.class,current.getAlarmRdv().getId());
                if (alarmRdv.getEcheance().equalsIgnoreCase(Echeance.OneHourAfter.toString())) {
                    cancelAlert((RdvExamen) object, Echeance.OneHourAfter.toString(),context, alarmRdv.getRequestCode());
                } else if (alarmRdv.getEcheance().equalsIgnoreCase(Echeance.OneDayAfter.toString())) {
                    cancelAlert((RdvExamen) object, Echeance.OneDayAfter.toString(),context, alarmRdv.getRequestCode());
                }
            }
        }*/
    }



    protected<T> void startAlert(T object, Context context) {
        //Class classe = object.getClass();
        Intent intent = null;

        Prise prise = (Prise) object;

            intent = new Intent(this, PriseNotificationBroadcastReceiver.class);
            intent.putExtra("detail",prise.getMedicament().getDenominationShort()+ " - "+prise.getQteDose()+ " "+prise.getDose().getName());

        //intent.putExtra("echeance",echeance);
        Date dateJour = new Date();
        Long dateJourLong = dateJour.getTime();
        int requestCode =dateJourLong.intValue();
        Log.i("requestCode",""+requestCode);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        Date dateAlerte = prise.getDate();



        alarmManager.set(AlarmManager.RTC_WAKEUP, dateAlerte.getTime(), pendingIntent);
        AlarmRdv alarmRdv = new AlarmRdv();
        alarmRdv.setClasse(object.getClass().getName());
        alarmRdv.setDate(dateAlerte);
        alarmRdv.setDateString(dateAlerte.toString());
        alarmRdv.setDetail(intent.getStringExtra("detail"));
        //alarmRdv.setEcheance(echeance);
        alarmRdv.setRequestCode(requestCode);
       // alarmRdv.setId(alarmRdv.save());
        alarmRdv.setId(alarmRdvDao.insert(alarmRdv));


        Toast.makeText(this, "Alarm set : " + prise.getDate().toString(), Toast.LENGTH_LONG).show();
    }

    protected<T> void startAlert(T object, String echeance, Context context) {
        //Class classe = object.getClass();
        Intent intent = null;

            //Rdv rdv = (Rdv) object;
        if (object instanceof Prise) {
               // Prise prise = (Prise) object;
                intent = new Intent(this, PriseNotificationBroadcastReceiver.class);
                intent.putExtra("detail",((Prise) object).getMedicament().toString());
            }
            intent.putExtra("echeance",echeance);
            Date dateJour = new Date();
            Long dateJourLong = dateJour.getTime();
            int requestCode =dateJourLong.intValue();
            Log.i("requestCode",""+requestCode);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            Date dateAlerte = new Date();
            if (echeance.equalsIgnoreCase("test recurrence")){
                Prise prise = (Prise) object;
                dateAlerte = prise.getDate();
            }


            alarmManager.set(AlarmManager.RTC_WAKEUP, dateAlerte.getTime(), pendingIntent);
            AlarmRdv alarmRdv = new AlarmRdv();
            alarmRdv.setClasse(object.getClass().getName());
            alarmRdv.setDate(dateAlerte);
            alarmRdv.setDateString(dateAlerte.toString());
            alarmRdv.setDetail(intent.getStringExtra("detail"));
            alarmRdv.setEcheance(echeance);
            alarmRdv.setRequestCode(requestCode);
            alarmRdv.setId(alarmRdvDao.insert(alarmRdv));

            AssociationAlarmRdv associationAlarmRdv = new AssociationAlarmRdv();
            associationAlarmRdv.setAlarmRdv(alarmRdv);


            associationAlarmRdv.setId(associationAlarmRdvDao.insert(associationAlarmRdv));
            //Toast.makeText(this, "Alarm set : " + rdv.getDate().toString(), Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Alarm set : OK", Toast.LENGTH_LONG).show();

    }







    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void initialiserDao() {
        AppOpenHelper helper = new AppOpenHelper(this, "my_pilulier_db", null);
        Database db = helper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
}
