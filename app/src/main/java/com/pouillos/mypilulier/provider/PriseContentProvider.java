package com.pouillos.mypilulier.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pouillos.mypilulier.dao.DaoSession;
import com.pouillos.mypilulier.dao.PriseDao;
import com.pouillos.mypilulier.MyApp;
import com.pouillos.mypilulier.dao.DaoMaster;
import com.pouillos.mypilulier.dao.DaoSession;
import com.pouillos.mypilulier.dao.PriseDao;
import com.pouillos.mypilulier.entities.Prise;

import org.greenrobot.greendao.database.Database;

import java.util.List;

public class PriseContentProvider extends ContentProvider {

    private DaoSession daoSession;
    private PriseDao priseDao;

    // FOR DATA
    public static final String AUTHORITY = "com.pouillos.mypilulier.provider";
    public static final String TABLE_NAME = Prise.class.getSimpleName();
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
        priseDao = daoSession.getPriseDao();
        return false;
    }

    /*@Nullable
    public List<Prise> listAllPrise() {
        List<Prise> listAllPrise = priseDao.loadAll();
        return listAllPrise;
    }*/

    public Cursor getCursorFromList(List<Prise> listPrise) {
        MatrixCursor cursor = new MatrixCursor(
                new String[] {"_id", "date", "dateString", "effectue", "medicament", "dose", "qteDose", }
        );
        for ( Prise prise : listPrise ) {
            cursor.newRow()
                    .add("_id", prise.getId())
                    .add("date", prise.getDate())
                    .add("dateString", prise.getDateString())
                    .add("effectue", prise.getEffectue())
                    .add("medicament", prise.getMedicament())
                    .add("dose", prise.getDose())
                    .add("qteDose", prise.getQteDose());
        }
        return cursor;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (selection == null) {
            Cursor cursor = getCursorFromList(priseDao.loadAll());
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
