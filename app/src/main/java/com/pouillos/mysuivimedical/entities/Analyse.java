package com.pouillos.mysuivimedical.entities;

import com.orm.SugarRecord;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Analyse implements Comparable<Analyse> {

    @Id
    private Long id;

    private String name;

    @Generated(hash = 1941805175)
    public Analyse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 1259833663)
    public Analyse() {
    }

    @Override
    public int compareTo(Analyse o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
