package com.pouillos.mysuivimedical.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.pouillos.mysuivimedical.entities.MedicamentLight;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MEDICAMENT_LIGHT".
*/
public class MedicamentLightDao extends AbstractDao<MedicamentLight, Long> {

    public static final String TABLENAME = "MEDICAMENT_LIGHT";

    /**
     * Properties of entity MedicamentLight.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property CodeCIS = new Property(1, Long.class, "codeCIS", false, "CODE_CIS");
        public final static Property Denomination = new Property(2, String.class, "denomination", false, "DENOMINATION");
    }


    public MedicamentLightDao(DaoConfig config) {
        super(config);
    }
    
    public MedicamentLightDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MEDICAMENT_LIGHT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"CODE_CIS\" INTEGER," + // 1: codeCIS
                "\"DENOMINATION\" TEXT);"); // 2: denomination
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MEDICAMENT_LIGHT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MedicamentLight entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long codeCIS = entity.getCodeCIS();
        if (codeCIS != null) {
            stmt.bindLong(2, codeCIS);
        }
 
        String denomination = entity.getDenomination();
        if (denomination != null) {
            stmt.bindString(3, denomination);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MedicamentLight entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long codeCIS = entity.getCodeCIS();
        if (codeCIS != null) {
            stmt.bindLong(2, codeCIS);
        }
 
        String denomination = entity.getDenomination();
        if (denomination != null) {
            stmt.bindString(3, denomination);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public MedicamentLight readEntity(Cursor cursor, int offset) {
        MedicamentLight entity = new MedicamentLight( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // codeCIS
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // denomination
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MedicamentLight entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCodeCIS(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setDenomination(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(MedicamentLight entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(MedicamentLight entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(MedicamentLight entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}