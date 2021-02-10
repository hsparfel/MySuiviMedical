// Generated code from Icepick. Do not modify!
package com.pouillos.mypilulier.activities.recherche;
import android.os.Bundle;
import icepick.Bundler;
import icepick.Injector.Helper;
import icepick.Injector.Object;

import java.util.Map;
import java.util.HashMap;

public class ChercherEtablissementActivity$$Icepick<T extends ChercherEtablissementActivity> extends com.pouillos.mypilulier.activities.NavDrawerActivity$$Icepick<T> {

  private final static Map<String, Bundler<?>> BUNDLERS = new HashMap<String, Bundler<?>>();
  static {
                  
  }

  private final static Helper H = new Helper("com.pouillos.mypilulier.activities.recherche.ChercherEtablissementActivity$$Icepick.", BUNDLERS);

  public void restore(T target, Bundle state) {
    if (state == null) return;
    target.booleanVille = H.getBoolean(state, "booleanVille");
    target.booleanCp = H.getBoolean(state, "booleanCp");
    target.booleanProximite = H.getBoolean(state, "booleanProximite");
    target.booleanPharmacie = H.getBoolean(state, "booleanPharmacie");
    target.booleanAutreType = H.getBoolean(state, "booleanAutreType");
    target.listAutocompletionVille = H.getStringArray(state, "listAutocompletionVille");
    target.etablissementSelected = H.getSerializable(state, "etablissementSelected");
    super.restore(target, state);
  }

  public void save(T target, Bundle state) {
    super.save(target, state);
    H.putBoolean(state, "booleanVille", target.booleanVille);
    H.putBoolean(state, "booleanCp", target.booleanCp);
    H.putBoolean(state, "booleanProximite", target.booleanProximite);
    H.putBoolean(state, "booleanPharmacie", target.booleanPharmacie);
    H.putBoolean(state, "booleanAutreType", target.booleanAutreType);
    H.putStringArray(state, "listAutocompletionVille", target.listAutocompletionVille);
    H.putSerializable(state, "etablissementSelected", target.etablissementSelected);
  }
}