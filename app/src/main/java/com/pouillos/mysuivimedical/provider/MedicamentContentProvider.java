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
import com.pouillos.mysuivimedical.dao.MedicamentDao;
import com.pouillos.mysuivimedical.entities.Medicament;

import org.greenrobot.greendao.database.Database;

import java.util.List;

public class MedicamentContentProvider extends ContentProvider {

    private DaoSession daoSession;
    private MedicamentDao medicamentDao;

    // FOR DATA
    public static final String AUTHORITY = "com.pouillos.mypilulier.provider";
    public static final String TABLE_NAME = Medicament.class.getSimpleName();
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
        medicamentDao = daoSession.getMedicamentDao();
        return false;
    }

    /*@Nullable
    public List<Medicament> listAllMedicament() {
        List<Medicament> listAllMedicament = medicamentDao.loadAll();
        return listAllMedicament;
    }*/

    public Cursor getCursorFromList(List<Medicament> listMedicament) {
        MatrixCursor cursor = new MatrixCursor(
                new String[] {"_id", "codeCIS", "denomination", "denominationShort", "formePharmaceutique", "titulaire"}
        );
        for ( Medicament medicament : listMedicament ) {
            cursor.newRow()
                    .add("_id", medicament.getId())
                    .add("codeCIS", medicament.getCodeCIS())
                    .add("denomination", medicament.getDenomination())
                    .add("denominationShort", medicament.getDenominationShort())
                    .add("formePharmaceutique", medicament.getFormePharmaceutique())
                    .add("titulaire", medicament.getTitulaire());
        }
        return cursor;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (selection == null) {
            Cursor cursor = getCursorFromList(medicamentDao.loadAll());
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
