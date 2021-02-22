// Generated code from Icepick. Do not modify!
package com.pouillos.mysuivimedical.activities.afficher;
import android.os.Bundle;
import icepick.Bundler;
import icepick.Injector.Helper;
import icepick.Injector.Object;

import java.util.Map;
import java.util.HashMap;

public class AfficherPhotoActivity$$Icepick<T extends AfficherPhotoActivity> extends Object<T> {

  private final static Map<String, Bundler<?>> BUNDLERS = new HashMap<String, Bundler<?>>();
  static {
            
  }

  private final static Helper H = new Helper("com.pouillos.mysuivimedical.activities.afficher.AfficherPhotoActivity$$Icepick.", BUNDLERS);

  public void restore(T target, Bundle state) {
    if (state == null) return;
    target.booleanOrdonnance = H.getBoolean(state, "booleanOrdonnance");
    target.booleanAnalyse = H.getBoolean(state, "booleanAnalyse");
    target.booleanExamen = H.getBoolean(state, "booleanExamen");
    target.file = H.getSerializable(state, "file");
    super.restore(target, state);
  }

  public void save(T target, Bundle state) {
    super.save(target, state);
    H.putBoolean(state, "booleanOrdonnance", target.booleanOrdonnance);
    H.putBoolean(state, "booleanAnalyse", target.booleanAnalyse);
    H.putBoolean(state, "booleanExamen", target.booleanExamen);
    H.putSerializable(state, "file", target.file);
  }
}