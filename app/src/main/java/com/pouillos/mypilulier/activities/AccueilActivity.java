package com.pouillos.mypilulier.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.stetho.Stetho;
import com.google.android.material.button.MaterialButton;
import com.pouillos.mypilulier.R;

import com.pouillos.mypilulier.activities.add.AddPrescriptionActivity;
import com.pouillos.mypilulier.activities.tools.ImportMedicamentActivity;
import com.pouillos.mypilulier.activities.utils.DateUtils;
import com.pouillos.mypilulier.entities.AssociationFormeDose;
import com.pouillos.mypilulier.entities.Dose;
import com.pouillos.mypilulier.entities.ImportMedicament;
import com.pouillos.mypilulier.entities.FormePharmaceutique;
import com.pouillos.mypilulier.entities.Medicament;
import com.pouillos.mypilulier.entities.MedicamentLight;
import com.pouillos.mypilulier.entities.Prescription;
import com.pouillos.mypilulier.interfaces.BasicUtils;
import com.pouillos.mypilulier.recycler.adapter.RecyclerAdapterPrescription;
import com.pouillos.mypilulier.utils.ItemClickSupport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;

public class AccueilActivity extends NavDrawerActivity implements BasicUtils, RecyclerAdapterPrescription.Listener {

    @BindView(R.id.my_progressBar)
    ProgressBar progressBar;

    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.text_nb_medicament)
    TextView text_nb_medicament;

    @BindView(R.id.list_prescription)
    RecyclerView listPrescription;
    @BindView(R.id.button_list_prescription)
    MaterialButton buttonListPrescription;
    private boolean prescriptionDisplay = true;

    private List<Prescription> listePrescription = new ArrayList<>();

    RecyclerAdapterPrescription adapterPrescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Stetho.initializeWithDefaults(this);

        // 6 - Configure all views
        this.configureToolBar();
        this.configureBottomView();

        ButterKnife.bind(this);

        progressBar.setVisibility(View.VISIBLE);

        textView.setText(DateUtils.ecrireDateLettre(new Date()));


       AccueilActivity.AsyncTaskRunnerBD runnerBD = new AccueilActivity.AsyncTaskRunnerBD();
       runnerBD.execute();

    }


    public void addPrescription(View view) {
        ouvrirActiviteSuivante(this, AddPrescriptionActivity.class,false);
    }

    public void razDb(View view) {
       /* ImportMedicament current = importMedicamentDao.load(0l);
        current.setImportCompleted(false);
        importMedicamentDao.update(current);
        importMedicamentDao.deleteAll();
       // formePharmaceutiqueDao.deleteAll();
        medicamentDao.deleteAll();*/
     //   associationFormeDoseDao.deleteAll();

    }

    public void importMedicament(View view) {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);
        AsyncTaskRunnerMedicament runner = new AsyncTaskRunnerMedicament(this);
        runner.execute();
    }

    private class AsyncTaskRunnerMedicament extends AsyncTask<Void, Integer, Void> {

        private Context context;
        public AsyncTaskRunnerMedicament(Context context) {
            this.context=context;
        }

        protected Void doInBackground(Void...voids) {

            InputStream is = null;
            BufferedReader reader = null;

            //List<Medicament> listMedicament = Medicament.listAll(Medicament.class);
            List<Medicament> listMedicament = medicamentDao.loadAll();
            Map<Long, Medicament> mapMedicamentOfficiel = new HashMap<>();
            for (Medicament medicament : listMedicament) {
                mapMedicamentOfficiel.put(medicament.getCodeCIS(), medicament);
            }

            //List<FormePharmaceutique> listFormePharamaceutique = FormePharmaceutique.listAll(FormePharmaceutique.class);
            List<FormePharmaceutique> listFormePharamaceutique = formePharmaceutiqueDao.loadAll();
            Map<String, FormePharmaceutique> mapFormePharamaceutique = new HashMap<>();
            for (FormePharmaceutique formePharmaceutique : listFormePharamaceutique) {
                mapFormePharamaceutique.put(formePharmaceutique.getName(), formePharmaceutique);
            }

            //List<ImportMedicament> listImportMedicament = ImportMedicament.find(ImportMedicament.class,"import_completed = ?","0");
            List<ImportMedicament> listImportMedicament = importMedicamentDao.queryRaw("where import_completed = ?","0");

            //List<ImportMedicament> listImportMedicament = ImportMedicament.listAll(ImportMedicament.class);
//TODO A REVOIR
            int nbImportEffectue =0;
            int nbImportIgnore = 0;
            int readerCount=0;
            int nbLigneLue=0;
            //ImportMedicament current = listImportMedicament.get(0);
            for (ImportMedicament current : listImportMedicament) {
                nbImportEffectue =0;
                nbImportIgnore = 0;
                //readerCount=0;
                nbLigneLue=0;
                if (current.getDateDebut() == null) {
                    current.setDateDebut(DateUtils.ecrireDateHeure(new Date()));
                }
                if (current.getNbLigneLue() != 0) {
                    nbLigneLue = current.getNbLigneLue();
                }
                if (current.getNbImportEffectue() != 0) {
                    nbImportEffectue = current.getNbImportEffectue();
                }
                if (current.getNbImportIgnore() != 0) {
                    nbImportIgnore = current.getNbImportIgnore();
                }

                //current.setDateFin("");
                //current.setNbImportEffectue(nbImportEffectue);
                ///current.setNbImportIgnore(nbImportIgnore);
                // nbImportEffectue = 0;
                // nbImportIgnore = 0;

                try {
                    is = getAssets().open(current.getPath());
                    reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
                    String line = null;

                    int readerSize = 15400;
                    readerCount = 0;
                    int compteur = 0;
                    publishProgress(compteur);



                    while ((line = reader.readLine()) != null) {
                        //todo revoir cette condition

                        if (readerCount<nbLigneLue) {
                            readerCount ++;
                            continue;
                        }
                        readerCount ++;
                        compteur = readerCount*100/readerSize;
                        //compteur ++;
                        publishProgress(compteur);


                        final String SEPARATEUR = "£";
                        String lineSplitted[] = line.split(SEPARATEUR);

                        Medicament medicament = new Medicament();
                        //verif si commercialise
                        if (!lineSplitted[6].equals("Commercialisée") ) {
                            nbImportIgnore++;
                            current.setNbImportIgnore(nbImportIgnore);
                            //current.save();
                            importMedicamentDao.update(current);
                            continue;
                        }

                        //verif si existant
                        Medicament verifMedicament = null;
                        verifMedicament = mapMedicamentOfficiel.get(Long.parseLong(lineSplitted[0]));
                        if (verifMedicament != null){
                            nbImportIgnore++;
                            current.setNbImportIgnore(nbImportIgnore);
                            //current.save();
                            importMedicamentDao.update(current);
                            continue;
                        }

                        medicament.setCodeCIS(Long.parseLong(lineSplitted[0]));

                        int positionVirgule = lineSplitted[1].indexOf(",");
                        if (positionVirgule >=0){
                            medicament.setDenomination(lineSplitted[1].substring(0,positionVirgule));
                        } else {
                            medicament.setDenomination(lineSplitted[1]);
                        }
                        if (lineSplitted[2].substring(0,1).equals(" ")) {
                            lineSplitted[2] = lineSplitted[2].substring(1,lineSplitted[2].length());
                        }

                        medicament.setFormePharmaceutique(mapFormePharamaceutique.get(lineSplitted[2]));

                        int positionPointVirgule = lineSplitted[10].indexOf(";");
                        if (positionPointVirgule >=0){
                            medicament.setTitulaire(lineSplitted[10].substring(0,positionPointVirgule));
                        } else {
                            medicament.setTitulaire(lineSplitted[10]);
                        }
                        //medicament.save();
                        medicament.setId(medicamentDao.insert((medicament)));
                        medicamentLightDao.insert(new MedicamentLight(medicament.getId(),medicament.getCodeCIS(),medicament.getDenomination()));

                        nbLigneLue++;
                        nbImportEffectue++;
                        current.setNbLigneLue(nbLigneLue);
                        current.setNbImportEffectue(nbImportEffectue);
                        //current.save();
                        importMedicamentDao.update(current);
                    }
                } catch (final Exception e) {
                    nbImportIgnore++;
                    current.setNbImportIgnore(nbImportIgnore);
                    //current.save();
                    importMedicamentDao.update(current);
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
                current.setDateFin(DateUtils.ecrireDateHeure(new Date()));
                current.setNbImportIgnore(nbImportIgnore);
                current.setNbImportEffectue(nbImportEffectue);
                current.setImportCompleted(true);
                //current.save();
                importMedicamentDao.update(current);

                publishProgress(100);
                //a voir si ça passe
                //Toast.makeText(MainActivity.this, "Import de " + current.getPath() + " fini", Toast.LENGTH_LONG).show();
            }

            return null;
        }

        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            Long nb = medicamentDao.count();
            int total = 13023;
            Long pourcentage = nb*100/total;
            text_nb_medicament.setText(" nb de medicaments importes:" + nb.toString()+"/"+total+" ("+pourcentage+"%)");
            Toast.makeText(AccueilActivity.this, "IMPORT TOTAL FINI", Toast.LENGTH_LONG).show();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }


    public void remplirImportMedicamentBD() {
        Long count = importMedicamentDao.count();
        if (count == 0) {
            importMedicamentDao.insert(new ImportMedicament(0l,"CIS_bdpm.txt", false,"","",0,0,0));
        }
    }


    @Override
    public void onClickDeleteButton(int position) {
        Toast.makeText(AccueilActivity.this, "click sur prescription", Toast.LENGTH_LONG).show();
        Prescription prescription = listePrescription.get(position);
        //creer la classe
        //ouvrirActiviteSuivante(this, AfficherPrescriptionActivity.class,"rdvContactId",prescription.getId(),false);
    }

    @OnClick(R.id.button_list_prescription)
    public void buttonListPrescriptionClick() {
        if (!prescriptionDisplay) {
            listPrescription.setVisibility(View.VISIBLE);
        } else {
            listPrescription.setVisibility(View.GONE);
        }
        prescriptionDisplay = !prescriptionDisplay;
    }




    private class AsyncTaskRunnerBD extends AsyncTask<Void, Integer, Void> {

        protected Void doInBackground(Void...voids) {
            publishProgress(0);

            //remplir BD avec valeur par defaut
            remplirFormePharmaceutiqueBD();
            publishProgress(10);

            remplirDefaultBD();
            publishProgress(20);



            publishProgress(65);
            remplirImportMedicamentBD();
            publishProgress(70);

            publishProgress(92);
            publishProgress(100);
            return null;
        }

        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(AccueilActivity.this, R.string.text_DB_created, Toast.LENGTH_LONG).show();

            configureRecyclerView();
            configureOnClickRecyclerView();
            if (listePrescription.size() == 0) {
                buttonListPrescription.setVisibility(View.GONE);
            }

        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }
    private void ActualiserListPrescription() {
        /*List<Ordonnance> listAllOrdonnance = Ordonnance.find(Ordonnance.class,"utilisateur = ? and validated = 1",activeUser.getId().toString());
        for (Ordonnance currentOrdonnance : listAllOrdonnance) {
            List<Prescription> listAllPrescription = Prescription.find(Prescription.class,"ordonnance = ?",currentOrdonnance.getId().toString());
            for (Prescription currentPrescription : listAllPrescription) {
                if (currentPrescription.getDateFin().after(DateUtils.ajouterJour(new Date(),1))) {
                    listePrescription.add(currentPrescription);
                }
            }
        }

        Collections.sort(listePrescription);*/
    }



    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(listPrescription, R.layout.recycler_list_prescription)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Log.e("TAG", "Position : "+position);
                    }
                });


            }

    public void configureRecyclerView() {
        adapterPrescription = new RecyclerAdapterPrescription(listePrescription, this);
        adapterPrescription.displayButtons(false);
        listPrescription.setAdapter(adapterPrescription);
        listPrescription.setLayoutManager(new LinearLayoutManager(this));
    }

    public void remplirFormePharmaceutiqueBD() {
        Long count = formePharmaceutiqueDao.count();
        if (count ==0) {
            formePharmaceutiqueDao.insert(new FormePharmaceutique(0l,"bain de bouche"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(1l,"bâton pour application"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(2l,"bâton pour usage dentaire"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(3l,"bâton pour usage urétral"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(4l,"capsule"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(5l,"capsule molle"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(6l,"capsule molle ou"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(7l,"capsule pour inhalation par vapeur"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(8l,"cartouche pour inhalation"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(9l,"collutoire"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(10l,"collyre"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(11l,"collyre à libération prolongée"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(12l,"collyre en émulsion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(13l,"collyre en solution"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(14l,"collyre en suspension"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(15l,"collyre pour solution"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(16l,"compresse et solution(s) et générateur radiopharmaceutique"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(17l,"compresse imprégné(e)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(18l,"compresse imprégné(e) pour usage dentaire"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(19l,"comprimé"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(20l,"comprimé à croquer"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(21l,"comprimé à croquer à sucer ou dispersible"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(22l,"comprimé à croquer ou à sucer"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(23l,"comprimé à croquer ou dispersible"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(24l,"comprimé à libération modifiée"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(25l,"comprimé à libération modifiée sécable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(26l,"comprimé à libération prolongée"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(27l,"comprimé à mâcher"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(28l,"comprimé à sucer"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(29l,"comprimé à sucer ou à croquer"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(30l,"comprimé à sucer sécable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(31l,"comprimé dispersible"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(32l,"comprimé dispersible et orodispersible"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(33l,"comprimé dispersible orodispersible"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(34l,"comprimé dispersible ou à croquer"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(35l,"comprimé dispersible sécable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(36l,"comprimé dragéifié"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(37l,"comprimé effervescent(e)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(38l,"comprimé effervescent(e) sécable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(39l,"comprimé enrobé"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(40l,"comprimé enrobé à croquer"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(41l,"comprimé enrobé à libération prolongée"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(42l,"comprimé enrobé et comprimé enrobé"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(43l,"comprimé enrobé et comprimé enrobé enrobé"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(44l,"comprimé enrobé et comprimé enrobé et comprimé enrobé"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(45l,"comprimé enrobé et comprimé enrobé et comprimé enrobé enrobé"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(46l,"comprimé enrobé gastro-résistant(e)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(47l,"comprimé enrobé sécable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(48l,"comprimé et comprimé"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(49l,"comprimé et comprimé et comprimé"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(50l,"comprimé et comprimé pelliculé"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(51l,"comprimé et gélule"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(52l,"comprimé et solution(s) et granules et poudre"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(53l,"comprimé et solution(s) et granules et poudre et pommade"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(54l,"comprimé gastro-résistant(e)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(55l,"comprimé muco-adhésif"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(56l,"comprimé orodispersible"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(57l,"comprimé orodispersible sécable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(58l,"comprimé osmotique"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(59l,"comprimé osmotique pelliculé à libération prolongée"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(60l,"comprimé pelliculé"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(61l,"comprimé pelliculé à libération modifiée"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(62l,"comprimé pelliculé à libération prolongée"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(63l,"comprimé pelliculé dispersible"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(64l,"comprimé pelliculé et comprimé pelliculé"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(65l,"comprimé pelliculé et comprimé pelliculé et comprimé pelliculé"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(66l,"comprimé pelliculé et comprimé pelliculé pelliculé"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(67l,"comprimé pelliculé et granulés effervescent(e)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(68l,"comprimé pelliculé gastro-résistant(e)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(69l,"comprimé pelliculé quadrisécable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(70l,"comprimé pelliculé sécable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(71l,"comprimé pelliculé sécable à libération prolongée"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(72l,"comprimé pour solution buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(73l,"comprimé pour suspension buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(74l,"comprimé quadrisécable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(75l,"comprimé sécable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(76l,"comprimé sécable à libération modifiée"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(77l,"comprimé sécable à libération prolongée"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(78l,"comprimé sécable pelliculé"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(79l,"comprimé sécable pour suspension buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(80l,"comprimé(s) pelliculé"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(81l,"cône pour usage dentaire"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(82l,"crème"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(83l,"crème épaisse pour application"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(84l,"crème et solution et granules et poudre"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(85l,"crème et solution et granules et poudre et pommade"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(86l,"crème et solution et granules et poudre unidose"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(87l,"crème pour application"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(88l,"crème pour usage dentaire"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(89l,"crème stérile"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(90l,"dispersion à diluer pour solution injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(91l,"dispersion injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(92l,"dispersion liposomale à diluer injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(93l,"dispersion pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(94l,"dispositif"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(95l,"dispositif et dispositif"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(96l,"dispositif et gel"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(97l,"dispositif pour application"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(98l,"dispositif(s)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(99l,"éluat et solution"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(100l,"emplâtre"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(101l,"emplâtre adhésif(ve)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(102l,"emplâtre médicamenteux(se)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(103l,"émulsion et solution et solution pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(104l,"émulsion fluide pour application"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(105l,"émulsion injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(106l,"émulsion injectable ou pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(107l,"émulsion injectable pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(108l,"émulsion pour application"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(109l,"émulsion pour inhalation par fumigation"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(110l,"émulsion pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(111l,"éponge pour usage dentaire"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(112l,"film orodispersible"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(113l,"gaz"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(114l,"gaz pour inhalation"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(115l,"gel"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(116l,"gel buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(117l,"gel dentifrice"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(118l,"gel et"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(119l,"gel intestinal"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(120l,"gel pour application"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(121l,"gel pour lavement"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(122l,"gel pour usage dentaire"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(123l,"gel stérile"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(124l,"gelée"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(125l,"gélule"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(126l,"gélule à libération modifiée"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(127l,"gélule à libération prolongée"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(128l,"gélule et gélule"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(129l,"gélule et gélule gastro-résistant(e)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(130l,"gélule gastro-résistant(e)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(131l,"gélule gastro-soluble et gélule gastro-résistant(e)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(132l,"générateur radiopharmaceutique"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(133l,"gomme"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(134l,"gomme à mâcher"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(135l,"gomme à mâcher médicamenteux(se)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(136l,"graines"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(137l,"granules"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(138l,"granulés"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(139l,"granulés à croquer"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(140l,"granulés à libération prolongée"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(141l,"granulés buvable pour solution"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(142l,"granulés buvable pour suspension"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(143l,"granulés effervescent(e)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(144l,"granulés effervescent(e) pour solution buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(145l,"granulés en gélule"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(146l,"granulés enrobé"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(147l,"granulés enrobé en vrac"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(148l,"granules et crème et solution en gouttes en gouttes"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(149l,"granulés et granulés pour solution buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(150l,"granules et pommade et solution en gouttes en gouttes"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(151l,"granules et poudre et solution en gouttes en gouttes"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(152l,"granules et solution"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(153l,"granules et solution en gouttes en gouttes"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(154l,"granules et solution en gouttes en gouttes buvable en gouttes"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(155l,"granules et solution en gouttes en gouttes et crème"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(156l,"granules et solution en gouttes en gouttes et poudre"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(157l,"granulés et solvant pour suspension buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(158l,"granulés gastro-résistant(e)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(159l,"granulés gastro-résistant(e) pour suspension buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(160l,"granulés orodispersible"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(161l,"granulés pour solution buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(162l,"granules pour suspension buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(163l,"granulés pour suspension buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(164l,"implant"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(165l,"implant injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(166l,"insert"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(167l,"liquide"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(168l,"liquide par vapeur pour inhalation"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(169l,"liquide pour application"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(170l,"liquide pour inhalation par fumigation"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(171l,"liquide pour inhalation par vapeur"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(172l,"lotion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(173l,"lotion pour application"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(174l,"lyophilisat"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(175l,"lyophilisat et poudre pour préparation injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(176l,"lyophilisat et poudre pour usage parentéral"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(177l,"lyophilisat et solution pour préparation injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(178l,"lyophilisat et solution pour usage parentéral"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(179l,"lyophilisat et solvant pour collyre"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(180l,"lyophilisat pour préparation injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(181l,"lyophilisat pour usage parentéral"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(182l,"lyophilisat pour usage parentéral pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(183l,"matrice"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(184l,"matrice pour colle"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(185l,"mélange de plantes pour tisane"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(186l,"microgranule à libération prolongée en gélule"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(187l,"microgranule en comprimé"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(188l,"microgranule gastro-résistant(e)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(189l,"microgranule gastro-résistant(e) en gélule"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(190l,"microsphère et solution pour usage parentéral à libération prolongée"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(191l,"mousse"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(192l,"mousse pour application"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(193l,"ovule"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(194l,"ovule à libération prolongée"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(195l,"pansement adhésif(ve)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(196l,"pansement médicamenteux(se)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(197l,"pastille"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(198l,"pastille à sucer"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(199l,"pâte"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(200l,"pâte à sucer"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(201l,"pâte dentifrice"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(202l,"pâte pour application"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(203l,"pâte pour usage dentaire"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(204l,"plante(s) pour tisane"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(205l,"plante(s) pour tisane en vrac"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(206l,"pommade"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(207l,"pommade pour application"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(208l,"pommade pour application et"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(209l,"poudre"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(210l,"poudre à diluer à diluer et diluant pour solution pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(211l,"poudre à diluer à diluer et solution pour solution pour solution"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(212l,"poudre à diluer pour solution pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(213l,"poudre buvable buvable et poudre buvable buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(214l,"poudre buvable effervescent(e) pour suspension"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(215l,"poudre buvable pour solution"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(216l,"poudre buvable pour suspension"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(217l,"poudre buvable pour suspension en pot"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(218l,"poudre effervescent(e) pour solution buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(219l,"poudre effervescent(e) pour suspension buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(220l,"poudre et dispersion et solvant pour solution à diluer pour dispersion pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(221l,"poudre et poudre"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(222l,"poudre et poudre pour solution buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(223l,"poudre et poudre pour solution injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(224l,"poudre et solution pour préparation injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(225l,"poudre et solution pour solution injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(226l,"poudre et solution pour usage parentéral"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(227l,"poudre et solution pour usage parentéral à diluer"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(228l,"poudre et solvant"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(229l,"poudre et solvant et matrice pour matrice implantable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(230l,"poudre et solvant et solvant pour solution injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(231l,"poudre et solvant pour dispersion injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(232l,"poudre et solvant pour inhalation par nébuliseur"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(233l,"poudre et solvant pour préparation injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(234l,"poudre et solvant pour solution"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(235l,"poudre et solvant pour solution à diluer pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(236l,"poudre et solvant pour solution injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(237l,"poudre et solvant pour solution injectable et pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(238l,"poudre et solvant pour solution injectable ou pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(239l,"poudre et solvant pour solution injectable pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(240l,"poudre et solvant pour solution injectable pour perfusion ou buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(241l,"poudre et solvant pour solution pour inhalation"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(242l,"poudre et solvant pour solution pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(243l,"poudre et solvant pour solution pour pulvérisation"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(244l,"poudre et solvant pour suspension"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(245l,"poudre et solvant pour suspension buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(246l,"poudre et solvant pour suspension injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(247l,"poudre et solvant pour suspension injectable à libération prolongée"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(248l,"poudre et solvant pour suspension pour instillation"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(249l,"poudre et solvant pour usage parentéral"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(250l,"poudre et suspension pour suspension injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(251l,"poudre pour aérosol et pour usage parentéral"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(252l,"poudre pour application"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(253l,"poudre pour concentré pour solution pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(254l,"poudre pour dispersion pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(255l,"poudre pour inhalation"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(256l,"poudre pour inhalation en gélule"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(257l,"poudre pour inhalation et poudre pour inhalation"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(258l,"poudre pour inhalation et poudre pour inhalation pour inhalation"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(259l,"poudre pour injection"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(260l,"poudre pour préparation injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(261l,"poudre pour solution"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(262l,"poudre pour solution à diluer injectable ou pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(263l,"poudre pour solution à diluer pour injection ou pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(264l,"poudre pour solution à diluer pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(265l,"poudre pour solution à diluer pour perfusion ou buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(266l,"poudre pour solution buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(267l,"poudre pour solution buvable entéral(e)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(268l,"poudre pour solution buvable et entéral(e)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(269l,"poudre pour solution buvable et gastro-entérale"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(270l,"poudre pour solution injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(271l,"poudre pour solution injectable et pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(272l,"poudre pour solution injectable ou pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(273l,"poudre pour solution injectable pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(274l,"poudre pour solution injectable pour perfusion ou"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(275l,"poudre pour solution injectable pour perfusion ou buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(276l,"poudre pour solution pour inhalation par nébuliseur"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(277l,"poudre pour solution pour injection ou pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(278l,"poudre pour solution pour irrigation vésical(e)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(279l,"poudre pour solution pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(280l,"poudre pour suspension buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(281l,"poudre pour suspension et"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(282l,"poudre pour suspension injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(283l,"poudre pour suspension injectable pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(284l,"poudre pour suspension ou"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(285l,"poudre pour suspension pour administration intravésicale"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(286l,"poudre pour usage diagnostic"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(287l,"poudre pour usage parentéral"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(288l,"précurseur radiopharmaceutique"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(289l,"précurseur radiopharmaceutique en solution"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(290l,"shampooing"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(291l,"sirop"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(292l,"solution"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(293l,"solution à diluer et diluant pour solution pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(294l,"solution à diluer et solvant à diluer pour solution injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(295l,"solution à diluer et solvant injectable injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(296l,"solution à diluer et solvant pour solution à diluer pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(297l,"solution à diluer injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(298l,"solution à diluer injectable ou pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(299l,"solution à diluer injectable pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(300l,"solution à diluer pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(301l,"solution à diluer pour solution buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(302l,"solution à diluer pour solution injectable pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(303l,"solution buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(304l,"solution buvable à diluer"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(305l,"solution buvable en gouttes"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(306l,"solution buvable et comprimé pelliculé buvable pelliculé"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(307l,"solution buvable et injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(308l,"solution buvable gouttes"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(309l,"solution buvable ou"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(310l,"solution cardioplégique"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(311l,"solution concentré(e) à diluer pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(312l,"solution concentré(e) à diluer pour solution pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(313l,"solution et"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(314l,"solution et émulsion et solution pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(315l,"solution et granules et poudre et pommade"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(316l,"solution et poudre pour injection"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(317l,"solution et solution buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(318l,"solution et solution et émulsion pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(319l,"solution et solution injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(320l,"solution et solution pour application"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(321l,"solution et solution pour colle"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(322l,"solution et solution pour dialyse péritonéale"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(323l,"solution et solution pour hémodialyse et pour hémofiltration"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(324l,"solution et solution pour hémodialyse pour hémofiltration"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(325l,"solution et solution pour hémofiltration et pour hémodialyse"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(326l,"solution et solution pour hémofiltration pour hémodialyse et pour hémodiafiltration"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(327l,"solution et solution pour marquage"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(328l,"solution et solution pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(329l,"solution et suspension pour suspension injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(330l,"solution filmogène pour application"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(331l,"solution gouttes"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(332l,"solution injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(333l,"solution injectable à diluer ou pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(334l,"solution injectable à diluer pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(335l,"solution injectable à libération prolongée"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(336l,"solution injectable et buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(337l,"solution injectable hypertonique pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(338l,"solution injectable isotonique"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(339l,"solution injectable ou"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(340l,"solution injectable ou à diluer pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(341l,"solution injectable ou pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(342l,"solution injectable pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(343l,"solution injectable pour usage dentaire"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(344l,"solution moussant(e)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(345l,"solution ou"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(346l,"solution ou injectable pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(347l,"solution pour administration endonasale"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(348l,"solution pour administration intravésicale"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(349l,"solution pour application"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(350l,"solution pour application à diluer"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(351l,"solution pour application et solution pour application"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(352l,"solution pour application moussant(e)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(353l,"solution pour application stérile"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(354l,"solution pour bain de bouche"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(355l,"solution pour dialyse péritonéale"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(356l,"solution pour gargarisme ou pour bain de bouche"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(357l,"solution pour hémodialyse pour hémodialyse et solution pour hémodialyse pour hémodialyse"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(358l,"solution pour hémofiltration"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(359l,"solution pour inhalation"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(360l,"solution pour inhalation par fumigation"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(361l,"solution pour inhalation par nébuliseur"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(362l,"solution pour injection"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(363l,"solution pour injection ou pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(364l,"solution pour instillation"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(365l,"solution pour irrigation oculaire"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(366l,"solution pour lavage"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(367l,"solution pour marquage"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(368l,"solution pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(369l,"solution pour préparation injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(370l,"solution pour préparation injectable ou pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(371l,"solution pour préparation parentérale"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(372l,"solution pour prick-test"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(373l,"solution pour pulvérisation"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(374l,"solution pour pulvérisation endo-buccal(e)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(375l,"solution pour usage dentaire"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(376l,"solvant et solution et poudre(s) pour colle"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(377l,"solvant pour préparation parentérale"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(378l,"solvant(s) et poudre(s) pour solution injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(379l,"substitut de tissu vivant"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(380l,"suppositoire"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(381l,"suppositoire effervescent(e)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(382l,"suppositoire sécable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(383l,"suspension"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(384l,"suspension à diluer pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(385l,"suspension buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(386l,"suspension buvable à diluer"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(387l,"suspension buvable ou"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(388l,"suspension buvable ou pour instillation"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(389l,"suspension colloidale injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(390l,"suspension et granulés effervescent(e) pour suspension buvable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(391l,"suspension et solvant pour usage dentaire"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(392l,"suspension injectable"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(393l,"suspension injectable à libération prolongée"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(394l,"suspension par nébuliseur pour inhalation"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(395l,"suspension pour application"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(396l,"suspension pour inhalation"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(397l,"suspension pour inhalation par nébuliseur"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(398l,"suspension pour injection"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(399l,"suspension pour instillation"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(400l,"suspension pour pulvérisation"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(401l,"système de diffusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(402l,"tampon imprégné(e) pour inhalation"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(403l,"tampon imprégné(e) pour inhalation par fumigation"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(404l,"trousse"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(405l,"trousse et trousse et trousse pour préparation radiopharmaceutique"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(406l,"trousse et trousse et trousse pour préparation radiopharmaceutique pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(407l,"trousse et trousse pour préparation radiopharmaceutique"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(408l,"trousse et trousse pour préparation radiopharmaceutique pour injection"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(409l,"trousse et trousse radiopharmaceutique"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(410l,"trousse pour préparation radiopharmaceutique"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(411l,"trousse pour préparation radiopharmaceutique et trousse pour préparation radiopharmaceutique"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(412l,"trousse radiopharmaceutique"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(413l,"vernis à ongles médicamenteux(se)"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(414l,"poudre et solvant pour suspension pour administration intravésicale"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(415l,"poudre pour solution à diluer injectable pour perfusion"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(416l,"solution oculaire pour lavage"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(417l,"solution pour pulvérisation ou"));
            formePharmaceutiqueDao.insert(new FormePharmaceutique(418l,"solvant(s) et poudre(s) pour colle"));




        }
    }

    public void remplirDefaultBD() {
        Long count = doseDao.count();
        if (count ==0) {
            doseDao.insert(new Dose(0l,"application"));
            doseDao.insert(new Dose(1l,"bâton"));
            doseDao.insert(new Dose(2l,"capsule"));
            doseDao.insert(new Dose(3l,"cartouche"));
            doseDao.insert(new Dose(4l,"compresse"));
            doseDao.insert(new Dose(5l,"comprimé"));
            doseDao.insert(new Dose(6l,"cuillère à café"));
            doseDao.insert(new Dose(7l,"cuillère à soupe"));
            doseDao.insert(new Dose(8l,"dispositif"));
            doseDao.insert(new Dose(9l,"emplâtre"));
            doseDao.insert(new Dose(10l,"gélule"));
            doseDao.insert(new Dose(11l,"gomme"));
            doseDao.insert(new Dose(12l,"goutte"));
            doseDao.insert(new Dose(13l,"graine"));
            doseDao.insert(new Dose(14l,"granulés"));
            doseDao.insert(new Dose(15l,"indéfini"));
            doseDao.insert(new Dose(16l,"pansement"));
            doseDao.insert(new Dose(17l,"pastille"));
            doseDao.insert(new Dose(18l,"sachet"));
            doseDao.insert(new Dose(19l,"suppositoire"));
            doseDao.insert(new Dose(20l,"tampon"));
            doseDao.insert(new Dose(21l,"trousse"));
        }

        count = associationFormeDoseDao.count();
        if (count ==0) {
            associationFormeDoseDao.insert(new AssociationFormeDose(0l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé et solution(s) et granules et poudre").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(1l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé et solution(s) et granules et poudre et pommade").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(2l,formePharmaceutiqueDao.queryRaw("where name = ?","crème et solution et granules et poudre").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(3l,formePharmaceutiqueDao.queryRaw("where name = ?","crème et solution et granules et poudre et pommade").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(4l,formePharmaceutiqueDao.queryRaw("where name = ?","dispositif(s)").get(0).getId(), doseDao.queryRaw("where name = ?","dispositif").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(5l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et granules et poudre et pommade").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(6l,formePharmaceutiqueDao.queryRaw("where name = ?","bain de bouche").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(7l,formePharmaceutiqueDao.queryRaw("where name = ?","bâton pour application").get(0).getId(), doseDao.queryRaw("where name = ?","bâton").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(8l,formePharmaceutiqueDao.queryRaw("where name = ?","bâton pour usage dentaire").get(0).getId(), doseDao.queryRaw("where name = ?","bâton").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(9l,formePharmaceutiqueDao.queryRaw("where name = ?","bâton pour usage urétral").get(0).getId(), doseDao.queryRaw("where name = ?","bâton").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(10l,formePharmaceutiqueDao.queryRaw("where name = ?","capsule").get(0).getId(), doseDao.queryRaw("where name = ?","capsule").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(11l,formePharmaceutiqueDao.queryRaw("where name = ?","capsule molle").get(0).getId(), doseDao.queryRaw("where name = ?","capsule").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(12l,formePharmaceutiqueDao.queryRaw("where name = ?","capsule molle ou").get(0).getId(), doseDao.queryRaw("where name = ?","capsule").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(13l,formePharmaceutiqueDao.queryRaw("where name = ?","capsule pour inhalation par vapeur").get(0).getId(), doseDao.queryRaw("where name = ?","capsule").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(14l,formePharmaceutiqueDao.queryRaw("where name = ?","cartouche pour inhalation").get(0).getId(), doseDao.queryRaw("where name = ?","cartouche").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(15l,formePharmaceutiqueDao.queryRaw("where name = ?","collutoire").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(16l,formePharmaceutiqueDao.queryRaw("where name = ?","collyre").get(0).getId(), doseDao.queryRaw("where name = ?","goutte").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(17l,formePharmaceutiqueDao.queryRaw("where name = ?","collyre à libération prolongée").get(0).getId(), doseDao.queryRaw("where name = ?","goutte").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(18l,formePharmaceutiqueDao.queryRaw("where name = ?","collyre en émulsion").get(0).getId(), doseDao.queryRaw("where name = ?","goutte").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(19l,formePharmaceutiqueDao.queryRaw("where name = ?","collyre en solution").get(0).getId(), doseDao.queryRaw("where name = ?","goutte").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(20l,formePharmaceutiqueDao.queryRaw("where name = ?","collyre en suspension").get(0).getId(), doseDao.queryRaw("where name = ?","goutte").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(21l,formePharmaceutiqueDao.queryRaw("where name = ?","collyre pour solution").get(0).getId(), doseDao.queryRaw("where name = ?","goutte").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(22l,formePharmaceutiqueDao.queryRaw("where name = ?","compresse et solution(s) et générateur radiopharmaceutique").get(0).getId(), doseDao.queryRaw("where name = ?","compresse").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(23l,formePharmaceutiqueDao.queryRaw("where name = ?","compresse imprégné(e)").get(0).getId(), doseDao.queryRaw("where name = ?","compresse").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(24l,formePharmaceutiqueDao.queryRaw("where name = ?","compresse imprégné(e) pour usage dentaire").get(0).getId(), doseDao.queryRaw("where name = ?","compresse").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(25l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(26l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé à croquer").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(27l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé à croquer à sucer ou dispersible").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(28l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé à croquer ou à sucer").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(29l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé à croquer ou dispersible").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(30l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé à libération modifiée").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(31l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé à libération prolongée").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(32l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé à mâcher").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(33l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé à sucer").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(34l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé à sucer ou à croquer").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(35l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé à sucer sécable").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(36l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé dispersible").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(37l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé dispersible et orodispersible").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(38l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé dispersible orodispersible").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(39l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé dispersible ou à croquer").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(40l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé dispersible sécable").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(41l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé dragéifié").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(42l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé effervescent(e)").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(43l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé effervescent(e) sécable").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(44l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé enrobé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(45l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé enrobé à croquer").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(46l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé enrobé à libération prolongée").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(47l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé enrobé et comprimé enrobé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(48l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé enrobé et comprimé enrobé enrobé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(49l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé enrobé et comprimé enrobé et comprimé enrobé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(50l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé enrobé et comprimé enrobé et comprimé enrobé enrobé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(51l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé enrobé gastro-résistant(e)").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(52l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé enrobé sécable").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(53l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé et comprimé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(54l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé et comprimé et comprimé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(55l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé et comprimé pelliculé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(56l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé et gélule").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(57l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé gastro-résistant(e)").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(58l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé muco-adhésif").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(59l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé orodispersible").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(60l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé orodispersible sécable").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(61l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé osmotique").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(62l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé osmotique pelliculé à libération prolongée").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(63l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé pelliculé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(64l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé pelliculé à libération modifiée").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(65l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé pelliculé à libération prolongée").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(66l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé pelliculé dispersible").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(67l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé pelliculé et comprimé pelliculé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(68l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé pelliculé et comprimé pelliculé et comprimé pelliculé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(69l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé pelliculé et comprimé pelliculé pelliculé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(70l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé pelliculé et granulés effervescent(e)").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(71l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé pelliculé gastro-résistant(e)").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(72l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé pelliculé quadrisécable").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(73l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé pelliculé sécable").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(74l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé pelliculé sécable à libération prolongée").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(75l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé pour solution buvable").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(76l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé pour suspension buvable").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(77l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé quadrisécable").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(78l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé sécable").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(79l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé sécable à libération modifiée").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(80l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé sécable à libération prolongée").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(81l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé sécable pelliculé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(82l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé sécable pour suspension buvable").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(83l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé(s) pelliculé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(84l,formePharmaceutiqueDao.queryRaw("where name = ?","cône pour usage dentaire").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(85l,formePharmaceutiqueDao.queryRaw("where name = ?","crème").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(86l,formePharmaceutiqueDao.queryRaw("where name = ?","crème épaisse pour application").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(87l,formePharmaceutiqueDao.queryRaw("where name = ?","crème et solution et granules et poudre unidose").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(88l,formePharmaceutiqueDao.queryRaw("where name = ?","crème pour application").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(89l,formePharmaceutiqueDao.queryRaw("where name = ?","crème pour usage dentaire").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(90l,formePharmaceutiqueDao.queryRaw("where name = ?","crème stérile").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(91l,formePharmaceutiqueDao.queryRaw("where name = ?","dispersion liposomale à diluer injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(92l,formePharmaceutiqueDao.queryRaw("where name = ?","dispersion pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(93l,formePharmaceutiqueDao.queryRaw("where name = ?","dispositif").get(0).getId(), doseDao.queryRaw("where name = ?","dispositif").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(94l,formePharmaceutiqueDao.queryRaw("where name = ?","dispositif et dispositif").get(0).getId(), doseDao.queryRaw("where name = ?","dispositif").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(95l,formePharmaceutiqueDao.queryRaw("where name = ?","dispositif et gel").get(0).getId(), doseDao.queryRaw("where name = ?","dispositif").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(96l,formePharmaceutiqueDao.queryRaw("where name = ?","dispositif pour application").get(0).getId(), doseDao.queryRaw("where name = ?","dispositif").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(97l,formePharmaceutiqueDao.queryRaw("where name = ?","éluat et solution").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(98l,formePharmaceutiqueDao.queryRaw("where name = ?","emplâtre").get(0).getId(), doseDao.queryRaw("where name = ?","emplâtre").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(99l,formePharmaceutiqueDao.queryRaw("where name = ?","emplâtre adhésif(ve)").get(0).getId(), doseDao.queryRaw("where name = ?","emplâtre").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(100l,formePharmaceutiqueDao.queryRaw("where name = ?","emplâtre médicamenteux(se)").get(0).getId(), doseDao.queryRaw("where name = ?","emplâtre").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(101l,formePharmaceutiqueDao.queryRaw("where name = ?","émulsion et solution et solution pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(102l,formePharmaceutiqueDao.queryRaw("where name = ?","émulsion fluide pour application").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(103l,formePharmaceutiqueDao.queryRaw("where name = ?","émulsion injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(104l,formePharmaceutiqueDao.queryRaw("where name = ?","émulsion injectable ou pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(105l,formePharmaceutiqueDao.queryRaw("where name = ?","émulsion injectable pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(106l,formePharmaceutiqueDao.queryRaw("where name = ?","émulsion pour application").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(107l,formePharmaceutiqueDao.queryRaw("where name = ?","émulsion pour inhalation par fumigation").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(108l,formePharmaceutiqueDao.queryRaw("where name = ?","émulsion pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(109l,formePharmaceutiqueDao.queryRaw("where name = ?","éponge pour usage dentaire").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(110l,formePharmaceutiqueDao.queryRaw("where name = ?","film orodispersible").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(111l,formePharmaceutiqueDao.queryRaw("where name = ?","gaz").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(112l,formePharmaceutiqueDao.queryRaw("where name = ?","gaz pour inhalation").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(113l,formePharmaceutiqueDao.queryRaw("where name = ?","gel").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(114l,formePharmaceutiqueDao.queryRaw("where name = ?","gel buvable").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(115l,formePharmaceutiqueDao.queryRaw("where name = ?","gel dentifrice").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(116l,formePharmaceutiqueDao.queryRaw("where name = ?","gel et").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(117l,formePharmaceutiqueDao.queryRaw("where name = ?","gel intestinal").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(118l,formePharmaceutiqueDao.queryRaw("where name = ?","gel pour application").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(119l,formePharmaceutiqueDao.queryRaw("where name = ?","gel pour lavement").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(120l,formePharmaceutiqueDao.queryRaw("where name = ?","gel pour usage dentaire").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(121l,formePharmaceutiqueDao.queryRaw("where name = ?","gel stérile").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(122l,formePharmaceutiqueDao.queryRaw("where name = ?","gelée").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(123l,formePharmaceutiqueDao.queryRaw("where name = ?","gélule").get(0).getId(), doseDao.queryRaw("where name = ?","gélule").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(124l,formePharmaceutiqueDao.queryRaw("where name = ?","gélule à libération modifiée").get(0).getId(), doseDao.queryRaw("where name = ?","gélule").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(125l,formePharmaceutiqueDao.queryRaw("where name = ?","gélule à libération prolongée").get(0).getId(), doseDao.queryRaw("where name = ?","gélule").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(126l,formePharmaceutiqueDao.queryRaw("where name = ?","gélule et gélule").get(0).getId(), doseDao.queryRaw("where name = ?","gélule").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(127l,formePharmaceutiqueDao.queryRaw("where name = ?","gélule et gélule gastro-résistant(e)").get(0).getId(), doseDao.queryRaw("where name = ?","gélule").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(128l,formePharmaceutiqueDao.queryRaw("where name = ?","gélule gastro-résistant(e)").get(0).getId(), doseDao.queryRaw("where name = ?","gélule").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(129l,formePharmaceutiqueDao.queryRaw("where name = ?","gélule gastro-soluble et gélule gastro-résistant(e)").get(0).getId(), doseDao.queryRaw("where name = ?","gélule").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(130l,formePharmaceutiqueDao.queryRaw("where name = ?","générateur radiopharmaceutique").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(131l,formePharmaceutiqueDao.queryRaw("where name = ?","gomme").get(0).getId(), doseDao.queryRaw("where name = ?","gomme").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(132l,formePharmaceutiqueDao.queryRaw("where name = ?","gomme à mâcher").get(0).getId(), doseDao.queryRaw("where name = ?","gomme").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(133l,formePharmaceutiqueDao.queryRaw("where name = ?","gomme à mâcher médicamenteux(se)").get(0).getId(), doseDao.queryRaw("where name = ?","gomme").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(134l,formePharmaceutiqueDao.queryRaw("where name = ?","graines").get(0).getId(), doseDao.queryRaw("where name = ?","graine").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(135l,formePharmaceutiqueDao.queryRaw("where name = ?","granules").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(136l,formePharmaceutiqueDao.queryRaw("where name = ?","granulés").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(137l,formePharmaceutiqueDao.queryRaw("where name = ?","granulés à croquer").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(138l,formePharmaceutiqueDao.queryRaw("where name = ?","granulés à libération prolongée").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(139l,formePharmaceutiqueDao.queryRaw("where name = ?","granulés buvable pour solution").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(140l,formePharmaceutiqueDao.queryRaw("where name = ?","granulés buvable pour suspension").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(141l,formePharmaceutiqueDao.queryRaw("where name = ?","granulés effervescent(e)").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(142l,formePharmaceutiqueDao.queryRaw("where name = ?","granulés effervescent(e) pour solution buvable").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(143l,formePharmaceutiqueDao.queryRaw("where name = ?","granulés en gélule").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(144l,formePharmaceutiqueDao.queryRaw("where name = ?","granulés enrobé").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(145l,formePharmaceutiqueDao.queryRaw("where name = ?","granulés enrobé en vrac").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(146l,formePharmaceutiqueDao.queryRaw("where name = ?","granules et crème et solution en gouttes en gouttes").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(147l,formePharmaceutiqueDao.queryRaw("where name = ?","granulés et granulés pour solution buvable").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(148l,formePharmaceutiqueDao.queryRaw("where name = ?","granules et pommade et solution en gouttes en gouttes").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(149l,formePharmaceutiqueDao.queryRaw("where name = ?","granules et poudre et solution en gouttes en gouttes").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(150l,formePharmaceutiqueDao.queryRaw("where name = ?","granules et solution en gouttes en gouttes").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(151l,formePharmaceutiqueDao.queryRaw("where name = ?","granules et solution en gouttes en gouttes et crème").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(152l,formePharmaceutiqueDao.queryRaw("where name = ?","granules et solution en gouttes en gouttes et poudre").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(153l,formePharmaceutiqueDao.queryRaw("where name = ?","granulés et solvant pour suspension buvable").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(154l,formePharmaceutiqueDao.queryRaw("where name = ?","granulés gastro-résistant(e)").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(155l,formePharmaceutiqueDao.queryRaw("where name = ?","granulés gastro-résistant(e) pour suspension buvable").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(156l,formePharmaceutiqueDao.queryRaw("where name = ?","granulés pour solution buvable").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(157l,formePharmaceutiqueDao.queryRaw("where name = ?","granules pour suspension buvable").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(158l,formePharmaceutiqueDao.queryRaw("where name = ?","granulés pour suspension buvable").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(159l,formePharmaceutiqueDao.queryRaw("where name = ?","implant").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(160l,formePharmaceutiqueDao.queryRaw("where name = ?","implant injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(161l,formePharmaceutiqueDao.queryRaw("where name = ?","insert").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(162l,formePharmaceutiqueDao.queryRaw("where name = ?","liquide").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(163l,formePharmaceutiqueDao.queryRaw("where name = ?","liquide par vapeur pour inhalation").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(164l,formePharmaceutiqueDao.queryRaw("where name = ?","liquide pour application").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(165l,formePharmaceutiqueDao.queryRaw("where name = ?","liquide pour inhalation par fumigation").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(166l,formePharmaceutiqueDao.queryRaw("where name = ?","liquide pour inhalation par vapeur").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(167l,formePharmaceutiqueDao.queryRaw("where name = ?","lotion").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(168l,formePharmaceutiqueDao.queryRaw("where name = ?","lotion pour application").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(169l,formePharmaceutiqueDao.queryRaw("where name = ?","lyophilisat").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(170l,formePharmaceutiqueDao.queryRaw("where name = ?","lyophilisat et poudre pour préparation injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(171l,formePharmaceutiqueDao.queryRaw("where name = ?","lyophilisat et poudre pour usage parentéral").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(172l,formePharmaceutiqueDao.queryRaw("where name = ?","lyophilisat et solution pour préparation injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(173l,formePharmaceutiqueDao.queryRaw("where name = ?","lyophilisat et solution pour usage parentéral").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(174l,formePharmaceutiqueDao.queryRaw("where name = ?","lyophilisat et solvant pour collyre").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(175l,formePharmaceutiqueDao.queryRaw("where name = ?","lyophilisat pour préparation injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(176l,formePharmaceutiqueDao.queryRaw("where name = ?","lyophilisat pour usage parentéral").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(177l,formePharmaceutiqueDao.queryRaw("where name = ?","lyophilisat pour usage parentéral pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(178l,formePharmaceutiqueDao.queryRaw("where name = ?","matrice").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(179l,formePharmaceutiqueDao.queryRaw("where name = ?","matrice pour colle").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(180l,formePharmaceutiqueDao.queryRaw("where name = ?","mélange de plantes pour tisane").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(181l,formePharmaceutiqueDao.queryRaw("where name = ?","microgranule à libération prolongée en gélule").get(0).getId(), doseDao.queryRaw("where name = ?","gélule").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(182l,formePharmaceutiqueDao.queryRaw("where name = ?","microgranule en comprimé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(183l,formePharmaceutiqueDao.queryRaw("where name = ?","microgranule gastro-résistant(e)").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(184l,formePharmaceutiqueDao.queryRaw("where name = ?","microgranule gastro-résistant(e) en gélule").get(0).getId(), doseDao.queryRaw("where name = ?","gélule").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(185l,formePharmaceutiqueDao.queryRaw("where name = ?","microsphère et solution pour usage parentéral à libération prolongée").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(186l,formePharmaceutiqueDao.queryRaw("where name = ?","mousse").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(187l,formePharmaceutiqueDao.queryRaw("where name = ?","mousse pour application").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(188l,formePharmaceutiqueDao.queryRaw("where name = ?","ovule").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(189l,formePharmaceutiqueDao.queryRaw("where name = ?","ovule à libération prolongée").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(190l,formePharmaceutiqueDao.queryRaw("where name = ?","pansement adhésif(ve)").get(0).getId(), doseDao.queryRaw("where name = ?","pansement").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(191l,formePharmaceutiqueDao.queryRaw("where name = ?","pansement médicamenteux(se)").get(0).getId(), doseDao.queryRaw("where name = ?","pansement").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(192l,formePharmaceutiqueDao.queryRaw("where name = ?","pastille").get(0).getId(), doseDao.queryRaw("where name = ?","pastille").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(193l,formePharmaceutiqueDao.queryRaw("where name = ?","pastille à sucer").get(0).getId(), doseDao.queryRaw("where name = ?","pastille").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(194l,formePharmaceutiqueDao.queryRaw("where name = ?","pâte").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(195l,formePharmaceutiqueDao.queryRaw("where name = ?","pâte à sucer").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(196l,formePharmaceutiqueDao.queryRaw("where name = ?","pâte dentifrice").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(197l,formePharmaceutiqueDao.queryRaw("where name = ?","pâte pour application").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(198l,formePharmaceutiqueDao.queryRaw("where name = ?","pâte pour usage dentaire").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(199l,formePharmaceutiqueDao.queryRaw("where name = ?","plante(s) pour tisane").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(200l,formePharmaceutiqueDao.queryRaw("where name = ?","plante(s) pour tisane en vrac").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(201l,formePharmaceutiqueDao.queryRaw("where name = ?","pommade").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(202l,formePharmaceutiqueDao.queryRaw("where name = ?","pommade pour application").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(203l,formePharmaceutiqueDao.queryRaw("where name = ?","pommade pour application et").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(204l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(205l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre à diluer à diluer et diluant pour solution pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(206l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre à diluer à diluer et solution pour solution pour solution").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(207l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre à diluer pour solution pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(208l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre buvable effervescent(e) pour suspension").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(209l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre buvable pour solution").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(210l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre buvable pour suspension").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(211l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre buvable pour suspension en pot").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(212l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre effervescent(e) pour solution buvable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(213l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre effervescent(e) pour suspension buvable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(214l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et dispersion et solvant pour solution à diluer pour dispersion pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(215l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et poudre").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(216l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et poudre pour solution buvable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(217l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et poudre pour solution injectable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(218l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solution pour préparation injectable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(219l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solution pour solution injectable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(220l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solution pour usage parentéral").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(221l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solution pour usage parentéral à diluer").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(222l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(223l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant et matrice pour matrice implantable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(224l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant et solvant pour solution injectable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(225l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour dispersion injectable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(226l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour inhalation par nébuliseur").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(227l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour préparation injectable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(228l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour solution").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(229l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour solution à diluer pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(230l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour solution injectable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(231l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour solution injectable et pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(232l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour solution injectable ou pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(233l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour solution injectable pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(234l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour solution injectable pour perfusion ou buvable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(235l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour solution pour inhalation").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(236l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour solution pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(237l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour solution pour pulvérisation").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(238l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour suspension buvable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(239l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour suspension injectable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(240l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour suspension injectable à libération prolongée").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(241l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour suspension pour administration intravésicale").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(242l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour suspension pour instillation").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(243l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour usage parentéral").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(244l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et suspension pour suspension injectable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(245l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour aérosol et pour usage parentéral").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(246l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour application").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(247l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour concentré pour solution pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(248l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour dispersion pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(249l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour inhalation").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(250l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour inhalation en gélule").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(251l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour inhalation et poudre pour inhalation").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(252l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour inhalation et poudre pour inhalation pour inhalation").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(253l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour injection").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(254l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour préparation injectable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(255l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour solution").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(256l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour solution à diluer injectable ou pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(257l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour solution à diluer injectable pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(258l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour solution à diluer pour injection ou pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(259l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour solution à diluer pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(260l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour solution à diluer pour perfusion ou buvable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(261l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour solution buvable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(262l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour solution buvable entéral(e)").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(263l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour solution buvable et entéral(e)").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(264l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour solution buvable et gastro-entérale").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(265l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour solution injectable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(266l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour solution injectable et pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(267l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour solution injectable ou pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(268l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour solution injectable pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(269l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour solution injectable pour perfusion ou buvable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(270l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour solution pour inhalation par nébuliseur").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(271l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour solution pour injection ou pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(272l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour solution pour irrigation vésical(e)").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(273l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour solution pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(274l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour suspension buvable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(275l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour suspension et").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(276l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour suspension injectable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(277l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour suspension injectable pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(278l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour suspension ou").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(279l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour suspension pour administration intravésicale").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(280l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour usage diagnostic").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(281l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour usage parentéral").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(282l,formePharmaceutiqueDao.queryRaw("where name = ?","précurseur radiopharmaceutique").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(283l,formePharmaceutiqueDao.queryRaw("where name = ?","précurseur radiopharmaceutique en solution").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(284l,formePharmaceutiqueDao.queryRaw("where name = ?","shampooing").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(285l,formePharmaceutiqueDao.queryRaw("where name = ?","sirop").get(0).getId(), doseDao.queryRaw("where name = ?","cuillère à café").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(286l,formePharmaceutiqueDao.queryRaw("where name = ?","sirop").get(0).getId(), doseDao.queryRaw("where name = ?","cuillère à soupe").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(287l,formePharmaceutiqueDao.queryRaw("where name = ?","solution").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(288l,formePharmaceutiqueDao.queryRaw("where name = ?","solution à diluer et diluant pour solution pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(289l,formePharmaceutiqueDao.queryRaw("where name = ?","solution à diluer et solvant à diluer pour solution injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(290l,formePharmaceutiqueDao.queryRaw("where name = ?","solution à diluer et solvant injectable injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(291l,formePharmaceutiqueDao.queryRaw("where name = ?","solution à diluer et solvant pour solution à diluer pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(292l,formePharmaceutiqueDao.queryRaw("where name = ?","solution à diluer injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(293l,formePharmaceutiqueDao.queryRaw("where name = ?","solution à diluer injectable ou pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(294l,formePharmaceutiqueDao.queryRaw("where name = ?","solution à diluer injectable pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(295l,formePharmaceutiqueDao.queryRaw("where name = ?","solution à diluer pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(296l,formePharmaceutiqueDao.queryRaw("where name = ?","solution à diluer pour solution buvable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(297l,formePharmaceutiqueDao.queryRaw("where name = ?","solution à diluer pour solution injectable pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(298l,formePharmaceutiqueDao.queryRaw("where name = ?","solution buvable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(299l,formePharmaceutiqueDao.queryRaw("where name = ?","solution buvable à diluer").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(300l,formePharmaceutiqueDao.queryRaw("where name = ?","solution buvable en gouttes").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(301l,formePharmaceutiqueDao.queryRaw("where name = ?","solution buvable et comprimé pelliculé buvable pelliculé").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(302l,formePharmaceutiqueDao.queryRaw("where name = ?","solution buvable et injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(303l,formePharmaceutiqueDao.queryRaw("where name = ?","solution buvable gouttes").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(304l,formePharmaceutiqueDao.queryRaw("where name = ?","solution buvable ou").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(305l,formePharmaceutiqueDao.queryRaw("where name = ?","solution concentré(e) à diluer pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(306l,formePharmaceutiqueDao.queryRaw("where name = ?","solution concentré(e) à diluer pour solution pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(307l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(308l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et émulsion et solution pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(309l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et poudre pour injection").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(310l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution buvable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(311l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution et émulsion pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(312l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(313l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution pour application").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(314l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution pour colle").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(315l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution pour dialyse péritonéale").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(316l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution pour hémodialyse et pour hémofiltration").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(317l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution pour hémodialyse pour hémofiltration").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(318l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution pour hémofiltration et pour hémodialyse").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(319l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution pour hémofiltration pour hémodialyse et pour hémodiafiltration").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(320l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution pour marquage").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(321l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(322l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et suspension pour suspension injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(323l,formePharmaceutiqueDao.queryRaw("where name = ?","solution filmogène pour application").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(324l,formePharmaceutiqueDao.queryRaw("where name = ?","solution gouttes").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(325l,formePharmaceutiqueDao.queryRaw("where name = ?","solution injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(326l,formePharmaceutiqueDao.queryRaw("where name = ?","solution injectable à diluer ou pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(327l,formePharmaceutiqueDao.queryRaw("where name = ?","solution injectable à diluer pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(328l,formePharmaceutiqueDao.queryRaw("where name = ?","solution injectable à libération prolongée").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(329l,formePharmaceutiqueDao.queryRaw("where name = ?","solution injectable et buvable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(330l,formePharmaceutiqueDao.queryRaw("where name = ?","solution injectable hypertonique pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(331l,formePharmaceutiqueDao.queryRaw("where name = ?","solution injectable isotonique").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(332l,formePharmaceutiqueDao.queryRaw("where name = ?","solution injectable ou").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(333l,formePharmaceutiqueDao.queryRaw("where name = ?","solution injectable ou à diluer pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(334l,formePharmaceutiqueDao.queryRaw("where name = ?","solution injectable ou pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(335l,formePharmaceutiqueDao.queryRaw("where name = ?","solution injectable pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(336l,formePharmaceutiqueDao.queryRaw("where name = ?","solution injectable pour usage dentaire").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(337l,formePharmaceutiqueDao.queryRaw("where name = ?","solution moussant(e)").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(338l,formePharmaceutiqueDao.queryRaw("where name = ?","solution oculaire pour lavage").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(339l,formePharmaceutiqueDao.queryRaw("where name = ?","solution ou").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(340l,formePharmaceutiqueDao.queryRaw("where name = ?","solution ou injectable pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(341l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour administration endonasale").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(342l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour administration intravésicale").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(343l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour application").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(344l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour application à diluer").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(345l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour application et solution pour application").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(346l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour application moussant(e)").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(347l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour application stérile").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(348l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour bain de bouche").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(349l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour dialyse péritonéale").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(350l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour gargarisme ou pour bain de bouche").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(351l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour hémodialyse pour hémodialyse et solution pour hémodialyse pour hémodialyse").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(352l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour hémofiltration").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(353l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour inhalation").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(354l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour inhalation par fumigation").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(355l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour inhalation par nébuliseur").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(356l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour injection").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(357l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour injection ou pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(358l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour instillation").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(359l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour irrigation oculaire").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(360l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour lavage").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(361l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour marquage").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(362l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(363l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour préparation injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(364l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour préparation injectable ou pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(365l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour préparation parentérale").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(366l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour prick-test").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(367l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour pulvérisation").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(368l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour pulvérisation endo-buccal(e)").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(369l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour pulvérisation ou").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(370l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour usage dentaire").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(371l,formePharmaceutiqueDao.queryRaw("where name = ?","solvant et solution et poudre(s) pour colle").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(372l,formePharmaceutiqueDao.queryRaw("where name = ?","solvant pour préparation parentérale").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(373l,formePharmaceutiqueDao.queryRaw("where name = ?","solvant(s) et poudre(s) pour colle").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(374l,formePharmaceutiqueDao.queryRaw("where name = ?","solvant(s) et poudre(s) pour solution injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(375l,formePharmaceutiqueDao.queryRaw("where name = ?","substitut de tissu vivant").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(376l,formePharmaceutiqueDao.queryRaw("where name = ?","suppositoire").get(0).getId(), doseDao.queryRaw("where name = ?","suppositoire").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(377l,formePharmaceutiqueDao.queryRaw("where name = ?","suppositoire effervescent(e)").get(0).getId(), doseDao.queryRaw("where name = ?","suppositoire").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(378l,formePharmaceutiqueDao.queryRaw("where name = ?","suppositoire sécable").get(0).getId(), doseDao.queryRaw("where name = ?","suppositoire").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(379l,formePharmaceutiqueDao.queryRaw("where name = ?","suspension").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(380l,formePharmaceutiqueDao.queryRaw("where name = ?","suspension à diluer pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(381l,formePharmaceutiqueDao.queryRaw("where name = ?","suspension buvable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(382l,formePharmaceutiqueDao.queryRaw("where name = ?","suspension buvable à diluer").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(383l,formePharmaceutiqueDao.queryRaw("where name = ?","suspension buvable ou").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(384l,formePharmaceutiqueDao.queryRaw("where name = ?","suspension buvable ou pour instillation").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(385l,formePharmaceutiqueDao.queryRaw("where name = ?","suspension colloidale injectable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(386l,formePharmaceutiqueDao.queryRaw("where name = ?","suspension et granulés effervescent(e) pour suspension buvable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(387l,formePharmaceutiqueDao.queryRaw("where name = ?","suspension et solvant pour usage dentaire").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(388l,formePharmaceutiqueDao.queryRaw("where name = ?","suspension injectable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(389l,formePharmaceutiqueDao.queryRaw("where name = ?","suspension injectable à libération prolongée").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(390l,formePharmaceutiqueDao.queryRaw("where name = ?","suspension par nébuliseur pour inhalation").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(391l,formePharmaceutiqueDao.queryRaw("where name = ?","suspension pour application").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(392l,formePharmaceutiqueDao.queryRaw("where name = ?","suspension pour inhalation").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(393l,formePharmaceutiqueDao.queryRaw("where name = ?","suspension pour inhalation par nébuliseur").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(394l,formePharmaceutiqueDao.queryRaw("where name = ?","suspension pour injection").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(395l,formePharmaceutiqueDao.queryRaw("where name = ?","suspension pour instillation").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(396l,formePharmaceutiqueDao.queryRaw("where name = ?","suspension pour pulvérisation").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(397l,formePharmaceutiqueDao.queryRaw("where name = ?","système de diffusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(398l,formePharmaceutiqueDao.queryRaw("where name = ?","tampon imprégné(e) pour inhalation").get(0).getId(), doseDao.queryRaw("where name = ?","tampon").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(399l,formePharmaceutiqueDao.queryRaw("where name = ?","tampon imprégné(e) pour inhalation par fumigation").get(0).getId(), doseDao.queryRaw("where name = ?","tampon").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(400l,formePharmaceutiqueDao.queryRaw("where name = ?","trousse").get(0).getId(), doseDao.queryRaw("where name = ?","trousse").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(401l,formePharmaceutiqueDao.queryRaw("where name = ?","trousse et trousse et trousse pour préparation radiopharmaceutique").get(0).getId(), doseDao.queryRaw("where name = ?","trousse").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(402l,formePharmaceutiqueDao.queryRaw("where name = ?","trousse et trousse et trousse pour préparation radiopharmaceutique pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","trousse").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(403l,formePharmaceutiqueDao.queryRaw("where name = ?","trousse et trousse pour préparation radiopharmaceutique").get(0).getId(), doseDao.queryRaw("where name = ?","trousse").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(404l,formePharmaceutiqueDao.queryRaw("where name = ?","trousse et trousse pour préparation radiopharmaceutique pour injection").get(0).getId(), doseDao.queryRaw("where name = ?","trousse").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(405l,formePharmaceutiqueDao.queryRaw("where name = ?","trousse et trousse radiopharmaceutique").get(0).getId(), doseDao.queryRaw("where name = ?","trousse").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(406l,formePharmaceutiqueDao.queryRaw("where name = ?","trousse pour préparation radiopharmaceutique").get(0).getId(), doseDao.queryRaw("where name = ?","trousse").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(407l,formePharmaceutiqueDao.queryRaw("where name = ?","trousse pour préparation radiopharmaceutique et trousse pour préparation radiopharmaceutique").get(0).getId(), doseDao.queryRaw("where name = ?","trousse").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(408l,formePharmaceutiqueDao.queryRaw("where name = ?","trousse radiopharmaceutique").get(0).getId(), doseDao.queryRaw("where name = ?","trousse").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(409l,formePharmaceutiqueDao.queryRaw("where name = ?","vernis à ongles médicamenteux(se)").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(410l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé et solution(s) et granules et poudre").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(411l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé et solution(s) et granules et poudre et pommade").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(412l,formePharmaceutiqueDao.queryRaw("where name = ?","crème et solution et granules et poudre").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(413l,formePharmaceutiqueDao.queryRaw("where name = ?","crème et solution et granules et poudre et pommade").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(414l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et granules et poudre et pommade").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(415l,formePharmaceutiqueDao.queryRaw("where name = ?","compresse et solution(s) et générateur radiopharmaceutique").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(416l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé dispersible et orodispersible").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(417l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé enrobé et comprimé enrobé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(418l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé enrobé et comprimé enrobé enrobé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(419l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé enrobé et comprimé enrobé et comprimé enrobé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(420l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé enrobé et comprimé enrobé et comprimé enrobé enrobé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(421l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé et comprimé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(422l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé et comprimé et comprimé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(423l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé et comprimé pelliculé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(424l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé et gélule").get(0).getId(), doseDao.queryRaw("where name = ?","gélule").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(425l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé pelliculé et comprimé pelliculé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(426l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé pelliculé et comprimé pelliculé et comprimé pelliculé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(427l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé pelliculé et comprimé pelliculé pelliculé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(428l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé pelliculé et granulés effervescent(e)").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(429l,formePharmaceutiqueDao.queryRaw("where name = ?","crème et solution et granules et poudre unidose").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(430l,formePharmaceutiqueDao.queryRaw("where name = ?","dispositif et dispositif").get(0).getId(), doseDao.queryRaw("where name = ?","dispositif").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(431l,formePharmaceutiqueDao.queryRaw("where name = ?","dispositif et gel").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(432l,formePharmaceutiqueDao.queryRaw("where name = ?","éluat et solution").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(433l,formePharmaceutiqueDao.queryRaw("where name = ?","émulsion et solution et solution pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(434l,formePharmaceutiqueDao.queryRaw("where name = ?","gélule et gélule").get(0).getId(), doseDao.queryRaw("where name = ?","gélule").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(435l,formePharmaceutiqueDao.queryRaw("where name = ?","gélule et gélule gastro-résistant(e)").get(0).getId(), doseDao.queryRaw("where name = ?","gélule").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(436l,formePharmaceutiqueDao.queryRaw("where name = ?","gélule gastro-soluble et gélule gastro-résistant(e)").get(0).getId(), doseDao.queryRaw("where name = ?","gélule").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(437l,formePharmaceutiqueDao.queryRaw("where name = ?","granules et crème et solution en gouttes en gouttes").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(438l,formePharmaceutiqueDao.queryRaw("where name = ?","granulés et granulés pour solution buvable").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(439l,formePharmaceutiqueDao.queryRaw("where name = ?","granules et pommade et solution en gouttes en gouttes").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(440l,formePharmaceutiqueDao.queryRaw("where name = ?","granules et poudre et solution en gouttes en gouttes").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(441l,formePharmaceutiqueDao.queryRaw("where name = ?","granules et solution en gouttes en gouttes").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(442l,formePharmaceutiqueDao.queryRaw("where name = ?","granules et solution en gouttes en gouttes et crème").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(443l,formePharmaceutiqueDao.queryRaw("where name = ?","granules et solution en gouttes en gouttes et poudre").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(444l,formePharmaceutiqueDao.queryRaw("where name = ?","granulés et solvant pour suspension buvable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(445l,formePharmaceutiqueDao.queryRaw("where name = ?","lyophilisat et poudre pour préparation injectable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(446l,formePharmaceutiqueDao.queryRaw("where name = ?","lyophilisat et poudre pour usage parentéral").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(447l,formePharmaceutiqueDao.queryRaw("where name = ?","lyophilisat et solution pour préparation injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(448l,formePharmaceutiqueDao.queryRaw("where name = ?","lyophilisat et solution pour usage parentéral").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(449l,formePharmaceutiqueDao.queryRaw("where name = ?","lyophilisat et solvant pour collyre").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(450l,formePharmaceutiqueDao.queryRaw("where name = ?","microsphère et solution pour usage parentéral à libération prolongée").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(451l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre à diluer à diluer et diluant pour solution pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(452l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre à diluer à diluer et solution pour solution pour solution").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(453l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et dispersion et solvant pour solution à diluer pour dispersion pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(454l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et poudre").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(455l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et poudre pour solution buvable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(456l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et poudre pour solution injectable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(457l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solution pour préparation injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(458l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solution pour solution injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(459l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solution pour usage parentéral").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(460l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solution pour usage parentéral à diluer").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(461l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(462l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant et matrice pour matrice implantable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(463l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant et solvant pour solution injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(464l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour dispersion injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(465l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour inhalation par nébuliseur").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(466l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour préparation injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(467l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour solution").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(468l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour solution à diluer pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(469l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour solution injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(470l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour solution injectable et pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(471l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour solution injectable ou pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(472l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour solution injectable pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(473l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour solution injectable pour perfusion ou buvable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(474l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour solution pour inhalation").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(475l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour solution pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(476l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour solution pour pulvérisation").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(477l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour suspension buvable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(478l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour suspension injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(479l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour suspension injectable à libération prolongée").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(480l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour suspension pour administration intravésicale").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(481l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour suspension pour instillation").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(482l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour usage parentéral").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(483l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et suspension pour suspension injectable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(484l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour aérosol et pour usage parentéral").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(485l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour inhalation et poudre pour inhalation").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(486l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour inhalation et poudre pour inhalation pour inhalation").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(487l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour solution buvable et entéral(e)").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(488l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour solution buvable et gastro-entérale").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(489l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre pour solution injectable et pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(490l,formePharmaceutiqueDao.queryRaw("where name = ?","solution à diluer et diluant pour solution pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(491l,formePharmaceutiqueDao.queryRaw("where name = ?","solution à diluer et solvant à diluer pour solution injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(492l,formePharmaceutiqueDao.queryRaw("where name = ?","solution à diluer et solvant injectable injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(493l,formePharmaceutiqueDao.queryRaw("where name = ?","solution à diluer et solvant pour solution à diluer pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(494l,formePharmaceutiqueDao.queryRaw("where name = ?","solution buvable et comprimé pelliculé buvable pelliculé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(495l,formePharmaceutiqueDao.queryRaw("where name = ?","solution buvable et injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(496l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et émulsion et solution pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(497l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et poudre pour injection").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(498l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution buvable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(499l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution et émulsion pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(500l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(501l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution pour application").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(502l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution pour colle").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(503l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution pour dialyse péritonéale").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(504l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution pour hémodialyse et pour hémofiltration").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(505l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution pour hémodialyse pour hémofiltration").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(506l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution pour hémofiltration et pour hémodialyse").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(507l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution pour hémofiltration pour hémodialyse et pour hémodiafiltration").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(508l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution pour marquage").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(509l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(510l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et suspension pour suspension injectable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(511l,formePharmaceutiqueDao.queryRaw("where name = ?","solution injectable et buvable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(512l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour application et solution pour application").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(513l,formePharmaceutiqueDao.queryRaw("where name = ?","solution pour hémodialyse pour hémodialyse et solution pour hémodialyse pour hémodialyse").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(514l,formePharmaceutiqueDao.queryRaw("where name = ?","solvant et solution et poudre(s) pour colle").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(515l,formePharmaceutiqueDao.queryRaw("where name = ?","solvant(s) et poudre(s) pour colle").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(516l,formePharmaceutiqueDao.queryRaw("where name = ?","solvant(s) et poudre(s) pour solution injectable").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(517l,formePharmaceutiqueDao.queryRaw("where name = ?","suspension et granulés effervescent(e) pour suspension buvable").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(518l,formePharmaceutiqueDao.queryRaw("where name = ?","suspension et solvant pour usage dentaire").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(519l,formePharmaceutiqueDao.queryRaw("where name = ?","trousse et trousse et trousse pour préparation radiopharmaceutique").get(0).getId(), doseDao.queryRaw("where name = ?","trousse").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(520l,formePharmaceutiqueDao.queryRaw("where name = ?","trousse et trousse et trousse pour préparation radiopharmaceutique pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","trousse").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(521l,formePharmaceutiqueDao.queryRaw("where name = ?","trousse et trousse pour préparation radiopharmaceutique").get(0).getId(), doseDao.queryRaw("where name = ?","trousse").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(522l,formePharmaceutiqueDao.queryRaw("where name = ?","trousse et trousse pour préparation radiopharmaceutique pour injection").get(0).getId(), doseDao.queryRaw("where name = ?","trousse").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(523l,formePharmaceutiqueDao.queryRaw("where name = ?","trousse et trousse radiopharmaceutique").get(0).getId(), doseDao.queryRaw("where name = ?","trousse").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(524l,formePharmaceutiqueDao.queryRaw("where name = ?","trousse pour préparation radiopharmaceutique et trousse pour préparation radiopharmaceutique").get(0).getId(), doseDao.queryRaw("where name = ?","trousse").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(525l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé et solution(s) et granules et poudre").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(526l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé et solution(s) et granules et poudre et pommade").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(527l,formePharmaceutiqueDao.queryRaw("where name = ?","crème et solution et granules et poudre").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(528l,formePharmaceutiqueDao.queryRaw("where name = ?","crème et solution et granules et poudre et pommade").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(529l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et granules et poudre et pommade").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(530l,formePharmaceutiqueDao.queryRaw("where name = ?","compresse et solution(s) et générateur radiopharmaceutique").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(531l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé enrobé et comprimé enrobé et comprimé enrobé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(532l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé enrobé et comprimé enrobé et comprimé enrobé enrobé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(533l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé et comprimé et comprimé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(534l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé pelliculé et comprimé pelliculé et comprimé pelliculé").get(0).getId(), doseDao.queryRaw("where name = ?","comprimé").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(535l,formePharmaceutiqueDao.queryRaw("where name = ?","crème et solution et granules et poudre unidose").get(0).getId(), doseDao.queryRaw("where name = ?","granulés").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(536l,formePharmaceutiqueDao.queryRaw("where name = ?","émulsion et solution et solution pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(537l,formePharmaceutiqueDao.queryRaw("where name = ?","granules et crème et solution en gouttes en gouttes").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(538l,formePharmaceutiqueDao.queryRaw("where name = ?","granules et pommade et solution en gouttes en gouttes").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(539l,formePharmaceutiqueDao.queryRaw("where name = ?","granules et poudre et solution en gouttes en gouttes").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(540l,formePharmaceutiqueDao.queryRaw("where name = ?","granules et solution en gouttes en gouttes et crème").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(541l,formePharmaceutiqueDao.queryRaw("where name = ?","granules et solution en gouttes en gouttes et poudre").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(542l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et dispersion et solvant pour solution à diluer pour dispersion pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(543l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant et matrice pour matrice implantable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(544l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant et solvant pour solution injectable").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(545l,formePharmaceutiqueDao.queryRaw("where name = ?","poudre et solvant pour solution injectable et pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(546l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et émulsion et solution pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(547l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution et émulsion pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(548l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution pour hémodialyse et pour hémofiltration").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(549l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution pour hémofiltration et pour hémodialyse").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(550l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et solution pour hémofiltration pour hémodialyse et pour hémodiafiltration").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(551l,formePharmaceutiqueDao.queryRaw("where name = ?","solvant et solution et poudre(s) pour colle").get(0).getId(), doseDao.queryRaw("where name = ?","indéfini").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(552l,formePharmaceutiqueDao.queryRaw("where name = ?","trousse et trousse et trousse pour préparation radiopharmaceutique").get(0).getId(), doseDao.queryRaw("where name = ?","trousse").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(553l,formePharmaceutiqueDao.queryRaw("where name = ?","trousse et trousse et trousse pour préparation radiopharmaceutique pour perfusion").get(0).getId(), doseDao.queryRaw("where name = ?","trousse").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(554l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé et solution(s) et granules et poudre").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(555l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé et solution(s) et granules et poudre et pommade").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(556l,formePharmaceutiqueDao.queryRaw("where name = ?","crème et solution et granules et poudre").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(557l,formePharmaceutiqueDao.queryRaw("where name = ?","crème et solution et granules et poudre et pommade").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(558l,formePharmaceutiqueDao.queryRaw("where name = ?","solution et granules et poudre et pommade").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(559l,formePharmaceutiqueDao.queryRaw("where name = ?","crème et solution et granules et poudre unidose").get(0).getId(), doseDao.queryRaw("where name = ?","sachet").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(560l,formePharmaceutiqueDao.queryRaw("where name = ?","comprimé et solution(s) et granules et poudre et pommade").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
            associationFormeDoseDao.insert(new AssociationFormeDose(561l,formePharmaceutiqueDao.queryRaw("where name = ?","crème et solution et granules et poudre et pommade").get(0).getId(), doseDao.queryRaw("where name = ?","application").get(0).getId()));
        }

    }
}
