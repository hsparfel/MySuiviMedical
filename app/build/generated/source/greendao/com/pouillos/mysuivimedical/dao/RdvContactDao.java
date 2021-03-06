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

import com.pouillos.mysuivimedical.entities.Contact;

import com.pouillos.mysuivimedical.entities.RdvContact;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "RDV_CONTACT".
*/
public class RdvContactDao extends AbstractDao<RdvContact, Long> {

    public static final String TABLENAME = "RDV_CONTACT";

    /**
     * Properties of entity RdvContact.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Detail = new Property(1, String.class, "detail", false, "DETAIL");
        public final static Property Date = new Property(2, java.util.Date.class, "date", false, "DATE");
        public final static Property DateString = new Property(3, String.class, "dateString", false, "DATE_STRING");
        public final static Property ContactId = new Property(4, long.class, "contactId", false, "CONTACT_ID");
    }

    private DaoSession daoSession;


    public RdvContactDao(DaoConfig config) {
        super(config);
    }
    
    public RdvContactDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"RDV_CONTACT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"DETAIL\" TEXT," + // 1: detail
                "\"DATE\" INTEGER," + // 2: date
                "\"DATE_STRING\" TEXT," + // 3: dateString
                "\"CONTACT_ID\" INTEGER NOT NULL );"); // 4: contactId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"RDV_CONTACT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, RdvContact entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String detail = entity.getDetail();
        if (detail != null) {
            stmt.bindString(2, detail);
        }
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            stmt.bindLong(3, date.getTime());
        }
 
        String dateString = entity.getDateString();
        if (dateString != null) {
            stmt.bindString(4, dateString);
        }
        stmt.bindLong(5, entity.getContactId());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, RdvContact entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String detail = entity.getDetail();
        if (detail != null) {
            stmt.bindString(2, detail);
        }
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            stmt.bindLong(3, date.getTime());
        }
 
        String dateString = entity.getDateString();
        if (dateString != null) {
            stmt.bindString(4, dateString);
        }
        stmt.bindLong(5, entity.getContactId());
    }

    @Override
    protected final void attachEntity(RdvContact entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public RdvContact readEntity(Cursor cursor, int offset) {
        RdvContact entity = new RdvContact( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // detail
            cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)), // date
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // dateString
            cursor.getLong(offset + 4) // contactId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, RdvContact entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDetail(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDate(cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)));
        entity.setDateString(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setContactId(cursor.getLong(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(RdvContact entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(RdvContact entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(RdvContact entity) {
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
            SqlUtils.appendColumns(builder, "T0", daoSession.getContactDao().getAllColumns());
            builder.append(" FROM RDV_CONTACT T");
            builder.append(" LEFT JOIN CONTACT T0 ON T.\"CONTACT_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected RdvContact loadCurrentDeep(Cursor cursor, boolean lock) {
        RdvContact entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Contact contact = loadCurrentOther(daoSession.getContactDao(), cursor, offset);
         if(contact != null) {
            entity.setContact(contact);
        }

        return entity;    
    }

    public RdvContact loadDeep(Long key) {
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
    public List<RdvContact> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<RdvContact> list = new ArrayList<RdvContact>(count);
        
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
    
    protected List<RdvContact> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<RdvContact> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
