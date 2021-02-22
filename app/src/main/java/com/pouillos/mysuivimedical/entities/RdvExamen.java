package com.pouillos.mysuivimedical.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.pouillos.mysuivimedical.dao.DaoSession;
import com.pouillos.mysuivimedical.dao.ExamenDao;
import org.greenrobot.greendao.annotation.NotNull;
import com.pouillos.mysuivimedical.dao.RdvExamenDao;
import com.pouillos.mysuivimedical.utils.DateUtils;

@Entity
public class RdvExamen implements Comparable<RdvExamen> {

    @Id
    private Long id;

    protected String detail;
    protected Date date;

    private long examenId;
    @ToOne(joinProperty = "examenId")
    private Examen examen;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 906815159)
    private transient RdvExamenDao myDao;

    @Generated(hash = 34201081)
    public RdvExamen(Long id, String detail, Date date, long examenId) {
        this.id = id;
        this.detail = detail;
        this.date = date;
        this.examenId = examenId;
    }

    @Generated(hash = 397757794)
    public RdvExamen() {
    }

    @Generated(hash = 623276103)
    private transient Long examen__resolvedKey;

    @Override
    public int compareTo(RdvExamen o) {
        return this.getDate().compareTo(o.getDate());
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getExamenId() {
        return this.examenId;
    }

    public void setExamenId(long examenId) {
        this.examenId = examenId;
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
    @Generated(hash = 887014926)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRdvExamenDao() : null;
    }

    @Override
    public String toString() {
        return examen + " -" + DateUtils.ecrireDateHeure(date);
    }

}
