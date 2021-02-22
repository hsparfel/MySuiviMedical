package com.pouillos.mysuivimedical.activities.add;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.mysuivimedical.R;
import com.pouillos.mysuivimedical.activities.AccueilActivity;
import com.pouillos.mysuivimedical.activities.NavDrawerActivity;
import com.pouillos.mysuivimedical.entities.Analyse;

import com.pouillos.mysuivimedical.interfaces.BasicUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;
import icepick.State;

public class AddAnalyseActivity extends NavDrawerActivity implements BasicUtils {

    @State
    Date date;

    @BindView(R.id.layoutName)
    TextInputLayout layoutName;
    @BindView(R.id.textName)
    TextInputEditText textName;

    private List<Analyse> listAnalyseBD;

    @BindView(R.id.fabSave)
    FloatingActionButton fabSave;

    @BindView(R.id.my_progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_add_analyse);
        // 6 - Configure all views
        this.configureToolBar();
        this.configureBottomView();

        ButterKnife.bind(this);

        progressBar.setVisibility(View.VISIBLE);

        AddAnalyseActivity.AsyncTaskRunnerBD runnerBD = new AddAnalyseActivity.AsyncTaskRunnerBD();
        runnerBD.execute();

        displayFabs();

        setTitle("+ Analyse");
    }

    public class AsyncTaskRunnerBD extends AsyncTask<Void, Integer, Void> {

        protected Void doInBackground(Void...voids) {
            publishProgress(0);
            publishProgress(10);
            listAnalyseBD = analyseDao.loadAll();
            Collections.sort(listAnalyseBD);
            publishProgress(100);
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }


    public void displayFabs() {
            fabSave.show();
    }


    public boolean isExistant() {
        boolean bool;
        bool = false;
        List<Analyse> listAnalyse = analyseDao.queryRaw("where name = ?",textName.getText().toString());
        if (listAnalyse.size() != 0) {
            bool = true;
        }
        return bool;
    }

    public boolean checkFields(){
        boolean bool = true;
        if (!isFilled(textName)){
            layoutName.setError("Obligatoire");
            bool = false;
        } else {
            layoutName.setError(null);
        }
        return bool;
    }

    @OnClick(R.id.fabSave)
    public void fabSaveClick() {
        if (checkFields()) {
            if (!isExistant()) {
                saveToDb();
                //ouvrirActiviteSuivante(AddAnalyseActivity.this, AccueilActivity.class,true);
                rouvrirActiviteAccueil(this,true);
            } else {
                Toast.makeText(AddAnalyseActivity.this, "Analyse déjà existant", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(AddAnalyseActivity.this, "Saisie non valide", Toast.LENGTH_LONG).show();
        }
    }

    public void saveToDb() {
        Analyse analyse = new Analyse();
        analyse.setName(textName.getText().toString());
        analyseDao.insert(analyse);
        Toast.makeText(AddAnalyseActivity.this, "Analyse Enregistrée", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
