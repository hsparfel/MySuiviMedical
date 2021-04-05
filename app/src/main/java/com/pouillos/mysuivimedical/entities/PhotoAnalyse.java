package com.pouillos.mysuivimedical.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Base64;
import java.util.Date;
import org.greenrobot.greendao.DaoException;
import com.pouillos.mysuivimedical.dao.DaoSession;
import com.pouillos.mysuivimedical.dao.AnalyseDao;
import org.greenrobot.greendao.annotation.NotNull;
import com.pouillos.mysuivimedical.dao.PhotoAnalyseDao;
import com.pouillos.mysuivimedical.interfaces.BasicUtils;
import com.pouillos.mysuivimedical.utils.DateUtils;
import com.pouillos.mysuivimedical.dao.RdvAnalyseDao;

@Entity
public class PhotoAnalyse implements Comparable<PhotoAnalyse>{

    @Id
    private Long id;

    private String path;

    private long analyseId;
    @ToOne(joinProperty = "analyseId")
    private Analyse analyse;

    private long rdvAnalyseId;
    @ToOne(joinProperty = "rdvAnalyseId")
    private RdvAnalyse rdvAnalyse;

    private Date date;
    private String dateString;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 580850058)
    private transient PhotoAnalyseDao myDao;


    @Generated(hash = 440316223)
    public PhotoAnalyse(Long id, String path, long analyseId, long rdvAnalyseId, Date date,
            String dateString) {
        this.id = id;
        this.path = path;
        this.analyseId = analyseId;
        this.rdvAnalyseId = rdvAnalyseId;
        this.date = date;
        this.dateString = dateString;
    }

    @Generated(hash = 1363960232)
    public PhotoAnalyse() {
    }

    @Generated(hash = 1192313522)
    private transient Long analyse__resolvedKey;

    @Generated(hash = 749204158)
    private transient Long rdvAnalyse__resolvedKey;

    
    @Override
    public int compareTo(PhotoAnalyse o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public String toString() {
        return this.getAnalyse().getName()+" - "+ DateUtils.ecrireDateHeure(this.getDate());
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

    public long getAnalyseId() {
        return this.analyseId;
    }

    public void setAnalyseId(long analyseId) {
        this.analyseId = analyseId;
    }

    public long getRdvAnalyseId() {
        return this.rdvAnalyseId;
    }

    public void setRdvAnalyseId(long rdvAnalyseId) {
        this.rdvAnalyseId = rdvAnalyseId;
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

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 929931609)
    public RdvAnalyse getRdvAnalyse() {
        long __key = this.rdvAnalyseId;
        if (rdvAnalyse__resolvedKey == null || !rdvAnalyse__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RdvAnalyseDao targetDao = daoSession.getRdvAnalyseDao();
            RdvAnalyse rdvAnalyseNew = targetDao.load(__key);
            synchronized (this) {
                rdvAnalyse = rdvAnalyseNew;
                rdvAnalyse__resolvedKey = __key;
            }
        }
        return rdvAnalyse;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1996209217)
    public void setRdvAnalyse(@NotNull RdvAnalyse rdvAnalyse) {
        if (rdvAnalyse == null) {
            throw new DaoException(
                    "To-one property 'rdvAnalyseId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.rdvAnalyse = rdvAnalyse;
            rdvAnalyseId = rdvAnalyse.getId();
            rdvAnalyse__resolvedKey = rdvAnalyseId;
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
    @Generated(hash = 1115987086)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPhotoAnalyseDao() : null;
    }

}
