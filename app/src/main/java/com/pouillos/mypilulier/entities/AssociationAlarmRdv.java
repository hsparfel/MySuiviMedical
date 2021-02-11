package com.pouillos.mypilulier.entities;

import com.orm.SugarRecord;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.pouillos.mypilulier.dao.DaoSession;
import com.pouillos.mypilulier.dao.AlarmRdvDao;
import org.greenrobot.greendao.annotation.NotNull;
import com.pouillos.mypilulier.dao.AssociationAlarmRdvDao;

@Entity
public class AssociationAlarmRdv implements Comparable<AssociationAlarmRdv>{

    @Id
    private Long id;

    private long alarmRdvId;
    @ToOne(joinProperty = "alarmRdvId")
    private AlarmRdv alarmRdv;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1641888147)
    private transient AssociationAlarmRdvDao myDao;



    @Generated(hash = 251175187)
    public AssociationAlarmRdv(Long id, long alarmRdvId) {
        this.id = id;
        this.alarmRdvId = alarmRdvId;
    }



    @Generated(hash = 518250889)
    public AssociationAlarmRdv() {
    }



    @Generated(hash = 902127758)
    private transient Long alarmRdv__resolvedKey;



    @Override
    public int compareTo(AssociationAlarmRdv o) {
        return this.getId().compareTo(o.getId());
    }



    public Long getId() {
        return this.id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public long getAlarmRdvId() {
        return this.alarmRdvId;
    }



    public void setAlarmRdvId(long alarmRdvId) {
        this.alarmRdvId = alarmRdvId;
    }



    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1833269466)
    public AlarmRdv getAlarmRdv() {
        long __key = this.alarmRdvId;
        if (alarmRdv__resolvedKey == null || !alarmRdv__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AlarmRdvDao targetDao = daoSession.getAlarmRdvDao();
            AlarmRdv alarmRdvNew = targetDao.load(__key);
            synchronized (this) {
                alarmRdv = alarmRdvNew;
                alarmRdv__resolvedKey = __key;
            }
        }
        return alarmRdv;
    }



    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2073512469)
    public void setAlarmRdv(@NotNull AlarmRdv alarmRdv) {
        if (alarmRdv == null) {
            throw new DaoException(
                    "To-one property 'alarmRdvId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.alarmRdv = alarmRdv;
            alarmRdvId = alarmRdv.getId();
            alarmRdv__resolvedKey = alarmRdvId;
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
    @Generated(hash = 1076521465)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAssociationAlarmRdvDao() : null;
    }


}
