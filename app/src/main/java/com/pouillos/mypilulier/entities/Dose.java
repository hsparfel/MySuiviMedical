package com.pouillos.mypilulier.entities;

import com.orm.SugarRecord;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Dose implements Comparable<Dose>  {

    @Id
    private Long id;

    private String name;


    @Generated(hash = 930673749)
    public Dose(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    @Generated(hash = 1032199828)
    public Dose() {
    }


    @Override
    public int compareTo(Dose o) {
        return this.getId().compareTo(o.getId());
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
