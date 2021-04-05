package com.pouillos.mysuivimedical.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import org.greenrobot.greendao.DaoException;
import com.pouillos.mysuivimedical.dao.DaoSession;
import com.pouillos.mysuivimedical.dao.ContactDao;
import org.greenrobot.greendao.annotation.NotNull;
import com.pouillos.mysuivimedical.dao.PhotoOrdonnanceDao;
import com.pouillos.mysuivimedical.utils.DateUtils;
import com.pouillos.mysuivimedical.dao.RdvContactDao;

@Entity
public class PhotoOrdonnance implements Comparable<PhotoOrdonnance>{

    @Id
    private Long id;

    private String path;

    private long contactId;
    @ToOne(joinProperty = "contactId")
    private Contact contact;

    private long rdvContactId;
    @ToOne(joinProperty = "rdvContactId")
    private RdvContact rdvContact;

    private Date date;
    private String dateString;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1400760445)
    private transient PhotoOrdonnanceDao myDao;



    @Generated(hash = 728830051)
    public PhotoOrdonnance(Long id, String path, long contactId, long rdvContactId, Date date,
            String dateString) {
        this.id = id;
        this.path = path;
        this.contactId = contactId;
        this.rdvContactId = rdvContactId;
        this.date = date;
        this.dateString = dateString;
    }

    @Generated(hash = 1784313719)
    public PhotoOrdonnance() {
    }

    @Generated(hash = 321829790)
    private transient Long contact__resolvedKey;

    @Generated(hash = 2050420240)
    private transient Long rdvContact__resolvedKey;



    @Override
    public int compareTo(PhotoOrdonnance o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public String toString() {
        return this.getContact().getName()+" - "+ DateUtils.ecrireDateHeure(this.getDate());
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

    public long getContactId() {
        return this.contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public long getRdvContactId() {
        return this.rdvContactId;
    }

    public void setRdvContactId(long rdvContactId) {
        this.rdvContactId = rdvContactId;
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
    @Generated(hash = 714839487)
    public Contact getContact() {
        long __key = this.contactId;
        if (contact__resolvedKey == null || !contact__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ContactDao targetDao = daoSession.getContactDao();
            Contact contactNew = targetDao.load(__key);
            synchronized (this) {
                contact = contactNew;
                contact__resolvedKey = __key;
            }
        }
        return contact;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 675168246)
    public void setContact(@NotNull Contact contact) {
        if (contact == null) {
            throw new DaoException(
                    "To-one property 'contactId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.contact = contact;
            contactId = contact.getId();
            contact__resolvedKey = contactId;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 212977022)
    public RdvContact getRdvContact() {
        long __key = this.rdvContactId;
        if (rdvContact__resolvedKey == null || !rdvContact__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RdvContactDao targetDao = daoSession.getRdvContactDao();
            RdvContact rdvContactNew = targetDao.load(__key);
            synchronized (this) {
                rdvContact = rdvContactNew;
                rdvContact__resolvedKey = __key;
            }
        }
        return rdvContact;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1836880594)
    public void setRdvContact(@NotNull RdvContact rdvContact) {
        if (rdvContact == null) {
            throw new DaoException(
                    "To-one property 'rdvContactId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.rdvContact = rdvContact;
            rdvContactId = rdvContact.getId();
            rdvContact__resolvedKey = rdvContactId;
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
    @Generated(hash = 829823800)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPhotoOrdonnanceDao() : null;
    }




}
