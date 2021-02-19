package com.pouillos.mypilulier.activities.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;

import com.pouillos.mypilulier.activities.AccueilActivity;
import com.pouillos.mypilulier.activities.NavDrawerActivity;

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer mp=MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
        mp.start();
    }
}
