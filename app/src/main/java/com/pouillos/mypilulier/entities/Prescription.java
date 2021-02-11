package com.pouillos.mypilulier.entities;

import com.orm.SugarRecord;
import com.pouillos.mypilulier.enumeration.Frequence;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.io.Serializable;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.pouillos.mypilulier.dao.DaoSession;
import com.pouillos.mypilulier.dao.MedicamentDao;
import org.greenrobot.greendao.annotation.NotNull;
import com.pouillos.mypilulier.dao.PrescriptionDao;

@Entity
public class Prescription implements Comparable<Prescription>  {

    @Id
    private Long id;

    private long medicamentId;
    @ToOne(joinProperty = "medicamentId")
    private Medicament medicament;

    @Convert(converter = FrequenceConverter.class, columnType = String.class)
    private Frequence frequence;

    int dureeOption;
    int frequenceOption;
    Date dateFin;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 43991223)
    private transient PrescriptionDao myDao;


    @Generated(hash = 1275334871)
    public Prescription(Long id, long medicamentId, Frequence frequence, int dureeOption,
            int frequenceOption, Date dateFin) {
        this.id = id;
        this.medicamentId = medicamentId;
        this.frequence = frequence;
        this.dureeOption = dureeOption;
        this.frequenceOption = frequenceOption;
        this.dateFin = dateFin;
    }

    @Generated(hash = 235982998)
    public Prescription() {
    }

    @Generated(hash = 191865126)
    private transient Long medicament__resolvedKey;


    @Override
    public int compareTo(Prescription o) {
        return this.getId().compareTo(o.getId());
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getMedicamentId() {
        return this.medicamentId;
    }

    public void setMedicamentId(long medicamentId) {
        this.medicamentId = medicamentId;
    }

    public Frequence getFrequence() {
        return this.frequence;
    }

    public void setFrequence(Frequence frequence) {
        this.frequence = frequence;
    }

    public int getDureeOption() {
        return this.dureeOption;
    }

    public void setDureeOption(int dureeOption) {
        this.dureeOption = dureeOption;
    }

    public int getFrequenceOption() {
        return this.frequenceOption;
    }

    public void setFrequenceOption(int frequenceOption) {
        this.frequenceOption = frequenceOption;
    }

    public Date getDateFin() {
        return this.dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1251693302)
    public Medicament getMedicament() {
        long __key = this.medicamentId;
        if (medicament__resolvedKey == null || !medicament__resolvedKey.equals(__key)) {
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
    @Generated(hash = 524467190)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPrescriptionDao() : null;
    }

    public static class FrequenceConverter implements PropertyConverter<Frequence, String> {
        @Override
        public Frequence convertToEntityProperty(String databaseValue) {
            return Frequence.valueOf(databaseValue);
        }

        @Override
        public String convertToDatabaseValue(Frequence entityProperty) {
            return entityProperty.name();
        }
    }
}
