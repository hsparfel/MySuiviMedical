package com.pouillos.mysuivimedical.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MedicamentLight implements Comparable<MedicamentLight> {

    @Id
    private Long id;

    private Long codeCIS;
    private String denomination;

    @Generated(hash = 152413031)
    public MedicamentLight(Long id, Long codeCIS, String denomination) {
        this.id = id;
        this.codeCIS = codeCIS;
        this.denomination = denomination;
    }

    @Generated(hash = 1801801039)
    public MedicamentLight() {
    }

    @Override
    public int compareTo(MedicamentLight o) {
        return this.denomination.compareTo(o.denomination);
    }

    @Override
    public String toString() {
        return denomination;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodeCIS() {
        return this.codeCIS;
    }

    public void setCodeCIS(Long codeCIS) {
        this.codeCIS = codeCIS;
    }

    public String getDenomination() {
        return this.denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

}
