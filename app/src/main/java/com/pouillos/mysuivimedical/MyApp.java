package com.pouillos.mysuivimedical;

import android.app.Application;
import android.content.res.Resources;

public class MyApp extends Application {
//sert juste à recup context
    private static MyApp instance;
    private static Resources res;

    /*public MyApp() {
        instance = this;
        res = getResources();
    }*/

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        res = getResources();
    }

    public static MyApp getInstance() {
        return instance;
    }

    public static Resources getRes() {
        return res;
    }
}
