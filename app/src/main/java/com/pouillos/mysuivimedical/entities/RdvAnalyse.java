package com.pouillos.mysuivimedical.entities;

import com.orm.SugarRecord;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.pouillos.mysuivimedical.dao.DaoSession;
import com.pouillos.mysuivimedical.dao.AnalyseDao;
import org.greenrobot.greendao.annotation.NotNull;
import com.pouillos.mysuivimedical.dao.RdvAnalyseDao;
import com.pouillos.mysuivimedical.utils.DateUtils;

@Entity
public class RdvAnalyse implements Comparable<RdvAnalyse> {

    @Id
    private Long id;

    protected String detail;
    protected Date date;
    protected String dateString;

    private long analyseId;
    @ToOne(joinProperty = "analyseId")
    private Analyse analyse;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 2095615785)
    private transient RdvAnalyseDao myDao;

    @Generated(hash = 93709023)
    public RdvAnalyse(Long id, String detail, Date date, String dateString,
            long analyseId) {
        this.id = id;
        this.detail = detail;
        this.date = date;
        this.dateString = dateString;
        this.analyseId = analyseId;
    }

    @Generated(hash = 617013843)
    public RdvAnalyse() {
    }

    @Generated(hash = 1192313522)
    private transient Long analyse__resolvedKey;

    @Override
    public int compareTo(RdvAnalyse o) {
        return this.getDate().compareTo(o.getDate());
    }

    @Override
    public String toString() {
        return this.getAnalyse().getName() + " - " + DateUtils.ecrireDateHeure(date);
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

    public String getDateString() {
        return this.dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public long getAnalyseId() {
        return this.analyseId;
    }

    public void setAnalyseId(long analyseId) {
        this.analyseId = analyseId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 356164801)
    public Analyse getAnalyse() {
        long __key = this.analyseId;
        if (analyse__resolvedKey == null || !analyse__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AnalyseDao targetDao = daoSession.getAnalyseDao();
            Analyse analyseNew = targetDao.load(__key);
            synchronized (this) {
                analyse = analyseNew;
                analyse__resolvedKey = __key;
            }
        }
        return analyse;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 197174584)
    public void setAnalyse(@NotNull Analyse analyse) {
        if (analyse == null) {
            throw new DaoException(
                    "To-one property 'analyseId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.analyse = analyse;
            analyseId = analyse.getId();
            analyse__resolvedKey = analyseId;
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
    @Generated(hash = 522213854)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRdvAnalyseDao() : null;
    }
}
