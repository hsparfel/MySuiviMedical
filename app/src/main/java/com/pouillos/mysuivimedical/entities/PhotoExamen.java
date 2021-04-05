package com.pouillos.mysuivimedical.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import org.greenrobot.greendao.DaoException;
import com.pouillos.mysuivimedical.dao.DaoSession;
import com.pouillos.mysuivimedical.dao.ExamenDao;
import org.greenrobot.greendao.annotation.NotNull;
import com.pouillos.mysuivimedical.dao.PhotoExamenDao;
import com.pouillos.mysuivimedical.utils.DateUtils;
import com.pouillos.mysuivimedical.dao.RdvExamenDao;

@Entity
public class PhotoExamen implements Comparable<PhotoExamen>{

    @Id
    private Long id;

    private String path;

    private long examenId;
    @ToOne(joinProperty = "examenId")
    private Examen examen;

    private long rdvExamenId;
    @ToOne(joinProperty = "rdvExamenId")
    private RdvExamen rdvExamen;

    private Date date;
    private String dateString;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1376252884)
    private transient PhotoExamenDao myDao;


    @Generated(hash = 1147599356)
    public PhotoExamen(Long id, String path, long examenId, long rdvExamenId, Date date,
            String dateString) {
        this.id = id;
        this.path = path;
        this.examenId = examenId;
        this.rdvExamenId = rdvExamenId;
        this.date = date;
        this.dateString = dateString;
    }

    @Generated(hash = 1865270895)
    public PhotoExamen() {
    }

    @Generated(hash = 623276103)
    private transient Long examen__resolvedKey;

    @Generated(hash = 1478148669)
    private transient Long rdvExamen__resolvedKey;


    @Override
    public int compareTo(PhotoExamen o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public String toString() {
        return this.getExamen().getName()+" - "+ DateUtils.ecrireDateHeure(this.getDate());
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getExamenId() {
        return this.examenId;
    }

    public void setExamenId(long examenId) {
        this.examenId = examenId;
    }

    public long getRdvExamenId() {
        return this.rdvExamenId;
    }

    public void setRdvExamenId(long rdvExamenId) {
        this.rdvExamenId = rdvExamenId;
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

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 790312792)
    public Examen getExamen() {
        long __key = this.examenId;
        if (examen__resolvedKey == null || !examen__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ExamenDao targetDao = daoSession.getExamenDao();
            Examen examenNew = targetDao.load(__key);
            synchronized (this) {
                examen = examenNew;
                examen__resolvedKey = __key;
            }
        }
        return examen;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2102617041)
    public void setExamen(@NotNull Examen examen) {
        if (examen == null) {
            throw new DaoException(
                    "To-one property 'examenId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.examen = examen;
            examenId = examen.getId();
            examen__resolvedKey = examenId;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1980754745)
    public RdvExamen getRdvExamen() {
        long __key = this.rdvExamenId;
        if (rdvExamen__resolvedKey == null || !rdvExamen__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RdvExamenDao targetDao = daoSession.getRdvExamenDao();
            RdvExamen rdvExamenNew = targetDao.load(__key);
            synchronized (this) {
                rdvExamen = rdvExamenNew;
                rdvExamen__resolvedKey = __key;
            }
        }
        return rdvExamen;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1101057968)
    public void setRdvExamen(@NotNull RdvExamen rdvExamen) {
        if (rdvExamen == null) {
            throw new DaoException(
                    "To-one property 'rdvExamenId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.rdvExamen = rdvExamen;
            rdvExamenId = rdvExamen.getId();
            rdvExamen__resolvedKey = rdvExamenId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1449285872)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPhotoExamenDao() : null;
    }




}
