package com.pouillos.mypilulier.entities;

import com.orm.SugarRecord;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.pouillos.mypilulier.dao.DaoSession;
import com.pouillos.mypilulier.dao.DoseDao;
import org.greenrobot.greendao.annotation.NotNull;
import com.pouillos.mypilulier.dao.MedicamentDao;
import com.pouillos.mypilulier.dao.PriseDao;

@Entity
public class Prise implements Comparable<Prise>  {

    @Id
    private Long id;

    Date date;
    String dateString;
    boolean effectue;

    private long medicamentId;
    @ToOne(joinProperty = "medicamentId")
    private Medicament medicament;

    private long doseId;
    @ToOne(joinProperty = "doseId")
    private Dose dose;

    float qteDose;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1272915347)
    private transient PriseDao myDao;


    @Override
    public String toString() {
        return "Prise{" +
                "id=" + id +
                ", date=" + date +
                ", dateString='" + dateString + '\'' +
                ", effectue=" + effectue +
                ", medicamentId=" + medicamentId +
                ", medicament=" + medicament +
                ", doseId=" + doseId +
                ", dose=" + dose +
                ", qteDose=" + qteDose +
                ", daoSession=" + daoSession +
                ", myDao=" + myDao +
                ", medicament__resolvedKey=" + medicament__resolvedKey +
                ", dose__resolvedKey=" + dose__resolvedKey +
                '}';
    }

    @Generated(hash = 773588632)
    public Prise(Long id, Date date, String dateString, boolean effectue,
            long medicamentId, long doseId, float qteDose) {
        this.id = id;
        this.date = date;
        this.dateString = dateString;
        this.effectue = effectue;
        this.medicamentId = medicamentId;
        this.doseId = doseId;
        this.qteDose = qteDose;
    }








    @Generated(hash = 2099907939)
    public Prise() {
    }








    @Generated(hash = 191865126)
    private transient Long medicament__resolvedKey;

    @Generated(hash = 56099619)
    private transient Long dose__resolvedKey;








    @Override
    public int compareTo(Prise o) {
        return this.getId().compareTo(o.getId());
    }








    public Long getId() {
        return this.id;
    }








    public void setId(Long id) {
        this.id = id;
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








    public boolean getEffectue() {
        return this.effectue;
    }








    public void setEffectue(boolean effectue) {
        this.effectue = effectue;
    }








    public long getMedicamentId() {
        return this.medicamentId;
    }








    public void setMedicamentId(long medicamentId) {
        this.medicamentId = medicamentId;
    }








    public long getDoseId() {
        return this.doseId;
    }








    public void setDoseId(long doseId) {
        this.doseId = doseId;
    }








    public float getQteDose() {
        return this.qteDose;
    }








    public void setQteDose(float qteDose) {
        this.qteDose = qteDose;
    }








    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1251693302)
    public Medicament getMedicament() {
        long __key = this.medicamentId;
        if (medicament__resolvedKey == null
                || !medicament__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MedicamentDao targetDao = daoSession.getMedicamentDao();
            Medicament medicamentNew = targetDao.load(__key);
            synchronized (this) {
                medicament = medicamentNew;
                medicament__resolvedKey = __key;
            }
        }
        return medicament;
    }








    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1507442270)
    public void setMedicament(@NotNull Medicament medicament) {
        if (medicament == null) {
            throw new DaoException(
                    "To-one property 'medicamentId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.medicament = medicament;
            medicamentId = medicament.getId();
            medicament__resolvedKey = medicamentId;
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
    @Generated(hash = 1603001220)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPriseDao() : null;
    }

















}
