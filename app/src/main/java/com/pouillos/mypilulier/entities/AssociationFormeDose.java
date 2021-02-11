package com.pouillos.mypilulier.entities;

import com.orm.SugarRecord;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.pouillos.mypilulier.dao.DaoSession;
import com.pouillos.mypilulier.dao.DoseDao;
import org.greenrobot.greendao.annotation.NotNull;
import com.pouillos.mypilulier.dao.FormePharmaceutiqueDao;
import com.pouillos.mypilulier.dao.AssociationFormeDoseDao;

@Entity
public class AssociationFormeDose implements Comparable<AssociationFormeDose>{

    @Id
    private Long id;

    private long formePharmaceutiqueId;
    @ToOne(joinProperty = "formePharmaceutiqueId")
    private FormePharmaceutique formePharmaceutique;

    private long doseId;
    @ToOne(joinProperty = "doseId")
    private Dose dose;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 772950794)
    private transient AssociationFormeDoseDao myDao;


    @Generated(hash = 1394952581)
    public AssociationFormeDose(Long id, long formePharmaceutiqueId, long doseId) {
        this.id = id;
        this.formePharmaceutiqueId = formePharmaceutiqueId;
        this.doseId = doseId;
    }

    @Generated(hash = 67477949)
    public AssociationFormeDose() {
    }

    @Generated(hash = 17303760)
    private transient Long formePharmaceutique__resolvedKey;

    @Generated(hash = 56099619)
    private transient Long dose__resolvedKey;


    @Override
    public int compareTo(AssociationFormeDose o) {
        return this.getId().compareTo(o.getId());
    }

    public boolean isExistante() {
        boolean bool=false;
        /*List<AssociationFormeDose> list = AssociationFormeDose.find(AssociationFormeDose.class,"formePharmaceutique = ? and dose = ?", this.formePharmaceutique.getId().toString(), this.dose.getId().toString());

        if (list.size() > 0) {
            bool = true;
        }*/
        return bool;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getFormePharmaceutiqueId() {
        return this.formePharmaceutiqueId;
    }

    public void setFormePharmaceutiqueId(long formePharmaceutiqueId) {
        this.formePharmaceutiqueId = formePharmaceutiqueId;
    }

    public long getDoseId() {
        return this.doseId;
    }

    public void setDoseId(long doseId) {
        this.doseId = doseId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1925681407)
    public FormePharmaceutique getFormePharmaceutique() {
        long __key = this.formePharmaceutiqueId;
        if (formePharmaceutique__resolvedKey == null || !formePharmaceutique__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FormePharmaceutiqueDao targetDao = daoSession.getFormePharmaceutiqueDao();
            FormePharmaceutique formePharmaceutiqueNew = targetDao.load(__key);
            synchronized (this) {
                formePharmaceutique = formePharmaceutiqueNew;
                formePharmaceutique__resolvedKey = __key;
            }
        }
        return formePharmaceutique;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 434609819)
    public void setFormePharmaceutique(@NotNull FormePharmaceutique formePharmaceutique) {
        if (formePharmaceutique == null) {
            throw new DaoException("To-one property 'formePharmaceutiqueId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.formePharmaceutique = formePharmaceutique;
            formePharmaceutiqueId = formePharmaceutique.getId();
            formePharmaceutique__resolvedKey = formePharmaceutiqueId;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2052519379)
    public Dose getDose() {
        long __key = this.doseId;
        if (dose__resolvedKey == null || !dose__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DoseDao targetDao = daoSession.getDoseDao();
            Dose doseNew = targetDao.load(__key);
            synchronized (this) {
                dose = doseNew;
                dose__resolvedKey = __key;
            }
        }
        return dose;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1867735885)
    public void setDose(@NotNull Dose dose) {
        if (dose == null) {
            throw new DaoException("To-one property 'doseId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.dose = dose;
            doseId = dose.getId();
            dose__resolvedKey = doseId;
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
    @Generated(hash = 1066961465)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAssociationFormeDoseDao() : null;
    }
}
