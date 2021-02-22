package com.pouillos.mysuivimedical.entities;

import com.orm.SugarRecord;
import com.pouillos.mysuivimedical.activities.utils.DateUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Photo implements Comparable<Photo>{

    @Id
    private Long id;

    private String type;
    private String path;
    private Long itemId;
    private Date date;

    @Generated(hash = 1411041857)
    public Photo(Long id, String type, String path, Long itemId, Date date) {
        this.id = id;
        this.type = type;
        this.path = path;
        this.itemId = itemId;
        this.date = date;
    }

    @Generated(hash = 1043664727)
    public Photo() {
    }

    @Override
    public int compareTo(Photo o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", path='" + path + '\'' +
                ", itemId=" + itemId +
                ", date=" + date +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getItemId() {
        return this.itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
