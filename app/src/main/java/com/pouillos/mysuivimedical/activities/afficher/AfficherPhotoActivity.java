package com.pouillos.mysuivimedical.activities.afficher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.ortiz.touchview.TouchImageView;
import com.pouillos.mysuivimedical.R;
import com.pouillos.mysuivimedical.activities.NavDrawerActivity;
import com.pouillos.mysuivimedical.entities.PhotoAnalyse;
import com.pouillos.mysuivimedical.entities.PhotoExamen;
import com.pouillos.mysuivimedical.entities.PhotoOrdonnance;
import com.pouillos.mysuivimedical.interfaces.BasicUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;
import icepick.State;

public class AfficherPhotoActivity  extends NavDrawerActivity implements BasicUtils, AdapterView.OnItemClickListener {

    List<?> listItemBD;
    ArrayAdapter adapter;

    @BindView(R.id.selectItem)
    AutoCompleteTextView selectedItem;
    @BindView(R.id.listItem)
    TextInputLayout listItem;

    @BindView(R.id.fabShare)
    FloatingActionButton fabShare;
    @BindView(R.id.fabDelete)
    FloatingActionButton fabDelete;

    @State
    boolean booleanOrdonnance = false;
    @State
    boolean booleanAnalyse = false;
    @State
    boolean booleanExamen = false;

    @BindView(R.id.chipOrdonnance)
    Chip chipOrdonnance;
    @BindView(R.id.chipAnalyse)
    Chip chipAnalyse;
    @BindView(R.id.chipExamen)
    Chip chipExamen;
    @BindView(R.id.chipGroupType)
    ChipGroup chipGroupType;

    @BindView(R.id.my_progressBar)
    ProgressBar progressBar;

    @BindView(R.id.imageView)
    TouchImageView imageView;

    PhotoAnalyse photoAnalyseSelected;
    PhotoExamen photoExamenSelected;
    PhotoOrdonnance photoOrdonnanceSelected;

    @State
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_afficher_photo);

        this.configureToolBar();
        this.configureBottomView();

        ButterKnife.bind(this);

        selectedItem.setOnItemClickListener(this);
        setTitle("Mes Photos");

        fabShare.hide();
        fabDelete.hide();

        Menu bottomNavigationViewMenu = bottomNavigationView.getMenu();
        bottomNavigationViewMenu.findItem(R.id.bottom_navigation_photo).setChecked(true);
    }

    @OnClick(R.id.fabShare)
    public void fabShareClick() {
        shareIt();
    }

    private void shareIt() {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);

        try {
            Uri fileUri = FileProvider.getUriForFile(
                    AfficherPhotoActivity.this,
                    "com.pouillos.mysuivimedical.fileprovider",
                    file);
            if (fileUri != null) {
                // Grant temporary read permission to the content URI
                sharingIntent.addFlags(
                        Intent.FLAG_GRANT_READ_URI_PERMISSION);
            sharingIntent.setDataAndType(
                    fileUri,
                    getContentResolver().getType(fileUri));
            // Set the result
            AfficherPhotoActivity.this.setResult(Activity.RESULT_OK,
                    sharingIntent);
            } else {
                sharingIntent.setDataAndType(null, "");
                   AfficherPhotoActivity.this.setResult(RESULT_CANCELED,
                        sharingIntent);
             }
        } catch (IllegalArgumentException e) {
            Log.e("File Selector",
                    "The selected file can't be shared: " + file.toString());
        }

        sharingIntent.setType("image/jpg");
        String shareBody = "MySuiviMedical - "+selectedItem.getText();





        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "MySuiviMedical - "+selectedItem.getText());
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        Uri photoURI = FileProvider.getUriForFile(AfficherPhotoActivity.this, "com.pouillos.mysuivimedical.fileprovider", file);

        sharingIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @OnClick(R.id.fabDelete)
    public void fabDeleteClick() {
        if (chipAnalyse.isChecked()) {
            photoAnalyseDao.delete(photoAnalyseSelected);
        } else if (chipExamen.isChecked()) {
            photoExamenDao.delete(photoExamenSelected);
        } else if (chipOrdonnance.isChecked()) {
            photoOrdonnanceDao.delete(photoOrdonnanceSelected);
        }
        //photoDao.delete(photoSelected);
        ouvrirActiviteSuivante(this,AfficherPhotoActivity.class,true);
    }


    @OnClick(R.id.chipOrdonnance)
    public void chipOrdonnanceClick() {
        progressBar.setVisibility(View.VISIBLE);
        booleanOrdonnance = !booleanOrdonnance;
        booleanAnalyse = false;
        booleanExamen = false;
        chipOrdonnance.setEnabled(false);
        chipAnalyse.setEnabled(true);
        chipExamen.setEnabled(true);
        imageView.setVisibility(View.INVISIBLE);
        fabShare.hide();
        fabDelete.hide();
        selectedItem.setText("");
        AfficherPhotoActivity.AsyncTaskRunnerBD runnerBD = new AfficherPhotoActivity.AsyncTaskRunnerBD();
        runnerBD.execute();
    }

    @OnClick(R.id.chipAnalyse)
    public void chipAnalyseClick() {
        progressBar.setVisibility(View.VISIBLE);
        booleanAnalyse = !booleanAnalyse;
        booleanOrdonnance = false;
        booleanExamen = false;
        chipOrdonnance.setEnabled(true);
        chipAnalyse.setEnabled(false);
        chipExamen.setEnabled(true);
        imageView.setVisibility(View.INVISIBLE);
        fabShare.hide();
        fabDelete.hide();
        selectedItem.setText("");
        AfficherPhotoActivity.AsyncTaskRunnerBD runnerBD = new AfficherPhotoActivity.AsyncTaskRunnerBD();
        runnerBD.execute();
    }

    @OnClick(R.id.chipExamen)
    public void chipExamenClick() {
        progressBar.setVisibility(View.VISIBLE);
        booleanExamen = !booleanExamen;
        booleanAnalyse = false;
        booleanOrdonnance = false;
        chipOrdonnance.setEnabled(true);
        chipAnalyse.setEnabled(true);
        chipExamen.setEnabled(false);
        imageView.setVisibility(View.INVISIBLE);
        fabShare.hide();
        fabDelete.hide();
        selectedItem.setText("");
        AfficherPhotoActivity.AsyncTaskRunnerBD runnerBD = new AfficherPhotoActivity.AsyncTaskRunnerBD();
        runnerBD.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //imageView.setImageBitmap(null);
        imageView.setVisibility(View.VISIBLE);
        if (chipAnalyse.isChecked()) {
            photoAnalyseSelected = (PhotoAnalyse) listItemBD.get(position);
            fabShare.hide();
            fabDelete.hide();
            afficherPhoto(photoAnalyseSelected);
        } else if (chipExamen.isChecked()) {
            photoExamenSelected = (PhotoExamen) listItemBD.get(position);
            fabShare.hide();
            fabDelete.hide();
            afficherPhoto(photoExamenSelected);
        } else if (chipOrdonnance.isChecked()) {
            photoOrdonnanceSelected = (PhotoOrdonnance) listItemBD.get(position);
            fabShare.hide();
            fabDelete.hide();
            afficherPhoto(photoOrdonnanceSelected);
        }



       // photoSelected = (Photo) listItemBD.get(position);
      //  fabShare.hide();
      //  fabDelete.hide();
      //  afficherPhoto(photoSelected);

    }

    private void afficherPhoto(Object object) {
        imageView.setVisibility(View.VISIBLE);
        String filename = "";
        if (chipAnalyse.isChecked()) {
            filename = ((PhotoAnalyse) object).getPath();
        } else if (chipExamen.isChecked()) {
            filename = ((PhotoExamen) object).getPath();
        } else if (chipOrdonnance.isChecked()) {
            filename = ((PhotoOrdonnance) object).getPath();
        }
        //String filename = photo.getPath();
        //String imagePath = getExternalFilesDir() + "/" + filename;
        //String imagePath = Environment
             //   .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/MonPilulierApp"+filename;
        file = new File(filename);
        if (file.isFile()) {
            Picasso.with(AfficherPhotoActivity.this)
                    .load(new File(filename))
                    //.rotate(90)
                    .into(imageView);
            fabShare.show();
            fabDelete.show();
        } else {
            Snackbar.make(fabDelete, "fichier image introuvable", Snackbar.LENGTH_LONG).show();
            fabDelete.show();
        }
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

    public class AsyncTaskRunnerBD extends AsyncTask<Void, Integer, Void> {

        protected Void doInBackground(Void...voids) {
            publishProgress(0);

            publishProgress(50);
            String requete = "";

            if (booleanOrdonnance) {

                listItemBD = photoOrdonnanceDao.loadAll();
              //  requete += "\""+ TypePhoto.Ordonnance.toString() +"\"";
            } else if (booleanAnalyse) {
                //requete += "\""+ "Analyses" +"\"";
                //requete += "\""+ TypePhoto.RdvAnalyse +"\"";
              // requete += "\""+ TypePhoto.Analyse.toString() +"\"";
                listItemBD = photoAnalyseDao.loadAll();
            } else if (booleanExamen) {
                //requete += "\""+ TypePhoto.Examen.toString() +"\"";
                listItemBD = photoExamenDao.loadAll();
            }
            //listItemBD = photoDao.queryRaw(requete);


            publishProgress(100);
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            if (listItemBD.size() == 0) {
                Snackbar.make(fabDelete, R.string.text_no_matching, Snackbar.LENGTH_LONG).show();
                listItem.setVisibility(View.GONE);
            } else {
                listItem.setVisibility(View.VISIBLE);
            }
            buildDropdownMenu(listItemBD, AfficherPhotoActivity.this,selectedItem);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }
}