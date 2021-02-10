// Generated code from Icepick. Do not modify!
package com.pouillos.mypilulier.activities.recherche;
import android.os.Bundle;
import icepick.Bundler;
import icepick.Injector.Helper;
import icepick.Injector.Object;

import java.util.Map;
import java.util.HashMap;

public class ChercherContactActivity$$Icepick<T extends ChercherContactActivity> extends com.pouillos.mypilulier.activities.NavDrawerActivity$$Icepick<T> {

  private final static Map<String, Bundler<?>> BUNDLERS = new HashMap<String, Bundler<?>>();
  static {
                        
  }

  private final static Helper H = new Helper("com.pouillos.mypilulier.activities.recherche.ChercherContactActivity$$Icepick.", BUNDLERS);

  public void restore(T target, Bundle state) {
    if (state == null) return;
    target.booleanMedecin = H.getBoolean(state, "booleanMedecin");
    target.booleanAutre = H.getBoolean(state, "booleanAutre");
    target.booleanSpecialite = H.getBoolean(state, "booleanSpecialite");
    target.booleanActivite = H.getBoolean(state, "booleanActivite");
    target.booleanDepartement = H.getBoolean(state, "booleanDepartement");
    target.booleanRegion = H.getBoolean(state, "booleanRegion");
    target.listAutocompletion = H.getStringArray(state, "listAutocompletion");
    target.utilisateurDepartement = H.getSerializable(state, "utilisateurDepartement");
    target.utilisateurRegion = H.getSerializable(state, "utilisateurRegion");
    target.contactSelected = H.getSerializable(state, "contactSelected");
    super.restore(target, state);
  }

  public void save(T target, Bundle state) {
    super.save(target, state);
    H.putBoolean(state, "booleanMedecin", target.booleanMedecin);
    H.putBoolean(state, "booleanAutre", target.booleanAutre);
    H.putBoolean(state, "booleanSpecialite", target.booleanSpecialite);
    H.putBoolean(state, "booleanActivite", target.booleanActivite);
    H.putBoolean(state, "booleanDepartement", target.booleanDepartement);
    H.putBoolean(state, "booleanRegion", target.booleanRegion);
    H.putStringArray(state, "listAutocompletion", target.listAutocompletion);
    H.putSerializable(state, "utilisateurDepartement", target.utilisateurDepartement);
    H.putSerializable(state, "utilisateurRegion", target.utilisateurRegion);
    H.putSerializable(state, "contactSelected", target.contactSelected);
  }
}