package com.pouillos.mypilulier.entities;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;

public class Rdv extends SugarRecord implements Serializable, Comparable<Rdv> {

    protected String note;
    protected Utilisateur utilisateur;
    protected Date date;

    public Rdv() {
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(Rdv o) {

        return this.getDate().compareTo(o.getDate());

    }

}
