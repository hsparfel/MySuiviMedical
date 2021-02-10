// Generated code from Icepick. Do not modify!
package com.pouillos.mypilulier.activities.add;
import android.os.Bundle;
import icepick.Bundler;
import icepick.Injector.Helper;
import icepick.Injector.Object;

import java.util.Map;
import java.util.HashMap;

public class AddRappelActivity$$Icepick<T extends AddRappelActivity> extends com.pouillos.mypilulier.activities.NavDrawerActivity$$Icepick<T> {

  private final static Map<String, Bundler<?>> BUNDLERS = new HashMap<String, Bundler<?>>();
  static {
              
  }

  private final static Helper H = new Helper("com.pouillos.mypilulier.activities.add.AddRappelActivity$$Icepick.", BUNDLERS);

  public void restore(T target, Bundle state) {
    if (state == null) return;
    target.activeUser = H.getSerializable(state, "activeUser");
    target.heure = H.getString(state, "heure");
    target.doseSelected = H.getSerializable(state, "doseSelected");
    target.prescription = H.getSerializable(state, "prescription");
    target.rappel = H.getSerializable(state, "rappel");
    super.restore(target, state);
  }

  public void save(T target, Bundle state) {
    super.save(target, state);
    H.putSerializable(state, "activeUser", target.activeUser);
    H.putString(state, "heure", target.heure);
    H.putSerializable(state, "doseSelected", target.doseSelected);
    H.putSerializable(state, "prescription", target.prescription);
    H.putSerializable(state, "rappel", target.rappel);
  }
}