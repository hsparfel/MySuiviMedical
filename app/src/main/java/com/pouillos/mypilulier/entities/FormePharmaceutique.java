package com.pouillos.mypilulier.entities;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class FormePharmaceutique implements Comparable<FormePharmaceutique> {

    @Id
    private Long id;


    private String name;




    @Generated(hash = 1593971618)
    public FormePharmaceutique() {
    }



    @Generated(hash = 747086625)
    public FormePharmaceutique(Long id, String name) {
        this.id = id;
        this.name = name;
    }



    @Override
    public int compareTo(FormePharmaceutique o) {
        return this.name.compareTo(o.name);
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
