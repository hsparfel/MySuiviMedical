package com.pouillos.mysuivimedical.entities;

import com.orm.SugarRecord;
import com.pouillos.mysuivimedical.activities.utils.DateUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Profil implements Comparable<Profil> {

    @Id
    private Long id;

    private float poids;
    private int taille;
    private float imc;
    private Date date;
    private String dateString;



    @Generated(hash = 1229466953)
    public Profil(Long id, float poids, int taille, float imc, Date date, String dateString) {
        this.id = id;
        this.poids = poids;
        this.taille = taille;
        this.imc = imc;
        this.date = date;
        this.dateString = dateString;
    }

    @Generated(hash = 655688928)
    public Profil() {
    }



    public Float getImcArrondi() {
        return ((float) Math.round(imc*10))/10;
    }

    @Override
    public int compareTo(Profil o) {
        return o.date.compareTo(this.date);
    }

    public float calculerImc() {
        float poids = this.getPoids();
        float taille = ((float) this.getTaille())/100;
        float tailleCarre = taille*taille;
        float imc = ((float) Math.round(poids/tailleCarre*10))/10;
        return imc;
    }

    @Override
    public String toString() {
        return DateUtils.ecrireDate(date)+ " - " + poids + " kgs / " + taille + " cm";
    }

    protected static float floatArrondi(float number, int decimalPlace) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getPoids() {
        return this.poids;
    }

    public void setPoids(float poids) {
        this.poids = poids;
    }

    public int getTaille() {
        return this.taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public float getImc() {
        return this.imc;
    }

    public void setImc(float imc) {
        this.imc = imc;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateString() {
        return this.dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

}
