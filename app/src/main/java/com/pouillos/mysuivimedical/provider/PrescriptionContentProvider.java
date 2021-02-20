package com.pouillos.mysuivimedical.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pouillos.mysuivimedical.MyApp;
import com.pouillos.mysuivimedical.dao.DaoMaster;
import com.pouillos.mysuivimedical.dao.DaoSession;
import com.pouillos.mysuivimedical.dao.PrescriptionDao;
import com.pouillos.mysuivimedical.entities.Prescription;

import org.greenrobot.greendao.database.Database;

import java.util.List;

public class PrescriptionContentProvider extends ContentProvider {

    private DaoSession daoSession;
    private PrescriptionDao prescriptionDao;

    // FOR DATA
    public static final String AUTHORITY = "com.pouillos.mypilulier.provider";
    public static final String TABLE_NAME = Prescription.class.getSimpleName();
    public static final Uri URI_ITEM = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    public void initialiserDao() {
        //Base pendant dev
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(MyApp.getInstance(), "my_pilulier_db");
        //Base de prod
        //AppOpenHelper helper = new AppOpenHelper(this, "my_pilulier_db", null);
        Database db = helper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    @Override
    public boolean onCreate() {
        initialiserDao();
        prescriptionDao = daoSession.getPrescriptionDao();
        return false;
    }

    /*@Nullable
    public List<Prescription> listAllPrescription() {
        List<Prescription> listAllPrescription = prescriptionDao.loadAll();
        return listAllPrescription;
    }*/

    public Cursor getCursorFromList(List<Prescription> listPrescription) {
        MatrixCursor cursor = new MatrixCursor(
                new String[] {"_id", "qte", "medicament", "frequence", "dateFin", "dateFinString"}
        );
        for ( Prescription prescription : listPrescription ) {
            cursor.newRow()
                    .add("_id", prescription.getId())
                    .add("qte", prescription.getQte())
                    .add("medicament", prescription.getMedicament())
                    .add("frequence", prescription.getFrequence())
                    .add("dateFin", prescription.getDateFin())
                    .add("dateFinString", prescription.getDateFinString());
        }
        return cursor;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (selection == null) {
            Cursor cursor = getCursorFromList(prescriptionDao.loadAll());
            return cursor;
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
