package com.pouillos.mysuivimedical.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.pouillos.mysuivimedical.entities.Profil;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PROFIL".
*/
public class ProfilDao extends AbstractDao<Profil, Long> {

    public static final String TABLENAME = "PROFIL";

    /**
     * Properties of entity Profil.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Poids = new Property(1, float.class, "poids", false, "POIDS");
        public final static Property Taille = new Property(2, int.class, "taille", false, "TAILLE");
        public final static Property Imc = new Property(3, float.class, "imc", false, "IMC");
        public final static Property Date = new Property(4, java.util.Date.class, "date", false, "DATE");
        public final static Property DateString = new Property(5, String.class, "dateString", false, "DATE_STRING");
    }


    public ProfilDao(DaoConfig config) {
        super(config);
    }
    
    public ProfilDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PROFIL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"POIDS\" REAL NOT NULL ," + // 1: poids
                "\"TAILLE\" INTEGER NOT NULL ," + // 2: taille
                "\"IMC\" REAL NOT NULL ," + // 3: imc
                "\"DATE\" INTEGER," + // 4: date
                "\"DATE_STRING\" TEXT);"); // 5: dateString
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PROFIL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Profil entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindDouble(2, entity.getPoids());
        stmt.bindLong(3, entity.getTaille());
        stmt.bindDouble(4, entity.getImc());
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            stmt.bindLong(5, date.getTime());
        }
 
        String dateString = entity.getDateString();
        if (dateString != null) {
            stmt.bindString(6, dateString);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Profil entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindDouble(2, entity.getPoids());
        stmt.bindLong(3, entity.getTaille());
        stmt.bindDouble(4, entity.getImc());
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            stmt.bindLong(5, date.getTime());
        }
 
        String dateString = entity.getDateString();
        if (dateString != null) {
            stmt.bindString(6, dateString);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Profil readEntity(Cursor cursor, int offset) {
        Profil entity = new Profil( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getFloat(offset + 1), // poids
            cursor.getInt(offset + 2), // taille
            cursor.getFloat(offset + 3), // imc
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)), // date
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // dateString
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Profil entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPoids(cursor.getFloat(offset + 1));
        entity.setTaille(cursor.getInt(offset + 2));
        entity.setImc(cursor.getFloat(offset + 3));
        entity.setDate(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
        entity.setDateString(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Profil entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Profil entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Profil entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
