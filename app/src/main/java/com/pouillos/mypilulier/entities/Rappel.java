package com.pouillos.mypilulier.entities;

import com.orm.SugarRecord;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.pouillos.mypilulier.dao.DaoSession;
import com.pouillos.mypilulier.dao.DoseDao;
import org.greenrobot.greendao.annotation.NotNull;
import com.pouillos.mypilulier.dao.PrescriptionDao;
import com.pouillos.mypilulier.dao.RappelDao;

@Entity
public class Rappel implements Comparable<Rappel> {

    @Id
    private Long id;

    private long prescriptionId;
    @ToOne(joinProperty = "prescriptionId")
    private Prescription prescription;

    private Double quantiteDose;

    private long doseId;
    @ToOne(joinProperty = "doseId")
    private Dose dose;

    private String heure;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1771451432)
    private transient RappelDao myDao;


    @Generated(hash = 1103703048)
    public Rappel(Long id, long prescriptionId, Double quantiteDose, long doseId,
            String heure) {
        this.id = id;
        this.prescriptionId = prescriptionId;
        this.quantiteDose = quantiteDose;
        this.doseId = doseId;
        this.heure = heure;
    }


    @Generated(hash = 1267220055)
    public Rappel() {
    }


    @Generated(hash = 2029478447)
    private transient Long prescription__resolvedKey;

    @Generated(hash = 56099619)
    private transient Long dose__resolvedKey;


    @Override
    public int compareTo(Rappel o) {
        return this.getId().compareTo(o.getId());
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public long getPrescriptionId() {
        return this.prescriptionId;
    }


    public void setPrescriptionId(long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }


    public Double getQuantiteDose() {
        return this.quantiteDose;
    }


    public void setQuantiteDose(Double quantiteDose) {
        this.quantiteDose = quantiteDose;
    }


    public long getDoseId() {
        return this.doseId;
    }


    public void setDoseId(long doseId) {
        this.doseId = doseId;
    }


    public String getHeure() {
        return this.heure;
    }


    public void setHeure(String heure) {
        this.heure = heure;
    }


    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1741219798)
    public Prescription getPrescription() {
        long __key = this.prescriptionId;
        if (prescription__resolvedKey == null
                || !prescription__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PrescriptionDao targetDao = daoSession.getPrescriptionDao();
            Prescription prescriptionNew = targetDao.load(__key);
            synchronized (this) {
                prescription = prescriptionNew;
                prescription__resolvedKey = __key;
            }
        }
        return prescription;
    }


    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1701094736)
    public void setPrescription(@NotNull Prescription prescription) {
        if (prescription == null) {
            throw new DaoException(
                    "To-one property 'prescriptionId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.prescription = prescription;
            prescriptionId = prescription.getId();
            prescription__resolvedKey = prescriptionId;
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
            throw new DaoException(
                    "To-one property 'doseId' has not-null constraint; cannot set to-one to null");
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
    @Generated(hash = 1524454231)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRappelDao() : null;
    }

}
