package com.pouillos.mysuivimedical.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ImportMedicament implements Comparable<ImportMedicament> {

    @Id
    private Long id;


    private String path;

    private boolean importCompleted;
    private String dateDebut;
    private String dateFin;
    private int nbLigneLue;
    private int nbImportEffectue;
    private int nbImportIgnore;



    



    @Generated(hash = 2024602717)
    public ImportMedicament() {
    }






    @Generated(hash = 552367349)
    public ImportMedicament(Long id, String path, boolean importCompleted,
            String dateDebut, String dateFin, int nbLigneLue, int nbImportEffectue,
            int nbImportIgnore) {
        this.id = id;
        this.path = path;
        this.importCompleted = importCompleted;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbLigneLue = nbLigneLue;
        this.nbImportEffectue = nbImportEffectue;
        this.nbImportIgnore = nbImportIgnore;
    }






    @Override
    public int compareTo(ImportMedicament o) {
        return this.getId().compareTo(o.getId());
    }



    public Long getId() {
        return this.id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public String getPath() {
        return this.path;
    }



    public void setPath(String path) {
        this.path = path;
    }



    public boolean getImportCompleted() {
        return this.importCompleted;
    }



    public void setImportCompleted(boolean importCompleted) {
        this.importCompleted = importCompleted;
    }



    public String getDateDebut() {
        return this.dateDebut;
    }



    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }



    public String getDateFin() {
        return this.dateFin;
    }



    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }



    public int getNbLigneLue() {
        return this.nbLigneLue;
    }



    public void setNbLigneLue(int nbLigneLue) {
        this.nbLigneLue = nbLigneLue;
    }



    public int getNbImportEffectue() {
        return this.nbImportEffectue;
    }



    public void setNbImportEffectue(int nbImportEffectue) {
        this.nbImportEffectue = nbImportEffectue;
    }



    public int getNbImportIgnore() {
        return this.nbImportIgnore;
    }



    public void setNbImportIgnore(int nbImportIgnore) {
        this.nbImportIgnore = nbImportIgnore;
    }
}
