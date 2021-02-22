package com.pouillos.mysuivimedical.dao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.pouillos.mysuivimedical.entities.Region;

import com.pouillos.mysuivimedical.entities.Departement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DEPARTEMENT".
*/
public class DepartementDao extends AbstractDao<Departement, Long> {

    public static final String TABLENAME = "DEPARTEMENT";

    /**
     * Properties of entity Departement.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Numero = new Property(1, String.class, "numero", false, "NUMERO");
        public final static Property Nom = new Property(2, String.class, "nom", false, "NOM");
        public final static Property RegionId = new Property(3, long.class, "regionId", false, "REGION_ID");
    }

    private DaoSession daoSession;


    public DepartementDao(DaoConfig config) {
        super(config);
    }
    
    public DepartementDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DEPARTEMENT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"NUMERO\" TEXT NOT NULL ," + // 1: numero
                "\"NOM\" TEXT NOT NULL ," + // 2: nom
                "\"REGION_ID\" INTEGER NOT NULL );"); // 3: regionId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DEPARTEMENT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Departement entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getNumero());
        stmt.bindString(3, entity.getNom());
        stmt.bindLong(4, entity.getRegionId());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Departement entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getNumero());
        stmt.bindString(3, entity.getNom());
        stmt.bindLong(4, entity.getRegionId());
    }

    @Override
    protected final void attachEntity(Departement entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Departement readEntity(Cursor cursor, int offset) {
        Departement entity = new Departement( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // numero
            cursor.getString(offset + 2), // nom
            cursor.getLong(offset + 3) // regionId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Departement entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNumero(cursor.getString(offset + 1));
        entity.setNom(cursor.getString(offset + 2));
        entity.setRegionId(cursor.getLong(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Departement entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Departement entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Departement entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getRegionDao().getAllColumns());
            builder.append(" FROM DEPARTEMENT T");
            builder.append(" LEFT JOIN REGION T0 ON T.\"REGION_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Departement loadCurrentDeep(Cursor cursor, boolean lock) {
        Departement entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Region region = loadCurrentOther(daoSession.getRegionDao(), cursor, offset);
         if(region != null) {
            entity.setRegion(region);
        }

        return entity;    
    }

    public Departement loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Departement> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Departement> list = new ArrayList<Departement>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Departement> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Departement> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
