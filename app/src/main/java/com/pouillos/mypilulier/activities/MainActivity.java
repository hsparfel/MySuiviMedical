package com.pouillos.mypilulier.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.facebook.stetho.Stetho;
import com.google.android.material.navigation.NavigationView;
import com.orm.SugarRecord;
import com.pouillos.mypilulier.R;
import com.pouillos.mypilulier.entities.AssociationFormeDose;
import com.pouillos.mypilulier.entities.Dose;
import com.pouillos.mypilulier.entities.FormePharmaceutique;
import com.pouillos.mypilulier.entities.Medicament;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

///import com.pouillos.monpilulier.activities.listallx.ListAllProfilActivity;
//import com.pouillos.monpilulier.activities.newx.NewUserActivity;

public class MainActivity extends NavDrawerActivity {

    private NotificationCompat.Builder notBuilder;
    private static final int MY_NOTIFICATION_ID = 12345;
    private static final int MY_REQUEST_CODE = 100;
    private ProgressBar progressBar;
    private TextView textUser;
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);


        Button buttonRAZ = (Button) findViewById(R.id.buttonRAZ);
     //   Button buttonCreerNotification = (Button) findViewById(R.id.buttonCreerNotification);
        Button buttonNewMedicamentOfficiel = (Button) findViewById(R.id.buttonMajMedicamentOfficiel);
        Button buttonNewMedecinOfficiel = (Button) findViewById(R.id.buttonMajMedecinOfficiel);
        Button buttonInfoDb = (Button) findViewById(R.id.buttonInfoDb);
        Button buttonAccueil = (Button) findViewById(R.id.buttonAccueil);
     //   Button buttonAlarm = (Button) findViewById(R.id.buttonAddAlarm);

        progressBar = (ProgressBar) findViewById(R.id.my_progressBar);


        progressBar.setVisibility(View.VISIBLE);

        AsyncTaskRunnerBD runnerBD = new AsyncTaskRunnerBD();
        runnerBD.execute();

        //notification
        //creation du channel
       /* createNotificationChannel();

        this.notBuilder = new NotificationCompat.Builder(this, "notifTest");
        // The message will automatically be canceled when the user clicks on Panel
        this.notBuilder.setAutoCancel(true);*/

        /*buttonCreerNotification.setOnClickListener(v -> {
            startNotification();
        });*/

    /*    buttonAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startAlert(DateUtils.ajouterSeconde(new Date(),15));
               // startAlert(DateUtils.ajouterSeconde(new Date(),30));
                Long requestCode = new Date().getTime();
                startAlert(DateUtils.ajouterSeconde(new Date(),5),requestCode.intValue());
                requestCode = new Date().getTime();
                startAlert(DateUtils.ajouterSeconde(new Date(),10),requestCode.intValue());
                requestCode = new Date().getTime();
                startAlert(DateUtils.ajouterSeconde(new Date(),15),requestCode.intValue());
            }
        }); */

        /*buttonNewMedicamentOfficiel.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            //remplirMedicamentOfficielBD();
            progressBar.setProgress(0);
            AsyncTaskRunnerMedicament runnerMedicament = new AsyncTaskRunnerMedicament();
            runnerMedicament.execute();
        });*/

        buttonNewMedecinOfficiel.setOnClickListener(v -> {
            /*//remplirMedecinOfficielBD();
            progressBar.setVisibility(View.VISIBLE);
            //remplirMedicamentOfficielBD();
            progressBar.setProgress(0);
            AsyncTaskRunnerContact runner = new AsyncTaskRunnerContact(this);
            runner.execute();*/
            Toast.makeText(MainActivity.this, "desactive", Toast.LENGTH_LONG).show();
        });

        buttonAccueil.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AccueilActivity.class);
            startActivity(intent);
            finish();
        });

        buttonInfoDb.setOnClickListener(v -> {
            //long size1 = Medicament.count(Medicament.class);
            long size1 = medicamentDao.count();

                    Toast toast = Toast.makeText(MainActivity.this, "nb medicament: "+size1+"/13023\nnb medecin: ", Toast.LENGTH_LONG);
            toast.show();
        });

        buttonRAZ.setOnClickListener(v -> {
            //mettre en commentaire pr eviter suppression involontaire FAIRE ATTENTION
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
            AsyncTaskRunnerRAZ runnerRAZ = new AsyncTaskRunnerRAZ();
            runnerRAZ.execute();
        });

    }

    /*public void startNotification() {
        // --------------------------
        // Prepare a notification
        // --------------------------

        this.notBuilder.setSmallIcon(R.mipmap.ic_launcher);
        this.notBuilder.setTicker("This is a ticker");
        this.notBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Set the time that the event occurred.
        // Notifications in the panel are sorted by this time.

        //Date dateJour = new Date();
        Date dateNotif = new Date();
        Long  longNotif = dateNotif.getTime();

        //  this.notBuilder.setWhen(new DateUtils().ajouterHeure(dateJour, 1).getTime() + 10*1000);

        // this.notBuilder.setWhen(System.currentTimeMillis()+ 300* 1000);
        this.notBuilder.setWhen(longNotif);

        //this.notBuilder.setShowWhen(false);
        this.notBuilder.setShowWhen(true);
        this.notBuilder.setContentTitle("This is title");
        String messageNotif = "";
        messageNotif += "prog: "+dateNotif.toString();

        this.notBuilder.setContentText(messageNotif);
        //this.notBuilder.setContentText("This is content text ....");

        // Create Intent
        Intent intent = new Intent(this, MainActivity.class);

        // PendingIntent.getActivity(..) will start an Activity, and returns PendingIntent object.
        // It is equivalent to calling Context.startActivity(Intent).
        PendingIntent pendingIntent = PendingIntent.getActivity(this, MY_REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        this.notBuilder.setContentIntent(pendingIntent);

        // Get a notification service (A service available on the system).
        NotificationManager notificationService  =
                (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);

        // Builds notification and issue it

        Notification notification =  notBuilder.build();
        notificationService.notify(MY_NOTIFICATION_ID, notification);
    }*/

   // public void startAlert(Date date) {
    /*public void startAlert(Date date,int requestCode) {
        //EditText text = findViewById(R.id.time);
        //int i = Integer.parseInt(text.getText().toString());
        //int i = 10;
        //Intent intent = new Intent(this, MyBroadcastReceiver.class);
        Intent intent = new Intent(this, MyNotificationBroadcastReceiver.class);

        intent.putExtra("testA","nom du contact");
        intent.putExtra("testB","dans une heure");

        //intent.putExtra("activity",MainActivity.class);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, intent, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), requestCode, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);


        AlarmRdv alarmRdv = new AlarmRdv();
        alarmRdv.setClasse(MainActivity.class.getName());
        alarmRdv.setDate(date);
        alarmRdv.setDateString(date.toString());
        alarmRdv.setDetail("detail");
        alarmRdv.setEcheance("echeance");
        alarmRdv.setRequestCode(requestCode);
        alarmRdv.save();

        Toast.makeText(this, "Alarm set : " + date.toString(), Toast.LENGTH_LONG).show();
        //startNotification();
    }*/

    private class AsyncTaskRunnerRAZ extends AsyncTask<Void, Integer, Void> {

        protected Void doInBackground(Void...voids) {
            publishProgress(0);


            //RAZ A LA DEMANDE
            //SugarRecord.executeQuery("DROP TABLE PATIENT_MEDECIN");
            //SugarRecord.executeQuery("DELETE FROM RDV");
            //SugarRecord.executeQuery("DELETE FROM ASSOCIATION");
            //SugarRecord.executeQuery("DELETE FROM UTILISATEUR");
            //SugarRecord.executeQuery("DELETE FROM PROFIL");
            //SugarRecord.executeQuery("DELETE FROM DEPARTEMENT");
            //SugarRecord.executeQuery("DELETE FROM CONTACT");
            //SugarRecord.executeQuery("DROP TABLE CONTACT");
            //SugarRecord.executeQuery("DELETE FROM REGION");
            //SugarRecord.executeQuery("DROP TABLE MEDECIN");
            //SugarRecord.executeQuery("DROP TABLE MEDECIN_OFFICIEL");
            //SugarRecord.executeQuery("DROP TABLE MEDICAMENT");
            //SugarRecord.executeQuery("DROP TABLE MEDICAMENT_OFFICIEL");
           // SugarRecord.executeQuery("DROP TABLE ASSOCIATION");
            //SugarRecord.executeQuery("DROP TABLE ASSOCIATION_OFFICIELLE");
            //SugarRecord.executeQuery("DROP TABLE CABINET");
            //SugarRecord.executeQuery("DROP TABLE ORDONNANCE");
            //SugarRecord.executeQuery("DROP TABLE ORDO_PRESCRIPTION");
            //SugarRecord.executeQuery("DROP TABLE ORDO_ANALYSE");
            //SugarRecord.executeQuery("DROP TABLE ORDO_EXAMEN");
            //SugarRecord.executeQuery("DROP TABLE RDV");
            //SugarRecord.executeQuery("DROP TABLE RDV_AUTRE");
            //SugarRecord.executeQuery("DROP TABLE RDV_OFFICIEL");
           // SugarRecord.executeQuery("DROP TABLE SPECIALITE");

           // SugarRecord.executeQuery("DELETE FROM ALARM");
            //SugarRecord.executeQuery("DROP TABLE ALARM");
            //SugarRecord.executeQuery("DELETE FROM ALARM_RDV");
            //SugarRecord.executeQuery("DELETE FROM ASSOCIATION_ALARM_RDV");
           // SugarRecord.executeQuery("DELETE FROM RDV_ANALYSE");
            //SugarRecord.executeQuery("DELETE FROM RDV_EXAMEN");
            //SugarRecord.executeQuery("DELETE FROM RDV_CONTACT");
            //SugarRecord.executeQuery("DELETE FROM PHOTO");
            //SugarRecord.executeQuery("DELETE FROM ORDONNANCE");
            //SugarRecord.executeQuery("DELETE FROM PRESCRIPTION");
            //SugarRecord.executeQuery("DELETE FROM RAPPEL");
           // SugarRecord.executeQuery("DELETE FROM IMPORT_CONTACT");
          // SugarRecord.executeQuery("DELETE FROM CONTACT");
            //SugarRecord.executeQuery("DROP TABLE IMPORT_CONTACT");
          //  SugarRecord.executeQuery("DROP TABLE CONTACT");
            //
           // SugarRecord.executeQuery("DROP TABLE RDV_ACTE_MEDICAL");
            //SugarRecord.executeQuery("DROP TABLE DUREE");
            //SugarRecord.executeQuery("DELETE FROM ALARM_RDV");
         //   SugarRecord.executeQuery("DELETE FROM ASSOCIATION_ALARM_RDV");
        //    SugarRecord.executeQuery("DELETE FROM ASSOCIATION_UTILISATEUR_CONTACT");
         //   SugarRecord.executeQuery("DELETE FROM ASSOCIATION_UTILISATEUR_ETABLISSEMENT");
         //   SugarRecord.executeQuery("DELETE FROM ORDONNANCE");
         //   SugarRecord.executeQuery("DELETE FROM PRESCRIPTION");
        //    SugarRecord.executeQuery("DELETE FROM PRISE");
         //   SugarRecord.executeQuery("DELETE FROM PROFIL");
         //   SugarRecord.executeQuery("DELETE FROM RAPPEL");
         //   SugarRecord.executeQuery("DELETE FROM RDV_ANALYSE");
         //   SugarRecord.executeQuery("DELETE FROM RDV_CONTACT");
         //   SugarRecord.executeQuery("DELETE FROM RDV_EXAMEN");
        //    SugarRecord.executeQuery("DELETE FROM UTILISATEUR");
           /* SugarRecord.executeQuery("DELETE FROM PRISE");
            SugarRecord.executeQuery("DELETE FROM ALARM_RDV");
            SugarRecord.executeQuery("DELETE FROM ORDONNANCE");
            SugarRecord.executeQuery("DELETE FROM PRESCRIPTION");*/

            //RAZ TOTAL
          /*  SugarContext.terminate();
            publishProgress(20);
            SchemaGenerator schemaGenerator = new SchemaGenerator(getApplicationContext());
            publishProgress(40);
            schemaGenerator.deleteTables(new SugarDb(getApplicationContext()).getDB());
            publishProgress(60);
            SugarContext.init(getApplicationContext());
            publishProgress(80);
            schemaGenerator.createDatabase(new SugarDb(getApplicationContext()).getDB());
*/

            publishProgress(100);

            finish();
            return null;
        }

        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "RAZ fini", Toast.LENGTH_LONG).show();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }

    private class AsyncTaskRunnerBD extends AsyncTask<Void, Integer, String> {

        protected String doInBackground(Void...voids) {
                    //remplir BD avec valeur par defaut



            publishProgress(10);
            remplirDefaultBD();
            publishProgress(20);

            publishProgress(30);

            publishProgress(40);

            publishProgress(50);

            publishProgress(70);
                  // pas de creation de user  remplirExempleBD();
            publishProgress(90);

                    //afficher le user actif
                    //Utilisateur utilisateur = (new Utilisateur()).findActifUser();

            publishProgress(100);
            return "userName";
        }

        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
          //  textUser.setText(result);
            Toast.makeText(MainActivity.this, "Creation BD fini", Toast.LENGTH_LONG).show();

        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }




    /*private class AsyncTaskRunnerMedicament extends AsyncTask<Void, Integer, Void> {

        protected Void doInBackground(Void...voids) {

                InputStream is = null;
                BufferedReader reader = null;

                try {
                    is = getAssets().open("CIS_bdpm.txt");
                    reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
                    String line = null;




                    while ((line = reader.readLine()) != null) {

                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException ignored) {
                        }
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException ignored) {
                        }
                    }
                }



            publishProgress(100);
            return null;
        }

        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            Toast toast = Toast.makeText(MainActivity.this, "Import fini", Toast.LENGTH_LONG);
            toast.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }*/

    private class AsyncTaskRunnerContact extends AsyncTask<Void, Integer, Void> {

        private Context context;
        public AsyncTaskRunnerContact(Context context) {
            this.context=context;
        }

        protected Void doInBackground(Void...voids) {

            InputStream is = null;
            BufferedReader reader = null;

            return null;
        }

        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "IMPORT TOTAL FINI", Toast.LENGTH_LONG).show();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }


    public void remplirDefaultBD() {

    }

    public void remplirExempleBD() {


    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //CharSequence name = getString(R.string.channel_name);
            CharSequence name = "channelName";
            //String description = getString(R.string.channel_description);
            String description = "channelDescription";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifTest", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
