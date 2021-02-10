// Generated code from Icepick. Do not modify!
package com.pouillos.mypilulier.activities.add;
import android.os.Bundle;
import icepick.Bundler;
import icepick.Injector.Helper;
import icepick.Injector.Object;

import java.util.Map;
import java.util.HashMap;

public class AddRdvContactActivity$$Icepick<T extends AddRdvContactActivity> extends com.pouillos.mypilulier.activities.NavDrawerActivity$$Icepick<T> {

  private final static Map<String, Bundler<?>> BUNDLERS = new HashMap<String, Bundler<?>>();
  static {
            
  }

  private final static Helper H = new Helper("com.pouillos.mypilulier.activities.add.AddRdvContactActivity$$Icepick.", BUNDLERS);

  public void restore(T target, Bundle state) {
    if (state == null) return;
    target.activeUser = H.getSerializable(state, "activeUser");
    target.contact = H.getSerializable(state, "contact");
    target.date = H.getSerializable(state, "date");
    target.rdvContactToCreate = H.getSerializable(state, "rdvContactToCreate");
    super.restore(target, state);
  }

  public void save(T target, Bundle state) {
    super.save(target, state);
    H.putSerializable(state, "activeUser", target.activeUser);
    H.putSerializable(state, "contact", target.contact);
    H.putSerializable(state, "date", target.date);
    H.putSerializable(state, "rdvContactToCreate", target.rdvContactToCreate);
  }
}