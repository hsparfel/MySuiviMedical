package com.pouillos.mypilulier.entities;

import com.orm.SugarRecord;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AlarmRdv implements Comparable<AlarmRdv> {

    @Id
    private Long id;

    private String classe;
    private Date date;
    private String dateString;
    private String detail;
    private String echeance;
    private int requestCode;


    @Generated(hash = 2052388542)
    public AlarmRdv(Long id, String classe, Date date, String dateString,
            String detail, String echeance, int requestCode) {
        this.id = id;
        this.classe = classe;
        this.date = date;
        this.dateString = dateString;
        this.detail = detail;
        this.echeance = echeance;
        this.requestCode = requestCode;
    }


    @Generated(hash = 196036216)
    public AlarmRdv() {
    }


    @Override
    public String toString() {
        return "Alarm{" +
                "classe='" + classe + '\'' +
                ", date=" + date +
                ", dateString='" + dateString + '\'' +
                ", detail='" + detail + '\'' +
                ", echeance='" + echeance + '\'' +
                ", requestCode='" + requestCode + '\'' +
                '}';
    }


    @Override
    public int compareTo(AlarmRdv o) {
        return this.getId().compareTo(o.getId());
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getClasse() {
        return this.classe;
    }


    public void setClasse(String classe) {
        this.classe = classe;
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


    public String getDetail() {
        return this.detail;
    }


    public void setDetail(String detail) {
        this.detail = detail;
    }


    public String getEcheance() {
        return this.echeance;
    }


    public void setEcheance(String echeance) {
        this.echeance = echeance;
    }


    public int getRequestCode() {
        return this.requestCode;
    }


    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }
}
