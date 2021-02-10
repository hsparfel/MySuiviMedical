package com.pouillos.mypilulier.entities;

import com.pouillos.mypilulier.activities.utils.DateUtils;

public class RdvExamen extends Rdv {

    private Examen examen;

    public RdvExamen() {
    }

    public Examen getExamen() {
        return examen;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }

    @Override
    public String toString() {
        return DateUtils.ecrireDateHeure(date)+ " - " + examen.getName();
    }
}
