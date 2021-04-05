// Generated code from Icepick. Do not modify!
package com.pouillos.mysuivimedical.activities.afficher;
import android.os.Bundle;
import icepick.Bundler;
import icepick.Injector.Helper;
import icepick.Injector.Object;

import java.util.Map;
import java.util.HashMap;

public class AfficherRdvActivity$$Icepick<T extends AfficherRdvActivity> extends Object<T> {

  private final static Map<String, Bundler<?>> BUNDLERS = new HashMap<String, Bundler<?>>();
  static {
            
  }

  private final static Helper H = new Helper("com.pouillos.mysuivimedical.activities.afficher.AfficherRdvActivity$$Icepick.", BUNDLERS);

  public void restore(T target, Bundle state) {
    if (state == null) return;
    target.date = H.getSerializable(state, "date");
    target.booleanContact = H.getBoolean(state, "booleanContact");
    target.booleanAnalyse = H.getBoolean(state, "booleanAnalyse");
    target.booleanExamen = H.getBoolean(state, "booleanExamen");
    super.restore(target, state);
  }

  public void save(T target, Bundle state) {
    super.save(target, state);
    H.putSerializable(state, "date", target.date);
    H.putBoolean(state, "booleanContact", target.booleanContact);
    H.putBoolean(state, "booleanAnalyse", target.booleanAnalyse);
    H.putBoolean(state, "booleanExamen", target.booleanExamen);
  }
}