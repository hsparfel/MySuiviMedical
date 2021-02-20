package com.pouillos.mysuivimedical.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.pouillos.mysuivimedical.dao.DaoSession;
import com.pouillos.mysuivimedical.dao.FormePharmaceutiqueDao;
import org.greenrobot.greendao.annotation.NotNull;
import com.pouillos.mysuivimedical.dao.MedicamentDao;

@Entity
public class Medicament implements Comparable<Medicament> {

    @Id
    private Long id;

    private Long codeCIS;
    private String denomination;
    private String denominationShort;


    private long formePharmaceutiqueId;
    @ToOne(joinProperty = "formePharmaceutiqueId")
    private FormePharmaceutique formePharmaceutique;

    private String titulaire;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 2077109059)
    private transient MedicamentDao myDao;

    @Generated(hash = 126855674)
    public Medicament(Long id, Long codeCIS, String denomination,
            String denominationShort, long formePharmaceutiqueId,
            String titulaire) {
        this.id = id;
        this.codeCIS = codeCIS;
        this.denomination = denomination;
        this.denominationShort = denominationShort;
        this.formePharmaceutiqueId = formePharmaceutiqueId;
        this.titulaire = titulaire;
    }

    @Generated(hash = 319139945)
    public Medicament() {
    }

    @Generated(hash = 17303760)
    private transient Long formePharmaceutique__resolvedKey;

    @Override
    public int compareTo(Medicament o) {
        return this.denomination.compareTo(o.denomination);
    }

    @Override
    public String toString() {
        return denomination;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodeCIS() {
        return this.codeCIS;
    }

    public void setCodeCIS(Long codeCIS) {
        this.codeCIS = codeCIS;
    }

    public String getDenomination() {
        return this.denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getDenominationShort() {
        return this.denominationShort;
    }

    public void setDenominationShort(String denominationShort) {
        this.denominationShort = denominationShort;
    }

    public long getFormePharmaceutiqueId() {
        return this.formePharmaceutiqueId;
    }

    public void setFormePharmaceutiqueId(long formePharmaceutiqueId) {
        this.formePharmaceutiqueId = formePharmaceutiqueId;
    }

    public String getTitulaire() {
        return this.titulaire;
    }

    public void setTitulaire(String titulaire) {
        this.titulaire = titulaire;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1925681407)
    public FormePharmaceutique getFormePharmaceutique() {
        long __key = this.formePharmaceutiqueId;
        if (formePharmaceutique__resolvedKey == null
                || !formePharmaceutique__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FormePharmaceutiqueDao targetDao = daoSession
                    .getFormePharmaceutiqueDao();
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
    public void setFormePharmaceutique(
            @NotNull FormePharmaceutique formePharmaceutique) {
        if (formePharmaceutique == null) {
            throw new DaoException(
                    "To-one property 'formePharmaceutiqueId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.formePharmaceutique = formePharmaceutique;
            formePharmaceutiqueId = formePharmaceutique.getId();
            formePharmaceutique__resolvedKey = formePharmaceutiqueId;
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
    @Generated(hash = 1753929491)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMedicamentDao() : null;
    }
}
