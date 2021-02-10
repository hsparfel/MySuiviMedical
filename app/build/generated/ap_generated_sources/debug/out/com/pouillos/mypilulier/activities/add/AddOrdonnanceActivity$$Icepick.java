// Generated code from Icepick. Do not modify!
package com.pouillos.mypilulier.activities.add;
import android.os.Bundle;
import icepick.Bundler;
import icepick.Injector.Helper;
import icepick.Injector.Object;

import java.util.Map;
import java.util.HashMap;

public class AddOrdonnanceActivity$$Icepick<T extends AddOrdonnanceActivity> extends com.pouillos.mypilulier.activities.NavDrawerActivity$$Icepick<T> {

  private final static Map<String, Bundler<?>> BUNDLERS = new HashMap<String, Bundler<?>>();
  static {
              
  }

  private final static Helper H = new Helper("com.pouillos.mypilulier.activities.add.AddOrdonnanceActivity$$Icepick.", BUNDLERS);

  public void restore(T target, Bundle state) {
    if (state == null) return;
    target.activeUser = H.getSerializable(state, "activeUser");
    target.date = H.getSerializable(state, "date");
    target.contactSelected = H.getSerializable(state, "contactSelected");
    target.rdvContactSelected = H.getSerializable(state, "rdvContactSelected");
    target.ordonnance = H.getSerializable(state, "ordonnance");
    super.restore(target, state);
  }

  public void save(T target, Bundle state) {
    super.save(target, state);
    H.putSerializable(state, "activeUser", target.activeUser);
    H.putSerializable(state, "date", target.date);
    H.putSerializable(state, "contactSelected", target.contactSelected);
    H.putSerializable(state, "rdvContactSelected", target.rdvContactSelected);
    H.putSerializable(state, "ordonnance", target.ordonnance);
  }
}