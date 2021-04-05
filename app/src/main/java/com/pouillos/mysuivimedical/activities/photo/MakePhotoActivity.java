package com.pouillos.mysuivimedical.activities.photo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Surface;
import android.widget.FrameLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.pouillos.mysuivimedical.R;
import com.pouillos.mysuivimedical.activities.NavDrawerActivity;
import com.pouillos.mysuivimedical.entities.PhotoAnalyse;
import com.pouillos.mysuivimedical.entities.PhotoExamen;
import com.pouillos.mysuivimedical.entities.PhotoOrdonnance;
import com.pouillos.mysuivimedical.entities.RdvAnalyse;
import com.pouillos.mysuivimedical.entities.RdvContact;
import com.pouillos.mysuivimedical.entities.RdvExamen;
import com.pouillos.mysuivimedical.enumeration.TypePhoto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;

public class MakePhotoActivity extends NavDrawerActivity {

    @BindView(R.id.fabTakePhoto)
FloatingActionButton fabTakePhoto;
    @BindView(R.id.fabSavePhoto)
    FloatingActionButton fabSavePhoto;
    @BindView(R.id.fabCancelPhoto)
    FloatingActionButton fabCancelPhoto;
    @BindView(R.id.preview_layout)
    FrameLayout previewFL;

    //Photo myPhoto;
    PhotoAnalyse myPhotoAnalyse;
    PhotoExamen myPhotoExamen;
    PhotoOrdonnance myPhotoOrdonnance;

    String type;
    Long itemId;

    final Camera camera = Camera.open();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_make_photo);

        // 6 - Configure all views
        this.configureToolBar();
        //this.configureBottomView();

        ButterKnife.bind(this);

        fabSavePhoto.hide();
        fabCancelPhoto.hide();

        traiterIntent();

        // needs explicit permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
            }
        }

       // final Camera camera = Camera.open();
        CameraPreview cameraPreview = new CameraPreview(this, camera);
       // cameraPreview.set
        // preview is required. But you can just cover it up in the layout.
        FrameLayout previewFL = findViewById(R.id.preview_layout);
        previewFL.addView(cameraPreview);
       // camera.setDisplayOrientation(90);
        
        //pr pb de resize
     /*   Camera.Parameters params = camera.getParameters();
        List<Camera.Size> supportedSizes = params.getSupportedPictureSizes();
        List<Camera.Size> supportedPreviews = params.getSupportedPreviewSizes();

        // layout in the activity that the cameraView will placed in
        int layoutWidth = previewFL.getWidth();
        int layoutHeight = previewFL.getHeight();

        Camera.Size sizePicture = supportedSizes.get(supportedSizes.size()-1);

        params.setPictureSize(sizePicture.width, sizePicture.height);

        params.setPictureSize(2304, 4096);
        params.setPreviewSize(720,1280);*/
        /////////
        
        
        camera.startPreview();
        
//camera.getParameters();

    }


    public void traiterIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("type")) {
            type = intent.getStringExtra("type");
        }
        if (intent.hasExtra("itemId")) {
            itemId = intent.getLongExtra("itemId",0);
        }
    }

    @OnClick(R.id.fabCancelPhoto)
    public void fabCancelPhotoClick() {
        if (type.equalsIgnoreCase(TypePhoto.Analyse.toString())) {
            photoAnalyseDao.delete(myPhotoAnalyse);
        } else if (type.equalsIgnoreCase(TypePhoto.Examen.toString())) {
            photoExamenDao.delete(myPhotoExamen);
        } else if (type.equalsIgnoreCase(TypePhoto.Ordonnance.toString())) {
            photoOrdonnanceDao.delete(myPhotoOrdonnance);
        }
       //photoDao.delete(myPhoto);
        recreate();
    }

    @OnClick(R.id.fabSavePhoto)
    public void fabSavePhotoClick() {
        Snackbar.make(fabSavePhoto, "Photo Enregistrée", Snackbar.LENGTH_LONG).show();
        rouvrirActiviteAccueil(this,true);
        //ouvrirActiviteSuivante(this);
    }

    @OnClick(R.id.fabTakePhoto)
    public void fabTakePhotoClick() {
        camera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                File pictureFileDir = getDir();
                if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
                    Snackbar.make(fabSavePhoto, "Can't create directory to save image.", Snackbar.LENGTH_LONG).show();
                    return;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
                Date dateNew = new Date();
                String date = dateFormat.format(dateNew);
                String photoFile = "MySuiviMedicalApp_Picture_" + date + ".jpg";
                String filename = pictureFileDir.getPath() + File.separator + photoFile;
                File pictureFile = new File(filename);
                int angleToRotate = getRoatationAngle(MakePhotoActivity.this, Camera.CameraInfo.CAMERA_FACING_FRONT);
                // Solve image inverting problem
                //angleToRotate = angleToRotate+180;
                Bitmap originalImage = BitmapFactory.decodeByteArray(data, 0, data.length);
               Bitmap bitmapImage = rotate(originalImage, angleToRotate);
                try {
                    FileOutputStream fOut = new FileOutputStream(pictureFile);
                    bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                    fOut.flush();
                    fOut.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (type.equalsIgnoreCase(TypePhoto.Analyse.toString())) {
                    myPhotoAnalyse = new PhotoAnalyse();
                    RdvAnalyse rdvAnalyse = rdvAnalyseDao.load(itemId);
                    myPhotoAnalyse.setDate(rdvAnalyse.getDate());
                    myPhotoAnalyse.setDateString(rdvAnalyse.getDateString());
                    myPhotoAnalyse.setPath(filename);
                    myPhotoAnalyse.setAnalyseId(rdvAnalyse.getAnalyseId());
                    myPhotoAnalyse.setRdvAnalyseId(itemId);
                    myPhotoAnalyse.setId(photoAnalyseDao.insert(myPhotoAnalyse));
                } else if (type.equalsIgnoreCase(TypePhoto.Examen.toString())) {
                    myPhotoExamen = new PhotoExamen();
                    RdvExamen rdvExamen = rdvExamenDao.load(itemId);
                    myPhotoExamen.setDate(rdvExamen.getDate());
                    myPhotoExamen.setDateString(rdvExamen.getDateString());
                    myPhotoExamen.setPath(filename);
                    myPhotoExamen.setExamenId(rdvExamen.getExamenId());
                    myPhotoExamen.setRdvExamenId(itemId);
                    myPhotoExamen.setId(photoExamenDao.insert(myPhotoExamen));
                } else if (type.equalsIgnoreCase(TypePhoto.Ordonnance.toString())) {
                    myPhotoOrdonnance = new PhotoOrdonnance();
                    RdvContact rdvContact = rdvContactDao.load(itemId);
                    myPhotoOrdonnance.setDate(rdvContact.getDate());
                    myPhotoOrdonnance.setDateString(rdvContact.getDateString());
                    myPhotoOrdonnance.setPath(filename);
                    myPhotoOrdonnance.setContactId(rdvContact.getContactId());
                    myPhotoOrdonnance.setRdvContactId(itemId);
                    myPhotoOrdonnance.setId(photoOrdonnanceDao.insert(myPhotoOrdonnance));
                }
                }
            });
        fabSavePhoto.show();
        fabCancelPhoto.show();
        fabTakePhoto.hide();
    }

    private File getDir() {
        File sdDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "MySuiviMedicalApp");
    }

    public static int getRoatationAngle(Activity mContext, int cameraId) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = mContext.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else { // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        return result;
    }

    public static Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        mtx.postRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }
}
