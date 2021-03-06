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

import com.pouillos.mysuivimedical.entities.Departement;
import com.pouillos.mysuivimedical.entities.Profession;
import com.pouillos.mysuivimedical.entities.Region;
import com.pouillos.mysuivimedical.entities.SavoirFaire;

import com.pouillos.mysuivimedical.entities.Contact;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CONTACT".
*/
public class ContactDao extends AbstractDao<Contact, Long> {

    public static final String TABLENAME = "CONTACT";

    /**
     * Properties of entity Contact.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property IdPP = new Property(1, String.class, "idPP", false, "ID_PP");
        public final static Property CodeCivilite = new Property(2, String.class, "codeCivilite", false, "CODE_CIVILITE");
        public final static Property Nom = new Property(3, String.class, "nom", false, "NOM");
        public final static Property Prenom = new Property(4, String.class, "prenom", false, "PRENOM");
        public final static Property ProfessionId = new Property(5, long.class, "professionId", false, "PROFESSION_ID");
        public final static Property SavoirFaireId = new Property(6, long.class, "savoirFaireId", false, "SAVOIR_FAIRE_ID");
        public final static Property RaisonSocial = new Property(7, String.class, "raisonSocial", false, "RAISON_SOCIAL");
        public final static Property Complement = new Property(8, String.class, "complement", false, "COMPLEMENT");
        public final static Property Adresse = new Property(9, String.class, "adresse", false, "ADRESSE");
        public final static Property Cp = new Property(10, String.class, "cp", false, "CP");
        public final static Property Ville = new Property(11, String.class, "ville", false, "VILLE");
        public final static Property Telephone = new Property(12, String.class, "telephone", false, "TELEPHONE");
        public final static Property Fax = new Property(13, String.class, "fax", false, "FAX");
        public final static Property Email = new Property(14, String.class, "email", false, "EMAIL");
        public final static Property DepartementId = new Property(15, long.class, "departementId", false, "DEPARTEMENT_ID");
        public final static Property RegionId = new Property(16, long.class, "regionId", false, "REGION_ID");
        public final static Property Latitude = new Property(17, double.class, "latitude", false, "LATITUDE");
        public final static Property Longitude = new Property(18, double.class, "longitude", false, "LONGITUDE");
        public final static Property IsSelected = new Property(19, boolean.class, "isSelected", false, "IS_SELECTED");
    }

    private DaoSession daoSession;


    public ContactDao(DaoConfig config) {
        super(config);
    }
    
    public ContactDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CONTACT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"ID_PP\" TEXT NOT NULL ," + // 1: idPP
                "\"CODE_CIVILITE\" TEXT," + // 2: codeCivilite
                "\"NOM\" TEXT," + // 3: nom
                "\"PRENOM\" TEXT," + // 4: prenom
                "\"PROFESSION_ID\" INTEGER NOT NULL ," + // 5: professionId
                "\"SAVOIR_FAIRE_ID\" INTEGER NOT NULL ," + // 6: savoirFaireId
                "\"RAISON_SOCIAL\" TEXT," + // 7: raisonSocial
                "\"COMPLEMENT\" TEXT," + // 8: complement
                "\"ADRESSE\" TEXT," + // 9: adresse
                "\"CP\" TEXT," + // 10: cp
                "\"VILLE\" TEXT," + // 11: ville
                "\"TELEPHONE\" TEXT," + // 12: telephone
                "\"FAX\" TEXT," + // 13: fax
                "\"EMAIL\" TEXT," + // 14: email
                "\"DEPARTEMENT_ID\" INTEGER NOT NULL ," + // 15: departementId
                "\"REGION_ID\" INTEGER NOT NULL ," + // 16: regionId
                "\"LATITUDE\" REAL NOT NULL ," + // 17: latitude
                "\"LONGITUDE\" REAL NOT NULL ," + // 18: longitude
                "\"IS_SELECTED\" INTEGER NOT NULL );"); // 19: isSelected
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CONTACT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Contact entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getIdPP());
 
        String codeCivilite = entity.getCodeCivilite();
        if (codeCivilite != null) {
            stmt.bindString(3, codeCivilite);
        }
 
        String nom = entity.getNom();
        if (nom != null) {
            stmt.bindString(4, nom);
        }
 
        String prenom = entity.getPrenom();
        if (prenom != null) {
            stmt.bindString(5, prenom);
        }
        stmt.bindLong(6, entity.getProfessionId());
        stmt.bindLong(7, entity.getSavoirFaireId());
 
        String raisonSocial = entity.getRaisonSocial();
        if (raisonSocial != null) {
            stmt.bindString(8, raisonSocial);
        }
 
        String complement = entity.getComplement();
        if (complement != null) {
            stmt.bindString(9, complement);
        }
 
        String adresse = entity.getAdresse();
        if (adresse != null) {
            stmt.bindString(10, adresse);
        }
 
        String cp = entity.getCp();
        if (cp != null) {
            stmt.bindString(11, cp);
        }
 
        String ville = entity.getVille();
        if (ville != null) {
            stmt.bindString(12, ville);
        }
 
        String telephone = entity.getTelephone();
        if (telephone != null) {
            stmt.bindString(13, telephone);
        }
 
        String fax = entity.getFax();
        if (fax != null) {
            stmt.bindString(14, fax);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(15, email);
        }
        stmt.bindLong(16, entity.getDepartementId());
        stmt.bindLong(17, entity.getRegionId());
        stmt.bindDouble(18, entity.getLatitude());
        stmt.bindDouble(19, entity.getLongitude());
        stmt.bindLong(20, entity.getIsSelected() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Contact entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getIdPP());
 
        String codeCivilite = entity.getCodeCivilite();
        if (codeCivilite != null) {
            stmt.bindString(3, codeCivilite);
        }
 
        String nom = entity.getNom();
        if (nom != null) {
            stmt.bindString(4, nom);
        }
 
        String prenom = entity.getPrenom();
        if (prenom != null) {
            stmt.bindString(5, prenom);
        }
        stmt.bindLong(6, entity.getProfessionId());
        stmt.bindLong(7, entity.getSavoirFaireId());
 
        String raisonSocial = entity.getRaisonSocial();
        if (raisonSocial != null) {
            stmt.bindString(8, raisonSocial);
        }
 
        String complement = entity.getComplement();
        if (complement != null) {
            stmt.bindString(9, complement);
        }
 
        String adresse = entity.getAdresse();
        if (adresse != null) {
            stmt.bindString(10, adresse);
        }
 
        String cp = entity.getCp();
        if (cp != null) {
            stmt.bindString(11, cp);
        }
 
        String ville = entity.getVille();
        if (ville != null) {
            stmt.bindString(12, ville);
        }
 
        String telephone = entity.getTelephone();
        if (telephone != null) {
            stmt.bindString(13, telephone);
        }
 
        String fax = entity.getFax();
        if (fax != null) {
            stmt.bindString(14, fax);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(15, email);
        }
        stmt.bindLong(16, entity.getDepartementId());
        stmt.bindLong(17, entity.getRegionId());
        stmt.bindDouble(18, entity.getLatitude());
        stmt.bindDouble(19, entity.getLongitude());
        stmt.bindLong(20, entity.getIsSelected() ? 1L: 0L);
    }

    @Override
    protected final void attachEntity(Contact entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Contact readEntity(Cursor cursor, int offset) {
        Contact entity = new Contact( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // idPP
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // codeCivilite
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // nom
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // prenom
            cursor.getLong(offset + 5), // professionId
            cursor.getLong(offset + 6), // savoirFaireId
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // raisonSocial
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // complement
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // adresse
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // cp
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // ville
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // telephone
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // fax
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // email
            cursor.getLong(offset + 15), // departementId
            cursor.getLong(offset + 16), // regionId
            cursor.getDouble(offset + 17), // latitude
            cursor.getDouble(offset + 18), // longitude
            cursor.getShort(offset + 19) != 0 // isSelected
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Contact entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIdPP(cursor.getString(offset + 1));
        entity.setCodeCivilite(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setNom(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPrenom(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setProfessionId(cursor.getLong(offset + 5));
        entity.setSavoirFaireId(cursor.getLong(offset + 6));
        entity.setRaisonSocial(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setComplement(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setAdresse(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setCp(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setVille(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setTelephone(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setFax(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setEmail(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setDepartementId(cursor.getLong(offset + 15));
        entity.setRegionId(cursor.getLong(offset + 16));
        entity.setLatitude(cursor.getDouble(offset + 17));
        entity.setLongitude(cursor.getDouble(offset + 18));
        entity.setIsSelected(cursor.getShort(offset + 19) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Contact entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Contact entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Contact entity) {
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
            SqlUtils.appendColumns(builder, "T0", daoSession.getProfessionDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getSavoirFaireDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T2", daoSession.getDepartementDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T3", daoSession.getRegionDao().getAllColumns());
            builder.append(" FROM CONTACT T");
            builder.append(" LEFT JOIN PROFESSION T0 ON T.\"PROFESSION_ID\"=T0.\"_id\"");
            builder.append(" LEFT JOIN SAVOIR_FAIRE T1 ON T.\"SAVOIR_FAIRE_ID\"=T1.\"_id\"");
            builder.append(" LEFT JOIN DEPARTEMENT T2 ON T.\"DEPARTEMENT_ID\"=T2.\"_id\"");
            builder.append(" LEFT JOIN REGION T3 ON T.\"REGION_ID\"=T3.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Contact loadCurrentDeep(Cursor cursor, boolean lock) {
        Contact entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Profession profession = loadCurrentOther(daoSession.getProfessionDao(), cursor, offset);
         if(profession != null) {
            entity.setProfession(profession);
        }
        offset += daoSession.getProfessionDao().getAllColumns().length;

        SavoirFaire savoirFaire = loadCurrentOther(daoSession.getSavoirFaireDao(), cursor, offset);
         if(savoirFaire != null) {
            entity.setSavoirFaire(savoirFaire);
        }
        offset += daoSession.getSavoirFaireDao().getAllColumns().length;

        Departement departement = loadCurrentOther(daoSession.getDepartementDao(), cursor, offset);
         if(departement != null) {
            entity.setDepartement(departement);
        }
        offset += daoSession.getDepartementDao().getAllColumns().length;

        Region region = loadCurrentOther(daoSession.getRegionDao(), cursor, offset);
         if(region != null) {
            entity.setRegion(region);
        }

        return entity;    
    }

    public Contact loadDeep(Long key) {
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
    public List<Contact> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Contact> list = new ArrayList<Contact>(count);
        
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
    
    protected List<Contact> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Contact> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
