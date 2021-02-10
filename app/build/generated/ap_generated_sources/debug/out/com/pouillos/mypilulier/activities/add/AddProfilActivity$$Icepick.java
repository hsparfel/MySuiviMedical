// Generated code from Icepick. Do not modify!
package com.pouillos.mypilulier.activities.add;
import android.os.Bundle;
import icepick.Bundler;
import icepick.Injector.Helper;
import icepick.Injector.Object;

import java.util.Map;
import java.util.HashMap;

public class AddProfilActivity$$Icepick<T extends AddProfilActivity> extends com.pouillos.mypilulier.activities.NavDrawerActivity$$Icepick<T> {

  private final static Map<String, Bundler<?>> BUNDLERS = new HashMap<String, Bundler<?>>();
  static {
            
  }

  private final static Helper H = new Helper("com.pouillos.mypilulier.activities.add.AddProfilActivity$$Icepick.", BUNDLERS);

  public void restore(T target, Bundle state) {
    if (state == null) return;
    target.activeUser = H.getSerializable(state, "activeUser");
    target.profilToCreate = H.getSerializable(state, "profilToCreate");
    target.lastProfil = H.getSerializable(state, "lastProfil");
    target.dateProfil = H.getSerializable(state, "dateProfil");
    super.restore(target, state);
  }

  public void save(T target, Bundle state) {
    super.save(target, state);
    H.putSerializable(state, "activeUser", target.activeUser);
    H.putSerializable(state, "profilToCreate", target.profilToCreate);
    H.putSerializable(state, "lastProfil", target.lastProfil);
    H.putSerializable(state, "dateProfil", target.dateProfil);
  }
}