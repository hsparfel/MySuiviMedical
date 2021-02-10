package com.pouillos.mypilulier.entities;

import com.pouillos.mypilulier.activities.utils.DateUtils;

public class RdvAnalyse extends Rdv {

    private Analyse analyse;

    public RdvAnalyse() {
    }

    public Analyse getAnalyse() {
        return analyse;
    }

    public void setAnalyse(Analyse analyse) {
        this.analyse = analyse;
    }

    @Override
    public String toString() {
        return DateUtils.ecrireDateHeure(date)+ " - " + analyse.getName();
    }
}
